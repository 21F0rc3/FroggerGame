package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
    public void login(String email, String password) throws RemoteException;

    public void register(String email, String password) throws RemoteException;
}
