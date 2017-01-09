import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Knight implements Piece{
	
	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	int numDirections = 4;
	ArrayList<Integer> availableMoves;
	ArrayList<Integer> equation;
	ArrayList<Boolean> direction;
	
	
	
	public Knight(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(getClass().getResource("/pa.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(getClass().getResource("/pe.png"));
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
		for(int i = 0; i < numDirections; i++){
			direction.add(true);
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
			
			for (int j = 0; j < direction.size(); j++){
				if (direction.get(j) && equation.get(j) > -1 && equation.get(j) < cells * cells){
					if (board[equation.get(j)] != null && board[equation.get(j)].getType() == this.type){
						direction.set(j, false);
					
					}else if((equation.get(j) + 1) % cells == 0){
						direction.set(j, false);
						if (position % cells != 0) availableMoves.add(equation.get(j));
					
					}else if((equation.get(j)) % cells == 0){
						direction.set(j, false);
						if (position % cells != 7) availableMoves.add(equation.get(j));
						
					}else if (isEnemyAt(equation.get(j), board)){
						direction.set(j, false);
						availableMoves.add(equation.get(j));					
					}else {
						availableMoves.add(equation.get(j));
					}
				}
			}
		}
		
		for(int i = availableMoves.size() - 1; i > -1; i--){
			//	check if the moves are inside the board
			if(availableMoves.get(i) > cells * cells - 1 || availableMoves.get(i) < 0 ||
			// 	or there is an ally piece on destination
			(board[availableMoves.get(i)] != null &&board[availableMoves.get(i)].getType() == this.type))
				availableMoves.remove(i);
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
		if(this.type == 1)return "p" ;
		else return "P";
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