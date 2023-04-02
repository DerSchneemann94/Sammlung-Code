package rothenberger.domenic.SnakeGameImproved;

import java.awt.Color;
import java.awt.Graphics;

public class Snake2Apple {

	protected Snake2Cell apple;
	private Color color = Color.red;
	
	public Snake2Apple(int x, int y) {
		apple = new Snake2Cell(x,y,color);
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		apple.render(g);
	}
	
	
}
