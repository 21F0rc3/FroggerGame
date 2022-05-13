package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerSystemRI extends Remote {
    public void create(Object object) throws RemoteException;

    public boolean exists(Object object) throws RemoteException;

    public ArrayList<FroggerGameRI> queryAllFroggerGames() throws RemoteException;

    public FroggerServer requestServer() throws RemoteException;
}
