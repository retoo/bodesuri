package intelliDOG.ai.test;

import intelliDOG.ai.bots.TwoStepBot;
import intelliDOG.ai.evaluators.SimpleEvaluatorV4;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.utils.DebugMsg;
import junit.framework.TestCase;
/**
 * Some tests for the TwoStepBot
 */
public class TwoStepBotTest extends TestCase
{
	byte[] board = null;
	SimpleEvaluatorV4 testObject = new SimpleEvaluatorV4(); 
	static DebugMsg msg = DebugMsg.getInstance();
	
	
	/**
	 * initialize
	 */
	public void setUp()
	{
		board = new byte[80];
		msg.addItemForWhiteList(this);
		msg.addItemForWhiteList(testObject);
		msg.addItemForWhiteList(TwoStepBot.class.getCanonicalName());
		msg.addItemForWhiteList(BotBoard.class.getCanonicalName());
		
	}
	
	/**
	 * test behaviour with the heaven
	 */
	public void testGoHeavenOne() throws Exception
	{	
		byte player = 1; 
		board[45] = 1;
		int[] mycards = new int[]{13,4,11,-1,-1,-1};
		assertEquals(728, doTest(player, mycards));
	}
	/**
	 * second test for the heaven. In some cases, it was more attractive to do some
	 * nonsense moves instead of moving into the heaven
	 */
	public void testGoHeavenTwo() throws Exception
	{
		byte player = 1; 
		board[57] = 1;
		int[] mycards = new int[]{6,100,-1,-1,-1,-1};
		assertEquals(930, doTest(player, mycards));
	}
	/**
	 * testcase for the exception "fourteen"
	 */
	public void testFourteen() throws Exception
	{
		byte player = 4;
		//player four
		board[77] = 5;
		board[78] = 5;
		board[79] = 5;
		board[76] = 5;
		//player two
		board[68] = 5;
		board[71] = 5;
		board[16] = 5;
		board[5] = 2;
		//player one
		board[67] = 5;
		board[66] = 5;
		//player three
		board[74] = 5;
		board[75] = 5;
		board[13] = 3; 
		int[] mycards = new int[]{7,100,9,6,10,-1};
		assertEquals(4228, doTest(player, mycards));
		
	}
	/**testcase with ace**/
	public void testAce() throws Exception
	{
		byte player = 4;
		board[61] = player;
		int[] mycards = new int[]{13,-1,-1,-1,-1,-1};
		assertEquals(551, doTest(player, mycards));
	}
	/**move into the heaven **/
	public void testmoveIntoHeaven() throws Exception
	{
		byte player = 4;
		board[44] = player;
		int[] mycards = new int[]{6,5,2,-1,-1,-1};
		assertEquals(1048, doTest(player, mycards));
	}
	/**all own tokens are in the heaven, should move now with the
	 * allied one in his heave **/
	public void testmoveIntoHeavenWithAlly() throws Exception
	{
		byte player = 1;
		board[64] = 5;
		board[65] = 5;
		board[66] = 5;
		board[67] = 5;
		board[73] = 5;
		board[74] = 5;
		board[75] = 5;
		board[28] = 3;
		board[62] = 2; 
		int[] mycards = new int[]{2,5,-1,-1,-1,-1};
		assertEquals(6000, doTest(player, mycards));
	}
	/** as above, but can't finish the game **/
	public void testmoveIntoHeavenWithAlly2() throws Exception
	{
		byte player = 1;
		board[64] = 5;
		board[65] = 5;
		board[66] = 5;
		board[67] = 5;
		board[73] = 5;
		board[74] = 5;
		board[61] = 3;
		board[28] = 3;
		board[62] = 2; 
		int[] mycards = new int[]{2,5,-1,-1,-1,-1};
		assertEquals(4832, doTest(player, mycards));
	}
	/** test the tactical play ability with a 4 backwards
	 * then use 7 **/
	public void testMoveHeaven() throws Exception
	{
		byte player = 1;
		board[64] = 5;
		board[65] = 5;
		board[67] = 5;
		board[0] = 5; 
		int[] mycards = new int[]{100,7,-1,-1,-1,-1};
		assertEquals(2420, doTest(player, mycards));
	}
	/**
	 * evacuate the 49 or go in with the 60?
	 */
	public void testMoveIntoHeaven2() throws Exception
	{
		byte player = 1;
		board[60] = 1; 
		board[49] = 1; 
		board[48] = 5; 
		int[] mycards = new int[]{8,9,13,-1,-1,-1};
		assertEquals(1130, doTest(player, mycards));
	}
	/**
	 * use joker or nine to get in? 
	 **/
	public void testAgainstJoker() throws Exception
	{
		byte player = 1;
		board[57] = 1; 
		int[] mycards = new int[]{100,9,-1,-1,-1,-1};
		assertEquals(1057, doTest(player, mycards));
	}
	/**
	 * joker or four to move 4 backwards?
	 */
	public void testKarte()
	{
		byte player = 1;
		board[0] = 5; 
		int[] mycards = new int[]{100,4,-1,-1,-1,-1};
		assertEquals(769, doTest(player, mycards));
	}
	/**move from bankfield */
	public void testMoveOut()
	{
		byte player = 1;
		board[64] = 5; 
		int[] mycards = new int[]{100,-1,-1,-1,-1,-1};
		assertEquals(940, doTest(player, mycards));
	}
	/**use the four to move backwards, not forward */
	public void testUse4wisely()
	{
		byte player = 1;
		board[59] = 1; 
		int[] mycards = new int[]{10,4,-1,-1,-1,-1};
		assertEquals(744, doTest(player, mycards));
	}
	
	/** help method */
	private int doTest(byte player, int[] mycards)
	{
		InformationGatherer ig = new InformationGatherer(player);
		ig.setCardsForPlayer(mycards, player);
		BotBoard b = new BotBoard(board, ig);
		TwoStepBot bot = new TwoStepBot(b,ig);
		bot.makeMove();
		return bot.getHighestValue();
		
		
	}
}
