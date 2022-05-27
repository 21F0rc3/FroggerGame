package edu.ufp.inf.sd.rmi.froggergame.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {
    public FroggerGameRI createGame(String serverName, Integer difficulty) throws RemoteException;

    public ArrayList<FroggerGameRI> getActiveGames() throws RemoteException;
}
