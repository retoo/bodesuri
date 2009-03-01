package intelliDOG.ai.evaluators;

import java.util.ArrayList;

import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

public class EnvironmentEvaluator implements Evaluator {


	
	private int enemyInFrontUpTo7 = 10;
	private int enemyInFrontUpTo13 = 5;
	private float distanceToHeaven = 1.2f;
	private int allyInFrontNear = 5;
	private int allyInFrontFar = 3; 
	private int allyBehindNear = 5;
	private int allyBehindFar = 3; 
	
	private int enemyBehind4 = 10;

	//negative influences
	private int enemyBehindUpTo7 = 10;
	private int enemyBehindUpTo13 = 5;
	
	private DebugMsg msg = DebugMsg.getInstance(); 
	private ArrayList<Integer> myPawns = null;
	private ArrayList<Integer> alliedPawns = null;
	private ArrayList<Integer> enemyPawns = null;  
	private byte[] targetState = new byte[80]; 
	private byte enemyPlayerOne = 0; 
	private byte enemyPlayerTwo = 0; 
	private byte player, alliedplayer; 
	private int myHome, alliedHome; 
	private float fading; 
	private int enemyHomeOne, enemyHomeTwo; 
	
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
			float fading) {
		
		int sum = 0; 
		this.targetState = targetState; 
		this.player = player; 
		this.fading = fading; 

		Rules r = Rules.getInstance();
	
		
		if(r.allPiecesInHeavenOfPlayer(targetState, player))
		{
			player = (byte) r.getPartnerForPlayer(player);
		}
		alliedplayer = r.getPartnerForPlayer(player);
		if(alliedplayer == 0)
			alliedplayer = 4; 
		
		myHome = r.getHomePositionForPlayer(player);
		alliedHome = r.getHomePositionForPlayer(alliedplayer);
		enemyPlayerOne = (byte) (player +1);
		
		if(enemyPlayerOne == 5)
		{
			enemyPlayerOne = 1; 
		}
		enemyPlayerTwo =   r.getPartnerForPlayer(enemyPlayerOne);
		enemyHomeOne = r.getHomePositionForPlayer(enemyPlayerOne);
		enemyHomeTwo = r.getHomePositionForPlayer(enemyPlayerTwo);
		

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
				else if(i == alliedHome || (i >= (64 + ((alliedplayer -1)*4)) 
						&& i<= (68 + (alliedplayer -1)*4)))
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

		// check enemies in front of my player
		sum += checkEnemyInFrontBackOfMyPlayers(); 
		
		// check enemies in front of my partner's player
		sum += checkEnemyInFrontBackOfPartner(); 
		msg.debug(this, "sum: " + sum); 
		return sum; 
	}
	
	public int checkEnemyInFrontBackOfMyPlayers()
	{
		
		int sum = 0; 
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
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo) &&
								j < 8 && j != 4)
						{
							sum += (int) (enemyInFrontUpTo7 * fading);
						}
						//enemy pawns are in front of (far)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo) &&
								j > 7)
						{
							sum +=  (int) (enemyInFrontUpTo13 * fading);
						}
						
						//allied pawns are in front of (far)
						if(playerInFront ==  alliedplayer && j < 8 && j != 4)
						{
							sum -= (int) (allyInFrontNear * fading);
						}
					
						//allied pawns are in front of (far)
						if(playerInFront ==  alliedplayer && j > 7 )
						{
							sum -=  (int) (allyInFrontFar * fading);
						}
						
						if(pos != myHome)
						{
							//enemy pawns are behind (near)
							int field = (64 + pos - j) %64;
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo ||
									(playerBehind == 5 &&
									(field == enemyHomeOne || field == enemyHomeTwo))) && j < 8)
							{
								sum -= (int) (enemyBehindUpTo7 * fading);
							}
							//enemy pawns are behind (far)
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo ||
									(playerBehind == 5 && 
									(field == enemyHomeOne || field == enemyHomeTwo))) && j > 7)
							{
								sum -= (int) (enemyBehindUpTo13 * fading);
							}
							//allied pawns are behind of (near)
							if(playerBehind ==  alliedplayer && j < 8 && j != 4)
							{
								sum -= (int) (allyBehindNear * fading);
							}
						
							//allied pawns are in front of (far)
							if(playerBehind ==  alliedplayer && j > 7 )
							{
								sum -= (int) (allyBehindFar * fading);
							}
						}
					}
				}
				//a enemy pawn is right 4 steps behind
				if(targetState[(64 +pos -4) % 64] == enemyPlayerOne ||
						targetState[(64 +pos -4) % 64] == enemyPlayerTwo)
				{
					sum +=  enemyBehind4;
				}
				//a enemy pawn is right 4 steps behind
				if(targetState[(64 +pos -4) % 64] == alliedplayer )
				{
					sum -=  enemyBehind4;
				}
				msg.debug(this, "After wheigting pawn on pos " + pos + ": " + sum);
	
