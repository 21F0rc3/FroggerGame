package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.RemoteException;

public class CreateGameMenuController {

    @FXML private TextField nameField;
    @FXML private TextField difficultyField;

    /**
     * Resposavel por chamar a funçaõ que cria um novo FroggerGame
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void createNewGameHandler() throws RemoteException {
        GUI.interfacesMediator.getGameSessionRI().createGame(nameField.getText(), Integer.parseInt(difficultyField.getText()));
    }

    public void backHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
