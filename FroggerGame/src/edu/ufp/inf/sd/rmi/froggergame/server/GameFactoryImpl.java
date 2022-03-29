package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    public HashMap<String, GameSessionRI> sessions;

    public Database db;

    public GameFactoryImpl() throws RemoteException {
        super();
        sessions = new HashMap<>();
        db = new Database();
    }

    @Override
    public void login(String username, String password) throws RemoteException {

    }

    @Override
    public void register(String username, String password) throws RemoteException {
        User user = new User(username, password);
        db.create(user);
    }
}
