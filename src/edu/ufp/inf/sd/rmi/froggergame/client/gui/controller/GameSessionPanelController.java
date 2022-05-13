package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GameSessionPanelController {
    /**
     * Função responsavel por fazer um switch de cenas
     * para a pagina CreateGameMenu onde é possivel criar
     * um novo froggerGame
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void startNewGameHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreateGameMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }

    /**
     * Função responsavel por fazer um switch de cenas
     * para a pagina ActiveGamePanel onde e possivel
     * consultar os jogos ativos no momento
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void joinHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ActiveGamesPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        ((ActiveGamesPanelController)loader.getController()).populate();

        GUI.context.setScene(scene);
        GUI.context.show();
    }

    public void logoutHandler() {

    }
}
