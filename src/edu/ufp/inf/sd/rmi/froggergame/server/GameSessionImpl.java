package edu.ufp.inf.sd.rmi.froggergame.server;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public GameSessionImpl() throws RemoteException {
        super();
    }

    @Override
    public void createGame(String serverName, Integer difficulty) throws RemoteException {
        System.out.println(ANSI_GREEN+"[CREATED]"+ANSI_RESET+" New FroggerGame name:"+serverName+" difficulty:"+difficulty+".");

        FroggerGameRI froggerGame = new FroggerGameImpl();
    }
}
