import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;   // instead of  import org.junit.Test;


public class GameTest {
	Game game;
	
	@Before
	public void setUp() throws Exception {
		int id = 1;
		Color c_turn = Color.Black;
		String board[][] = new String[][]
							 {{"r", "n", "b", "k", "q", "b", "n", "r"},
							  {"p", "p", "p", "p", "p", "p", "p", "p"},
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"P", "P", "P", "P", "P", "P", "P", "P"},
							  {"R", "N", "B", "K", "Q", "B", "N", "R"}};
		this.game = new Game(id, board, c_turn, Color.Black);
	}

	@After
	public void tearDown() throws Exception {
		this.game = null;
	}

	@Test
	public void testMakeMove() {
		Game ng = this.game.makeMove(new Move(1,0,3,0), Color.Black);
		assertEquals(ng.board[3][0], "p");
		assertEquals(ng.board[1][0], "");
	}
	
	@Test
	public void testMinimaxBestMovePawnTakesPawn(){
		int id = 2;
		Color c_turn = Color.White;
		String board[][] = new String[][]
							 {{"r", "n", "b", "k",  "", "b", "n", "r"},
							  {"p", "p", "p", "p",  "", "p", "p", "p"},
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "", "p", "" , "" , "" },
							  {"" ,  "",  "", "P",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"P", "P", "P",  "", "P", "P", "P", "P"},
							  {"R", "N" , "B", "K", "Q", "B", "N", "R"}};
		Game game = new Game(id, board, c_turn, Color.White);
		Move bestMove = game.minimaxBestMove(0);
		assertEquals(3, bestMove.to[0]);
		assertEquals(4, bestMove.to[1]);
		assertEquals(4, bestMove.from[0]);
		assertEquals(3, bestMove.from[1]);
	}
	
	@Test
	public void testMinimaxBestMovePawnBecomesQueen(){
		int id = 2;
		Color c_turn = Color.White;
		String board[][] = new String[][]
				 {{"" , "", "",  "", "K", "", "", ""},
				  {"" , "", "",  "", "", "", "", ""},
				  {"" , "", "",  "", "", "", "P", ""},
				  {"" , "", "",  "","p", "", "", ""},
				  {"" , "", "", "", "", "", "", ""},
				  {"" , "", "",  "P", "", "", "", ""},
				  {"" , "", "",  "", "", "", "", ""},
				  {"" , "", "",  "", "k", "", "", ""}};
		Game game = new Game(id, board, c_turn, Color.White);
		Move bestMove = game.minimaxBestMove(2);
		assertEquals(1, bestMove.to[0]);
		assertEquals(6, bestMove.to[1]);
		assertEquals(2, bestMove.from[0]);
		assertEquals(6, bestMove.from[1]);
		
		board = new String[][]
				 {{"" , "", "",  "", "K", "", "", ""},
				  {"" , "", "",  "", "", "", "P", ""},
				  {"" , "", "",  "", "", "", "", ""},
				  {"" , "", "",  "","p", "", "", ""},
				  {"" , "", "",  "", "", "", "", ""},
				  {"" , "", "",  "P", "", "", "", ""},
				  {"" , "", "",  "", "", "", "", ""},
				  {"" , "", "",  "", "k", "", "", ""}};
		game = new Game(id, board, c_turn, Color.White);
		bestMove = game.minimaxBestMove(2);
		assertEquals(0, bestMove.to[0]);
		assertEquals(6, bestMove.to[1]);
		assertEquals(1, bestMove.from[0]);
		assertEquals(6, bestMove.from[1]);
	}

	@Test
	public void testEvaluate() {
		assertEquals(0, this.game.evaluate(this.game));
	}
	
	@Test
	public void testPossibleMovesPawnAttack(){
		int id = 2;
		Color c_turn = Color.White;
		String board[][] = new String[][]
							 {{"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "","p", "", "", ""},
							  {"" , "", "", "P", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""}};
		Game game = new Game(id, board, c_turn, Color.White);
		List<Move> possible_moves = game.allPossibleMoves(Color.White);
		assertEquals(2, possible_moves.size());
	}
	
	@Test
	public void testBestMovesBlackCheckMateNextMove(){
		int id = 2;
		Color c_turn = Color.Black;
		String board[][] = new String[][]
							 {{"" , "","K",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "","r", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "","r", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "k", "", "", ""}};
		Game game = new Game(id, board, c_turn, Color.Black);
		List<Move> possible_moves = game.allPossibleMoves(c_turn);
		assertFalse(possible_moves.isEmpty());
		Move bestMove = game.minimaxBestMove(2);
		assertEquals(5, bestMove.from[0]);
		assertEquals(4, bestMove.from[1]);
		assertEquals(0, bestMove.to[0]);
		assertEquals(4, bestMove.to[1]);
	}
	
	@Test
	public void testPawnOutOfBoundsExceptionBug(){
		int id = 2;
		Color c_turn = Color.Black;
		String board[][] = new String[][]
							 {{"" , "", "",  "", "", "", "", ""},
							  {"" , "","k",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "","K",  "", "", "", "", ""},
							  {"" ,"p", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""}};
		Game game = new Game(id, board, c_turn, Color.Black);
		game.minimaxBestMove(2);
		assert(true);
	}

	@Test
	public void testColorOfPieceInCell() {
		assertEquals(Color.White, this.game.colorOfPieceInCell(7,3));
		assertEquals(Color.Black, this.game.colorOfPieceInCell(1,3));
		assertEquals(Color.NoColor, this.game.colorOfPieceInCell(4,3));
	}
	
	@Test
	public void testCheckMateCrashesClientBug(){
		int id = 2;
		Color c_turn = Color.White;
		String board[][] = new String[][]
							 {{"" , "", "",  "", "", "", "", ""},
							  {"" ,"n", "",  "", "", "", "", ""},
							  {"" , "","R",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "","p", "k","p", "", "", ""},
							  {"K","r","n",  "", "","p", "", ""},
							  {"" , "", "",  "", "", "", "", ""},
							  {"" , "", "",  "", "", "", "", ""}};
		Game game = new Game(id, board, c_turn, Color.White);
		assertNull(game.minimaxBestMove(2));
		assert(true);
	}

}
