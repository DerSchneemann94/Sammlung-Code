package battleships;

public class GameNetwork{

    public int port;
    public volatile boolean newGame=true, loadingFlag=false, placement=true, waiting=true, shooting=false, shotAllowed=false, skipNextShooting=false, answerToEnemy=false, doneFlag=false, gameWon=false, gameLost=false;
    public Controller controller;
    public String responseToEnemy="";
    public Bot bot;

}
