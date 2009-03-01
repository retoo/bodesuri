

package intelliDOG.ai.utils;

import intelliDOG.ai.bots.IBot;
import intelliDOG.ai.bots.N_StepBot;
import intelliDOG.ai.bots.RandomBot;
import intelliDOG.ai.bots.SimpleBot;
import intelliDOG.ai.bots.TwoStepBot;
import intelliDOG.ai.evaluators.Evaluator;
import intelliDOG.ai.evaluators.SimpleEvaluatorV2;
import intelliDOG.ai.evaluators.SimpleEvaluatorV4;
import intelliDOG.ai.framework.Board;
import intelliDOG.ai.framework.BotBoard;
import intelliDOG.ai.framework.Game;
import intelliDOG.ai.framework.InformationGatherer;
import intelliDOG.ai.framework.Parser;
import intelliDOG.ai.framework.Players;
import intelliDOG.ai.ui.GameWindow;
import intelliDOG.ai.ui.IntelliDOGStarter;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import ch.bodesuri.applikation.bot.BotController;
import ch.bodesuri.applikation.bot.IntelliBot;
import ch.bodesuri.applikation.bot.Stupidbot;
import ch.bodesuri.applikation.client.konfiguration.Konfiguration;
import ch.bodesuri.applikation.client.zustaende.NichtAmZug;
import ch.bodesuri.initialisierung.BodesuriBot;
import ch.bodesuri.initialisierung.BodesuriServer;


/**
 * Based on the bodesuri arena. Use this class to test your bot 
 * against each other.
 *
 */
public class AIArena extends Thread
{
	
	private IntelliDOGStarter ids;
	
	public AIArena(){}
	
	public AIArena(IntelliDOGStarter ids){
		this.ids = ids;
	}
	
	public static void main(String[] args) throws InterruptedException 
	{
		AIArena aia = new AIArena();
		aia.start();
	}
	
	/**
	 * starts a game with standard options
	 * (one game, with gui, 500ms timeout, simplebot's vs. randombot's)
	 */
	public void startGame(){
		boolean ui = true;
		int nrOfGames = 1;
		
		String[] botClassNames = {"intelliDOG.ai.bots.SimpleBot", "intelliDOG.ai.bots.RandomBot", "intelliDOG.ai.bots.SimpleBot", "intelliDOG.ai.bots.RandomBot"};
		String[] botNames = {"SimpleBot1", "RandomBot1", "SimpleBot2", "RandomBot2"};
		
		startGame(ui, nrOfGames, botClassNames, botNames);
	}
	
	/**
	 * starts games with the given parameters
	 * @param ui with ui?
	 * @param nrOfGames how many games that shall be played
	 * @param botClassNames the class names of the bots that shall play
	 * @param botNames the names of the bots
	 */
	public void startGame(boolean ui, int nrOfGames, String[] botClassNames, String [] botNames){
		assert botClassNames.length == 4 && botNames.length == 4;
		
		GameWindow gw = null;
		
		IBot[] bots = new IBot[4];
		try {
			InformationGatherer ig0 = new InformationGatherer(Players.P1);
			Class [] botClasses = new Class[4];
			for(int i = 0; i < 4; i++){
				botClasses[i] = Class.forName(botClassNames[i]);
			}
			
			//set this on true to view the game, 
			boolean mitgui = ui; 
			//how many rounds would you like to play?
			int times = nrOfGames;
			DebugMsg msg = DebugMsg.getInstance();
			//add here the class which you want to debug 
//			msg.addItemForWhiteList(NichtAmZug.class.getCanonicalName());
//			msg.addItemForWhiteList(Parser.class.getCanonicalName());
//			msg.addItemForWhiteList(N_StepBot.class.getCanonicalName());
//			msg.addItemForWhiteList(TwoStepBot.class.getCanonicalName());
//			msg.addItemForWhiteList(Board.class.getCanonicalName());
//			msg.addItemForWhiteList(SimpleEvaluatorV4.class.getCanonicalName());

			for(int j = 0; j<times; j++)
			{
				BodesuriServer server = new BodesuriServer();
				server.start();
				server.warteAufBereitschaft();

				Vector<Thread> clients = new Vector<Thread>();

				//add here the names of the bot 
				Vector<String> nicks = new Vector<String>();
				nicks.add(botNames[0]); 	
				nicks.add(botNames[1]);
				nicks.add(botNames[2]);
				nicks.add(botNames[3]);

				for (int i = 0; i < 4; i++) 
				{
					//do not modify 
					Konfiguration konfig = new Konfiguration();
					konfig.defaultName = nicks.get(i);
					konfig.debugAutoLogin = true;
					konfig.debugMeldungen = false;
					konfig.debugBotsZoegernNicht = !mitgui;
					konfig.aiKeinGui = !mitgui;
					konfig.aiDebugMsg = false;

					//insert the bot classes 
					Thread t = new BodesuriBot(konfig, botClasses[i], mitgui);

					t.start();
					Thread.sleep(300);
					clients.add(t);

				}

				//clean up
				server.join();
				for (Thread t : clients)
				{
					t.join();
					t = null;
				}
				server = null;
				Thread.sleep(100);

				System.out.println("game: " +j + " over");
			}	
			//search for the specified bot in the intelliLog
			DebugMsg.examineLog("TwoStepBot");
			System.out.println("Alles fertig");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		if(ids == null){
			startGame();
		}else{
			startGame(ids.isGUISelected(), ids.getNrOfGames(),
				ids.getBotClassNames(), ids.getBotNames());
		}
	}
}