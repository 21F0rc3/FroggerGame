package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl;

import java.rmi.RemoteException;
import java.util.Scanner;

public class InitialMenuInterface extends ClientInterface {
    public InitialMenuInterface(GameFactoryRI gameFactoryRI) {
        super(gameFactoryRI);

    }

    public void initialMenu() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.println("FroggerGame" +
                "\n [1]: Register" +
                "\n [2]: Login");

        String option = myObj.nextLine();  // Read user input

        initialMenuChoice(option);
    }

    private void initialMenuChoice(String option) {
        switch (option) {
            case "1": {
                register();
                break;
            }
            case "2": {
                login();
                break;
            }
            default: {
                System.out.println(option + " is not a valid option.");
                break;
            }
        }
    }

    private void register() {
        System.out.println("<<< Register >>>");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.print("Email: ");

        String email = myObj.nextLine();  // Read user input

        System.out.print("Password: ");

        String password = myObj.nextLine();  // Read user input

        try {
            this.gameFactoryRI.register(email, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        initialMenu();
    }

    private void login() {
        System.out.println("<<< Login >>>");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.print("Email: ");

        String email = myObj.nextLine();  // Read user input

        System.out.print("Password: ");

        String password = myObj.nextLine();  // Read user input

        try {
            GameSessionImpl session = this.gameFactoryRI.login(email, password);

            if(session!=null) {
                LoggedInInterface loggedInInterface = new LoggedInInterface(gameFactoryRI, session);
                loggedInInterface.loggedInMenu();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
