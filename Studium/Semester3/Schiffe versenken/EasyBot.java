package battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToIntFunction;

public class EasyBot extends Bot{

    public EasyBot(int size, int[] ships) {
        super(size, ships);

        /*
        System.out.println("Botfield with placed ships");
        printField(botfield);
        printShippositions(shipPositions);
        System.out.println("______________");
        for (int t = 0; t < 25; t++) {
            botCheckForHit(botTurn(), enemyfield);
            printField(enemyfield);
            System.out.println("______________");
        }
        printField(botfield);
        System.out.println("______________");
         */

        }

    //EasyBot shot (Array with 2 Random coordinates)
    public int[] botTurn() {
        BotShot = randomShot(enemyfield);
        return BotShot;
    }

    public void botSetState(int serverAnswer){
        if (serverAnswer == 1 || serverAnswer == 2 || serverAnswer == 3) {
            shot(BotShot[0], BotShot[1], enemyfield, true);
            return;
        }
        if (serverAnswer == 0) {
            shot(BotShot[0], BotShot[1], enemyfield, false);
            return;
        }
    }
}
