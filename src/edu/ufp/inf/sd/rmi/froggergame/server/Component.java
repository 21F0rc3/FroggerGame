package edu.ufp.inf.sd.rmi.froggergame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Utilizado no padrão Mediator
 *
 * Identifica um Componente, depois no InterfacesMediator
 * ele distingue o que é o que
 *
 * @author Gabriel Fernandes 08/05/2022
 */
public interface Component extends Remote {
    String getName() throws RemoteException;
}
