package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerService {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerService() { }

    @FXML
    public void initialize() { }

    /*
    AB HIER ALLES VON MARTIN
     */

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
            User user = ControllerMainMenu.user;
            if (user.getStatus().equals("Admin")) {
                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("SettingsAdmin.fxml"));
                rootPane.getChildren().setAll(pane);

            } else {
                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Settings.fxml"));
                rootPane.getChildren().setAll(pane);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}