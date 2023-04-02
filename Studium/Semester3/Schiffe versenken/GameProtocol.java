package battleships;

public class GameProtocol {
    private int status = 0;
    private String Befehl;
    String Ausgabe = null;

    //Protokoll gibt Ausgabe vom Server vor, dies brauchen wir bei dem Botgame
    public String process(String Eingabe){

        //Strings durch Methoden ersetzen
        //Ready ist noch falsch



        //Spiel
        if (status == 0){
            //size befehl aufrufen
            //Ausgabe = getSize();
            Ausgabe = "size 10";

            status += 1;

        } else if (status==1) {
            if (Eingabe.matches("next")) {

                Ausgabe = "ships 5 3 2 2 1 3 1";
                status += 1;

            } else if (Eingabe.matches("done")){
                done();

            }
            if (status==1){

                Ausgabe = "Falscher Befehl: next, done wird erwartet";

            }
        } else if (status==2) {

            if (Eingabe.matches("next|done")) {
                Ausgabe = "name Eric";
                status += 1;
            } else {
                Ausgabe = "Falscher Befehl: next, done wird erwartet";
            }
        } else if (status==3) {

            if (Eingabe.matches("next|done")) {
                Ausgabe = "timeout 100000";
                status += 1;
            } else {
                Ausgabe = "Falscher Befehl: next, done wird erwartet";
            }
        } else if (status==4) {

            if (Eingabe.matches("next|done")) {
                Ausgabe = "repeat false or true";
                status += 1;
            } else {
                Ausgabe = "Falscher Befehl: next, done wird erwartet";
            }
        } else if (status==5) {

            if (Eingabe.matches("next|done")) {
                Ausgabe = "firstShot";
                status += 1;
            } else {
                Ausgabe = "Falscher Befehl: next, done wird erwartet";
            }
        } else if (status==6) {
            if (Eingabe.matches("next|done")) {

                Ausgabe = "Einstellungen fertig, get ready";
                status = 7;

            } else {
                Ausgabe = "Falscher Befehl: next, done wird erwartet";
            }
        }  else if (status==7){
            ready();
            //Ausgabe = "ready";
        } else {
            //else{ verlauf des spiels

            //
            if (Eingabe.matches("ready")){
                ready();
            }



            if (status==15){
                //verlauf
                //Client antwortet ready auf Server
                if (Eingabe.matches("ready")){
                    //Server muss schießen
                    Ausgabe = "Shot 0";
                }



                //Ausgabe = "Status ist gerade 15";

                if (Eingabe.matches("shot 0")){
                    Ausgabe = "Answer 0";
                }
                if (Eingabe.matches("shot 1")){
                    Ausgabe = "Answer 1";
                }
                if (Eingabe.matches("answer 0")){
                    next();
                }
                if (Eingabe.matches("answer 1")){
                    Ausgabe = "shot 0 oder shot 1";
                }


            }


            if (status==99){
                Ausgabe = "The End.";
            }



        }//else
        return Ausgabe;
    }//process






    //Auf nächsten Befehl warten
    public void next(){
        //Erster Part für die Wiedergabe der Spieleinstellungen
        if (status<7){
            status +=1;
        }
        //Zweiter Part für next -> Auf nächsten Befehl warten
        if (status==15){
            Ausgabe = "next";
        }




    }

    //Fertig mit Befehlen
    //Done wird bei vorzeitigem Beenden von Spieleinstellungen aufgerufen oder nach Laden/Speichern
    public void done(){
        //Erster Part vom Einstellungen zu Skippen
        if (status<7){
            status=7;

        }
        //Zweiter Part fürs Laden/Speichern



    }


    //Bereit zum Spielen
    public void ready(){
        //Setze Status auf Wert von Ready
        status=15;
        Ausgabe = "ready";
    }


    /*
    Befehle:
    size
    ships int[][]
    shot [][]
    save
    load
    next
    done
    ready
    timeout []
    name String
    repeat[]
    firstShot
*/


//class
}
