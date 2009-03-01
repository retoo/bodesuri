package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Move;
import intelliDOG.ai.framework.Parser;
import intelliDOG.ai.framework.Players;

import java.util.List;
import java.util.Map;

import pd.regelsystem.Karte;
import pd.regelsystem.ZugEingabe;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

public class RandomBot implements IBot {

	private BotBoard bb;
	private InformationGatherer ig;
	
	/**
	 * The constructor for the RandomBot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public RandomBot(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the RandomBot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public RandomBot(){	
		this.ig = new InformationGatherer(Players.EMPTY);
		this.bb = new BotBoard(new byte[80], this.ig);
	}
	
	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich, Map<Karte, applikation.client.pd.Karte> kartenMap) 
	{
		if(moeglich.size() < 1)
		{
			return null; 
		}
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, bb, ig);
		
		Move highestMove = makeMove();
		
		ZugErfasstEvent zee = null;
		zee = parser.convertBack(highestMove.getCard(),highestMove.getPositions(), ig.getMyPlayer());
		
		return zee;
	}

	@Override
	public Move makeMove() {
		List<Move> possible = bb.getAllPossibleMoves(getPlayer());
		if(possible.size() == 0){ return null; }
		int choice = (int)(Math.random() * (possible.size() - 1) + 0.5);
		return possible.get(choice);
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
		throw new UnsupportedOperationException();
	}
	

}
