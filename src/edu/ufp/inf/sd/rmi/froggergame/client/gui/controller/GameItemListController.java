package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class GameItemListController {
    @FXML private Text gameName;
    @FXML private Button joinGame;

    private FroggerGameRI froggerGameRI;

    public Text getGameName() {
        return gameName;
    }

    public void setGameName(String name) {
        gameName.setText(name);
    }

    public Button getJoinGame() {
        return joinGame;
    }

    public void setJoinGame(Button joinGame) {
        this.joinGame = joinGame;
    }

    public FroggerGameRI getFroggerGameRI() {
        return froggerGameRI;
    }

    public void setFroggerGameRI(FroggerGameRI froggerGameRI) {
        this.froggerGameRI = froggerGameRI;
    }

    /**
     * Entra num jogo
     *
     * Define o froggergame e abre uma nova scene que representa o lobby do jogo
     *
     *
     * @author Gabriel Fernandes
     */
    public void joinGame() throws IOException {
        GUI.interfacesMediator.registerComponent((Component) froggerGameRI);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameLobby.fxml"));
        Parent parent = loader.load();

        ((GameLobbyController)loader.getController()).title.setText(GUI.interfacesMediator.getFroggerGameRI().getServerInfo()[0]);
        ((GameLobbyController)loader.getController()).playerCounter.setText(GUI.interfacesMediator.getFroggerGameRI().getServerInfo()[2]);

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
