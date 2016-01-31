package m2dl.osgi.editor;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import m2dl.osgi.editor.interfaces.Tokenizer;

public class TokenizerServiceTracker implements ServiceTrackerCustomizer<Tokenizer, Tokenizer> {

	private static final Logger logger = LoggerFactory.getLogger(TokenizerServiceTracker.class);
	
	private CodeViewerController codeViewerController;

	public TokenizerServiceTracker(CodeViewerController codeViewerController) {
		this.codeViewerController = codeViewerController;
	}
	
	@Override
	public Tokenizer addingService(ServiceReference<Tokenizer> reference) {
		Tokenizer tokenizer = Activator.context.getService(reference);
		codeViewerController.tokenizerServices.put(tokenizer.getType(), tokenizer);
		logger.info("Tokenizer service {} has been loaded", tokenizer.getClass().getName());
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
