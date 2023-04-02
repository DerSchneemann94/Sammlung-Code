package rothenberger.domenic.SpaceInvader;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable {

	public static final long serialVersionUID = 1550691097823471818L;
	private Thread thread;
	private boolean running = false; 
	private Handler handler;
//	private HUD hud;
	private Level lvl;
	private BufferedImage img;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	public Game() {
		handler = new Handler();
		lvl = new Level(handler); 
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH,HEIGHT,"Space Invader",this);
		
		handler.objectAdd.add(new Player(Game.WIDTH/2,Window.HEIGHT2-50,ID.Player,handler));
//		handler.objectAdd.add(new Enemy(Game.WIDTH/2,50,ID.Enemy,handler));

		
//		hud = new HUD();
		
//		r = new Random();
		
		
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			this.requestFocus();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta>=1) {
				tick();
				delta--;
			}	
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS:  " + frames);
				frames = 0;
			}
		}
		stop();
		
		
		
	}
	
	private void tick() {
		handler.tick();
//		hud.tick();
		lvl.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics(); 
		
		try {
			img = ImageIO.read(new File("C:\\Users\\Domenic\\Pictures\\Games\\Background3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(img,0,0,null);
//		g.setColor(Color.black);
//		g.fillRect(0, 0,WIDTH, HEIGHT);
		
		handler.render(g);
		lvl.render(g);
//		hud.render(g);
		
		g.dispose();
		bs.show();
	
	}
	
	public static int clamp(int var, int min, int max) {
		if(var>=max)
			return var = max;
		else if(var<=min)
			return var = min;
		else
			return var;
	}
	
	
	
	
	public static void main(String[] args) {
		new Game();
	
	}
	
}
