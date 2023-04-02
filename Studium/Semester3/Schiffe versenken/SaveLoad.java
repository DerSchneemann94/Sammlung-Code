package battleships;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;
import javafx.stage.Stage;

//Klasse zum Speichern und laden des Spieles
public class SaveLoad {
    public static String saveID;
    public static volatile boolean waitingForSave=true, loadingDone=false;

    public static void saveBotData(Bot bot) {
        try{
            //Über FileChooser den Speicherort und Dateinamen festlegen
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");

            //Dateiart
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".ser", "*.*"));

            //einzigartige SpeicherID (Lässt sich nach belieben ändern)
            saveID = ""+System.currentTimeMillis();
            bot.saveID = saveID;
            fileChooser.setInitialFileName(saveID);

            //Pfad zum Speicherort
            Path p = Paths.get(System.getProperty("user.dir")+"/savefiles");
            Files.createDirectories(p);
            fileChooser.setInitialDirectory(new File(p.toString()));

            //speichern
            File save = fileChooser.showSaveDialog(new Stage());
            if(save!=null){
                FileOutputStream fileOut = new FileOutputStream(save);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(bot);
            }
            //Flag ändern für Host/Client
            SaveLoad.waitingForSave=false;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void saveBotData(Bot bot, String ID) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");

            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".ser", "*.*"));
            fileChooser.setInitialFileName(ID);

            Path p = Paths.get(System.getProperty("user.dir")+"/savefiles");
            Files.createDirectories(p);
            fileChooser.setInitialDirectory(new File(p.toString()));

            File save = fileChooser.showSaveDialog(new Stage());
            if(save!=null){
                FileOutputStream fileOut = new FileOutputStream(save);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(bot);
            }
            SaveLoad.waitingForSave=false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Daten speichern
    public static void saveData(Game game) {
        try{
            //Über FileChooser den Speicherort und Dateinamen festlegen
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");

            //Dateiart
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".ser", "*.*"));

            //einzigartige SpeicherID (Lässt sich nach belieben ändern)
            saveID = ""+System.currentTimeMillis();
            game.saveID = saveID;
            fileChooser.setInitialFileName(saveID);

            //Pfad zum Speicherort
            Path p = Paths.get(System.getProperty("user.dir")+"/savefiles");
            Files.createDirectories(p);
            fileChooser.setInitialDirectory(new File(p.toString()));

            //speichern
            File save = fileChooser.showSaveDialog(new Stage());
            if(save!=null){
                FileOutputStream fileOut = new FileOutputStream(save);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(game);
            }
            //Flag ändern für Host/Client
            SaveLoad.waitingForSave=false;

        }catch(Exception e){
            e.printStackTrace();
        }


    }//saveData

    //Speichern (Falls der andere Spieler zuerst gespeichert hat)
    public static void saveData(Game game, String ID) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");

            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".ser", "*.*"));
            fileChooser.setInitialFileName(ID);

            Path p = Paths.get(System.getProperty("user.dir")+"/savefiles");
            Files.createDirectories(p);
            fileChooser.setInitialDirectory(new File(p.toString()));

            File save = fileChooser.showSaveDialog(new Stage());
            if(save!=null){
                FileOutputStream fileOut = new FileOutputStream(save);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(game);
            }
            SaveLoad.waitingForSave=false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }//saveData

    public static Bot loadBotData(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");

            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".ser", "*.*"));

            Path p = Paths.get(System.getProperty("user.dir")+"/savefiles");
            fileChooser.setInitialDirectory(new File(p.toString()));

            File save = fileChooser.showOpenDialog(new Stage());
            if(save!=null){
                FileInputStream fileInput = new FileInputStream(save);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);
                return (Bot)objectInput.readObject();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Laden eines Speicherstandes
    public static Game loadData(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");

            fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".ser", "*.*"));

            Path p = Paths.get(System.getProperty("user.dir")+"/savefiles");
            fileChooser.setInitialDirectory(new File(p.toString()));

            File save = fileChooser.showOpenDialog(new Stage());
            if(save!=null){
                FileInputStream fileInput = new FileInputStream(save);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);
                return (Game)objectInput.readObject();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }//loadData



}//Save Class
