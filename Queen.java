import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Queen implements Piece{

	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	int numDirections = 4;
	boolean up, down, left, right;
	ArrayList<Integer> availableMoves;
	ArrayList<Integer> equation;
	ArrayList<Boolean> direction;
	ArrayList<Integer> equation2;
	ArrayList<Boolean> direction2;
	
	public Queen(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(new File("src/img/qa.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(new File("src/img/qe.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
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
		direction = new ArrayList<Boolean>();
		// knight directions
		for(int i = 0; i < numDirections; i++){
			direction.add(true);
		}
		direction2 = new ArrayList<Boolean>();
		// up, down, left, right: Tower move set
		for(int i = 0; i < numDirections; i++){
			direction2.add(true);
		}
		for(int i = 1; i < cells; i++){
			equation = new ArrayList<Integer>();
			// upRight
			equation.add(position - (cells * i) + i);
			// downRight
			equation.add(position + (cells * i) + i);
			// upLeft
			equation.add(position - (cells * i) - i);
			// downLeft
			equation.add(position + (cells * i) - i);
			
			equation2 = new ArrayList<Integer>();
			// up
			equation2.add(position - (cells * i));
			// down
			equation2.add(position + (cells * i));
			// Left
			equation2.add(position - i);
			// right
			equation2.add(position + i);
			for (int j = 0; j < direction.size(); j++){
				
				// Knight moveset
				if (direction.get(j) && equation.get(j) > -1 && equation.get(j) < cells * cells){
					if (board[equation.get(j)] != null && board[equation.get(j)].getType() == this.type){
						direction.set(j, false);

						// on right side of board
					}else if ((equation.get(j) + 1) % cells == 0){
						direction.set(j, false);
						if (position % cells != 0) availableMoves.add(equation.get(j));
					
						// on left side of board
					}else if ((equation.get(j)) % cells == 0){
						direction.set(j, false);
						if (position % cells != 7) availableMoves.add(equation.get(j));
						
					}else if (isEnemyAt(equation.get(j), board)){
						direction.set(j, false);
						availableMoves.add(equation.get(j));
						
					}else {
						availableMoves.add(equation.get(j));
					}
				}
				// Tower moveset
				if (direction2.get(j) && equation2.get(j) > -1 && equation2.get(j) < cells * cells){
					// ally blocks the path
					if (board[equation2.get(j)] != null && board[equation2.get(j)].getType() == this.type){
						direction2.set(j, false);
					// on left side
					}else if(equation2.get(j) == position - i && equation2.get(j) % cells == 7){
						direction2.set(j, false);
					// on right side
					}else if(equation2.get(j) == position + i && equation2.get(j) % cells == 0){
						direction2.set(j, false);
					// stop at, but include enemy
					}else if (isEnemyAt(equation2.get(j), board)){
						direction2.set(j, false);
						availableMoves.add(equation2.get(j));					
					}else {
						availableMoves.add(equation2.get(j));
					}
				}
			}
		}
		return availableMoves;
	}

	@Override
	public void makeMove(int destination) {
		position = destination;
	}

	@Override
	public int getType() {
		return this.type;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(int pos) {
		this.position = pos;
	}
	public String toString(){
		if(this.type == 1)return "q" ;
		else return "Q";
	}


	@Override
	public Image getImage() {
		if(this.type == 1){
			return this.ally;
		}else{
			return this.enemy;
		}
	}

}