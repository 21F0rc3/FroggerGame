package edu.ufp.inf.sd.rmi.froggergame.server.data;

import edu.ufp.inf.sd.rmi.froggergame.server.data.models.User;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.util.TerminalColors;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public class Database {
    /**
     * Array com todos os users registrados na database
     * PS.: Isto é so para testar o login não sei se vai ser assim que vamos implementar a base de dados, podemos usar ficheiros ou sql msm
     * @author Gabriel  29/03/2022
     */
    private final ArrayList<User> users;// = new ArrayList();

    // Lista com todos os jogos ativos
    private ArrayList<FroggerGameRI> froggerGames;

    public Database() {
        users = new ArrayList();
        froggerGames = new ArrayList<>();
    }

    /**
     * Cria/Insere na base de dados um model na sua respetiva tabela.
     * Nesta função ele so chama as funções responsaveis por inserir determinado TIPO de objeto
     *
     * @param object - Este objeto pode ser de qualquer tipo, neste metodo e distinguido e depois sera resolvido no respetivo metodo responsavel pelo seu tipo
     *
     * @author Gabriel  29/03/2022
     */
    public void create(Object object) {
        switch(object.getClass().getSimpleName()) {
            case "User": {
                createUser((User)object);
                break;
            }
            case "Game": {
                createFroggerGame((FroggerGameImpl) object);
                break;
            }
            default: {
                System.out.println(TerminalColors.ANSI_RED+"[ERROR] "+ TerminalColors.ANSI_RESET+"Class "+object.getClass().getSimpleName()+" is not recognized in Database.create()!");
            }
        }
    }

    /**
     * Insere um User no base de dados
     *
     * @param user - User que vai ser adicionado
     *
     * @author Gabriel  29/03/2022
     */
    private void createUser(User user) {
        users.add(user);
        System.out.println(TerminalColors.ANSI_GREEN+"[CREATED] "+TerminalColors.ANSI_RESET+"New User "+user.toString()+" has been added to Database.");
    }

    /**
     * Update propagation pull-based
     */
    private void createFroggerGame(FroggerGameImpl froggerGameRI) {
        try {
            FroggerGameRI froggerGame = new FroggerGameImpl(froggerGameRI);

            froggerGames.add(froggerGame);
            System.out.println(TerminalColors.ANSI_GREEN+"[CREATED] "+TerminalColors.ANSI_RESET+"New FroggerGame "+froggerGame.toString()+" has been added to Database.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se um determinado objeto existe na base daddos.
     * Este metodo so diferencia os tipos de objeto e chama o metodo responsavel por verificar a existencia daquele tipo de objeto na respetiva tabela da base de dados
     *
     * @param object - Este objeto pode ser de qualquer tipo, neste metodo e distinguido e depois sera resolvido no respetivo metodo responsavel pelo seu tipo
     *
     * @author Gabriel 29/03/2022
     *
     * @return true se existir, e false caso não exista
     */
    public boolean exists(Object object) {
        switch(object.getClass().getSimpleName()) {
            case "User": {
                return existsUser((User)object);
            }
            default: {
                System.out.println(TerminalColors.ANSI_RED+"[ERROR] "+TerminalColors.ANSI_RESET+"Class "+object.getClass().getName()+" is not recognized in Database.exists()!");
                return false;
            }
        }
    }

    /**
     * Verifica se o utilizar especificado existe na base da dados. Ou seja, verifica as credenciais.
     * Se a password e o username não combinarem então ele retorna false.
     *
     * @param user - User a ser verificado se existe.
     *
     * @return True se o utilizador existir, False se não existir OU se as credenciais não estiverem corretas
     */
    private boolean existsUser(User user) {
        for(User u : users) {
            if (Objects.equals(u.getEmail(), user.getEmail()) && Objects.equals(u.getPassword(), user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<FroggerGameRI> queryAllFroggerGames() {
        return this.froggerGames;
    }
}
