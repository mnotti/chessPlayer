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
	
	//returns all possible moves for the game ()
	public List<Move> allPossibleMoves(Color color, Game game){
		List<Move> moves = new ArrayList<Move>();
		for (Piece piece : (color == Color.White ? game.white_pieces : game.black_pieces)){
			List<Move> piece_moves = piece.allPossibleMoves(game);
			moves.addAll(piece_moves);
		}
		return moves;
	}
	
	//moves are generated in the form [0,1, 0,0] (to: 0,0, from: 0,1)
	public Move generateRandMove(){
		List<Move> moves = allPossibleMoves(this.client_color, this);
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
	
	//best move for client_color in (game) for depth (depth)
	public Move minimaxBestMove(int depth){
		//get every possible move for client color
		int current_best_score = 0;
		Boolean current_best_move_set = false;
		List<Move> possible_moves = allPossibleMoves(client_color, this);
		List<Move> best_moves = new ArrayList<Move>();
		//System.out.println("before loop");
		for (Move m : possible_moves){
			Game ng = makeMove(m, client_color);
			//System.out.println("EXAMINING FOLLOWING BOARD IN BEST MOVE");
			//Utility.printBoard(ng.board);
			System.out.println("Move from: [" + m.from[0] + "," + m.from[1] + "], to: [" + m.to[0] + "," + m.to[1] + "]");
			int local_score = minimaxMoveScore(depth, 0, ng);
			System.out.println("Score for move: " + local_score);
			if (current_best_move_set == false || local_score > current_best_score){
				current_best_score = local_score;
				best_moves.clear();
				best_moves.add(m);
				current_best_move_set = true;
			}else if(local_score == current_best_score){
				best_moves.add(m);
			}
		}
		//System.out.println("after loop");
		//randomize equally scored...
		int randomNum = (int)(Math.random() * best_moves.size()); 
		Move best_move = best_moves.get(randomNum);
		return best_move;
	}
	
	public int minimaxMoveScore(int depth, int current_depth, Game game){
		int current_best_score = 0;
		Boolean current_best_score_set = false;
		
		Color color;
		
		if (current_depth%2 == 1){
			color = client_color;
		}
		else{
			color = (client_color == Color.White ? Color.Black : Color.White);
		}
		
		List<Move> possible_moves = allPossibleMoves(color, game);
		//first check if no moves are possible...
		if (possible_moves.isEmpty()){
			if (color == client_color){
				return -9999;
			}
			else{
				return 9999 - current_depth*5;
			}
		}
		if (depth == current_depth){
			//evaluate score for client color on this game
			//update score var and continue
			for (Move m : possible_moves){
				if (client_color == color){
					//set max score, move
					Game ng = game.makeMove(m, color);
					//System.out.println("EVALUATING FOLLOWING BOARD IN SCORE (bottom level)...client color => max");
					//Utility.printBoard(ng.board);
					int local_score = evaluate(ng);
					//System.out.println("Score is: " + local_score);
					if (local_score > current_best_score || current_best_score_set == false){
						current_best_score = local_score;
						current_best_score_set = true;
						//System.out.println("Score is current best");
					}
				}else{
					//set min score, move
					Game ng = game.makeMove(m, color);
					//System.out.println("EVALUATING FOLLOWING BOARD IN SCORE (bottom level)...opp color => min");
					//Utility.printBoard(ng.board);
					int local_score = evaluate(ng);
					if (local_score < current_best_score || current_best_score_set == false){
						current_best_score = local_score;
						current_best_score_set = true;
						//System.out.println("Score is current best");
					}
				}
			}
		}
		else{
			for (Move m : possible_moves){
				if (client_color == color){
					//set max score, move
					Game ng = game.makeMove(m, color);
					//System.out.println("made client move to get FOLLOWING BOARD IN SCORE...diving to depth: " + current_depth + 1);
					//Utility.printBoard(ng.board);
					int local_score = minimaxMoveScore(depth, current_depth + 1, ng);
					if (local_score > current_best_score || current_best_score_set == false){
						current_best_score = local_score;
						current_best_score_set = true;
					}
				}else{
					//set min score, move
					Game ng = game.makeMove(m, color);
					//System.out.println("made opp move to get FOLLOWING BOARD IN SCORE...diving to depth: " + current_depth + 1);					Utility.printBoard(ng.board);
					//Utility.printBoard(ng.board);
					int local_score = minimaxMoveScore(depth, current_depth + 1, ng);
					if (local_score < current_best_score || current_best_score_set == false){
						current_best_score = local_score;
						current_best_score_set = true;
					}
				}
			}
		}
		//System.out.println("FOR BOARD AT RETURN: ");
		//Utility.printBoard(game.board);
		//System.out.println("returning board score: " + current_best_score);
		return current_best_score;
	}
	
	//evaluates score of game passed in (for client color of the game which it is in)
	public int evaluate(Game game){
		//basic... measures difference between piece scores of ally and enemy...
		int p_v = 1;
		int r_v = 5;
		int n_v = 3;
		int b_v = 3;
		int q_v = 8;
		int white_score = 0;
		int black_score = 0;
		//first measure the score by tallying the above 
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				switch (game.board[i][j]){
					case "P": 
						white_score += p_v;
						break;
					case "p": 
						black_score += p_v;
						break;
					case "R": 
						white_score += r_v;
						break;
					case "r": 
						black_score += r_v;
						break;
					case "N": 
						white_score += n_v;
						break;
					case "n":
						black_score += n_v;
						break;
					case "B": 
						white_score += b_v;
						break;
					case "b": 
						black_score += b_v;
						break;
					case "Q": 
						white_score += q_v;
						break;
					case "q": 
						black_score += q_v;
						break;
					default:
						break;
							
				}
			}
		}
		//TODO... king value
		//king value... removed when 1 team has no possible moves left and the king is threatened check...
		//int k_v = 999;
		Color turn = game.turn;
		List<Move> possible_moves_for_next_player = allPossibleMoves(turn, game);
		if(possible_moves_for_next_player.isEmpty()){
			if(turn == Color.White){
				white_score -= 999;
			}
			else{
				black_score -= 999;
			}
		}
		return (client_color == Color.White ? white_score - black_score : black_score - white_score);
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
	
	public Game makeMove(Move move, Color color){
		Game ng = new Game(this);
		Piece piece_moved;
		for (Piece p : (color == Color.White ? ng.white_pieces : ng.black_pieces)){
			if (p.position[0] == move.from[0] && p.position[1] == move.from[1]){
				piece_moved = p;
				piece_moved.position[0] = move.to[0];
				piece_moved.position[1] = move.to[1];
				ng.board[move.from[0]][move.from[1]] = "";
				//iterate through opponent's pieces...
				for (Iterator<Piece> iterator = (color == Color.White ? ng.black_pieces.iterator() : ng.white_pieces.iterator()); iterator.hasNext();) {
				    Piece enemy_piece = iterator.next();
				    //if piece is being attacked on this move...
				    if (enemy_piece.position[0] == move.to[0] && enemy_piece.position[1] == move.to[1]){
						iterator.remove();
					}
				}
				ng = piece_moved.makeMoveSpecificDetails(move, ng);
				ng.turn = (ng.turn == Color.White ? Color.Black : Color.White);
				break;
			}
		}
		
		return ng;
	}
	
	public void printMoves(List<Move> moves){
		System.out.println("Moves generated:");
		for (Move m : moves){
			System.out.println("From: [" + m.from[0] + "," + m.from[1] + "], " + "To: [" + m.to[0] + "," + m.to[1] + "]");	
		}
		System.out.println("\n");
		
	}
}
