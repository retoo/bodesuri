package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.*;
import intelliDOG.ai.framework.*;
import intelliDOG.ai.utils.DebugMsg;
import java.util.*;

import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.zugsystem.Bewegung;

public class AlphaBeta_V1 implements IBot {
	
	private DebugMsg msg = DebugMsg.getInstance();
	Evaluator evaluator = new SimpleEvaluatorV5();  
	private ZugErfasstEvent zee = null; 
	private Move highestMove = null;
	private byte currentPlayer; 
	private int maxdepth = 8; 
	private int cutMoves = 5; 
	private InformationGatherer ig;
	private BotBoard bb;
	
	/**
	 * The constructor for the AlphaBeta_V1 Bot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public AlphaBeta_V1(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the AlphaBeta_V1 Bot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public AlphaBeta_V1(){	
		this.ig = new InformationGatherer(Players.EMPTY);
		this.bb = new BotBoard(new byte[80], this.ig);
	}
	
	
	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenMap) {
		
		if(moeglich.size() < 1)
		{
			msg.debug(this, "AlphaBeta_V1: They don't even give me possibilities to choose from!");
			return null; 
		}
		
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, bb, ig);
		
		Move highestMove = makeMove();
		
		if(highestMove == null)
			msg.debug(this, " highest move is null."); 
		
		zee = parser.convertBack(highestMove.getCard(),highestMove.getPositions(), ig.getMyPlayer());
		
		StringBuffer sb = new StringBuffer();
		for(Bewegung bew : zee.toZugEingabe().getBewegungen())
		{
			sb.append("start: " + bew.start.getNummer() + " ziel: " +bew.ziel.getNummer() + "\n");
		}
		msg.debug(this, "Karte: " + zee.getKarte() + ", konkrete Karte: " + zee.getKonkreteKarte() + ", Züge: " + sb);
		return zee;
	}


	public boolean terminalState(byte[] b, byte player)
	{
		byte partner = Rules.getInstance().getPartnerForPlayer(player); 
		
		if(Rules.getInstance().allPiecesInHeavenOfPlayer(b, player) &&
				Rules.getInstance().allPiecesInHeavenOfPlayer(b, partner))
			return true; 
		else
			return false; 
	}
	
	
	
	int AlphaBeta(int alpha, int beta, int depth, boolean is_max_node)
	{
		int result = 0; 
		int score = 0; 
		int succScore = 0; 
		List<Move> possible = null; 
		
		if(depth == 0 || terminalState(bb.getBoard(), currentPlayer))  
		{
			result = evaluator.evaluate(bb.getBoard(), currentPlayer, ig.getMyCards(), 1); 
			msg.debug(this, "player who's evaluating: " + currentPlayer + " - score: " + result); 
			return result; 
		}
		
		// Get all possible moves for Joker in case it's not my player who is on turn
		if(ig.getMyPlayer() != currentPlayer) {
			possible = bb.getPossibleMovesForCard(Cards.JOKER, currentPlayer);
		
		} else {
			// Get all my possible moves - If the card set still contains a Joker, then reduce 
			// the number of possible moves by sorting the moves based on a static evaluation
			
			possible = bb.getAllPossibleMoves(currentPlayer); 
			if(ig.hasJokerOrSeven())
			{
				// sort
				possible = getSortedMoves(possible);
			}			
		}
						
		if(is_max_node)
		{
			score = -2000; 
			
			int cut  = possible.size();
			if(ig.getMyPlayer() != currentPlayer)
			{
				if(possible.size() > cutMoves) 
					cut = cutMoves;
			}else
			{
				// check if my player is still able to move 
				if(possible.isEmpty() && depth != maxdepth)
				{
					msg.debug(this, "no more move available from player: " + currentPlayer + ", depth = " + depth); 
					result = evaluator.evaluate(bb.getBoard(), currentPlayer, ig.getMyCards(), 1); 
					return result; 
				}
				if( (depth == maxdepth && !possible.isEmpty()) )
					if(Rules.getInstance().getPiecesInGameForPlayer(bb.getBoard(), ig.getMyPlayer()).length == 0)
					{
						highestMove = chooseSimpleMove(possible);
						return 5555; // again... reduce steps
					}
			}
			
			msg.debugLegalMoves(this, possible); 

			for(int i=0; i< cut; i++ )
			{
				msg.debug(this,"makeMove MAX: player "+ currentPlayer + " with this move: "); //+"  :" +   board.toString()); 
				msg.debugMove(this, possible.get(i)); 
				
				bb.makeMove(possible.get(i), currentPlayer);
				currentPlayer = bb.getNextPlayer(currentPlayer);
				
				succScore = AlphaBeta(alpha, beta, depth-1, !is_max_node); 
					
				if(succScore > score)
				{
					score = succScore;
					byte tmp = bb.getLastPlayer(currentPlayer);
					
					// only set highest move for my player and at outermost level
					if(ig.getMyPlayer() == tmp &&  depth == maxdepth ) {
						highestMove = possible.get(i);
						msg.debug(this, "PL: " + ig.getMyPlayer() + " - tmp:  " + tmp + " , card: " + highestMove.getCard() + " , " +highestMove.getPositions()[0] + " --> " + highestMove.getPositions()[1]);  
					}
				}
				if(score > alpha) alpha = score; 
				if(alpha >= beta)
				{
					msg.debug(this, "MAX CUTOFF: score: " + score); 
					currentPlayer = bb.getLastPlayer(currentPlayer);
					bb.undoMove(currentPlayer); 
					return score; 
				}
				currentPlayer = bb.getLastPlayer(currentPlayer);
				bb.undoMove(currentPlayer); 
			}
		}else {
				
			score = 2000; 
		
			int cut  = possible.size(); 
			if(ig.getMyPlayer() != currentPlayer)
			{
				if(possible.size() > cutMoves) 
					cut = cutMoves;
			} 
			
			for(int i=0; i< cut; i++ )
			{
				msg.debug(this,"makeMove MIN: player "+ currentPlayer + " with this move: ");
				msg.debugMove(this, possible.get(i)); 

				bb.makeMove(possible.get(i), currentPlayer);
				currentPlayer = bb.getNextPlayer(currentPlayer);
				
				succScore = AlphaBeta(alpha, beta, depth-1, !is_max_node); 
				
				if(succScore < score)
				{
					score = succScore;
				}
				if(score < beta) beta = score; 
				if(beta <= alpha) 
				{
				    msg.debug(this, "MIN CUTOFF: score: " + score); 
					currentPlayer = bb.getLastPlayer(currentPlayer);
					bb.undoMove(currentPlayer); 
					return score;
				}
				currentPlayer = bb.getLastPlayer(currentPlayer);
				bb.undoMove(currentPlayer); 
			}
		}	
		return score; 
	}

	@Override
	public Move makeMove() {
		boolean is_max_node = true;
		
		highestMove = null; 
		
		if(evaluator == null)
		{
			evaluator = new SimpleEvaluatorV5(); 
		}
		
		currentPlayer = ig.getMyPlayer(); 
		
		// bug 12
		// if my player has only one card left, then set maxdepth to 4 (one round)
		
		if(ig.getCardsForPlayer(currentPlayer)[1] == -1)
			maxdepth = 4; 
		
		// FIXME
		List<Move> test = bb.getAllPossibleMoves(ig.getMyPlayer()); 
		msg.debug(this, "==== test ==="); 
		msg.debugLegalMoves(this, test); 
		msg.debug(this, "============="); 
		
		if(test.isEmpty())
			return null; 
		
		int highest = AlphaBeta(-2000, 2000, maxdepth, is_max_node); 
		
		// There must be a bug
		if(highestMove == null && !test.isEmpty())
		{
			test = getSortedMoves(test); 
			System.out.println("highestMove == null && !test.isEmpty()"); 
			msg.debugLegalMoves(this, test); 
			highestMove = test.get(0); 
//			System.exit(1); 
		}

		// set maxdepth to original value in case it was reduced
		maxdepth = 8; 
		if(highestMove != null) {
			msg.debug(this, "Karte: " + highestMove.getCard() + ", Bewertung: " + highest + ", s: " + highestMove.getPositions()[0] + ", t: " + highestMove.getPositions()[1]);
		}
		return highestMove;
	}


	/**
	 * Sorts a given set of possible moves while evaluating each move.
	 * The moves are ordered in descending order, that the best move is on top of the list.
	 * Regard that the evaluation function evaluates the board for my player and my cards.
	 *  
	 * @param moves moves to be sort
	 * @return sorted moves
	 */
	public List<Move> getSortedMoves(List<Move> moves)
	{
		List<MoveEvalPair> c = new ArrayList<MoveEvalPair>(); 
		
		List<Move> sortedMoves = null; 
//		sortedMoves = bb.getAllPossibleMoves(currentPlayer);
		
		sortedMoves = moves; 
		
		for(int i = 0; i < sortedMoves.size(); i++)
		{
			bb.makeMove(sortedMoves.get(i), getPlayer());
			int result = evaluator.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1);
			// add result of evaluation and its move into a list
			c.add(new MoveEvalPair(result, sortedMoves.get(i))); 
			
			bb.undoMove(getPlayer());
		}
		Collections.sort(c, new ReverseComperator()); 
		
