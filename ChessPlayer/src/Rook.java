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
	
	public List<Move> threatens(Game game){
		//TODO
		List<Move> moves = new ArrayList<Move>();
		return moves;
	}
	public Boolean doesThreaten(Game game, int[] position){
		//TODO
		return false;
	}
	public Game makeMove(Move move, Game game){
		//TODO
		return game;
	}
}
