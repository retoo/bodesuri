package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

/**
 * That evaluator does only perform an evaluation if the homefield of the current
 * player or his partner is occupied. In this case the evaluator looks if pawns of the enemy can be 
 * blocked. It also takes in consideration blocking allied pawns.  
 * 
 */
public class BlockEvaluator implements Evaluator {

	private DebugMsg msg = DebugMsg.getInstance(); 
	
	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player,
			int[] card) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int[] card,
			float fading) 
	{
		
		
		Rules r = Rules.getInstance(); 
		byte partner; 
		int sum = 0; 
		
		if(r.allPiecesInHeavenOfPlayer(targetState, player))
		{
			player = (byte) r.getPartnerForPlayer(player);
		}
		partner = r.getPartnerForPlayer(player); 
	
		byte enemyPlayerOne = (byte) (player +1);
		if(enemyPlayerOne == 5)
		{
			enemyPlayerOne = 1; 
		}
		byte enemyPlayerTwo =   r.getPartnerForPlayer(enemyPlayerOne);
		
		
		// I'm on homefield
		int myHome = r.getHomePositionForPlayer(player); 
		if(targetState[myHome] == 5)
		{
			// first only consider 4 fields
			// The probability that an enemy has to give up is higher. 
			for(int j = 1; j<5; j++)
			{
				
				int playerBehind = targetState[(64 + myHome - j) %64];
				if(playerBehind != 0)
				{
					
					if(playerBehind == enemyPlayerOne || playerBehind == enemyPlayerTwo)
						sum += 15; 
					else if(playerBehind == partner)
						sum -= 15; 
					
				}
			}
			
			//consider fields from 5 to 14
			// The probability that an enemy has to give up is higher. 
			int q = 0; 
			for(int j = 5; j<15; j++)
			{
				int playerBehind = targetState[(64 + myHome - j) %64];
				if(playerBehind != 0)
				{
					if(playerBehind == enemyPlayerOne || playerBehind == enemyPlayerTwo)
						sum += 20-j; 
					else if(targetState[j] == partner)
						sum -= 20-j; 
				}
			}
			
	
		} // end my player
		
		
		// evaluate in case if my partner is on the homfield and could possibly block
		int partnerHome = r.getHomePositionForPlayer(r.getPartnerForPlayer(player));  
		
		if(targetState[partnerHome] == 5)
		{
			// first only consider 5 fields
			// The probability that an enemy has to give up is higher. 
			for(int j = 1; j<5; j++)
			{
				int playerBehind = targetState[(64 + partnerHome - j) %64];
				
				if(playerBehind != 0)
				{
					
					if(playerBehind == enemyPlayerOne || playerBehind == enemyPlayerTwo)
						sum += 15; 
					else if(playerBehind == player) // He probably would have to block me too 
						sum -= 15; 
					
				}
			}
			
			//consider fields from 5 to 14
			// The probability that an enemy has to give up is higher. 
			int q = 0; 
			for(int j = 5; j<15; j++)
			{
				
				int playerBehind = targetState[(64 + partnerHome - j) %64];
				if(playerBehind != 0)
				{
					if(playerBehind == enemyPlayerOne || playerBehind == enemyPlayerTwo)
						sum += 20-j; 
					else if(playerBehind == partner)
						sum -= 20-j;
				}
			}
			
	
		} // end  homepos for partner
		msg.debug(this, "sum: " + sum); 
		return sum;

	}
}
