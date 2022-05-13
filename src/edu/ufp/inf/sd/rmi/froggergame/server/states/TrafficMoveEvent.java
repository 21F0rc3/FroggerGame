package edu.ufp.inf.sd.rmi.froggergame.server.states;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.ufp.inf.sd.rmi.froggergame.client.Mediator;
import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.Serializable;
import java.rmi.RemoteException;

public class TrafficMoveEvent extends GameState implements Serializable {
    private String trafficType;
    private Posititon pos;
    private Posititon vel;
    private String spriteName;
    private long deltaMs;

    public TrafficMoveEvent(int GameScore, int levelTimer, int GameLevel, String trafficType, Posititon pos, Posititon vel, String spriteName, long deltaMs) {
        super(GameScore, levelTimer, GameLevel);
        this.trafficType = trafficType;
        this.pos = pos;
        this.vel = vel;
        this.spriteName = spriteName;
        this.deltaMs = deltaMs;
    }

    public void execute() {
        Mediator.getInstance().getGameStateHandler().movingTraffic(this);
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

    public String toString() {
        return "TrafficMoveEvent,"+
                getGameScore()+","+
                getLevelTimer()+"," +
                getGameLevel()+","+
                trafficType+","+
                pos.toString()+","+
                vel.toString()+","+
                spriteName+","+
                +deltaMs;
    }
}
