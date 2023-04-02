package rothenberger.domenic.SpaceInvader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Player extends GameObject {

	protected float hp,maxhp;
	protected int mouseX=10,mouseY=10;
	BufferedImage img;
	public int test=3;
	
	
	public Player(int x,int y,ID id,Handler handler) {
		super(x,y,id,handler);		
		this.velX = 0; this.velY = 0;
		this.sizeX = 50; this.sizeY = 30;
		rec = new Rectangle[2]; 
		rec[0] = new Rectangle(posX-10,posY+16,50,30);
		rec[1] = new Rectangle(posX+5,posY,20,30);
		hp = 50;
		maxhp=hp;
		centerX = this.posX + 15;
		
		
		
	}

	@Override
	public void tick() {
		posX = clamp(posX + velX, 10, Window.WIDTH2-sizeX+8);
		posY = clamp(posY + velY, 0, Window.HEIGHT2-sizeY);
		centerX = posX + 15;
		rec[0].x = posX-10;
		rec[0].y = posY+16;
		rec[1].x = posX+5;
		rec[1].y = posY;

		
//		centerX = posX + sizeX / 2;
//		centerY = posY + sizeY / 2;
		collision();
		if(hp<=0)
			remove=true;
		
	}

	private void collision() {
		for(GameObject o:handler.object) {
			if(o.id==ID.BulletE) {
				Bullet b = (Bullet) o;
				for(Rectangle rec:this.rec) {
					if(b.rec.intersects(rec)) {
						b.remove = true;
						hp-=2;
						break;
					}
				}
				
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
//		g.drawImage(img, posX, posY, null);
		//Rumpf
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(posX+5,posY,20,30);
		g.fillRect(posX-10,posY+16,50,16);
		
		//Kanonen
		g.fillRect(posX-10,posY+5,2,25);
		g.fillRect(posX+38,posY+5,2,25);
		g.fillRect(posX+14,posY-5,2,6);
		
		//Fenster
		g.setColor(Color.blue);
		g.fillRect(posX+10,posY+4,10,10);
		
		//Hitbox
//		g.setColor(Color.green);
//		g.draw3DRect(posX-10,posY,50,30,true);
		
		//HP
		g.setColor(Color.green);
		g.fillRect(posX-10,posY+37,Math.round(50*(hp/maxhp)),5);
		g.setColor(Color.red);
		g.fillRect(Math.round(posX-10+50*(hp/maxhp)),posY+37,Math.round(50-50*(hp/maxhp)),5);
	}

	public void shoot() {
		handler.objectAdd.add(new Bullet(posX+14,posY -5,ID.BulletP,-7,handler));
	}
	
}
