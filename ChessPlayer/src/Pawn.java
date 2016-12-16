import java.util.ArrayList;
import java.util.List;


public class Pawn extends Piece {
	public Pawn(Color color, int[] position){
		super(color, position);
	}
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		//first determine all moves for individual pieces...
		//then eliminate possibilities (if necessary)
		return moves;
	}
}
