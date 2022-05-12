package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;
import jig.engine.util.Vector2D;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FroggerGameRI extends Remote {

    public void attachGame(ObserverRI observerRI) throws RemoteException;

    public void dettachGame(ObserverRI observerRI) throws RemoteException;

    public void setGameState(GameState gameState) throws RemoteException;

    public void updateGameState() throws RemoteException;

    public String[] getServerInfo() throws RemoteException;
}
