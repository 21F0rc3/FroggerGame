package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.Mediator;
import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;

import javax.print.attribute.standard.Media;
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

    public void execute() {
        Mediator.getInstance().getGameStateHandler().move(this);
    }

    public Integer getFrogID() {
        return frogID;
    }

    public void setFrogID(Integer frogID) {
        this.frogID = frogID;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
