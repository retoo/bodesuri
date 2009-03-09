package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.SimpleEvaluatorV5;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.framework.Parser;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.zugsystem.Bewegung;


/**
 * This bot behaves as Alpha-Beta_V1 but he only considers 
 * the two best moves of every opponent player. 
 * 
 */
public class AlphaBeta_V2 implements IBot {
	
	private DebugMsg msg = DebugMsg.getInstance();
	Evaluator evaluator = new SimpleEvaluatorV5();  
	private ZugErfasstEvent zee = null; 
	private Move highestMove = null;
	private byte currentPlayer; 
	private int maxdepth = 8; 
	private InformationGatherer ig;
	private BotBoard bb;
	
	/**
	 * The constructor for the AlphaBeta_V2 Bot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public AlphaBeta_V2(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the AlphaBeta_V2 Bot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public AlphaBeta_V2(){	
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
			possible = getSortedMoves(possible);
			
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
			
			if(ig.getMyPlayer() == currentPlayer)
			{
				// check if my player is still able to move (bug 12)

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

			for(int i=0; i< possible.size(); i++ )
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
		
			
			
			for(int i=0; i< possible.size(); i++ )
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
		
		
		if(ig.getCardsForPlayer(currentPlayer)[1] == -1)
			maxdepth = 4; 
		
		// FIXME
		List<Move> test = bb.getAllPossibleMoves(ig.getMyPlayer()); 
		msg.debug(this, "==== test ==="); 
		msg.debugLegalMoves(this, test); 
		msg.debug(this, "============="); 
		
		
		if(test.isEmpty())
			return null; 
		
		byte[] expectedArray = new byte[80];
		for(int i = 0; i < expectedArray.length; i++)
			expectedArray[i] = bb.getBoard()[i];

		
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
		
		for(int i = 0; i < expectedArray.length; i++)
		{
			if(expectedArray[i] != bb.getBoard()[i])
			{
				msg.debug(this, "expected[i] != bb.getBoard()[i]: "+ expectedArray[i] + ", " +  bb.getBoard()[i] + ", i: " + i);
				msg.debug(this, bb.toString()); 
				msg.debugLegalMoves(this, test); 
//				System.exit(1); 
			}
		}
		
		return highestMove;
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
		int[] tmpCards = new int[] {-1, -1, -1, -1, -1, -1}; 
		int result = 0; 
		
		List<Move> sortedMoves = null; 		
		sortedMoves = moves; 
		
		for(int i = 0; i < sortedMoves.size(); i++)
		{
			bb.makeMove(sortedMoves.get(i), currentPlayer); 
			
			if(ig.getMyPlayer() == currentPlayer)
				result = evaluator.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1);
			else
				result = evaluator.evaluate(bb.getBoard(), currentPlayer, tmpCards, 1);
			// add result of evaluation and its move into a list
			c.add(new MoveEvalPair(result, sortedMoves.get(i))); 
		
			bb.undoMove(currentPlayer); 
		}
		Collections.sort(c, new ReverseComperator()); 
		
		msg.debug(this, "---- List contains " + c.size() + " elements ----"); 

		sortedMoves.clear(); 
		
		if(currentPlayer != ig.getMyPlayer())
		{
			// if there is just one possible move, then add only that otherwise two
			int newSize = c.size() == 1? 1: 2; 
			for(int i=0; i<newSize; i++)
			{
				sortedMoves.add(c.get(i).move); 
			}
		}
		// just in case it's my player:
		// if size is smaller than 5, then take it. Otherwise reduce size to at most 17
		else if(c.size() <= 5)
		{
			for(MoveEvalPair m: c)
			sortedMoves.add(m.move); 
			
		}  else {
			int newSize = ( (c.size()/2) % 2) == 0 ? c.size()/2 : c.size()/2-1;
			int upperLimit = (newSize <= 17 ? newSize : 17); 
			for(int i=0; i<upperLimit; i++)
			{
				sortedMoves.add(c.get(i).move); 
			}
		}
		msg.debug(this, "After Shrinking the List contains " + sortedMoves.size() + " elements ----"); 
		
		return sortedMoves; 
	}
	
	
	/**
	 * this helper class consists of a move-value pair. The value is the result 
	 * after evaluation for that move.   
	 */
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
	class ReverseComperator implements Comparator
	{
		
		/**
		 * @param a first object to be compared
		 * @param b second object to be compared
		 * @return -1 if a>b, 0 if a=b, +1 if a<b
		 */
		public int compare( Object a, Object b)
		{
			MoveEvalPair val1 = (MoveEvalPair)a;
			MoveEvalPair val2 = (MoveEvalPair)b;
			if(val1.value > val2.value)
			  return -1; 
			else if(val1.value < val2.value)
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
	 * @param moves list of possible moves
	 * @return best move
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
