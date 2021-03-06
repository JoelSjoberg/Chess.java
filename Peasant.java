import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Peasant implements Piece<Object>{
	
	private int timesMoved;
	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	ArrayList<Integer> availableMoves = new ArrayList<Integer>();
	
	
	public Peasant(int c, int pos, int t){
		timesMoved = 0;
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(getClass().getResource("/peasanta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(getClass().getResource("/peasante.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	// this is where "type" is essential to see if there is an ally or an enemy
	public boolean isEnemyAt(int position, Piece[] board) {
		try{
			if(board[position] != null &&
					board[position].getType() != this.type) return true;			
		}catch(ArrayIndexOutOfBoundsException e){
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList getValidMoves(Piece[] board) {
		availableMoves = new ArrayList<Integer>();
		
							// reminder: type = 1 : ally
		if (this.type == 1){
							// can take one step by default if no piece is in the way
			if (position - cells > -1 && board[position - cells] == null){
				availableMoves.add(position - cells);
			}
							// peasant can move 2 steps the first move if no piece is in the way
			if (timesMoved == 0 && board[position - (cells * 2)] == null && board[position - cells] == null){
				availableMoves.add(position - (cells * 2));
			}
							// if there is an opponent to the upper left or right, you can kill it
			if (position - cells - 1 > -1 && isEnemyAt((position - cells - 1), board) && position % cells != 0){
				availableMoves.add(position - cells - 1);
			}
			
			if (position - cells + 1 > -1 && isEnemyAt((position - cells + 1), board) && (position + 1) % cells != 0){
				availableMoves.add(position - cells + 1);
			}
							// catch edge cases if the piece wants to jump outside of the board!
			for (int i = availableMoves.size() - 1; i > -1; i--){
				if (availableMoves.get(i) >= board.length || availableMoves.get(i) < 0){
					availableMoves.remove(i);
				}
			}
			
		}
		
		else if(this.type == 0){
							// enemy Peasant (type = 0)
			if (position + cells < cells*cells && board[position + cells] == null){
				availableMoves.add(position + cells);
			}
							// peasant can move 2 steps the first move if no piece is in the way
			if ((timesMoved == 0 && board[position + (cells * 2)] == null) && board[position + cells] == null){
				availableMoves.add(position + (cells * 2));
			}
							// if there is an opponent to the lower left or right, you can kill it
			if ( position + cells - 1 < cells*cells && isEnemyAt((position + cells - 1), board) && position % cells != 0){
				availableMoves.add(position + cells - 1);
			}
			
			if ( position + cells + 1 < cells*cells && isEnemyAt((position + cells + 1), board) && (position + 1) % cells != 0){
				availableMoves.add(position + cells + 1);
			}
							// catch edge cases if the piece wants to jump outside of the board!
			for (int i = availableMoves.size() - 1; i > -1; i--){
				if (availableMoves.get(i) >= board.length || availableMoves.get(i) < 0){
					availableMoves.remove(i);
				}
			}
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
		if(this.type == 1)return "i" ;
		else return "I";
	}

	@Override
	public int getPosition() {
		return this.position;
	}
	public void setPosition(int pos){
		this.position = pos;
	}
	
	public Image getImage(){
		if(this.type == 1){
			return this.ally;
		}else{
			return this.enemy;
		}
	}
}