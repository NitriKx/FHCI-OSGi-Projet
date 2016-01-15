package m2dl.osgi.b2.activator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import m2dl.example.osgi.service.provider.MyService;

public class MyServiceTrackerCustomiser implements ServiceTrackerCustomizer<MyService, MyService> {

	private final BundleContext context;

	public MyServiceTrackerCustomiser(BundleContext _context) {
		context = _context;
	}

	@Override
	public MyService addingService(ServiceReference<MyService> serviceReference) {

		final MyService service = context.getService(serviceReference);

		System.out.println("A new \"MyService\" appeared with the extention type = "
				+ serviceReference.getProperty("my.metadata.type"));

		service.sayHello();

		return service;
	}

	@Override
	public void modifiedService(ServiceReference<MyService> arg0, MyService arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removedService(ServiceReference<MyService> arg0, MyService arg1) {
		// TODO Auto-generated method stub

	}

}
