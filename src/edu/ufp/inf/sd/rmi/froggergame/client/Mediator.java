package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.*;

import java.rmi.RemoteException;

/**
 * Implementa o padrão Mediator
 *
 * Eu utilizei este padrão porque pareceu-me melhor para gerir as diferentes
 * interfaces remotas
 *
 * @author Gabriel Fernandes 08/05/2022
 */
public class Mediator {
    private GameFactoryRI gameFactoryRI;
    private GameSessionRI gameSessionRI;
    private FroggerGameRI froggerGameRI;
    //private FroggerClient froggerClient;
    private GameStateHandler gameStateHandler;
    private ObserverRI observerRI;

    /**
     * Singleton
     */
    private static Mediator instance;

    public static Mediator getInstance() {
        if(instance == null) {
            instance = new Mediator();
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
        }
    }

    public void setGameFactoryRI(GameFactoryRI gameFactoryRI) {
        this.gameFactoryRI = gameFactoryRI;
    }

    public GameFactoryRI getGameFactoryRI() {
        return gameFactoryRI;
    }

    public GameSessionRI getGameSessionRI() {
        return gameSessionRI;
    }

    public FroggerGameRI getFroggerGameRI() {
        return froggerGameRI;
    }

    public GameStateHandler getGameStateHandler() {
        return gameStateHandler;
    }

    public ObserverRI getObserverRI() {
        return observerRI;
    }
}
