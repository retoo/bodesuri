package intelliDOG.ai.init;

import intelliDOG.ai.bots.IBot;
import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.Game;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.ui.GameWindow;
import intelliDOG.ai.ui.IntelliDOGStarter;
import intelliDOG.ai.utils.Statistics;

import java.lang.reflect.InvocationTargetException;

public class Init extends Thread {
	private IntelliDOGStarter ids;

	public Init(IntelliDOGStarter ids){
		this.ids = ids;
	}
	
	
	@Override
	public void run(){
		if(ids == null){
			startGame();
		}else{
			startGame(ids.isGUISelected(), ids.getTimeout(), ids.getNrOfGames(),
				ids.getBotClassNames(), ids.getBotNames(), ids.getEvaluators());
		}
	}

	/**
	 * starts a game with standard options
	 * (one game, with gui, 500ms timeout, simplebot's vs. randombot's)
	 */
	public void startGame(){
		boolean ui = true;
		long timeout = 500;
		int nrOfGames = 1;
		
		String[] botClassNames = {"intelliDOG.ai.bots.SimpleBot", "intelliDOG.ai.bots.RandomBot", "intelliDOG.ai.bots.SimpleBot", "intelliDOG.ai.bots.RandomBot"};
		String[] botNames = {"SimpleBot1", "RandomBot1", "SimpleBot2", "RandomBot2"};
		String[] evaluators = {"Standard", "Standard", "Standard", "Standard"};
		
		startGame(ui, timeout, nrOfGames, botClassNames, botNames, evaluators);
	}

	/**
	 * starts games with the given parameters
	 * @param ui with ui?
	 * @param timeout timeout after each move (only relevant when ui=true)
	 * @param nrOfGames how many games that shall be played
	 * @param botClassNames the class names of the bots that shall play
	 * @param botNames the names of the bots
	 * @param evaluators the class names of the evaluators to use by the bots
	 */
	public void startGame(boolean ui, long timeout, int nrOfGames, String[] botClassNames, String [] botNames, String [] evaluators){
		assert botClassNames.length == 4 && botNames.length == 4;
		
		GameWindow gw = null;
		
		if(ui) {gw = new GameWindow();}
		
		IBot[] bots = new IBot[4];
		try {
			InformationGatherer ig0 = new InformationGatherer(Players.P1);
			Class bot0 = Class.forName(botClassNames[0]);
			bots[0] = (IBot)bot0.getConstructor(BotBoard.class, InformationGatherer.class).newInstance(new BotBoard(new byte[80], ig0), ig0);
			InformationGatherer ig1 = new InformationGatherer(Players.P2);
			Class bot1 = Class.forName(botClassNames[1]);
			bots[1] = (IBot)bot1.getConstructor(BotBoard.class, InformationGatherer.class).newInstance(new BotBoard(new byte[80], ig1), ig1);
			InformationGatherer ig2 = new InformationGatherer(Players.P3);
			Class bot2 = Class.forName(botClassNames[2]);
			bots[2] = (IBot)bot2.getConstructor(BotBoard.class, InformationGatherer.class).newInstance(new BotBoard(new byte[80], ig2), ig2);
			InformationGatherer ig3 = new InformationGatherer(Players.P4);
			Class bot3 = Class.forName(botClassNames[3]);
			bots[3] = (IBot)bot3.getConstructor(BotBoard.class, InformationGatherer.class).newInstance(new BotBoard(new byte[80], ig3), ig3);
			
			for(int i = 0; i < 4; i++){
				if(!evaluators[i].equals("Standard")){
					Class eval = Class.forName(evaluators[i]);
					bots[i].setEvaluator((Evaluator)eval.newInstance());
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < nrOfGames; i++){
			Game g;
			if(gw != null){ gw.setTitle("Game " + (i + 1) + " of " + nrOfGames); }
			g = new Game(bots, gw, timeout);
			g.run();
			Statistics.updateGameStats(i + 1);
		}
	}

}
