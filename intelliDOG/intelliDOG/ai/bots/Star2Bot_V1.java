package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.SimpleEvaluatorV5;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.framework.Parser;
import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.zugsystem.Bewegung;


/**
 * Another variation of of Star2 algorithm.
 * It alternates between chance node and calls Alpha-Beta for a normal node
 * 
 */
public class Star2Bot_V1 implements IBot {

	private static int MAXDEPTH = 8; 
	private static int cutoffs_chance_alpha = 0;
	private static int cutoffs_chance_beta = 0; 
	private static int cutoffs_alpha = 0; 
	private static int cutoffs_beta = 0; 
	private static int rounds = 0; 
	private static int usedTwoStep = 0; 
	private static int singleEvaluation = 0; 
	private static int chance = 0; 
	private int limitOppMoves = 2; 
	private boolean singleEvalUsed = false; 
	
	private DebugMsg msg = DebugMsg.getInstance(); 
	private Move highestMove = null; 
	private ZugErfasstEvent zee = null; 
	private Evaluator evaluator = new SimpleEvaluatorV5();
	private byte currentPlayer; 
	private boolean chanceNode = false; 
	private int N = 13; 
	private int U = 2000; 
	private int L = -2000; 
	private InformationGatherer ig;
	private BotBoard bb;
	private float highest;
	private boolean small;

	/**
	 * The constructor for the Star2Bot_V1
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public Star2Bot_V1(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 *  
	 *  This method is called in case the current node is a chance node
	 */	
	private float chanceNode(BotBoard board, float alpha, float beta, int depth, boolean is_max_node )
	{
		int result = 0; 
		float vsum = 0; 
		float A, B, AX, BX = 0;
		double v = 0; 
		int realNbrSucc = 0; 
		
		HashMap<Integer, List<Move> > realMoves = new HashMap<Integer,List<Move>>(); 
		List<Move> possible = null; 
		
		if(depth == 0 || board.terminalState(getPlayer()) )
		{
			result = evaluator.evaluate(bb.getBoard(), getPlayer(), ig.getCardsForPlayer(getPlayer()), 1.0f);
			return result; 
		}
	
		ig.calcProb(); 
		
		// look how many successor this chance node has
		for(int i=1; i<=N; i++)
		{
			// reduce while only considering higher probabilities: 
			if(ig.getProb(i) > 0.5)
			{
				possible = board.getPossibleMovesForCard(i, currentPlayer);
				// if list is not empty, add it to realMoves
				if(!possible.isEmpty())
					realMoves.put(i, possible);
			}
		}
		realNbrSucc = realMoves.size(); 
		
		A = realNbrSucc * (alpha-U) + U; 
		B = realNbrSucc * (beta -L) + L; 
		
		// First calculate current probability of cards
		double prob = 0.0;
		int probSize = ig.getProbSize(); 
		
		// only cards from 1 to King. Ignore Joker
		for(int i=1; i< probSize; i++)
		{
			if(realMoves.containsKey(i))
				prob += ig.getProb(i); 
		}
		prob = prob / realNbrSucc; 
		
		Iterator<Integer> iter = realMoves.keySet().iterator(); 

		while(iter.hasNext())
		{
			int i = iter.next(); 
			List<Move> moves = realMoves.get(i); 
		
			chance++; 
			AX = Math.max(A, L); 
			BX = Math.min(B, U);
				
			chanceNode = false; 

			ig.calcProb(); 
			double exp = ig.getProb(i);
			double probability = new Double( (exp * (1.0/realNbrSucc))) / prob;
			ig.setUsedCards(i); 

			v = search(board, AX, BX, depth, is_max_node, moves);
 
			ig.restoreUsedCards(i); 

			if(v <= A) {
				cutoffs_chance_alpha++;
				return  alpha;
			} 

			if(v >= B) { 
				cutoffs_chance_beta++;
				return beta; 
			} 
			vsum += probability * v; 
			A+= U -v; 
			B+= L -v; 
		}
		return vsum; 
	}
	
	/**
	 * This method decides what kind of node is called. It either calls Alpha-Beta for Min, Max Node
	 * or Star1 for a chance Node.
	 * @param b the board
	 * @param AX alpha value
	 * @param BX beta value
	 * @param depth current depth
	 */
	private float search(BotBoard b, float AX, float BX, int depth, boolean is_max_node, List<Move> possible) 
	{
		// call chance node only for other three players but not for my player
		if(chanceNode && (currentPlayer != ig.getMyPlayer()) )
			return chanceNode(b, AX, BX, depth-1, is_max_node); 
		else
			return alphaBeta(b, AX, BX, depth, !is_max_node,  possible); 
	}
	

