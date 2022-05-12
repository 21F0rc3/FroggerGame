package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private int GameScore;
    private int levelTimer;
    private int GameLevel;

    public GameState(int GameScore, int levelTimer, int GameLevel) {
        this.GameScore = GameScore;
        this.levelTimer = levelTimer;
        this.GameLevel = GameLevel;
    }

    public void execute(ObserverRI observerRI) {

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
}
