package rothenberger.domenic.SnakeGameImproved;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Snake2Field {

	private Rectangle[] border;
	private Graphics g;
	private int anzahlZellenX=30,anzahlZellenY=30,fieldSizeX=anzahlZellenX*Snake2Cell.cellsize,fieldSizeY=anzahlZellenX*Snake2Cell.cellsize;
	
	public Snake2Field() {
		
		border = new Rectangle[4];
		border[0] = new Rectangle(0,Snake2Game.offSet,this.anzahlZellenX*Snake2Cell.cellsize,Snake2Cell.cellsize);
		border[1] = new Rectangle(0,Snake2Game.offSet,Snake2Cell.cellsize,this.anzahlZellenY*Snake2Cell.cellsize);
		border[2] = new Rectangle((this.anzahlZellenX-1)*Snake2Cell.cellsize,Snake2Game.offSet,Snake2Cell.cellsize,this.anzahlZellenY*Snake2Cell.cellsize);
		border[3] = new Rectangle(0,(this.anzahlZellenY-1)*Snake2Cell.cellsize+Snake2Game.offSet,this.anzahlZellenX*Snake2Cell.cellsize,Snake2Cell.cellsize);
	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		for(Rectangle rec:border)			
			g.fillRect(rec.x, rec.y, rec.width, rec.height);
	}
	
	public int getFieldSizeX() {
		return fieldSizeX;
	}

	public int getFieldSizeY() {
		return fieldSizeY;
	}
	
}
