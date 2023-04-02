package battleships;

import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//Der Spielclient
public class GameClient extends GameNetwork implements Runnable {
    private String hostIP, newFromServer;
    public boolean next=false, done=false, newNext=true;


    GameClient(String port, String hostIP, Controller controller){
        this.hostIP = hostIP;
        this.port = Integer.parseInt(port);
        this.controller = controller;
    }

    //starten des Client Threads
    public void run() {
        try (
                //Neuer Socket wird erstellt
                Socket battleshipsSocket = new Socket(hostIP, port);
                //Socket outputstream
                //Um Daten auf den Socket vom Server zu schreiben braucht man PrintWriter
                PrintWriter out = new PrintWriter(battleshipsSocket.getOutputStream(), true);
                //Socket inputstream
                //Antwort vom Server wird auf BufferedReader in geschrieben
                BufferedReader in = new BufferedReader(new InputStreamReader(battleshipsSocket.getInputStream()));
        ) {
            String inputLine;
            String outputLine;
            String resultat;

            GameOnlineProtocol protocol = new GameOnlineProtocol(controller, this);

            //Unterscheidung ob neues Spiel oder ob ein altes Spiel geladen wird
            inputLine = in.readLine();
            resultat = protocol.receive(inputLine);

            controller.btnNext.setDisable(false);
            controller.lblReceive.setVisible(true);

            String finalInputLine = inputLine;
            Platform.runLater(() -> {
                controller.lblReceive.setText(finalInputLine);
            });


            if (resultat.equals("load")) {
                //Laden des gleichen Spiels wie der Host
                Platform.runLater(() -> {
                    controller.loadGame();
                });
                //Warten bis Laden fertig ist
                while (!SaveLoad.loadingDone) {
                    Thread.onSpinWait();
                }
                //Anschließend done schicken
                out.println("done");
                //Auf ready warten
                while ((inputLine = in.readLine()) != null && !inputLine.equals("ready")) {
                    Thread.onSpinWait();
                }
                //ready schicken
                out.println("ready");
                newGame = false;
            } else {
                while (inputLine != null) {

                    String finalInputLine2 = inputLine;
                    Platform.runLater(() -> {
                        controller.lblReceive.setText(finalInputLine2);
                    });

                    if (!inputLine.equals(newFromServer)) {
                        newNext = true;
                        System.out.println("Server: " + inputLine);
                    }
                    //TODO EIN NEXT TOO MUCH
                    while (true) {
                        Thread.onSpinWait();
                        if (this.next && newNext) {
                            newFromServer = inputLine;
                            outputLine = "next";
                            System.out.println("Client: " + outputLine); //TODO LÖSCHEN
                            out.println(outputLine);
                            next = false;
                            newNext = false;

                            controller.btnNext.setDisable(true);
                            controller.btnDone.setDisable(false);

                            break;
                        } else if (this.done) {
                            outputLine = "done";
                            System.out.println("Client: " + outputLine); //TODO LÖSCHEN
                            out.println(outputLine);
//                        controller.btntoPlacement.setDisable(false);
                            break;
                        }
                    }
                    if (done) {
                        break;
                    }
                    inputLine = in.readLine();
                    resultat = protocol.receive(inputLine);
                }
                // PLACEMENTSCENE
                while (placement) {
                    Thread.onSpinWait();
                }


                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("ready")) {
                        out.println("ready");
                        break;
                    }

                }
                Platform.runLater(
                        () -> controller.goToPlayingScene()
                );
            }

            if (controller.asKi){

                bot.myTurn=false;
                if (bot.firstShotAfterLoading && !newGame){
                    while ((inputLine=in.readLine())!=null && !inputLine.equals("next")){
                        Thread.onSpinWait();
                    }
                    bot.myTurn=true;
                }
                //TODO GAMELOOP
                while (true) {

                    if (bot.myTurn) {
                        while (waiting) {
                            Thread.onSpinWait();
                        }if (gameWon){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU WIN");});
                            break;
                        }else if (controller.saveFlag) {
                            bot.firstShotAfterLoading = true;
                            Platform.runLater(() -> {
                                SaveLoad.saveBotData(bot);
                            });
                            controller.saveFlag = false;
                            while (SaveLoad.waitingForSave) {
                                Thread.onSpinWait();
                            }
                            out.println("save " + SaveLoad.saveID);
                            break;
                        }else if (shotAllowed) {
                            int[] coordinates = bot.botTurn();
                            int x = coordinates[0] + 1;
                            int y = coordinates[1] + 1;
                            out.println("shot " + x + " " + y);
                            shotAllowed = false;
                        } else if (skipNextShooting) {
                            out.println("next");
                            skipNextShooting=false;
                        } else if (answerToEnemy) {
                            out.println("answer " + responseToEnemy);
                            answerToEnemy=false;
                            if (gameLost){
                                Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU LOST");});
                                System.out.println("Loose: " + responseToEnemy);
                                break;
                            }
                        }else if (doneFlag){
                            out.println("done");
                            break;
                        }
                        bot.myTurn = false;
                    } else {
                        inputLine = in.readLine();
                        System.out.println(inputLine);
                        responseToEnemy = protocol.botReceive(inputLine);
                        if (inputLine.equals("done")){
                            break;
                        }
                        bot.myTurn = true;
                    }
                }
            }

            if (!controller.asKi) {

                //else

                controller.game.myTurn=false;
                if (controller.game.firstShotAfterLoading && !newGame){
                    while ((inputLine=in.readLine())!=null && !inputLine.equals("next")){
                        Thread.onSpinWait();
                    }
                    controller.game.myTurn=true;
                }
                //TODO GAMELOOP
                while (true) {

                    if (controller.game.myTurn) {
                        while (waiting) {
                            Thread.onSpinWait();
                        }
                        if (shooting && shotAllowed) {
                            out.println("shot " + controller.shotX + " " + controller.shotY);
                            shooting = false;
                        } else if (skipNextShooting) {
                            out.println("next");
                            skipNextShooting=false;
                        } else if (answerToEnemy) {
                            out.println("answer " + responseToEnemy);
                            answerToEnemy = false;
                        }else if (controller.saveFlag){
                            controller.game.firstShotAfterLoading=true;
                            Platform.runLater(() -> {
                                SaveLoad.saveData(controller.game);
                            });
                            while (SaveLoad.waitingForSave){
                                Thread.onSpinWait();

                            }

                            out.println("save "+ SaveLoad.saveID);
                            break;
                        }else if (doneFlag){
                            out.println("done");
                            break;
                        }
                        if (controller.game.amountOfShips== controller.game.playerShipsDestroyed){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU LOST");});
                            break;
                        }
                        controller.game.myTurn = false;
                    } else {
                        inputLine = in.readLine();
                        System.out.println("input " + inputLine);
                        responseToEnemy = protocol.receive(inputLine);
                        if (responseToEnemy==null){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU WIN");});
                            break;

                        }
                        if (inputLine.equals("done")){
                            break;
                        }
                        controller.game.myTurn = true;
                        if (controller.game.amountOfShips== controller.game.playerShipsDestroyed){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU LOST");});
                            break;
                        }
                    }
                }
            }

            System.out.println("Ende");
            battleshipsSocket.close();


            } catch (UnknownHostException e) {
            System.err.println("Unbekannter Host: " + hostIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException for the connection to: " + hostIP);
            System.exit(1);
        }


    //Methode
    }
    public void printNext(){
        next = true;
    }
    public void printDone(){
        done = true;
    }
//class
}
