package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Array das sessões ativas
    public HashMap<String, GameSessionRI> sessions;

    public Database db;

    public GameFactoryImpl() throws RemoteException {
        super();
        sessions = new HashMap<>();
        db = new Database();
    }

    /**
     * Metodo responsavel por fazer o login
     *
     * @param username - Username do utilizador
     * @param password - Password do utilizador
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public void login(String username, String password) throws RemoteException {
        User user = new User(username, password);

        if(db.exists(user)) {
            System.out.println(ANSI_CYAN+"Estas logado!"+ANSI_RESET);
        }

        System.out.println(ANSI_PURPLE+"Nome de utilizador ou password errados."+ANSI_RESET);
    }

    /**
     * Metodo responsavel por registar um novo utilizador
     *
     * @param username - Username do novo utilizador
     * @param password - Password do novo utilizador
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public void register(String username, String password) throws RemoteException {
        User user = new User(username, password);
        db.create(user);
    }
}
