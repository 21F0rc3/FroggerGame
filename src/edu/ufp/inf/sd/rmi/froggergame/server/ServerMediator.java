package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameFactoryRI;

import java.rmi.RemoteException;

public class ServerMediator {
    private FroggerServer froggerServer;
    private ServerSystemRI serverSystemRI;
    private GameFactoryRI gameFactoryRI;

    /**
     * Singleton
     */
    private static ServerMediator instance;

    public static ServerMediator getInstance() {
        if(instance == null) {
            instance = new ServerMediator();
        }
        return instance;
    }

    /**
     * Regista um componente novo, ou seja, inicializa a interface remota
     *
     * @param component Component que sera inicializado em uma das interfaces remotas
     *
     * @author Gabriel Fernandes 13/05/2022
     */
    public void registerComponent(Component component) throws RemoteException {
        switch(component.getName()) {
            case "FroggerServer": {
                this.froggerServer = (FroggerServer) component;
                break;
            }
            case "ServerSystem": {
                this.serverSystemRI = (ServerSystemRI) component;
                break;
            }
            case "GameFactory": {
                this.gameFactoryRI = (GameFactoryRI) component;
                break;
            }
        }
    }

    public FroggerServer getFroggerServer() {
        return froggerServer;
    }

    public ServerSystemRI getServerSystemRI() {
        // Se o gameFactoryRI esta null então e porque o servidor foi a baixo e precisamos de
        // uma nova conexão outro servidor
        froggerServer.getConnectionToPrimaryServer();

        return serverSystemRI;
    }

    public GameFactoryRI getGameFactoryRI() {
        return gameFactoryRI;
    }

}
