package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class CreateGameMenuController {

    @FXML private TextField nameField;
    @FXML private TextField difficultyField;

    public void createNewGameHandler() throws RemoteException {
        GameSessionPanelController.gameSessionRI.createGame(nameField.getText(), Integer.parseInt(difficultyField.getText()));


    }
}
