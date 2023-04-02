package rothenberger.domenic.SpaceInvader;

import java.awt.Canvas;
import javax.swing.JFrame;


public class Window extends Canvas {

	private static final long serialVersionUID = 1L;
	public String title;
	public static int WIDTH, HEIGHT,WIDTH2,HEIGHT2,offSet=100;
	
	
	public Window(int w,int h,String title,Game game) {
	
		WIDTH = w;
		HEIGHT = h;
		
		JFrame frame = new JFrame();
		frame.setTitle(title);
		
		frame.setSize(WIDTH, HEIGHT);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
	 
		frame.setVisible(true);
		
		WIDTH2 = WIDTH - (frame.getInsets().right + frame.getInsets().left);
		HEIGHT2 =HEIGHT - (frame.getInsets().top + frame.getInsets().bottom);
		
		game.start();
	}
	
}