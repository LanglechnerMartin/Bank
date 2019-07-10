package model;

import java.sql.Date;

/*
 GESAMTE DATENBANK VON >JULIAN<
 */

public abstract class Account {

    private String firstName, lastName, password, email, street, status, streetNumber;
    private Date birthdate;
    private char gender;
    private int postalCode, id;

    public Account(String fn, String ln, String pw, String em, String st, char ge,
                   int pc, String strn, Date bd, String stat, int idN){
        firstName = fn;
        lastName = ln;
        password = pw;
        email = em;
        postalCode = pc;
        street = st;
        streetNumber = strn;
        gender = ge;
        birthdate = bd;
        status = stat;
        id = idN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
