import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Peasant implements Piece{
	
	private int timesMoved;
	private int position;
	private int cells;
	private int type;
	
	
	public Peasant(int c, int pos, int t){
		timesMoved = 0;
		cells = c;
		position = pos;
		type = t;
	}
	
	@Override
	// this is where "type" is essential to see if there is an ally or an enemy
	public boolean isEnemyAt(int position, Piece[] board) {
		if(board[position] != null &&
				board[position].getType() != type) return true;			
		return false;
	}
	
	@Override
	public Collection<Integer> getValidMoves(Piece[] board) {
		
		List<Integer> availableMoves = new ArrayList<Integer>();
		// can take one step by default
		if(!(isEnemyAt((position - cells), board))){
			availableMoves.add(position - cells);
		}
		// peasant can move 2 steps the first move
		if(timesMoved == 0 && !(isEnemyAt((position - (cells * 2)), board))){
			availableMoves.add(position - (cells * 2));
		}
		// if there is an opponent to the upper left or right, you can kill it
		// and check edge cases!(try in case of empty squares).
		if(isEnemyAt((position - cells - 1), board) && position % cells != 0){
			availableMoves.add(position - cells - 1);
		}
		if(isEnemyAt((position - cells + 1), board) && position % (cells-1) != 0){
			availableMoves.add(position - cells + 1);
		}			
		return availableMoves;
	}

	@Override
	public void makeMove(int destination) {
		timesMoved++;
		position = destination;
	}

	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public String toString(){
		//return "Peasant at: " + position;
		return "i" ;
	}

	@Override
	public int getPosition() {
		return this.position;
	}
}