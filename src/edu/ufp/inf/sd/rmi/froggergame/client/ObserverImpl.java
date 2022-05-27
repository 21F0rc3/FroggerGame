package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI, Component {
    private FroggerGameRI game;
    private GameState state;

    protected ObserverImpl() throws RemoteException {
        super();
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
