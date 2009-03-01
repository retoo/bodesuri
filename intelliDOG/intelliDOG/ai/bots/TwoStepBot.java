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

import pd.regelsystem.Karte;
import pd.regelsystem.ZugEingabe;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;
/**
 * TwoStepBot - spawns all immediate successor states and their successor
 * The second step is weighted as 25% compared to the first step
 */
public class TwoStepBot implements IBot {
	
	private DebugMsg msg = DebugMsg.getInstance();
	private Evaluator se = new SimpleEvaluatorV5();
	Move highestMove = null;
	int highest = -2000; 
	
	private InformationGatherer ig;
	private BotBoard bb;
	
	/**
	 * The constructor for the TwoStepBot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public TwoStepBot(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the TwoStepBot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public TwoStepBot(){	
		this.ig = new InformationGatherer(Players.EMPTY);
		this.bb = new BotBoard(new byte[80], this.ig);
	}

	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, applikation.client.pd.Karte> kartenMap) {
		if(moeglich.size() < 1)
		{
			msg.debug(this,"SimpleBot: They don't even give me possibilities to choose from!");
			return null; 
		}
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, bb, ig);

		makeMove();
		ZugErfasstEvent zee = parser.convertBack(highestMove.getCard(),highestMove.getPositions(), ig.getMyPlayer());
		msg.debug(this, "Karte: " + zee.getKarte() + ", konkrete Karte: " + zee.getKonkreteKarte());
		
		return zee;
	}
	//prototype of the first twostepmove
//	/**
//	 * calculate the best move
//	 * @param b the board
//	 * @param fading factor for the volatile targets
//	 */
//	public void calculateBestMove(float fading)
//	{
//		List<Move> possible = bb.getAllPossibleMoves(ig.getMyPlayer());
//		List<Move> possibleStageTwo = null; 
//		//immediate successor iteration
//		for(int i = 0; i < possible.size(); i++)
//		{
//				msg.debug(this, "################ outer loop: " + i+ " ####################");
//				bb.makeMove(possible.get(i), ig.getMyPlayer());
//				possibleStageTwo = bb.getAllPossibleMoves(ig.getMyPlayer());
//				int temp = se.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1);
//				//assure, that at least one move is in the second generation
//				if(possibleStageTwo.size() != 0)
//				{
//					//the second generation of successor
//					for(int j = 0; j <possibleStageTwo.size(); j++)
//					{
//						msg.debug(this, "############### inner loop: " + i+ " "+ j +" ######################");
//						bb.makeMove(possibleStageTwo.get(j), ig.getMyPlayer());
//						//evaluate, but lower the weight of the second generation to one quarter.
//						int result = temp +(int) (0.2f * se.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), fading));
//						msg.debug(this, "score: " + result + " card: " + possibleStageTwo.get(j).getCard() +
//								" from: "+ possibleStageTwo.get(j).getPositions()[0] + " to: " +
//								possibleStageTwo.get(j).getPositions()[1]);
//						if(highest < result)
//						{
//							
//							highest = result;
//							highestMove = possible.get(i);
//						}
//						bb.undoMove(ig.getMyPlayer());
//					}
//				}
//				// else, take the best from the first generation
//				else
//				{
//					if(highest < temp)
//					{
//						highest = temp;
//						highestMove = possible.get(i);
//					}
//				}
//				bb.undoMove(ig.getMyPlayer());
//		}
//		msg.debug(this, "Karte: " + highestMove.getCard() + ", Bewertung: " + highest + ", s: " + highestMove.getPositions()[0] + ", t: " + highestMove.getPositions()[1]);
//	}
	/**
	 * search recursively for a better move, walk to all tree nodes
	 * @param rootMove the immediate successor of the current state.
	 * @param value sum of all previous values
	 * @param depth the depth search, at maximum 6.
	 */
	private void calculateBestMove(Move rootMove,int value, int depth)
	{
		//get all possbible moves
		List<Move> moves = bb.getAllPossibleMoves(ig.getMyPlayer());
		msg.debug(this, "value: " + value + " depth: " + depth);
		if(moves.size() == 1 && depth == 0)
		{
			highestMove = moves.get(0);
			return;
		}
		//do we have moves left to evaluate
		if(moves.size() == 0 || depth ==2)
		{
			//now, we are on a leaf of the search tree
			//look if we have a better sum of values
			if(value > highest)
			{
				msg.debug(this, "found better move with old: " + highest + " new: " + value);
				highest = value;
				this.highestMove = rootMove;
			}
			return;
		}
		else
		{
			//for each moves in moves
			for(Move m : moves)
			{
				//calculate the fading
				float fading = (1f/5);
				if(depth == 0)
				{
					//decide the root move
					rootMove = m;
					//on the root move, fading must be 1
					fading = 1; 
				}
				bb.makeMove(m, ig.getMyPlayer());
				int result = (int) (fading * se.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), (float) (1/ Math.pow(2, depth))));
				msg.debug(this,"result is: " +result + " value was: " +value + " thats a sum of: " + (value + result) + "");
				calculateBestMove(rootMove, value + result, depth + 1);

				bb.undoMove(ig.getMyPlayer());
			}
		}
	}
	
	@Override
	public Move makeMove() {
		highest = -2000; 
		highestMove = null;
		calculateBestMove(null,0,0);
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
		this.se = eval; 
	}
	
	/**
	 * help method for testing purposes
	 * @return the highest value
	 */
	public int getHighestValue()
	{
		return this.highest;
	}

}
