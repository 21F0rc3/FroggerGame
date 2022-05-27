package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private int GameScore;
    private int levelTimer;
    private int GameLevel;
    private ArrayList<Integer> FrogsLives;
    private int playersNumber;
    private String serverName;

    public GameState(ArrayList<Integer> frogsLives, int GameScore, int levelTimer, int GameLevel, int playersNumber, String serverName) {
        this.FrogsLives = frogsLives;
        this.GameScore = GameScore;
        this.levelTimer = levelTimer;
        this.GameLevel = GameLevel;
        this.playersNumber = playersNumber;
        this.serverName = serverName;
    }

    public void execute() {
        // Se o cliente ainda não começou a sua instancia...
        if(!ClientMediator.getInstance().getGameStateHandler().isGameStarted()) {
            /* Se o servidor tiver mais de dois jogadores "READY"
             *  enão o jogo começa */
             if (playersNumber >= 2) {
                 ClientMediator.getInstance().getGameStateHandler().start();
                 ClientMediator.getInstance().getGameStateHandler().setGameStarted(true);
             }
        }
    }

    public int getGameScore() {
        return GameScore;
    }

    public int getLevelTimer() {
        return levelTimer;
    }

    public int getGameLevel() {
        return GameLevel;
    }

    public ArrayList<Integer> getFrogsLives() { return FrogsLives; }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public String getServerName() {
        return serverName;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public String toString() {
        return "GameState,"+
                FrogsLives.get(0)+","+
                FrogsLives.get(1)+","+
                FrogsLives.get(2)+","+
                FrogsLives.get(3)+","+
                GameScore +","+
                levelTimer+","+
                GameLevel+","+
                playersNumber+","+
                serverName;
    }
}
