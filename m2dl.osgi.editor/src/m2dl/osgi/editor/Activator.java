package m2dl.osgi.editor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import m2dl.osgi.editor.interfaces.Colorizer;
import m2dl.osgi.editor.interfaces.Tokenizer;

public class Activator implements BundleActivator {

	public static final Logger logger = LoggerFactory.getLogger("m2dl.osgi.editor");
	
	public static BundleContext context;
	
	/**
	 * To know if the window is running.
	 */
	private static Boolean windowIsRunning = false;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		Activator.context = bundleContext;
		
		/*
		 * Starting only one JavaFX window.
		 */

		if (!windowIsRunning) {
			new JFXPanel();
			Platform.runLater(() -> {
				try {
					/*
					 * Init the JavaFX nodes.
					 */
					final FXMLLoader loader = new FXMLLoader(
							CodeViewerController.class.getResource("main-window-exercice.fxml"));
					loader.load();

					final VBox root = (VBox) loader.getRoot();
					final Stage primaryStage = new Stage();
					final Scene scene = new Scene(root, 400, 400);

					final CodeViewerController controller = loader.getController();

					controller.setPrimaryStage(primaryStage);
					/*
					 * Setup the window
					 */
					primaryStage.setTitle("The first OSGi modulable text editor");
					primaryStage.setScene(scene);
					/*
					 * Display the window.
					 */
					primaryStage.show();
					windowIsRunning = true;
					logger.info("The editor is running");

					// Register the service trackers
					final ServiceTrackerCustomizer<Tokenizer, Tokenizer> tokenizerCustomizer = new TokenizerServiceTracker(controller);
					final ServiceTracker<Tokenizer, Tokenizer> mainServiceTokenizer = new ServiceTracker<Tokenizer, Tokenizer>(context, Tokenizer.class.getName(), tokenizerCustomizer);
					mainServiceTokenizer.open();
					
					final ServiceTrackerCustomizer<Colorizer, Colorizer> colorizerCustomizer = new ColorizerServiceTracker(controller);
					final ServiceTracker<Colorizer, Colorizer> mainServiceColorizer = new ServiceTracker<Colorizer, Colorizer>(context, Colorizer.class.getName(), colorizerCustomizer);
					mainServiceColorizer.open();

					
				} catch (final Exception e) {
					logger.info("Error during loading the window");
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		/*
		 * Stopping the editor.
		 */
		logger.info("The editor will be stopted.");
	}

}

/*
 * Get the menu bar from the window. File "main-window-exercice.java", line 15:
 * <OSGiMenuBar fx:id="MenuActions" VBox.vgrow="NEVER">
 */
// final OSGiMenuBar osgiMenuBar = (OSGiMenuBar)
// scene.lookup("#MenuActions");
// /*
// * Export the OSGiMenuBar service.
// */
// final Hashtable<String, String> dictionnary = new
// Hashtable<>();
// dictionnary.put("m2dl.osgi.editor.javafx-node",
// OSGiMenuBar.class.getName());
// bundleContext.registerService(IOSGiMenuBar.class.getName(),
// osgiMenuBar, dictionnary);
// logger.info(osgiMenuBar + " is now available");
// /*
// * TODO THE OTHER OSGi SERVICES BASED ON THE WINDOW HAVE
// TO
// * BE DECLARED HERE!
// */
//
