package model;

/*
 GESAMTE DATENBANK VON >JULIAN<
 */

public class Ledger {
    private int balance, PIN;
    private String accountNumber;

    public Ledger(int bl, int pin, String accNb){
        balance = bl;
        PIN = pin;
        accountNumber = accNb;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
