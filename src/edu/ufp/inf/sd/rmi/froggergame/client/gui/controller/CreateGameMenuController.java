package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameSessionRI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateGameMenuController {

    @FXML private TextField nameField;
    @FXML private TextField difficultyField;

    /**
     * Resposavel por chamar a funçaõ que cria um novo FroggerGame
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void createNewGameHandler() throws IOException {
        FroggerGameRI froggerGameRI = ClientMediator.getInstance().getGameSessionRI().createGame(nameField.getText(), Integer.parseInt(difficultyField.getText()));

        if(froggerGameRI == null) { // Provavelmente porque a token expirou
            // Redireciona para o menu de autenticação

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Auth.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            GUI.context.setScene(scene);
            GUI.context.show();
        }

        ClientMediator.getInstance().registerComponent((Component) froggerGameRI);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameLobby.fxml"));
        Parent parent = loader.load();

        ((GameLobbyController)loader.getController()).title.setText(ClientMediator.getInstance().getFroggerGameRI().getServerInfo()[0]);
        ((GameLobbyController)loader.getController()).playerCounter.setText(ClientMediator.getInstance().getFroggerGameRI().getServerInfo()[2]);

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }

    /**
     * Responsavel por voltar atras ao menu principal
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    public void backHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
