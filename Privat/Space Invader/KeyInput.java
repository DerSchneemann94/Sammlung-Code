package rothenberger.domenic.SpaceInvader;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {

	private Handler handler;
	private Player d;
	private boolean keyDown[];
	private boolean spacePressed=false;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
		keyDown = new boolean[2];
		keyDown[0] = false;
		keyDown[1] = false;
	}
	
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
		d = (Player) handler.object.get(0);
		if(key==KeyEvent.VK_LEFT) {d.velX = -4; keyDown[0] = true;}
		if(key==KeyEvent.VK_RIGHT) {d.velX = 4; keyDown[1] = true;}
		if(key==KeyEvent.VK_SPACE && !spacePressed) {spacePressed=true;d.shoot();}

	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	
		d = (Player) handler.object.get(0);
		if(key == KeyEvent.VK_LEFT) {keyDown[0] = false;}
		if(key == KeyEvent.VK_RIGHT) {keyDown[1] = false;}
		if(!keyDown[0] && !keyDown[1]) d.velX = 0;
		if(key==KeyEvent.VK_SPACE ) {spacePressed=false;}
	}		
}	
		