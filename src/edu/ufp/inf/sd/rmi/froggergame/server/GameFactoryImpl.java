package edu.ufp.inf.sd.rmi.froggergame.server;


import edu.ufp.inf.sd.rmi.froggergame.server.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.JwtUtil;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    private Database db;
    private JwtUtil jwtUtil;

    public GameFactoryImpl() throws RemoteException {
        super();
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
     * @return GameSession por onde o utilizador pode ver ou criar novos jogos FroggerGame se ele exister, caso contrario retorna null
     *
     * @author Gabriel Fernandes    29/03/2022
     */
    @Override
    public GameSessionRI login(String email, String password) throws RemoteException {
        User user = new User(email, password);

        if(db.exists(user)) {
            GameSessionRI session = new GameSessionImpl();

            return session;
        }
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
