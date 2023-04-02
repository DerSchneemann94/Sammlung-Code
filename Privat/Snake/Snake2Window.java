package rothenberger.domenic.SnakeGameImproved;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Snake2Window extends Canvas {

	
	private static final long serialVersionUID = 1L;
	private String title;
	private JFrame frame;
	
	
	public Snake2Window(String title, Snake2Game game,Snake2Field field) {
		this.title = title;
		
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(field.getFieldSizeX()+12,field.getFieldSizeY()+30 + Snake2Game.offSet));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public JFrame getFrame() {
		return frame;
	}

}
