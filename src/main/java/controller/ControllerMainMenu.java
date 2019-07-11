package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.Caesar;
import model.Database;
import model.User;
import view.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainMenu {


    public static String emailLogin;
    private static String  passwordLogin;
    public static User user;
    private Caesar cs;


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
//Caesar encrypt + decrypt
    @FXML
    public void loginCheck() {

        try {
            cs = new Caesar();

            emailLogin = emailTextArea.getText();
            passwordLogin = loginPasswordField.getText();

            Database db = new Database();
            db.connect();
            user = db.getUser(emailLogin);
            String passwordDatabase = user.getPassword();
            db.closeConnection();

            if(passwordLogin.equals(cs.decrypt(passwordDatabase))){
                login();
            }
            else{
                showDialog();
            }

        } catch (Exception e) {
            showDialog();
        }

    }

    private void showDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bank-Login");
        alert.setHeaderText("Login failed!");
        alert.setContentText("Wrong Password or E-Mail! Please check your inputs!");
        alert.showAndWait();
    }

    @FXML
    private void login(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
