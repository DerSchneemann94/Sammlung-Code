package rothenberger.domenic.SnakeGameImproved;

import java.awt.Color;
import java.awt.Graphics;

public class Snake2Cell {

	public static int cellsize=15;
	protected int posX,posY;
	protected Color color;
	
	
	public Snake2Cell(int x,int y,Color color) {
		this.posX = x;
		this.posY = y;
		this.color = Color.white;
	}

	public void render(Graphics g) {
		g.fillRect(this.posX*Snake2Cell.cellsize, this.posY*Snake2Cell.cellsize+Snake2Game.offSet, Snake2Cell.cellsize, Snake2Cell.cellsize);
	}
	
}

