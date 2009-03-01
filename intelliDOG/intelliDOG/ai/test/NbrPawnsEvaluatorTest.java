package intelliDOG.ai.test;

import static org.junit.Assert.*;
import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.NbrPawnsEvaluator;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.utils.DebugMsg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NbrPawnsEvaluatorTest {

	
	private Evaluator evaluator;
	private byte[] board; 
	private byte player; 
	private DebugMsg msg = DebugMsg.getInstance(); 
	
	
	@Before
	public void setUp() throws Exception {
		
		evaluator = new NbrPawnsEvaluator(); 
		board = new byte[80]; 
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * my player and my partner are on homefield
	 */
	@Test
	public void testOnHomeField()
	{
		int sum = 0; 
		board[0] = Players.ANY_SAVE; 
		board[32] = Players.ANY_SAVE; 
		sum = evaluator.evaluate(board, Players.P1, null, 1.0f); 
		assertEquals(6, sum); 
	}
	
	/**
	 * Two of my pawns are in heaven 
	 */
	@Test
	public void testInHeaven()
	{
		int sum = 0; 
		board[64] = Players.ANY_SAVE; 
		board[65] = Players.ANY_SAVE; 
		sum = evaluator.evaluate(board, Players.P1, null, 1.0f); 
		assertEquals(6, sum); 
	}
	
	/**
	 * Two of my pawns are in heaven and three of enemy pawns
	 */
	@Test
	public void testInHeaven2()
	{
		int sum = 0; 
		board[64] = Players.ANY_SAVE; 
		board[65] = Players.ANY_SAVE; 
		board[68] = Players.ANY_SAVE; 
		sum = evaluator.evaluate(board, Players.P1, null, 1.0f); 
		assertEquals(3, sum); 
	}
	
	
	/**
	 * All my pawns are in heaven and one of my partner's 
	 */
	@Test
	public void testAllMyPawnsHeaven()
	{
		int sum = 0; 
		board[64] = Players.ANY_SAVE; 
		board[65] = Players.ANY_SAVE; 
		board[66] = Players.ANY_SAVE; 
		board[67] = Players.ANY_SAVE; 
		
		board[75] = Players.ANY_SAVE; 
		sum = evaluator.evaluate(board, Players.P1, null, 1.0f); 
		assertEquals(15, sum); 
	}
	
	@Test
	public void testEvaluateByteArrayByte() {
		
		int sum = 0; 
		board = createTargetState(Players.P1);
	
		sum = evaluator.evaluate(board, player); 
		assertEquals(9, sum); 
		
		board = createTargetState(Players.P3);
		sum = evaluator.evaluate(board, Players.P3); 
		assertEquals(9, sum);
		
		board = createTargetState(Players.P2);
		sum = evaluator.evaluate(board, Players.P2); 
		assertEquals(-9, sum);
		
		// P1 & P3 have 8 pawns together on the field. The other team has two together. 
		board = createTargetState2(Players.P1); 
		sum = evaluator.evaluate(board, Players.P1); 
		assertEquals(18, sum); 
		
		// P2 & P4 have 2 pawns together on the field. The other team has 8 together. 
		board = createTargetState2(Players.P2); 
		sum = evaluator.evaluate(board, Players.P2); 
		assertEquals(-18, sum);
		
		// P1 & P3 have 8 pawns together on the field. The other team has two together. 
		board = createTargetState2(Players.P3); 
		sum = evaluator.evaluate(board, Players.P3); 
		assertEquals(18, sum);
		
		// P2 & P4 have 2 pawns together on the field. The other team has 8 together. 
		board = createTargetState2(Players.P4); 
		sum = evaluator.evaluate(board, Players.P4); 
		assertEquals(-18, sum);
	}

	@Test 
	public void testNobodyOnField()
	{
		int sum = 0; 
		player = Players.P1; 
		sum = evaluator.evaluate(board, player); 
		assertEquals(0, sum); 
	}
	
	@Test 
	public void testP1_OnField()
	{
		int sum = 0; 
		board[33] = Players.P1; 
		sum = evaluator.evaluate(board, Players.P1); 
		assertEquals(3, sum); 
	}
	
	@Test 
	public void test2P1_OnField()
	{
		int sum = 0; 
		board[33] = Players.P1; 
		board[65] = Players.P1;
		 
		sum = evaluator.evaluate(board, Players.P1); 
		assertEquals(6, sum); 
	}
	
	@Test 
	public void test3P1_OnField()
	{
		int sum = 0; 
		board[33] = Players.P1; 
		board[65] = Players.P1;
		board[0] = Players.P1;
		
		sum = evaluator.evaluate(board, Players.P1); 
		assertEquals(9, sum); 
	}
	
	@Test 
	public void testAllP1_OnField()
	{
		int sum = 0; 
		board[33] = Players.P1; 
		board[65] = Players.P1;
		board[0] = Players.P1;
		board[4] = Players.P1;

		sum = evaluator.evaluate(board, Players.P1); 
		assertEquals(12, sum); 
	}
	
	@Test 
	public void testAllP1_P3_OnField()
	{
		int sum = 0; 
		board[33] = Players.P1; 
		board[65] = Players.P1;
		board[0] = Players.P1;
		board[4] = Players.P1;
		
		board[32] = Players.P3; 
		board[74] = Players.P3;
		board[34] = Players.P3;
		board[75] = Players.P3;
		
		sum = evaluator.evaluate(board, Players.P1); 
		assertEquals(24, sum); 
	}
	
	@Test 
	public void testEvenNbr_OnField()
	{
		int sum = 0; 
		// Team A
		board[33] = Players.P1; 
		board[65] = Players.P1;
		board[0] = Players.P1;
		board[4] = Players.P1;
		
		board[32] = Players.P3; 
		board[74] = Players.P3;
		board[34] = Players.P3;
		board[75] = Players.P3;
		
		// Team B
		board[16] = Players.P2; 
		board[17] = Players.P2;
		board[18] = Players.P2;
		board[70] = Players.P2;
		
		board[48] = Players.P4; 
		board[10] = Players.P4;
		board[77] = Players.P4;
		board[76] = Players.P4;
		
		
		player = Players.P1; 
		sum = evaluator.evaluate(board, player); 
		assertEquals(0, sum); 
		
		player = Players.P2; 
		sum = evaluator.evaluate(board, player); 
		assertEquals(0, sum); 
		
		player = Players.P3; 
		sum = evaluator.evaluate(board, player); 
		assertEquals(0, sum); 
		
		player = Players.P4; 
		sum = evaluator.evaluate(board, player); 
		assertEquals(0, sum); 
	}
	
	
	/**
	 * Initialize the board with some specific settings, in order to verify that the evaluation
	 * method returns the correct sum. 
	 * @param p player on turn
	 */
	public byte[] createTargetState(byte p) {
		
		byte b[] = new byte[80]; 
		
		// set player 1 on some fields
		b[0]  = 5;
		b[11] = 1; 
		b[15] = 1;
		
		// set player 2 on some fields
		b[16]  = 5;
		b[18] = 2; 
		
		// set player 3 on some fields
		b[32] = 5;
		b[37] = 3; 
		b[40] = 3; 
		b[47] = 3; 
		
		// set player 4 on some fields
		b[48]  = 5;
		b[79]  = 4; 
		
		this.player = p;
		return b; 
	}

	/**
	 * Initialize the board with some specific settings, in order to verify that the evaluation
	 * method returns the correct sum. 
	 * @param p player on turn
	 */
	public byte[] createTargetState2(byte p) {
		
		byte b[] = new byte[80]; 
		
		// set player 1 on some fields
		b[0]  = 5;
		b[1]  = 1; 
		b[64] = 1; 
		b[65] = 1; 
		
		// set player 2 on some fields
		b[16]  = 5;
		
		// set player 3 on some fields
		b[32] = 5;
		b[33] = 3; 
		b[72] = 3; 
		b[73] = 3; 
		
		// set player 4 on some fields
		b[48]  = 5;
		
		this.player = p;
		return b; 
	}
	
}
