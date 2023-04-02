package battleships;

import javafx.application.Platform;

public class SingleplayerRun implements Runnable{

    public Singleplayer single;
    public Controller controller;

    public SingleplayerRun(Controller controller, Singleplayer singleplayer) {
        this.controller = controller;
        this.single = singleplayer;
    }

    @Override
    public void run() {

        while (single.placement){
            Thread.onSpinWait();
        }
        Platform.runLater(
                () -> {
                    controller.goToPlayingScene();
                }
        );
        while (true){
            if (single.myTurn){
                while (single.waiting){
                    Thread.onSpinWait();
                }
                if (single.save){

                }else if(single.shotAllowed && single.shooting){
                    int response = single.bot.botCheckForHit(new int[]{single.shotX-1, single.shotY-1}, single.bot.botfield);
                    single.answer(response + "");
                    if (response == 3){
                        Platform.runLater(() -> {single.board.gameOver.setVisible(true);
                            single.board.gameOver.setText("YOU WIN");});
                        break;
                    }
                }
            }else {
                if (single.save){

                }else {
                    int[] botShot = single.bot.botTurn();
                    int response = single.game.setEnemyShot(botShot[0], botShot[1]);
                    single.myTurn = true;
                    single.shotAllowed = true;
                    if (response == 2){
                        single.myTurn = false;
                        single.game.playerShipsDestroyed++;
                    }else if (response == 1){
                        single.myTurn = false;
                    }
                    single.board.colourRightField(botShot[0], botShot[1], response);
                    single.waiting = true;
                    single.bot.botSetState(response);
                    if (single.game.playerShipsDestroyed == single.game.amountOfShips){
                        Platform.runLater(() -> {single.board.gameOver.setVisible(true);
                            single.board.gameOver.setText("YOU LOST");});
                        break;
                    }
                }
            }
        }
    }
}
