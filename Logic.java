import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Logic{
	
	private static Piece[] grid;
	static ArrayList<Piece> playerPieces;
	static ArrayList<Piece> enemyPieces;
	private static int cells;
	private static Frame frame;
	
	private static void printGrid(){
		// update the whole grid before printing
		for(int i = 0; i < grid.length; i++){
			if(grid[i] != null && grid[i].getPosition() != i){
				grid[grid[i].getPosition()] = grid[i];
				grid[i] = null;
			}
		}
		
		// print the game board and pieces
		for(int i = 0; i < grid.length; i++){
			if(i % 8 == 0){
				System.out.println();
			}
			if(grid[i] != null){
				System.out.print("|"+grid[i]+"|");				
			}else{
				System.out.print("|_|");
			}
		}
		System.out.println();
		System.out.println("________________________");
	}
	
	// fill the grid with pieces at the beginning of the game
	private static void fillGrid(){
		Peasant p;

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
	}
	
	// Take input from the player, variables needed for value controll
	static Scanner userInput = new Scanner(System.in);
	static Random rand = new Random();
	static int pos, dest;
	static boolean checker;
	
	private static void getPlayerMove(){
		checker = true;
		do {
			try { // for NumberFormatException
				// show the available options and get the selected piece
				System.out.print("Available options: ");
				for (int i = 0; i < playerPieces.size(); i++){
					System.out.print(playerPieces.get(i).getPosition() + ", ");
				}
				
				//get user input
				System.out.println();
				System.out.print("Enter piece position: ");
				pos = Integer.parseInt(userInput.next());
				
				// if there is no piece at given position
				try {
					if (!playerPieces.contains(grid[pos])) throw new ArrayIndexOutOfBoundsException();
					
				} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("Position is outside of the board!");
					checker = false;
					continue;				
				}
				
				// if there are no available moves that selected piece can make
				try {
					if (grid[pos].getValidMoves(grid).size() == 0) throw new ArrayIndexOutOfBoundsException();				
				
				} catch(ArrayIndexOutOfBoundsException e){
					System.out.println("This piece cannot move further!");
					checker = false;
					continue;
				}
				
				//Show the available destinations, get the destination of the selected piece
				System.out.println("Avaliable moves are: " + grid[pos].getValidMoves(grid));
				System.out.print("Enter piece destination: ");
				dest = Integer.parseInt(userInput.next());
				
				// if the input destination is not in the available moves: loop
				if (!(grid[pos].getValidMoves(grid).contains(dest))){
					System.out.println("Invalid destination! Pick again!");
					checker = false;
					continue;
				} else checker = true;
			}catch(NumberFormatException e){
				System.out.println("Enter only numbers!");
				checker = false;
				continue;
			}
				
		}while(!checker);
		// Make the move
		grid[pos].makeMove(dest);
		
		// if an enemy is at the destination, remove it
		if(grid[dest] != null && grid[dest].getType() == 0){
			enemyPieces.remove(grid[dest]);
		}
	
	}// end of getPLayerMove
	
	
	// the AI controll
	private static void getCompMove(){
		
		
		/* AI algorithm
		 * 
		 1. pos = enemiesCopy.get(rand.nextInt(enemiesCopy.size())).getPosition();		
		 
		 2. if (grid[pos].getValidMoves(grid).size() == 0) throw new ArrayIndexOutOfBoundsException();

		 3. dest = (int) grid[pos].getValidMoves(grid).get(rand.nextInt(grid[pos].getValidMoves(grid).size()));
		 
		 4. if (!(grid[pos].getValidMoves(grid).contains(dest))) throw new ArrayIndexOutOfBoundsException();
		 
		 
		 */
		// enemiesCopy is used to reduce load time for ai
		@SuppressWarnings("rawtypes")
		ArrayList<Piece> enemiesCopy = new ArrayList<Piece>(enemyPieces);
		System.out.println("Computers turn!");
		do {
			checker = true;
			// Select a random piece
			
			try {// IllegalArgumentException will appear if size = 0
				System.out.println(enemiesCopy.size());
				pos = enemiesCopy.get(rand.nextInt(enemiesCopy.size())).getPosition();								
			
			} catch(IllegalArgumentException e){
				System.out.println("AI cannot make any moves");
				pos = -1; dest = -1;
				break;
			}
			
			// if there are no available moves: loop
			try {
				if (grid[pos].getValidMoves(grid).size() == 0) throw new ArrayIndexOutOfBoundsException();
				System.out.println(grid[pos].getValidMoves(grid));
			
			} catch (ArrayIndexOutOfBoundsException e){	
				System.out.println(pos + " " + dest);
				enemiesCopy.remove(grid[pos]);
				checker = false;
				continue;
				
			} catch (NullPointerException e1){
				e1.printStackTrace();
			}
			
			// Select random destination of given moves
			dest = (int) grid[pos].getValidMoves(grid).get(rand.nextInt(grid[pos].getValidMoves(grid).size()));
						
			// if the input destination is not in the available moves: loop
			try {
				if (!(grid[pos].getValidMoves(grid).contains(dest))) throw new ArrayIndexOutOfBoundsException();				
			} catch(ArrayIndexOutOfBoundsException e){
				System.out.println("problem with Dest");
				checker = false;
				continue;	
			}
			grid[pos].makeMove(dest);
			System.out.println("The opponent moved " + pos + " to " + dest);
		} while (!checker);
		
		if (dest != -1 && grid[dest] != null && grid[dest].getType() == 1){
			playerPieces.remove(grid[dest]);
		}
		
	}// end of getCompMove
	
	
	
	// game will run here
	private static void beginGame(){
		fillGrid();
		while(true){
			printGrid();
			frame.setBoard(grid);
			frame.repaint();
			frame.revalidate();
			getPlayerMove();
			printGrid();
			getCompMove();
		}
	}
	
	public static void main(String args[]){
		cells = 8;
		grid = new Piece[cells*cells];
		playerPieces = new ArrayList<Piece>();
		enemyPieces = new  ArrayList<Piece>();
		frame = new Frame(cells, grid);
		beginGame();
	}
}