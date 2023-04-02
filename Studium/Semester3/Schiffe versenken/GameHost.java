package battleships;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Runnable;
//Master = Host
//Client = Bot/2. Spieler
//Protocol = Spielablauf
public class GameHost extends GameNetwork implements Runnable{
//    private int port;
    public boolean shipbuilder=true; //waiting=true;
    public String fieldsize;
    public String[]ships;
    private boolean Botgame = false;
//    public boolean placement=true;
//    private Controller controller;

//    private ServerSocket serverSocket;
//    private Socket clientSocket;
//    private PrintWriter out;
//    private BufferedReader in;
//    private BufferedReader stdIn;




    GameHost(String port, boolean botgame, Controller controller){
        this.port = Integer.parseInt(port);
        this.Botgame = botgame;
        this.controller = controller;
        this.shotAllowed = true;
    }

    public void run(){
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            controller.btnMPSettings.setDisable(false);
            controller.btnLoad.setDisable(false);
            System.out.println("Verbunden!");
            //Input und Output
            String inputLine;
            String outputLine;

            //Konversation starten mit Client über Socket
            //Abfrage ob Botgame oder 2 Spieler
            //Botgame
            GameOnlineProtocol protocol = new GameOnlineProtocol(controller, this);

            System.out.println("aski" + controller.asKi + "vor loop");

            while (waiting) {
                Thread.onSpinWait();
            }
            if (loadingFlag){
                Platform.runLater(() -> {

                    controller.loadGame();
                });
                while (!SaveLoad.loadingDone){
                    Thread.onSpinWait();
                }
                if (controller.asKi){
                    out.println("load " + bot.saveID);
                }
                else {
                    out.println("load " + controller.game.saveID);
                }
                while ((inputLine = in.readLine()) != null && !inputLine.equals("done")){
                    Thread.onSpinWait();
                }
                out.println("ready");
                while ((inputLine = in.readLine()) != null && !inputLine.equals("ready")){
                    Thread.onSpinWait();
                }
                newGame=false;


            }else {
                outputLine = "size "+fieldsize;
                out.println(outputLine);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ships");

                controller.btnLoad.setDisable(true);
                controller.btnMPSettings.setDisable(true);
                controller.btnMPSettingsGamemode.setDisable(true);


                while ((inputLine = in.readLine()) != null){
                    if (inputLine.equals("done")){
                        break;
                    }
                    //TODO ERNEUTES NEXT WIRD DOPPELTE AUS
                    if (shipbuilder){
                        for (int i =ships.length-1;i>=0;i--){
                            for (int j=0;j<Integer.parseInt(ships[i]);j++){
                                stringBuilder.append(" ").append(i + 2);
                            }
                        }
                        shipbuilder=false;
                    }
//                    for (int i =ships.length-1;i>=0;i--){
//                        for (int j=0;j<Integer.parseInt(ships[i]);j++){
//                            stringBuilder.append(" ").append(i + 2);
//                        }
//                    }
                    outputLine=stringBuilder.toString();
                    out.println(outputLine);
                    System.out.println(""+outputLine);

                }
                controller.btnToPlacement.setDisable(false);
                //2. Protokoll für 2 Spieler
                while(placement) {
                    Thread.onSpinWait();
                }
                out.println("ready");

                while ((inputLine=in.readLine()) != null){
                    if (inputLine.equals("ready")){
                        break;
                    }
                }

                Platform.runLater(
                        () -> {
                            controller.goToPlayingScene();
                        }
                );
            }

            //TODO BOT VS BOT
            if (controller.asKi){
                System.out.println("bot aski");

                shotAllowed = true;

                if (!bot.firstShotAfterLoading && !newGame){
                    bot.myTurn=false;
                    shotAllowed = false;
                    out.println("next");
                }

                //TODO GAMELOOP
                waiting = true;
                while (true){
                    if (bot.myTurn){
                        while(waiting){
                            Thread.onSpinWait();
                        }
                        if (gameWon){
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
                        }
                        else if (shotAllowed){
                            int[] coordinates = bot.botTurn();
                            int x = coordinates[0] + 1;
                            int y = coordinates[1] + 1;
                            out.println("shot " + x + " " + y);
                            shotAllowed = false;
                        }else if (skipNextShooting){
                            out.println("next");
                            skipNextShooting=false;
                        }else if (answerToEnemy){
                            out.println("answer " + responseToEnemy);
                            answerToEnemy=false;
                            if (gameLost){
                                Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                    controller.board.gameOver.setText("YOU LOST");});
                                System.out.println("Loose: " + responseToEnemy);
                                break;
                            }
                        }else if (doneFlag){
                            System.out.println("done");
                            out.println("done");
                            break;
                        }
                        bot.myTurn = false;
                    }else {
                        System.out.println("hier erst nachm Schießen später rein");
                        inputLine = in.readLine();
                        System.out.println("Client response: "+ inputLine);
                        responseToEnemy = protocol.botReceive(inputLine);
                        if (inputLine.equals("done")){
                            break;
                        }
                        System.out.println("ResponseToEnemy: "+ responseToEnemy);
                        bot.myTurn = true;
                    }
                }
            }

            System.out.println("aski" + controller.asKi + "nach loop");
            //2-Spieler
            if (!controller.asKi){
                System.out.println("not aski");
//                outputLine = protocol.process2("Erfolgreich verbunden");
//                out.println(outputLine);


                //while schleife die wartet bis sendsettings geklickt worden ist

                //loading else

                controller.game.myTurn = true;

                if (!controller.game.firstShotAfterLoading && !newGame){
                    controller.game.myTurn=false;
                    out.println("next");
                }

                //TODO GAMELOOP
                waiting = true;
                while (true){
                    if (controller.game.myTurn){
                        while(waiting){
                            Thread.onSpinWait();

                        }
                        if (shooting && shotAllowed){
                            out.println("shot " + controller.shotX + " " + controller.shotY);
//                            protocol.send("shot " + controller.shotX + " " + controller.shotY);
                            shooting = false;

                        }else if (skipNextShooting){
                            out.println("next");

                            skipNextShooting=false;
                        }else if (answerToEnemy){
                            out.println("answer " + responseToEnemy);
                            answerToEnemy=false;

                        }else if (controller.saveFlag){
                            controller.game.firstShotAfterLoading=true;
                            Platform.runLater(() -> {
                                SaveLoad.saveData(controller.game);});
                            controller.saveFlag=false;
                            while (SaveLoad.waitingForSave){
                                Thread.onSpinWait();
                            }
                            out.println("save "+ SaveLoad.saveID);
                            break;
                        }else if (doneFlag){
                            System.out.println("done");
                            out.println("done");
                            break;
                        }
                        if (controller.game.amountOfShips== controller.game.playerShipsDestroyed){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU LOST");});
                            break;
                        }
                        controller.game.myTurn = false;
                    }else {
                        System.out.println("hier erst nachm Schießen später rein");
                        inputLine = in.readLine();
                        System.out.println("Client response: "+ inputLine);
                        responseToEnemy = protocol.receive(inputLine);
                        if (responseToEnemy==null){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU WIN");});
                            break;
                        }
                        if (inputLine.equals("done")){
                            break;
                        }
                        System.out.println("ResponseToEnemy: "+ responseToEnemy);
                        controller.game.myTurn = true;
                        if (controller.game.amountOfShips==controller.game.playerShipsDestroyed){
                            Platform.runLater(() -> {controller.board.gameOver.setVisible(true);
                                controller.board.gameOver.setText("YOU LOST");});
                            break;
                        }
                    }
                }
                System.out.println("Ende");
                serverSocket.close();


//                while ((inputLine = in.readLine()) != null) {
//                    System.out.println("Server while-Schleife");
//
//
//
//                    //inputLine ist das vom Client geschriebene, das Resultat vom Protocol ist dann outputLine welches die wiedergabe vom Server ist.
//                    //outputLine = protocol.process2(inputLine);
//                    //Hier muss irgendwie inputLine verarbeitet werden
//
//                    //Server antwortet hier
//                    //wartet hier
//                    outputLine = stdIn.readLine();
//                    out.println(outputLine);
//
//
//                    //Wenn es zuende ist:
//                    if (outputLine.equals("The End.")) {
//                       System.out.println("Verbindung wird getrennt!");
//                        break;
//                    }
//                }
            }

        } catch (IOException e) {
            System.out.println("IOException by port: "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }



    //main
    }//run

//class
}
