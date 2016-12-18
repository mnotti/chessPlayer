
public class ChessPlayer {
	public static void main(String[] args) {
		String address = args[0];
		String action = args[1];

		GameManager gm = new GameManager(address);
		if (action.equals("create")){
			gm.createGame();
		}
		else if(action.equals("join")){
			int id = Integer.parseInt(args[2]);
			gm.joinGame(id);
		}
		gm.playGame();
	}

}
