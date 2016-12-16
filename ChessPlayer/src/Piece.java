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
		    if (game.colorOfPieceInCell(move.to[0], move.to[1]) == game.client_color){
		    	//if move is to a cell with a friendly piece... remove that sucka...
		    	iterator.remove();
			}

		}
	}
	
	//removes moves that leave the king in check
	public void removeStillInCheckMoves(List<Move> moves, Game game){
		//TODO
	}
	
	public abstract List<Move> allPossibleMoves(Game game);
}
