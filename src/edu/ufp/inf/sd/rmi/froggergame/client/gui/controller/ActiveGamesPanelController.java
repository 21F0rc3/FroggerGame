package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.FroggerGameRI;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.attribute.PosixFileAttributeView;
import java.rmi.RemoteException;

public class ActiveGamesPanelController {
    public VBox vBox;

    /**
     * Popula a o gridPane com o nome de cada um dos jogos ativos.
     * Ele vai buscar a lista de jogos ativos do gameSessionRI(no servidor)
     *
     * @throws RemoteException
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void populate() throws RemoteException {
        System.out.println(GUI.interfacesMediator.getGameSessionRI().getActiveGames().size());

        for(FroggerGameRI froggerGameRI : GUI.interfacesMediator.getGameSessionRI().getActiveGames()) {
            addGameItemToList(froggerGameRI);
        }
    }

    /**
     * Cria uma nova template de um servidor, para ser listado na lista de jogos ativos
     * no painel.
     *
     * Cria um Text com o nome do servidor e um Botão para dar Join
     *
     * @param game Proxy(FroggerGameRI) do jogo
     *
     * @author Gabriel Fernandes 26/04/2022
     */
    private void addGameItemToList(FroggerGameRI game) throws RemoteException {
        Text text = new Text();
        text.setText(game.getServerInfo()[0]);

        Button button = new Button();
        button.setText("Join");
        button.setOnMouseClicked(event -> {
            try {
                joinGame(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().add(text);
        hBox.getChildren().add(button);

        vBox.getChildren().add(hBox);
    }

    private void joinGame(FroggerGameRI game) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameLobby.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GameLobbyController controller = new GameLobbyController(game);
        loader.setController(controller);

        GUI.context.setScene(scene);
        GUI.context.show();
    }

    /**
     * Resposavel por voltar para atras quando clicamos no botão
     *
     * @throws IOException
     *
     * @author Gabriel Fernandes 26/04/2022
     */
    public void backHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        GUI.context.setScene(scene);
        GUI.context.show();
    }
}