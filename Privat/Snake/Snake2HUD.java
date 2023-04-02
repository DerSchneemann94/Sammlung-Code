package rothenberger.domenic.SnakeGameImproved;

import java.awt.Color;
import java.awt.Graphics;

public class Snake2HUD {

	protected static int SCORE;
	private Snake2Field field;
	
	public Snake2HUD(Snake2Field field) {
		this.field = field;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawString("Punkte: ", 1*Snake2Cell.cellsize, (Snake2Cell.cellsize*2));
		g.drawString(Snake2HUD.SCORE + "", 2*Snake2Cell.cellsize+30,(Snake2Cell.cellsize*2));
	}
	
	
	
}