//				int distance = pos - ((player -1) * 16);
//				if(distance <=0)
//				{
//					distance = 64 + distance; 
//				}
//				if(pos % 16 != 0 || (pos == myHome && targetState[pos] != 5))
//				{
//					msg.debug(this,"the distance is wheighted for pawn at " + pos +
//							" as: " + (Math.pow(distance, distanceToHeaven)));
//					sum += (int) (Math.pow(distance, distanceToHeaven)); 
//				}
			}
			else
			{
				// don't consider heaven right now
//				int temp = ((pos -64)%4) + 1;
//				sum  += ((temp * distanceInHeaven) + staticHeaven);
//				msg.debug(this,"the distance is wheighted for pawn at " + pos +
//						" as: " + (temp * distanceInHeaven +staticHeaven));
			}
		}
		
		return sum;
	}

	
	
	public int checkEnemyInFrontBackOfPartner()
	{
		int sum = 0; 
		//check for each own pawn if..
		for(int i = 0; i< alliedPawns.size(); i++)
		{
			int pos = alliedPawns.get(i);
			msg.debug(this, "the actual pos of my partner is: " + pos); 
			if(alliedPawns.get(i)<64)
			{
				// check only for players in the front
				for(int j = 1; j<14; j++)
				{
					int playerInFront = targetState[(pos + j) %64] ;
					int playerBehind = targetState[(64 + pos - j) %64];
					if(playerInFront != 0 || playerBehind != 0)
					{
						//enemy pawns are in front of (near)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo) &&
								j < 8 && j != 4)
						{
							sum += (int) (enemyInFrontUpTo7 * fading);
						}
						//enemy pawns are in front of (far)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo) &&
								j > 7)
						{
							sum += (int) (enemyInFrontUpTo13 * fading);
						}
						
						//my pawns pawns are in front of (far)
						if(playerInFront ==  player && j < 8 && j != 4)
						{
							sum -= (int) (allyInFrontNear * fading);
						}
					
						//allied pawns are in front of (far)
						if(playerInFront ==  player && j > 7 )
						{
							sum -= (int) (allyInFrontFar * fading);
						}
						
						
						if(pos != alliedHome)
						{
							//enemy pawns are behind (near)
							int field = (64 + pos - j) %64;
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo ||
									(playerBehind == 5 &&
									(field == enemyHomeOne || field == enemyHomeTwo))) && j < 8)
							{
								sum -= (int) (enemyBehindUpTo7 * fading);
							}
							//enemy pawns are behind (far)
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo ||
									(playerBehind == 5 && 
									(field == enemyHomeOne || field == enemyHomeTwo))) && j > 7)
							{
								sum -=  (int) (enemyBehindUpTo13 * fading);
							}
							//allied pawns are behind of (near)
							if(playerBehind ==  player && j < 8 && j != 4)
							{
								sum -= (int) (allyBehindNear * fading);
							}
						
							//allied pawns are in front of (far)
							if(playerBehind ==  player && j > 7 )
							{
								sum -= (int) (allyBehindFar * fading);
							}
						}
					}
				}
				//a enemy pawn is right 4 steps behind
				if(targetState[(64 +pos -4) % 64] == enemyPlayerOne ||
						targetState[(64 +pos -4) % 64] == enemyPlayerTwo)
				{
					sum +=  enemyBehind4;
				}
				//a enemy pawn is right 4 steps behind
				if(targetState[(64 +pos -4) % 64] == player)
				{
					sum -=  enemyBehind4;
				}
				msg.debug(this, "After wheigting pawn on pos for partner " + pos + ": " + sum);
	
//				int distance = pos - ((alliedplayer -1) * 16);
//				if(distance <=0)
//				{
//					distance = 64 + distance; 
//				}
//				if(pos % 16 != 0 || (pos == alliedHome && targetState[pos] != 5))
//				{
//					msg.debug(this,"the distance is wheighted for partner pawn at " + pos +
//							" as: " + (Math.pow(distance, distanceToHeaven)));
//					sum +=  (int) (Math.pow(distance, distanceToHeaven)); 
//				}
			}
			else
			{
				// don't consider heaven right now
//				int temp = ((pos -64)%4) + 1;
//				sum  += ((temp * distanceInHeaven) + staticHeaven);
//				msg.debug(this,"the distance is wheighted for partner pawn at " + pos +
//						" as: " + (temp * distanceInHeaven +staticHeaven));
			}
		}
		
		return sum;
		
	}
	
	
	
}
