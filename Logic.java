import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

public class Logic{
	
	private static Piece[] grid;
	static ArrayList<Piece> playerPieces;
	static ArrayList<Piece> enemyPieces;
	private static int cells;
	private static Frame frame;
	private static int x, y, index;
	static Random rand = new Random();
	static int pos = -1, dest = -1;
	static boolean checker;
	private static Keyboard key = new Keyboard();
	private static Piece enemyKing;
	private static Piece playerKing;
	private static Ai ai;
	
	private static void updateGrid(){
		// update the whole grid before drawing
		for(int i = 0; i < grid.length; i++){
			if(grid[i] != null && grid[i].getPosition() != i){
				grid[grid[i].getPosition()] = grid[i];
				grid[i] = null;
			}
		}
	}
	
	// fill the grid with pieces at the beginning of the game
	private static void fillGrid(){
		for(int i = 0; i < cells*cells; i++){
			grid[i] = null;
		}
		enemyPieces = new ArrayList<Piece>();
		playerPieces = new ArrayList<Piece>();
		Piece p;
														// Enemy pieces
		// Tower
		p = new Tower(cells, 0, 0);
		grid[0] = p;
		enemyPieces.add(p);
		p = new Tower(cells, 7, 0);
		grid[7] = p;
		enemyPieces.add(p);
		
		// Horse
		p = new Horse(cells, 1, 0);
		grid[1] = p;
		enemyPieces.add(p);
		p = new Horse(cells, 6, 0);
		grid[6] = p;
		enemyPieces.add(p);
		
		// Knight
		p = new Knight(cells, 2, 0);
		grid[2] = p;
		enemyPieces.add(p);
		p = new Knight(cells, 5, 0);
		grid[5] = p;
		enemyPieces.add(p);
		
		// Queen
		p = new Queen(cells, 3, 0);
		grid[3] = p;
		enemyPieces.add(p);
		
		// King
		
		p = new King(cells, 4, 0);
		grid[4] = p;
		enemyPieces.add(p);
		enemyKing = p;
		
		// peasants
		for(int i = 0; i < grid.length; i++){
			if(i <= (cells * 2) - 1 && i >= cells || i >= (cells * 6) && i <= (cells * 7) - 1 ){
				
				if(i < grid.length / 2){
					//enemy pieces type is 0
					p = new Peasant(cells, i, 0);
					grid[i] = p;
					enemyPieces.add(p);
					
				}else if(i > grid.length / 2){
					// player pieces type is 1
					p = new Peasant(cells, i, 1);
					grid[i] = p;
					playerPieces.add(p);
				}
			}
		}
														//Player pieces 
		// Tower
		p = new Tower(cells, 56, 1);
		grid[56] = p;
		playerPieces.add(p);
		p = new Tower(cells, 63, 1);
		grid[63] = p;
		playerPieces.add(p);
		
		// Horse
		p = new Horse(cells, 57, 1);
		grid[57] = p;
		playerPieces.add(p);
		p = new Horse(cells, 62, 1);
		grid[62] = p;
		playerPieces.add(p);
		
		// Knight
		p = new Knight(cells, 58, 1);
		grid[58] = p;
		playerPieces.add(p);
		p = new Knight(cells, 61, 1);
		grid[61] = p;
		playerPieces.add(p);
		
		// Queen
		p = new Queen(cells, 60, 1);
		grid[60] = p;
		playerPieces.add(p);
		
		// King
		p = new King(cells, 59, 1);
		grid[59] = p;
		playerPieces.add(p);
		playerKing = p;
	}
	
	// game will run here
	
	static boolean playerTurn = true;
	private static void beginGame(){
		fillGrid();
		ai = new Ai();
		while(playerPieces.contains(playerKing) && enemyPieces.contains(enemyKing)){
			
			frame.repaint();
			frame.revalidate();
			frame.setBoard(grid);
			frame.setX(x);
			frame.setY(y);
			if(key.getKeys()[78] == true){
				fillGrid();
				playerTurn = true;
			}
			updateGrid();
			if(!playerTurn){
				ai.getMove(grid, enemyPieces, playerPieces);			
				playerTurn = true;
			}
			updateGrid();
		}
		if(playerPieces.contains(playerKing)) System.out.println("You Win!");
		else{
			System.out.println("Computer Wins!");
		}
	}
	
	
	public static void main(String args[]){
		cells = 8;
		grid = new Piece[cells*cells];
		playerPieces = new ArrayList<Piece>();
		enemyPieces = new  ArrayList<Piece>();
		frame = new Frame(cells, grid);
		
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e){
				x = e.getX();
				y = e.getY();
			}
		});
													// Player input comes from mouse-events
		frame.addMouseListener(new MouseAdapter() {
			boolean playerSelected = false;
			int SelectedPlayer = -1;
			
			public void mouseExited(MouseEvent e){
				x = frame.getHeight();
				y = frame.getWidth();
			}
			
			public void mouseClicked(MouseEvent e){
				index = (x / (frame.getWidth() / cells)) + (y / (frame.getHeight()/ cells)) * cells;
													// right-click
				if(e.getButton() == 3){
					frame.clearMoves();
					playerSelected = false;
				}				
				else if(e.getButton() == 1){
					
													// Choose player and give frame info to draw
					if (!playerSelected && playerTurn){
						if(grid[index] != null && grid[index].getType() == 1){
							frame.setMoves(grid[index].getValidMoves(grid));
							SelectedPlayer = index;
							playerSelected = true;
						}else{
													// If the wrong cell is picked, remove info from frame
							frame.clearMoves();
						}
						
					}else if (playerSelected && playerTurn){
						try{
							if (grid[SelectedPlayer].getValidMoves(grid).contains(index)){
														// remove enemy piece from memory if they clash!
								if (grid[SelectedPlayer].isEnemyAt(index, grid)){
									enemyPieces.remove(grid[index]);
								}
								System.out.println("Player: " + SelectedPlayer + " - " + index);
								grid[SelectedPlayer].makeMove(index);
								playerTurn = false;
							}	
						}catch(NullPointerException e1){
							System.out.println(SelectedPlayer);
						}
						playerSelected = false;
						frame.clearMoves();
					}
				}
			}
		});
		while(true){
			beginGame();
			
		}
	}
}