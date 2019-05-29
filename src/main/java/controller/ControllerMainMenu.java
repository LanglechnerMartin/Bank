package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainMenu {

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
}
