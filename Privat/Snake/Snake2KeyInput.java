package rothenberger.domenic.SnakeGameImproved;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Snake2KeyInput extends KeyAdapter {

	private Snake2Snake snake;
	private Snake2Game game;
	
	
	public Snake2KeyInput(Snake2Snake snake,Snake2Game game) {
		this.snake = snake;
		this.game = game;
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		if(game.getKeyPressed()) {
			return;
		}
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_UP && snake.getDirection()!=3) {snake.setDirection(1);}
		if(key == KeyEvent.VK_RIGHT && snake.getDirection()!=4) {snake.setDirection(2);}
		if(key == KeyEvent.VK_DOWN && snake.getDirection()!=1) {snake.setDirection(3);}
		if(key == KeyEvent.VK_LEFT && snake.getDirection()!=2) {snake.setDirection(4);}
		game.setKeyPressed(true);
		
		if(Snake2Game.getGameOver()) {
			if(key == KeyEvent.VK_ENTER) {
				Snake2Game.setGameOver(false);
				game.restart();
			}
		}
	}
	
	public void updateSnake(Snake2Snake snake) {
			this.snake = snake;
	}
	
	
}		
		