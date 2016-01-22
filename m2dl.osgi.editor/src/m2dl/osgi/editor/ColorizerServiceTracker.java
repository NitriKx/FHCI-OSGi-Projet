package m2dl.osgi.editor;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.osgi.editor.interfaces.Colorizer;

public class ColorizerServiceTracker implements ServiceTrackerCustomizer<Colorizer, Colorizer> {

	private CodeViewerController codeViewerController;

	public ColorizerServiceTracker(CodeViewerController codeViewerController) {
		this.codeViewerController = codeViewerController;
	}
	
	@Override
	public Colorizer addingService(ServiceReference<Colorizer> reference) {
		Colorizer colorizerService = Activator.context.getService(reference);
		codeViewerController.colorizerService = colorizerService;
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
