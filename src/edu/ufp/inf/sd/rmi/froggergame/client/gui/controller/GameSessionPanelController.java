package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.Index;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionRI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSessionPanelController {
    public static GameSessionRI gameSessionRI;

    public void startNewGameHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreateGameMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        Index.context.setScene(scene);
        Index.context.show();
    }

    public void joinHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ActiveGamesPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        ((ActiveGamesPanelController)loader.getController()).populate();

        Index.context.setScene(scene);
        Index.context.show();
    }

    public void logoutHandler() {

    }
}
