package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private int GameScore;
    private int levelTimer;
    private int GameLevel;
    private ArrayList<Integer> FrogsLives;

    public GameState(ArrayList<Integer> frogsLives, int GameScore, int levelTimer, int GameLevel) {
        this.FrogsLives = frogsLives;
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

    public ArrayList<Integer> getFrogsLives() { return FrogsLives; }

    public String toString() {
        return "GameState,"+
                FrogsLives.get(0)+","+
                FrogsLives.get(1)+","+
                FrogsLives.get(2)+","+
                FrogsLives.get(3)+","+
                GameScore +","+
                levelTimer+","+
                GameLevel;
    }
}
