package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

import java.util.ArrayList;

/**
 * This version introduces an linear algorithm  which measured
 * the distance between the bank field and the actual position.
 */
public class SimpleEvaluatorV2 implements Evaluator {

	//positive influences
	private int myPawnsOnField = 10;
	private int myPawnsInHeaven = 150;
	private int myAllyOnField = 30;
	private int myAllyInHeaven = 40;
	private int myEnemyOffField = 60;
	private int myStartpointOccupied = 40;
	private int enemyInFrontUpTo7 = 20;
	private int enemyInFrontUpTo13 = 10;
	private int enemyBehind4 = 10;
	private int distanceToHeaven = 2; 
	private int distanceInHeaven = 30;
	//negative influences
	private int enemyBehindUpTo7 = -10;
	private int enemyBehindUpTo13 = -5;
	private int enemyInHeaven = -40;
	//support variables
	private DebugMsg msg = DebugMsg.getInstance(); 
	
	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player) {
		throw new UnsupportedOperationException("No state to state evaluation implemented!");
	}

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player,
			int card[]) {
		throw new UnsupportedOperationException("No state to state evaluation implemented!");
	}

	/**
	 * simple straight forward algorithm 
	 */
	public int evaluate(byte[] targetState, byte player)
	{
		Rules r = Rules.getInstance();
		if(r.allPiecesInHeavenOfPlayer(targetState, player))
		{
			try{
			player = (byte) r.getPartnerForPlayer(player);
			}
			catch(Exception e)
			{
				msg.debug(this, e.getMessage());
			}
		}
		
		int sum = 0; 
		byte switcher = 0;
		//decide to which team the player belongs to 
		if(player == 3 || player == 1)
		{
			switcher = 1;
		}
		else
		{
			switcher = 0; 
		}
		
		ArrayList<Integer> myPawns = new ArrayList<Integer>();
		ArrayList<Integer> alliedPawns = new ArrayList<Integer>();
		ArrayList<Integer> enemyPawns = new ArrayList<Integer>();
		
		//add the positions of each pawn into the datastructure
		for(int i =0; i<80; i++)
		{
			if(targetState[i] == 0)
			{
				//do nothing 
			}
			else if(targetState[i] == player || (i >= (64 + ((player-1)*4)) && i<= (68 + (player-1)*4)))
			{
				myPawns.add(i);
			}
			else if(targetState[i] % 2 == switcher || (i >= (64 + ((4 - player)*4)) && i<= (68 + (4-player)*4)))
			{
				alliedPawns.add(i);
			}
			else if(targetState[i] % 2 != switcher || i >= 64)
			{
				enemyPawns.add(i);
			}
		}
		//check the game for pawns of the player
		sum = sum + (myPawns.size() * myPawnsOnField);
		//check for pawns in the heaven
		for(int i = 0; i<myPawns.size(); i++)
		{
			if(myPawns.get(i) >= 64)
			{
				sum = sum + myPawnsInHeaven;
			}
		}
		msg.debug(this, "After own Pawns on field and in heaven: " + sum);
		//check for allied pawns on the field
		sum = sum + (alliedPawns.size() * myAllyOnField);
		//check for allied pawns in the heaven
		msg.debug(this, "After Allied Pawns on field: " + sum);
		for(int i = 0; i<alliedPawns.size(); i++)
		{
			if(alliedPawns.get(i) >= 64)
			{
				sum = sum + myAllyInHeaven;
			}
		}
		msg.debug(this, "After Allied Pawns in heaven: " + sum);
		//check how many enemy pawns are off the game
		sum = sum + ((8-enemyPawns.size()) * myEnemyOffField);
		msg.debug(this, "After checking if enemy pawns are off the field: " + sum);
		//check if the own startpoint is occupied
		if(targetState[(player-1)*16] == player)
		{
			sum = sum + myStartpointOccupied;
		}
		msg.debug(this, "After checking own startpoint: " + sum);
		//check for each own pawn if..
		for(int i = 0; i< myPawns.size(); i++)
		{
			int pos = myPawns.get(i);
			msg.debug(this, "the actual pos is: " + pos); 
			if(myPawns.get(i)<64)
			{
				for(int j = 0; j<14; j++)
				{
					//enemy pawns are in front of (near)
					if(targetState[(pos + j) %64]%2  != switcher && targetState[(pos + j) %64]
					                                                            != 0 && j < 8)
					{
						sum = sum + enemyInFrontUpTo7;
					}
					//enemy pawns are in front of (far)
					if(targetState[(pos + j) %64]%2  != switcher && targetState[(pos + j) %64]
					                                                            != 0 & j > 7)
					{
						sum = sum + enemyInFrontUpTo13;
					}
					//enemy pawns are behind (near)
					if(pos != (player -1)* 16)
					{
						if(targetState[(64 + pos - j) %64]%2  != switcher && targetState[(64 + pos - j) %64]
						                                                                 != 0 && j < 8)
						{
							sum = sum + enemyBehindUpTo7;
						}
						//enemy pawns are behind (far)
						if(targetState[(64 + pos - j) %64]%2 != switcher && targetState[(64 + pos - j) %64]
						                                                                != 0 && j > 7)
						{
							sum = sum + enemyBehindUpTo13;
						}
					}
					
				}
				//a enemy pawn is right 4 steps behind
				if((targetState[(64 +pos -4) % 64]  != 0))
				{
					if(targetState[(64 +pos -4) % 64]%2  != switcher)
					{
						sum = sum + enemyBehind4;
					}
				}
				msg.debug(this, "After wheigting pawn on pos " + pos + ": " + sum);

				int distance = pos - ((player -1) * 16);
				if(distance <0)
				{
					distance = 64 + distance; 
				}
				msg.debug(this,"the distance is wheighted for pawn at " + pos + " as: " + distance);
				sum = sum + (distance * distanceToHeaven); 
			}
			else
			{
				int temp = ((pos -64)%4) + 1;
				sum  = sum + (temp * distanceInHeaven);
				msg.debug(this,"the distance is wheighted for pawn at " + pos + " as: " + (temp * distanceInHeaven));
			}
		}
		//check for enemy pawns in the heaven 
		for(int i = 0; i< enemyPawns.size(); i++)
		{
			if(enemyPawns.get(i) >= 64)
			{
				sum = sum + enemyInHeaven;
			}
		}
		msg.debug(this, "The final score is: " + sum);
		msg.debug(this, "===========================");
		return sum; 
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int card[], float fading) {
		return evaluate(targetState, player);
	}

}
