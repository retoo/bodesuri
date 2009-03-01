package intelliDOG.ai.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import intelliDOG.ai.bots.IBot;
import intelliDOG.ai.ui.GameWindow;
import intelliDOG.ai.utils.Statistics;

/**
 * The Game class is the game controller for games running over the intelliDOG
 * framework only
 */
public class Game {

	private Board board;
	private byte playerOnTurn;
	private IBot[] bots;
	private int [][] cards;
	private Stack<Integer> cardStock;
	private List<Integer> usedCards;
	
	private Stack<Move> moveStack = new Stack<Move>();
	private GameWindow gw;
	
	public Game(IBot[] bots, GameWindow gw, long timeout){
		this.board = new Board();
		this.playerOnTurn = Players.P1;
		this.bots = bots;
		this.cards = new int[4][6];
		this.gw = gw;
		if(this.gw != null){
			this.gw.setTimeout(timeout);
		}
		this.usedCards = new ArrayList<Integer>();
	}
	
	public Game(IBot[] bots){
		this(bots, null, 0);
	}
		
	public void run(){
		long startTime = System.nanoTime();
		
		//initially shuffle cards
		this.cardStock = shuffleCards();
		this.usedCards.clear();
		
		int cardsToDistribute = 6;
		int cardsCountBack = 0;
		//while not game is won
		while(hasWon() == 0){
			//give cards to players
			if(cardsCountBack == 0){
				cardsCountBack = cardsToDistribute;
				distributeCards(cardsToDistribute--);
				if(cardsToDistribute == 1){ cardsToDistribute = 6; }
			}
			updateBotGaveUp(null); 
			
			boolean allGaveUp = true;
			for(int i = 0; i < 4; i++){
				//if game is won break;
				if(hasWon() != 0){ break; }
				updateBotInformation(bots[i].getBotBoard(), bots[i].getInformationGatherer());
				Move move = bots[i].makeMove();
				if(move == null){
					updateBotGaveUp(bots[i]); 
					//bot gives up or gave already up.
					takeCardsAway(); //take the other cards away
					this.playerOnTurn = getNextPlayer(this.playerOnTurn);
					this.board.setPlayerOnTurn(this.playerOnTurn);
					continue; //go on with next player
				}else{
					this.board.makeMove(move);
					removeCard(move.getCard());
					moveStack.push(move);
					this.playerOnTurn = getNextPlayer(this.playerOnTurn);
					this.board.setPlayerOnTurn(this.playerOnTurn);
					allGaveUp = false;
				}
				if(gw != null){
					gw.update(board.getBoard(), cards, this.playerOnTurn);
				}
			}
			
			if(allGaveUp){
				cardsCountBack = 0;
			}else{
				cardsCountBack -= 1;
			}
		}
		
		//clean up..
		//System.out.println("Team " + hasWon() + " has won!");
		//System.out.println("Game time = " + ((System.nanoTime() - startTime) / 1000000 ) + " msecs");
		int pc1 = Rules.getInstance().nrOfPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P1);
		pc1 += Rules.getInstance().nrOfPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P3);
		int pc2 = Rules.getInstance().nrOfPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P2);
		pc2 += Rules.getInstance().nrOfPiecesInHeavenOfPlayer(this.board.getBoard(), Players.P4);
		double t = (System.nanoTime() - startTime) / 1000000.0 / 1000.0;
		
		Statistics.setGameStats(hasWon(), pc1, pc2, t, moveStack.size());
		
	}
	
	
	/**
	 * This method will update the information for the bot on turn. (Before he will do the next move)
	 * @param bb The <class>BotBoard</class> of the bot on turn.
	 * @param ig The <class>InformationGatherer</class> of the bot on turn.
	 */
	private void updateBotInformation(BotBoard bb, InformationGatherer ig) {
		bb.setBoard(getBoardArray());
		ig.setCardsForPlayer(cards[ig.getMyPlayer() - 1], playerOnTurn);
		ig.distributeUsedCards(usedCards); 
	}

	/**
	 * Method that will update all bot's <class>InformationGatherers</class>
	 * when a bot had to give up
	 * @param bot the bot that gave up
	 */
	private void updateBotGaveUp(IBot bot)
	{
		if(bot == null)
		{
			for(int i=0; i<bots.length; i++)
			{
				bots[i].getInformationGatherer().cleanBotsGaveUp(); 
			}
		}
		else
		{
			for(int i=0; i<bots.length; i++)
			{
				bots[i].getInformationGatherer().setBotGaveUp(bot.getPlayer()); 
			}
		}
	}
	
	
	/**
	 * Determines if a team has won and when yes which one it is.
	 * @return 0, if no one has won / 1, if team one (P1 & P3) has won / 2, if team two (P2 & P4) has won
	 */
	private int hasWon(){
		if(Rules.getInstance().allPiecesInHeavenOfPlayer(getBoardArray(), Players.P1)
				&& Rules.getInstance().allPiecesInHeavenOfPlayer(getBoardArray(), Players.P3)){
			return 1;
		}
		if(Rules.getInstance().allPiecesInHeavenOfPlayer(getBoardArray(), Players.P2)
				&& Rules.getInstance().allPiecesInHeavenOfPlayer(getBoardArray(), Players.P4)){
			return 2;
		}
		return 0;
	}
	
	/**
	 * returns the player that is on turn after the given player
	 * @param currentPlayer the player actually on turn
	 * @return the next player on turn
	 */
	private byte getNextPlayer(byte currentPlayer){
		byte nextPlayer = (byte)((currentPlayer + 1) % 5);
		return nextPlayer == 0 ? 1 : nextPlayer;
	}
	
	/**
	 * This method takes a player's card's away if he has to give up.
	 */
	private void takeCardsAway(){
		for(int i = 0; i < 6; i++){
			if(cards[playerOnTurn - 1][i] == -1){
				break;
			}else{
				usedCards.add(cards[playerOnTurn - 1][i]);
				cards[playerOnTurn - 1][i] = -1;
			}
		}
	}
	
	/**
	 * gets a copy of the current board's byte array
	 * @return a copy of the Boards board
	 */
	private byte[] getBoardArray(){
		byte[] boardArray = this.board.getBoard();
		byte[] boardArrayCopy = new byte[80];
		for(int i = 0; i < 80; i++){
			boardArrayCopy[i] = boardArray[i];
		}
		return boardArrayCopy;
	}
	
	/**
	 * Method that will distribute the cards to players
	 * @param nrOfCards how many cards shall be distributed per player
	 */
	private void distributeCards(int nrOfCards){
		for(int i = 0; i < nrOfCards; i++){
			for(int j = 0; j < 4; j++){
				if(this.cardStock.size() == 0){
					this.cardStock = shuffleCards();
					this.usedCards.clear();
				}
				this.cards[j][i] = this.cardStock.pop();
			}
		}
		for(int i = nrOfCards; i < 6; i++){
			for(int j = 0; j < 4; j++){
				this.cards[j][i] = -1;
			}
		}
	}
	
	/**
	 * generate card stack with each card 2 times and joker 8 times
	 * @return a stack with all cards shuffled
	 */
	private Stack<Integer> shuffleCards(){
		ArrayList<Integer> sortedCards = new ArrayList<Integer>(112);
		//add 8 jokers
		for(int i = 0; i < 8; i++){
			sortedCards.add(100);
		}
		//add each card twice
		for(int i = 1; i <= 52; i++){
			sortedCards.add(i);
			sortedCards.add(i);
		}
		Stack<Integer> cards = new Stack<Integer>();
		//fill stack with cards from the two sorted decks
		for(int i = 0; i < 112; i++){
			//randomly choose a card from the sorted ones and push it on our unsorted stack
			int pos = (int)(Math.random() * (sortedCards.size() - 1) + 0.5);
			cards.push(sortedCards.get(pos));
			sortedCards.remove(pos); //remove the used card from the sorted ones
		}
		return cards;
	}
	
	/**
	 * remove the card used for the last move from the players cards.
	 * @param usedCard the card that was used for the move
	 */
	private void removeCard(int usedCard){
		//remove used card
		int usedCardPos = 0;
		usedCards.add(usedCard);
		int[] myCardsOld = new int[cards[playerOnTurn - 1].length];
		for(int i = 0; i < cards[playerOnTurn - 1].length; i++){
			if(cards[this.playerOnTurn - 1][i] == (usedCard >= 53 ? Cards.JOKER : usedCard)){ usedCardPos = i; }
			myCardsOld[i] = cards[playerOnTurn - 1][i];
		}
		for(int i = usedCardPos; i < cards[playerOnTurn - 1].length - 1; i++){
			cards[playerOnTurn - 1][i] = myCardsOld[i + 1];
		}
		cards[playerOnTurn - 1][cards[playerOnTurn - 1].length - 1] = -1;
	}

	/**
	 * @return the usedCards
	 */
	public List<Integer> getUsedCards() {
		return usedCards;
	}
	
}
