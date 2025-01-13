package com.michal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import atlantafx.base.theme.Dracula;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;
    private static ICommunication communication;

    @Override
    public void start(Stage stage) throws IOException {
        communication = new SocketCommunication();
        scene = new Scene(loadFXML("secondary"), 600, 600);
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static ICommunication getCommunication() {
        return communication;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
