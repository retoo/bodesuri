package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

public class HeavenEvaluator implements Evaluator {

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
	public int evaluate(byte[] targetState, byte player, int[] card, float fading) {
		int sum = 0; 
		byte partner = 0; 
		Rules rules = Rules.getInstance();
		int switcher = (player % 2); 
		byte heavenPieces[] = new byte[4]; 
		byte heavenPiecesPartner[] = new byte[4]; 
		byte heavenPiecesEnemy1[] = new byte[4]; 
		byte heavenPiecesEnemy2[] = new byte[4]; 
	
		partner = rules.getPartnerForPlayer(player); 
		msg.debug(this, "player: " + player + ", partner: " + partner); 
		
	
		heavenPieces 		= rules.getPiecesInHeavenOfPlayer(targetState, player); 
		heavenPiecesPartner = rules.getPiecesInHeavenOfPlayer(targetState, partner); 
		
		if(switcher == 0)
		{
			heavenPiecesEnemy1 = rules.getPiecesInHeavenOfPlayer(targetState , (byte)(player-1)); 
			heavenPiecesEnemy2 = rules.getPiecesInHeavenOfPlayer(targetState , (byte)(partner-1));
			
			
		} else {
			heavenPiecesEnemy1 = rules.getPiecesInHeavenOfPlayer(targetState , (byte)(player+1));
			heavenPiecesEnemy2 = rules.getPiecesInHeavenOfPlayer(targetState , (byte)(partner+1));
			
		}
		
		
		for(int i=0; i<4; i++)
		{
			if(heavenPieces[i] != 0) sum += 15; 
			if(heavenPiecesPartner[i] != 0) sum += 15; 
			if(heavenPiecesEnemy1[i] != 0) sum -= 10; 
			if(heavenPiecesEnemy2[i] != 0) sum -= 10; 
		}
		msg.debug(this, "sum: " + sum); 
		return sum;
	}


}
