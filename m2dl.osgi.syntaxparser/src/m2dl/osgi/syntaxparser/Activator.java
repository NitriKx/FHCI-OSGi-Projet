package m2dl.osgi.syntaxparser;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import m2dl.osgi.syntaxparser.impl.TokenizerImpl;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private static final Logger logger = LoggerFactory.getLogger("m2dl.osgi.syntaxparser");
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		logger.info("Registering tokenizerService...");
		final Tokenizer tokenizerService = new TokenizerImpl();
		bundleContext.registerService(Tokenizer.class.getName(), tokenizerService, new Hashtable<>());
		
		logger.info("Module m2dl.osgi.syntaxparser has been loaded");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
