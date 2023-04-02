package battleships;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//Der Controller steuert alle Aktionen der GUI
public class Controller {
    @FXML public Slider sliderSPdifficulty, sliderMPdifficulty, sliderMPrecivedifficulty, sliderVolume;
    @FXML public TextField textSPFieldSize, textFieldSPtwo, textFieldSPthree, textFieldSPfour, textFieldSPfive, textPort, textIP, textMPFieldSize, textFieldMPtwo, textFieldMPthree, textFieldMPfour, textFieldMPfive;
    @FXML public Button btnLoad, btnOptions, btnSPPlay, btnSPLoad, btnSingleplayer, btnMultiplayer, btnClose, btnHost, btnConnect, btnMPSettings, btnIP, btnToPlacement, btnNext, btnDone, btnBackToMenu, btnSPRotate, btnSPPlace, btnSPReady;
    @FXML public Label lblEasyMPSettings, lblMediumMPSettings, lblHardMPSettings, lblMPSettingsDifficulty, lblEasyMPreciveSettings, lblMediumMPreciveSettings, lblHardMPreciveSettings, lblMPreceiveSettingsDifficulty, lblIP, lblReceive, lblSPEasy, lblSPMedium, lblSPHard, lblSPShipAmount, lblMPClientGamemode;
    @FXML public ToggleButton btnMPClientGamemode, btnMPSettingsGamemode, btnMusic;
    @FXML public Spinner spinnerSPShipSize;
    @FXML public HBox boxSPPlace;
    @FXML public Pane paneSPPlace;

    public Game game;
    public Board board;
    public Stage stage;
    public Parent root;
    public GameHost gameHost;
    public GameClient gameClient;
    public GameNetwork gameNetwork;
    public Clip clip;
    public Singleplayer singleplayer;
    public int fieldsize, shotX=0, shotY=0, sliderValue = 0;
    public int[] ships, fleet;
    public volatile boolean host, saveFlag=false, asKi, SP;

    public Controller(){

    }

    //Startmenü Buttons
    public void setMenuButtons(){
        btnSingleplayer.setOnAction(event -> {goToSingleplayerSettings();});
        btnMultiplayer.setOnAction(event -> {goToMPRoles();});
        btnClose.setOnAction(event -> {closeGame();});
        btnOptions.setOnAction(event -> {goToOptions();});
    }

    //Singleplayermenü
    public void goToSingleplayerSettings(){
        //FXML öffnen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/SPSettings.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Szene erzeugen und setzen
        Scene scene = new Scene(root);
        stage.setScene(scene);

        SP = true;
        btnSPPlay.setOnAction(event -> goToSPPlacementScene());
        //btnSPLoad.setOnAction(event -> loadGame());

        //Slider-Befehle
        sliderSPdifficulty.setOnMouseClicked(mouseEvent -> moveSlider(sliderSPdifficulty, lblSPEasy, lblSPMedium, lblSPHard));
        sliderSPdifficulty.setOnMouseDragged(mouseEvent -> moveSlider(sliderSPdifficulty, lblSPEasy, lblSPMedium, lblSPHard));
        sliderSPdifficulty.setOnMouseReleased(mouseEvent -> moveSlider(sliderSPdifficulty, lblSPEasy, lblSPMedium, lblSPHard));
        sliderSPdifficulty.setOnMouseEntered(mouseEvent -> moveSlider(sliderSPdifficulty, lblSPEasy, lblSPMedium, lblSPHard));
        sliderSPdifficulty.setOnMousePressed(mouseEvent -> moveSlider(sliderSPdifficulty, lblSPEasy, lblSPMedium, lblSPHard));

        //Ausgewählte Settings einlesen
        fieldsize = Integer.parseInt(textSPFieldSize.getText());
        ships = new int[]{Integer.parseInt(textFieldSPtwo.getText()), Integer.parseInt(textFieldSPthree.getText()),Integer.parseInt(textFieldSPfour.getText()), Integer.parseInt(textFieldSPfive.getText())};
    }//goToSingleplayerSettings

