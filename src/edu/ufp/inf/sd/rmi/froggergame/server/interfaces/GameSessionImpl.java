package edu.ufp.inf.sd.rmi.froggergame.server.interfaces;

import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.server.ServerMediator;
import edu.ufp.inf.sd.rmi.froggergame.server.ServerSystemImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.data.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.JwtUtil;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerCloneException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI, Component {
    private String jwt_token;
    private User user;

    public GameSessionImpl(String token, User user) throws RemoteException {
        super();
        this.jwt_token = token;
        this.user = user;
    }

    /**
     * Cria um novo jogo FroggerGame e adiciona a lista de jogos ativos
     *
     * @param serverName - Nome do/da servidor/sala
     * @param difficulty - Dificuldade inicial do jogo deste servidor
     *
     * @author Gabriel Fernandes 11/04/2022
     */
    @Override
    public FroggerGameRI createGame(String serverName, Integer difficulty) throws RemoteException {
        // Valida a token
        if(Boolean.FALSE.equals(JwtUtil.validateToken(jwt_token, user))) {
            return null;
        }

        FroggerGameRI froggerGame = new FroggerGameImpl(serverName, difficulty);

        ((GameFactoryImpl) ServerMediator.getInstance().getGameFactoryRI()).froggerGames.add(froggerGame);
        //ServerMediator.getInstance().getServerSystemRI().create(froggerGame);

        return froggerGame;
    }

    /**
     * Retorna a lista de servidores ativos
     *
     * @return Retorna o arraylist edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl#froggerGames
     */
    @Override
    public ArrayList<FroggerGameRI> getActiveGames() throws RemoteException {
        // Valida a token
        if(Boolean.FALSE.equals(JwtUtil.validateToken(jwt_token, user))) {
            return null;
        }

        return ((GameFactoryImpl)ServerMediator.getInstance().getGameFactoryRI()).froggerGames;
        //return ServerMediator.getInstance().getServerSystemRI().queryAllFroggerGames();
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
        return "GameSession";
    }
}
