package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.SimpleEvaluatorV5;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.framework.Parser;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.utils.DebugMsg;

import java.util.List;
import java.util.Map;

import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.zugsystem.Bewegung;



/**
 * Implementation of Star1 algorithm.
 * It alternates between Star1 for a chance node and Alpha-Beta for a normal node
 * 
 */
public class Star1_Bot implements IBot {

	private DebugMsg msg = DebugMsg.getInstance(); 
	private Move highestMove = null; 
	private ZugErfasstEvent zee = null; 
	private Evaluator evaluator = new SimpleEvaluatorV5();
	private byte currentPlayer; 
	private int playersCard; 
	private boolean chanceNode = false; 
	private int N = 13; 
	private int U = 2000; 
	private int L = -2000; 
	
	private InformationGatherer ig;
	private BotBoard bb;
	
	/**
	 * The constructor for the Star1_Bot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public Star1_Bot(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the Star1_Bot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public Star1_Bot(){	
		this.ig = new InformationGatherer(Players.EMPTY);
		this.bb = new BotBoard(new byte[80], this.ig);
	}
	
	
	/**
	 *  
	 *  This method is called if the current node is a chance node
	 */	
	float Star1(float alpha, float beta, int depth, boolean is_max_node )
	{
		int result = 0, vsum = 0; 
		float A, B, AX, BX, v = 0; 
		if(depth == 0)
		{
			result = evaluator.evaluate(bb.getBoard(), currentPlayer, ig.getCardsForPlayer(ig.getMyPlayer()), 1.0F);
			msg.debug(this, "result: " + result); 
			return result; 
		}
		A = N * (alpha - U) + U; 
		B = N * (beta  - L) + L; 
		
		for(int i=1; i <= N; i++)
		{
			msg.debug(this, " Chance Node for Player: " + currentPlayer + " Card: " + i + ", DEPTH = " + depth);   
			AX = Math.max(A, L); 
			BX = Math.min(B, U);
			chanceNode = false; 
			playersCard = i; 
			v = search(AX, BX, depth, is_max_node);
			if(v <= A) { msg.debug(this, " ##### chance node cutoff v<=A: " + v + " <= " + A + " ##############"); return  alpha ; } 
			if(v >= B) { msg.debug(this, " ##### chance node cutoff v>=B: " + v + " >= " + B + " ##############"); return (beta); } 
			vsum += v; 
			A += U - v; 
			B += L - v; 
	
		}
		return (vsum/N); 
	}
	
	/**
	 * This method decides which kind is of Node is called. It either calls Alpha-Beta for Min, Max Node
	 * or Star1 for a chance Node.
	 * @param AX
	 * @param BX
	 * @param depth
	 */
	public float search(float AX, float BX, int depth, boolean is_max_node) 
	{
		// call chance node only for other three players but not for my player
		if(chanceNode && (currentPlayer != ig.getMyPlayer()) )
			return Star1(AX, BX, depth-1, is_max_node); 
		else
			return AlphaBeta(AX, BX, depth-1, !is_max_node); 
	}
	
	
	public float AlphaBeta(float alpha, float beta, int depth, boolean is_max_node)
	{
		float result = 0; 
		float score = 0; 
		float succScore = 0; 
		
		if(depth == 0) 
		{
			result = evaluator.evaluate(bb.getBoard(), currentPlayer, ig.getCardsForPlayer(ig.getMyPlayer()), 1); 
			msg.debug(this, "player who's evaluating: " + currentPlayer + " - score: " + result); 
			return result; 
		}
		List<Move> possible = null; 
		
		if(currentPlayer != ig.getMyPlayer())
		{
			possible = bb.getPossibleMovesForCard(playersCard, currentPlayer); 
		} else {
			possible = bb.getAllPossibleMoves(currentPlayer);
		}
		
		if(is_max_node)
		{			
			score = -2000; 
			
			// print only my legal moves
			if(currentPlayer == ig.getMyPlayer())
				msg.debugLegalMoves(this, possible); 

			for(int i=0; i< possible.size(); i++ )
			{
				msg.debugMove(this, possible.get(i)); 
				
				bb.makeMove(possible.get(i), currentPlayer);
				
				currentPlayer = bb.getNextPlayer(currentPlayer);
				chanceNode = true; 
				succScore = search(alpha, beta, depth, is_max_node); 
					
				if(succScore > score)
				{
					score = succScore;
					
					byte tmp = bb.getLastPlayer(currentPlayer);
					
					// set highest move if it's my player and if I'm in outermost level. 
					if(ig.getMyPlayer() == tmp && depth == 7) // 3
						highestMove = possible.get(i);
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
				msg.debugMove(this, possible.get(i)); 

				bb.makeMove(possible.get(i), currentPlayer);
				currentPlayer = bb.getNextPlayer(currentPlayer); 
				chanceNode = true; 
				succScore = search(alpha, beta, depth, is_max_node);  
				
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
		
		highestMove = null; 
		if(evaluator == null)
			evaluator = new SimpleEvaluatorV5(); 
		
		currentPlayer = ig.getMyPlayer();
		
		chanceNode = false; 
		boolean is_max_node = false; 
		float highest = search(-2000.0f, 2000.0f, 8, is_max_node); // 4
		
		if(highestMove != null){
			msg.debug(this, "Karte: " + highestMove.getCard() + ", Bewertung: " + highest + ", s: " + highestMove.getPositions()[0] + ", t: " + highestMove.getPositions()[1]);
		}
		return highestMove;
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

	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenMap) {
		
		if(moeglich.size() < 1)
		{
			msg.debug(this, "Star1_Bot: They don't even give me possibilities to choose from!");
			return null; 
		}
		
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, this.bb, this.ig); 
		
		makeMove();
		 
		
		if(highestMove == null)
			msg.debug(this, "highestMove is null"); 
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