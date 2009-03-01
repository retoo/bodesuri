package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

import java.util.ArrayList;
/**
 * The V3 includes an enhanced token calculation function, measures
 * now also the cards left on the hand and gives additional points
 * when all tokens are placed in the heaven. 
 */
public class SimpleEvaluatorV3 implements Evaluator {

	//positive influences
	private int myPawnsOnField = 10;
	private int myPawnsInHeaven = 150;
	private int myAllyOnField = 30;
	private int myAllyInHeaven = 40;
	private int myEnemyOffField = 60;
	private int myStartpointOccupied = 30;
	private int enemyInFrontUpTo7 = 20;
	private int enemyInFrontUpTo13 = 10;
	private int enemyBehind4 = 10;
	private int distanceToHeaven = 2; 
	private int distanceInHeaven = 15;
	private float distanceForAlly = 0.8f; 
	private int allInHeaven = 200;
	private int staticHeaven = 100; 
	//negative influences
	private int enemyBehindUpTo7 = -20;
	private int enemyBehindUpTo13 = -10;
	private int enemyInHeaven = -40;
	//card weights
	private int jokerWeight = 4;
	private int aceWeight = 3;
	private int fourSevenJackKingWeight = 2;
	private int otherCardWeight = 0; 
	//support variables
	private DebugMsg msg = DebugMsg.getInstance(); 
	private ArrayList<Integer> myPawns = null;
	private ArrayList<Integer> alliedPawns = null;
	private ArrayList<Integer> enemyPawns = null;  
	
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
	public int evaluate(byte[] targetState, byte player, int[] cards, float fading) {
		Rules r = Rules.getInstance();
		int sum = 0; 
		if(r.allPiecesInHeavenOfPlayer(targetState, player))
		{
			sum = sum + allInHeaven;
			player = (byte) r.getPartnerForPlayer(player);
		}
		byte alliedplayer = r.getPartnerForPlayer(player);
		if(alliedplayer == 0)
			alliedplayer = 4; 
		
		int myHome = r.getHomePositionForPlayer(player);
		int alliedHome = r.getHomePositionForPlayer(alliedplayer);
		byte enemyPlayerOne = (byte) (player +1);
		if(enemyPlayerOne == 5)
		{
			enemyPlayerOne = 1; 
		}
		byte enemyPlayerTwo =   r.getPartnerForPlayer(enemyPlayerOne);
		int enemyHomeOne = r.getHomePositionForPlayer(enemyPlayerOne);
		int enemyHomeTwo = r.getHomePositionForPlayer(enemyPlayerTwo);
		

		myPawns = new ArrayList<Integer>();
		alliedPawns = new ArrayList<Integer>();
		enemyPawns = new ArrayList<Integer>();
		
		//add the positions of each pawn into the datastructure
		for(int i =0; i<80; i++)
		{
			if(targetState[i] == 0)
			{
				//do nothing 
			}
			else if(targetState[i] == 5)
			{
				if(i == myHome || (i >= (64 + ((player-1)*4)) && i<= (68 + (player-1)*4)))
				{
					myPawns.add(i);
				}
				else if(i == alliedHome || (i >= (64 + ((alliedplayer -1)*4)) && i<= (68 + (alliedplayer -1)*4)))
				{
					alliedPawns.add(i);
				}
				else
				{
					enemyPawns.add(i);
				}
			}
			else if(targetState[i] == player )
			{
				myPawns.add(i);
			}
			else if((targetState[i] == alliedplayer))
			{
				alliedPawns.add(i);
			}
			else //if(targetState[i] % 2 != switcher || i >= 64)
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
		if(targetState[myHome] == 5)
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
				for(int j = 1; j<14; j++)
				{
					int playerInFront = targetState[(pos + j) %64] ;
					int playerBehind = targetState[(64 + pos - j) %64];
					if(playerInFront != 0 || playerBehind != 0)
					{
						//enemy pawns are in front of (near)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo) && j < 8 && j != 4)
						{
							sum = sum + enemyInFrontUpTo7;
						}
						//enemy pawns are in front of (far)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo) && j > 7)
						{
							sum = sum + enemyInFrontUpTo13;
						}
						if(pos != myHome)
						{
							//enemy pawns are behind (near)
							int field = (64 + pos - j) %64;
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo || (playerBehind == 5 &&
									(field == enemyHomeOne || field == enemyHomeTwo))) && j < 8)
							{
								sum = sum + enemyBehindUpTo7;
							}
							//enemy pawns are behind (far)
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo || (playerBehind == 5 && 
									(field == enemyHomeOne || field == enemyHomeTwo))) && j > 7)
							{
								sum = sum + enemyBehindUpTo13;
							}
						}
					}
				}
				//a enemy pawn is right 4 steps behind
				if(targetState[(64 +pos -4) % 64] == enemyPlayerOne || targetState[(64 +pos -4) % 64] == enemyPlayerTwo)
				{
					sum = sum + enemyBehind4;
				}
				msg.debug(this, "After wheigting pawn on pos " + pos + ": " + sum);
	
				int distance = pos - ((player -1) * 16);
				if(distance <0)
				{
					distance = 64 + distance; 
				}
				if(pos % 16 != 0 || (pos == myHome && targetState[pos] != 5))
				{
					msg.debug(this,"the distance is wheighted for pawn at " + pos + " as: " + distance * distanceToHeaven);
					sum = sum + (distance * distanceToHeaven); 
				}
			}
			else
			{
				int temp = ((pos -64)%4) + 1;
				sum  = sum + ((temp * distanceInHeaven) + staticHeaven);
				msg.debug(this,"the distance is wheighted for pawn at " + pos + " as: " + (temp * distanceInHeaven +staticHeaven));
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
		msg.debug(this, "after enemy in heaven: " + sum);
		
		for(int i = 0; i< 6; i++)
		{
			int card = cards[i];
			if(card == Cards.JOKER)
				sum = sum + jokerWeight;
			else
			{
				card = cards[i] % 13;
				if(card == Cards.HEARTS_ACE)
				{
					sum = sum + aceWeight;
				}
				else if(card == Cards.HEARTS_FOUR ||
						card == Cards.HEARTS_SEVEN || 
						card == Cards.HEARTS_JACK ||
						card == 0)
				{
					sum = sum + fourSevenJackKingWeight;
				}
				else 
				{
					sum = sum + otherCardWeight;
				}
			}
		}
		msg.debug(this, "after card weigthing: " + sum);
		
		for(int i = 0; i< alliedPawns.size(); i++)
		{
			int pos = alliedPawns.get(i);
			if( pos > 63)
			{
				break; 
			}
			else
			{
				
				int distance = (pos - alliedHome)%64;
				if(distance <0)
				{
					distance = 64 + distance; 
				}
				sum = sum + (int) (distance * distanceForAlly);
			}
		}
		msg.debug(this, "score after weighting allys advance: "+ sum);
		
		msg.debug(this, "The final score is: " + sum);
		msg.debug(this, "===========================");
		return sum; 
	}
	
	/**
	 * for debugging purposes
	 */
	public void  printPawns()
	{
		System.out.print("mypawns: ");
		for(int i = 0; i< myPawns.size(); i++)
		{
			
			System.out.print(myPawns.get(i) + " ");
		}
		System.out.print("alliedPawns: ");
		for(int i = 0; i< alliedPawns.size(); i++)
		{
			
			System.out.print(alliedPawns.get(i) + " ");
		}
		System.out.print("enemyPawns: ");
		for(int i = 0; i< enemyPawns.size(); i++)
		{
			
			System.out.print(enemyPawns.get(i) + " ");
		}
		System.out.println();
	}

}
