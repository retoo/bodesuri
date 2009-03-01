package intelliDOG.ai.test;

import static org.junit.Assert.*;
import intelliDOG.ai.evaluators.SimpleCardEvaluator;
import intelliDOG.ai.framework.Cards;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleCardEvaluatorTest {

	private SimpleCardEvaluator evaluator; 
	private byte player; 
	private byte[] board; 
	
	@Before
	public void setUp() throws Exception {
		 
		evaluator = new SimpleCardEvaluator();
		board = new byte[80]; 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCards_01()
	{
		int c[] = new int[6];
		int sum = 0; 
		
		c[0] 	= Cards.HEARTS_ACE; 
		c[1] 	= Cards.HEARTS_EIGHT; 
		c[2]	= Cards.HEARTS_FOUR; 
		c[3] 	= Cards.HEARTS_KING; 
		c[4]	= Cards.HEARTS_SEVEN; 
		c[5] 	= Cards.JOKER; 
		
		sum = evaluator.evaluate(board, board, player, c); 
		assertEquals(84, sum); 
	}
	
	@Test
	public void testCards_02()
	{
		int c[] = new int[6];
		int sum = 0; 
		c[0] 	= Cards.CLUBS_TWO; 
		c[1] 	= Cards.CLUBS_THREE; 
		c[2]	= Cards.CLUBS_FOUR; 
		c[3] 	= Cards.CLUBS_FIVE; 
		c[4]	= Cards.CLUBS_SIX; 
		c[5] 	= Cards.CLUBS_SEVEN; 
		
		sum = evaluator.evaluate(board,board,player, c); 
		assertEquals(22, sum); 
	}
	
	@Test 
	public void testKing()
	{
		int sum = 0; 
		int c[] = new int[6]; 
		
		c[0] = Cards.DIAMONDS_KING; 
		for(int i=1; i<c.length; i++)
			c[i] = -1; 
		sum = evaluator.evaluate(board, board, player, c); 
		assertEquals(5, sum); 
	}
	
	@Test 
	public void testJack()
	{
		int sum = 0; 
		int c[] = new int[6];
		
		c[0] = Cards.DIAMONDS_JACK; 
		for(int i=1; i<c.length; i++)
			c[i] = -1; 
		
		sum = evaluator.evaluate(board, board, player, c); 
		assertEquals(2, sum); 
	}
	
	@Test
	public void testJoker()
	{
		int sum = 0; 
		int c[] = new int[6];
		
		c[0] = Cards.JOKER; 
		for(int i=1; i<c.length; i++)
			c[i] = -1; 
		
		sum = evaluator.evaluate(board, board, player, c); 
		assertEquals(50, sum); 
	}
	
}
