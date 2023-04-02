package battleships;

import java.io.Serializable;

public class Singleplayer implements Serializable {

    public Bot bot;
    public Board board;
    public Game game;
    public int fieldsize, shotX, shotY;
    public int[] ships;
    public boolean placement = true, firstShotAfterLoading, waiting = true, save, shooting, shotAllowed=true, gameLost, gameWon, myTurn=true;

    public Singleplayer(int fieldsize, int[] ships, Board board, Game game, Bot bot, Controller controller) {
        this.fieldsize = fieldsize;
        this.ships = ships;
        this.board = board;
        this.game = game;
        this.bot = bot;

        Thread spThread = new Thread(new SingleplayerRun(controller, this));
        spThread.setDaemon(true);
        spThread.start();

    }

    public void answer(String response){
        if (response.equals("3")){
            gameLost = true;
            response = 2 + "";
        }
        switch (response){
            case "0":
                board.setEnemyShot(response);
                shotAllowed = false;
                myTurn = false;
                //shotflag false -> das
                break;
            case "1":
                board.setEnemyShot(response);
                shotAllowed = true;
                waiting=true;
                myTurn = true;
                //shotflag true
                break;
            case "2":
                game.enemyShipsDestroyed++;
                board.setEnemyShot(response);
                shotAllowed = true;
                waiting = true;
                myTurn = true;
                //shotflag true
                break;
        }
    }



}
