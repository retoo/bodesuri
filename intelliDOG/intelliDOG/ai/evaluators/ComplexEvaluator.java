package intelliDOG.ai.evaluators;

import java.util.ArrayList;

/**
 * 
 * The class ComplexEvaluator contains a list of simple evaluation algorithms.
 * The goal is to compose several small evaluators modularly which represents 
 * a complete evaluation algorithm for the game. 
 *  
 */
public class ComplexEvaluator implements Evaluator {

	private ArrayList<Evaluator> childEvaluators = new ArrayList<Evaluator>(); 
	

	/**
	 * Add a new evaluator to the list
	 * @param evaluator a new evaluator that will be added 
	 */
    public void add(Evaluator evaluator) {
        childEvaluators.add(evaluator);
    }
 
   
    /**
	 * Remove an evaluator from the list
	 * @param evaluator the evaluator that should be removed
	 */
    public void remove(Evaluator evaluator) {
    	childEvaluators.remove(evaluator);
    }
    
    @Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player) {
		
		int sum = 0; 
		
		for (Evaluator e : childEvaluators) {
		        sum += e.evaluate(actualState, targetState, player);
		}
		   
		return sum;
	}

    
    /**
     * Returns the number of evaluators which are currently in the list
     * @return number of evaluators
     */
    public int getNbrOfEvaluators() {
    	return this.childEvaluators.size(); 
    }
	
    
    /**
     * This method is mainly used for the JUnit test. 
     * Checks if an evaluator exists in the ArrayList
     * @param e evaluator to be checked
     * @return true if the evaluator can be found in the list, false otherwise
     */
    public boolean containsEvaluator(Evaluator e) {
    	return (childEvaluators.contains(e)); 
    }
    
    
    @Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player, int[] card) {

		int sum = 0; 
		
		for (Evaluator e : childEvaluators) {
		        sum += e.evaluate(actualState, targetState, player, card);
		}
		
		return sum;
	}

	
    @Override
	public int evaluate(byte[] targetState, byte player) {

		int sum = 0; 
		
		for (Evaluator e : childEvaluators) {
		        sum += e.evaluate(targetState, player);
		}
		   
		return sum;
	}

    @Override
	public int evaluate(byte[] targetState, byte player, int[] card, float fading) {

		int sum = 0; 
		
		for (Evaluator e : childEvaluators) {
		        sum += e.evaluate(targetState, player, card, fading); 
		}
		   
		return sum;
	}
}

