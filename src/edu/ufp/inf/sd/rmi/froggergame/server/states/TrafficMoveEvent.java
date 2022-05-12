package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;

import java.io.Serializable;
import java.rmi.RemoteException;

public class TrafficMoveEvent extends GameState implements Serializable {
    private String place;
    private String trafficType;
    private Posititon pos;
    private Posititon vel;
    private String spriteName;
    private long deltaMs;

    public TrafficMoveEvent(int GameScore, int levelTimer, int GameLevel, String place, String trafficType, Posititon pos, Posititon vel, String spriteName, long deltaMs) {
        super(GameScore, levelTimer, GameLevel);
        this.trafficType = trafficType;
        this.pos = pos;
        this.vel = vel;
        this.spriteName = spriteName;
        this.deltaMs = deltaMs;
    }

    public void execute(ObserverRI observerRI) {
        try {
            observerRI.movingTraffic(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getPlace() {
        return place;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public Posititon getPos() {
        return pos;
    }

    public Posititon getVel() {
        return vel;
    }

    public long getDeltaMs() {
        return deltaMs;
    }

    public String getSpriteName() {
        return this.spriteName;
    }
}
