package m2dl.osgi.b2.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.example.osgi.service.provider.MyService;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {

		final ServiceTrackerCustomizer<MyService, MyService> trackerCustomizer = new MyServiceTrackerCustomiser(
				context);

		final ServiceTracker<MyService, MyService> mainService = new ServiceTracker<MyService, MyService>(context,
				MyService.class.getName(), trackerCustomizer);
		mainService.open();

		System.out.println("A tracker for \"MyService\" is started.");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
