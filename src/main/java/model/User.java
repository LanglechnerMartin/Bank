package model;

import java.sql.Date;
import java.util.Random;

public class User extends Account{

    private Ledger ledger;
    private Random random = new Random();

    public User(String fn, String ln, String pw, String em, String st, char ge,
                int pc, int strn, Date bd, String stat) {
        super(fn, ln, pw, em, st, ge, pc, strn, bd, stat);
        ledger = new Ledger(0, random.nextInt(9999), generateAccountNumber());
    }

    public String generateAccountNumber() {
        String tmp3 = "00000000";
        int tmp2 = random.nextInt(99);
        int tmp4 = random.nextInt(999999);
        return String.format("DE02%d%s%d", tmp2, tmp3, tmp4);
    }

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }
}
