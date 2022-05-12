package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;

import java.io.Serializable;
import java.rmi.RemoteException;

public class FrogMoveEvent extends GameState implements Serializable {
    private Integer frogID;
    private String direction;

    public FrogMoveEvent(int GameScore, int levelTimer, int GameLevel, Integer frogID, String direction) {
        super(GameScore, levelTimer, GameLevel);
        this.frogID = frogID;
        this.direction = direction;
    }

    public void execute(ObserverRI observerRI) {
        try {
            observerRI.move(frogID, direction);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "FrogMoveEvent,"+
                getGameScore() +","+
                getLevelTimer()+"," +
                getGameLevel()+","+
                frogID+","+
                direction+",";
    }
}