		msg.debug(this, "---- List contains " + c.size() + " elements ----"); 

		sortedMoves.clear(); 
		
		if(c.size() <= 5)
		{
			for(MoveEvalPair m: c)
			sortedMoves.add(m.move); 
		} else 
		{
			int newSize = ( (c.size()/2) % 2) == 0 ? c.size()/2 : c.size()/2+1;
			int upperLimit = (newSize <= 17 ? newSize : 17); 
			for(int i=0; i<upperLimit; i++)
			{
				sortedMoves.add(c.get(i).move); 
			}
		}
		msg.debug(this, "After Shrinking the List contains " + sortedMoves.size() + " elements ----"); 
		
		return sortedMoves; 
	}
	
	
	// delete later: 
	public void printMove(Move m)
	{
		System.out.print("card -> " + m.getCard() + ", ");
		System.out.print("positions -> ");
		for(int j=0; j<m.getPositions().length; j+= 2){
			System.out.print("s: " + m.getPositions()[j] + ", ");
			System.out.print("t: " + m.getPositions()[j + 1]);
			if(j != m.getPositions().length - 2){
				System.out.print(" / ");
			}
		}
		System.out.println();	
	}
	
	class MoveEvalPair {
		
		public int value; 
		public Move move; 
		public MoveEvalPair(int value, Move move) { this.value = value; this.move = move; }
	}
	
	@Override
	public BotBoard getBotBoard() {
		return this.bb;
	}

	@Override
	public InformationGatherer getInformationGatherer() {
		return this.ig;
	}

	@Override
	public byte getPlayer() {
		return this.ig.getMyPlayer();
	}
	
	@Override
	public void setEvaluator(Evaluator eval)
	{
		this.evaluator = eval; 
	}
	
	
	/**
	 * Compare two objects by in descending  orders
	 */
	class ReverseComperator implements Comparator<MoveEvalPair>
	{
		
		/**
		 * @param a first object to be compared
		 * @param b second object to be compared
		 * @return -1 if a>b, 0 if a=b, +1 if a<b
		 */
		public int compare( MoveEvalPair a, MoveEvalPair b)
		{
			if(a.value > b.value)
			  return -1; 
			else if(a.value < b.value)
			  return 1; 
			else 
			  return 0; 
		  }
	}
	
	/**
	 * This method simply evaluates each move and chooses the best one. 
	 * It is thought to be called in case the only possible move is to go 
	 * to the homefield. For that move it's not necessary to make the whole calculation
	 * including the other players. 
	 * @param moves
	 * @return returns the move
	 */
	public Move chooseSimpleMove(List<Move> moves)
	{
		Move bestMove = null; 
		int bestValue = 0; 
		
		if(moves != null)
		{
			bestMove = moves.get(0); 
			
			for(Move m : moves)
			{
				bb.makeMove(m, ig.getMyPlayer());
				int result = evaluator.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1.0f);
				if(result > bestValue)
				{
					bestValue = result; 
					bestMove = m;
				}
				bb.undoMove(ig.getMyPlayer());
			}
		}
		return bestMove; 
	}
}
