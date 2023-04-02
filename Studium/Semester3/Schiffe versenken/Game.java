package battleships;

import java.io.Serial;
import java.io.Serializable;

//Das Spielgeschehen
public class Game implements Serializable {
	@Serial
	private static final long serialVersionUID=420L;
	public Unit[][] field;
	public Unit[][] enemyField;
	public int fieldsize,amountOfShips,enemyShipsDestroyed,playerShipsDestroyed;
	public boolean myTurn, firstShotAfterLoading;
	public String saveID;

	public Game(int fieldsize, int[]amount) {
		for(int i=0;i<amount.length;i++) {
			amountOfShips+=amount[i];
		}
		this.field = new Unit[fieldsize][fieldsize];
		this.fieldsize = fieldsize;
		this.enemyField = new Unit[fieldsize][fieldsize];
		this.enemyShipsDestroyed=0;
		this.playerShipsDestroyed=0;
		this.myTurn = true;
	}

	//Platziert ein Schiff
	public void placeShip(int x, int y,int length, boolean directionHorizontal, int shipNr) {
			int k=0;
			for(int i=0;i<length;i++) {
				if (directionHorizontal) {
					this.field[x + i][y] = new Unit();
					this.field[x + i][y].shipNr = shipNr;
				} else {
					this.field[x][y + i] = new Unit();
					this.field[x][y + i].shipNr = shipNr;
				}
			}
	}

	//Prüft ob ein Schiff platziert werden kann
	public boolean placeTest(int x, int y,int length,boolean directionHorizontal) {
		for(int i=0;i<length;i++) {
			if(this.field[x][y]!=null) {
				return false;
			}
			if(directionHorizontal) {
				if (x+i== fieldsize){
					return false;
				}
				//links
				if(x+i-1>0) {
					if(field[x+i-1][y]!=null)
						return false;
				}
				//rechts
				if(x+i+1< fieldsize) {
					if(field[x+i+1][y]!=null)
						return false;
				}
				//oben
				if(x+i< fieldsize && y-1>=0) {
					if(field[x+i][y-1]!=null)
						return false;
				}
				//unten
				if(x+i< fieldsize && y+1< fieldsize) {
					if(field[x+i][y+1]!=null)
						return false;
				}
				//links oben
				if(x+i-1>=0 && y-1>=0) {
					if(field[x+i-1][y-1]!=null)
						return false;
				}
				//lins unten 
				if(x+i-1>=0 && y+1< fieldsize) {
					if(field[x+i-1][y+1]!=null)
						return false;
				}
				//rechts oben 
				if(x+i+1< fieldsize && y-1>=0) {
					if(field[x+i+1][y-1]!=null)
						return false;
				}
				//rechts unten
				if(x+i+1< fieldsize && y+1< fieldsize) {
					if(field[x+i+1][y+1]!=null)
						return false;
				}
			}
			else {
				if (y+i== fieldsize){
					return false;
				}
				//links
				if(x-1>=0) {
					if(field[x-1][y]!=null)
						return false;
				}
				//rechts
				if(x+1< fieldsize) {
					if(field[x+1][y]!=null)
						return false;
				}
				//oben
				if(y+i-1>=0) {
					if(field[x][y+i-1]!=null)
						return false;
				}
				//unten
				if(y+i+1< fieldsize) {
					if(field[x][y+i+1]!=null)
						return false;
				}
				//links oben
				if(x-1>=0 && y+i-1>=0) {
					if(field[x-1][y+i-1]!=null)
						return false;
				}
				//lins unten 
				if(x-1>=0 && y+i+1< fieldsize) {
					if(field[x-1][y+i+1]!=null)
						return false;
				}
				//rechts oben 
				if(x+1< fieldsize && y+i-1>=0) {
					if(field[x+1][y+i-1]!=null)
						return false;
				}
				//rechts unten
				if(x+1< fieldsize && y+i+1< fieldsize) {
					if(field[x+1][y+i+1]!=null)
						return false;
				}	
			}
		}
	return true;
	}//placeTest

	//Gegnerischen Schuss überprüfen und Antwort zurückliefern
	public int setEnemyShot(int x, int y){
		int res;
		if (field[x][y]== null){
			field[x][y] = new Unit();
			field[x][y].water=true;
			return 0;
		}
		if (field[x][y].water||field[x][y].destroyed||field[x][y].hit){
			return 0;
		}
		field[x][y].hit = true;
		res = 2;
		for (int xx=0;xx<field.length;xx++){
			for (int yy=0;yy<field.length;yy++){
				if (field[xx][yy] != null && field[xx][yy].shipNr == field[x][y].shipNr && !field[xx][yy].hit){
					res = 1;
					break;
				}
			}
		}
		if (res==2){
			for (int xx=0;xx<field.length;xx++){
				for (int yy=0;yy<field.length;yy++){
					if (field[xx][yy] != null && field[xx][yy].shipNr == field[x][y].shipNr){
						field[xx][yy].destroyed = true;
					}
				}
			}
		}
		return res;
	}//setEnemyShot


}//Game Ende
