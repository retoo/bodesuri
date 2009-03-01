package intelliDOG.ai.test;

import intelliDOG.ai.evaluators.BlockEvaluator;
import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.utils.DebugMsg;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BlockEvaluatorTest {

	private Evaluator evaluator;  
	private byte[] board; 
	private DebugMsg msg = DebugMsg.getInstance(); 
	
	@Before
	public void setUp() throws Exception {
		evaluator = new BlockEvaluator();
		board = new byte[80];
	}

	@Test
	public void testBlockingHome()
	{
		board[0] = Players.ANY_SAVE; 
		board[63] = Players.P3; // partner is one field behind -15
		board[59] = Players.P2; // enemy is five fields behind 20-5 = +15
		board[54] = Players.P4; // enemy is ten fields behind (20-10) = +10
		int[] mycards = new int[]{100,4,-1,-1,-1,-1}; // FIXME: i don't need that
		int res = evaluator.evaluate(board, Players.P1, mycards, 1.0f); 
		assertEquals(10, res); 
	}

	@Test
	public void testBlockingMe_and_Partner()
	{
		board[0] = Players.ANY_SAVE; 
		 
		board[63] = Players.P3; // partner is one field behind -15
		board[58] = Players.P2; // enemy is six fields behind 20-6 = +14
		board[50] = Players.P4; // enemy is fourteen fields behind (20-14) = +6
		
		board[32] = Players.ANY_SAVE;
		board[28] = Players.P1; // I'm four fields behind my partner (-15)
		board[20] = Players.P4; // enemy is twelve fields behind my partner (20-12) = +8
		int[] mycards = new int[]{100,4,-1,-1,-1,-1}; // FIXME: i don't need that
		int res = evaluator.evaluate(board, Players.P1, mycards, 1.0f); 
		assertEquals(-2, res); 
	}
	
	
	@Test
	public void testBlocking_All()
	{
		board[0] = Players.ANY_SAVE; 
		 
		board[63] = Players.P4; // enemy right behind (+15)
		board[60] = Players.P2; // enemy right behind (+15)
		
		board[32] = Players.ANY_SAVE;
		board[28] = Players.P2; // enemy right behind partner (+15)
		board[20] = Players.P4; // enemy is twelve fields behind my partner (20-12) = +8
		int[] mycards = new int[]{100,4,-1,-1,-1,-1}; // FIXME: i don't need that
		int res = evaluator.evaluate(board, Players.P1, mycards, 1.0f); 
		assertEquals(53, res); 
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
