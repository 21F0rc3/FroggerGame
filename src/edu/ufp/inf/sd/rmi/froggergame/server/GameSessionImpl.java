package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Frogger;
import edu.ufp.inf.sd.rmi.froggergame.server.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    // Lista com todos os jogos ativos
    private ArrayList<FroggerGameRI> froggerGames;

    public GameSessionImpl() throws RemoteException {
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

        FroggerGameImpl froggerGame = new FroggerGameImpl(serverName, difficulty);
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
