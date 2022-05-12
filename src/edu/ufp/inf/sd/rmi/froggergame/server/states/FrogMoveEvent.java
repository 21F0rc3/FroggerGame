package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;

import java.io.Serializable;
import java.rmi.RemoteException;

public class FrogMoveEvent extends GameState implements Serializable {
    private Integer frogID;
    private String direction;

    public FrogMoveEvent(Integer frogID, String direction) {
        super();
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
}
