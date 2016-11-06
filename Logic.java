import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Logic {
	
	private static Piece[] grid;
	static ArrayList<Piece> playerPieces, enemyPieces;
	private static int cells;
	
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
		for(int i = 0; i < grid.length; i++){
			if(i <= (cells * 2) - 1 && i >= cells || i >= (cells * 6) && i <= (cells * 7) - 1 ){
				if(i < grid.length / 2){
					p = new Peasant(cells, i, 0);
					grid[i] = p;
					enemyPieces.add(p);
				}else if(i > grid.length / 2){
					p = new Peasant(cells, i, 1);
					grid[i] = p;
					playerPieces.add(p);
				}
			}
		}
	}
	
	// Take input from the player, variables needed for value controll
	static Scanner userInput = new Scanner(System.in);
	static int pos, dest;
	private static void getPlayerMove(){
		try{
			// show the available options and get the selected piece
			System.out.print("Available options: ");
			for(int i = 0; i < playerPieces.size(); i++){
				System.out.print(playerPieces.get(i).getPosition() + ", ");
			}
			System.out.println();
			System.out.print("Enter piece position: ");
			pos = Integer.parseInt(userInput.next());
			if(!playerPieces.contains(grid[pos]))
				throw new NullPointerException();
			
			// if there are no available moves: loop
			if(grid[pos].getValidMoves(grid).size() == 0)throw new ArrayIndexOutOfBoundsException();

			// Get the destination of the selected piece
			System.out.println("Avaliable moves are: " + grid[pos].getValidMoves(grid));
			System.out.print("Enter piece destination: ");
			dest = Integer.parseInt(userInput.next());
			
			// if the input destination is not in the available moves: loop
			if(!(grid[pos].getValidMoves(grid).contains(dest)))throw new NullPointerException();
			
		}catch(NullPointerException e){
			System.out.println("Invalid move! Pick again");
			getPlayerMove();
		}catch(NumberFormatException e1){
			System.out.println("Invalid input type! Pick again");
			getPlayerMove();
		}catch(ArrayIndexOutOfBoundsException e2){
			System.out.println("This piece cannot move any further");
			getPlayerMove();
		}
		grid[pos].makeMove(dest);
		// if an enemy is at the destination, remove it
		if(grid[dest] != null && grid[dest].getType() == 0){
			enemyPieces.remove(grid[dest]);
		}
	}// end of getPLayerMove()
	
	// the AI controll
	private void getCompMove(){
		
		
		
	}// end of getCompMove()
	
	// game will run here
	private static void beginGame(){
		fillGrid();
		while(true){
			printGrid();
			getPlayerMove();
		}
	}
	
	public static void main(String args[]){
		cells = 8;
		grid = new Piece[cells*cells];
		playerPieces = new ArrayList<Piece>();
		enemyPieces = new  ArrayList<Piece>();
		beginGame();
	}
}
