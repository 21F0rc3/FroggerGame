package edu.ufp.inf.sd.rmi.froggergame.server.models;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "Username=" + username + ", Password=" + password + '}';
    }

    /**
     * @return the uname
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the uname to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
