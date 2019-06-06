package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerSettings {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField firstNameTextField, lastNameTextField, emailTextField, addressTextField,
            oldPasswordTextField, newPasswordTextField, passwordAgainTextField;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerSettings() { }

    @FXML
    public void initialize() {
        //Todo: First Name usw aus Datenbank holen und anzeigen lassen
    }

    //Todo: Überlegen, ob evt Email auch geändert werden darf

    @FXML
    private void changePassword(){
        //Todo:
        // 1. TextFields disable auf False setzen (Text darin löschen)
        // --> Falls schon auf True Passwort eingabe prüfen, ob leer
        // 2. Passwort abfragen und ändern
        // 3. TextFields disable auf True setzen
    }

    @FXML
    public void logout(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void home(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void transfer(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("TransferPage.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void service(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Service.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void settings(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Settings.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}