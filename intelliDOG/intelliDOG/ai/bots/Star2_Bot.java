package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.SimpleEvaluatorV5;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.framework.Parser;
import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;
import java.util.List;
import java.util.Map;
import pd.regelsystem.Karte;
import pd.regelsystem.ZugEingabe;
import pd.zugsystem.Bewegung;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

/**
 * Implementation of variation of Star2 algorithm.
 * It alternates between chance node and calls Alpha-Beta for a normal node
 * 
 */
public class Star2_Bot implements IBot {

	private static int MAXDEPTH = 4; 
	private static int cutoffs_chance_alpha = 0;
	private static int cutoffs_chance_beta = 0; 
	private static int cutoffs_alpha = 0; 
	private static int cutoffs_beta = 0; 
	private static int rounds = 0; 
	private static int usedTwoStep = 0; 
	private static int singleEvaluation = 0; 
	private static int chance = 0; 
	
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
	 * The constructor for the Star2_Bot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public Star2_Bot(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 *  
	 *  This method is called if the current node is a chance node
	 */	
	float chanceNode(BotBoard board, float alpha, float beta, int depth, boolean is_max_node )
	{
		int result = 0, vsum = 0; 
		float A, B, AX, BX = 0;
		double v = 0; 
		
		if(depth == 0 || board.terminalState(getPlayer()) )
		{
			result = evaluator.evaluate(bb.getBoard(), getPlayer(), ig.getCardsForPlayer(getPlayer()), 1.0f);
			return result; 
		}
		
		A = N*(alpha-U) + U; 
		B = N*(beta -L) + L; 
		
		ig.calcProb(); 
		
		// First calculate current probability of cards
		double prob = 0.0;
		int probSize = ig.getProbSize(); 
		
		// only cards from 1 to King. Ignore Joker
		for(int i=1; i< probSize; i++)
		{
			prob += ig.getProb(i); 
		}
		prob = prob / N; 
		
		for(int i=1; i <= N; i++)
		{
			List<Move> possible = null; 
			possible = board.getPossibleMovesForCard(i, currentPlayer); 
		
			chance++; 
			AX = Math.max(A, L); 
			BX = Math.min(B, U);
				
			chanceNode = false; 

			ig.calcProb(); 
			double exp = ig.getProb(i);
			double probability = new Double( (exp * (1.0/13))) / prob;
	
			// and now perform action after chance event
			ig.setUsedCards(i); 

			v = search(board, AX, BX, depth, is_max_node, possible);
 
			ig.restoreUsedCards(i); 

			if(v <= A) {
//				msg.debug(this, " ######################################################################"); 
//				msg.debug(this, " ######################################################################"); 
//				msg.debug(this, " ######################################################################"); 
//				msg.debug(this, " ##### chance node cutoff v<=A: " + v + " <= " + A + " ##############"); 
				cutoffs_chance_alpha++;
				return  alpha ;
			} 

			if(v >= B) { 
//				msg.debug(this, " ######################################################################"); 
//				msg.debug(this, " ######################################################################"); 
//				msg.debug(this, " ######################################################################"); 
//				msg.debug(this, " ##### chance node cutoff v>=B: " + v + " >= " + B + " ##############");
				cutoffs_chance_beta++;
				return (beta); 
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
	 * @param b
	 * @param AX
	 * @param BX
	 * @param depth
	 */
	public float search(BotBoard b, float AX, float BX, int depth, boolean is_max_node, List<Move> possible) 
	{
		// call chance node only for other three players but not for my player
		if(chanceNode && (currentPlayer != ig.getMyPlayer()) )
			return chanceNode(b, AX, BX, depth-1, is_max_node); 
		else
			return AlphaBeta(b, AX, BX, depth, !is_max_node,  possible); 
	}
	

	/**
	 * This method calculates and evaluates all moves for either max- or min player. Then it performs that
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
	public float AlphaBeta(BotBoard board, float alpha, float beta, int depth, boolean is_max_node,  List<Move> possible)
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
			
		
			// check if I'm not able to move
			if( depth == MAXDEPTH && possible.isEmpty())
				return 0; 
			
			if( depth == MAXDEPTH && ig.hasJokerOrSeven())
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
					return 5555; // again... reduce steps
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
					if(nextPlayer == getPlayer() && (small == true? depth == 4 : depth == MAXDEPTH )) 
					{	
						highestMove = possible.get(i);
						msg.debug(this, "new score for Player: " + board.getLastPlayer(currentPlayer) +" - " + score);
						msg.debugMove(this, highestMove); 
					}
				}
				if(score > alpha) alpha = score; 
				if(alpha >= beta)
				{
//					    msg.debug(this, "MAX CUTOFF: score: " + score); 
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
//				    msg.debug(this, "MIN CUTOFF: score: " + score); 
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
		
//		// FIXME
		List<Move> test = bb.getAllPossibleMoves(ig.getMyPlayer()); 
//		msg.debug(this, "==== test ==="); 
//		msg.debugLegalMoves(this, test); 
//		msg.debug(this, "============="); 
		
		if(test.isEmpty())
			return null; 
		

		if(ig.getCardsForPlayer(currentPlayer)[1] == -1)
			MAXDEPTH = 4; 

//		if(ig.hasJokerOrSeven())
//		{
//			highestMove = chooseSimpleMove(test); 
//			usedTwoStep++; 
//			
//		} else
//		{
			highest = search(bb, -2000.0f, 2000.0f, MAXDEPTH, is_max_node, null); 
//		}
		
		MAXDEPTH = 4; 
		
		// There must be a bug
		if(highestMove == null && !test.isEmpty())
		{
			System.out.println("highestMove == null && !test.isEmpty()"); 
			msg.debugLegalMoves(this, test); 
			highestMove = test.get(0); 
//			System.exit(1); 
		}
		
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
			Map<Karte, applikation.client.pd.Karte> kartenMap) {
		
		if(moeglich.size() < 1)
		{
			msg.debug(this, "Star1_Bot: They don't even give me possibilities to choose from!");
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
}