    //Multiplayermenü
    public void goToMPRoles(){
        double height = stage.getHeight();
        double width = stage.getWidth();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/MPRole.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnHost.setOnAction(event -> {hostGame();});
        btnConnect.setOnAction(event -> {connectToHost();});

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(height);
        stage.setWidth(width);
    }//goToMPRole

    //Spiel laden
    public void loadGame(){
        ToggleButton btn;
        if (host){
            btn = btnMPSettingsGamemode;
        }else {
            btn = btnMPClientGamemode;
        }
        if (btn.isSelected()){
            gameNetwork.bot = SaveLoad.loadBotData();
            if (gameNetwork.bot != null){
                board = new Board(gameNetwork.bot.size, null, this, gameNetwork.bot);
                board.createLoadingBotGamePlayingScene();
                asKi = true;
            }
        }else {
            game = SaveLoad.loadData();
            if (game != null) {
                board = new Board(game.fieldsize, game, this, null);
                board.createLoadingGamePlayingScene();
            }
        }
        Scene scene = new Scene(board.getPlayingScene(), board.getPlayingScene().getWidth(), board.getPlayingScene().getHeight());
        stage.setScene(scene);
        SaveLoad.loadingDone = true;
    }

    //Spiel schließen
    public void closeGame(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void goToSPPlacementScene(){

        double height = stage.getHeight();
        double width = stage.getWidth();

            System.out.println("1");
            ships = new int[]{Integer.parseInt(textFieldSPtwo.getText()), Integer.parseInt(textFieldSPthree.getText()),Integer.parseInt(textFieldSPfour.getText()), Integer.parseInt(textFieldSPfive.getText())};
            fieldsize = Integer.parseInt(textSPFieldSize.getText());

            ArrayList<Integer> dummy = new ArrayList<Integer>();
            for (int i = ships.length - 1; i >= 0; i--) {
                for (int j = 0; j < ships[i]; j++) {
                    dummy.add(i + 2);
                }
            }
            fleet = dummy.stream().mapToInt(i -> i).toArray();

            Bot bot = null;

            switch (sliderValue) {
                case 0:
                    bot = new EasyBot(fieldsize, fleet);
                    System.out.println("Easy");
                    break;
                case 1:
                    bot = new MedBot(fieldsize, fleet);
                    System.out.println("Medium");
                    break;
                case 2:
                    //bot = new hardBot(fieldsize, fleet);
                    break;
            }
            System.out.println("2");
            game = new Game(fieldsize, ships);
            board = new Board(fieldsize, game, this, null);

            board.setAmountOfShips(ships[0], ships[1], ships[2], ships[3]);
            game.amountOfShips = board.getAmountOfShips();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/SPPlacement.fxml"));
            loader.setController(this);
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            board.createNewSPPlacementScene(paneSPPlace, boxSPPlace, btnSPPlace, btnSPRotate, btnSPReady, lblSPShipAmount);

            singleplayer = new Singleplayer(fieldsize, ships, board, game, bot, this);
            System.out.println("singleplayer " + singleplayer);



            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(false);

            stage.setHeight(height);
            stage.setWidth(width);
    }

    //Spiel starten
    public void goToPlacementScene(){

            if (host) {
                ships = new int[]{Integer.parseInt(textFieldMPtwo.getText()), Integer.parseInt(textFieldMPthree.getText()), Integer.parseInt(textFieldMPfour.getText()), Integer.parseInt(textFieldMPfive.getText())};
                fieldsize = Integer.parseInt(textMPFieldSize.getText());
                asKi = btnMPSettingsGamemode.isSelected();
            } else {
                asKi = btnMPClientGamemode.isSelected();
            }
            System.out.println("aski1:" + asKi);
            if (asKi) {
                System.out.println("--KI--");
                ArrayList<Integer> dummy = new ArrayList<Integer>();
                for (int i = ships.length - 1; i >= 0; i--) {
                    for (int j = 0; j < ships[i]; j++) {
                        dummy.add(i + 2);
                    }
                }
                fleet = dummy.stream().mapToInt(i -> i).toArray();

                switch (sliderValue) {
                    case 0:
                        gameNetwork.bot = new EasyBot(fieldsize, fleet);
                        System.out.println("Easy");
                        break;
                    case 1:
                        gameNetwork.bot = new MedBot(fieldsize, fleet);
                        System.out.println("Medium");
                        break;
                    case 2:
                        //gameNetwork.bot = new hardBot(fieldsize, fleet);
                        break;
                }

                board = new Board(fieldsize, null, this, gameNetwork.bot);
                board.createNewGameBotPlacementScene();
            } else {
                game = new Game(fieldsize, ships);
                board = new Board(fieldsize, game, this, null);
                board.setAmountOfShips(ships[0], ships[1], ships[2], ships[3]);
                game.amountOfShips = board.getAmountOfShips();
                board.createNewGamePlacementScene();
                stage.setResizable(true);
            }

        Scene scene = new Scene(board.getPlacementScene(), board.getPlacementScene().getWidth(), board.getPlacementScene().getHeight());
        stage.setScene(scene);
        stage.setMaximized(false);

        System.out.println("6");
    }//goToPlacementScene

    public void changeAsKiLabel(){
        if (host) {
            if (btnMPSettingsGamemode.isSelected()) {
                btnMPSettingsGamemode.setText("Ki vs Ki");
                lblMPSettingsDifficulty.setVisible(true);
                lblEasyMPSettings.setVisible(true);
                lblMediumMPSettings.setVisible(true);
                lblHardMPSettings.setVisible(true);
                sliderMPdifficulty.setVisible(true);
                sliderMPdifficulty.setDisable(false);
            } else {
                btnMPSettingsGamemode.setText("Player vs Player");
                lblMPSettingsDifficulty.setVisible(false);
                lblEasyMPSettings.setVisible(false);
                lblMediumMPSettings.setVisible(false);
                lblHardMPSettings.setVisible(false);
                sliderMPdifficulty.setVisible(false);
                sliderMPdifficulty.setDisable(true);
            }
        }else {
            if (btnMPClientGamemode.isSelected()) {
                btnMPClientGamemode.setText("Ki vs Ki");
                lblMPreceiveSettingsDifficulty.setVisible(true);
                lblEasyMPreciveSettings.setVisible(true);
                lblMediumMPreciveSettings.setVisible(true);
                lblHardMPreciveSettings.setVisible(true);
                sliderMPrecivedifficulty.setVisible(true);
                sliderMPrecivedifficulty.setDisable(false);
            } else {
                btnMPClientGamemode.setText("Player vs Player");
                lblMPreceiveSettingsDifficulty.setVisible(false);
                lblEasyMPreciveSettings.setVisible(false);
                lblMediumMPreciveSettings.setVisible(false);
                lblHardMPreciveSettings.setVisible(false);
                sliderMPrecivedifficulty.setVisible(false);
                sliderMPrecivedifficulty.setDisable(true);
            }
        }
    }

    //Zur Spielszene gehen
    public void goToPlayingScene(){
        if (asKi){
            board.createNewBotPlayingScene();
        }else {
            board.createNewGamePlayingScene();
        }
        Scene scene = new Scene(board.getPlayingScene(), board.getPlayingScene().getWidth(), board.getPlayingScene().getHeight());
        stage.setScene(scene);

    }

    //Schwierigkeitsslider definiert
    public void moveSlider(Slider slider, Label lblE, Label lblM, Label lblH) {

        //Listener der bei Änderung des Sliderwerts aufgerufen wird

        slider.valueProperty().addListener((observableValue, t, t1) -> {
            double value = (double) t1;
            if (value <= 0.5){
                sliderValue = 0;
            }else if (value <= 1.5){
                sliderValue = 1;
            }else{
                sliderValue = 2;
            }
            //Farbe wird geändert, je nach Schwierigkeit um zu zeigen, welches ausgewählt ist
            switch (sliderValue) {
                case 1 -> {
                    lblE.setTextFill(Color.WHITE);
                    lblM.setTextFill(Color.RED);
                    lblH.setTextFill(Color.WHITE);
                }
                case 2 -> {
                    lblE.setTextFill(Color.WHITE);
                    lblM.setTextFill(Color.WHITE);
                    lblH.setTextFill(Color.RED);
                }
                default -> {
                    lblE.setTextFill(Color.RED);
                    lblM.setTextFill(Color.WHITE);
                    lblH.setTextFill(Color.WHITE);
                }
            }
        });

}

    //IP erhalten
    public void getIP(){
        String IPv4 = null;
        try {
            IPv4 = ""+ Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        lblIP.setVisible(true);
        lblIP.setText(""+IPv4);
    }

    //Zum Spielhost werden
    public void hostGame(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/MPSettings.fxml"));

        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sliderMPdifficulty.setOnMouseClicked(mouseEvent -> moveSlider(sliderMPdifficulty, lblEasyMPSettings, lblMediumMPSettings, lblHardMPSettings));
        sliderMPdifficulty.setOnMouseDragged(mouseEvent -> moveSlider(sliderMPdifficulty, lblEasyMPSettings, lblMediumMPSettings, lblHardMPSettings));
        sliderMPdifficulty.setOnMouseReleased(mouseEvent -> moveSlider(sliderMPdifficulty, lblEasyMPSettings, lblMediumMPSettings, lblHardMPSettings));
        sliderMPdifficulty.setOnMouseEntered(mouseEvent -> moveSlider(sliderMPdifficulty, lblEasyMPSettings, lblMediumMPSettings, lblHardMPSettings));
        sliderMPdifficulty.setOnMousePressed(mouseEvent -> moveSlider(sliderMPdifficulty, lblEasyMPSettings, lblMediumMPSettings, lblHardMPSettings));

        Scene scene = new Scene(root);
        stage = (Stage) btnHost.getScene().getWindow();
        stage.setScene(scene);

        btnMPSettings.setOnAction(event -> sendSettings());
        btnLoad.setOnAction(event -> {gameHost.loadingFlag=true;gameHost.waiting=false;});
        btnIP.setOnAction(event -> {getIP();});
        btnToPlacement.setOnAction(event -> {goToPlacementScene();});
        btnMPSettingsGamemode.setOnAction(event -> changeAsKiLabel());

        if (!textPort.getText().isEmpty()){
            host=true;
            gameHost = new GameHost(textPort.getText(), false, this);

            gameNetwork = gameHost;

            Thread hostThread = new Thread(gameHost);
            hostThread.setDaemon(true);
            hostThread.start();
        }else {
            System.out.println("Port nicht vergeben");
        }

    }//hostGame

    //Startet Client der sich zum Host verbindet
    public void connectToHost(){
        if (!textIP.getText().isEmpty()){
            host=false;
            gameClient = new GameClient(textPort.getText(), textIP.getText(), this);

            gameNetwork = gameClient;

            Thread clientThread = new Thread(gameClient);
            clientThread.setDaemon(true);
            clientThread.start();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/MPReceiveSettings.fxml"));
            loader.setController(this);
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sliderMPrecivedifficulty.setOnMouseClicked(mouseEvent -> moveSlider(sliderMPrecivedifficulty, lblEasyMPreciveSettings, lblMediumMPreciveSettings, lblHardMPreciveSettings));
            sliderMPrecivedifficulty.setOnMouseDragged(mouseEvent -> moveSlider(sliderMPrecivedifficulty, lblEasyMPreciveSettings, lblMediumMPreciveSettings, lblHardMPreciveSettings));
            sliderMPrecivedifficulty.setOnMouseReleased(mouseEvent -> moveSlider(sliderMPrecivedifficulty, lblEasyMPreciveSettings, lblMediumMPreciveSettings, lblHardMPreciveSettings));
            sliderMPrecivedifficulty.setOnMouseEntered(mouseEvent -> moveSlider(sliderMPrecivedifficulty, lblEasyMPreciveSettings, lblMediumMPreciveSettings, lblHardMPreciveSettings));
            sliderMPrecivedifficulty.setOnMousePressed(mouseEvent -> moveSlider(sliderMPrecivedifficulty, lblEasyMPreciveSettings, lblMediumMPreciveSettings, lblHardMPreciveSettings));

            btnNext.setOnAction(event -> gameClient.printNext());
            btnDone.setOnAction(event -> {gameClient.printDone(); goToPlacementScene();});
            btnMPClientGamemode.setOnAction(event -> changeAsKiLabel());

            btnDone.setDisable(true);
            btnNext.setDisable(true);

            Scene scene = new Scene(root);
            stage = (Stage) btnHost.getScene().getWindow();
            stage.setScene(scene);
        }else {
            System.out.println("Client Port oder IP fehlt");
        }
    }//connectToHost

    //Übergebe Multiplayer Settings zum Client
    public void sendSettings(){
        gameHost.fieldsize = textMPFieldSize.getText();
        gameHost.ships = new String[]{textFieldMPtwo.getText(), textFieldMPthree.getText(), textFieldMPfour.getText(), textFieldMPfive.getText()};
        gameHost.waiting = false;
    }

    //Flag
    public void setPlacementFlag(){
        if (SP){
            singleplayer.placement = false;
            return;
        }
        gameNetwork.placement = false;
    }//setPlacementFlag

    //Man gelangt in die Einstellungen des Spiels für Sound und Fenstergröße
    public void goToOptions(){
        //FXML öffnen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/Options.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        btnWindowMode.setVisible(false);
//        btnFullscreen.setVisible(false);

        btnMusic.setOnAction(event -> {toggleMusic();});
        btnBackToMenu.setOnAction(event -> {goBackToMenu();});
//        btnFullscreen.setOnAction(event -> {goToFullscreen();});
//        btnWindowMode.setOnAction(event -> {goToWindowMode();});

        //Slider ändert die Lautstärke
        sliderVolume.setOnMouseClicked(mouseEvent -> changeVolume());
        sliderVolume.setOnMouseDragged(mouseEvent -> changeVolume());
        sliderVolume.setOnMouseReleased(mouseEvent -> changeVolume());
        sliderVolume.setOnMouseEntered(mouseEvent -> changeVolume());
        sliderVolume.setOnMousePressed(mouseEvent -> changeVolume());

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }//goToOptions

    //Entscheidet ob Musik überhaupt laufen soll oder nicht
    public void toggleMusic(){
        //Falls nicht, dann wird die Musik gestoppt
        if(!btnMusic.isSelected()){
            try{
                if(clip != null) {
                    clip.stop();
                    clip.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else {
            //Wenn man jedoch den Button klickt, fängt die Musik an
            Path p = Paths.get(System.getProperty("user.dir")+"/src/music");
            AudioInputStream audio = null;
            try {
                audio = AudioSystem.getAudioInputStream(new File(p.toString()+"/grillen.wav"));
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(audio);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-40.0f);
        }
    }//toggleMusic

    //Ändert die Lautstärke der Musik über den Slider
    public void changeVolume(){
        sliderVolume.valueProperty().addListener((observableValue, t, t1) -> {
            double value = (double) t1;
            if (value <= 0.5){
                sliderValue = 0;
            }else if (value <= 1.5){
                sliderValue = 1;
            }else if (value <= 2.5){
                sliderValue = 2;
            }else if (value <= 3.5){
                sliderValue = 3;
            }else{
                sliderValue = 4;
            }
            switch (sliderValue) {
                case 0 -> {
                    if(btnMusic.isSelected()){
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-60.0f);
                    }
                }
                case 1 -> {
                    if(btnMusic.isSelected()){
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-50.0f);
                    }
                }
                case 3 -> {
                    if(btnMusic.isSelected()){
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-30.0f);
                    }
                }
                case 4 -> {
                    if(btnMusic.isSelected()){
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-20.0f);
                    }
                }
                default -> {
                    if(btnMusic.isSelected()){
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-40.0f);
                    }
                }
            }
        });
    }//changeVolume

    public void goBackToMenu(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/battleships/Startmenu.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = btnBackToMenu.getScene();
        scene.setRoot(root);
        setMenuButtons();
    }

}//Controller Ende
