package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.Index;
import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryRI;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;



public class AuthController {
    public static GameFactoryRI gameFactoryRI;

    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    public void registerHandler() {
        try {
            gameFactoryRI.register(emailField.getText(), passwordField.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void loginHandler() {
        try{
            if(gameFactoryRI.login(emailField.getText(), passwordField.getText()) != null) {
                GameSessionPanelController.gameSessionRI = gameFactoryRI.login(emailField.getText(), passwordField.getText());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
                Parent parent = loader.load();

                Scene scene = new Scene(parent);

                Index.context.setScene(scene);
                Index.context.show();
            }
            System.out.println();
        }catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
