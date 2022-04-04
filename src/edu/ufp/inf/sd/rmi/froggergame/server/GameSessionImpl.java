package edu.ufp.inf.sd.rmi.froggergame.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    public GameSessionImpl() throws RemoteException {
        super();
    }

    @Override
    public void createGame() throws RemoteException {

    }
}
