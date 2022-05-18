package edu.ufp.inf.sd.rmi.froggergame.server.states;

import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;

import java.io.Serializable;
import java.util.ArrayList;

public class TrafficMoveEvent extends GameState implements Serializable {
    private String trafficType;
    private Posititon pos;
    private Posititon vel;
    private String spriteName;
    private long deltaMs;

    public TrafficMoveEvent(ArrayList<Integer> frogsLives, int GameScore, int levelTimer, int GameLevel, String trafficType, Posititon pos, Posititon vel, String spriteName, long deltaMs) {
        super(frogsLives, GameScore, levelTimer, GameLevel);
        this.trafficType = trafficType;
        this.pos = pos;
        this.vel = vel;
        this.spriteName = spriteName;
        this.deltaMs = deltaMs;
    }

    public void execute() {
        ClientMediator.getInstance().getGameStateHandler().movingTraffic(this);
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
