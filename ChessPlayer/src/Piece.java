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
		    Move m = iterator.next();
		    if (game.colorOfPieceInCell(m.to[0], m.to[1]) == color){
		    	//if move is to a cell with a friendly piece... remove that sucka...
		    	System.out.println("removing " + "From: [" + m.from[0] + "," + m.from[1] + "], " + "To: [" + m.to[0] + "," + m.to[1] + "]");
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
	
	public Boolean doesThreaten(Game game, int[] position){
		List<Move> threateningMoves = threatens(game);
		for(Move m : threateningMoves){
			if (m.to[0] == position[0] && m.to[1] == position[1]){
				return true;
			}
		}
		return false;
	}
	
	public Game makeMove(Move move, Game game){
		Game ng = new Game(game);
		for (Piece p : (color == Color.White ? ng.white_pieces : ng.black_pieces)){
			if (p.position[0] == move.from[0] && p.position[1] == move.from[1]){
				p.position[0] = move.to[0];
				p.position[1] = move.to[1];
				ng.board[move.from[0]][move.from[1]] = "";
				ng.board[move.to[0]][move.to[1]] = (p.color == Color.White ? "P" : "p");
			}
		}
		//iterate through opponent's pieces...
		for (Iterator<Piece> iterator = (color == Color.White ? ng.black_pieces.iterator() : ng.white_pieces.iterator()); iterator.hasNext();) {
		    Piece p = iterator.next();
		    //if piece is being attacked on this move...
		    if (p.position[0] == move.to[0] && p.position[1] == move.to[1]){
				iterator.remove();
			}
		}
		ng = makeMoveSpecificDetails(move, ng);
		ng.turn = (ng.turn == Color.White ? Color.Black : Color.White);
		return ng;
	}
	
	//returns a new game object after a specific move has been made in a game
	
	public abstract List<Move> allPossibleMoves(Game game);
	public abstract List<Move> threatens(Game game);
	public abstract Game makeMoveSpecificDetails(Move move, Game ng);
}
