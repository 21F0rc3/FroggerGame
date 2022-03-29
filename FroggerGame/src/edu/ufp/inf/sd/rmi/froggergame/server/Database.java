package edu.ufp.inf.sd.rmi.froggergame.server;


import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class Database {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final ArrayList<User> users;// = new ArrayList();

    public Database() {
        users = new ArrayList();
    }

    public void create(Object object) {
        switch(object.getClass().getSimpleName()) {
            case "User": {
                createUser((User)object);
                break;
            }
            default: {
                System.out.println(ANSI_RED+"[ERROR] "+ANSI_RESET+"Class "+object.getClass().getName()+" is not recognized in Database.create()!");
            }
        }
    }

    private void createUser(User user) {
        users.add(user);
        System.out.println(ANSI_GREEN+"[CREATED] "+ANSI_RESET+"New User "+user.toString()+" has been added to Database.");
    }

    public boolean exists(Object object) {
        switch(object.getClass().getSimpleName()) {
            case "User": {
                return existsUser((User)object);
            }
            default: {
                System.out.println(ANSI_RED+"[ERROR] "+ANSI_RESET+"Class "+object.getClass().getName()+" is not recognized in Database.exists()!");
                return false;
            }
        }
    }

    private boolean existsUser(User user) {
        for(User u : users) {
            if (Objects.equals(u.getUsername(), user.getUsername()) && Objects.equals(u.getPassword(), user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
