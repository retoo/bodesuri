package intelliDOG.ai.evaluators;


/**
 * 
 * This evaluator only considers the cards without regarding the usefulness of them. 
 * For example is a seven more powerful if there are enemy pawns right in front of the 
 * current player or to get into heaven. 
 * This evaluator only rates special cards like King or Ace more than normal cards. 
 *
 */
public class SimpleCardEvaluator implements Evaluator {

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player,
			int[] card) {
		
		int aceWeight  = 10;  // 
		int kingWeight  = 5; // 
		int fourWeight  = 6;  //
		int sevenWeight  = 10; // 
		int jackWeight 	= 2; 
		// 2 and three are the only one which don't give points
		
		int sum = 0; 
		
		for(int i=0; i<card.length; i++)
		{
			int cur_card = card[i] % 13; 
			
			switch(cur_card)
			{
			case 1: // ACE
				sum += aceWeight; 
				break; 
				
			case 4: // Four
				sum += fourWeight; 
				break; 
				
			case 7: // Seven
				sum += sevenWeight; 
				break; 
					
			case 11: // Jack
				sum += jackWeight; 
				break; 
					
			case 0:  // King
				sum += kingWeight; 
				break; 
			
			case 2:
			case 3: 
				break; 
			
			case 5: case 6: case 8: case 9: case 10: case 12: 
				if(card[i] == 100)
					sum += 50; 
				else
					sum += 3; 
				break; 
			
			default: 

			}
		}
		return sum;
	}

	@Override
	public int evaluate(byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int[] card, float fading) {
	
		int aceWeight  = 10;  // 
		int kingWeight  = 10; // 
		int fourWeight  = 6;  //
		int sevenWeight  = 7; // 
		int jackWeight 	= 4; 
		// 2 and three are the only one which don't give points
		
		int sum = 0; 
		
		for(int i=0; i<card.length; i++)
		{
			int cur_card = card[i] % 13; 
			
			switch(cur_card)
			{
			case 1: // ACE
				sum += aceWeight; 
				break; 
				
			case 4: // Four
				sum += fourWeight; 
				break; 
				
			case 7: // Seven
				sum += sevenWeight; 
				break; 
					
			case 11: // Jack
				sum += jackWeight; 
				break; 
					
			case 0:  // King
				sum += kingWeight; 
				break; 
			
			case 2:
			case 3: 
				break; 
			
			case 5: case 6: case 8: case 9: case 10: case 12: 
				if(card[i] == 100)
					sum += 50; 
				else
					sum += 3; 
				break; 

			default: 
			}
		}
		return sum;
	
	}

}
