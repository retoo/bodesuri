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
 * The SimpleBot searches one step deep the possible game tree
 */
public class SimpleBot implements IBot {
	
	private DebugMsg msg = DebugMsg.getInstance();
	private Evaluator se = new SimpleEvaluatorV5();

	private InformationGatherer ig;
	private BotBoard bb;
	
	/**
	 * The constructor for the SimpleBot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public SimpleBot(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the SimpleBot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public SimpleBot(){	
		this.ig = new InformationGatherer(Players.EMPTY);
		this.bb = new BotBoard(new byte[80], this.ig);
	}
	
	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenMap) {
		if(moeglich.size() < 1)
		{
			msg.debug(this, "SimpleBot: They don't even give me possibilities to choose from!");
			return null; 
		}
		//convert the game state 
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, bb, ig);
		
		Move highestMove = makeMove();
		//unconvert the new move
		ZugErfasstEvent zee = null;
		zee = parser.convertBack(highestMove.getCard(),highestMove.getPositions(), ig.getMyPlayer());
		
		StringBuffer sb = new StringBuffer();
		for(Bewegung bew : zee.toZugEingabe().getBewegungen())
		{
			sb.append("start: " + bew.start.getNummer() + " ziel: " +bew.ziel.getNummer() + "\n");
		}
		msg.debug(this, "Karte: " + zee.getKarte() + ", konkrete Karte: " + zee.getKonkreteKarte() + "züge: " + sb);
		
//		if(moeglich.size() != possible.size()){
//			msg.debug(this, "They give us " + moeglich.size() + " possible moves and we have calculated " + possible.size() + " possible moves.");
//		}
		
		return zee;
	}
	
	@Override
	public Move makeMove() {
		List<Move> possible = bb.getAllPossibleMoves(getPlayer());
		//reset
		Move highestMove = null;
		int highest = -2000;
		//for each possible move..
		for(int i = 0; i < possible.size(); i++)
		{
			//..execute it
			bb.makeMove(possible.get(i), getPlayer());
			//..measure it
			int result = se.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1);
			//if it's the new best store it 
			if(highest < result)
			{
				highest = result;
				highestMove = possible.get(i);
			}
			//..finally, undo it again
			bb.undoMove(getPlayer());
		}
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
		this.se = eval; 
	}

}
