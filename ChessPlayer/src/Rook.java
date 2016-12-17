import java.util.ArrayList;
import java.util.List;


public class Rook extends Piece {
	public Rook(Color color, int[] position){
		super(color, position);
	}
	
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		moves = threatens(game);
		removeFriendlyTargetMoves(moves, game);
		removeStillInCheckMoves(moves, game);
		return moves;
	}
	
	//includes friendly squares...
	public List<Move> threatens(Game game){
		List<Move> moves = new ArrayList<Move>();
		//threatens horizontally +...
		int i = position[0];
		int j = position[1] + 1;
		Boolean collision_detected = false;
		while(j < 8 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			j++;
		}
		
		//horizontally -...
		i = position[0];
		j = position[1] - 1;
		collision_detected = false;
		while(j >= 0 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			j--;
		}
		//threatens vertically +...
		i = position[0] + 1;
		j = position[1];
		collision_detected = false;
		while(i < 8 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			i++;
		}
		//vertically -...
		i = position[0] - 1;
		j = position[1];
		collision_detected = false;
		while(i >= 0 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			i--;
		}
		
		return moves;
	}

	public Game makeMoveSpecificDetails(Move move, Game ng){
		ng.en_passant[0] = -1;
		ng.en_passant[1] = -1;
		//castling stuff
		if (color == Color.White){
			if(move.from[0] == 7 && move.from[1] == 0){
				ng.white_can_castle_king_side = false;
			}else if(move.from[0] == 7 && move.from[1] == 7){
				ng.white_can_castle_queen_side = false;
			}
		}else{
			if(move.from[0] == 0 && move.from[1] == 0){
				ng.black_can_castle_king_side = false;
			}else if(move.from[0] == 0 && move.from[1] == 7){
				ng.black_can_castle_queen_side = false;
			}
		}
		return ng;
	}
}
