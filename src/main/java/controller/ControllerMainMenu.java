package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import view.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainMenu {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextArea emailTextArea;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerMainMenu() { }

    @FXML
    public void initialize() { }

    @FXML
    public void loginCheck() {

        String email = emailTextArea.getText();
        String password = loginPasswordField.getText();
        System.out.println("" + email + " " + password);

        if(password.equals("Daniel")){ // Todo Passwort aus Datenbank holen (Aus Datenbank holen, entschlüsseln, abgleichen und dementsprechend einloggen)
            login();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bank-Login");
            alert.setHeaderText("Login failed!");
            alert.setContentText("Wrong Password or E-Mail! Please check your inputs!");
            alert.showAndWait();
        }

    }

    @FXML
    public void login(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
