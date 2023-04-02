package battleships;

import java.io.Serializable;

//Spielfeldeinheiten, zum Speichern/Darstellen von Schiffen (IDNummer, ob getroffen, zerstört oder unversehrt) & Wasser
public class Unit implements Serializable {

    public boolean hit, destroyed, water;
    public int shipNr;


    public Unit() {
        this.hit = false;
        this.destroyed=false;
        this.water=false;
    }
}//Unit