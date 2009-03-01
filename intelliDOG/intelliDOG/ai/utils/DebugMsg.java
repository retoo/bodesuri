package intelliDOG.ai.utils;

import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.Move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * This class helps with debugging. You may add your class to 
 * the white list and send all messages to this class. All not 
 * white-listed messages are suppressed. If you wish to see all:
 * set the setDebugAll() on true. 
 */
public final class DebugMsg {

	/** 
	 *   Debug flag to enable debugging output
	 */ 
	private static boolean debug = false; 
	/** myself **/ 
	private static DebugMsg myself = null;
	/** white list for debugging **/ 
	private LinkedList<String> whiteList = new LinkedList<String>();
	private FileOutputStream fos = null; 

	/**
	 * private constructor because its a singleton 
	 */
	private DebugMsg()
	{
		try{
		fos = new FileOutputStream(new File("intellilog.txt"));
		}
		catch(Exception e)
		{
			System.out.println("fileoutput down.. ");
		}
		//do nothing here
	}

	/**
	 * singleton method to get the instance of this class
	 * (lazy creation)
	 */
	public static DebugMsg getInstance()
	{
		if(myself == null)
		{
			myself = new DebugMsg(); 
		}
		return myself; 
	}
	/**
	 * sets the general debug
	 * @param b true/false
	 */
	public void setDebugAll(boolean b)
	{
		debug = b; 
	}
	/**
	 * gives back if the general debug is on
	 * @return true if yes
	 */
	public boolean isDebugAll()
	{
		return debug; 
	}
	/**
	 * adds the full qualified class name to the white list
	 * @param s class name 
	 */
	public void addItemForWhiteList(String s)
	{
		whiteList.add(s);
	}
	/**
	 * adds the objects full qualified class name to the white list
	 * @param o the object
	 */
	public void addItemForWhiteList(Object o)
	{
		String s = o.getClass().getCanonicalName();
		whiteList.add(s);
	}
	/**
	 * debug method for text
	 * @param o the object which sent the message
	 * @param msg the text to be shown
	 */
	public void debug(Object o,String msg)
	{
		String sender = o.getClass().getCanonicalName();
		if(debug || isWhiteListed(sender))
			System.out.println(sender + ": " + msg); 
		
	}
	/**
	 * prints the cards in a human readable form 
	 * @param o the object which sent the message
	 * @param cards to be shown
	 */
	public void debugCards(Object o, int[] cards)
	{
		String sender = o.getClass().getCanonicalName();
		if(debug || isWhiteListed(sender))
		{
			System.out.print(sender + ": ");
			System.out.print("Cards: "); 
			for(int i=0; i<cards.length; i++)
				if(cards[i] == Cards.JOKER)
					System.out.print(cards[i] + " "); 
				else
					System.out.print(cards[i]%13 + " ") ; 
			
			System.out.println(); 
		}
	}
	
	/**
	 * prints the pieces /pawns in a human readable form
	 * @param o the object which sent the message
	 * @param pieces to be shown
	 */
	public void debugPieces(Object o, int[] pieces)
	{
		String sender = o.getClass().getCanonicalName();
		if(debug || isWhiteListed(sender))
		{
			if(pieces.length == 0)
				return; 
			
			System.out.print(sender + ": ");
			System.out.print("Pieces: "); 
			for(int i=0; i<pieces.length; i++)
					System.out.print(pieces[i] + " ") ; 
			
			System.out.println(); 
		}
	}
	/**
	 * prints the moves in a human readable form
	 * @param o the object which sent the message
	 * @param moves to be shown
	 */
	public void debugLegalMoves(Object o, List<Move> moves)
	{
		String sender = o.getClass().getCanonicalName();
		if(debug || isWhiteListed(sender))
		{
			System.out.print(sender + ": ");
			System.out.println("Legal moves: "); 
			for(int i=0; i<moves.size(); i++)
			{
				System.out.print("card -> " + moves.get(i).getCard() + ", ");
				System.out.print("positions -> ");
				for(int j=0; j<moves.get(i).getPositions().length; j+= 2){
					System.out.print("s: " + moves.get(i).getPositions()[j] + ", ");
					System.out.print("t: " + moves.get(i).getPositions()[j + 1]);
					if(j != moves.get(i).getPositions().length - 2){
						System.out.print(" / ");
					}
				}
				System.out.println();
			}
		}
	}
	/**
	 * returns if the class is present in the white list
	 * @param sender full qualified class name
	 * @return true if the class is present
	 */
	private boolean isWhiteListed(String sender)
	{
		for(int i = 0; i< whiteList.size(); i++)
		{
			if(whiteList.get(i).contains(sender))
			{
				return true; 
			}
		}
		return false;
		
	}
	/**
	 * does the filehandling
	 * @param logtext the debug message
	 */
	private synchronized void  log2file(String logtext)
	{
		try
		{
			logtext = new Date(System.currentTimeMillis()) + " " + logtext + "\n";
			fos.write(logtext.getBytes());
			fos.flush();
		}
		catch(IOException io)
		{
			//failure in log subsystem. can't do much
			io.printStackTrace(); 
		}
	}
	
	/**
	 * logs excpetion to a file
	 * @param o the object which sent the message
	 * @param elements the stacktrace
	 */
	public void log2file(Object o, StackTraceElement[] elements)
	{
		if(isWhiteListed(o.getClass().getCanonicalName()))
		{
			StringBuffer res = new StringBuffer();
			for(int i = 0; i<elements.length; i++)
			{
				res.append(elements[i].toString()+"\n");
			}
			log2file(res.toString());
		}
	}
	/**
	 * writes the message to a file if the sender was whitelisted
	 * @param o the object which sent the message
	 * @param text the debugmessage
	 */
	public void log2file(Object o, String text)
	{
		if(debug || isWhiteListed(o.getClass().getCanonicalName()))
		{
			log2file(text);
		}
	}
	/**
	 * goes through the intellilog.txt, counts all lines and how many
	 * times the search string was found. 
	 * @param search which bot should be searched
	 */
	public static void examineLog(String search)
	{	try
		{
			int games = 0;
			int won = 0; 
			String result = null; 
			File f = new File("intellilog.txt");
			BufferedReader in = new BufferedReader(new FileReader(f));
	        while((result = in.readLine()) != null)
	        { 
	        	if(result.contains(search))
	        		won++;
	        	games++; 
	        }
	        if(games == 0)
	        	return; //file empty.. 
	        games = games / 4; 
	        won = won / 4; 
	        if(games == 0)
	        	return; //file empty..
	        float percentage = (100/games * won);
	        System.out.println("our bot won " + won + " in total " + games + " thats: " + percentage + "%");
		
		}
		catch(Exception e)
		{
			System.out.println("Auswertung verreckt");
			e.printStackTrace();
		}	
	}
	
	public void debugMove(Object o, Move m)
	{
		String sender = o.getClass().getCanonicalName();
		if(debug || isWhiteListed(sender))
		{
			System.out.print("card -> " + m.getCard() + ", ");
			System.out.print("positions -> ");
			for(int j=0; j<m.getPositions().length; j+= 2){
				System.out.print("s: " + m.getPositions()[j] + ", ");
				System.out.print("t: " + m.getPositions()[j + 1]);
				if(j != m.getPositions().length - 2){
					System.out.print(" / ");
				}
			}
			System.out.println();
		}
	}
	/**
	 * Backport to examine already existing logs
	 */
	public static void main(String[] args)
	{
		examineLog(args[0]);
	}
	
}
