package m2dl.osgi.editor;

import java.util.logging.Logger;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.interfaces.Tokenizer;

public class TokenizerServiceTracker implements ServiceTrackerCustomizer<Tokenizer, Tokenizer> {

	private static final Logger logger = Logger.getLogger(TokenizerServiceTracker.class.getName());
	
	private CodeViewerController codeViewerController;

	public TokenizerServiceTracker(CodeViewerController codeViewerController) {
		this.codeViewerController = codeViewerController;
	}
	
	@Override
	public Tokenizer addingService(ServiceReference<Tokenizer> reference) {
		Tokenizer tokenizer = Activator.context.getService(reference);
		codeViewerController.tokenizerServices.put(tokenizer.getType(), tokenizer);
		logger.info(String.format("Tokenizer service %s has been loaded", tokenizer.getClass().getName()));
		return tokenizer;
	}

	@Override
	public void modifiedService(ServiceReference<Tokenizer> reference, Tokenizer service) {
		// NOTHING
	}

	@Override
	public void removedService(ServiceReference<Tokenizer> reference, Tokenizer service) {
		codeViewerController.tokenizerServices.remove(service.getType());
	}

}
