/**
 * @author Daniel
 */
package model;

import java.sql.Date;

public class User extends Account{

    public User(String fn, String ln, String pw, String em, String st, char ge,
                int pc, String strn, Date bd, String stat, int idN) {
        super(fn, ln, pw, em, st, ge, pc, strn, bd, stat, idN);
    }
}
