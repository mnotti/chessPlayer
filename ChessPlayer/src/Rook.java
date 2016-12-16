import java.util.ArrayList;
import java.util.List;


public class Rook extends Piece {
	public Rook(Color color, int[] position){
		super(color, position);
	}
	
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		//TODO;
		//first determine all moves for individual pieces...
		//then eliminate possibilities (if necessary)
		return moves;
	}
}
