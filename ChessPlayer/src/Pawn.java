import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Pawn extends Piece {
	public Pawn(Color color, int[] position){
		super(color, position);
	}
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		List<Move> attackingMoves = threatens(game);
		//remove attacking moves that aren't attacking an enemy piece (or en_passant)
		filterAttackingMoves(attackingMoves, game);
		moves.addAll(attackingMoves);
		//determine all non attacking moves...
		List<Move> blank_board_moves = blankBoardMoves(game);
		removeBlockedMoves(blank_board_moves, game);
		moves.addAll(blank_board_moves);
		//then eliminate possibilities (if necessary)
		removeFriendlyTargetMoves(moves, game);
		removeStillInCheckMoves(moves, game);
		
		return moves;
	}
	public List<Move> blankBoardMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		if(color == Color.White){
			moves.add(new Move(position[0], position[1], position[0] - 1, position[1]));
			if(position[0] == 6){
				moves.add(new Move(position[0], position[1], position[0] - 2, position[1]));
			}
		}else{
			moves.add(new Move(position[0], position[1], position[0] + 1, position[1]));
			if(position[0] == 1){
				moves.add(new Move(position[0], position[1], position[0] + 2, position[1]));
			}
		}
		return moves;
	}
	
	public void removeBlockedMoves(List<Move> moves, Game game){
		for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext();) {
		    Move m = iterator.next();
			if (!game.board[m.to[0]][m.to[1]].equals("")){
				iterator.remove();
			}
			else if (Math.abs(m.from[0] - m.to[0]) == 2){
				int checkX = m.from[0] + (m.to[0] - m.from[0])/2;
				if (!game.board[checkX][m.from[1]].equals("")){
					iterator.remove();
				}
			}
		}
	}
	
	//remove moves that aren't attacking an enemy piece OR aren't attacking an en_passant square OR isn't on the board
	public void filterAttackingMoves(List<Move> attackingMoves, Game game){
		for (Iterator<Move> iterator = attackingMoves.iterator(); iterator.hasNext();) {
		    Move m = iterator.next();
		    //if off of board
		    if (m.to[1] > 7 || m.to[1] < 0){
		    	iterator.remove();
		    }else{
		    	//if not attacking a piece
		    	String attacked_cell = game.board[m.to[0]][m.to[1]];
				if (attacked_cell.equals("")){
					//now check if en_passant
					if (!(game.en_passant[0] == m.to[0] && game.en_passant[1] == m.to[1]))
						iterator.remove();
				}else if (Character.isUpperCase(attacked_cell.charAt(0))){
					if (color == Color.White){
						iterator.remove();
					}
				}else{
					if (color == Color.Black){
						iterator.remove();
					}
				}
		    }
		}
	}
	
	//all moves (in effect squares) that the piece threatens
	public List<Move> threatens(Game game){
		List<Move> moves = new ArrayList<Move>();
		if(color == Color.White){
			moves.add(new Move(position[0], position[1], position[0] - 1, position[1] + 1));
			moves.add(new Move(position[0], position[1], position[0] - 1, position[1] - 1));
		}else{
			moves.add(new Move(position[0], position[1], position[0] + 1, position[1] + 1));
			moves.add(new Move(position[0], position[1], position[0] + 1, position[1] - 1));
		}
		return moves;
	}

	public Game makeMoveSpecificDetails(Move move, Game ng){
		ng.board[move.to[0]][move.to[1]] = (color == Color.White ? "P" : "p");
		
		if (Math.abs(move.to[0] - move.from[0]) == 1){
			ng.en_passant[0] = -1;
			ng.en_passant[1] = -1;
		}else{
			ng.en_passant[0] = move.from[0] + (move.to[0] - move.from[0])/2;
			ng.en_passant[1] = move.from[1];
		}
		return ng;
	}
	
}

