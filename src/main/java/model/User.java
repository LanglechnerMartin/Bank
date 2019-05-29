package model;

        import java.util.Date;

public class User extends Account {

    private String accountNumber;
    private int balance;
    private int pin;

    public User(String fn, String ln, String pw, String em, String st, char ge, int pc, int strn, Date bd, Status stat, String an, int bc, int pinN) {
        super(fn, ln, pw, em, st, ge, pc, strn, bd, stat);
        accountNumber = an;
        balance = bc;
        pin = pinN;
    }
}
