package edu.ufp.inf.sd.rmi.froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {
    public void createGame(String serverName, Integer difficulty) throws RemoteException;

    public void getActiveGames() throws RemoteException;
}
