package edu.ufp.inf.sd.rmi.froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FroggerGameRI extends Remote {
    public void attachGame() throws RemoteException;

    public void setGameState() throws RemoteException;

    public String getServerName() throws RemoteException;
}
