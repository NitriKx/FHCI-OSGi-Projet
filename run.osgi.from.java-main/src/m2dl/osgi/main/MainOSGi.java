package m2dl.osgi.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class MainOSGi {

	private static final String ECLIPSE_RCP_INSTALLED_PATH = "/Applications/Eclipse OSGi.app/Contents/Eclipse";

	private static BundleContext bundleContext;

	public static void installAndStartBundle(final String fileJar) {

		final File fileBundle = new File(fileJar);
		Bundle myBundle;
		try {
			myBundle = bundleContext.installBundle(fileBundle.toURI().toString());
			myBundle.start();
			System.out.println("The bundle " + fileJar + " installed and started");
		} catch (final BundleException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		startOSGiFramework();

		// installAndStartBundle("/tmp/plugins/m2dl.osgi.editor_1.0.0.jar");

		showInstalledBundles();
	}

	private static void showInstalledBundles() {

		System.out.println("Installed bundles:");

		for (final Bundle bundle : bundleContext.getBundles()) {
			System.out.println(" - " + bundle.getSymbolicName());
		}

	}

	public static void startOSGiFramework() {

		final Map<String, String> properties = new HashMap<>();
		/*
		 * Liste des bundle à lancer au démarrage de OSGi.
		 */
		properties.put("osgi.bundles", ECLIPSE_RCP_INSTALLED_PATH
				+ "/plugins/org.apache.felix.gogo.command_0.10.0.v201209301215.jar@start," + ECLIPSE_RCP_INSTALLED_PATH
				// + "/plugins/org.eclipse.equinox.console_1.1.100.v20141023-1406.jar@start," + ECLIPSE_RCP_INSTALLED_PATH
				+ "/plugins/org.eclipse.equinox.console_1.1.200.v20150929-1405.jar@start," + ECLIPSE_RCP_INSTALLED_PATH
				+ "/plugins/org.apache.felix.gogo.runtime_0.10.0.v201209301036.jar@start," + ECLIPSE_RCP_INSTALLED_PATH
				+ "/plugins/org.apache.felix.gogo.shell_0.10.0.v201212101605.jar@start," + ECLIPSE_RCP_INSTALLED_PATH
				+ "/plugins/org.apache.log4j_1.2.15.v201012070815.jar@start,");
		/*
		 * Pour avoir un accès à OSGi en telnet (telnet localhost 8080)
		 */
		properties.put("osgi.console", "127.0.0.1:8080");
		try {
			/*
			 * Démarrage de OSGi
			 */
			EclipseStarter.setInitialProperties(properties);
			bundleContext = EclipseStarter.startup(new String[] { "-clean" }, null);
			System.out.println("OSGi is started");
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
	}
}
