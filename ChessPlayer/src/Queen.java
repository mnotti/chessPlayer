import java.util.ArrayList;
import java.util.List;


public class Queen extends Piece {
	public Queen(Color color, int[] position){
		super(color, position);
	}
	public List<Move> allPossibleMoves(Game game){
		List<Move> moves = threatens(game);
		removeFriendlyTargetMoves(moves, game);
		removeStillInCheckMoves(moves, game);
		return moves;
	}
	public List<Move> threatens(Game game){
		List<Move> moves = new ArrayList<Move>();
		//up/left
		int i = position[0] - 1;
		int j = position[1] - 1;
		Boolean collision_detected = false;
		while(i >= 0 && j >= 0 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			i--;
			j--;
		}
		//up/right
		i = position[0] - 1;
		j = position[1] + 1;
		collision_detected = false;
		while(i >= 0 && j < 8 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			i--;
			j++;
		}
		
		//down/left
		i = position[0] + 1;
		j = position[1] - 1;
		collision_detected = false;
		while(i < 8 && j >= 0 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			i++;
			j--;
		}
		
		//down/right
		i = position[0] + 1;
		j = position[1] + 1;
		collision_detected = false;
		while(i < 8 && j < 8 && !collision_detected){
			if(!game.board[i][j].equals("")){
				collision_detected = true;
			}
			moves.add(new Move(position[0], position[1], i, j));
			i++;
			j++;
		}
		
		//threatens horizontally +...
		i = position[0];
		j = position[1] + 1;
		collision_detected = false;
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
		ng.board[move.to[0]][move.to[1]] = (color == Color.White ? "Q" : "q");
		ng.en_passant[0] = -1;
		ng.en_passant[1] = -1;
		return ng;
	}
	
}
