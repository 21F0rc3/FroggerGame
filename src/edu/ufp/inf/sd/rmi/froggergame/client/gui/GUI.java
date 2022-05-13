package edu.ufp.inf.sd.rmi.froggergame.client.gui;

import edu.ufp.inf.sd.rmi.froggergame.client.FroggerClient;
import edu.ufp.inf.sd.rmi.froggergame.client.Mediator;
import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class GUI extends Application {
    public static Stage context;

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
