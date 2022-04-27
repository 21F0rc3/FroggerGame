package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;
import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Frogger;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FroggerGameRI extends Remote {
    public void attachGame(ObserverRI observerRI) throws RemoteException;

    public void dettachGame(ObserverRI observerRI) throws RemoteException;

    public GameState getGameState() throws RemoteException;

    public void setGameState() throws RemoteException;

    public void updateGameState() throws RemoteException;

    public String[] getServerInfo() throws RemoteException;
}
