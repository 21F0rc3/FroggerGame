package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.FroggerClient;
import edu.ufp.inf.sd.rmi.froggergame.client.GameStateHandler;
import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
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
        // Prepara a instancia do jogo
        GameStateHandler game = new GameStateHandler();
        ClientMediator.getInstance().registerComponent(game);

        // Atualiza o numero de jogadores do servidor
        ClientMediator.getInstance().getFroggerGameRI().setPlayersNumber(Integer.parseInt(ClientMediator.getInstance().getFroggerGameRI().getServerInfo()[2]) + 1);

        if(FroggerClient.SYNC_METHOD == "RabbitMQ") { // Começa um ouvinte no servidor
            FroggerClient.initRabbitMQListener();

            // Anuncia a todos que um novo jogador chegou
            ClientMediator.getInstance().getGameStateHandler().sendGameState(ClientMediator.getInstance().getFroggerGameRI().getGameState());

            // Executa o game state do servidor(para o caso de o proprio jogador ser 0 2º a entrar na partida tbm comecar)
            ClientMediator.getInstance().getFroggerGameRI().getGameState().execute();
        }else {
            // Efetua o attach
            System.out.println(ClientMediator.getInstance().getObserverRI() == null ? "OBSERVER NULL" : "OBSERVER NOT NULL");
            ClientMediator.getInstance().getFroggerGameRI().attachGame(ClientMediator.getInstance().getObserverRI());
        }
    }

    /**
     * Responsavel por sair do lobby quando o cliente carrega no botão
     * de leave
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    public void leaveHandler() throws IOException {
        // Atualiza o numero de jogadores do servidor
        ClientMediator.getInstance().getFroggerGameRI().setPlayersNumber(Integer.parseInt(ClientMediator.getInstance().getFroggerGameRI().getServerInfo()[2]) - 1);

        ClientMediator.getInstance().getFroggerGameRI().dettachGame(ClientMediator.getInstance().getObserverRI());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ActiveGamesPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}
