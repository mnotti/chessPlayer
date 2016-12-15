
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
	
	//moves are generated in the form [0,1, 0,0] (to: 0,0, from: 0,1)
	public int[] generateDefaultMove(){
		//find the first piece belonging to player that can be moved and move it 
		//TODO: check for pieces not pawns...
		int def[] = {0,0,0,1};
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				String piece = board[i][j];
				if(piece.equals("p") && client_color == Color.Black){
					int move[] = new int[]{i,j,i+1,j};
					return move;
				}else if(piece.equals("P") && client_color == Color.White){
					int move[] = new int[]{i,j,i-1,j};
					return move;
				}
			}
		}
		return def;
	}
}
