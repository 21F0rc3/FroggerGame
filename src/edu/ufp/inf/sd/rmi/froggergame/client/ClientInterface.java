package edu.ufp.inf.sd.rmi.froggergame.client;

import com.sun.xml.internal.ws.client.ClientTransportException;
import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl;

import java.util.Scanner;

public class ClientInterface {
    public GameFactoryRI gameFactoryRI;
    public GameSessionImpl gameSession;

    public static void init(GameFactoryRI gameFactoryRI) {
        ClientInterface clientInterface = new ClientInterface(gameFactoryRI);

        InitialMenuInterface initialMenuInterface = new InitialMenuInterface(gameFactoryRI);
        initialMenuInterface.initialMenu();
    }

    public ClientInterface(GameFactoryRI gameFactoryRI) {
        this.gameFactoryRI = gameFactoryRI;
        this.gameSession = null;
    }
}
