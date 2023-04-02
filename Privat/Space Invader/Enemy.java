package rothenberger.domenic.SpaceInvader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends GameObject {

	protected float hp,maxhp;
	private int counter,aggroCounter;
	
	public Enemy(int x, int y, ID id,Handler handler) {
		super(x, y, id,handler);
		rec =  new Rectangle[3];
		rec[0] = new Rectangle(posX,posY,30,20);
		rec[1] = new Rectangle(posX-8,posY+12,8,20);
		rec[2] = new Rectangle(posX+30,posY+12,8,20);
		hp = 5;
		maxhp = hp;
		centerX = posX + 15;
		this.sizeX=20; this.sizeY=20;
	}

	@Override
	public void tick() {
		collision();
		if(hp<=0)
			remove = true;
		for(GameObject o:handler.object) {
			if(o.id==ID.Player) {
				if(centerX<=o.centerX) {
					if(o.centerX-centerX<=20) {counter++;aggroCounter++;}
					else {counter=0;aggroCounter=0;}
				}	
				if(centerX>=o.centerX) {
					if(centerX-o.centerX<=20) {counter++;aggroCounter++;} 
					else {counter=0;aggroCounter=0;}
				}
			}
		}
		if(counter>=25 && aggroCounter>=45) {
			shoot();
			counter=0;
		}
			
	}

	private void collision() {
		for(GameObject o:handler.object) {
			if(o.id==ID.BulletP) {
				Bullet b = (Bullet) o;
				for(Rectangle rec:this.rec)
					if(b.rec.intersects(rec)) {
						b.remove = true;
						hp -= 2;
						break;
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		//Rumpf
		g.setColor(Color.MAGENTA);
		g.fillRect(posX,posY,30,20);
		g.fillRect(posX-8,posY+12,8,20);
		g.fillRect(posX+30,posY+12,8,20);
		
		//Kanonen
//		g.fillRect(posX+13,posY+20,4,6);
		
		//Gesicht
		g.setColor(Color.WHITE);
		g.fillRect(posX+4,posY+8,6,6);
		g.fillRect(posX+20,posY+8,6,6);
		g.fillRect(posX+11,posY+12,8,8);
		
		
		//HP
		g.setColor(Color.green);
		g.fillRect(posX,posY-10,Math.round(30*(hp/maxhp)),4);
		g.setColor(Color.red);
		g.fillRect(Math.round(posX+30*(hp/maxhp)),posY-10,Math.round(30-30*(hp/maxhp)),4);
	}
	
	public void shoot() {
		handler.objectAdd.add(new Bullet(posX+14,posY+20,ID.BulletE,3,handler));
	}

}
