package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
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

    /**
     * Responsavel por fazer attach ao froggergame quando o cliente
     * clica no botão de attach
     *
     * @author Gabriel Fernandes
     */
    public void attachHandler() throws RemoteException {
        GUI.interfacesMediator.getFroggerGameRI().attachGame(GUI.froggerClient.observer);
    }

    /**
     * Responsavel por sair do lobby quando o cliente carrega no botão
     * de leave
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    public void leaveHandler() throws IOException {
        GUI.interfacesMediator.getFroggerGameRI().dettachGame(GUI.froggerClient.observer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ActiveGamesPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
