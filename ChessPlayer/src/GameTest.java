import static org.junit.Assert.*;

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
	public void testAllPossibleMoves() {
		assertTrue(true);
	}

	@Test
	public void testGenerateDefaultMove() {
		assertTrue(true);
	}

	@Test
	public void testColorOfPieceInCell() {
		assertEquals(Color.White, this.game.colorOfPieceInCell(7,3));
		assertEquals(Color.Black, this.game.colorOfPieceInCell(1,3));
		assertEquals(Color.NoColor, this.game.colorOfPieceInCell(4,3));
	}

}
