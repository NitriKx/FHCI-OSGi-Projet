package m2dl.osgi.editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

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
import m2dl.osgi.editor.interfaces.Tokenizer;
import m2dl.osgi.editor.interfaces.Tokenizer.Type;

public class CodeViewerController {

	private Map<String, Bundle> loadedBundle = new LinkedHashMap<>(Type.values().length);
	
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
				Bundle freshInstalledBundle = Activator.context.installBundle(selectedFile.getAbsolutePath());
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
			WebEngine webEngine = webViewer.getEngine();
			String fileContent;
			try {
				fileContent = new String(Files.readAllBytes(selectedFile.toPath()), "UTF-8");
			} catch (IOException e) {
				throw new RuntimeException("Impossible de lire le fichier: " + selectedFile.getAbsolutePath(), e);
			}
			webEngine.loadContent(fileContent, "text/plain");
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
		String tokenizerBundleName = String.format("m2dl.osgi.tokenizer.%s", "css");
		boolean bundleHasBeenLoaded = this.loadedBundle.containsKey(tokenizerBundleName);
		
		if (bundleHasBeenLoaded == false) {
			throw new RuntimeException("Vous devez charger le bundle " + tokenizerBundleName + " avant de vouloir le charger");
		}
		
		Bundle cssTokenizerBundle = this.loadedBundle.get(tokenizerBundleName);
		if (cssTokenizerBundle.getState() == Bundle.ACTIVE) {
			try {
				cssTokenizerBundle.stop();
			} catch (BundleException e) {
				throw new RuntimeException("Impossible d'arrêter le bundle " + tokenizerBundleName, e);
			}
		} else if (cssTokenizerBundle.getState() == Bundle.RESOLVED) {
			try {
				cssTokenizerBundle.start();
			} catch (BundleException e) {
				throw new RuntimeException("Impossible de démarrer le bundle " + tokenizerBundleName, e);
			}
		}
	}

	@FXML
	void fireRadioMenuDecorator(ActionEvent event) {
		/*
		 * If the decorator bundle is stated -> stop it otherwise start it (if
		 * it has been loaded before)
		 */
	}

	@FXML
	void fireRadioMenuJava(ActionEvent event) {
		/*
		 * If the Java bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
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

}
