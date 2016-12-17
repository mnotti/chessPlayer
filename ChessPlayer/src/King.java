import java.util.ArrayList;
import java.util.List;


public class King extends Piece {
	public King(Color color, int[] position){
		super(color, position);
	}
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		//first determine all moves for individual pieces...
		//then eliminate possibilities (if necessary)
		return moves;
	}
	public List<Move> threatens(Game game){
		//TODO
		List<Move> moves = new ArrayList<Move>();
		return moves;
	}

	public Game makeMoveSpecificDetails(Move move, Game ng){
		//TODO
		return ng;
	}
	
}
