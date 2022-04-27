package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;

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
    }

    @Override
    public void attachGame(ObserverRI player) throws RemoteException {
        players.add(player);

        /* Se o servidor tiver mais de dois jogadores "READY"
        *  enão notifica que o jogo pode começar */
        updateGameState();
    }

    @Override
    public void dettachGame(ObserverRI player) throws RemoteException {
        players.remove(player);
    }

    @Override
    public void updateGameState() throws RemoteException {
        for(ObserverRI player : players) {
            player.update();
        }
    }

    @Override
    public GameState getGameState() throws RemoteException {
        return this.gameState;
    }

    @Override
    public void setGameState() throws RemoteException {

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
