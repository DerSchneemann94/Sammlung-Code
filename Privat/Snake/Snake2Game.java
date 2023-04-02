package rothenberger.domenic.SnakeGameImproved;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Snake2Game extends Canvas implements Runnable {

	protected static int offSet=45;
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running = false;
	private Snake2Apple apple;
	private Snake2Snake snake;
	private Snake2Field field ;
	private Snake2HUD hud;
	private Snake2KeyInput listen;
	private boolean keyPressed=false;
	protected static boolean gameOver=false;
	
	public Snake2Game() {
		
		field = new Snake2Field();
		
		new Snake2Window("Snake",this,field);
		apple = new Snake2Apple(10,15);
		snake = new Snake2Snake(apple);
		hud = new Snake2HUD(field);

		this.addKeyListener(listen = new Snake2KeyInput(snake,this));
		
		start();
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

	@Override
	public void run() {
		this.requestFocus();
		while(running) {
			tick();
			render();
			
			this.setKeyPressed(false);
			
			try {
				Thread.sleep(115);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}
	
	private void tick() {
		snake.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0,field.getFieldSizeX(), field.getFieldSizeY()+offSet);
		
		if(Snake2Game.gameOver) {
			g.setColor(Color.red);
			g.drawString("GAME OVER",150,150);
			g.drawString("Press Enter to restart", 140, 165);
		}else {
			apple.render(g);
			snake.render(g);
			
		}
	
		field.render(g);
		hud.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public void restart() {
		field = new Snake2Field();
		apple = new Snake2Apple(10,15);
		snake = new Snake2Snake(apple);
		this.setKeyPressed(false);
		listen.updateSnake(snake);
		Snake2HUD.SCORE = 0;
	}
	
	
	public void setKeyPressed(boolean b) {
		this.keyPressed = b;
	}
	
	public boolean getKeyPressed() {
		return this.keyPressed;
	}
	
	public static void setGameOver(boolean b) {
		Snake2Game.gameOver = b;
	}
	
	public static boolean getGameOver() {
		return Snake2Game.gameOver;
	}
	
	public static void main(String[] args) {
		new Snake2Game();

	}
	
}
