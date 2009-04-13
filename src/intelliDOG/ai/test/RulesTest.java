package intelliDOG.ai.test;

import static org.junit.Assert.*;
import intelliDOG.ai.framework.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RulesTest {

	private byte [] board;
	
	@Before
	public void setUp() throws Exception {
		this.board = new byte[80];
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsRulesConformStart() {
		int homeP1 = 0, homeP2 = 0, homeP3 = 0, homeP4 = 0;
		try{
			homeP1 = Rules.getInstance().getHomePositionForPlayer(Players.P1);
			homeP2 = Rules.getInstance().getHomePositionForPlayer(Players.P2);
			homeP3 = Rules.getInstance().getHomePositionForPlayer(Players.P3);
			homeP4 = Rules.getInstance().getHomePositionForPlayer(Players.P4);
		}catch(Exception ex){
			fail(ex.getMessage());
		}
		assertTrue(Rules.getInstance().isRulesConform(board, Cards.CLUBS_ACE, -1, homeP1, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.CLUBS_ACE, -1, Players.P1, false));
		assertTrue(Rules.getInstance().isRulesConform(board, Cards.SPADES_ACE, -1, homeP2, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.SPADES_ACE, -1, Players.P2, false));
		assertTrue(Rules.getInstance().isRulesConform(board, Cards.DIAMONDS_KING, -1, Players.P3, false));
		assertTrue(Rules.getInstance().isRulesConform(board, Cards.HEARTS_KING, -1, Players.P4, false));
		
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.HEARTS_QUEEN, -1, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.CLUBS_EIGHT, -1, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.SPADES_FIVE, -1, Players.P3, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.DIAMONDS_FOUR, -1, homeP4, Players.P4, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.HEARTS_JACK, -1, homeP1, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.CLUBS_NINE, -1, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.SPADES_SEVEN, -1, Players.P3, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.DIAMONDS_SIX, -1, Players.P4, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.HEARTS_TEN, -1, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.CLUBS_THREE, -1, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(board, Cards.SPADES_TWO, -1, Players.P3, false));
	}
	
	@Test
	public void testIsRulesConformMidGame1() {
		board[16] = 5;
		board[24] = 3;
		board[29] = 4;
		board[44] = 2;
		board[45] = 1;
		board[46] = 3;
		board[47] = 2;
		board[48] = 5;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_ACE, 48, 49, Players.P4, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_ACE, 48, 59, Players.P4, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_ACE, 29, 30, Players.P4, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_ACE, 29, 40, Players.P4, false));
	}
	
	@Test
	public void testIsRulesConformMidGame2() {
		board[63] = 2;
		//board[0] = 5;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 3, Players.P2, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 59, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 3, Players.P2, true));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 59, Players.P2, true));
		
		board[0] = 5;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 0, 4, Players.P1, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 0, 60, Players.P1, false));
		
		board[0] = 0;
		board[63] = 1;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 3, Players.P1, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 66, Players.P1, true));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 66, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 67, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 63, 67, Players.P1, true));
		
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_ACE, 63, 0, Players.P1, false));
		board[0] = 1;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_ACE, 63, 0, Players.P1, false));
	}
	
	@Test
	public void testIsRulesConformMidGame3() {
		board[28] = 2;
		board[32] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_SIX, 28, Players.P2, true));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_SIX, 28, Players.P2, false));
		board[32] = 3;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_SIX, 28, Players.P2, false));
		
		board[58] = 4;
		board[0] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_QUEEN, 58, Players.P4, false));
	}
	
	@Test
	public void testIsRulesConformMidGame4() {
		//cards 1 8 8 10
		board[68] = 5;
		board[69] = 5;
		board[70] = 5;
		board[71] = 5;
		      
		board[32] = 5;
		      
		board[31] = 4;
		
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_EIGHT, 31, Players.P2, false));
		
		board[32] = 3;
		
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_EIGHT, 31, Players.P2, false));
		
		/*
		 * 68 69 70 71 
			intelliDOG.ai.framework.Board: Legal moves: 
			card -> 1, positions -> s: -1, t: 48
			card -> 1, positions -> s: 76, t: 77
			card -> 1, positions -> s: 31, t: 42
			card -> 1, positions -> s: 31, t: 32
			card -> 8, positions -> s: 31, t: 39
			card -> 34, positions -> s: 31, t: 39
			card -> 10, positions -> s: 31, t: 41
			intelliDOG.ai.bots.SimpleBot: Karte: 8, Bewertung: 52, s: 31, t: 39
		 * 
		 */
	}
	
	@Test
	public void testIsRulesConformMidGame5() {
		board[51] = 2;
		board[48] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_FOUR, 51, 47, Players.P2, false));
		
		board[48] = 4;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_FOUR, 51, 47, Players.P2, false));
	}
	
	@Test
	public void testIsRulesConformMidGame6() {
		board[2] = 2;
		board[0] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.SPADES_FOUR, 2, 62, Players.P2, false));
		
		/*
		 * intelliDOG.ai.framework.Board: Player on Turn: 2
		 * intelliDOG.ai.framework.Board: Cards: 7 4 8 -1 -1 -1
		 * intelliDOG.ai.framework.Board: Pieces: 69 70 71 2
		 * intelliDOG.ai.framework.Board: Legal moves:
		 * card -> 46, positions -> s: 2, t: 9
		 * card -> 4, positions -> s: 2, t: 6
		 * card -> 4, positions -> s: 2, t: 62
		 * card -> 34, positions -> s: 2, t: 10 
		 */
	}
	
	
	@Test
	public void testIsRulesConformHeaven() {
		board[57] = 1;
		board[56] = 1;
		board[55] = 1;
		board[67] = 5;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_QUEEN, 57, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_QUEEN, 57, Players.P1, true));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_QUEEN, 56, Players.P1, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_QUEEN, 56, Players.P1, true));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_QUEEN, 55, Players.P1, true));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_TEN, 56, Players.P1, true));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_TEN, 55, Players.P1, true));
		
		board[55] = 0;
		board[64] = 5;
		board[67] = 0;
		//for pieces already in heaven!
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_THREE, 64, Players.P1, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_THREE, 64, Players.P1, true));
		
		
		board[76] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_THREE, 76, Players.P4, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_THREE, 76, Players.P4, true));
		
		board = new byte[80];
		board[76] = 5;
		board[77] = 5;
		board[79] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_ACE, 77, 78, Players.P4, false));
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_ACE, 77, 78, Players.P4, true));
		
	}
	
	@Test
	public void testIsRulesConformHeaven2() {
		board[16] = 2;
		board[71] = 5;
		board[70] = 5;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_TWO, 16, Players.P2, true));
		board[16] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_TWO, 16, Players.P2, true));
		
		board[0] = 1;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 0, 67, Players.P1, true));
		board[0] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FOUR, 0, 67, Players.P1, true));
	}
	
	@Test
	public void testIsRulesConformEndGame() {
		//P2's heaven
		board[68] = 5;
		board[69] = 5;
		board[70] = 5;
		board[71] = 5;
		
		//P4's heaven
		board[78] = 5;
		board[79] = 5;
		
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_KING, -1, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.HEARTS_FIVE, -1, Players.P2, false));
		
		board[48] = 5;
		assertTrue(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_NINE, 48, Players.P2, false));
		assertFalse(Rules.getInstance().isRulesConform(this.board, Cards.CLUBS_NINE, 48, Players.P2, true));
	}
	
	@Test
	public void testIsRulesConformSevenSplit(){
		board[0] = 5;
		board[2] = 1;
		
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2}, new int[] {1, 8}, Players.P1, new boolean[]{false, false}));
		
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {0}, new int[] {1, 8}, Players.P1, new boolean[]{false, false}));
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2}, new int[] {8}, Players.P1, new boolean[]{false, false}));
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2}, new int[] {1, 8}, Players.P1, new boolean[]{false, false, false}));
		
		board[15] = 1;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15}, new int[] {1, 6, 17}, Players.P1, new boolean[]{false, false, false}));
		
		board[16] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15}, new int[] {1, 6, 17}, Players.P1, new boolean[]{false, false, false}));
		
		board[16] = 2;
		board[63] = 1;
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15, 63}, new int[] {1, 4, 17, 0}, Players.P1, new boolean[]{false, false, false, false}));

		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15, 63}, new int[] {1, 4, 18, 0}, Players.P1, new boolean[]{false, false, false, false}));
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15, 63}, new int[] {3, 4, 16, 0}, Players.P1, new boolean[]{false, false, false, false}));
		
		board[0] = 1;
		
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15, 63}, new int[] {1, 4, 18, 0}, Players.P1, new boolean[]{false, false, false, false}));
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {0, 2, 15, 63}, new int[] {1, 4, 17, 64}, Players.P1, new boolean[]{false, false, false, true}));
	}
	
	@Test
	public void testIsRulesConformSevenSplit2(){
		board[61] = 1;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {61}, new int[] {67}, Players.P1, new boolean[]{true}));
		
		board[13] = 2;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {13}, new int[] {71}, Players.P2, new boolean[]{true}));
		
		board[11] = 2;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {11}, new int[] {69}, Players.P2, new boolean[]{true}));
		
		board[26] = 3;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {26}, new int[] {72}, Players.P3, new boolean[]{true}));
		
		board[43] = 4;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {43}, new int[] {77}, Players.P4, new boolean[]{true}));
		
	}
	
	@Test
	public void testIsRulesConformSevenSplit3(){
		board[47] = 4;
		board[77] = 5;
		board[78] = 5;
		board[79] = 5;
		board[45] = 2;
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {47, 45}, new int[] {76, 50}, Players.P4, new boolean[]{true, false}));
		
		board[77] = 0;
		board[76] = 5;
		assertFalse(Rules.getInstance().isRulesConform(this.board, new int[] {47, 45}, new int[] {77, 50}, Players.P4, new boolean[]{true, false}));
		assertTrue(Rules.getInstance().isRulesConform(this.board, new int[] {76, 47, 45}, new int[] {77, 76, 49}, Players.P4, new boolean[]{true, true, false}));
		
	}

	/*@Test
	public void testIsProtected() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHomePositionForPlayer() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testGetPiecesInGameForPlayer() {
		try{
			assertArrayEquals(new int [] {}, Rules.getInstance().getPiecesInGameForPlayer(board, Players.P1));
			board[0] = 5;
			board[27] = 1;
			board[16] = 5;
			board[67] = 5;
			board[69] = 5;
			board[32] = 1;
			board[17] = 2;
			//first home fields, then heaven fields and finally normal fields will be considered
			assertArrayEquals(new int [] {0, 67, 27, 32}, Rules.getInstance().getPiecesInGameForPlayer(board, Players.P1));
		}catch(Exception ex){
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testAllPiecesInHeavenOfPlayer(){
		//P2's heaven
		board[68] = 5;
		board[69] = 5;
		board[70] = 5;
		board[71] = 5;
		
		//P4's heaven
		board[78] = 5;
		board[79] = 5;
		
		assertTrue(Rules.getInstance().allPiecesInHeavenOfPlayer(this.board, Players.P2));
		assertFalse(Rules.getInstance().allPiecesInHeavenOfPlayer(this.board, Players.P4));
	}
	
	@Test
	public void testHeavenReachabilityTest() {
		try{
			Method method = Rules.class.getDeclaredMethod("heavenReachabilityTest", new Class[]{int.class, int.class, byte.class});
			method.setAccessible(true);
			assertTrue((Boolean)method.invoke(Rules.getInstance(), new Object[]{63, 3, (byte)1 }));
		}
        catch (NoSuchMethodException e) {
            // Should happen only rarely, because most times the
            // specified method should exist. If it does happen, just let
            // the test fail so the programmer can fix the problem.
            fail(e.getMessage());
        }
        catch (SecurityException e) {
            // Should happen only rarely, because the setAccessible(true)
            // should be allowed in when running unit tests. If it does
            // happen, just let the test fail so the programmer can fix
            // the problem.
        	fail(e.getMessage());
        }
        catch (IllegalAccessException e) {
            // Should never happen, because setting accessible flag to
            // true. If setting accessible fails, should throw a security
            // exception at that point and never get to the invoke. But
            // just in case, wrap it in a TestFailedException and let a
            // human figure it out.
        	fail(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            // Should happen only rarely, because usually the right
            // number and types of arguments will be passed. If it does
            // happen, just let the test fail so the programmer can fix
            // the problem.
        	fail(e.getMessage());
        } catch (InvocationTargetException e) {
			fail(e.getMessage());
		}
	}

}
