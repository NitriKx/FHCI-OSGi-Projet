package m2dl.osgi.editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import m2dl.osgi.editor.interfaces.Colorizer;
import m2dl.osgi.editor.interfaces.Tokenizer;
import m2dl.osgi.editor.interfaces.Tokenizer.Type;

public class CodeViewerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CodeViewerController.class);

	private Map<String, Bundle> loadedBundle = new LinkedHashMap<>(Type.values().length);
	
	public Map<Type, Tokenizer> tokenizerServices = new LinkedHashMap<>();
	public Colorizer colorizerService = null;
	
	/**
	 * The main window of the application.
	 */
	private Stage primaryStage;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/**
	 * Radio button: indicate if the html bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuJava;

	/**
	 * Radio button: indicate if the decorator bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuDecorator;

	/**
	 * The viewer to display the content of the opened file.
	 */
	@FXML
	private WebView webViewer;

	/**
	 * The radio button: indicate if the css bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuCSS;

	/**
	 * The button "À propos" have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuAPropos(ActionEvent event) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		final VBox dialogVbox = new VBox(50);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.getChildren().add(new Text("This is a modulable code viewer"));
		final Scene dialogScene = new Scene(dialogVbox, 300, 80);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	@FXML
	void fireMenuCloseFile(ActionEvent event) {
		WebEngine webEngine = webViewer.getEngine();
		webEngine.load("about:blank");
	}

	@FXML
	void fireMenuExit(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * The button to load a bundle have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuLoadBundle(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

		if (selectedFile != null) {
			Activator.logger.info("File selected: " + selectedFile.getName());
			try {
				String filePathWithProtocol = String.format("file://%s", selectedFile.getAbsolutePath());
				Bundle freshInstalledBundle = Activator.context.installBundle(filePathWithProtocol);
				freshInstalledBundle.stop();
				String bundleSymbolicName = freshInstalledBundle.getHeaders().get("Bundle-SymbolicName");
				this.loadedBundle.put(bundleSymbolicName, freshInstalledBundle);
			} catch (Exception e) {
				String message = String.format("Impossible d'installer le bundle: %s", selectedFile.getAbsolutePath());
				Activator.logger.error(message, e);
				throw new RuntimeException(message, e);
			}
			
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}

	/**
	 * The button to open a file have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuOpenFile(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

		if (selectedFile != null) {
			Activator.logger.info("File selected: " + selectedFile.getName());
			processFileThrgouhtColorationPipeline(selectedFile);
			
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}

	@FXML
	void fireRadioMenuCSS(ActionEvent event) {
		/*
		 * If the css bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
		String cssBundleName = String.format("m2dl.osgi.tokenizer.%s", "css");
		toggleBundleState(cssBundleName);
	}

	@FXML
	void fireRadioMenuDecorator(ActionEvent event) {
		/*
		 * If the decorator bundle is stated -> stop it otherwise start it (if
		 * it has been loaded before)
		 */
		String decoratorBundleName = String.format("m2dl.osgi.colorator");
		toggleBundleState(decoratorBundleName);
	}

	@FXML
	void fireRadioMenuJava(ActionEvent event) {
		/*
		 * If the Java bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
		String javaTokenizerBundleName = String.format("m2dl.osgi.tokenizer.%s", "java");
		toggleBundleState(javaTokenizerBundleName);
	}

	@FXML
	void initialize() {
		assert radioMenuJava != null : "fx:id=\"radioMenuJava\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert radioMenuDecorator != null : "fx:id=\"radioMenuDecorator\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert webViewer != null : "fx:id=\"webViewer\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert radioMenuCSS != null : "fx:id=\"radioMenuCSS\" was not injected: check your FXML file 'main-window-exercice.fxml'.";

	}

	public void setPrimaryStage(final Stage _stage) {
		primaryStage = _stage;
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
	}

	
	//
	//
	//   PRIVATE
	//
	//
	
	private void toggleBundleState(String tokenizerBundleName) {
		boolean bundleHasBeenLoaded = this.loadedBundle.containsKey(tokenizerBundleName);
		
		if (bundleHasBeenLoaded == false) {
			throw new RuntimeException("Vous devez charger le bundle " + tokenizerBundleName + " avant de vouloir le charger");
		}
		
		Bundle tokenizerBundle = this.loadedBundle.get(tokenizerBundleName);
		if (tokenizerBundle.getState() == Bundle.ACTIVE || tokenizerBundle.getState() == Bundle.STARTING) {
			try {
				tokenizerBundle.stop();
				logger.info("Le bundle {} a été stoppé", tokenizerBundleName);
			} catch (BundleException e) {
				throw new RuntimeException("Impossible d'arrêter le bundle " + tokenizerBundleName, e);
			}
		} else if (tokenizerBundle.getState() == Bundle.INSTALLED || tokenizerBundle.getState() == Bundle.RESOLVED) {
			try {
				tokenizerBundle.start();
				logger.info("Le bundle {} a été démarré", tokenizerBundleName);
			} catch (BundleException e) {
				throw new RuntimeException("Impossible de démarrer le bundle " + tokenizerBundleName, e);
			}
		}
	}
	
	/**
	 * 
	 * @param fileToProcess
	 */
	private void processFileThrgouhtColorationPipeline(File fileToProcess) {
		WebEngine webEngine = webViewer.getEngine();
		String fileContent;
		try {
			logger.info("Passage du fichier {} par le pipeline de coloration", fileToProcess.getAbsolutePath());
			fileContent = new String(Files.readAllBytes(fileToProcess.toPath()), "UTF-8");
			
			if (this.tokenizerServices.size() > 0) {
				Tokenizer tokenizer = this.tokenizerServices.values().iterator().next();
				logger.info("STEP 1 - Envoi au service Tokenizer [type = {}]...", tokenizer.getType());
				String tokenizedFileContent = tokenizer.tokenize(fileContent);
				
				if (this.colorizerService != null) {
					Colorizer colorizer = this.colorizerService;
					logger.info("STEP 2 - Envoi au service Colorizer ...");
					String colorizedFileContentAsHTML = colorizer.colorize(tokenizedFileContent);
					webEngine.loadContent(colorizedFileContentAsHTML, "text/html");
					
				} else {
					logger.info("STEP 1 - Aucun colorateur de chargé. Affiche du fichier en version parsée sans couleurs...");
					webEngine.loadContent(tokenizedFileContent, "text/plain");
				}
				
			} else {
				logger.info("Aucun bundle n'a été chargé, chargement du fichier en plain/text", fileToProcess.getAbsolutePath());
				webEngine.loadContent(fileContent, "text/plain");
			}
			 
			
		} catch (IOException e) {
			throw new RuntimeException("Impossible de lire le fichier: " + fileToProcess.getAbsolutePath(), e);
		}
		
	}
}
