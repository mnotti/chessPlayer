import java.net.*;
import java.io.*;
import org.json.*;

public class GameManager {
	Game game;
	String player_token;
	String address;
	
	public GameManager(String address){
		this.address = address;
		System.out.println(this.address);
	}
	
	public void joinGame(int id){
		System.out.println("Attempting to join game...");
		try {
		    // Construct data
			String data = "";

		    // Send data
		    URL url = new URL(this.address + "/games/" + id + "/join");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

		    // Get the response
		    System.out.println("Getting response...");
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		    	JSONObject obj = new JSONObject(line);
		    	String player_token = obj.getString("token");
		    	Boolean turn = obj.getBoolean("turn");
		    	String board[][] = new String[8][8];
		    	JSONArray temp_board = obj.getJSONArray("board");
		    	for (int i = 0; i < temp_board.length(); i++){
		    		JSONArray temp_row = temp_board.getJSONArray(i);
		    		for (int j = 0; j < temp_row.length(); j++){
		    			String cell = temp_row.getString(j);
		    			board[i][j] = cell;
		    		}
		    	}
		    	Color c_turn = turn ? Color.Black : Color.White;
		    	this.game = new Game(id, board, c_turn, Color.Black);
		    	this.player_token = player_token;
		    	printBoard(board);
		    }
		    wr.close();
		    rd.close();
		} catch (Exception e) {
			System.out.println("Exception found trying to join game: " + e);
		}
	}
	
	public void playGame(){
		System.out.println("Starting to play game...");
		//if not turn, poll, and update game object
		while(game.afoot){
			while(game.client_color != game.turn){
				pollAndUpdate();
				try {
				    Thread.sleep(5000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				//wait a second so i can see changes...
			}
			//now that it is client turn, generate and make move...
			int[] move = game.generateDefaultMove();
			sendMove(move);	
			pollAndUpdate();
		}
	}
	
	public void pollAndUpdate(){
		System.out.println("Polling for updates on game state...");
		try {

		    URL url = new URL(this.address + "/games/" + game.id + "/state");
		    URLConnection conn = url.openConnection();

		    // Get the response
		    System.out.println("Handling state response...");
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    if ((line = rd.readLine()) != null) {
		    	JSONObject obj = new JSONObject(line);
		    	System.out.println(obj.toString());
		    	
		    	JSONObject en_passant_obj = obj.getJSONObject("en_passant");
		    	int en_passant[] = new int[2];	
		    	en_passant[0] = en_passant_obj.getInt("x"); 
		    	en_passant[1] = en_passant_obj.getInt("y");
		    	
		    	Boolean turn = obj.getBoolean("turn");
		    
		    	Boolean white_ks = obj.getBoolean("white_can_castle_king_side");
		    	Boolean white_qs = obj.getBoolean("white_can_castle_queen_side");
		    	Boolean black_ks = obj.getBoolean("black_can_castle_king_side");
		    	Boolean black_qs = obj.getBoolean("black_can_castle_queen_side");
		    			
		    	String board[][] = new String[8][8];
		    	JSONArray temp_board = obj.getJSONArray("board");
		    	for (int i = 0; i < temp_board.length(); i++){
		    		JSONArray temp_row = temp_board.getJSONArray(i);
		    		for (int j = 0; j < temp_row.length(); j++){
		    			String cell = temp_row.getString(j);
		    			board[i][j] = cell;
		    		}
		    	}
		    	this.game.board = board;
		    	this.game.turn = turn ? Color.White : Color.Black;
		    	this.game.white_can_castle_king_side = white_ks;
		    	this.game.white_can_castle_queen_side = white_qs;
		    	this.game.black_can_castle_king_side = black_ks;
		    	this.game.black_can_castle_queen_side = black_qs;
		    	this.game.en_passant = en_passant;
		    }
		    rd.close();
		} catch (Exception e) {
			System.out.println("exception when getting state: " + e);
		}
	}

	public void createGame() {
		System.out.println("Attempting to create game...");
		try {
		    // Construct data
			String data = "";

		    // Send data
		    URL url = new URL(this.address + "/games/new");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

		    // Get the response
		    System.out.println("Getting response...");
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    if ((line = rd.readLine()) != null) {
		    	JSONObject obj = new JSONObject(line);
		    	int id = obj.getInt("id");
		    	String player_token = obj.getString("token");
		    	String board[][] = new String[8][8];
		    	JSONArray temp_board = obj.getJSONArray("board");
		    	for (int i = 0; i < temp_board.length(); i++){
		    		JSONArray temp_row = temp_board.getJSONArray(i);
		    		for (int j = 0; j < temp_row.length(); j++){
		    			String cell = temp_row.getString(j);
		    			board[i][j] = cell;
		    		}
		    	}
		    	this.game = new Game(id, board, Color.White, Color.White);
		    	this.player_token = player_token;
		    	printBoard(board);
		    }
		    wr.close();
		    rd.close();
		} catch (Exception e) {
			System.out.println("exception!");
		}
	}
	//returns true if the move is accepted and is valid... false if receives move invalid...
	public Boolean sendMove(int[] move){
		try {
		    // Construct data
			int from[] = new int[]{move[0], move[1]};
			int to[] = new int[]{move[2], move[3]};
			
			JSONObject obj = new JSONObject();
			obj.put("token", this.player_token);
			obj.put("from", from);
			obj.put("to", to);
			
			System.out.println("Sending Move from: [" + from[0] + "," + from[1] + "], to: [" + to[0] + "," + to[1] + "]");
			String data = obj.toString();
		   
		    // Send data
		    URL url = new URL(this.address + "/games/" + Integer.toString(this.game.id) + "/move");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

		    // Get the response
		    System.out.println("Getting response...");
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    if ((line = rd.readLine()) != null) {
		    	JSONObject response_obj = new JSONObject(line);
		    	String error_message = response_obj.getString("error");
		    	if(!error_message.equals("")){
		    		//error exists
		    		System.out.println("Move failed!");
		    		return false;
		    	}
		    }
		    wr.close();
		    rd.close();
		    System.out.println("Move made!");
		    return true;
		} catch (Exception e) {
			System.out.println("Exception found trying to send move!");
			return false;
		}
	}
	
	public void printBoard(String[][] board){
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				System.out.print(board[i][j] + '\t');
			}
			System.out.print('\n');
		}
	}

}
