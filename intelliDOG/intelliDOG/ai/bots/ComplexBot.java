package intelliDOG.ai.bots;

import intelliDOG.ai.evaluators.BlockEvaluator;
import intelliDOG.ai.evaluators.ComplexEvaluator;
import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.HeavenEvaluator;
import intelliDOG.ai.evaluators.NbrPawnsEvaluator;
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
import pd.zugsystem.Bewegung;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

/**
 * This Bot uses the ComplexEvaluator. Single Evaluators are added and make up 
 * a single evaluation function. 
 * 
 */
public class ComplexBot implements IBot {
	
	private DebugMsg msg = DebugMsg.getInstance();
	private ComplexEvaluator se = null;
	
	private InformationGatherer ig;
	private BotBoard bb;
	
	/**
	 * The constructor for the ComplexBot
	 * @param bb The <class>BotBoard</class> for this bot
	 * @param ig The <class>InformationGatherer</class> for this bot
	 */
	public ComplexBot(BotBoard bb, InformationGatherer ig){
		this.bb = bb;
		this.ig = ig;
	}
	
	/**
	 * This is the Default Constructor for the ComplexBot
	 * It is used to provide compatibility with the Bodesuri Framework
	 * Don't use this constructor outside of the Bodesuri Framework!
	 */
	public ComplexBot(){	
		this.ig = new InformationGatherer(Players.EMPTY);
		this.bb = new BotBoard(new byte[80], this.ig);
	}

	@Override
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, applikation.client.pd.Karte> kartenMap) {
		if(moeglich.size() < 1)
		{
			msg.debug(this, "ComplexBot: They don't even give me possibilities to choose from!");
			return null; 
		}
		Parser parser = new Parser(); 
		parser.convert(spiel, kartenMap, bb, ig); 
		
		Move highestMove = makeMove();
		ZugErfasstEvent zee = null;
		
		zee = parser.convertBack(highestMove.getCard(),highestMove.getPositions(), ig.getMyPlayer());
		
		StringBuffer sb = new StringBuffer();
		for(Bewegung bew : zee.toZugEingabe().getBewegungen())
		{
			sb.append("start: " + bew.start.getNummer() + " ziel: " +bew.ziel.getNummer() + "\n");
		}
		msg.debug(this, "Karte: " + zee.getKarte() + ", konkrete Karte: " + zee.getKonkreteKarte() + "züge: " + sb);
		
		return zee;
	}
	
	@Override
	public Move makeMove() {
		List<Move> possible = bb.getAllPossibleMoves(ig.getMyPlayer()); 
		Move highestMove = null;
		int highest = -2000; 
		if(se == null)
		{
			Evaluator e1 = new NbrPawnsEvaluator(); 
			Evaluator e2 = new HeavenEvaluator();
//			Evaluator e3 = new SimpleCardEvaluator(); 
			Evaluator e4 = new BlockEvaluator(); 
//			Evaluator e5 = new EnvironmentEvaluator();  
			
			se = new ComplexEvaluator();
			se.add(e1);
			se.add(e2); 
//			se.add(e3);
			se.add(e4); 
//			se.add(e5); 
		}
		for(int i = 0; i < possible.size(); i++)
		{
			bb.makeMove(possible.get(i), ig.getMyPlayer());
			int result = se.evaluate(bb.getBoard(), ig.getMyPlayer(), ig.getMyCards(), 1);
			if(highest < result)
			{
				highest = result;
				highestMove = possible.get(i);
			}
			bb.undoMove(ig.getMyPlayer());
			
		}
		if(highestMove != null)
			msg.debug(this, "Karte: " + highestMove.getCard() + ", Bewertung: " + highest + ", s: " + highestMove.getPositions()[0] + ", t: " + highestMove.getPositions()[1]);
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
		throw new UnsupportedOperationException(); 
	}

}
