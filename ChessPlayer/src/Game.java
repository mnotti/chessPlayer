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
	List<Piece> white_pieces;
	List<Piece> black_pieces;
	int white_king_position[] = new int[]{7, 3};
	int black_king_position[] = new int[]{0, 3};
	
	public Game(int id, String[][] board, Color turn, Color client_color){
		this.id = id;
		this.board = board;
		this.turn = turn;
		this.client_color = client_color;
		this.white_pieces = new ArrayList<Piece>();
		this.black_pieces = new ArrayList<Piece>();
		//generate lists of pieces...
    	//(and king location)
    	PieceFactory pf = new PieceFactory();
		
    	for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				String cell = board[i][j];
				if (!cell.equals("")){
					int[] position = {i, j};		
						//piece belongs to player
						Piece piece = pf.createPiece(cell, position);
						if (piece.color == Color.White){
							this.white_pieces.add(piece);
							if (cell.equals("K")){
								this.white_king_position = new int[]{i, j}; 
							}
						}
						else{
							this.black_pieces.add(piece);
							if (cell.equals("k")){
								this.black_king_position = new int[]{i, j};
							}
						}
				}
			}
		}
	}
	
	//copy constructor
	public Game(Game game){
		this.white_can_castle_king_side = game.white_can_castle_king_side;
		this.white_can_castle_queen_side = game.white_can_castle_queen_side;
		this.black_can_castle_king_side = game.black_can_castle_king_side;
		this.black_can_castle_queen_side = game.black_can_castle_queen_side;
		this.afoot = game.afoot;
		this.turn = (game.turn == Color.White ? Color.White : Color.Black);
		this.client_color = (game.client_color == Color.White ? Color.White : Color.Black);
		this.en_passant[0] = game.en_passant[0];
		this.en_passant[1] = game.en_passant[1];
		this.id = game.id;
		
		PieceFactory pf = new PieceFactory();
		this.white_pieces = new ArrayList<Piece>();
		this.black_pieces = new ArrayList<Piece>();
		
    	for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				String cell = game.board[i][j];
				this.board[i][j] = cell;
				if (!cell.equals("")){
					int[] position = {i, j};		
						//piece belongs to player
						Piece piece = pf.createPiece(cell, position);
						if (piece.color == Color.White){
							this.white_pieces.add(piece);
							if (cell.equals("K")){
								this.white_king_position = new int[]{i, j}; 
							}
						}
						else{
							this.black_pieces.add(piece);
							if (cell.equals("k")){
								this.black_king_position = new int[]{i, j};
							}
						}
				}
			}
		}
	}
	
	public List<Move> allPossibleMoves(){
		List<Move> moves = new ArrayList<Move>();
		for (Piece piece : (this.client_color == Color.White ? this.white_pieces : this.black_pieces)){
			List<Move> piece_moves = piece.allPossibleMoves(this);
			moves.addAll(piece_moves);
		}
		return moves;
	}
	
	//moves are generated in the form [0,1, 0,0] (to: 0,0, from: 0,1)
	public Move generateDefaultMove(){
		List<Move> moves = allPossibleMoves();
		if (moves.isEmpty()){
			return null;
		}
		else{
			printMoves(moves);
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
	
	public void printMoves(List<Move> moves){
		System.out.println("Moves generated:");
		for (Move m : moves){
			System.out.println("From: [" + m.from[0] + "," + m.from[1] + "], " + "To: [" + m.to[0] + "," + m.to[1] + "]");	
		}
		System.out.println("\n");
		
	}
}
