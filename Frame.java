import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel{
	private static final long serialVersionUID = 1L;
	final int width = 640;int height = 560;
	int cells;
	int cellWidth;
	int cellHeight;
	JFrame frame;
	Piece[] board;
	
	public Frame(int c, Piece[] b){
		cells = c;
		board = b;
		board = new Piece[cells*cells];
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		cellWidth = frame.getWidth() / cells;
		cellHeight = frame.getHeight() / cells;
		
		g2.setColor(new Color(245,184,0));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(new Color(184,138,0));
		
		// DRAW THE BOARD
		for(int y = 0; y < getHeight(); y += cellHeight * 2){
			for(int x = 0; x < getWidth(); x += cellWidth * 2){
				g2.fillRect(x, y, cellWidth, cellHeight);
			}			
		}
		for(int y = cellHeight; y < getHeight(); y += cellHeight * 2){
			for(int x = cellWidth; x < getWidth(); x += cellWidth * 2){
				g2.fillRect(x, y, cellWidth, cellHeight);
			}			
		}
		
		// draw the pieces on the board
		for(int i = 0; i < board.length; i++){
			if(board[i] != null){
				g2.setColor(Color.BLACK);
				if(board[i].getType() == 1)g.setColor(Color.WHITE);
// REMINDER: check if (i / cells) throws exception in the future!
				g2.fillOval((i % cells) * cellWidth, (i / cells) * cellHeight, cellWidth, cellHeight);					
			}
		}
	}
	
	public void setBoard(Piece[] b){
		this.board = b;
	}
}
