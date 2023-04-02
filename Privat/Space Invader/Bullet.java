package rothenberger.domenic.SpaceInvader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet extends GameObject {

	protected int velY;
	protected Rectangle rec;
	
	public Bullet(int x, int y, ID id,int vely,Handler handler) {
		super(x, y, id,handler);
		velY = vely;
		rec = new Rectangle(posX,posY,2,5);
	}

	@Override
	public void tick() {
		posY += velY;
		rec.y = posY;
		if(posY<=0) this.remove=true; 
		if(posY>=Game.HEIGHT) this.remove=true;
	}

	@Override
	public void render(Graphics2D g) {
		if(id==ID.BulletP) 
			g.setColor(Color.yellow);
		else
			g.setColor(Color.green);
		g.fillRect(posX, posY, 2, 5);
	}

}
