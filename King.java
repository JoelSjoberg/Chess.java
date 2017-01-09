import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class King implements Piece{

	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	ArrayList<Integer> availableMoves = new ArrayList<Integer>();
	
	public King(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(getClass().getResource("/ka.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(getClass().getResource("/ke.png"));
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
		
		if(position % cells != 0){
			availableMoves.add(position - 1);
			availableMoves.add(position - cells - 1);
			availableMoves.add(position + cells - 1);			
		}
		if((position + 1) % cells != 0){
			availableMoves.add(position - cells + 1);
			availableMoves.add(position + 1);
			availableMoves.add(position + cells + 1);			
		}
		
		availableMoves.add(position - cells);
		availableMoves.add(position + cells);
		
		for(int i = availableMoves.size() - 1; i > -1; i--){
			if(!(availableMoves.get(i) < cells * cells && availableMoves.get(i) > -1)){
				availableMoves.remove(i);
			}else if(board[availableMoves.get(i)] != null && board[availableMoves.get(i)].getType() == this.type){
				availableMoves.remove(i);
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
		if(this.type == 1)return "k" ;
		else return "K";
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