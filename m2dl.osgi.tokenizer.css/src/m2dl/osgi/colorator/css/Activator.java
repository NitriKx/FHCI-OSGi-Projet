package m2dl.osgi.colorator.css;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

import m2dl.osgi.colorator.css.impl.TokenizerCSSImpl;
import m2dl.osgi.editor.interfaces.Tokenizer;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration<Tokenizer> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		ServiceFactory<Tokenizer> cssTokenizerFactory = new ServiceFactory<Tokenizer>() {
			@Override
			public void ungetService(Bundle bundle, ServiceRegistration<Tokenizer> registration, Tokenizer service) {
			}
			
			@Override
			public Tokenizer getService(Bundle bundle, ServiceRegistration<Tokenizer> registration) {
				Tokenizer cssTokenizer = new TokenizerCSSImpl();
				return cssTokenizer;
			}
		};
		serviceRegistration = Activator.context.registerService(Tokenizer.class, cssTokenizerFactory, new Hashtable<String, Object>());
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
