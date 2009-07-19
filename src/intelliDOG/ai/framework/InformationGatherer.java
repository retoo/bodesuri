package intelliDOG.ai.framework;

import intelliDOG.ai.utils.DebugMsg;

import java.util.List;


/**
 * This class will gather all needed information for one bot.
 *
 */
public class InformationGatherer {

	private DebugMsg msg = DebugMsg.getInstance(); 
	private int cardsLeft = 112; 
	private int[][] cards;
	private byte myPlayer;
	private int[] usedCards; 
	private double[] prob;  
	private boolean[] botsGaveUp = new boolean[] {false,false,false,false}; 
	
	
	public InformationGatherer(byte myPlayer){
		this.cards = new int[4][6];
		this.myPlayer = myPlayer;
		this.usedCards = new int[14];
		this.prob = new double[14]; 
	}
	
	
//	public int getCardsLeft() { return cardsLeft; }
	
	/**
	 * add the used card to the existing array. This array contains one entry for each
	 * card from 1,2,...,Q,K,A,J and increments the value by one until all eight cards are gone.
	 * @param u used card  
	 */
	public void setUsedCards(int u)
	{
		int index = (u%13); 
		if(u > 0)
		{
			if(u > 52)
				usedCards[13] += 1; 
			else if (index == 0)
				usedCards[12] += 1; 
			else
				usedCards[index-1] += 1;
		}
		if(cardsLeft != 0)
			cardsLeft -= 1; 
		
	}

	/**
	 * Decrement the value of this used card by one. That means, there is a additional card
	 * of this kind in the game.  
	 * @param u card to restore
	 */
	public void restoreUsedCards(int u)
	{
		int index = (u%13); 
		if(u > 0)
		{
			if(u > 52)
				usedCards[13] -= 1;
			else if(index == 0)
				usedCards[12] -= 1; 
			else
				usedCards[index-1] -= 1;
		}
		cardsLeft += 1;
		
	}
	
	/**
	 * This method is called from Game and updates the array of used cards for
	 * every bot. 
	 * @param l list containing all used cards in the game
	 */
	public void distributeUsedCards(List<Integer> l)
	{
		usedCards  = new int[14]; 
		prob = new double[14];
		
		for(int i=0; i<l.size(); i++)
			setUsedCards(l.get(i));
		
		calcProb(); 
		
	}
	
	public int getProbSize()
	{
		return prob.length; 
	}
	
	/**
	 * Calculate probability for every kind of card, how likely it is, that somebody
	 * would play this card.
	 * Every kind consists of 8 cards.    
	 */
	public void calcProb()
	{
		
		for(int i=0; i<prob.length; i++)
		{
			double d = new Double(8 - usedCards[i]) / 8;
			if(d < 0.0)
				prob[i] = 0.0; 
			else
				prob[i] = d; 
			msg.debug(this, " " + d); 
		}
	}

	public int[] getNhighestSuccessor(double d)
	{
		int count = 0; 
		int[] tmp = new int[13]; 
		
		for(int i=0; i<prob.length-1; i++)
		{
			if(prob[i] > d ) 
				tmp[count++] = i+1; 

		}
		return tmp; 
	}
	
	/** 
	 * Verify if all cards of one kind (f.e. all kings) are played.
	 * @param c card to check
	 * @return true if all cards are played, false otherwise
	 */
	public boolean allGone(int c)
	{
		boolean b = usedCards[c-1]>= 8;
		return b; 
	}
	
	/**
	 * Return probability of card, how likely it is, that the enemy plays it
	 * @param c the card to be verified
	 * @return probability of this card
	 */
	public double getProb(int c)
	{
		return prob[c-1]; 
	}
	
	
//	public int[] getUsedCards()
//	{
//		return usedCards;    
//	}


	/**
	 * Test if a certain player gave up. 
	 */
	public boolean getPlayerGaveUp(byte player)
	{
		return botsGaveUp[player-1]; 
	}
	
	/**
	 * Mark player that he gave up.
	 * @param player who gave up
	 */
	public void setBotGaveUp(byte player)
	{
		botsGaveUp[player-1] = true; 
	}
	
	/**
	 * Players who gave up has to be cleaned, when starting a new round. 
	 * 
	 */
	public void cleanBotsGaveUp()
	{
		botsGaveUp = new boolean[]{false, false, false, false}; 
	}
	
	/**
	 * Return array of cards of my player
	 * @return cards of my player
	 */
	public int[] getMyCards(){
		return getCardsForPlayer(myPlayer);
	}
	
	/**
	 * Return array of cards for any player
	 * @param playerOnTurn 
	 * @return cards for playerOnTurn
	 */
	public int[] getCardsForPlayer(byte playerOnTurn) {
		return this.cards[playerOnTurn - 1];
	}
	
	/**
	 * Set cards for a player
	 * @param cards 
	 * @param playerOnTurn
	 */
	public void setCardsForPlayer(int[] cards, byte playerOnTurn) {
		this.cards[playerOnTurn - 1] = cards;
	}

	/**
	 * @return return my player
	 */
	public byte getMyPlayer() {
		return myPlayer;
	}

	/**
	 * @param myPlayer the myPlayer to set
	 */
	public void setMyPlayer(byte myPlayer) {
		this.myPlayer = myPlayer;
	}
	

	/**
	 * verify if the player has still a joker in his set of cards
	 * @return true if the player has a joker, false otherwise
	 */
	public boolean hasJokerOrSeven()
	{
		int[] tmp = new int[6]; 
		tmp = getMyCards(); 
		
		for(int i=0; i<tmp.length; i++)
		{
			if(tmp[i] == 100 || (tmp[i]%13==7))
				return true; 
		}
		return false; 
	}
	
	public int[] getUsedCards()
	{
		return usedCards; 
	}
}
