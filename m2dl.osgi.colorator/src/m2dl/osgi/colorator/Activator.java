package m2dl.osgi.colorator;

import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import m2dl.osgi.colorator.impl.ColorizerServiceImpl;
import m2dl.osgi.editor.interfaces.Colorizer;

public class Activator implements BundleActivator {

	private static final Logger logger = LoggerFactory.getLogger("m2dl.osgi.syntaxparser");
	
	private static BundleContext context;
	private ServiceRegistration<Colorizer> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		ServiceFactory<Colorizer> colorizerFactory = new ServiceFactory<Colorizer>() {
			@Override
			public void ungetService(Bundle bundle, ServiceRegistration<Colorizer> registration, Colorizer service) {
			}
			
			@Override
			public Colorizer getService(Bundle bundle, ServiceRegistration<Colorizer> registration) {
				Colorizer colorizerService = new ColorizerServiceImpl();
				return colorizerService;
			}
		};
		serviceRegistration = Activator.context.registerService(Colorizer.class, colorizerFactory, new Hashtable<String, Object>());
		
		logger.info("Module m2dl.osgi.colorator has been loaded");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		serviceRegistration.unregister();
	}

}
