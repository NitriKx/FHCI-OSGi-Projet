package m2dl.example.osgi.service.provider.impl;

import m2dl.example.osgi.service.provider.MyService;

public class MyServiceImpl implements MyService {

	@Override
	public void sayHello() {
		System.out.println("I say hello!");
	}

}
