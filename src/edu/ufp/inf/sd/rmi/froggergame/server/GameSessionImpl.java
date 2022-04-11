package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Frogger;
import edu.ufp.inf.sd.rmi.froggergame.server.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    private User user;
    private ArrayList<FroggerGameImpl> froggerGames;

    public GameSessionImpl(User user) throws RemoteException {
        super();
        this.user = user;
        froggerGames = new ArrayList<>();
    }

    @Override
    public void createGame(String serverName, Integer difficulty) throws RemoteException {
        /*if(!jwtUtil.validateToken(jwtToken, user)) {
            System.out.println(TerminalColors.ANSI_RED+"[ERROR]"+TerminalColors.ANSI_RESET+" Jwt token is not valid. Please authenticate again");
            return;
        }*/

        System.out.println(TerminalColors.ANSI_GREEN+"[CREATED]"+TerminalColors.ANSI_RESET+" New FroggerGame name:"+serverName+" difficulty:"+difficulty+".");

        FroggerGameImpl froggerGame = new FroggerGameImpl(serverName, difficulty);

        froggerGames.add(froggerGame);
    }

    @Override
    public void getActiveGames() throws RemoteException {
        if(froggerGames.isEmpty()) {
            System.out.println("Não existe nenhum servidor ativo");
            return;
        }

        for(FroggerGameImpl froggerGame : froggerGames) {
            System.out.println(TerminalColors.ANSI_BLACK + "[Servidor] " + TerminalColors.ANSI_RESET + froggerGame.serverName + " (Dificuldade: " + froggerGame.difficulty + ")");
        }
    }
}
