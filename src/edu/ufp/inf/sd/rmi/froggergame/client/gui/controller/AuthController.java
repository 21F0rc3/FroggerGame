package edu.ufp.inf.sd.rmi.froggergame.client.gui.controller;

import edu.ufp.inf.sd.rmi.froggergame.client.gui.GUI;
import edu.ufp.inf.sd.rmi.froggergame.server.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.GameFactoryRI;
import edu.ufp.inf.sd.rmi.froggergame.server.GameSessionImpl;
import edu.ufp.inf.sd.rmi.froggergame.server.models.User;
import edu.ufp.inf.sd.rmi.froggergame.util.JwtUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.RemoteException;



public class AuthController {
    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    /**
     * Responsavel por chamar a função que regista um novo usuario
     * quando clicamos no botão de Register
     *
     * @author Gabriel Fernandes 18/04/2022
     */
    public void registerHandler() {
        try {
            GUI.interfacesMediator.getGameFactoryRI().register(emailField.getText(), passwordField.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsavel por chamar a função que autentica o usuario
     * quando clicamos no botão de Login
     *
     * Se a autenticaçaõ for um sucesso ele faz um switch de scenes
     * para a pagina inicial onde tem o menu "GameSessionPanel"
     *
     * @author Gabriel Fernandes
     */
    public void loginHandler() {
        try{
            if(GUI.interfacesMediator.getGameFactoryRI().login(emailField.getText(), passwordField.getText()) != null) {
                // Guarda a interface de GameSession nos mediator de interfaces
                GUI.interfacesMediator.registerComponent((Component) (GUI.interfacesMediator.getGameFactoryRI().login(emailField.getText(), passwordField.getText())));

                GUI.interfacesMediator.getGameFactoryRI().login(emailField.getText(), passwordField.getText());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameSessionPanel.fxml"));
                Parent parent = loader.load();

                Scene scene = new Scene(parent);

                GUI.context.setScene(scene);
                GUI.context.show();
            }
            System.out.println();
        }catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
