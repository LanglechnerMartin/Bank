package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Database;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
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
            oldPasswordTextField, newPasswordTextField, passwordAgainTextField,
            idField, emailField, fnField, lnField, deleteEmail, addUserFN, addUserLN,
            addUserPW, addUserEmail, addUserBD, addUserPC, addUserStreet, addUserSN,
            addUserGender, addUserStatus;

    @FXML
    private TextArea addressTextField;

    @FXML
    private TableView tableView;

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

            db = new Database();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        setupTable();
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

    private void setupTable(){

        try {
            db.connect();

            String SQL = "SELECT ID, FirstName, LastName, Email, Birthdate, Status from Account";

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

    @FXML
    public void delete() {
        try {
            db.connect();
            db.deleteUser(deleteEmail.getText());
            db.closeConnection();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletedUser() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bank-Login");
        alert.setHeaderText("Deleted User");
        alert.setContentText("You successfully deleted this Account");
        alert.showAndWait();
    }

    @FXML
    public void submit() {
        try {
            db.connect();

            Date date = new Date(Integer.parseInt(addUserBD.getText()));

            db.addAccount(addUserFN.getText(), addUserLN.getText(), addUserPW.getText(), addUserEmail.getText(),
                    Integer.parseInt(addUserPC.getText()), addUserStreet.getText(), addUserSN.getText(),
                    addUserGender.getText().charAt(0), date, addUserStatus.getText(), 1);

            db.closeConnection();
        }

        catch(Exception e){
            e.printStackTrace();
        }

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