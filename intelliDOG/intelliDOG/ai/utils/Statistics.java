package intelliDOG.ai.utils;

import intelliDOG.ai.ui.IntelliDOGStarter;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * this class is used for some statistical purposes
 *
 */
public abstract class Statistics {
	
	private static Map<Long, String[]> timeMap = new TreeMap<Long, String[]>();
	private static Stack<Long> openMethodStack = new Stack<Long>();
	private static IntelliDOGStarter ids;
	private static int winningTeam, pieceCount1, pieceCount2, moves;
	private static double time;

	/**
	 * used for performance measures with AspectJ
	 * @param callingClass
	 * @param invokedClass
	 * @param invokedMethodName
	 */
	public static void methodCalled(Class callingClass, Class invokedClass, String invokedMethodName){
		//work with key, value pair!?
		//System.out.println("Calling Class: " + callingClass.getCanonicalName() + ", invoked Class: " + invokedClass.getCanonicalName() + ", invoked Method: " + invokedMethodName);
		//System.out.println("Started @ " + System.nanoTime() / 100000);
		String[] values = new String[4];
		values[0] = "MethodCall";
		values[1] = callingClass.getCanonicalName();
		values[2] = invokedClass.getCanonicalName();
		values[3] = invokedMethodName;
		timeMap.put(System.nanoTime(), values);
	}
	
	/**
	 * used for performance measures with AspectJ
	 * @param callingClass
	 * @param invokedClass
	 * @param invokedMethodName
	 */
	public static void methodEnd(Class callingClass, Class invokedClass, String invokedMethodName){
		//System.out.println("Calling Class: " + callingClass.getCanonicalName() + ", invoked Class: " + invokedClass.getCanonicalName() + ", invoked Method: " + invokedMethodName);
		//System.out.println("Ended @ " + System.nanoTime() / 100000);
		
		String[] values = new String[4];
		values[0] = "MethodEnd";
		values[1] = callingClass.getCanonicalName();
		values[2] = invokedClass.getCanonicalName();
		values[3] = invokedMethodName;
		timeMap.put(System.nanoTime(), values);
	}
	
	/**
	 * used for performance measures with AspectJ
	 */
	public static void printTimeTable(){
		System.out.println("Nr. of elements: " + timeMap.size());
		String[] values;
		int i = 0;
		long lastTime = 0, firstTime = 0;
		long sum = 0;
		for(long key : timeMap.keySet()){
			if(i == 0){ firstTime = key; }
			if(i++ == timeMap.size() - 1){ lastTime = key; }
			values = timeMap.get(key);
			if(values[0].equals("MethodCall")){
				//push actual key
				openMethodStack.push(key);
			}else{
				//pop and calculate
				long openingTime = openMethodStack.pop();
				System.out.print("@ " + openingTime + ": ");
				System.out.print(values[1] + " --> " + values[2] + ".");
				System.out.println(values[3] + ": " + (key - openingTime));
				sum += key - openingTime;
			}
			/*System.out.print(values[0]);
			System.out.println(" @ " + key);
			System.out.print("Calling class: " + values[1]);
			System.out.print(", invoked class: " + values[2]);
			System.out.println(", invoked method: " + values[3]);*/
		}
		System.out.println("Sum of time spent in observed Methods: " + sum);
		System.out.println("total time elapsed: " + (lastTime - firstTime));
	}
	
	/**
	 * sets a reference to the <class>IntelliDOGStarter</class> used to start the game(s)
	 * @param iDogS
	 */
	public static void setIds(IntelliDOGStarter iDogS){
		ids = iDogS;
	}
	
	/**
	 * this method will update the game statistics on the <class>IntelliDOGStarter</class>
	 * with the collected stats
	 * @param gameNr the nr of the current game
	 */
	public static void updateGameStats(int gameNr){
		ids.addGameStat(gameNr, winningTeam, pieceCount1, pieceCount2, time, moves);
	}
	
	/**
	 * this method is used to update the stats in this class
	 * @param wt winning team
	 * @param pc1 pieces in heaven of team 1
	 * @param pc2 pieces in heaven of team 2
	 * @param t time elapsed
	 * @param mov the nr of moves used for a game
	 */
	public static void setGameStats(int wt, int pc1, int pc2, double t, int mov){
		winningTeam = wt;
		pieceCount1 = pc1;
		pieceCount2 = pc2;
		time = t;
		moves = mov;
	}
	
	/**
	 * used for performance measures with AspectJ
	 */
	public static void clearTime(){
		timeMap.clear();
		openMethodStack.clear();
	}
	
}
