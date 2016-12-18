import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class King extends Piece {
	public King(Color color, int[] position){
		super(color, position);
	}
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = threatens(game);
		removeStillInCheckMoves(moves, game);
		removeFriendlyTargetMoves(moves, game);
		return moves;
	}
	public List<Move> threatens(Game game){
		List<Move> moves = new ArrayList<Move>();
		int i = position[0];
		int j = position[1];
		moves.add(new Move(i, j, i, j + 1));
		moves.add(new Move(i, j, i, j - 1));
		moves.add(new Move(i, j, i + 1, j));
		moves.add(new Move(i, j, i + 1, j + 1));
		moves.add(new Move(i, j, i + 1, j - 1));
		moves.add(new Move(i, j, i - 1, j));
		moves.add(new Move(i, j, i - 1, j + 1));
		moves.add(new Move(i, j, i - 1, j - 1));
		//remove out of bounds...
		for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext();) {
		    Move move = iterator.next();
		    if (move.to[0] > 7 || move.to[1] > 7 || move.to[0] < 0 || move.to[1] < 0){
		    	iterator.remove();
		    }
		}
		return moves;
	}

	public Game makeMoveSpecificDetails(Move move, Game ng){
		ng.board[move.to[0]][move.to[1]] = (color == Color.White ? "K" : "k");
		if (color == Color.White){
			ng.white_king_position[0] = move.to[0];
			ng.white_king_position[1] = move.to[1];
			ng.white_can_castle_king_side = false;
			ng.white_can_castle_queen_side = false;
		}else{
			ng.black_king_position[0] = move.to[0];
			ng.black_king_position[1] = move.to[1];
			ng.black_can_castle_king_side = false;
			ng.black_can_castle_queen_side = false;
		}
		ng.en_passant[0] = -1;
		ng.en_passant[1] = -1;
		return ng;
	}
	
}
