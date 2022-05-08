package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameState;

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

    /**
     * Metodo para definir qual o froggergame que este cliente esta a observar
     *
     * @param game FroggerGame que o cliente está a observar
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void setGame(FroggerGameRI game) throws RemoteException {
        this.game = game;
    }

    /**
     * Padrão Observer
     *
     * Atualiza o GameState local com o do servidor
     *
     * @param state GameState do servidor
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void update(GameState state) throws RemoteException {
        if(!gameStarted) {
            /* Se o servidor tiver mais de dois jogadores "READY"
             *  enão o jogo começa */
            if (Integer.parseInt(this.game.getServerInfo()[2]) >= 2) {
                Integer difficulty = Integer.parseInt(this.game.getServerInfo()[1]);

                /* Serve para criar uma nova thread que contem o jogo.
                * Deste modo, o servidor não bloqueia porque o jogo comecou.
                * Caso contrario, o segundo cliente não conseguia abrir um jogo. */
                Runnable runnable = () -> {
                    Main gameInstance = new Main();
                    gameInstance.initializeLevel(difficulty);
                    gameInstance.run();
                };
                Thread thread = new Thread(runnable);
                thread.start();

                gameStarted = true;

                // Atualiza o server
                //game.setGameState(state);
            }
        }

        this.state = state;
    }
}
