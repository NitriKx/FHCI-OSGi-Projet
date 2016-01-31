package m2dl.osgi.editor;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import m2dl.osgi.editor.interfaces.Colorizer;

public class ColorizerServiceTracker implements ServiceTrackerCustomizer<Colorizer, Colorizer> {

	private static final Logger logger = LoggerFactory.getLogger(ColorizerServiceTracker.class);
	
	private CodeViewerController codeViewerController;

	public ColorizerServiceTracker(CodeViewerController codeViewerController) {
		this.codeViewerController = codeViewerController;
	}
	
	@Override
	public Colorizer addingService(ServiceReference<Colorizer> reference) {
		Colorizer colorizerService = Activator.context.getService(reference);
		codeViewerController.colorizerService = colorizerService;
		logger.info("Colorizer service {} has been loaded", colorizerService.getClass().getName());
		return colorizerService;
	}

	@Override
	public void modifiedService(ServiceReference<Colorizer> reference, Colorizer service) {
		// NOTHING
	}

	@Override
	public void removedService(ServiceReference<Colorizer> reference, Colorizer service) {
		codeViewerController.colorizerService = null;
	}

}
