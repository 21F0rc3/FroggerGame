package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ActiveGamesPanelController {
    public VBox vBox;

    /**
     * Popula a o gridPane com o nome de cada um dos jogos ativos.
     * Ele vai buscar a lista de jogos ativos do gameSessionRI(no servidor)
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void populate() throws IOException {
        System.out.println(ClientMediator.getInstance().getGameSessionRI().getActiveGames().size());

        ArrayList<FroggerGameRI> activeGames = ClientMediator.getInstance().getGameSessionRI().getActiveGames();

        if(activeGames.isEmpty()) { // Provavelmente porque a token expirou
            // Redireciona para o menu de autenticação
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Auth.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            GUI.context.setScene(scene);
            GUI.context.show();
        }

        for(FroggerGameRI froggerGameRI : activeGames) {
            try {
                addGameItemToList(froggerGameRI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cria uma nova template de um servidor, para ser listado na lista de jogos ativos
     * no painel.
     *
     * Cria um Text com o nome do servidor e um Botão para dar Join
     *
     * @param game Proxy(FroggerGameRI) do jogo
     *
     * @author Gabriel Fernandes 26/04/2022
     */
    private void addGameItemToList(FroggerGameRI game) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameItemList.fxml"));
        Parent parent = loader.load();

        ((GameItemListController)loader.getController()).setFroggerGameRI(game);
        ((GameItemListController)loader.getController()).setGameName(game.getServerInfo()[0]);

        vBox.getChildren().add(parent);
    }

    /**
     * Resposavel por voltar para atras quando clicamos no botão
     *
     * @author Gabriel Fernandes 26/04/2022
     */
    public void backHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
