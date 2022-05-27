package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.server.data.Database;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Primary-Based Remote-Write Protocol
 *
 * Este é o server primario que contem a base de dados
 * Todos os outros servidores falam com este servidor para obter os dados.
 */
public class ServerSystemImpl extends UnicastRemoteObject implements ServerSystemRI, Component {
    private ArrayList<FroggerServer> servers;
    private Database db;

    public ServerSystemImpl() throws RemoteException {
        super();
        servers = new ArrayList<>();
        this.db = new Database();
    }

    @Override
    public void create(Object object) throws RemoteException {
        db.create(object);
    }

    @Override
    public boolean exists(Object object) throws RemoteException {
        return db.exists(object);
    }

    @Override
    public ArrayList<FroggerGameRI> queryAllFroggerGames() throws RemoteException {
        return db.queryAllFroggerGames();
    }

    @Override
    public FroggerServer requestServer() throws RemoteException {
        for(FroggerServer froggerServer : servers) {
            if(froggerServer != null) {
                return froggerServer;
            }
        }

        return null;
    }

    @Override
    public String getName() throws RemoteException {
        return "ServerSystem";
    }
}
