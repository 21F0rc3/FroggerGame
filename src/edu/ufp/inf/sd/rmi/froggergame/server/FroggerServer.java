package edu.ufp.inf.sd.rmi.froggergame.server;

import edu.ufp.inf.sd.rmi.froggergame.client.FroggerClient;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameFactoryImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.interfaces.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.util.rmisetup.SetupContextRMI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FroggerServer implements Component {

    /**
     * Context for running a RMI Servant on a SMTP_HOST_ADDR
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold reference MAIL_TO_ADDR the Servant impl
     */
    public GameFactoryRI gameFactoryRI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    public ServerSystemRI serverSystemRI;

    public static void main(String args[]) {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi.froggergame.server.FroggerClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            new FroggerServer(args);
        }
    }

    /**
     *
     * @param args
     */
    public FroggerServer(String args[]) {
        //1. Init the RMI context (load security manager, lookup subject, etc.)
        initContext(args);
        //2. Register this component in Mediator
        initComponent();
        //3. Rebind
        rebindService();
    }

    private void initContext(String args[]) {
        try {
            //============ List and Set args ============
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serverServiceName = args[2];
            String primaryServerServiceName = args[3];
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to setup RMI context...");
            //============ Create a context for RMI setup ============
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serverServiceName, primaryServerServiceName});
            this.serverSystemRI = (ServerSystemRI) lookupService();
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void initComponent() {
        try {
            ServerMediator.getInstance().registerComponent(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    protected void rebindService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Bind service on rmiregistry and wait for calls
            if (registry != null) {
                //============ Create Servant ============
                gameFactoryRI= new GameFactoryImpl();
                ServerMediator.getInstance().registerComponent((Component) gameFactoryRI);

                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR rebind service @ {0}", serviceUrl);

                //============ Rebind servant ============
                //Naming.bind(serviceUrl, helloWorldRI);
                registry.rebind(serviceUrl, gameFactoryRI);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "service bound and running. :)");
            } else {
                //System.out.println("HelloWorldServer - Constructor(): create registry on port 1099");
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(1);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR FroggerGame service ============
                serverSystemRI = (ServerSystemRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return serverSystemRI;
    }

    private static void loadProperties() throws IOException {

        Logger.getLogger(Thread.currentThread().getName()).log(Level.INFO, "goig MAIL_TO_ADDR load props...");
        // create and load default properties
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("defaultproperties.txt");
        defaultProps.load(in);
        in.close();

        BiConsumer<Object, Object> bc = (key, value) ->{
            System.out.println(key.toString()+"="+value.toString());
        };
        defaultProps.forEach(bc);

        // create application properties with default
        Properties props = new Properties(defaultProps);

        FileOutputStream out = new FileOutputStream("defaultproperties2.txt");
        props.store(out, "---No Comment---");
        out.close();
    }

    public void getConnectionToPrimaryServer() {
        //============ Get proxy MAIL_TO_ADDR FroggerGame service ============
        try {
            serverSystemRI = (ServerSystemRI) contextRMI.getRegistry().lookup(contextRMI.getServicesUrl(1));
            ServerMediator.getInstance().registerComponent((Component) serverSystemRI);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() throws RemoteException {
        return "FroggerServer";
    }
}
