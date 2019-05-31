package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private PasswordField loginPassword;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerMainMenu() { }

    @FXML
    public void initialize() { }

    @FXML
    public void loginButtonClicked() {

        String email = emailTextArea.getText();
        String password = loginPassword.getText();
        System.out.println("" + email + " " + password);
        //todo: look for Password and Email in database
    }

    @FXML
    public void loginButton(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
