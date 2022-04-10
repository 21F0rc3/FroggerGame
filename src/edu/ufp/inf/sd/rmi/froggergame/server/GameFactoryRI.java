package edu.ufp.inf.sd.rmi.froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
    public GameSessionRI login(String email, String password) throws RemoteException;

    public void register(String email, String password) throws RemoteException;
}
