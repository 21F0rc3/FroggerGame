package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI, Component {
    private String serverName;
    private Integer difficulty;

    private ArrayList<ObserverRI> players;

    private GameState gameState;

    public FroggerGameImpl(String serverName, Integer difficulty) throws RemoteException {
        super();
        this.serverName = serverName;
        this.difficulty = difficulty;
        this.players = new ArrayList<ObserverRI>();
        this.gameState = new GameState();
    }

    @Override
    public void attachGame(ObserverRI player) throws RemoteException {
        players.add(player);
        player.setGame(this);

        // Notifica outros jogadores que um novo jogador chegou
        updateGameState();
    }

    @Override
    public void dettachGame(ObserverRI player) throws RemoteException {
        players.remove(player);
    }

    @Override
    public void updateGameState() throws RemoteException {
        for(ObserverRI player : players) {
            player.hello();
            player.update(this.gameState);
        }
    }

    @Override
    public GameState getGameState() throws RemoteException {
        return this.gameState;
    }

    @Override
    public void setGameState(GameState gameState) throws RemoteException {
        this.gameState = gameState;
        updateGameState();
    }

    public String[] getServerInfo() throws RemoteException {
        String[] info = new String[3];

        info[0] = serverName;
        info[1] = String.valueOf(difficulty);
        info[2] = String.valueOf(players.size());

        return info;
    }

    @Override
    public String getName() throws RemoteException {
        return "FroggerGame";
    }
}
