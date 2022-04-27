package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;
import java.rmi.RemoteException;

public class GameLobbyController {
    @FXML public Text title;
    @FXML public Text playerCounter;

    public void attachHandler() throws RemoteException {

            String args[] = new String[3];
            Main.main(args);

        //GUI.interfacesMediator.getFroggerGameRI().attachGame(GUI.froggerClient.observer);
    }

    public void leaveHandler() throws IOException {
        GUI.interfacesMediator.getFroggerGameRI().dettachGame(GUI.froggerClient.observer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ActiveGamesPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
