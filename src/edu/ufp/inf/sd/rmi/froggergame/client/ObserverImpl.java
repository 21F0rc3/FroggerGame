package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Frogger;
import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameState;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;
import jig.engine.util.Vector2D;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private FroggerGameRI game;
    private GameState state;
    private boolean gameStarted;

    protected ObserverImpl() throws RemoteException {
        super();
        gameStarted = false;
    }

    @Override
    public void setGame(FroggerGameRI game) throws RemoteException {
        this.game = game;
    }

    @Override
    public void update(GameState state) throws RemoteException {
        if(!gameStarted) {
            /* Se o servidor tiver mais de dois jogadores "READY"
             *  enão o jogo começa */
            if (Integer.parseInt(this.game.getServerInfo()[2]) >= 2) {
                Runnable runnable =
                        () -> { Main gameInstance = new Main();
                            gameInstance.run(); };
                Thread thread = new Thread(runnable);
                thread.start();

                //gameInstance.initializeLevel(Integer.parseInt(this.game.getServerInfo()[1]));
                gameStarted = true;
                // Atualiza o server
                //game.setGameState(state);
            }
        }

        this.state = state;
    }

    @Override
    public void hello() throws RemoteException {
        System.out.println("Hello");
    }


}
