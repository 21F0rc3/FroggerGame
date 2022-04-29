package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    public void setGame(FroggerGameRI game) throws RemoteException;

    public void update(GameState gameState) throws RemoteException;

    public void hello() throws RemoteException;
}
