package model;

import java.sql.Date;

public class Administrator extends Account{

    public Administrator(String fn, String ln, String pw, String em, String st, char ge, int pc, int strn, Date bd, String stat) {
        super(fn, ln, pw, em, st, ge, pc, strn, bd, stat);
    }
}
