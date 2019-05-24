package model;

import java.util.Date;

public class Administrator extends User {

    public Administrator(String fn, String ln, String pw, String em, String st, char ge, int pc, int strn, Date bd, Status stat) {
        super(fn, ln, pw, em, st, ge, pc, strn, bd, stat);
    }
}
