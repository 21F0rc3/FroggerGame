package edu.ufp.inf.sd.rmi.froggergame.client.gui;

import edu.ufp.inf.sd.rmi.froggergame.client.FroggerClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    public static Stage context;
    public static FroggerClient froggerClient;
    public static InterfacesMediator interfacesMediator;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Auth.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        context = primaryStage;

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
