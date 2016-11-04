import java.util.Scanner;

public class Logic {
	
	private static Piece[] grid;
	private static int cells;
	
	private static void printGrid(){
		// uppdate the whole grid before printing
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
		for(int i = 0; i < grid.length; i++){
			System.out.println(i >= (cells * 6) - 1 && i < (cells * 7));
			if(i <= (cells * 2) - 1 && i >= cells ||
			   i >= (cells * 6) && i <= (cells * 7) - 1 ){
				if(i < grid.length / 2){
					grid[i] = new Peasant(cells, i, 0);					
				}else if(i > grid.length / 2){
					System.out.println("true");
					grid[i] = new Peasant(cells, i, 1);
				}
			}
		}
	}
	
	// game will run here
	private static void beginGame(){
		fillGrid();
	}
	
	static Scanner userInput = new Scanner(System.in);
	static int pos, dest;
	private static void getPlayerMove(){
		System.out.print("Enter piece position");
		pos = Integer.parseInt(userInput.next());
		System.out.println("Avaliable moves are: " + grid[pos].getValidMoves(grid));
		System.out.print("Enter piece destination");
		dest = Integer.parseInt(userInput.next());
		
		grid[pos].makeMove(dest);
	}
	
	public static void main(String args[]){
		cells = 8;
		grid = new Piece[cells*cells];
		
		fillGrid();
		printGrid();
		getPlayerMove();
		printGrid();
	}
}
