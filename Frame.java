import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel{
	private static final long serialVersionUID = 1L;
	final int width = 640;int height = 560;
	int cells;
	int cellWidth;
	int cellHeight;
	int selectorX;
	int selectorY;
	JFrame frame;
	Piece[] board;
	int boxX, boxY;
	float alpha = 0.8f;
	float alphaCounter = 0.0003f;
	ArrayList<Integer> availableMoves = new ArrayList<Integer>();
	private static Keyboard key = new Keyboard();
	
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
		frame.setMinimumSize(new Dimension(100, 100));
		frame.setTitle("Press 'n' to start new game");
		frame.addKeyListener(key);
		frame.setVisible(true);
		selectorX = getWidth();
		selectorY = getHeight();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		cellWidth = frame.getWidth() / cells - 2;
		cellHeight = frame.getHeight() / cells - 5;
		
		g2.setColor(Color.decode("#FFFFFF"));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.decode("#BCBABE"));
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
		
															// REMOVE ALPHA COUNTING IF ANNOYING!
		
		alpha += alphaCounter;
		if(alpha > 0.8f || alpha < 0.3f) alphaCounter = alphaCounter * -1;
		
															// show available moves
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.decode("#FF0038"));
		for(int i = 0; i < availableMoves.size(); i++){
			if(board[availableMoves.get(i)] != null) g2.setColor(Color.ORANGE);
			boxX = (availableMoves.get(i) % cells) * cellWidth;
			boxY = availableMoves.get(i) / cells * cellHeight;
			g2.fillRect(boxX, boxY, cellWidth, cellHeight);
			g2.setColor(Color.decode("#FF0038"));
		}
		g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1.0f));
															// show where the player is
		g2.setColor(Color.decode("#257985"));
		g2.fillRect(selectorX, selectorY, cellWidth, cellHeight);
		
															// draw the pieces on the board
		for(int i = 0; i < board.length; i++){
			if(board[i] != null){
				if(board[i].getType() == 1)g.setColor(Color.WHITE);			
				g2.drawImage(board[i].getImage(),(i % cells) * cellWidth, (i / cells) * cellHeight, cellWidth, cellHeight, null);
			}
		}
	}
	public void setBoard(Piece[] b){this.board = b;}
	public void setX(int x) {selectorX = (x / cellWidth) * cellWidth;}
	public void setY(int y) {selectorY = (y / cellHeight) * cellHeight;}
	public void setMoves(ArrayList ls) {availableMoves = ls;}
	public void clearMoves(){ availableMoves.clear();}
}