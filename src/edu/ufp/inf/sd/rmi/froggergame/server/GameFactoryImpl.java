package edu.ufp.inf.sd.rmi.froggergame.server;


import edu.ufp.inf.sd.rmi.froggergame.server.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.JwtUtil;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    // Array das sessões ativas
    public HashMap<String, GameSessionRI> sessions;

    public Database db;
    private JwtUtil jwtUtil;

    public GameFactoryImpl() throws RemoteException {
        super();
        sessions = new HashMap<>();
        db = new Database();
    }

    /**
     * Metodo responsavel por fazer o login
     *
     * @param email - E-mail do utilizador
     * @param password - Password do utilizador
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public GameSessionRI login(String email, String password) throws RemoteException {
        User user = new User(email, password);

        if(db.exists(user)) {
            GameSessionRI session = new GameSessionImpl(user);

            /**
             * @// TODO: 11/04/2022 Eu não sei o que colocar aqui no hashmap de sessões como key, por enquanto está email
             * @author Gabriel Fernandes
             */
            sessions.put(email, session);

            System.out.println(TerminalColors.ANSI_CYAN+"Estas logado!"+ TerminalColors.ANSI_RESET);
            return session;
        }

        System.out.println(TerminalColors.ANSI_PURPLE+"Email ou password errados."+TerminalColors.ANSI_RESET);
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
