package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Players;
import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

import java.util.ArrayList;


/**
 * 
 * This Evaluator counts the number of pawns of the current player and his team-mate as well 
 * as the number of enemy pawns. 
 * sum = myPawns - enemyPawns
 *
 */
public class NbrPawnsEvaluator implements Evaluator {

	private DebugMsg msg = DebugMsg.getInstance(); 
	private float weight = 3; 

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
		
		int sum = 0; 
		Rules r = Rules.getInstance(); 
		byte partner = r.getPartnerForPlayer(player); 
		int myHome = r.getHomePositionForPlayer(player); 
		int partnerHome = r.getHomePositionForPlayer(partner);  
		
		ArrayList<Integer> myPawns = new ArrayList<Integer>();
		ArrayList<Integer> enemyPawns = new ArrayList<Integer>();
		
		//add the positions of each pawn into the datastructure
		for(int i =0; i<80; i++)
		{
			if(targetState[i] == 0)
			{
				//do nothing 
			}
			
			// This is a home position. Heaven positions are added later
			else if(targetState[i] == Players.ANY_SAVE)
			{
				if( (i == myHome || i== partnerHome ) )
					myPawns.add(i); 
				else if(i<64) // add to enemy array only if it's not a heaven field
					enemyPawns.add(i); 
			}
			else if(targetState[i] == player || targetState[i] == partner)
			{
				myPawns.add(i);
			}
			else
			{
				enemyPawns.add(i);
			}
		}
		 
		byte enemyPlayerOne = (byte) (player +1);
		if(enemyPlayerOne == 5)
			enemyPlayerOne = 1; 
	
		byte enemyPlayerTwo =   r.getPartnerForPlayer(enemyPlayerOne);
		
		
		int nr1 = r.nrOfPiecesInHeavenOfPlayer(targetState, player);
		int nr2 = r.nrOfPiecesInHeavenOfPlayer(targetState, partner);
		int nr3 = r.nrOfPiecesInHeavenOfPlayer(targetState, enemyPlayerOne); 
		int nr4 = r.nrOfPiecesInHeavenOfPlayer(targetState, enemyPlayerTwo);
		
		
		// Number of my pawns and my teammate's pawns - enemy player pawns 
		sum = myPawns.size();
		sum = sum + nr1 + nr2; 
		sum = sum -nr3 - nr4; 
		
		sum -= enemyPawns.size(); 
		
		sum *= weight; 
		msg.debug(this, "sum: " + sum); 
		return sum;
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int[] card, float fading) {
		int sum = 0; 
		
		Rules r = Rules.getInstance(); 
		byte partner = r.getPartnerForPlayer(player); 
		int myHome = r.getHomePositionForPlayer(player); 
		int partnerHome = r.getHomePositionForPlayer(partner);  
		
		ArrayList<Integer> myPawns = new ArrayList<Integer>();
		ArrayList<Integer> enemyPawns = new ArrayList<Integer>();
		
		//add the positions of each pawn into the datastructure
		for(int i =0; i<80; i++)
		{
			if(targetState[i] == 0)
			{
				//do nothing 
			}
			
			// Hack to determine the ANY_SAVE field that belongs to the player 
			else if(targetState[i] == Players.ANY_SAVE)
			{
				if( i ==  myHome || i == partnerHome)
					myPawns.add(i); 
				else if(i<64)
					enemyPawns.add(i); 
			}
			else if(targetState[i] == player || targetState[i] == partner)
			{
				myPawns.add(i);
			}
			else
			{
				enemyPawns.add(i);
			}
		}
		byte enemyPlayerOne = (byte) (player +1);
		if(enemyPlayerOne == 5)
			enemyPlayerOne = 1; 
	
		byte enemyPlayerTwo =   r.getPartnerForPlayer(enemyPlayerOne);
		
		
		int nr1 = r.nrOfPiecesInHeavenOfPlayer(targetState, player);
		int nr2 = r.nrOfPiecesInHeavenOfPlayer(targetState, partner);
		int nr3 = r.nrOfPiecesInHeavenOfPlayer(targetState, enemyPlayerOne); 
		int nr4 = r.nrOfPiecesInHeavenOfPlayer(targetState, enemyPlayerTwo);
		
		
		// Number of my pawns and my teammate's pawns - enemy player pawns 
		sum = myPawns.size();
		sum = sum + nr1 + nr2; 
		sum = sum -nr3 - nr4; 
		
		sum -= enemyPawns.size(); 
		
		sum *= weight; 
		msg.debug(this, "sum: " + sum); 
		return sum;
	}
}
