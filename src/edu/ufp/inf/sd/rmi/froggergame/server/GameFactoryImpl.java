package edu.ufp.inf.sd.rmi.froggergame.server;


import edu.ufp.inf.sd.rmi.froggergame.server.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.JwtUtil;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    private static GameFactoryImpl instance;

    /**
     * Singleton
     *
     * @return intance de GameSessionImpl
     * @throws RemoteException
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public static GameFactoryImpl getInstance() throws RemoteException {
        if(instance == null) {
            instance = new GameFactoryImpl();
        }
        return instance;
    }

    // Lista com todos os jogos ativos
    public ArrayList<FroggerGameRI> froggerGames;
    private Database db;

    private GameFactoryImpl() throws RemoteException {
        super();
        instance = this;
        db = new Database();
        froggerGames = new ArrayList<>();
    }

    /**
     * Metodo responsavel por fazer o login
     *
     * @param email - E-mail do utilizador
     * @param password - Password do utilizador
     *
     * @throws RemoteException
     *
     * @return GameSession por onde o utilizador pode ver ou criar novos jogos FroggerGame se ele exister, caso contrario retorna null
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public GameSessionRI login(String email, String password) throws RemoteException {
        User user = new User(email, password);

        if(db.exists(user)) {
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
        db.create(user);
    }
}
