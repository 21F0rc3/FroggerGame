package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Frogger;
import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI {
    public String serverName;
    public Integer difficulty;

    public FroggerGameImpl(String serverName, Integer difficulty) throws RemoteException {
        super();
        this.serverName = serverName;
        this.difficulty = difficulty;
    }

    @Override
    public void attachGame() throws RemoteException {
    }

    @Override
    public void setGameState() throws RemoteException {

    }
}
