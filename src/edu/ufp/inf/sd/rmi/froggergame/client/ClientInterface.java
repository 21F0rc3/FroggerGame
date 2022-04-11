package edu.ufp.inf.sd.rmi.froggergame.client;

import com.sun.xml.internal.ws.client.ClientTransportException;
import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionRI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientInterface {
    public GameFactoryRI gameFactoryRI;
    public GameSessionRI gameSessionRI;

    public static void init(GameFactoryRI gameFactoryRI) {
        ClientInterface clientInterface = new ClientInterface(gameFactoryRI);
        clientInterface.initialMenu();
    }

    public ClientInterface(GameFactoryRI gameFactoryRI) {
        this.gameFactoryRI = gameFactoryRI;
        this.gameSessionRI = null;
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
            GameSessionRI session = this.gameFactoryRI.login(email, password);

            this.gameSessionRI = session;

            loggedInMenu();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void loggedInMenu() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.println("FroggerGame" +
                "\n [1]: Create New Game" +
                "\n [2]: Join Game" +
                "\n [3]: Log Out");

        String option = myObj.nextLine();  // Read user input

        loggedInMenuChoice(option);
    }

    public void loggedInMenuChoice(String option) {
        switch (option) {
            case "1": {
                createGame();
                break;
            }
            case "2": {
                listServers();
                break;
            }
            default: {
                System.out.println(option + " is not a valid option.");
                break;
            }
        }
    }

    private void createGame() {
        System.out.println("<<< Create New Game >>>");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.print("Server Name: ");

        String serverName = myObj.nextLine();  // Read user input

        System.out.print("Difficulty: ");

        String difficulty = myObj.nextLine();  // Read user input

        try {
            this.gameSessionRI.createGame(serverName, Integer.parseInt(difficulty));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        loggedInMenu();
    }

    private void listServers() {
        try {
            this.gameSessionRI.getActiveGames();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        loggedInMenu();
    }
}
