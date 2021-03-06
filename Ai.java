import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

// Version 2.0
/*
 	AI will kill player pieces if available
 	else it will make a random move

 */


public class Ai {
	
	int pos;
	int dest;
	Iterator<Integer> iterator;
	ArrayList<Piece> pieces;
	Random rand;
	
	public void getMove(Piece[] board, ArrayList<Piece> enemies, ArrayList<Piece> allies){
		// priority case
		for (int i = 0; i < enemies.size(); i++){
			iterator = enemies.get(i).getValidMoves(board).iterator();
			
			while(iterator.hasNext()){
				dest = iterator.next();
				if(enemies.get(i).isEnemyAt(dest, board)){
					if(allies.contains(board[dest]))allies.remove(board[dest]);
					enemies.get(i).makeMove(dest);
					System.out.println("COMP: " + pos+  " - " + dest);
					return;
				}
			}
		}
		
		pos = -1; dest = -1;						// pos and dest need to be > -1
		pieces = new ArrayList<Piece>(enemies);		// copy of enemy pieces for looping and removing
		rand = new Random();						// for random selection
		// if all the other priorities fail, make a random move
		while(pos == -1 && dest == -1){
			pos = -1; dest = -1;
			
			pos = pieces.get(rand.nextInt(pieces.size())).getPosition();
			
			if (board[pos].getValidMoves(board).size() == 0){
				pieces.remove(board[pos]);
				pos = -1;
				continue;
			}else{
				dest = (int) board[pos].getValidMoves(board).get(rand.nextInt(board[pos].getValidMoves(board).size()));
			}
			System.out.println("COMP: " + pos+  " - " + dest);
			if(allies.contains(board[dest]))allies.remove(board[dest]);
			board[pos].makeMove(dest);
			return;
		}	
	}
}
