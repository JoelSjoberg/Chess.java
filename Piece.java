import java.util.Collection;

/**
 * Breaking ISP:
 * In interface segregation principle, we make multiple interfaces for each game piece to implement
 * In this case it would needlessly increase the amount of files in the project
 * since all pieces will need all these methods.
 * @author Joel
 *
 */

public interface Piece<T> {
	boolean isEnemyAt(int position, Piece[] board);
	Collection<T> getValidMoves(Piece[] board);
	void makeMove(int position);
	int getType();
	int getPosition();
	void setPosition(int pos);
}
