package rothenberger.domenic.SnakeGameImproved;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Snake2Snake implements Snake2GameObject {

	private ArrayList<Snake2Cell> snakeBody; 
	private int direction=3,snakeHeadX,snakeHeadY;
	private Snake2Apple apple;
	private Color colorBody=Color.green,colorEyes=Color.black;
	
	public Snake2Snake(Snake2Apple apple) {
		this.snakeBody = new ArrayList<Snake2Cell>(1);
		snakeBody.add(0, new Snake2Cell(3,3,colorBody));
		this.apple = apple;
		
	}


	public void tick() {
		newSnakeHead();
		collision();
		
	}

	public void render(Graphics g) {
		g.setColor(colorBody);
		for(Snake2Cell cell:snakeBody) 
			cell.render(g);
	}

	private void move() {
		snakeBody.add(0, new Snake2Cell(this.snakeHeadX,this.snakeHeadY,colorBody));
	}
	
	private void collision(){
		if(this.snakeHeadX == this.apple.apple.posX && this.snakeHeadY == this.apple.apple.posY) {
			appleTick();
			move();
			return;
		}		
		for(Snake2Cell cell:snakeBody) {
			if(this.snakeHeadX == cell.posX && this.snakeHeadY == cell.posY) {
				Snake2Game.setGameOver(true);
			}
		}
		if(this.snakeHeadX>28 || this.snakeHeadX<1 || this.snakeHeadY>28 || this.snakeHeadY<1){
			Snake2Game.setGameOver(true);
		}
	move();
	snakeBody.remove(snakeBody.size()-1);
		
	}	
	
	private void newSnakeHead() {
		this.snakeHeadX = snakeBody.get(0).posX;
		this.snakeHeadY = snakeBody.get(0).posY;
		switch(this.direction) {
		case 1: 
			this.snakeHeadY -= 1;
			break;
		case 2: 
			this.snakeHeadX += 1;
			break;
		case 3: 
			this.snakeHeadY += 1;
			break;
		case 4: 
			this.snakeHeadX -= 1;
			break;
		}
	}
	
	public void appleTick() {

		boolean intersect;
		do {
			intersect = false;
			apple.apple.posX = ThreadLocalRandom.current().nextInt(1, 29);
			apple.apple.posY = ThreadLocalRandom.current().nextInt(1, 29);
			for(Snake2Cell cell: snakeBody) {
				if(apple.apple.posX == cell.posX && apple.apple.posY == cell.posY)
					intersect=true;
			}
		}	while(intersect);
		Snake2HUD.SCORE++;
	}
	
	
	public void setDirection(int i) {
		this.direction = i;		
	}
	
	public int getDirection() {
		return this.direction;
	}
	
}
