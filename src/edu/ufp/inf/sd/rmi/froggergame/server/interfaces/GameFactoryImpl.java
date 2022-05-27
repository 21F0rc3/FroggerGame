package edu.ufp.inf.sd.rmi.froggergame.server.interfaces;


import edu.ufp.inf.sd.rmi.froggergame.server.ServerMediator;
import edu.ufp.inf.sd.rmi.froggergame.server.ServerSystemImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.data.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.JwtUtil;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI, Component {
    // Lista com todos os jogos ativos
    public ArrayList<FroggerGameRI> froggerGames;

    public GameFactoryImpl() throws RemoteException {
        super();
        froggerGames = new ArrayList<>();
    }

    /**
     * Metodo responsavel por fazer o login
     *
     * @param email - E-mail do utilizador
     * @param password - Password do utilizador
     *
     * @return GameSession por onde o utilizador pode ver ou criar novos jogos FroggerGame se ele exister, caso contrario retorna null
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public GameSessionRI login(String email, String password) throws RemoteException {
        User user = new User(email, password);

        if(ServerMediator.getInstance().getServerSystemRI().exists(user)) {
            // Cria uma token
            String token = JwtUtil.generateToken(user);
            //System.out.println(token);

            // Cria uma nova sessão e coloca a token
            GameSessionImpl gameSession = new GameSessionImpl(token, user);

            System.out.println(TerminalColors.ANSI_PURPLE+"Estas logado!"+TerminalColors.ANSI_RESET);

            return gameSession;
        }
        System.out.println(TerminalColors.ANSI_RED+"Credenciais erradas!"+TerminalColors.ANSI_RESET);
        return null;
    }

    /**
     * Metodo responsavel por registar um novo utilizador
     *
     * @param email - E-mail do novo utilizador
     * @param password - Password do novo utilizador
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public void register(String email, String password) throws RemoteException {
        User user = new User(email, password);
        ServerMediator.getInstance().getServerSystemRI().create(user);
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
        return "GameFactory";
    }
}
