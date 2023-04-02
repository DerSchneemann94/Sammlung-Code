package battleships;

import java.util.ArrayList;
import java.util.List;

public class MedBot extends Bot {

    private int medbotstate = 0; //0 = random shot, 1 = nach links, 2 = nach rachts, 3 = nach oben, 4 = nach unten

    private List<Pair<Integer, Integer>> shipsHit = new ArrayList<Pair<Integer, Integer>>();

    private int followinghits = 1;

    public MedBot(int size, int[] ships) {
        super(size, ships);
    }

    public int[] botTurn(){
        BotShot = medBotShot();
        return BotShot;
    }

    private int[] medBotShot() {
        if (medbotstate == 0){
            medbotShotRandom();
        }
        else if(medbotstate == 1){
            shotX = shipsHit.get(0).getKey() - followinghits;
            if (shotX < 0){
                followinghits = 1;
                shotX = shipsHit.get(0).getKey();
                medbotstate++;
                return medBotShot();
            }
        }
        else if(medbotstate == 2){
            shotX = shipsHit.get(0).getKey() + followinghits;
            if (shotX >= size){
                followinghits = 1;
                shotX = shipsHit.get(0).getKey();
                medbotstate++;
                return medBotShot();
            }
        }
        else if(medbotstate == 3){
            shotX = shipsHit.get(0).getKey();
            shotY = shipsHit.get(0).getValue() - followinghits;
            if (shotY < 0){
                followinghits = 1;
                shotY = shipsHit.get(0).getValue();
                medbotstate++;
                return medBotShot();
            }
        }
        else if(medbotstate == 4){
            shotY = shipsHit.get(0).getValue() + followinghits;
            if (shotY >= size){
                followinghits = 1;
                shotY = shipsHit.get(0).getValue();
                medbotstate++;
                return medBotShot();
            }
        }
        return new int[]{shotX, shotY};
    }

    private void medbotShotRandom() {
        randomShot(enemyfield);
    }

    public void botSetState(int serverAnswer){
        recycleLastShot(serverAnswer);
        if (serverAnswer == 0){
            if (medbotstate != 0){
                followinghits = 1;
                medbotstate++;
                return;
            }
            medbotstate = 0;
        }
        else if (serverAnswer == 1){
            if (medbotstate == 0){
                followinghits = 1;
                medbotstate = 1;
                return;
            }
            followinghits++;
        }
        else if (serverAnswer == 2){
            followinghits = 1;
            medbotstate = 0;
        }
    }

    private void recycleLastShot(int serverAnswer) {
        boolean debug = true;
        if (serverAnswer == 0){
            if (debug){
                System.out.println("Shot was NOT a Hit: " + shotX + "|" + shotY);
            }
            shot(shotX, shotY, enemyfield, false);
        }
        else if(serverAnswer == 1){
            if (debug){
                System.out.println("Shot was a Hit: " + shotX + "|" + shotY);
            }
            shot(shotX, shotY, enemyfield, true);
            Pair<Integer, Integer> shot = new Pair<Integer, Integer>(shotX, shotY);
            shipsHit.add(shot);
        }
        else if (serverAnswer == 2){
            Pair<Integer, Integer> shot = new Pair<Integer, Integer>(shotX, shotY);
            shipsHit.add(shot);
            for (int i = 0; i < shipsHit.size() - 1; i++) {
                int shipX = shipsHit.get(i).getKey();
                int shipY = shipsHit.get(i).getValue();
                enemyfield[shipX][shipY] = 2;
            }
            shipsHit.clear();
        }
    }

}
