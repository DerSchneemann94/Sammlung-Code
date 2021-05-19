package battleships;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class Bot implements Serializable {

    @Serial
    private static final long serialVersionUID=174L;

    //Feldgröße (von Server)
    protected int size;

    public String saveID;

    //return array
    protected int[] BotShot;

    //x and y coordinates of shot for return array
    protected int shotX;
    protected int shotY;

    protected boolean myTurn = true, firstShotAfterLoading;

    //sunken ships
    public int sunkShipsLast = 0;

    public int sunkEnemyShips = 0;

    //ships array
    protected final int[] ships;

    //ai field to place ships and check for hits
    protected int[][] botfield;  //0 = water, 1 = ship, 2 = hit, 3 = watershot, 4 = sunk

    //playerfield to check for logik to shot with brain
    protected int[][] enemyfield;  //0 = water, 1 = hit, 2 = sunk, 3 = watershot

    //Shippositions
    protected ArrayList<int[][]> shipPositions = new ArrayList<int[][]>();  //dim1 = Felder des Schiffs dim2= x/y Wert

    //Construktor Bots: 2 fields to play, ships testing arraylist
    public Bot(int size, int[] ships) {
        this.size = size;
        this.botfield = new int[size][size];
        this.enemyfield = new int[size][size];
        this.ships = new int[ships.length];

        for (int i = 0; i < ships.length; i++) {
            this.ships[i] = ships[i];
        }

        //place ships on botfield
        botPlace();

    }

    //debug Shiplist
    protected void printShippositions(ArrayList<int[][]> shipList){
        int t=0;
        for (int[][] ship : shipList) {
            System.out.println("Ship " + t);
            for (int i = 0; i < ship.length; i++){
                System.out.println("shipPosition (" + ship[i][0] + "|" + ship[i][1] + ")");
            }
            System.out.println("__________________");
            t++;
        }
    }

    //Reset Field -> all 0
    protected void clearField(int[][] field) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = 0;
            }
        }
    }

    //Print Field
    public void printField(int[][] field) {
        System.out.println("Field:\n" + field);
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {
                System.out.print(field[y][x] + " ");
            }
            System.out.print("\n");
        }
    }

    //Place ships on Aifield
    protected boolean botPlace() {

        clearField(botfield);
        shipPositions.clear();

        boolean debug = false;

        int row, collumn;
        int placeTryCounter = 0;
        int unableToPlaceCounter = 0; //Counts Retries -> infinte loop prevention
        boolean direction; //false = waagrecht, true = senkrecht
        boolean notAllShipsPlaced = true;

        ArrayList<Integer> unPlacedShips = new ArrayList<Integer>();
        for (int i = 0; i < ships.length; i++) {
            unPlacedShips.add(ships[i]);
        }

        while (notAllShipsPlaced) {
            if (unableToPlaceCounter > 1000) {
                System.out.println("TooManyShips");
                clearField(botfield);
                shipPositions.clear();
                return false;
            }

            if (placeTryCounter > 100) {
                unPlacedShips.clear();
                for (int i = 0; i < ships.length; i++) {
                    unPlacedShips.add(ships[i]);
                }
                shipPositions.clear();
                clearField(botfield);
                placeTryCounter = 0;
                unableToPlaceCounter++;
            }

            if (debug) {
                System.out.println("unPlacedShips: " + unPlacedShips.toString());
            }

            for (int index = 0; index < unPlacedShips.size(); index++) {
                row = getRandomNumberInRange(0, size - 1);
                collumn = getRandomNumberInRange(0, size - 1);
                direction = new Random().nextBoolean();

                if (debug) {
                    System.out.println("row\t\t\t" + row);
                    System.out.println("collumn\t\t" + collumn);
                    System.out.println("direction\t" + direction);
                }

                int lengthOfShip2Place = unPlacedShips.get(index);

                if (botPlacePositionGood(row, collumn, lengthOfShip2Place, direction)) {
                    if (botPlaceShip(row, collumn, lengthOfShip2Place, direction)) {

                        int[][] ship = new int [lengthOfShip2Place][2];
                        for (int c = 0; c < lengthOfShip2Place; c++){
                            //store c_th coordinate in ship
                            if (direction){
                                //senkrecht
                                ship[c][0] = row + c;
                                ship[c][1] = collumn;
                            }else{
                                //waagrecht
                                ship[c][0] = row;
                                ship[c][1] = collumn + c;
                            }
                        }
                        shipPositions.add(ship);

                        unPlacedShips.remove(index);
                        if (debug) {
                            System.out.println("Ship succesfully placed");
                        }
                    } else {
                        if (debug) {
                            System.out.println("Ship not placed");
                        }
                    }
                    if (debug) {
                        printField(botfield);
                        System.out.println("_________________________________________________________");
                    }
                }
            }
            if (unPlacedShips.isEmpty()) {
                notAllShipsPlaced = false;
            }
            placeTryCounter++;
            if (debug) {
                System.out.println("TryCounter: " + placeTryCounter);
            }
        }
        return true;
    }

    //Help Method to Placeships on AiField
    protected boolean botPlaceShip(int x, int y, int shiplength, boolean direction) {
        boolean debug = false;

        if (debug) {
            System.out.println("PlaceX: " + x);
            System.out.println("PlaceY: " + y);
            System.out.println("shiplength: " + shiplength);
            System.out.println("direction: " + direction);
        }

        boolean positionIsPossible;
        if (direction) {
            //senkrecht
            positionIsPossible = !(x + shiplength > size || x > size || x < 0 || y < 0);
        } else {
            //waagrecht
            positionIsPossible = !(y + shiplength > size || y > size || x < 0 || y < 0);
        }

        if (debug) {
            System.out.println("PlaceIsPossible: " + positionIsPossible);
        }
        if (positionIsPossible) {
            for (int i = 0; i < shiplength; i++) {
                if (direction) {
                    //senkrecht
                    botfield[x + i][y] = 1;
                } else {
                    //waagrecht
                    botfield[x][y + i] = 1;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    //Help Method to check if it is possible to place ship at this place
    protected boolean botPlacePositionGood(int x, int y, int shiplength, boolean direction) {
        boolean debug = false;

        int xStart = x - 1;
        int yStart = y - 1;
        int xEnde, yEnde;

        if (direction) {
            //senkrecht
            xEnde = x + shiplength;
            yEnde = y + 1;
        } else {
            //waagrecht
            xEnde = x + 1;
            yEnde = y + shiplength;
        }
        for (int xWalk = xStart; xWalk <= xEnde; xWalk++) {
            for (int yWalk = yStart; yWalk <= yEnde; yWalk++) {
                boolean coordiantesNotOutOfBound = !(xWalk < 0 || xWalk > botfield.length - 1 || yWalk < 0 || yWalk > botfield.length - 1);
                if (debug) {
                    System.out.println("checking (" + xWalk + "|" + yWalk + ")");
                    System.out.println(coordiantesNotOutOfBound);
                }
                if (coordiantesNotOutOfBound) {
                    if (debug) {
                        System.out.println("value" + botfield[xWalk][yWalk]);
                    }
                    if (botfield[xWalk][yWalk] != 0) {
                        if (debug) {
                            System.out.println("Area NOT good");
                        }
                        return false;
                    }
                }
            }
        }
        if (debug) {
            System.out.println("Area clear");
        }
        return true;
    }

    //Method to Check if incoming shot is a hit or sunk on field. Returns 0 on water, 1 on hit, 2 on sunk, 3 on lost all ships, 4 for Error
    public int botCheckForHit(int[] enemyShot, int[][] field) {
        boolean debug = false;

        boolean shotOutOfBounds = !(enemyShot[0] < size && enemyShot[1] < size && enemyShot[0] >= 0 && enemyShot[1] >= 0);

        if (shotOutOfBounds) {
            System.out.println("SHOT OUT OF BOUNDS");
            return 404;
        }
        if (debug){
            System.out.println("incoming shot: " + enemyShot[0] + " | " + enemyShot[1]);
        }

        if (field[enemyShot[0]][enemyShot[1]] == 1) {
            field[enemyShot[0]][enemyShot[1]] = 2;
            //Hit
            if (sunkNewShip()){
                // Schiff versenkt
                System.out.println("U sunk a ship");
                if (allShipsSunk()){
                    //lost all ships
                    System.out.println("All ships sunk");
                    return 3;
                }
                return 2;
            } else {
                if (debug) {
                    System.out.println("hit");
                }
                return 1;
            }

        } else {
            //noHit
            field[enemyShot[0]][enemyShot[1]] = 3;
            if (debug) {
                System.out.println("water");
            }
            return 0;
        }
    }

    //Help Method to check for a new sunk ship
    protected boolean sunkNewShip(){
        int sunkShipsNow = 0;
        for (int[][] ship : shipPositions){
            if (isShipSunk(ship, botfield)){
                for (int c = 0; c < ship.length; c++){
                    System.out.println("schiffspoition: " + ship[c][0] + " " + ship[c][1]);
                    botfield[ship[c][0]][ship[c][1]] = 4;
                }
                sunkShipsNow++;
            }
        }
        if (sunkShipsNow > sunkShipsLast){
            sunkShipsLast++;
            return true;
        }
        return false;
    }

    //Help Method is whole ship sunk
    protected boolean isShipSunk(int[][] ship, int[][] field){
        boolean isSunk = true;
        for (int c = 0; c < ship.length; c++){
            if (field[ship[c][0]][ship[c][1]] == 1) {
                isSunk = false;
            }
        }
        if (isSunk){
            return true;
        }else{
            return false;
        }
    }

    //Help Method to check if u lost
    private boolean allShipsSunk() {
        if (sunkShipsLast == ships.length){
            return true;
        }
        return false;
    }

    //shot Methode do alter fields on server answer
    protected void shot(int shotX, int shotY, int[][] field, boolean hit) {
        if (hit){
            field[shotX][shotY] = 1;
        }
        if (!hit){
            field[shotX][shotY] = 3;
        }
    }

    //random shot generator for all bots
    protected int[] randomShot(int[][] field) {
        boolean newShot = false;
        while (!newShot){
            shotX = getRandomNumberInRange(0, size - 1);
            shotY = getRandomNumberInRange(0, size - 1);
            if (field[shotX][shotY] == 0){
                newShot = true;
            }
        }
        return new int[]{shotX, shotY};
    }

    //Help Method to check if (x|y) already shot
    protected boolean alreadyShot(int x, int y, int[][] field) {
        if (field[x][y] == 2 || field[x][y] == 3 || field[x][y] == 4) {
            //already Shot
            return true;
        } else {
            //not already Shot
            return false;
        }
    }

    //Help Method to get possible value in Range
    protected int getRandomNumberInRange(int min, int max) {
        int b = (int) (Math.random() * (max - min + 1) + min);
        return b;
    }

    public abstract int[] botTurn();

    public abstract void botSetState(int serverAnswer);
}