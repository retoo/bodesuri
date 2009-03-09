package intelliDOG.ai.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.utils.DebugMsg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BotBoardTest {
	private BotBoard board;
	private InformationGatherer ig;
	private byte[] boardArray;

	@Before
	public void setUp() throws Exception {
		boardArray = new byte[80];
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllPossibleMoves() {
		boardArray[63] = 2;
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { 4, -1, -1, -1, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		assertEquals(2, board.getAllPossibleMoves(Players.P2).size());
	}

	@Test
	public void testGetAllPossibleMoves2() {
		boardArray[68] = 5;
		boardArray[69] = 5;
		boardArray[70] = 5;
		boardArray[71] = 5;
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { 4, 26, 8, -1, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		assertEquals(1, board.getAllPossibleMoves(Players.P2).size());
		assertEquals(48, board.getAllPossibleMoves(Players.P2).get(0).getPositions()[1]);
	}

	@Test
	public void testGetAllPossibleMoves3() {
		// without protected 32
		boardArray[68] = 5;
		boardArray[69] = 5;
		boardArray[70] = 5;
		boardArray[71] = 5;

		boardArray[76] = 5;
		boardArray[79] = 5;
		boardArray[31] = 4;
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { 1, 8, 8, 10, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		assertEquals(7, board.getAllPossibleMoves(Players.P2).size());

		// with protected 32
		boardArray[32] = 5;
		board.setBoard(boardArray);
		assertEquals(2, board.getAllPossibleMoves(Players.P2).size());

		/*
		 * P4: 76 79 18 (danach 18 getauscht nach 31?)
		 * 
		 * 
		 * intelliDOG.ai.framework.Board: Cards: 1 8 8 10 -1 -1
		 * intelliDOG.ai.framework.Board: Pieces: 68 69 70 71
		 * intelliDOG.ai.framework.Board: Legal moves: card -> 1, positions ->
		 * s: -1, t: 48 card -> 1, positions -> s: 76, t: 77 card -> 1,
		 * positions -> s: 31, t: 42 card -> 1, positions -> s: 31, t: 32 card ->
		 * 8, positions -> s: 31, t: 39 card -> 34, positions -> s: 31, t: 39
		 * card -> 10, positions -> s: 31, t: 41 intelliDOG.ai.bots.SimpleBot:
		 * Karte: 8, Bewertung: 52, s: 31, t: 39 intelliDOG.ai.bots.SimpleBot:
		 * Karte: Herz Acht, konkrete Karte: Herz Acht
		 */

	}
	
	@Test
	public void testGetAllPossibleMoves4() {
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[46] = 4;
		boardArray[77] = 5;
		boardArray[79] = 5;
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { 7, 5, 3, 11, 9, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getAllPossibleMoves(Players.P4);
		msg.debug(this, "-------------------------GetAllPossibleMoves4-------------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "-----------------------End GetAllPossibleMoves4-----------------------");
		
		assertEquals(7, possible.size());
		
		/*
		 * intelliDOG.ai.framework.Board: Player on Turn: 4
		 * intelliDOG.ai.framework.Board: Cards: 7 5 3 11 9 -1 
		 * intelliDOG.ai.framework.Board: Pieces: 77 79 46 
		 * Exception in thread "Bot SlauBotZwei" java.lang.RuntimeException: Es trat ein schwerer Fehler auf. Das Spiel wird beendet. (3)
		 */
		
	}
	
	@Test
	public void testGetAllPossibleMoves5() {
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[46] = 4;
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { 7, -1, -1, -1, -1, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getAllPossibleMoves(Players.P4);
		msg.debug(this, "-------------------------GetAllPossibleMoves5-------------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "-----------------------End GetAllPossibleMoves5-----------------------");
		
		assertEquals(1, possible.size());
		
		/*
		 * intelliDOG.ai.framework.Board: Player on Turn: 4
		 * intelliDOG.ai.framework.Board: Cards: 7 -1 -1 -1 -1 -1 
		 * intelliDOG.ai.framework.Board: Pieces: 46 
		 * Exception in thread "Bot SlauBotZwei" java.lang.RuntimeException: Es trat ein schwerer Fehler auf. Das Spiel wird beendet. (1)
		 */
		
	}
	
	@Test
	public void testGetAllPossibleMoves6() {
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[45] = 4;
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { 7, 9, 6, 10, 11, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getAllPossibleMoves(Players.P4);
		msg.debug(this, "-------------------------GetAllPossibleMoves6-------------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "-----------------------End GetAllPossibleMoves6-----------------------");
		assertEquals(6, possible.size());
		
		boardArray = new byte[80];
		boardArray[76] = 5;
		boardArray[77] = 5;
		boardArray[78] = 5;
		boardArray[79] = 5;
		boardArray[45] = 2;
		board.setBoard(boardArray);
		possible = board.getAllPossibleMoves(Players.P4);
		msg.debug(this, "------------------------GetAllPossibleMoves6 2------------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "----------------------End GetAllPossibleMoves6 2----------------------");
		assertEquals(4, possible.size());
		
		
		boardArray = new byte[80];
		boardArray[47] = 4;
		boardArray[77] = 5;
		boardArray[78] = 5;
		boardArray[79] = 5;
		boardArray[45] = 2;
		board.setBoard(boardArray);
		possible = board.getAllPossibleMoves(Players.P4);
		msg.debug(this, "------------------------GetAllPossibleMoves6 3------------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "----------------------End GetAllPossibleMoves6 3----------------------");
		assertEquals(6, possible.size()); //moving with every card possible + with seven to heaven and then going on with partner pieces
		
		/*
		 * intelliDOG.ai.framework.Board: Player on Turn: 4
		 * intelliDOG.ai.framework.Board: Cards: 7 9 6 10 11 -1 
		 * intelliDOG.ai.framework.Board: Pieces: 45 
		 * Exception in thread "Bot SlauBotZwei" java.lang.RuntimeException: Es trat ein schwerer Fehler auf. Das Spiel wird beendet. (1)
		 */
	}
	
	@Test
	public void testGetAllPossibleMoves7() {
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		//finishing with own and going on with partner's pieces
		boardArray[47] = 4;
		boardArray[77] = 5;
		boardArray[78] = 5;
		boardArray[79] = 5;
		boardArray[45] = 2;
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { 7, -1, -1, -1, -1, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getAllPossibleMoves(Players.P4);
		msg.debug(this, "-------------------------GetAllPossibleMoves7-------------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "-----------------------End GetAllPossibleMoves7-----------------------");
		assertEquals(2, possible.size());
	}
	
	@Test
	public void testGetAllPossibleMoves8() {
		boardArray[69] = 5;
		boardArray[70] = 5;
		boardArray[71] = 5;
		boardArray[39] = 2;
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { 2, 7, 12, 7, 8, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getAllPossibleMoves(Players.P2);
		assertEquals(5, possible.size());
		
		boardArray[48] = 5;
		board.setBoard(boardArray);
		possible = board.getAllPossibleMoves(Players.P2);
		assertEquals(4, possible.size());
	}

	@Test
	public void testGetPossibleMovesForCard() {
		boardArray[58] = 4;
		boardArray[0] = 5;
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { Cards.SPADES_QUEEN, -1, -1, -1, -1, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		assertEquals(0, board.getPossibleMovesForCard(Cards.SPADES_QUEEN, Players.P4).size());
	}
	
	@Test
	public void testGetPossibleMovesForCard2() {
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[0] = 5;
		boardArray[8] = 1;
		ig = new InformationGatherer(Players.P1);
		ig.setCardsForPlayer(new int[] { Cards.CLUBS_SEVEN, -1, -1, -1, -1, -1 }, Players.P1);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN, Players.P1);
		msg.debug(this, "-----------------------ONE-----------------------");
		msg.debugLegalMoves(this, possible);
		int size = possible.size();
		assertEquals(14, size);  //14
		msg.debug(this, "---------------------END-ONE---------------------");
		
		
		/*intelliDOG.ai.framework.Board: Player on Turn: 2
		intelliDOG.ai.framework.Board: Cards: 2 7 2 -1 -1 -1 
		intelliDOG.ai.framework.Board: Pieces: 68*/ 
		
		
		/*
		boardArray[16] = 1;
		possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN);
		msg.debug(this, "-----------------------TWO-----------------------");
		msg.debugLegalMoves(this, possible);
		size = possible.size();
		assertEquals(39, size); //39 old 21
		msg.debug(this, "---------------------END-TWO---------------------");
		
		boardArray[30] = 1;
		possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN);
		msg.debug(this, "----------------------THREE----------------------");
		msg.debugLegalMoves(this, possible);
		size = possible.size();
		assertEquals(76, size); //76 old 28
		msg.debug(this, "--------------------END-THREE--------------------");
		*/
	}
	
	@Test
	public void testGetPossibleMovesForCard3(){
		
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[0] = 5;
		
		ig = new InformationGatherer(Players.P1);
		ig.setCardsForPlayer(new int[] { Cards.CLUBS_SEVEN, -1, -1, -1, -1, -1 }, Players.P1);
		board = new BotBoard(boardArray, ig);
		//int[] pieces = new int[0];
		//pieces = Rules.getInstance().getPiecesInGameForPlayer(this.board.getBoard(), Players.P1);
		//byte [] heavenPieces = Rules.getInstance().getPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P1);
		//ArrayList<Move> possible = board.getPossibleMovesForSevenSplit(Cards.CLUBS_SEVEN, 7, pieces, 0, new int[7], new int[7], new boolean[7], Players.P1, heavenPieces, new boolean[pieces.length]);
		List<Move> possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN, Players.P1);
		int size = possible.size();
		assertEquals(1, size);
		
		//with two pieces
		boardArray[8] = 1;
		
		board.setBoard(boardArray);
		//pieces = Rules.getInstance().getPiecesInGameForPlayer(this.board.getBoard(), Players.P1);
		//heavenPieces = Rules.getInstance().getPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P1);
		//possible = board.getPossibleMovesForSevenSplit(Cards.CLUBS_SEVEN, 7, pieces, 0, new int[7], new int[7], new boolean[7], Players.P1, heavenPieces, new boolean[pieces.length]);
		possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN, Players.P1);
		msg.debug(this, "-----------------------RECURSIVE TWO-----------------------");
		msg.debugLegalMoves(this, possible);
		size = possible.size();
		assertEquals(14, size);
		msg.debug(this, "---------------------END RECURSIVE TWO---------------------");
		
		//with three pieces
		boardArray[14] = 1;
		
		board.setBoard(boardArray);
		//pieces = Rules.getInstance().getPiecesInGameForPlayer(this.board.getBoard(), Players.P1);
		//heavenPieces = Rules.getInstance().getPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P1);
		//possible = board.getPossibleMovesForSevenSplit(Cards.CLUBS_SEVEN, 7, pieces, 0, new int[7], new int[7], new boolean[7], Players.P1, heavenPieces, new boolean[pieces.length]);
		possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN, Players.P1);
		msg.debug(this, "-----------------------RECURSIVE THREE-----------------------");
		msg.debugLegalMoves(this, possible);
		size = possible.size();
		assertEquals(128, size); //<-- hope this is true!
		msg.debug(this, "---------------------END RECURSIVE THREE---------------------");
		
		//with four pieces
		boardArray[26] = 1;
		
		board.setBoard(boardArray);
		//pieces = Rules.getInstance().getPiecesInGameForPlayer(this.board.getBoard(), Players.P1);
		//heavenPieces = Rules.getInstance().getPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P1);
		//possible = board.getPossibleMovesForSevenSplit(Cards.CLUBS_SEVEN, 7, pieces, 0, new int[7], new int[7], new boolean[7], Players.P1, heavenPieces, new boolean[pieces.length]);
		possible = board.getPossibleMovesForCard(Cards.CLUBS_SEVEN, Players.P1);
		msg.debug(this, "-----------------------RECURSIVE FOUR-----------------------");
		msg.debugLegalMoves(this, possible);
		size = possible.size();
		assertEquals(915, size); //<--hope this is true!
		msg.debug(this, "---------------------END RECURSIVE FOUR---------------------");
	}
	
	@Test
	public void testGetPossibleMovesForCard4(){
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[15] = 2;
		
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { Cards.HEARTS_THREE, Cards.JOKER, Cards.HEARTS_NINE, Cards.CLUBS_ACE, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getPossibleMovesForCard(Cards.JOKER, Players.P2);
		msg.debug(this, "--------------------GetPossibleMovesForCard4-Joker--------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "------------------End GetPossibleMovesForCard4-Joker------------------");
		int size = possible.size();
		assertEquals(20, size);
		
		possible = board.getPossibleMovesForCard(Cards.CLUBS_ACE, Players.P2);
		msg.debug(this, "---------------------GetPossibleMovesForCard4-Ace---------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "-------------------End GetPossibleMovesForCard4-Ace-------------------");
		size = possible.size();
		assertEquals(3, size);
		
		boardArray[16] = 5;
		board.setBoard(boardArray);
		
		possible = board.getPossibleMovesForCard(Cards.JOKER, Players.P2);
		msg.debug(this, "-------------------GetPossibleMovesForCard4-Joker 2-------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "-----------------End GetPossibleMovesForCard4-Joker 2-----------------");
		size = possible.size();
		assertEquals(25, size);
		
		possible = board.getPossibleMovesForCard(Cards.CLUBS_ACE, Players.P2);
		msg.debug(this, "--------------------GetPossibleMovesForCard4-Ace 2--------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "------------------End GetPossibleMovesForCard4-Ace 2------------------");
		size = possible.size();
		assertEquals(2, size);
	}
	
	@Test
	public void testGetPossibleMovesForCard5(){
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		
		boardArray[15] = 2;
		
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { Cards.HEARTS_THREE, Cards.JOKER, Cards.HEARTS_NINE, Cards.CLUBS_ACE, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getPossibleMovesForCard(Cards.JOKER, Players.P2);
		msg.debug(this, "--------------------GetPossibleMovesForCard5-Joker--------------------");
		msg.debugLegalMoves(this, possible);
		msg.debug(this, "------------------End GetPossibleMovesForCard5-Joker------------------");
		int size = possible.size();
		assertEquals(20, size);
		
		
	}

	
	@Test public void testMakeMove1() { 
		boardArray[79] = 5;
		boardArray[71] = 5;
		boardArray[15] = 2;
		boardArray[31] = 1;
		boardArray[32] = 5;
		boardArray[74] = 5;
		boardArray[75] = 5;
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		expectedArray[48] = 5;
		
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { Cards.SPADES_TWO, Cards.SPADES_QUEEN, Cards.CLUBS_NINE, 
				Cards.SPADES_KING,  Cards.SPADES_EIGHT, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		
		List<Move> possible = this.board.getAllPossibleMoves(Players.P4);
		
		assertEquals(1, possible.size());
		assertEquals(Cards.SPADES_KING, possible.get(0).getCard());
		assertEquals(-1, possible.get(0).getPositions()[0]);
		assertEquals(48, possible.get(0).getPositions()[1]);
		
		for (int i = 0; i < possible.size(); i++)
		{
			this.board.makeMove(possible.get(i), Players.P4);
		}
		assertArrayEquals(expectedArray, this.board.getBoard());
	}
	
	@Test public void testMakeMove2() { 
		boardArray[67] = 5;
		boardArray[71] = 5;
		boardArray[75] = 5;
		boardArray[41] = 4;
		boardArray[43] = 1;
		boardArray[46] = 4;
		boardArray[77] = 5;
		boardArray[61] = 3;
		
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		expectedArray[16] = 5;
		
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { Cards.HEARTS_SEVEN, Cards.DIAMONDS_SIX, Cards.HEARTS_ACE, 
				Cards.CLUBS_TEN, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		
		List<Move> possible = this.board.getAllPossibleMoves(Players.P2);
		
		assertEquals(1, possible.size());
		assertEquals(Cards.HEARTS_ACE, possible.get(0).getCard());
		assertEquals(-1, possible.get(0).getPositions()[0]);
		assertEquals(16, possible.get(0).getPositions()[1]);
		
		for (int i = 0; i < possible.size(); i++)
		{
			this.board.makeMove(possible.get(i), Players.P2);
		}
		
		assertArrayEquals(expectedArray, this.board.getBoard());
		
	}
	
	@Test public void testMakeMove3() { 
		byte[] boardArray = new byte[80];
		boardArray[0] = 5;
		boardArray[62] = 1;
		boardArray[47] = 3;
		boardArray[42] = 4;
		boardArray[66] = 5; //heaven p1
		boardArray[67] = 5; //heaven p1
		boardArray[70] = 5; //heaven p2
		boardArray[71] = 5; //heaven p2
		boardArray[73] = 5; //heaven p3
		boardArray[75] = 5; //heaven p3
		//boardArray[79] = 5; //heaven p4
		
		
		/*byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		expectedArray[16] = 5;*/
		
		ig = new InformationGatherer(Players.P4);
		ig.setCardsForPlayer(new int[] { Cards.SPADES_SEVEN, -1, -1, -1, -1, -1 }, Players.P4);
		board = new BotBoard(boardArray, ig);
		
		List<Move> possible = this.board.getAllPossibleMoves(Players.P4);
		
		assertEquals(2, possible.size());
		//assertEquals(Cards.HEARTS_ACE, possible.get(0).getCard());
		//assertEquals(-1, possible.get(0).getPositions()[0]);
		//assertEquals(16, possible.get(0).getPositions()[1]);
		
		for (int i = 0; i < possible.size(); i++)
		{
			this.board.makeMove(possible.get(i), Players.P4);
		}
		
		//assertArrayEquals(expectedArray, this.board.getBoard());
		
	}
	

	@Test
	public void testUndoMove1() {

		byte[] boardArray = new byte[80];
		boardArray[5] = Players.P1;
		boardArray[21] = 4;
		boardArray[37] = 3;
		boardArray[60] = 2;
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		
		int[] mycards = new int[] { 6, 9, 11, -1, -1, -1 };
		ig = new InformationGatherer(Players.P1);
		ig.setCardsForPlayer(mycards, Players.P1);
		board = new BotBoard(boardArray, ig);
		
		//System.out.println(this.board);

		List<Move> possible = this.board.getAllPossibleMoves(Players.P1);

		assertEquals(5, possible.size());
		
		for (int i = 0; i < possible.size(); i++)
		{
			this.board.makeMove(possible.get(i), Players.P1);
			this.board.undoMove(Players.P1);
			assertArrayEquals(expectedArray, this.board.getBoard());
		}
		
		assertArrayEquals(expectedArray, this.board.getBoard());
		
	}
	
	@Test
	public void testUndoMove2() {

		byte[] boardArray = new byte[80];
		boardArray[71] = Players.ANY_SAVE;
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		
		//Cards: 7 6 1 10
		int[] mycards = new int[] { 7, 6, 1, 10, -1, -1 };
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(mycards, Players.P2);
		board = new BotBoard(boardArray, ig);
		
		List<Move> possible = this.board.getAllPossibleMoves(Players.P2);

		assertEquals(1, possible.size());
		
		for (int i = 0; i < possible.size(); i++)
		{
			this.board.makeMove(possible.get(i), Players.P2);
			this.board.undoMove(Players.P2);
			assertArrayEquals(expectedArray, this.board.getBoard());
		}
		
		assertArrayEquals(expectedArray, this.board.getBoard());
		
	}
	
	@Test
	public void testUndoMove3() {
		
		testMakeMove1();
		
		byte[] expectedArray = new byte[80];
		expectedArray[79] = 5;
		expectedArray[71] = 5;
		expectedArray[15] = 2;
		expectedArray[31] = 1;
		expectedArray[32] = 5;
		expectedArray[74] = 5;
		expectedArray[75] = 5;
		
		this.board.undoMove(Players.P4);
		
		assertArrayEquals(expectedArray, this.board.getBoard());
		
	}
	
	@Test
	public void testUndoMove4() {
		
		testMakeMove2();
		
		byte[] expectedArray = new byte[80];
		expectedArray[67] = 5;
		expectedArray[71] = 5;
		expectedArray[75] = 5;
		expectedArray[41] = 4;
		expectedArray[43] = 1;
		expectedArray[46] = 4;
		expectedArray[77] = 5;
		expectedArray[61] = 3;
		
		this.board.undoMove(Players.P2);
		
		assertArrayEquals(expectedArray, this.board.getBoard());
	}
	
	@Test
	 public void testMakeAndUndoMove(){
		byte[] boardArray = new byte[80];
		 boardArray[37] = 1;
		 int[] mycards = new int[]{13,4,11,-1,-1,-1};
		 int[] expectedCards = new int[mycards.length];
		 for(int i = 0; i < expectedCards.length; i++){
			 expectedCards[i] = mycards[i];
		 }
		 
		ig = new InformationGatherer(Players.P1);
		ig.setCardsForPlayer(mycards, Players.P1);
		board = new BotBoard(boardArray, ig);
		List<Move> possible = board.getAllPossibleMoves(Players.P1);
		List<Move> possibleStageTwo = null;
		 
		 for(int i = 0; i < possible.size(); i++){
			 assertTrue(possible.get(i).getCard() == 13 || possible.get(i).getCard() == 4 || possible.get(i).getCard() == 11);
			 board.makeMove(possible.get(i), Players.P1);
			 
			//get cards from board
			 int[] cardsAfterFirstMove = new int[6];
			 for(int x = 0; x < 6; x++){
				 cardsAfterFirstMove[x] = ig.getCardsForPlayer(Players.P1)[x];
			 }
			 //board.setPlayerOnTurn(board.getMyPlayer());
			 possibleStageTwo = board.getAllPossibleMoves(Players.P1); 
			 for(int j = 0; j <possibleStageTwo.size(); j++){
				 assertTrue(possible.get(i).getCard() == 13 || possible.get(i).getCard() == 4 || possible.get(i).getCard() == 11);
				 
				 board.makeMove(possibleStageTwo.get(j), Players.P1);
				//get cards from board
				 int[] cardsAfterSecondMove = new int[6];
				 for(int x = 0; x < 6; x++){
					 cardsAfterSecondMove[x] = ig.getCardsForPlayer(Players.P1)[x];
				 }
				 
				 board.undoMove(Players.P1);
				 //assertArrayEquals(cardsAfterSecondMove, board.getMyCards());
			 }
			 board.undoMove(Players.P1);
			 
			 //assertArrayEquals(cardsAfterFirstMove, board.getMyCards());
		 }
		 java.util.Arrays.sort(expectedCards);
		 //boardCards
		 int[] boardCards = ig.getCardsForPlayer(Players.P1);
		 java.util.Arrays.sort(boardCards);
		 assertArrayEquals(expectedCards, boardCards);
	 }
	
	@Test
    public void testMakeAndUndoMove2() throws Exception
    {
        byte player = 1;
        boardArray[64] = 5;
        boardArray[65] = 5;
        boardArray[66] = 5;
        boardArray[67] = 5;
        boardArray[40] = 3;
        boardArray[58] = 2;
        boardArray[30] = 2;
        int[] mycards = new int[]{3,12,11,-1,-1,-1};
        ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		board = new BotBoard(boardArray, ig);
        List<Move> possible = board.getAllPossibleMoves(player);
       
        byte[] expectedArray = new byte[boardArray.length];
        for(int i = 0; i < boardArray.length; i++){
        	expectedArray[i] = boardArray[i];
        }
        
        //Move highestMove = null;
        //int highest = 0;
        for(int i = 0; i < possible.size(); i++)
        {
            board.makeMove(possible.get(i), player);
            //int result = eval.evaluate(b.getBoard(), b.getMyPlayer(), mycards);
            /*if(highest < result)
            {
                highest = result;
                highestMove = possible.get(i);
            }*/
            board.undoMove(player);
        }
        assertArrayEquals(expectedArray, boardArray);
    }
	
	@Test
	public void testMakeAndUndoMove3() throws Exception
	{
		DebugMsg msg = DebugMsg.getInstance();
		msg.addItemForWhiteList(this);
		byte player = 4;
		//player four
		boardArray[77] = 5;
		boardArray[78] = 5;
		boardArray[79] = 5;
		boardArray[76] = 5;
		//player two
		boardArray[68] = 5;
		boardArray[71] = 5;
		boardArray[16] = 5;
		boardArray[5] = 2;
		//player one
		boardArray[67] = 5;
		boardArray[66] = 5;
		//player three
		boardArray[74] = 5;
		boardArray[75] = 5;
		boardArray[13] = 3; 
		int[] mycards = new int[]{7,100,9,6,10,-1};
		ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		board = new BotBoard(boardArray, ig);
		
		List<Move> possible = board.getAllPossibleMoves(player);
		
		msg.debug(this, "--------------------testMakeAndUndoMove3--------------------");
		msg.debugLegalMoves(this, (ArrayList)possible);
		msg.debug(this, "------------------End testMakeAndUndoMove3------------------");
		
		assertEquals(184, possible.size());
		
		//TODO: use this as time measurement method:
		//TODO: add time measure point here
		for(Move m : possible){
			this.board.makeMove(m, player);
			List<Move> poss2 = this.board.getAllPossibleMoves(player);
			for(Move m2 : poss2){
				this.board.makeMove(m2, player);
				List<Move> poss3 = this.board.getAllPossibleMoves(player);
				for(Move m3 : poss3){
					this.board.makeMove(m3, player);
					this.board.undoMove(player);
				}
				this.board.undoMove(player);
			}
			this.board.undoMove(player);
		}
		//TODO: add time end point here
	}
	
	@Test
	public void testMakeAndUndoMoveSeven()
	{
		DebugMsg msg = DebugMsg.getInstance(); 
		msg.addItemForWhiteList(this); 
		boardArray[32] = 5; 
		boardArray[73] = 5;
		boardArray[74] = 5; 
		ig = new InformationGatherer(Players.P3);
		ig.setCardsForPlayer(new int[] { 33, -1, -1, -1, -1, -1 }, Players.P3);
		board = new BotBoard(boardArray, ig);
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		List<Move> possible = board.getAllPossibleMoves(Players.P3); 
		
		msg.debugLegalMoves(this, possible);
		
		assertEquals(6, possible.size());
		
		//Test if move 1 is made correct
		expectedArray[32] = 0;
		expectedArray[39] = 3;
		Move m = possible.get(0);
		board.makeMove(m, Players.P3);
		assertArrayEquals(expectedArray, boardArray);
		assertTrue(m.getwasProtected()[0]);
		
		//Test if undoing move 1 works correctly
		expectedArray[32] = 5;
		expectedArray[39] = 0;
		board.undoMove(Players.P3);
		assertArrayEquals(expectedArray, boardArray);
		
		//Test if move 2 is made correct
		expectedArray[32] = 0;
		expectedArray[38] = 3;
		expectedArray[74] = 0;
		expectedArray[75] = 5;
		m = possible.get(1);
		board.makeMove(m, Players.P3);
		assertArrayEquals(expectedArray, boardArray);
		assertTrue(m.getwasProtected()[0]);
		assertTrue(m.getwasProtected()[1]);
		
		//Test if undoing move 2 works correctly
		expectedArray[32] = 5;
		expectedArray[38] = 0;
		expectedArray[74] = 5;
		expectedArray[75] = 0;
		board.undoMove(Players.P3);
		assertArrayEquals(expectedArray, boardArray);
		
		//Test if move 3 is made correct
		expectedArray[32] = 0;
		expectedArray[37] = 3;
		expectedArray[75] = 5;
		expectedArray[73] = 0;
		m = possible.get(2);
		board.makeMove(m, Players.P3);
		assertArrayEquals(expectedArray, boardArray);
		assertTrue(m.getwasProtected()[0]);
		assertTrue(m.getwasProtected()[1]);
		assertTrue(m.getwasProtected()[2]);
		
		//Test if undoing move 3 works correctly
		expectedArray[32] = 5;
		expectedArray[37] = 0;
		expectedArray[75] = 0;
		expectedArray[73] = 5;
		board.undoMove(Players.P3);
		assertArrayEquals(expectedArray, boardArray);
		

		  /*for(int i = 0; i < possible.size(); i++)
	        {
			  msg.debug(this, "before " +  board.toString()); 
	            board.makeMove(possible.get(i), Players.P3);
	            board.undoMove(Players.P3);
	            msg.debug(this, "after  " +  board.toString()); 
	        }
	        assertArrayEquals(expectedArray, boardArray);*/
	}

	
	@Test
	public void testMoveSeven()
	{
		DebugMsg msg = DebugMsg.getInstance(); 
		msg.addItemForWhiteList(this); 
		boardArray[32] = 5; 
		boardArray[73] = 5;
		boardArray[74] = 5; 
		ig = new InformationGatherer(Players.P3);
		ig.setCardsForPlayer(new int[] { 33, -1, -1, -1, -1, -1 }, Players.P3);
		board = new BotBoard(boardArray, ig);
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		List<Move> possible = board.getAllPossibleMoves(Players.P3); 
		
		msg.debugLegalMoves(this, possible);

		  for(int i = 0; i < possible.size(); i++)
	        {
			  msg.debug(this, "before " +  board.toString()); 
	            board.makeMove(possible.get(i), Players.P3);
	            board.undoMove(Players.P3);
	            msg.debug(this, "after  " +  board.toString()); 
	        }
	        assertArrayEquals(expectedArray, boardArray);
	}
	
	
	@Test
	public void testUndoMoveSevenSplit() {

		DebugMsg msg = DebugMsg.getInstance(); 
		msg.addItemForWhiteList(this); 
		
		byte[] boardArray = new byte[80];
		
		boardArray[14] = 2;
		boardArray[16] = Players.ANY_SAVE;
		boardArray[33] = 1;
		boardArray[70] = Players.ANY_SAVE;
		boardArray[16] = Players.ANY_SAVE;
		boardArray[71] = Players.ANY_SAVE;
		boardArray[75] = Players.ANY_SAVE;
		boardArray[79] = Players.ANY_SAVE;
		
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++){
			expectedArray[i] = boardArray[i];
		}
		
		ig = new InformationGatherer(Players.P2);
		//ig.setCardsForPlayer(new int[] { 34, 100, 51, -1, -1, -1 }, Players.P2);
		ig.setCardsForPlayer(new int[] { 7, -1, -1, -1, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		
		msg.debug(this, board.toString()); 
		
		List<Move> possible = board.getAllPossibleMoves(Players.P2);
		msg.debugLegalMoves(this, possible); 
	
		//Test if move 7 is made correct
		expectedArray[16] = 0;
		expectedArray[14] = 0;
		expectedArray[19] = 2;
		Move m = possible.get(7);
		board.makeMove(m, Players.P2);
		assertArrayEquals(expectedArray, boardArray);
		assertTrue(m.getwasProtected()[0]);
		assertTrue(m.getHits().length == 0);
		
		//Test if undoing move 7 works correctly
		expectedArray[16] = 5;
		expectedArray[14] = 2;
		expectedArray[19] = 0;
		board.undoMove(Players.P2);
		assertArrayEquals(expectedArray, boardArray);
		
		
		for (int i = 0; i < possible.size(); i++)
		{
			board.makeMove(possible.get(i), Players.P2);
			board.undoMove(Players.P2);
			msg.debug(this, "Nr: " + i); 
			msg.debug(this, this.board.toString()); 
			
			assertArrayEquals(expectedArray, this.board.getBoard());
		}
		assertArrayEquals(expectedArray, this.board.getBoard());
	}
	
	/*@Test
	public void testOptimizedGetPossibleMoves() {
		try{
			Method method = BotBoard.class.getDeclaredMethod("getPossibleMovesForJokerOptimized", new Class[]{int[].class, byte.class, boolean[].class});
			method.setAccessible(true);
			ig = new InformationGatherer(Players.P1);
			ig.setCardsForPlayer(new int[]{}, Players.P1);
			board = new BotBoard(boardArray, ig);
			boolean[] calculated = new boolean[13];
			for(int i = 0; i < calculated.length; i++){
				calculated[i] = true;
			}
			assertEquals(0, ((List<Move>)method.invoke(board, new Object[]{new int[]{}, Players.P1, calculated })).size());
			//assertTrue((Boolean)method.invoke(Rules.getInstance(), new Object[]{63, 3, (byte)1 }));
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
	}*/
	@Test
	public void testTerminalState()
	{
		byte[] boardArray = new byte[80];
		
		// heaven for Player 1
		boardArray[64] = Players.ANY_SAVE;
		boardArray[65] = Players.ANY_SAVE;
		boardArray[66] = Players.ANY_SAVE;
		boardArray[67] = Players.ANY_SAVE;
		
		// heaven for Player 3
		boardArray[72] = Players.ANY_SAVE;
		boardArray[73] = Players.ANY_SAVE;
		boardArray[74] = Players.ANY_SAVE;
		boardArray[75] = Players.ANY_SAVE;
		
		ig = new InformationGatherer(Players.P1);
		ig.setCardsForPlayer(new int[] { 7, -1, -1, -1, -1, -1 }, Players.P1);
		board = new BotBoard(boardArray, ig);
		
		assertTrue(board.terminalState(Players.P1)); 
		assertTrue(board.terminalState(Players.P3)); 
		assertEquals(false, board.terminalState(Players.P2));
		assertEquals(false, board.terminalState(Players.P4));  
	}
	@Test
	public void testGetFinishedPieces() {
		try{
			Method method = BotBoard.class.getDeclaredMethod("getFinishedPiecesForPlayer", new Class[]{byte.class});
			method.setAccessible(true);
			boardArray[10] = 1;
			boardArray[64] = 5;
			boardArray[66] = 5;
			boardArray[67] = 5;
			ig = new InformationGatherer(Players.P1);
			ig.setCardsForPlayer(new int[]{}, Players.P1);
			board = new BotBoard(boardArray, ig);
			
			assertArrayEquals(new int[]{67, 66}, (int[])method.invoke(board, new Object[]{Players.P1}));
			//assertEquals(0, ((List<Move>)method.invoke(board, new Object[]{new int[]{}, Players.P1, calculated })).size());
			//assertTrue((Boolean)method.invoke(Rules.getInstance(), new Object[]{63, 3, (byte)1 }));
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
	
	@Test
	public void testTerminalState2()
	{
		byte[] boardArray = new byte[80];
		
		// heaven for Player 1
		boardArray[64] = Players.ANY_SAVE;
		boardArray[65] = Players.ANY_SAVE;
		boardArray[66] = Players.ANY_SAVE;
		
		// heaven for Player 2
		boardArray[68] = Players.ANY_SAVE;
		boardArray[69] = Players.ANY_SAVE;
		boardArray[70] = Players.ANY_SAVE;
		boardArray[71] = Players.ANY_SAVE;
		
		// heaven for Player 3
		boardArray[72] = Players.ANY_SAVE;
		boardArray[73] = Players.ANY_SAVE;
		boardArray[74] = Players.ANY_SAVE;
		boardArray[75] = Players.ANY_SAVE;
		
		// heaven for Player 4
		boardArray[76] = Players.ANY_SAVE;
		boardArray[77] = Players.ANY_SAVE;
		boardArray[78] = Players.ANY_SAVE;
		boardArray[79] = Players.ANY_SAVE;
		
		ig = new InformationGatherer(Players.P2);
		ig.setCardsForPlayer(new int[] { 7, -1, -1, -1, -1, -1 }, Players.P2);
		board = new BotBoard(boardArray, ig);
		
		assertTrue(board.terminalState(Players.P2)); 
		assertTrue(board.terminalState(Players.P4)); 
		assertEquals(false, board.terminalState(Players.P1));
		assertEquals(false, board.terminalState(Players.P3));  
	}
	
}
