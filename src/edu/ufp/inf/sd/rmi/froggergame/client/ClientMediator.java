package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.FroggerGameRI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameSessionRI;

import java.rmi.RemoteException;
import java.util.Observer;

/**
 * Implementa o padrão Mediator
 *
 * Eu utilizei este padrão porque pareceu-me melhor para gerir as diferentes
 * interfaces remotas
 *
 * @author Gabriel Fernandes 08/05/2022
 */
public class ClientMediator {
    private FroggerClient froggerClient;
    private GameFactoryRI gameFactoryRI;
    private GameSessionRI gameSessionRI;
    private FroggerGameRI froggerGameRI;
    private GameStateHandler gameStateHandler;
    private ObserverRI observerRI;

    /**
     * Singleton
     */
    private static ClientMediator instance;

    public static ClientMediator getInstance() {
        if(instance == null) {
            instance = new ClientMediator();
        }
        return instance;
    }

    /**
     * Regista um componente novo, ou seja, inicializa a interface remota
     *
     * @param component Component que sera inicializado em uma das interfaces remotas
     *
     * @author Gabriel Fernandes 08/05/2022
     */
    public void registerComponent(Component component) throws RemoteException {
        switch(component.getName()) {
            case "GameFactory": {
                this.gameFactoryRI = (GameFactoryRI)component;
                break;
            }
            case "GameSession": {
                this.gameSessionRI = (GameSessionRI)component;
                break;
            }
            case "FroggerGame": {
                this.froggerGameRI = (FroggerGameRI)component;
                break;
            }
            case "ObserverRI": {
                this.observerRI = (ObserverRI)component;
                break;
            }
            case "GameStateHandler": {
                this.gameStateHandler = (GameStateHandler)component;
                break;
            }
            case "FroggerClient": {
                this.froggerClient = (FroggerClient) component;
                break;
            }
        }
    }

    public GameFactoryRI getGameFactoryRI() {
        // Se o gameFactoryRI esta null então e porque o servidor foi a baixo e precisamos de
        // uma nova conexão outro servidor
        froggerClient.getConnectionToServer();

        return gameFactoryRI;
    }

    public GameSessionRI getGameSessionRI() {
        // Se o gameFactoryRI esta null então e porque o servidor foi a baixo e precisamos de
        // uma nova conexão outro servidor
        froggerClient.getConnectionToServer();

        return gameSessionRI;
    }

    public FroggerGameRI getFroggerGameRI() {
        // Se o gameFactoryRI esta null então e porque o servidor foi a baixo e precisamos de
        // uma nova conexão outro servidor
        froggerClient.getConnectionToServer();

        return froggerGameRI;
    }

    public GameStateHandler getGameStateHandler() {
        // Se o gameFactoryRI esta null então e porque o servidor foi a baixo e precisamos de
        // uma nova conexão outro servidor
        froggerClient.getConnectionToServer();

        return gameStateHandler;
    }

    public ObserverRI getObserverRI() {
        // Se o gameFactoryRI esta null então e porque o servidor foi a baixo e precisamos de
        // uma nova conexão outro servidor
        froggerClient.getConnectionToServer();

        return observerRI;
    }

    public FroggerClient getFroggerClient() {
        return froggerClient;
    }
}
