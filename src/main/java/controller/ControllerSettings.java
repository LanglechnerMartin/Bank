package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Database;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerSettings {

    private User user;
    private Database db;
    private String email;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TitledPane addUserTitledPane, deleteUserTitledPane;

    @FXML
    private TextField firstNameTextField, lastNameTextField, emailTextField,
            oldPasswordTextField, newPasswordTextField, passwordAgainTextField;

    @FXML
    private TextArea addressTextField;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerSettings() { }

    @FXML
    public void initialize() {
        try {
            user = ControllerMainMenu.user;

            firstNameTextField.setText(user.getFirstName());
            lastNameTextField.setText(user.getLastName());
            emailTextField.setText(user.getEmail());
            addressTextField.setText(user.getPostalCode() + "\n" + user.getStreet() + " " + user.getStreetNumber());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Todo: Überlegen, ob evt Email auch geändert werden darf

    @FXML
    private void changePassword() {
        try {
            if (oldPasswordTextField.isDisabled()) {
                oldPasswordTextField.setDisable(false);
                newPasswordTextField.setDisable(false);
                passwordAgainTextField.setDisable(false);

            } else {
                if (!oldPasswordTextField.getText().equals(user.getPassword())) {
                    showDialog();

                } else {
                    db.connect();
                    db.changePassword(email, newPasswordTextField.getText());
                    db.closeConnection();

                    settings();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteUser() {
        deleteUserTitledPane.setVisible(true);
    }

    @FXML
    private void addUser() {
        addUserTitledPane.setVisible(true);
    }

    private void showDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bank-Login");
        alert.setHeaderText("Login failed!");
        alert.setContentText("Wrong Password or E-Mail! Please check your inputs!");
        alert.showAndWait();
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