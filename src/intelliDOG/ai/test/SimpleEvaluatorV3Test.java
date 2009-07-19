package intelliDOG.ai.test;

import intelliDOG.ai.evaluators.SimpleEvaluatorV3;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.utils.DebugMsg;

import java.util.List;

import junit.framework.TestCase;
/**
 * test cases for the SimpleEvaluatorV3
 */
public class SimpleEvaluatorV3Test extends TestCase
{
	private byte[] board = null;
	private SimpleEvaluatorV3 eval = new SimpleEvaluatorV3(); 
	static DebugMsg msg = DebugMsg.getInstance();
	/**
	 * initialize
	 */
	public void setUp()
	{
		System.out.println("!!!!!!!!!!!!!new test!!!!!!!!!!!!!!!!!");
		board = new byte[80];
		msg.addItemForWhiteList(eval);
	}
	/**
	 * test a standard situation, look if a longer move
	 * is more attractive 
	 */
	public void testNormal() throws Exception 
	{
		byte player = 1;
		board[15] = 3;
		int[] mycards = new int[]{3,13,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(587,highest);
	}
	/**
	 * test the blocking conditions
	 */
	public void testBlock() throws Exception 
	{
		byte player = 1;
		board[15] = 1;
		int[] mycards = new int[]{3,13,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(560,highest);
	}
	/**
	 * test the blocking conditions with more pawns on the field
	 */
	public void testBlock2() throws Exception 
	{
		byte player = 1;
		board[30] = 1;
		board[39] = 2; 
		int[] mycards = new int[]{10,13,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(540,highest);
	}
	/**
	 * test the blocking condition without the possibility to move into 
	 * the heaven 
	 */
	public void testBlock3() throws Exception 
	{
		byte player = 1;
		board[30] = 1;
		board[60] = 2; 
		int[] mycards = new int[]{10,13,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(540,highest);
	}
	/**
	 * test the blocking condition with the possibility to move into 
	 * the heaven 
	 */
	public void testBlockAndHeaven() throws Exception 
	{
		byte player = 1;
		board[2] = 2;
		board[4] = 2;
		board[60] = 1; 
		int[] mycards = new int[]{5,13,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(637,highest);
	}
	/**
	 * test the swapping possibility. It should choose the 
	 * nearest from the heaven except he may help his ally
	 */
	public void testSwap() throws Exception
	{
		byte player = 1;
		board[33] = 3;
		board[30] = 1;
		board[58] = 2;
		int[] mycards = new int[]{11,-1,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(576,highest);
	}
	/**
	 * here, the decision should be more clear with the swap possibility
	 */
	public void testSwap2() throws Exception
	{
		byte player = 1;
		board[33] = 3;
		board[18] = 1;
		board[58] = 2;
		int[] mycards = new int[]{11,-1,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(576,highest);
	}
	/**
	 * move to heaven in a simple case
	 */
	public void testMoveToHeaven() throws Exception
	{
		byte player = 1;
		board[33] = 1;
		board[60] = 1;
		int[] mycards = new int[]{8,12,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(876,highest);
	}
	/**
	 * look in front an identify targets
	 */
	public void testLookInFront() throws Exception 
	{
		byte player = 1;
		board[30] = 1;
		board[39] = 2; 
		int[] mycards = new int[]{10,8,-1,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(526,highest);
	}
	/**
	 * look in front with another player
	 */
	public void testLookInFront2() throws Exception 
	{
		byte player = 2;
		board[0] = 1;
		board[50] = 2; 
		int[] mycards = new int[]{10,3,6,-1,-1,-1};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards,1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
		}
		assertEquals(520,highest);
	}
	/**
	 * complex situation with a very good chance to play the 7
	 */
	public void testcomplexSituation() throws Exception
	{
		byte player = 1;
		board[66] = 5;
		board[40] = 1;
		board[0] = 5;
		board[6] = 2;
		board[16] = 5;
		board[69] = 5;
		board[32] = 5;
		board[48] = 3;
		board[7] = 3;
		board[60] = 4;
		board[4] = 4;
		board[2] = 4;
		board[79] = 5;
		int[] mycards = new int[]{8,12,4,5,13,7};
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		List<Move> possible = b.getAllPossibleMoves(player); 
		int highest = 0; 
		for(int i = 0; i < possible.size(); i++)
		{
			b.makeMove(possible.get(i),player);
			int result = eval.evaluate(b.getBoard(), player, mycards, 1);
			if(highest < result)
			{
				highest = result;
			}
			b.undoMove(player);
			eval.printPawns();
		}
		assertEquals(742,highest);
		
	}
}
