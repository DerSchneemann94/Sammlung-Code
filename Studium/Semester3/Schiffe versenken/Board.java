package battleships;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Screen;

//Board erstellt die Platzierungs- und Spielszenen
public class Board {
    private Pane box, container;
    private Button[][] leftFieldButtons, rightFieldButtons;
    private Region[][] reg;
    private String stringAmountOfShips;
    private int fieldsize, shiplength=2, placementX, placementY, amountOfShips=0;
    private int[] amountOfEveryShipType;
    private Label lblAmountOfShips;
    public Label gameOver;
    private Game game;
    public boolean directionHorizontal;
    private Controller controller;
    private Button ready, save, botTurn;
    public GridPane field,elements;
    private Bot bot;


    public Board(int fieldsize, Game game, Controller controller, Bot bot) {
        this.fieldsize = fieldsize;
        this.game = game;
        this.controller = controller;
        this.bot = bot;
    }

    public void createNewSPPlacementScene(Pane pane, HBox box, Button place, Button rotate, Button ready, Label AmountOfShips){
        field = new GridPane();

        lblAmountOfShips = AmountOfShips;

        //Erstellt die verschiedenen UI-Elemente und deren Eigenschaften
        AmountOfShips.setText("Ship Amount: " + stringAmountOfShips);

        rotate.setOnAction(event -> {directionHorizontal = !directionHorizontal; testShipPlacement();});

        place.setOnAction(event -> {placeShip();});

        this.ready = ready;
        ready.setDisable(true);
        ready.setOnAction(event -> {controller.setPlacementFlag();});

        Spinner<Integer> spinner = new Spinner<Integer>(2,5,2);

        spinner.setLayoutX(135.0);
        spinner.setLayoutY(35.0);
        spinner.setPrefHeight(25.0);
        spinner.setPrefWidth(70);

        spinner.getValueFactory().setValue(shiplength);
        Label finalAmountOfShips = AmountOfShips;
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> finalAmountOfShips.setText("Ship Amount: " + (stringAmountOfShips = ""+ amountOfEveryShipType[(int) newValue-2])));
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> shiplength= (int) newValue);
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> testShipPlacement());


        //Buttons für das linke Spielfeld zum Setzen der Schiffe
        leftFieldButtons = new Button[fieldsize][fieldsize];
        for(int x = 0; x < fieldsize; x++) {
            for (int y = 0; y < fieldsize; y++) {
                field.add(leftFieldButtons[x][y] = new Button(), x, y);
                leftFieldButtons[x][y].setPrefHeight(Screen.getPrimary().getBounds().getHeight()/ fieldsize);
                leftFieldButtons[x][y].setPrefWidth((Screen.getPrimary().getBounds().getWidth()-pane.getWidth())/ fieldsize);
                int finalX = x;
                int finalY = y;
                leftFieldButtons[x][y].setOnAction(event -> {testShipPlacement(finalX, finalY);});
                leftFieldButtons[x][y].setStyle("-fx-base: aqua;");
            }
        }

        pane.getChildren().addAll(spinner);
        box.getChildren().addAll(field);

    }

    //Erstellt die Szene für das Platzieren der Schiffe
    public void createNewGamePlacementScene(){
        System.out.println("Hello");
        field = new GridPane();
        elements = new GridPane();

        //Erstellt die verschiedenen UI-Elemente und deren Eigenschaften
        lblAmountOfShips = new Label("Ship Amount: "+ stringAmountOfShips);

        Button rotate = new Button("Rotate");
        rotate.setOnAction(event -> {directionHorizontal = !directionHorizontal; testShipPlacement();});

        Button place = new Button("Place");
        place.setOnAction(event -> {placeShip();});

        ready = new Button("Ready");
        ready.setDisable(true);
        ready.setOnAction(event -> {controller.setPlacementFlag();});

        Spinner<Integer> spinner = new Spinner<Integer>(2, 5, 2);
        spinner.getValueFactory().setValue(shiplength);
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> lblAmountOfShips.setText("Ship Amount: " + (stringAmountOfShips = ""+ amountOfEveryShipType[newValue-2])));
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> shiplength= newValue);
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> testShipPlacement());
        elements.setMinWidth(100);

        container = new HBox();
        container.getChildren().addAll(elements, field);


        elements.add(new Label("Ship Size"), 0,1);
        elements.add(spinner, 0,3);
        elements.add(lblAmountOfShips, 0,4);
        elements.add(rotate, 0,5);
        elements.add(place, 0, 6);
        elements.add(ready,0,10);

        System.out.println("Hello THERE");
        System.out.println("Fieldsize: " + fieldsize);

        //Buttons für das linke Spielfeld zum Setzen der Schiffe
        leftFieldButtons = new Button[fieldsize][fieldsize];
        for(int x = 0; x < fieldsize; x++) {
            for (int y = 0; y < fieldsize; y++) {
                field.add(leftFieldButtons[x][y] = new Button(), x, y);
                leftFieldButtons[x][y].setPrefHeight(Screen.getPrimary().getBounds().getHeight()/ fieldsize);
                leftFieldButtons[x][y].setPrefWidth((Screen.getPrimary().getBounds().getWidth()-elements.getWidth())/ fieldsize);
                int finalX = x;
                int finalY = y;
                leftFieldButtons[x][y].setOnAction(event -> {testShipPlacement(finalX, finalY);});
                leftFieldButtons[x][y].setStyle("-fx-base: aqua;");
            }
        }
        System.out.println("Hello THERE GENERAL");
    }

    public void createNewGameBotPlacementScene(){

        field = new GridPane();
        elements = new GridPane();

        Button place = new Button("Replace");
        place.setOnAction(event -> {setBotGameReadyButton();});

        ready = new Button("Ready");
        ready.setOnAction(event -> {controller.setPlacementFlag();});

        if (!controller.gameNetwork.bot.botPlace()){
            ready.setDisable(true);
        }

        elements.setMinWidth(80);

        container = new HBox();
        container.getChildren().addAll(elements, field);

        elements.add(place, 0, 6);
        elements.add(ready,0,10);

        //Buttons für das linke Spielfeld zum Setzen der Schiffe
        leftFieldButtons = new Button[fieldsize][fieldsize];
        colorBotPlacement();

    }

    public void setBotGameReadyButton(){
        boolean replaceSuccses;
        ready.setDisable(true);

        replaceSuccses = controller.gameNetwork.bot.botPlace();

        if (replaceSuccses){
            ready.setDisable(false);
            colorBotPlacement();
        }
    }

    public void colorBotPlacement(){
        for(int x = 0; x < fieldsize; x++) {
            for (int y = 0; y < fieldsize; y++) {
                field.add(leftFieldButtons[x][y] = new Button(), x, y);
                leftFieldButtons[x][y].setPrefHeight(Screen.getPrimary().getBounds().getHeight()/ fieldsize);
                leftFieldButtons[x][y].setPrefWidth((Screen.getPrimary().getBounds().getWidth()-elements.getWidth())/ fieldsize);
                if (controller.gameNetwork.bot.botfield[x][y] == 0){
                    leftFieldButtons[x][y].setStyle("-fx-base: aqua;-fx-background-color: aqua; -fx-border-color: black");
                }
                if (controller.gameNetwork.bot.botfield[x][y] == 1){
                    leftFieldButtons[x][y].setStyle("-fx-base: black;");
                }
            }
        }
    }

    public void createNewBotPlayingScene() {
        GridPane leftField = new GridPane();
        GridPane rightField = new GridPane();
        VBox menu = new VBox();

        //Erstellt die verschiedenen UI-Elemente und deren Eigenschaften
        box = new HBox();
        box.getChildren().addAll(leftField, menu, rightField);

        leftFieldButtons = new Button[fieldsize][fieldsize];
        rightFieldButtons = new Button[fieldsize][fieldsize];

        save = new Button("Save");
        save.setMinWidth(80);
        save.setOnAction(event -> {
            if (bot.myTurn){
                controller.saveFlag = true;
                controller.gameNetwork.waiting = false;
            }});

        botTurn = new Button("Bot Turn");
        botTurn.setOnAction(event -> {
            if (controller.gameNetwork.bot.myTurn){
                controller.gameNetwork.waiting = false;
            }
        });

        gameOver = new Label();
        gameOver.setVisible(false);
        gameOver.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(save,botTurn,gameOver);

        double buttonHeight = Screen.getPrimary().getBounds().getHeight()/ fieldsize;
        double buttonWidth = ((Screen.getPrimary().getBounds().getWidth() - menu.getWidth())/(2* fieldsize));

        //Buttons für das linke Spielfeld zum Beschießen des gegnerischen Spielfelds
        for(int x = 0; x < fieldsize; x++){
            for (int y = 0; y < fieldsize; y++){
                leftField.add(leftFieldButtons[x][y] = new Button(),x,y);
                leftFieldButtons[x][y].setPrefHeight(buttonHeight);
                leftFieldButtons[x][y].setPrefWidth(buttonWidth);
                leftFieldButtons[x][y].setStyle("-fx-base: aqua;");
            }
        }
        Region[][] reg = new Region[fieldsize][fieldsize];

        for (int x = 0; x < fieldsize; x++){
            for (int y = 0; y < fieldsize; y++){
                rightField.add(reg[x][y] = new Region(),x,y);
                reg[x][y].setPrefHeight(buttonHeight);
                reg[x][y].setPrefWidth(buttonWidth);
                reg[x][y].setStyle("-fx-base: aqua;-fx-border-color: black;");

                if (bot.botfield[x][y] == 1){
                    reg[x][y].setStyle("-fx-base: black;");
                }
            }
        }

        //Deaktivierte Buttons zur Darstellung eigener Schiffe und gegnerischer Schüsse
        for (int x = 0; x < fieldsize; x++){
            for (int y = 0; y < fieldsize; y++){
                rightField.add(rightFieldButtons[x][y] = new Button(),x,y);
                rightFieldButtons[x][y].setPrefHeight(buttonHeight);
                rightFieldButtons[x][y].setPrefWidth(buttonWidth);
                rightFieldButtons[x][y].setStyle("-fx-base: aqua;-fx-border-color: black;");

                if (bot.botfield[x][y] == 1){
                    rightFieldButtons[x][y].setStyle("-fx-base: black;");
                }
            }
        }
        Region[][] reg2 = new Region[fieldsize][fieldsize];

        for (int x = 0; x < fieldsize; x++){
            for (int y = 0; y < fieldsize; y++){
                rightField.add(reg2[x][y] = new Region(),x,y);
                reg2[x][y].setPrefHeight(buttonHeight);
                reg2[x][y].setPrefWidth(buttonWidth);
                reg2[x][y].setStyle("-fx-base: aqua;-fx-border-color: black;");

                if (bot.botfield[x][y] == 1){
                    reg2[x][y].setStyle("-fx-base: black;");
                }
            }
        }
    }


    //Erstellt die Spielszene für ein neues Spiel
    public void createNewGamePlayingScene() {
        GridPane leftField = new GridPane();
        GridPane rightField = new GridPane();
        VBox menu = new VBox();

        //Erstellt die verschiedenen UI-Elemente und deren Eigenschaften
        box = new HBox();
        box.getChildren().addAll(leftField, menu, rightField);

        leftFieldButtons = new Button[fieldsize][fieldsize];
        rightFieldButtons = new Button[fieldsize][fieldsize];

        save = new Button("Save");
        save.setMinWidth(80);
        save.setOnAction(event -> {
            if (controller.game.myTurn){
                controller.saveFlag = true;
                controller.gameNetwork.waiting = false;
                }});


        gameOver = new Label();
        gameOver.setVisible(false);
        gameOver.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(save,gameOver);

        double buttonHeight = Screen.getPrimary().getBounds().getHeight()/ fieldsize;
        double buttonWidth = ((Screen.getPrimary().getBounds().getWidth() - menu.getWidth())/(2* fieldsize));

        //Buttons für das linke Spielfeld zum Beschießen des gegnerischen Spielfelds
        for(int x = 0; x < fieldsize; x++){
            for (int y = 0; y < fieldsize; y++){
                leftField.add(leftFieldButtons[x][y] = new Button(),x,y);
                leftFieldButtons[x][y].setPrefHeight(buttonHeight);
                leftFieldButtons[x][y].setPrefWidth(buttonWidth);
                int finalX = x;
                int finalY = y;
                leftFieldButtons[x][y].setOnAction(event -> {
                    if (controller.SP){
                        controller.singleplayer.shotX = finalX + 1;
                        controller.singleplayer.shotY = finalY + 1;
                        controller.singleplayer.shooting=true;
                        controller.singleplayer.waiting=false;

                    }else {
                        controller.shotX = finalX + 1;
                        controller.shotY = finalY + 1;
                        if (controller.game.myTurn) {
                            controller.gameNetwork.shooting = true;
                            controller.gameNetwork.waiting = false;
                        }
                    }
                });
                leftFieldButtons[x][y].setStyle("-fx-base: aqua;");
            }
        }
        //Deaktivierte Buttons zur Darstellung eigener Schiffe und gegnerischer Schüsse
        for (int i = 0; i < fieldsize; i++){
            for (int j = 0; j < fieldsize; j++){
                rightField.add(rightFieldButtons[i][j] = new Button(),i,j);
                rightFieldButtons[i][j].setPrefHeight(buttonHeight);
                rightFieldButtons[i][j].setPrefWidth(buttonWidth);
                rightFieldButtons[i][j].setStyle("-fx-base: aqua;-fx-border-color: black;");

                if (game.field[i][j] == null){
                    rightFieldButtons[i][j].setStyle("-fx-base: aqua;-fx-background-color: aqua; -fx-border-color: black");
                }else {
                    rightFieldButtons[i][j].setStyle("-fx-base: black;");
                }
            }
        }
        Region[][] reg = new Region[fieldsize][fieldsize];

        for (int x = 0; x < fieldsize; x++){
            for (int y = 0; y < fieldsize; y++){
                rightField.add(reg[x][y] = new Region(),x,y);
                reg[x][y].setPrefHeight(buttonHeight);
                reg[x][y].setPrefWidth(buttonWidth);
                reg[x][y].setStyle("-fx-base: aqua;-fx-border-color: black;");

                if (game.field[x][y] != null){
                    reg[x][y].setStyle("-fx-base: black;");
                }
            }
        }

    }//createNewGamePlayingScene

    public void createLoadingBotGamePlayingScene(){
        createNewBotPlayingScene();
        for(int x = 0; x< fieldsize; x++){
            for(int y = 0; y< fieldsize; y++){
                if (bot.enemyfield[x][y] == 1){
                    leftFieldButtons[x][y].setStyle("-fx-base: grey;");
                }
                if (bot.enemyfield[x][y] == 3) {
                    leftFieldButtons[x][y].setStyle("-fx-base: navy;");
                }
                if (bot.enemyfield[x][y] == 2) {
                    leftFieldButtons[x][y].setStyle("-fx-base: darkred;");
                }
            }
        }
        for(int x = 0; x< fieldsize; x++){
            for(int y = 0; y< fieldsize; y++){
                if(bot.botfield[x][y] == 1){
                    rightFieldButtons[x][y].setStyle("-fx-base: black;");
                }
                if (bot.botfield[x][y] == 2){
                    rightFieldButtons[x][y].setStyle("-fx-base: grey;");
                }
                if (bot.botfield[x][y] == 3) {
                    rightFieldButtons[x][y].setStyle("-fx-base: navy;");
                }
                if (bot.botfield[x][y] == 4) {
                    rightFieldButtons[x][y].setStyle("-fx-base: darkred;");
                }
            }
        }

    }

    //Kreiert das Spielfeld für ein geladenes Spiel
    public void createLoadingGamePlayingScene(){
        createNewGamePlayingScene();
        for(int x = 0; x< fieldsize; x++){
            for(int y = 0; y< fieldsize; y++){
                if (game.enemyField[x][y]!=null && game.enemyField[x][y].hit && !game.enemyField[x][y].destroyed){
                    leftFieldButtons[x][y].setStyle("-fx-base: grey;");
                }
                if (game.enemyField[x][y]!=null && game.enemyField[x][y].water) {
                    leftFieldButtons[x][y].setStyle("-fx-base: navy;");
                }
                if (game.enemyField[x][y]!=null && game.enemyField[x][y].destroyed) {
                    leftFieldButtons[x][y].setStyle("-fx-base: darkred;");
                }
            }
        }
        for(int x = 0; x< fieldsize; x++){
            for(int y = 0; y< fieldsize; y++){
                rightFieldButtons[x][y].setDisable(true);
                if((game.field[x][y])!=null && !game.field[x][y].hit && !game.field[x][y].water){
                    rightFieldButtons[x][y].setStyle("-fx-base: black;");
                }
                if (game.field[x][y]!=null && game.field[x][y].hit && !game.field[x][y].destroyed){
                    rightFieldButtons[x][y].setStyle("-fx-base: grey;");
                }
                if (game.field[x][y]!=null && game.field[x][y].water) {
                    rightFieldButtons[x][y].setStyle("-fx-base: navy;");
                }
                if (game.field[x][y]!=null && game.field[x][y].destroyed) {
                    rightFieldButtons[x][y].setStyle("-fx-base: darkred;");
                }
            }
        }

    }//createLoadingGamePlayingScene

    //getter für die Spielszene
    public Pane getPlayingScene(){
        return box;
    }

    //Speichert die jeweilige Anzahl der verschiedenen Schiffe, als auch die Gesamtzahl aller Schiffe
    public void setAmountOfShips(int size2, int size3, int size4, int size5){
        amountOfEveryShipType = new int[]{size2, size3, size4, size5};
        for (int i = 0; i <= amountOfEveryShipType.length; i++){
            if (amountOfEveryShipType[i] != 0){
                stringAmountOfShips = "" +amountOfEveryShipType[i];
                shiplength = i+2;
                break;
            }
        }
        this.amountOfShips = size2 + size3 + size4 + size5;
    }

    //getter für die Gesamtzahl der Schiffe
    public int getAmountOfShips() {
        return amountOfShips;
    }

    //Testet ob ein Schiff platziert werden kann
    public void testShipPlacement(int x, int y){
        this.placementX = x;
        this.placementY = y;
        placementSceneGridReset();
        if (amountOfEveryShipType[shiplength-2] == 0){
            return;
        }
        if ((game.placeTest(x, y, shiplength, directionHorizontal ))){
            for (int i = 0; i<shiplength; i++){
                if (directionHorizontal){
                    leftFieldButtons[x+i][y].setStyle("-fx-base: lime;");
                }else {
                    leftFieldButtons[x][y+i].setStyle("-fx-base: lime;");
                }
            }
        }else{//placeTest gibt false zurück
            if (directionHorizontal){
                for (int i = 0; i<shiplength; i++){
                    if (x+i == fieldsize){
                        break;
                    }
                    leftFieldButtons[x+i][y].setStyle("-fx-base: red;");
                }
            }else {
                for (int i = 0; i<shiplength; i++){
                    if (y+i == fieldsize){
                        break;
                    }
                    leftFieldButtons[x][y+i].setStyle("-fx-base: red;");
                }
            }
        }

    }//testShipPlacement

    //Überladenene Methode, die beim Rotieren des Schiffes benutzt wird
    public void testShipPlacement() {
        placementSceneGridReset();
        if (amountOfEveryShipType[shiplength-2] == 0){
            return;
        }
        if ((game.placeTest(this.placementX, this.placementY, shiplength, directionHorizontal))) {
            for (int i = 0; i < shiplength; i++) {
                if (directionHorizontal) {
                    leftFieldButtons[this.placementX + i][this.placementY].setStyle("-fx-base: lime;");
                } else {
                    leftFieldButtons[this.placementX][this.placementY + i].setStyle("-fx-base: lime;");
                }
            }
        } else {//placeTest gibt false zurück
            if (directionHorizontal) {
                for (int i = 0; i < shiplength; i++) {

                    if (this.placementX + i == fieldsize) {
                        break;
                    }
                    leftFieldButtons[this.placementX + i][this.placementY].setStyle("-fx-base: red;");
                }
            } else {
                for (int i = 0; i < shiplength; i++) {

                    if (this.placementY + i == fieldsize) {
                        break;
                    }
                    leftFieldButtons[this.placementX][this.placementY + i].setStyle("-fx-base: red;");
                }
            }
        }
    }

    //Platziert das Schiff endgültig
    private void placeShip(){
        if ((game.placeTest(placementX, placementY, shiplength, directionHorizontal))&& amountOfEveryShipType[shiplength-2] !=0){
            game.placeShip(placementX, placementY, shiplength, directionHorizontal, amountOfShips);
            if (directionHorizontal){
                for (int i = 0; i<shiplength; i++){
                    leftFieldButtons[this.placementX +i][this.placementY].setStyle("-fx-base: black;");
                }
            }else {
                for (int i = 0; i<shiplength; i++){
                    leftFieldButtons[this.placementX][this.placementY +i].setStyle("-fx-base: black;");
                }
            }

            amountOfEveryShipType[shiplength-2]--;
            amountOfShips--;
            lblAmountOfShips.setText("Ship Amount: " + (stringAmountOfShips = ""+ amountOfEveryShipType[shiplength-2]));
        }else{
            //Popup oder Anzeige die einem Ingame anzeigt, dass keine Schiffe der Länge verfügbar sind oder das die Position ungültig ist.
            System.out.println("Position ungültig oder keine Schiffe mehr");
        }

        //Aktivierung des Ready-Buttons, wenn alle Schiffe platziert sind
        if (amountOfShips == 0){
            ready.setDisable(false);
        }
    }//placeShip

    //getter für die Platzierungszene
    public Pane getPlacementScene(){
        return container;
    }

    //Updated das Feld in der Platzierungsszene, falls man ein Schiff woanders platzieren möchte
    private void placementSceneGridReset(){
        for (int i = 0; i< fieldsize; i++){
            for (int j = 0; j< fieldsize; j++){
                if (game.field[i][j] == null){
                    leftFieldButtons[i][j].setStyle("-fx-base: aqua;");
                }else{
                    leftFieldButtons[i][j].setStyle("-fx-base: black;");
                }
            }
        }
    }//placementSceneGridReset

    //Die Antwort die auf einen Schuss folgt. Färbt die jeweiligen Felder und updated die Game-Klasse.
    public void setEnemyShot(String answer){
        int shotX = controller.SP ? controller.singleplayer.shotX : controller.shotX;
        int shotY = controller.SP ? controller.singleplayer.shotY : controller.shotY;

        switch (answer){
            case "0": //Wasser
                leftFieldButtons[shotX-1][shotY-1].setStyle("-fx-base: navy;");
                game.enemyField[shotX-1][shotY-1] = new Unit();
                game.enemyField[shotX-1][shotY-1].water=true;
                break;
            case "1": //Treffer
                game.enemyField[shotX-1][shotY-1]= new Unit();
                game.enemyField[shotX-1][shotY-1].hit=true;
                leftFieldButtons[shotX-1][shotY-1].setStyle("-fx-base: grey;");
                break;
            case "2": //Treffer-Versenkt
                game.enemyField[shotX-1][shotY-1]= new Unit();
                game.enemyField[shotX-1][shotY-1].hit=true;
                game.enemyField[shotX-1][shotY-1].destroyed=true;
                leftFieldButtons[shotX-1][shotY-1].setStyle("-fx-base: darkred;");
                int deltaX=0, deltaY=1;
                if ((shotX-2 >= 0 && game.enemyField[shotX-2][shotY-1] != null && !game.enemyField[shotX-2][shotY-1].water) || (shotX < fieldsize &&game.enemyField[shotX][shotY-1] != null && !game.enemyField[shotX][shotY-1].water)){
                    deltaX=1;
                    deltaY=0;
                }
                int i=1;
                while (true){
                    if (shotX-1+(deltaX*i)>= fieldsize || shotY-1+deltaY*i >= fieldsize ||
                            game.enemyField[shotX-1+deltaX*i][shotY-1+deltaY*i] == null || game.enemyField[shotX-1+deltaX*i][shotY-1+deltaY*i].water ){
                        break;
                    }
                    leftFieldButtons[shotX-1+deltaX*i][shotY-1+deltaY*i].setStyle("-fx-base: darkred;");
                    game.enemyField[shotX-1+deltaX*i][shotY-1+deltaY*i].destroyed=true;
                    i++;
                }
                i=1;
                deltaX*=-1;
                deltaY*=-1;
                while (true){
                    if (shotX-1+(deltaX*i)<0 || shotY-1+deltaY*i <0 ||
                            game.enemyField[shotX-1+deltaX*i][shotY-1+deltaY*i] == null || game.enemyField[shotX-1+deltaX*i][shotY-1+deltaY*i].water){
                        break;
                    }
                    leftFieldButtons[shotX-1+deltaX*i][shotY-1+deltaY*i].setStyle("-fx-base: darkred;");
                    game.enemyField[shotX-1+deltaX*i][shotY-1+deltaY*i].destroyed=true;
                    i++;
                }
                break;
        }
    }//answer

    //Die Antwort die auf einen Schuss folgt. Färbt die jeweiligen Felder und updated die Game-Klasse.
    public void setEnemyBotShot(String answer){
        switch (answer){
            case "0": //Wasser
                leftFieldButtons[bot.BotShot[0]][bot.BotShot[1]].setStyle("-fx-base: navy;");
                bot.botSetState(0);
                break;
            case "1": //Treffer
                bot.botSetState(1);
                leftFieldButtons[bot.BotShot[0]][bot.BotShot[1]].setStyle("-fx-base: grey;");
                break;
            case "2": //Treffer-Versenkt
                bot.botSetState(2);
                System.out.println( "Botshot: " + bot.BotShot[0] + " " + bot.BotShot[1]);
                bot.enemyfield[bot.BotShot[0]][bot.BotShot[1]] = 2;
                for (int x = 0; x < bot.enemyfield.length; x++){
                    for (int y = 0; y < bot.enemyfield.length; y++){
                        if (bot.enemyfield[x][y] == 2){
                            leftFieldButtons[x][y].setStyle("-fx-base: darkred;");
                        }
                    }
                }
                int deltaX=0, deltaY=1;
                if ((bot.BotShot[0]-1 >= 0 && bot.enemyfield[bot.BotShot[0]-1][bot.BotShot[1]] != 0 && bot.enemyfield[bot.BotShot[0]-1][bot.BotShot[1]] != 3) || (bot.BotShot[0]+1 < fieldsize && bot.enemyfield[bot.BotShot[0]+1][bot.BotShot[1]] != 0 && bot.enemyfield[bot.BotShot[0]+1][bot.BotShot[1]] != 3)) {
                    deltaX=1;
                    deltaY=0;
                }
                int i=1;
                while (true){
                    if (bot.BotShot[0]+(deltaX*i)>= fieldsize || bot.BotShot[1]+deltaY*i >= fieldsize ||
                            bot.enemyfield[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i] == 0 || bot.enemyfield[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i] == 3 ){
                        break;
                    }
                    System.out.println( "Botshot: " + bot.BotShot[0] + " " + bot.BotShot[1]);
                    leftFieldButtons[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i].setStyle("-fx-base: darkred;");
                    bot.enemyfield[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i] = 2;
                    i++;
                }
                i=1;
                deltaX*=-1;
                deltaY*=-1;
                while (true){
                    if (bot.BotShot[0]+(deltaX*i)<0 || bot.BotShot[1]+deltaY*i <0 ||
                            bot.enemyfield[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i] == 0 || bot.enemyfield[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i] == 3){
                        break;
                    }
                    System.out.println( "Botshot: " + bot.BotShot[0] + " " + bot.BotShot[1]);
                    leftFieldButtons[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i].setStyle("-fx-base: darkred;");
                    bot.enemyfield[bot.BotShot[0]+deltaX*i][bot.BotShot[1]+deltaY*i] = 2;
                    i++;
                }
                break;
        }
    }

    //Das Färben gegnerischer Schüsse im rechten Feld
    public void colourRightField(int x, int y, int response){
            switch (response){
                case 0:
                    rightFieldButtons[x][y].setStyle("-fx-base: navy;");
                    break;
                case 1:
                    rightFieldButtons[x][y].setStyle("-fx-base: grey;");
                    break;
                case 2:
                    for (int xx=0;xx<game.field.length;xx++){
                        for (int yy=0;yy<game.field.length;yy++){
                            if (game.field[xx][yy] != null && game.field[xx][yy].destroyed){
                                rightFieldButtons[xx][yy].setStyle("-fx-base: darkred;");
                            }
                        }
                    }
                    break;
            }
    }//colourRightField

    public void colourRightFieldBot(int x, int y, int response){
        switch (response){
            case 0:
                rightFieldButtons[x][y].setStyle("-fx-base: navy;");
                break;
            case 1:
                rightFieldButtons[x][y].setStyle("-fx-base: grey;");
                break;
            case 2:
                for (int xx=0;xx<bot.botfield.length;xx++){
                    for (int yy=0;yy<bot.botfield.length;yy++){
                        if (bot.botfield[xx][yy]==4){
                            rightFieldButtons[xx][yy].setStyle("-fx-base: darkred;");
                        }
                    }
                }
                break;
        }
    }

}//Board Ende
