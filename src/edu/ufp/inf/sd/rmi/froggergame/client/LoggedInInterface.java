package edu.ufp.inf.sd.rmi.froggergame.client;

import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl;

import java.rmi.RemoteException;
import java.util.Scanner;

public class LoggedInInterface extends ClientInterface {
    public LoggedInInterface(GameFactoryRI gameFactoryRI, GameSessionImpl gameSessionRI) {
        super(gameFactoryRI);
        this.gameSession = gameSessionRI;
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
            this.gameSession.createGame(serverName, Integer.parseInt(difficulty));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
