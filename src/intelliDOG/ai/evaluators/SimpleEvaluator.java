package intelliDOG.ai.evaluators;

import intelliDOG.ai.utils.DebugMsg;

import java.util.ArrayList;
/**
 * The first instance of the TwoStepFamily
 */
public class SimpleEvaluator implements Evaluator {

	//positive influences
	private int myPawnsOnField = 1;
	private int myPawnsInHeaven = 5;
	private int myAllyOnField = 1;
	private int myAllyInHeaven = 2;
	private int myEnemyOffField = 3;
	private int myStartpointOccupied = 2;
	private int enemyInFrontUpTo7 = 3;
	private int enemyInFrontUpTo13 = 2;
	private int enemyBehind4 = 1;
	//negative influences 
	private int enemyBehindUpTo7 = -2;
	private int enemyBehindUpTo13 = -1;
	private int enemyInHeaven = -4;
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
			else if(targetState[i] == player)
			{
				myPawns.add(i);
			}
			else if(targetState[i] % 2 == switcher)
			{
				alliedPawns.add(i);
			}
			else if(targetState[i] % 2 != switcher)
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
		for(int i = 0; i<alliedPawns.size(); i++)
		{
			if(alliedPawns.get(i) >= 64)
			{
				sum = sum + myAllyInHeaven;
			}
		}
		msg.debug(this, "After Allied Pawns on field and in heaven: " + sum);
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
				//a enemy pawn is right 4 steps behind
				if((targetState[(64 +pos -4) % 64]  != 0))
				{
					if(targetState[(64 +pos -4) % 64]%2  != switcher)
					{
						sum = sum + enemyBehind4;
					}
				}
				msg.debug(this, "After wheigting pawn on pos " + pos + ": " + sum);

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
		return sum; 
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int card[], float fading) {
		return this.evaluate(targetState, player);
	}

}
