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
        login();
        //todo: Passwort mit Datenbank anbgleichen
        // (Aus Datenbank holen, entschlüsseln, abgleichen und dementsprechend einloggen)
        // Bei Falsch Dialog erscheinen lassen und warnen
        // (Beispiel bei meinem Github Projekt unter den AddLehrer und AddSchueler Contollern)
        // (Des Alert ist ein Dialog)
    }

    @FXML
    public void login(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
