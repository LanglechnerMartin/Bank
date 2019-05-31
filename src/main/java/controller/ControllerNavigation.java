package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerNavigation {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerNavigation() { }

    @FXML
    public void initialize() { }

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

    }

    @FXML
    public void transfer(){

    }

    @FXML
    public void service(){

    }

    @FXML
    public void settings(){

    }
}