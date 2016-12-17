import static org.junit.Assert.*;
import java.util.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PieceTest {
	Game game;
	Piece blackKnight;
	
	@Before
	public void setUp() throws Exception {
		int id = 1;
		Color c_turn = Color.Black;
		String board[][] = new String[][]
							 {{"r",  "", "b", "k", "q", "b", "n", "r"},
							  {"p", "p", "p", "p", "p", "p", "p", "p"},
							  {"" ,  "", "n",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"" ,  "",  "",  "",  "", "" , "" , "" },
							  {"P", "P", "P", "P", "P", "P", "P", "P"},
							  {"R", "N", "B", "K", "Q", "B", "N", "R"}};
		this.game = new Game(id, board, c_turn, Color.Black);
		this.blackKnight = new Knight(Color.Black, new int[]{2, 2});
	}

	@After
	public void tearDown() throws Exception {
		this.game = null;
		this.blackKnight = null;
	}

	@Test
	public void testRemoveFriendlyTargetMoves() {
		List<Move> moves = new ArrayList<Move>();
		moves.add(new Move(2, 2, 1, 4));
		blackKnight.removeFriendlyTargetMoves(moves, this.game);
		assertTrue(moves.isEmpty());
	}

}
