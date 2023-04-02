package rothenberger.domenic.SpaceInvader;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {

	protected int posX,posY,sizeX,sizeY,centerX,centerY;
	protected int velX,velY;
	protected ID id;
	protected boolean remove;
	protected Handler handler;
	protected Rectangle[] rec;
	
	
	
	public GameObject(int x,int y,ID id,Handler handler) {
		this.posX = x;
		this.posY = y;
		this.id = id;
		this.handler = handler;
		
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g);
	
	protected int clamp(int v, int min, int max) {
		if(v<=min) return min;
		if(v>=max) return max;
		return v;
	}
	
	
}
