package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI, Component {
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
                 ClientMediator.getInstance().getGameStateHandler().start();
                 gameStarted = true;
             }
        }

        // Executa a ação do evento
        // Por exemplo um FrogMoveEvent faz uma frog mover, um TrafficMoveEvent faz o trafico mover
        state.execute();

        this.state = state;
    }

    public void setGameStateHandler(GameStateHandler game) throws RemoteException {
        ClientMediator.getInstance().registerComponent(game);
    }

    @Override
    public String getName() throws RemoteException {
        return "ObserverRI";
    }
}
