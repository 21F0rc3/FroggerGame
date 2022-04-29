package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Frogger;
import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.util.Posititon;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    public Boolean gameStarted = false;
    private ArrayList<Posititon> frogsPositions;

    public GameState() {
        frogsPositions = new ArrayList<>();
    }

    public ArrayList<Posititon> getFrogsPositions() {
        return frogsPositions;
    }

    public void setFrogsPositions(ArrayList<Posititon> frogsPositions) {
        this.frogsPositions = frogsPositions;
    }
}
