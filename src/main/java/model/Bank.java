package model;

public class Bank{
    private int balance;

    private static Bank dieBank = new Bank(0);
    private int balance;

    private Bank(int bc){
        balance = bc;
    }

    public void changeBalance(int newBalance){
        balance = newBalance;
    }

    public int getBalance() {
        return balance;
    }

    public static Bank getBank(){
        return dieBank;
    }
}
