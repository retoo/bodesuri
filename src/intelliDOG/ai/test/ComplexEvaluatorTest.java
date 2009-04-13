
package intelliDOG.ai.test;

import static org.junit.Assert.*;
import intelliDOG.ai.evaluators.BlockEvaluator;
import intelliDOG.ai.evaluators.ComplexEvaluator;
import intelliDOG.ai.evaluators.EnvironmentEvaluator;
import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.NbrPawnsEvaluator;
import intelliDOG.ai.evaluators.SimpleEvaluator;
import intelliDOG.ai.framework.Players;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class to verify that ComplexEvaluator is working correctly
 *
 */
public class ComplexEvaluatorTest {

	private ComplexEvaluator evaluator;
	private byte[] targetState = new byte[80]; 
	private byte player; 
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		evaluator = new ComplexEvaluator(); 
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link intelliDOG.ai.evaluators.ComplexEvaluator#add(intelliDOG.ai.evaluators.Evaluator)}.
	 */
	@Test
	public void testAdd() {
		
		Evaluator e1 = new SimpleEvaluator();
		Evaluator e2 = new NbrPawnsEvaluator();
		assertEquals(evaluator.getNbrOfEvaluators(), 0); 
		evaluator.add(e1);
		assertEquals(evaluator.getNbrOfEvaluators(), 1); 
		evaluator.add(e2); 
		assertEquals(evaluator.getNbrOfEvaluators(), 2); 
	}

	/**
	 * Test method for {@link intelliDOG.ai.evaluators.ComplexEvaluator#remove(intelliDOG.ai.evaluators.Evaluator)}.
	 * Test to verify correctness of adding and removing evaluators.
	 */
	@Test
	public void testRemove() {
		
		Evaluator e1 = new SimpleEvaluator();
		Evaluator e2 = new NbrPawnsEvaluator();
		Evaluator e3 = new EnvironmentEvaluator();
		assertEquals(0, evaluator.getNbrOfEvaluators()); 
		evaluator.add(e1);
		assertEquals(1, evaluator.getNbrOfEvaluators()); 
		evaluator.add(e2); 
		assertEquals(2, evaluator.getNbrOfEvaluators()); 
		evaluator.remove(e1); 
		assertFalse(evaluator.containsEvaluator(e1));
		assertTrue(evaluator.containsEvaluator(e2));
		
		evaluator.add(e3); 
		evaluator.remove(e2); 
		assertFalse(evaluator.containsEvaluator(e1));
		assertFalse(evaluator.containsEvaluator(e2));
		assertTrue(evaluator.containsEvaluator(e3));
		assertEquals(1, evaluator.getNbrOfEvaluators()); 
		evaluator.remove(e3); 
		assertEquals(0, evaluator.getNbrOfEvaluators()); 
	}


	/**
	 * Test method for {@link intelliDOG.ai.evaluators.ComplexEvaluator#evaluate(byte[], byte)}.
	 * Test with combination of NbrOfPawns and BlockEvaluator.
	 */
	@Test
	public void testEvaluateByteArrayByte() {
		
		int sum = 0; 
		
		createTargetState(targetState, Players.P1);
		
		Evaluator nbrPieces = new NbrPawnsEvaluator(); 
		evaluator.add(nbrPieces); 
		Evaluator blockeval = new BlockEvaluator(); 
		evaluator.add(blockeval); 
		
		// nbrofpawns: 9
		// block: 6
		sum = evaluator.evaluate(targetState, player, null, 1.0f); 
		assertEquals(15, sum); 
		
		createTargetState(targetState, Players.P3);
		sum = evaluator.evaluate(targetState, player, null, 1.0f); 
		assertEquals(15, sum);
		
		// both P2 and his partner P4 are blocking several enemies
		// nbrofpawns: -9
		// block: 66
		createTargetState(targetState, Players.P2);
		sum = evaluator.evaluate(targetState, player, null, 1.0f); 
		assertEquals(57, sum);
	}

	
	/**
	 * Initialize the board with some specific settings, in order to verify that the evaluation
	 * method returns the correct sum. 
	 * @param b board to be initialized with a given state
	 */
	public void createTargetState(byte[] b, byte p) {
		
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
	}
}
