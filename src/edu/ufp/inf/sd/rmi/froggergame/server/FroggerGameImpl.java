package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI, Component {
    private String serverName;
    private Integer difficulty;

    private ArrayList<ObserverRI> players;

    private GameState gameState;

    public FroggerGameImpl(String serverName, Integer difficulty) throws RemoteException {
        super();
        this.serverName = serverName;
        this.difficulty = difficulty;
        this.players = new ArrayList<ObserverRI>();
        this.gameState = new GameState();
    }

    /**
     * Padrão Observer
     *
     * Um client ao fazer attach a este froggergame será adicioniado a lista de observadores
     * e assim, comecerá a receber notificacoes do jogo
     *
     * @param player Cliente que pretende receber notificações deste froggergame
     *
     * @author Gabriel Fernandes 08/05/20222
     */
    @Override
    public void attachGame(ObserverRI player) throws RemoteException {
        players.add(player);
        player.setGame(this);

        // Notifica outros jogadores que um novo jogador chegou
        updateGameState();
    }

    /**
     * Padrão Observer
     *
     * Um cliente ao fazer dettach a este froggergame será removido da lista de observadores
     * e assim, deixa de receber notificações do jogo
     *
     * @param player Observador que quer deixar de receber notificações do servidor
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void dettachGame(ObserverRI player) throws RemoteException {
        players.remove(player);
    }

    /**
     * Padrão Observer
     *
     * Itera a lista de observadores e notifica todas com o estado atual do jogo
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void updateGameState() throws RemoteException {
        for(ObserverRI player : players) {
            player.update(this.gameState);
        }
    }

    /**
     * Padrão Observer
     *
     * Um cliente utiliza este metodo para definir o seu estado do jogo como o estado
     * atual do servidor
     *
     * Depois é chamada a updataGameState() que notifica todos os outros observadores
     *
     * @param gameState Novo GameState do jogo
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public void setGameState(GameState gameState) throws RemoteException {
        //this.gameState = gameState;
        updateGameState();
    }

    /**
     * Retorna as informações do servidor
     *
     * @returns 0 - Nome do servidor, 1 - Dificuldade, 2 - Numero de jogadores
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public String[] getServerInfo() throws RemoteException {
        String[] info = new String[3];

        info[0] = serverName;
        info[1] = String.valueOf(difficulty);
        info[2] = String.valueOf(players.size());

        return info;
    }

    /**
     * Padrão Mediator
     *
     * Metodo que identifica o nome deste Component
     * Utilizado no InterfacesMediator
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    @Override
    public String getName() throws RemoteException {
        return "FroggerGame";
    }
}
