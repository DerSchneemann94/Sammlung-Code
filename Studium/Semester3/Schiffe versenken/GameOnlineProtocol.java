package battleships;

import javafx.application.Platform;

public class GameOnlineProtocol {
    private int status = 0;
    String Ausgabe = null;
    Controller controller;
    GameNetwork gameNetwork;

    GameOnlineProtocol(Controller controller, GameNetwork gameNetwork)
    {
        this.controller = controller;
        this.gameNetwork = gameNetwork;
    }



    // Das "Protokoll" braucht man für die 2 Spieler kommunikation
    public String send(String Eingabe) {
        switch (Eingabe){
            case "next":
                Ausgabe = "next";
                break;
            case "done":
                Ausgabe = "done";
                break;
            case "answer":
                Ausgabe = "answer";
                break;

        }



        controller.game.myTurn = !controller.game.myTurn;
        return Ausgabe;
    }

    public String receive(String Eingabe){
        if (Eingabe==null){
            controller.board.setEnemyShot("2");
            return null;
        }
        String[] splitted = Eingabe.split(" ");

        switch (splitted[0]){
            case "size":
                controller.fieldsize = Integer.parseInt(splitted[1]);

                break;
            case "ships":
                controller.ships = new int[4];
                for (int i=1; i<splitted.length;i++){

                    controller.ships[Integer.parseInt(splitted[i])-2]++;
                }
//                System.out.println(Arrays.toString(splitted));
                break;
            case "shot":
//                Unit u = controller.game.enemyField[Integer.parseInt(splitted[1])-1][Integer.parseInt(splitted[2])-1];
                int response = controller.game.setEnemyShot(Integer.parseInt(splitted[1])-1, Integer.parseInt(splitted[2])-1);
                if (response==2){
                    controller.game.playerShipsDestroyed++;

                }
                controller.board.colourRightField(Integer.parseInt(splitted[1])-1, Integer.parseInt(splitted[2])-1, response);
                System.out.println(response+" <- Response Wert");
                gameNetwork.answerToEnemy=true;
                gameNetwork.waiting=false;
                gameNetwork.skipNextShooting=false;

                return ""+response;

            case "answer":
                switch (splitted[1]){
                    case "0":
                        controller.board.setEnemyShot(splitted[1]);
                        gameNetwork.shotAllowed = false;
                        gameNetwork.skipNextShooting = true;
                        //shotflag false -> das
                        break;
                    case "1":
                        controller.board.setEnemyShot(splitted[1]);
                        gameNetwork.shotAllowed = true;
                        gameNetwork.waiting=true;
                        //shotflag true
                        break;
                    case "2":
                        controller.game.enemyShipsDestroyed++;
                        controller.board.setEnemyShot(splitted[1]);
                        gameNetwork.shotAllowed = true;
                        gameNetwork.waiting = true;
                        //shotflag true
                        break;
                }


                break;
            case "save":
                Platform.runLater(() -> {
                    SaveLoad.saveData(controller.game, splitted[1]);});
                gameNetwork.doneFlag = true;
                gameNetwork.waiting = false;
                controller.game.firstShotAfterLoading=false;
                while (SaveLoad.waitingForSave){
                    Thread.onSpinWait();
                }
                break;
            case "load":
                return "load";

            case "next":
                gameNetwork.shotAllowed=true;
                gameNetwork.waiting=true;

                break;
            case "done":


                break;
            case "ready":


                break;

        }
        Ausgabe = Eingabe;
        return Ausgabe;
    }

    public String botReceive(String Eingabe){
        if (Eingabe==null){
            controller.board.setEnemyBotShot("2");
            return null;
        }
        String[] splitted = Eingabe.split(" ");

        switch (splitted[0]){
            case "size":
                controller.fieldsize = Integer.parseInt(splitted[1]);

                break;
            case "ships":
                controller.ships = new int[4];
                for (int i=1; i<splitted.length;i++){

                    controller.ships[Integer.parseInt(splitted[i])-2]++;
                }
                break;
            case "shot":
                int response = gameNetwork.bot.botCheckForHit(new int[]{Integer.parseInt(splitted[1])-1, Integer.parseInt(splitted[2])-1},gameNetwork.bot.botfield);
                if (response==3){
                    gameNetwork.gameLost = true;
                    response = 2;
                }

                controller.board.colourRightFieldBot(Integer.parseInt(splitted[1])-1, Integer.parseInt(splitted[2])-1, response);
                System.out.println(response+" <- Response Wert");
                gameNetwork.answerToEnemy=true;
                gameNetwork.waiting=false;
                gameNetwork.skipNextShooting=false;

                gameNetwork.bot.printField(gameNetwork.bot.botfield);
                System.out.println("______________");

                return ""+response;

            case "answer":
                switch (splitted[1]){
                    case "0":
                        controller.board.setEnemyBotShot(splitted[1]);
                        gameNetwork.shotAllowed = false;
                        gameNetwork.skipNextShooting = true;
                        //shotflag false -> das
                        break;
                    case "1":
                        controller.board.setEnemyBotShot(splitted[1]);
                        gameNetwork.shotAllowed = true;
                        gameNetwork.waiting=true;
                        //shotflag true
                        break;
                    case "2":
                        controller.board.setEnemyBotShot(splitted[1]);
                        gameNetwork.shotAllowed = true;
                        gameNetwork.waiting = true;
                        gameNetwork.bot.sunkEnemyShips++;
                        if (gameNetwork.bot.sunkEnemyShips >= gameNetwork.bot.ships.length){
                            gameNetwork.waiting=false;
                            gameNetwork.gameWon = true;
                        }
                        System.out.println("versenkte SChiffe: " + gameNetwork.bot.sunkEnemyShips + "| GesamtZahl Schiffe: " + gameNetwork.bot.ships.length);
                        //shotflag true
                        break;
                }

                gameNetwork.bot.printField(gameNetwork.bot.enemyfield);
                System.out.println("______________");

                break;
            case "save":
                Platform.runLater(() -> {
                    SaveLoad.saveBotData(gameNetwork.bot, splitted[1]);});
                gameNetwork.doneFlag = true;
                gameNetwork.waiting = false;
                gameNetwork.bot.firstShotAfterLoading=false;
                while (SaveLoad.waitingForSave){
                    Thread.onSpinWait();
                }
                break;
            case "load":
                return "load";

            case "next":
                gameNetwork.shotAllowed=true;
                gameNetwork.waiting=true;

                break;
            case "done":


                break;
            case "ready":


                break;

        }
        Ausgabe = Eingabe;
        return Ausgabe;
    }

}