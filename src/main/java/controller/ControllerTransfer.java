package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Database;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ControllerTransfer {

    private User user; //user = ControllerMainMenu.user;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView tableView;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerTransfer() { }

    @FXML
    public void initialize() {
        setupTable();
    }

    @FXML
    private void directTransfer() {
        //Todo: XY
    }

    private void setupTable(){

        try {
            Database db = new Database();
            db.connect();

            user = ControllerMainMenu.user;
            int id = user.getId();

            String SQL = String.format("SELECT * FROM History WHERE ToUser = '%d' OR FromUser = '%d';", id, id);

            Statement stat = db.getConnection().createStatement();
            ResultSet rs = stat.executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }
            db.closeConnection();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @FXML
    private void search(){
        String id = idField.getText();
        String vorname = fnField.getText();
        String nachname = lnField.getText();
        String email = emailField.getText();

        String query = "SELECT ID, FirstName, LastName, Email, Birthdate, Status FROM Account WHERE ";
        String tmp = "";

        if (!id.equals("")) {
            query += tmp + "ID = '" + id + "'";
            tmp = " AND ";
        }
        if (!vorname.equals("")) {
            query += tmp + "FirstName = '" + vorname + "'";
            tmp = " AND ";
        }
        if (!nachname.equals("")) {
            query += tmp + "LastName = '" + nachname + "'";
            tmp = " AND ";
        }
        if (!email.equals("")) {
            query += tmp + "Email = '" + email + "'";
            tmp = " AND ";
        }
        if (id.equals("") && email.equals("") && vorname.equals("") && nachname.equals("")) {
            query = "SELECT ID, FirstName, LastName, Email, Birthdate, Status FROM Account";
        }

        db.connect();
        ObservableList data = db.getAccount(query);
        tableView.getItems().clear();
        tableView.setItems(data);
        db.closeConnection();

    }
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