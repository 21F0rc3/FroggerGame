package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.rmi.RemoteException;

public class GameLobbyController {
    @FXML private Text title;
    @FXML private Text playerCounter;

    private FroggerGameRI game;

    public GameLobbyController(FroggerGameRI game) throws RemoteException {
        this.game = game;
        this.title.setText(game.getServerInfo()[0]);
        this.playerCounter.setText(game.getServerInfo()[2]);
    }

    public void attachHandler() {
    }

    public void leaveHandler() {

    }
}
