/*package edu.ufp.inf.sd.rmi.froggergame.server.data.models;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;



public class Game implements Serializable {
    private String serverName;
    private Integer difficulty;

    private ArrayList<ObserverRI> players;

    private GameState gameState;

    public Game(String serverName, Integer difficulty, ArrayList<ObserverRI> players, GameState gameState) {
        this.serverName = serverName;
        this.difficulty = difficulty;
        this.players = players;
        this.gameState = gameState;
    }

    public Game(FroggerGameImpl froggerGame) {
        try {
            this.serverName = froggerGame.getServerInfo()[0];
            this.difficulty = Integer.parseInt(froggerGame.getServerInfo()[1]);
            this.players = froggerGame.getPlayers();
            this.gameState = getGameState();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getServerName() {
        return serverName;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public ArrayList<ObserverRI> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }
}
*/