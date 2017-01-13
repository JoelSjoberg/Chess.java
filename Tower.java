import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tower implements Piece<Object>{

	private int position;
	private int cells;
	private int type;
	private Image ally;
	private Image enemy;
	int numDirections = 4;
	ArrayList<Integer> availableMoves;
	ArrayList<Integer> equation;
	ArrayList<Boolean> direction;
	
	public Tower(int c, int pos, int t){
		cells = c;
		position = pos;
		type = t;
		try {
			ally = ImageIO.read(getClass().getResource("/ta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			enemy = ImageIO.read(getClass().getResource("/te.png"));
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
			// up
			equation.add(position - (cells * i));
			// down
			equation.add(position + (cells * i));
			// Left
			equation.add(position - i);
			// right
			equation.add(position + i);
			
			for (int j = 0; j < direction.size(); j++){
				if (direction.get(j) && equation.get(j) > -1 && equation.get(j) < cells * cells){
					if (board[equation.get(j)] != null && board[equation.get(j)].getType() == this.type){
						direction.set(j, false);
					}else if(equation.get(j) == position - i && equation.get(j) % cells == 7){
						direction.set(j, false);
					}else if(equation.get(j) == position + i && equation.get(j) % cells == 0){
						direction.set(j, false);
					}else if (isEnemyAt(equation.get(j), board)){
						direction.set(j, false);
						availableMoves.add(equation.get(j));					
					}else {
						availableMoves.add(equation.get(j));
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
		if(this.type == 1)return "t" ;
		else return "T";
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