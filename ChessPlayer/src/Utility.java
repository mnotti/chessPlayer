
public class Utility {
	static int debugging_moves = 0; 
	public static void printBoard(String[][] board){
		for (int i = 0; i < 40; i++){
			System.out.print("=");
		}
		System.out.print('\n');
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				System.out.print(board[i][j] + '\t');
			}
			System.out.print('\n');
		}
		for (int i = 0; i < 40; i++){
			System.out.print("=");
		}
		System.out.print('\n');
	}
}
