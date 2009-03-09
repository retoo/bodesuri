package intelliDOG.ai.utils;


/**
 * the starting class for games over the intelliDOG framework
 *
 */
public class IntelliOnlyArena extends Thread{
	
	
	public IntelliOnlyArena(){}

	private static DebugMsg msg = DebugMsg.getInstance();
	
	
	public static void main(String[] args) {
		
		//msg.addItemForWhiteList(BoardWrapper.class.getCanonicalName());
		
		//TODO: make commandline switches available for setting bot types and nr of games to run and enable UI!
		IntelliOnlyArena ioa = new IntelliOnlyArena();
		ioa.start();
	}


	
	
}