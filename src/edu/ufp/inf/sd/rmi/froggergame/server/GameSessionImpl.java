package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    public GameSessionImpl() throws RemoteException {
        super();
    }

    @Override
    public void createGame(String serverName, Integer difficulty) throws RemoteException {
        System.out.println(TerminalColors.ANSI_GREEN+"[CREATED]"+TerminalColors.ANSI_RESET+" New FroggerGame name:"+serverName+" difficulty:"+difficulty+".");

        FroggerGameRI froggerGame = new FroggerGameImpl();
    }
}
