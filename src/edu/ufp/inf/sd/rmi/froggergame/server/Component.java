package edu.ufp.inf.sd.rmi.froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component extends Remote {
    String getName() throws RemoteException;
}
