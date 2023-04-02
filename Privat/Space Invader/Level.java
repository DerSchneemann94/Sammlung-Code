package rothenberger.domenic.SpaceInvader;

import java.awt.Color;
import java.awt.Graphics2D;

public class Level {

	private Handler handler;
	private int counter=0,level;
	private int maxPosX=0,maxPosY=0; 
	private boolean newLevel;
	public static int score=0;
	
	public Level(Handler handler) {
		this.handler = handler;
		this.newLevel = true;
		this.level = 1;
	}

	public void tick() {
		for(GameObject o:handler.object) {
			if(o.id == ID.Enemy) {newLevel = false;}
		}
		if(newLevel) {
			switch(level) {
			case 1:
				maxPosX = 3;
				maxPosY = 2;
				break;
			case 2:
				maxPosX = 4;
				maxPosY = 3;
				break;
			case 3:
				maxPosX = 5;
				maxPosY = 3;
				break;
			}
			for(int i=0;i<=maxPosX;i++) {
				for(int j=0;j<=maxPosY;j++) {
					handler.objectAdd.add(new Enemy(Game.WIDTH/(maxPosX+2)*(i+1),Game.HEIGHT/(maxPosY+6)*j+30,ID.Enemy,handler));
				}
			}
		level++;	
		}
		newLevel = true;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawString("Score: " + score,10,Game.HEIGHT-50);
	}
}
