import java.util.*;

public class Knight extends Piece {
	public Knight(Color color, int[] position){
		super(color, position);
	}
	public List<Move> allPossibleMoves(Game game){
		//all moves that would be valid on a blank board
		List<Move> moves = blankBoardMoves();
		//removes moves that move to space occupied by friendly piece
		//System.out.println(color == Color.White ? "White" : (color == Color.Black ? "Black" : "Neither"));
		removeFriendlyTargetMoves(moves, game);
		//removes moves that would leave king in check
		removeStillInCheckMoves(moves, game);
		return moves;
	}
	
	public List<Move> blankBoardMoves(){
		List<Move> moves = new ArrayList<Move>();
		moves.add(new Move(position[0], position[1], position[0] + 1, position[1] + 2));
		moves.add(new Move(position[0], position[1], position[0] + 1, position[1] - 2));
		moves.add(new Move(position[0], position[1], position[0] + 2, position[1] + 1));
		moves.add(new Move(position[0], position[1], position[0] + 2, position[1] - 1));
		moves.add(new Move(position[0], position[1], position[0] - 1, position[1] + 2));
		moves.add(new Move(position[0], position[1], position[0] - 1, position[1] - 2));
		moves.add(new Move(position[0], position[1], position[0] - 2, position[1] + 1));
		moves.add(new Move(position[0], position[1], position[0] - 2, position[1] - 1));
		for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext();) {
		    Move move = iterator.next();
		    if (move.to[0] > 7 || move.to[0] < 0 || move.to[1] > 7 || move.to[1] < 0){
		    	//if move is out of bounds, remove it...
		    	iterator.remove();
			}

		}
		return moves;
	}
	public List<Move> threatens(Game game){
		List<Move> moves = blankBoardMoves();
		return moves;
	}
	public Game makeMoveSpecificDetails(Move move, Game ng){
		ng.board[move.to[0]][move.to[1]] = (color == Color.White ? "N" : "n");
		ng.en_passant[0] = -1;
		ng.en_passant[1] = -1;
		return ng;
	}
}
