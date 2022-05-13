package edu.ufp.inf.sd.rmi.froggergame.server.states;

import java.io.Serializable;

public class GameState implements Serializable {
    private int GameScore;
    private int levelTimer;
    private int GameLevel;

    public GameState(int GameScore, int levelTimer, int GameLevel) {
        this.GameScore = GameScore;
        this.levelTimer = levelTimer;
        this.GameLevel = GameLevel;
    }

    public void execute() {
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

    public String toString() {
        return "GameState,"+
                GameScore +","+
                levelTimer+","+
                GameLevel;
    }
}
