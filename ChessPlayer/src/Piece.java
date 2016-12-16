import java.util.Iterator;
import java.util.List;


public abstract class Piece {
	Color color;
	int[] position;
	
	public Piece(Color color, int[] position){
		this.color = color;
		this.position = position;
	}
	//removes moves that have friendly targets
	public void removeFriendlyTargetMoves(List<Move> moves, Game game){
		for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext();) {
		    Move move = iterator.next();
		    if (game.colorOfPieceInCell(move.to[0], move.to[1]) == color){
		    	//if move is to a cell with a friendly piece... remove that sucka...
		    	iterator.remove();
			}

		}
	}
	
	//removes moves that leave the king in check
	public void removeStillInCheckMoves(List<Move> moves, Game game){
		for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext();) {
		    Move move = iterator.next();
		    Game ng = makeMove(move, game);
		    for (Piece p : (game.turn == Color.White ? ng.black_pieces : ng.white_pieces)){
		    	if (p.doesThreaten(ng, (game.turn == Color.White ? ng.white_king_position : ng.black_king_position))){
		    		iterator.remove();
		    	}
		    }

		}
	}
	
	//returns a new game object after a specific move has been made in a game
	
	public abstract List<Move> allPossibleMoves(Game game);
	public abstract List<Move> threatens(Game game);
	public abstract Boolean doesThreaten(Game game, int[] position);
	public abstract Game makeMove(Move move, Game game);
}
