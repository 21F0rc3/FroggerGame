package edu.ufp.inf.sd.rmi.froggergame.client;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.util.RabbitUtils;
import edu.ufp.inf.sd.rmi.froggergame.util.rmisetup.SetupContextRMI;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FroggerClient implements Component {
    /**
     * Context for connecting a RMI client MAIL_TO_ADDR a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    public GameFactoryRI gameFactoryRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi.froggergame.server.FroggerClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            new FroggerClient(args);
        }
    }

    public FroggerClient(String args[]) {
        //1. Init the RMI context (load security manager, lookup subject, etc.)
        initContext(args);
        //2. Create observer (which attaches himself to subject)
        initObserver(args);
        //3. Init the GUI components
        initGUI();
    }

    private void initContext(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to setup RMI context...");
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
            this.gameFactoryRI = (GameFactoryRI)lookupService();
        } catch (RemoteException e) {
            Logger.getLogger(FroggerClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void initObserver(String args[]) {
        try {
            ObserverImpl observer=new ObserverImpl();
            ClientMediator.getInstance().registerComponent(observer);
        } catch (Exception e) {
            Logger.getLogger(FroggerClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void initGUI() {
        try {
            ClientMediator.getInstance().registerComponent(this);
            String[] args = {"0"};
            GUI.main(args);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR FroggerGame service ============
                gameFactoryRI = (GameFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return gameFactoryRI;
    }

    public void getConnectionToServer() {
        //============ Get proxy MAIL_TO_ADDR FroggerGame service ============
        try {
            gameFactoryRI = (GameFactoryRI) contextRMI.getRegistry().lookup(contextRMI.getServicesUrl(0));
            ClientMediator.getInstance().registerComponent((Component) gameFactoryRI);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("DEU MERDA");
        }
    }

    @Override
    public String getName() throws RemoteException {
        return "FroggerClient";
    }
}
