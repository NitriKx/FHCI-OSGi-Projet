package m2dl.example.osgi.service.provider.activator;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import m2dl.example.osgi.service.provider.MyService;
import m2dl.example.osgi.service.provider.impl.MyServiceImpl;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		final MyService myService = new MyServiceImpl();

		final Hashtable<String, String> dictionnary = new Hashtable<>();
		dictionnary.put("my.metadata.type", "my.metadata.value");

		bundleContext.registerService(MyService.class.getName(), myService, dictionnary);
		System.out.println("My bundle is started and registered");

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