	/**
	 * Calculates and evaluates all moves for either max- or min player. Then it performs that
	 * move and calls search() for the next chance node. 
	 * 
	 * @param board current board
	 * @param alpha upper bound
	 * @param beta lower bound
	 * @param depth current depth
	 * @param is_max_node boolean that alternates between min and max node
	 * @param possible possible moves
	 * @return current score reached so far
	 */
	private float alphaBeta(BotBoard board, float alpha, float beta, int depth, boolean is_max_node,  List<Move> possible)
	{
		float result = 0; 
		float score = 0; 
		float succScore = 0; 
		
		if(depth == 0 || board.terminalState(currentPlayer) )
		{
			result = evaluator.evaluate(bb.getBoard(), getPlayer(), ig.getCardsForPlayer(getPlayer()), 1.0f);
			return result; 
		}
		
		if(currentPlayer == getPlayer())
		{
			possible = board.getAllPossibleMoves(currentPlayer);
			
			if(depth != MAXDEPTH)
				possible = getSortedMoves(possible); 	
		
			// check if I'm not able to move
			if( depth == MAXDEPTH && possible.isEmpty())
				return 0; 
			
			// use simpe move if the set contains a joker or seven, or there is only one possible move
			if( (depth == MAXDEPTH ) &&  (ig.hasJokerOrSeven() || possible.size()==1))
			{
				highestMove = chooseSimpleMove(possible);
				usedTwoStep++; 
				return 0; 
			}
			
			// check if my player is still able to move 
			if(possible.isEmpty() && depth != MAXDEPTH)
			{
				msg.debug(this, "no more move available from player: " + currentPlayer + ", depth = " + depth); 
				result = evaluator.evaluate(bb.getBoard(), currentPlayer, ig.getMyCards(), 1); 
				return result; 
			}
			if( (depth == MAXDEPTH && !possible.isEmpty()) )
			{
				if(Rules.getInstance().getPiecesInGameForPlayer(bb.getBoard(), ig.getMyPlayer()).length == 0)
				{
					highestMove = chooseSimpleMove(possible);
					return 5555; // reduce steps
				}
			}
			if(depth == MAXDEPTH) {
				msg.debug(this, "All moves of my player: " + currentPlayer + " has to be " + getPlayer()); 
				msg.debugLegalMoves(this, possible);
			}
		}

		
		if(is_max_node)
		{			
			score = -2000; 
			
			for(int i=0; i< possible.size(); i++ )
			{
				board.makeMove(possible.get(i), currentPlayer);
				
				currentPlayer = board.getNextPlayer(currentPlayer); 
				chanceNode = true; 
				succScore = search(board, alpha, beta, depth, is_max_node, possible); 
					
				if(succScore == 0.0 && depth == MAXDEPTH )
				{
					// just increment once per round - in order to be able to compare
					if(singleEvalUsed == false)
						singleEvaluation++; 
					
					singleEvalUsed = true; 
					succScore = evaluator.evaluate(board.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1.0f); 
				}
				if(succScore > score)
				{
					score = succScore;

					byte nextPlayer = board.getLastPlayer(currentPlayer); 
					
					// set highest move if it's my player and if I'm in outermost level. 
					if(nextPlayer == ig.getMyPlayer() && depth == MAXDEPTH ) 
					{	
						highestMove = possible.get(i);
						msg.debug(this, "new score for Player: " + board.getLastPlayer(currentPlayer) +" - " + score + ", depth = " + depth);
						msg.debugMove(this, highestMove); 
					}
				}
				if(score > alpha) alpha = score; 
				if(alpha >= beta)
				{
					    currentPlayer = board.getLastPlayer(currentPlayer); 
						board.undoMove(currentPlayer); 
						cutoffs_alpha++; 
						return score; 
				}
				currentPlayer = board.getLastPlayer(currentPlayer); 
				board.undoMove(currentPlayer);
			}
			
		}else {
				
			score = 2000; 
					
			for(int i=0; i< possible.size(); i++ )
			{
				board.makeMove(possible.get(i), currentPlayer);
				currentPlayer = board.getNextPlayer(currentPlayer); 
				chanceNode = true;
				
				// after last chance node follows my node directly and I need to subtract depth-1
				if(currentPlayer == ig.getMyPlayer())
					succScore = search(board, alpha, beta, depth-1, is_max_node, possible);
				else
					succScore = search(board, alpha, beta, depth, is_max_node, possible);  
				
				if(succScore < score)
				{
					score = succScore;
				}
				if(score < beta) beta = score; 
				if(beta <= alpha) 
				{
					currentPlayer = board.getLastPlayer(currentPlayer); 
					board.undoMove(currentPlayer);
					cutoffs_beta++; 
					return score;
				}
				currentPlayer = board.getLastPlayer(currentPlayer); 
				board.undoMove(currentPlayer); 
			}
		}	
		return score; 
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
	public Move makeMove() {
		 
		int[] tmp = new int[6]; 
		highestMove = null; 
		small = false; 
		singleEvalUsed = false; 
		
		tmp = ig.getCardsForPlayer(getPlayer()); 
		for(int i=0; i<tmp.length; i++)
		{
			ig.setUsedCards(tmp[i]);
		}	
		rounds++; 
		
		currentPlayer = ig.getMyPlayer(); 
		chanceNode = false; 
		boolean is_max_node = false; 
		
		List<Move> test = bb.getAllPossibleMoves(ig.getMyPlayer()); 
		if(test.isEmpty())
			return null; 
				
		if(ig.getCardsForPlayer(currentPlayer)[1] == -1) 
		{
			MAXDEPTH = 4; 
		}else 
		{
			int[] usedCards = ig.getUsedCards(); 
		
			int s =0; 
			for(int i=0; i<usedCards.length; i++)
				s += usedCards[i]; 
		
			if(s > 52)
			{
				MAXDEPTH = 8;
				small = false; 
			}else {
				MAXDEPTH = 4; 
				small = true; 
			}
		}

		highest = search(bb, -2000.0f, 2000.0f, MAXDEPTH, is_max_node, null); 
		
		if(small == false)
			MAXDEPTH = 8; 
		else
			MAXDEPTH = 4; 
		
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" ); 
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" ); 
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@   RETURNING CARD    @@@@@@@@@@@@@@@@@@@@" ); 
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" ); 
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
		msg.debug(this, " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
		msg.debug(this, " chance_cutoffs_alpha: " + cutoffs_chance_alpha + " , chance_cutoffs_alpha: " + cutoffs_beta);
		msg.debug(this, " alpha_cutoffs: " + cutoffs_alpha + ", beta_cutoffs: " + cutoffs_beta);
		msg.debug(this, " rounds: " + rounds);
		msg.debug(this, " hasJokerOrSeven: " + usedTwoStep); 
		msg.debug(this, " singleEvaluation: " + singleEvaluation); 
		
		if(highestMove != null)
			msg.debug(this, "Player: " + ig.getMyPlayer() + ", Karte: " + highestMove.getCard() + ", Bewertung: " + highest + ", s: " + highestMove.getPositions()[0] + ", t: " + highestMove.getPositions()[1]);
		else
			msg.debug(this, "highest move is null"); 
		
		return highestMove;
	}

	
	@Override
	public void setEvaluator(Evaluator eval)
	{
		this.evaluator = eval; 
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
	
	
	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenMap) {
		
		if(moeglich.size() < 1)
		{
			return null; 
		}
		
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, bb, ig);
		
		highestMove = makeMove();
	
		if(highestMove == null)
			msg.debug(this, "highestMove is null"); 
		msg.debug(this, "Karte: " + highestMove.getCard() + ", Bewertung: " + highest + ", s: " + highestMove.getPositions()[0] + ", t: " + highestMove.getPositions()[1]);
		zee = parser.convertBack(highestMove.getCard(),highestMove.getPositions(), ig.getMyPlayer());
		
		
		StringBuffer sb = new StringBuffer();
		for(Bewegung bew : zee.toZugEingabe().getBewegungen())
		{
			sb.append("start: " + bew.start.getNummer() + " ziel: " +bew.ziel.getNummer() + "\n");
		}
		msg.debug(this, "Karte: " + zee.getKarte() + ", konkrete Karte: " + zee.getKonkreteKarte() + ", Züge: " + sb);
	
		msg.debug(this, "##################################################");
		msg.debug(this, "##################################################");
		msg.debug(this, "##################################################");
		msg.debug(this, "=========== RETURN CONCRETE CARD ================"); 
		msg.debug(this, "##################################################");
		msg.debug(this, "##################################################");
		msg.debug(this, "##################################################");
		
		return zee;
		
	}
	
	/**
	 * Sorts a set of possible moves. Note, that it doesn't matter what player this 
	 * method calls. The evaluation is performed in consideration of the current player. 
	 * If it's not my player, the the cards are not taken into account. 
	 * @param moves list of moves to sort
	 * @return sorted list of moves
	 */
	public List<Move> getSortedMoves(List<Move> moves)
	{
		List<MoveEvalPair> c = new ArrayList<MoveEvalPair>(); 
		int result = 0; 
		
		List<Move> sortedMoves = null; 		
		sortedMoves = moves; 
		
		for(int i = 0; i < sortedMoves.size(); i++)
		{
			bb.makeMove(sortedMoves.get(i), currentPlayer); 
			result = evaluator.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1);
		
			// add result of evaluation and its move into a list
			c.add(new MoveEvalPair(result, sortedMoves.get(i))); 
		
			bb.undoMove(currentPlayer); 
		}
		Collections.sort(c, new ReverseComperator()); 
		sortedMoves.clear(); 
		int newSize = 0; 
		
		// if there is just one possible move, then add only that otherwise two
		if(c.size() < limitOppMoves) {
			newSize = c.size() == 1? 1: c.size(); 
		} else
			newSize = limitOppMoves; 
		for(int i=0; i<newSize; i++)
		{
			sortedMoves.add(c.get(i).move); 
		}
		msg.debug(this, "Shrinking from --> " + c.size() + " <-- to  " + sortedMoves.size() + " elements ----"); 
		
		return sortedMoves; 
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
	 * This class is composed of a move and its appropriate evaluated value
	 */
	class MoveEvalPair {
		
		public int value; 
		public Move move; 
		public MoveEvalPair(int value, Move move) { this.value = value; this.move = move; }
	}
}