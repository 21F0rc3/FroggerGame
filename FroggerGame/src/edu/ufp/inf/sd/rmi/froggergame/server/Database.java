package edu.ufp.inf.sd.rmi.froggergame.server;


import edu.ufp.inf.sd.rmi.froggergame.server.models.User;

import java.util.ArrayList;

public class Database {
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
                System.out.println("[ERROR] Class "+object.getClass().getName()+" is not recognized in Database.create()!");
            }
        }
    }

    private void createUser(User user) {
        System.out.println("[CREATED] New User "+user.toString()+" has been added to Database.");
    }
}
