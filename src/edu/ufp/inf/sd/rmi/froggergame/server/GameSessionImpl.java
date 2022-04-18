package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    private static GameSessionImpl instance;

    /**
     * Singleton
     *
     * @return intance de GameSessionImpl
     * @throws RemoteException
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public static GameSessionRI getInstance() throws RemoteException {
        if(instance == null) {
            instance = new GameSessionImpl();
        }
        return instance;
    }

    // Lista com todos os jogos ativos
    private ArrayList<FroggerGameRI> froggerGames;

    private GameSessionImpl() throws RemoteException {
        super();
        froggerGames = new ArrayList<>();
    }

    @Override
    /**
     * Cria um novo jogo FroggerGame e adiciona a lista de jogos ativos
     *
     * @param serverName - Nome do/da servidor/sala
     * @param difficulty - Dificuldade inicial do jogo deste servidor
     *
     * @author Gabriel Fernandes 11/04/2022
     */
    public void createGame(String serverName, Integer difficulty) throws RemoteException {
        /*if(!jwtUtil.validateToken(jwtToken, user)) {
            System.out.println(TerminalColors.ANSI_RED+"[ERROR]"+TerminalColors.ANSI_RESET+" Jwt token is not valid. Please authenticate again");
            return;
        }*/

        FroggerGameRI froggerGame = new FroggerGameImpl(serverName, difficulty);
        froggerGames.add(froggerGame);

        System.out.println(TerminalColors.ANSI_GREEN+"[CREATED]"+TerminalColors.ANSI_RESET+" New FroggerGame name:"+serverName+" difficulty:"+difficulty+".");
    }

    @Override
    /**
     * Retorna a lista de servidores ativos
     *
     * @return Retorna o arraylist edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl#froggerGames
     */
    public ArrayList<FroggerGameRI> getActiveGames() throws RemoteException {
        return froggerGames;
    }
}
