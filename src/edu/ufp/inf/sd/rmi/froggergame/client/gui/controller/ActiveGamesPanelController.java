package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.rmi.RemoteException;

public class ActiveGamesPanelController {
    public GridPane gridPane;

    public void populate() throws RemoteException {
        System.out.println(GameSessionPanelController.gameSessionRI.getActiveGames().size());

        int r = 0, c = 0;
        for(FroggerGameRI froggerGameRI : GameSessionPanelController.gameSessionRI.getActiveGames()) {
            Text text = new Text();
            text.setText(froggerGameRI.getServerName());
            gridPane.add(text, r, c);

            c++;
            if(c==3) {
                c=0;
                r++;
            }
        }
    }
}
