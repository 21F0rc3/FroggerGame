package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.server.states.TrafficMoveEvent;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;
import jig.engine.util.Vector2D;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    public void setGame(FroggerGameRI game) throws RemoteException;

    public void update(GameState gameState) throws RemoteException;

    public void move(Integer playerIndex, String direction) throws RemoteException;

    public void movingTraffic(TrafficMoveEvent event) throws RemoteException;

    public void setFrogIndex(Integer index) throws RemoteException;
}
