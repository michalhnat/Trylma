package com.michal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import atlantafx.base.theme.Dracula;

/**
 * JavaFX Application main class that handles the initialization and management of the application
 * window. This class extends JavaFX Application and provides core functionality for scene
 * management, communication handling, and FXML loading.
 * 
 * @author Michal
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;
    private static ICommunication communication;
    private static GeneralListener generalListener;

    /**
     * Initializes and starts the JavaFX application. Sets up the primary scene, applies the Dracula
     * theme, and configures the main window.
     *
     * @param stage The primary stage for this application
     * @throws IOException If the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        communication = new SocketCommunication();
        scene = new Scene(loadFXML("primary"), 1000, 800);
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> Platform.exit());
    }

    /**
     * Changes the root of the scene to a different FXML layout.
     *
     * @param fxml The name of the FXML file to load (without .fxml extension)
     * @throws IOException If the FXML file cannot be loaded
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Returns the current communication interface instance.
     *
     * @return The ICommunication instance used for network communication
     */
    static ICommunication getCommunication() {
        return communication;
    }

    /**
     * Sets the general listener for the application.
     *
     * @param generalListener The GeneralListener instance to be set
     */
    static void setGeneralListener(GeneralListener generalListener) {
        App.generalListener = generalListener;
    }

    /**
     * Returns the current general listener instance.
     *
     * @return The current GeneralListener instance
     */
    static GeneralListener getGeneralListener() {
        return generalListener;
    }

    /**
     * Loads an FXML file and returns its root element.
     *
     * @param fxml The name of the FXML file to load (without .fxml extension)
     * @return The root Parent node of the loaded FXML
     * @throws IOException If the FXML file cannot be loaded
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * The main entry point for the JavaFX application. Launches the application and calls the
     * start() method.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch();
    }
}
