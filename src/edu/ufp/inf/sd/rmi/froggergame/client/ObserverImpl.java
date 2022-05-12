package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.server.states.TrafficMoveEvent;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;
import jig.engine.util.Vector2D;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private FroggerGameRI game;
    private GameState state;
    private boolean gameStarted;

    private Main gameInstance;

    public Integer playerIndex;

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
        //    if (Integer.parseInt(this.game.getServerInfo()[2]) >= 2) {
                Integer difficulty = Integer.parseInt(this.game.getServerInfo()[1]);

                /* Serve para criar uma nova thread que contem o jogo.
                 * Deste modo, o servidor não bloqueia porque o jogo comecou.
                 * Caso contrario, o segundo cliente não conseguia abrir um jogo. */
                Runnable runnable = () -> {
                    this.gameInstance = new Main(playerIndex);
                    this.gameInstance.run();
                };
                Thread thread = new Thread(runnable);

                thread.start();

                gameStarted = true;

                // Atualiza o server
                //game.setGameState(state);
          //  }
        }else{
            // Executa a ação do evento
            // Por exemplo um FrogMoveEvent faz uma frog mover, um TrafficMoveEvent faz o trafico mover
            state.execute(this);
        }

        this.state = state;
    }

    @Override
    public void move(Integer playerIndex, String direction) throws RemoteException {
        if(gameInstance != null) gameInstance.move(playerIndex, direction);
    }

    @Override
    public void movingTraffic(TrafficMoveEvent event) throws RemoteException {
        // If muito importante !! Acredito que ele comeca a fazer updates antes do jogo comecar então
        // a instancia ainda não esta definida no inicio
        if(gameInstance != null) gameInstance.movingTraffic(event.getTrafficType(), event.getPos(), event.getVel(), event.getSpriteName(), event.getDeltaMs());
    }

    /**
     * Atribui uma frogger a este jogador
     */
    @Override
    public void setFrogIndex(Integer index) {
        playerIndex = index;
    }
}
