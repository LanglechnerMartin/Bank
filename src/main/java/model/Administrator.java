package model;

import java.util.Date;

public class Administrator extends Account{

    public Administrator(String fn, String ln, String pw, String em, String st, char ge, int pc, int strn, String bd, Status stat) {
        super(fn, ln, pw, em, st, ge, pc, strn, bd, stat);
    }
}
