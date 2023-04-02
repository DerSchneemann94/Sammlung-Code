package battleships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

//Main
public class Main extends Application {

    public void start(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Startmenu.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Schiffe versenken!");
        Scene scene= new Scene(root,800,450);
        primaryStage.setScene(scene);


        primaryStage.setResizable(false);
        primaryStage.show();
        Controller c = loader.getController();
        c.stage = primaryStage;
        c.setMenuButtons();
    }//start

    public static void main(String[] args) {
        launch(args);
    }

}//Main Ende
