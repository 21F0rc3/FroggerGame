package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameState;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private FroggerGameRI game;
    private GameState state;

    protected ObserverImpl() throws RemoteException {
        super();
    }

    @Override
    public void update() throws RemoteException {
        this.state = game.getGameState();

        /* Se o servidor tiver mais de dois jogadores "READY"
         *  enão o jogo começa */
        if(Integer.parseInt(this.game.getServerInfo()[1]) >= 2) {
            String args[] = new String[3];
            Main.main(args);
        }
    }
}
