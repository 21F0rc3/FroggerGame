package edu.ufp.inf.sd.rmi.froggergame.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameFactoryRI extends Remote {
    public GameSessionRI login(String email, String password) throws RemoteException;

    public void register(String email, String password) throws RemoteException;
}
