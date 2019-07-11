package model;

/*
 GESAMTE DATENBANK VON >JULIAN<
 */

public class Caesar {

    private int switchIt = -1;
    private char[] chArray = null;

    public Caesar() {
        this(3);
    }

    public Caesar(int switchIt) {

        this.chArray = createArray();
        setSwitchIt(switchIt);
    }

    public static void main(String[] args) {
        Caesar c = new Caesar();
        String tmp = c.decrypt("Lqirupdwln534<");
        System.out.println(tmp);
    }

    public int getSwitchIt() {
        return this.switchIt;
    }

    public void setSwitchIt(int switchIt) {
        this.switchIt = switchIt;
    }

    public String decrypt(String text) {

        setSwitchIt(getSwitchIt() * -1);
        text = encrypt(text);
        setSwitchIt(getSwitchIt() * -1);
        return text;
    }

    public String encrypt(String text) {

        StringBuffer encrypted = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char cur = text.charAt(i);
            for (int j = 0; j < this.chArray.length; j++) { if (cur == this.chArray[j]) { int pos = j + getSwitchIt(); if (pos >= this.chArray.length) {
                pos -= this.chArray.length;
            }
            else if (pos < 0) {
                pos += this.chArray.length;
            }
                cur = this.chArray[pos];
                break;
            }
            }
            encrypted.append(cur);
        }
        return encrypted.toString();
    }

    public char[] createArray() {

        char[] ch = new char[26 + 26 + 10 + 33 + 6];
        int pos = 0;
        for (int i = 32; i < 127; i++) {
            ch[pos++] = (char)i;
        }
        ch[pos++] = 'ä';
        ch[pos++] = 'ö';
        ch[pos++] = 'ü';
        ch[pos++] = 'Ä';
        ch[pos++] = 'Ö';
        ch[pos++] = 'Ü';
        return ch;
    }

    public char[] getChArray() {
        return this.chArray;
    }

    public void setChArray(char[] chArray) {
        this.chArray = chArray;
    }
}