package intelliDOG.ai.test;

import intelliDOG.ai.evaluators.SimpleEvaluator;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.utils.DebugMsg;
import junit.framework.TestCase;

/**
 * test class for the first SimpleEvaluator. 
 * Goal here was to test the calculation mechanism
 */
public class SimpleEvaluatorTest extends TestCase
{
	byte[] board = null;
	SimpleEvaluator testObject = new SimpleEvaluator(); 
	DebugMsg msg = DebugMsg.getInstance();
	
	@Override
	public void setUp()
	{
		board = new byte[80];
		msg.addItemForWhiteList(testObject);
		msg.addItemForWhiteList(BotBoard.class.getCanonicalName());
		
	}
	/** test the calculation function**/
	public void testNoMoveYet() throws Exception
	{
		byte player = 1; 
		int result = testObject.evaluate(board, player);
		assertEquals(24, result);
	}
	/** test the calculation function**/
	public void testSimpleHeaven() throws Exception
	{
		byte player = 1;
		board[64] = 1; 
		int result = testObject.evaluate(board, player);
		//24 + 1 + 5
		assertEquals(30, result);
	}
	/** test the calculation function**/
	public void testMayRollOverEnemy() throws Exception
	{
		byte player = 1;
		board[1] = 1;
		board[2] = 2;
		//eigener im feld + kann schlagen (nah) + 7 gegner im aus 
		//1 + 3 + 21
		int result = testObject.evaluate(board, player);
		assertEquals(25, result);
	}
	/** test the calculation function**/
	public void testMayRollOverEnemy2() throws Exception
	{
		byte player = 1;
		board[1] = 1;
		board[2] = 2;
		board[3] = 2;
		//eigener im feld + kann schlagen (nah)(2) + 6 gegner im aus 
		//1 + 6 + 18
		int result = testObject.evaluate(board, player);
		assertEquals(25, result);
	}
	/** test the calculation function**/
	public void testMayRollOverEnemy3() throws Exception
	{
		byte player = 1;
		board[1] = 1;
		board[2] = 2;
		board[9] = 4;
		//eigener im feld + kann schlagen (nah) und fern + 6 gegner im aus 
		//1 + 5 +18
		int result = testObject.evaluate(board, player);
		assertEquals(24, result);
	}
	/** test the calculation function**/
	public void testBackward4behind() throws Exception
	{
		byte player = 1;
		board[1] = 2;
		board[5] = 1;
		int result = testObject.evaluate(board, player);
		//1 + 1 -2 +21
		assertEquals(21, result);
	}
	/** test the calculation function**/
	public void testBehaviourwithAlly() throws Exception
	{
		byte player = 1;
		board[1] = 1;
		board[2] = 3;
		int result = testObject.evaluate(board, player);
		//1 + 1 + 24
		assertEquals(26, result);
	}	
	/** test the calculation function**/
	public void testBehaviourwithAlly2() throws Exception
	{
		byte player = 1;
		board[2] = 1;
		board[1] = 3;
		int result = testObject.evaluate(board, player);
		//1 + 1 + 24
		assertEquals(26, result);
	}
	/** test the calculation function**/
	public void testStartPointOccupied() throws Exception
	{
		byte player = 1;
		board[0] = 1;
		int result = testObject.evaluate(board, player);
		//1+2+24
		assertEquals(27, result);
	}
	/** test the calculation function**/
	public void testStartPointOccupied2() throws Exception
	{
		byte player = 1;
		board[0] = 3;
		board[1] = 1; 
		int result = testObject.evaluate(board, player);
		//1+1+24
		assertEquals(26, result);
	}
	/** test the calculation function**/
	public void otherPlayer() throws Exception
	{
		byte player = 2;
		board[2] = player; 
		board[4] = 4;
		board[40] = 1;
		int result = testObject.evaluate(board, player);
		// 1 + 1 + 21 
		assertEquals(23, result);
	}
	/** test the calculation function**/
	public void otherPlayer2() throws Exception
	{
		byte player = 2;
		board[2] = player; 
		board[4] = 4;
		board[39] = player;
		board[40] = 1;
		int result = testObject.evaluate(board, player);
		// 2 + 1 +3 + 18 
		assertEquals(25, result);
	}

}
