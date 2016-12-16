import java.util.*;

public class Game {
	Boolean white_can_castle_king_side = true;
	Boolean white_can_castle_queen_side = true;
	Boolean black_can_castle_king_side = true;
	Boolean black_can_castle_queen_side = true;
	Boolean afoot = true;
	String board[][] = new String[8][8];
	Color turn;
	Color client_color;
	int en_passant[] = new int[]{-1, -1};
	int id;
	
	public Game(int id, String[][] board, Color turn, Color client_color){
		this.id = id;
		this.board = board;
		this.turn = turn;
		this.client_color = client_color;
	}
	
	public List<Move> allPossibleMoves(){
		List<Move> moves = new ArrayList<Move>();
		PieceFactory pf = new PieceFactory();
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				String cell = board[i][j];
				if (!cell.equals("")){
					int[] position = new int[]{i, j};
					if (Character.isUpperCase(cell.charAt(0))){
						if(client_color == Color.White){
							//piece belongs to player
							Piece piece = pf.createPiece(cell, position);
							List<Move> piece_moves = piece.allPossibleMoves(this);
							moves.addAll(piece_moves);
						}
					}else{
						if(client_color == Color.Black){
							//piece belongs to player
							Piece piece = pf.createPiece(cell, position);
							List<Move> piece_moves = piece.allPossibleMoves(this);
							moves.addAll(piece_moves);
						}
					}
				}
			}
		}
		return moves;
	}
	
	//moves are generated in the form [0,1, 0,0] (to: 0,0, from: 0,1)
	public Move generateDefaultMove(){
		//find the first piece belonging to player that can be moved and move it 
		//TODO: check for pieces not pawns...
		List<Move> moves = allPossibleMoves();
		if (moves.isEmpty()){
			return null;
		}
		else{
			Random generator = new Random();
			int i = generator.nextInt(moves.size());
			return moves.get(i);
		}
	}
	
	public Color colorOfPieceInCell(int i, int j){
		String piece_in_cell = board[i][j];
		if (piece_in_cell.equals("")){
			return Color.NoColor;
		}else if (Character.isUpperCase(piece_in_cell.charAt(0))){
			return Color.White;
		}
		else{
			return Color.Black;
		}
	}
}
