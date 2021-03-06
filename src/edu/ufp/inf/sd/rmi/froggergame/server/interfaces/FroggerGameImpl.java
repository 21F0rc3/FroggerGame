package edu.ufp.inf.sd.rmi.froggergame.server.interfaces;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.util.RabbitUtils;
import edu.ufp.inf.sd.rmi.froggergame.util.Sync;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI, Component {
    private ArrayList<ObserverRI> players;

    private GameState gameState;

    public FroggerGameImpl(String serverName, Integer difficulty) throws RemoteException {
        super();
        this.players = new ArrayList<ObserverRI>();

        ArrayList<Integer> frogLives = new ArrayList<>();
        for(int i=0; i<4; i++) {
            frogLives.add(5);
        }

        this.gameState = new GameState(frogLives,0, 3200, difficulty, 0, serverName);

        if(Sync.SYNC_METHOD == Sync.RABBITMQ) { // Começa um listener do jogo
            RabbitUtils.initRabbitMQServerListener(gameState.getServerName());
        }
    }

    public FroggerGameImpl(FroggerGameImpl game) throws RemoteException {
        super();

        this.players = game.getPlayers();

        ArrayList<Integer> frogLives = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            frogLives.add(5);
        }

        this.gameState = new GameState(frogLives, 0, 3200, game.gameState.getGameLevel(), game.gameState.getPlayersNumber(), game.gameState.getServerName());
    }

    /**
     * Padrão Observer
     *
     * Um client ao fazer attach a este froggergame será adicioniado a lista de observadores
     * e assim, comecerá a receber notificacoes do jogo
     *
     * @param player Cliente que pretende receber notificações deste froggergame
     *
     * @author Gabriel Fernandes 08/05/20222
     */
    @Override
    public void attachGame(ObserverRI player) throws RemoteException {
        players.add(player);
        player.setGame(this);

        // Notifica outros jogadores que um novo jogador chegou
        updateGameState();
    }

    /**
     * Padrão Observer
     *
     * Um cliente ao fazer dettach a este froggergame será removido da lista de observadores
     * e assim, deixa de receber notificações do jogo
     *
     * @param player Observador que quer deixar de receber notificações do servidor
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void dettachGame(ObserverRI player) throws RemoteException {
        players.remove(player);
    }

    /**
     * Padrão Observer
     *
     * Itera a lista de observadores e notifica todas com o estado atual do jogo
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void updateGameState() throws RemoteException {
        System.out.println(gameState == null ? "STATE NULL" : "STATE NOT NULL");
        for(ObserverRI player : players) {
            System.out.println(player == null ? "PLAYER NULL" : "PLAYER NOT NULL");
            player.update(this.gameState);
        }
    }

    /**
     * Padrão Observer
     *
     * Um cliente utiliza este metodo para definir o seu estado do jogo como o estado
     * atual do servidor
     *
     * Depois é chamada a updataGameState() que notifica todos os outros observadores
     *
     * @param gameState Novo GameState do jogo
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void setGameState(GameState gameState) throws RemoteException {
        this.gameState = gameState;

        updateGameState();
    }

    /**
     * Retorna as informações do servidor
     *
     * @returns 0 - Nome do servidor, 1 - Dificuldade, 2 - Numero de jogadores
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public String[] getServerInfo() throws RemoteException {
        String[] info = new String[3];

        info[0] = gameState.getServerName();
        info[1] = String.valueOf(gameState.getGameLevel());
        info[2] = String.valueOf(gameState.getPlayersNumber());

        return info;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Padrão Mediator
     *
     * Metodo que identifica o nome deste Component
     * Utilizado no InterfacesMediator
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public String getName() throws RemoteException {
        return "FroggerGame";
    }

    public ArrayList<ObserverRI> getPlayers() {
        return players;
    }

    public String toString() {
        return "FroggerGame{" + "ServerName=" + gameState.getServerName() + ", Difficulty=" + gameState.getGameLevel() + "}";
    }

    /**
     * Atualiza o estado do jogo com o numero de jogadores
     *
     * @author Gabriel Fernandes 27/05/2022
     */
    public void setPlayersNumber(int number) {
        this.gameState.setPlayersNumber(number);
    }
}
