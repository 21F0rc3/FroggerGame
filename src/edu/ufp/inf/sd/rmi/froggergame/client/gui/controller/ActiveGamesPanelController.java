package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.Index;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.rmi.RemoteException;

public class ActiveGamesPanelController {
    public GridPane gridPane;

    /**
     * Popula a o gridPane com o nome de cada um dos jogos ativos.
     * Ele vai buscar a lista de jogos ativos do gameSessionRI(no servidor)
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes 18/04/2022
     */
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

    public void backHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        Index.context.setScene(scene);
        Index.context.show();
    }
}
