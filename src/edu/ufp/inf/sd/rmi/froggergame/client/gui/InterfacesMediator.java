package edu.ufp.inf.sd.rmi.froggergame.client.gui;

import edu.ufp.inf.sd.rmi.froggergame.server.*;

import java.rmi.RemoteException;

public class InterfacesMediator {
    private GameFactoryRI gameFactoryRI;
    private GameSessionRI gameSessionRI;
    private FroggerGameRI froggerGameRI;

    public InterfacesMediator(GameFactoryRI gameFactoryRI) {
        this.gameFactoryRI = gameFactoryRI;
    }

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
        }
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
}
