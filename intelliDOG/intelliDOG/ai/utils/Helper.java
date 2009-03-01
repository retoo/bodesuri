package intelliDOG.ai.utils;

import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.Players;

/**
 * this class is not yet used!
 * perhaps it will be used in future!?
 *
 */
public final class Helper {

	
	private static final int HOMEPOSTION_P1 = 0;
	private static final int HOMEPOSTION_P2 = 16;
	private static final int HOMEPOSTION_P3 = 32;
	private static final int HOMEPOSTION_P4 = 48;
	
	private volatile static Helper instance;
	
	
	private Helper(){}
		
	/**
	 * get the only instance of the Helper class
	 * This is the thread-safe but yet performant implementation of a singleton.
	 * @return return an instance of the Helper class
	 */
	public synchronized static Helper getInstance() {
        if (instance == null) {
        	synchronized (Helper.class) {
				if(instance == null){
					instance = new Helper();
				}
			}
        }
        return instance;
    }
	
	
	
	/**
	 * Method to test if a field is occupied by a protected piece
	 * @param board the actual situation
	 * @param position the position on the board to test
	 * @return true if the field is occupied by a protected piece, false otherwise
	 */
	public boolean isProtected(byte[] board, int position){
		if(position >= board.length || position < 0) return false; 
		return board[position] == 5;
	}
	
	/**
	 * Method that returns the home position of the given player
	 * @param player the player to find its home position
	 * @return the home position of the given player
	 * @throws Exception if an invalid player is given over
	 */
	public int getHomePositionForPlayer(byte player){
		switch (player){
			case 1:
				return HOMEPOSTION_P1;
			case 2:
				return HOMEPOSTION_P2;
			case 3:
				return HOMEPOSTION_P3;
			case 4:
				return HOMEPOSTION_P4;
			default:
				assert false;
				return -1; 
		}
	}
	
	/**
	 * Method to determine if the position given over is one of the four home positions.
	 * @param position the position to test
	 * @return true if the given position is a home position, false otherwise
	 */
	public boolean isAHomePosition(int position){
		return (position == HOMEPOSTION_P1 || position == HOMEPOSTION_P2
					|| position == HOMEPOSTION_P3 || position == HOMEPOSTION_P4);
	}

	/**
	 * This method will find all pieces of a player on the given board
	 * @param board the board to search on
	 * @param player the player who's pieces shall be found
	 * @return an int[] containing all positions of the given player's pieces
	 */
	public int [] getPiecesInGameForPlayer(byte[] board, byte player) throws Exception{
		int [] tempPieces = new int[4];
		int pieceCount = 0;
		switch(player){
			case Players.P1:
				if(board[HOMEPOSTION_P1] == Players.ANY_SAVE){ tempPieces[pieceCount++] = HOMEPOSTION_P1; }
				for(int i = 64; i < 68; i++){
					if(board[i] == Players.ANY_SAVE){ tempPieces[pieceCount++] = i; }
				}
				break;
			case Players.P2:
				if(board[HOMEPOSTION_P2] == Players.ANY_SAVE){ tempPieces[pieceCount++] = HOMEPOSTION_P2; }
				for(int i = 68; i < 72; i++){
					if(board[i] == Players.ANY_SAVE){ tempPieces[pieceCount++] = i; }
				}
				break;
			case Players.P3:
				if(board[HOMEPOSTION_P3] == Players.ANY_SAVE){ tempPieces[pieceCount++] = HOMEPOSTION_P3; }
				for(int i = 72; i < 76; i++){
					if(board[i] == Players.ANY_SAVE){ tempPieces[pieceCount++] = i; }
				}
				break;
			case Players.P4:
				if(board[HOMEPOSTION_P4] == Players.ANY_SAVE){ tempPieces[pieceCount++] = HOMEPOSTION_P4; }
				for(int i = 76; i < 80; i++){
					if(board[i] == Players.ANY_SAVE){ tempPieces[pieceCount++] = i; }
				}
				break;
		}
		
		//count pieces
		for(int i = 0; i < 64; i++){
			if(board[i] == player){
				if(pieceCount == 4) { throw new Exception("too many pieces for player " + player + "on field!"); }
				tempPieces[pieceCount++] = i;
			}
		}
		int [] pieces = new int[pieceCount];
		for(int i = 0; i < pieceCount; i++){
			pieces[i] = tempPieces[i];
		}
		return pieces;
	}
	
	/**
	 * This method tests if a player has all his pieces in heaven
	 * @param board the board to search on
	 * @param player the player who's heaven shall be tested
	 * @return true if this player has all pieces in heaven, false otherwise
	 */
	public boolean allPiecesInHeavenOfPlayer(byte[] board, byte player){
		return nrOfPiecesInHeavenOfPlayer(board, player) == 4;
	}
	
	/**
	 * This method counts the number of Pieces in Heaven for the given player
	 * @param board the board to search on
	 * @param player the player who's pieces shall be counted
	 * @return the number of pieces in heaven of the given player
	 */
	public int nrOfPiecesInHeavenOfPlayer(byte[] board, byte player){
		int piecesInHeaven = 0;
		for(int i = 60 + (player * 4); i < 64 + (player * 4);i++){
			if(board[i] == Players.ANY_SAVE){ piecesInHeaven++; }
		}
		return piecesInHeaven;
	}
	
	/**
	 * This method will give you an array with length 4 representing the heaven of the given player
	 * @param board the board to test
	 * @param player the player's heaven to test
	 * @return a byte array of length 4 representing the player's heaven.
	 */
	public byte[] getPiecesInHeavenOfPlayer(byte[] board, byte player){
		int j = 0;
		byte[] piecesInHeaven = new byte[4];
		for(int i = 60 + (player * 4); i < 64 + (player * 4);i++){
			piecesInHeaven[j++] = board[i];
		}
		return piecesInHeaven;
	}
	
	/**
	 * This method determines the partner of the given player
	 * @param player the player who's partner has to be determined
	 * @return the partner of the given player
	 * @throws Exception if an invalid player is given over
	 */
	public byte getPartnerForPlayer(byte player){
		switch(player){
			case Players.P1:
				return (byte) Players.P3;
			case Players.P2:
				return (byte) Players.P4;
			case Players.P3:
				return (byte) Players.P1;
			case Players.P4:
				return (byte) Players.P2;
			default:
				assert false;
				return 0;
		}
	}
	
	/**
	 * this function tells you if you can reach heaven with a given piece using the given card
	 * in this method it is NOT tested if we are going over or to a protected (heaven- or home-) field
	 * @param board the board to test
	 * @param startPos the startPosition from where you want to move
	 * @param cardToUse the card you want to use
	 * @param playerOnTurn the player who's heaven shall be reached
	 * @return true if heaven is reachable, false if not
	 */
	public boolean isHeavenReachable(byte[] board, int startPos, int cardToUse, byte playerOnTurn){
		int cardType = cardToUse % 13;
		if(cardType == 0){ cardType = 13; }
		if(cardType == Cards.HEARTS_JACK){ return false; }
		if(board[startPos] <= 0 || board[startPos] > 5){ return false; }
		if(isAHomePosition(startPos) && board[startPos] == Players.ANY_SAVE){ return false; }
		if(playerOnTurn != board[startPos] && board[startPos] != Players.ANY_SAVE ){ return false; }
		
		int targetPos = startPos + cardType;
		if(startPos < 64 && playerOnTurn == Players.P1){
			targetPos = (startPos + cardType) % 64;
		}
		
		return heavenReachabilityTest(startPos, targetPos, playerOnTurn);
	}
	
	/**
	 * this function tells you if you can reach heaven with an ace played as 11
	 * in this method it is NOT tested if we are going over or to a protected (heaven- or home-) field
	 * @param board the board to test
	 * @param startPos the startPosition from where you want to move
	 * @param playerOnTurn the player who's heaven shall be reached
	 * @return true if heaven is reachable, false if not
	 */
	public boolean isHeavenReachableForAceAsEleven(byte[] board, int startPos, byte playerOnTurn){
		if(board[startPos] <= 0 || board[startPos] > 5){ return false; }
		if(isAHomePosition(startPos) && board[startPos] == Players.ANY_SAVE){ return false; }
		if(playerOnTurn != board[startPos] && !isProtectedPieceMine(board, startPos, playerOnTurn) ){ return false; }
		
		
		int targetPos = startPos + 11;
		if(startPos < 64 && playerOnTurn == Players.P1){
			targetPos = (startPos + 11) % 64;
		}
		
		return heavenReachabilityTest(startPos, targetPos, playerOnTurn);
	}
	
	/**
	 * private method that tests if the targetPos would be in heaven
	 * @param startPos the start position (needed to ensure that not going backwards to heaven)
	 * @param targetPos the target position
	 * @return true if heaven will be reached, false if not
	 */
	private boolean heavenReachabilityTest(int startPos, int targetPos, byte playerOnTurn){
		if(startPos >= 64){
			switch(playerOnTurn){
				case Players.P1:
					return (startPos >= 64 && startPos <= 68 && targetPos <= 68 && targetPos > startPos);
				case Players.P2:
					return (startPos >= 68 && startPos <= 72 && targetPos <= 72 && targetPos > startPos);
				case Players.P3:
					return (startPos >= 72 && startPos <= 76 && targetPos <= 76 && targetPos > startPos);
				case Players.P4:
					return (startPos >= 76 && startPos <= 80 && targetPos <= 80 && targetPos > startPos);
			}
		}else{
			//home field p1 (0)
			if(playerOnTurn == Players.P1 && targetPos <= HOMEPOSTION_P1 + 4 && targetPos > 0 && ((startPos < 64 && startPos > 48) || startPos == 0)){
				return true;
			}
			//homefield p2 (16)
			if(playerOnTurn == Players.P2 && targetPos <= HOMEPOSTION_P2 + 4 && targetPos > 16 && startPos <= 16 && startPos > 0){
				return true;
			}
			//homefield p3 (32)
			if(playerOnTurn == Players.P3 && targetPos <= HOMEPOSTION_P3 + 4 && targetPos > 32 && startPos <= 32 && startPos > 16){
				return true;
			}
			//homefield p4 (48)
			if(playerOnTurn == Players.P4 && targetPos <= HOMEPOSTION_P4 + 4 && targetPos > 48 && startPos <= 48 && startPos > 32){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method that gives you the target Position in Heaven
	 * in this method it is NOT tested if we are going over or to a protected (heaven- or home-) field
	 * @param board the board to test
	 * @param startPos the starting position where you want to move from
	 * @param cardToUse the card you want to use
	 * @param playerOnTurn the player who's heaven shall be reached
	 * @return -1 if heaven is not reachable, position in heaven otherwise
	 */
	public int getTargetPosInHeaven(byte[] board, int startPos, int cardToUse, byte playerOnTurn){
		int cardType = cardToUse % 13;
		if(cardType == 0){ cardType = 13; }
		if(!isHeavenReachable(board, startPos, cardToUse, playerOnTurn)){ return -1; }
		
		int targetPos = startPos + cardType;
		if(startPos < 64 && playerOnTurn == Players.P1){
			targetPos = (startPos + cardType) % 64;
		}
		if(startPos >= 64){
			switch(playerOnTurn){
				case Players.P1:
					if(targetPos > 67) { return -1; }
					break;
				case Players.P2:
					if(startPos < 68 || targetPos > 71) { return -1; }
					break;
				case Players.P3:
					if(startPos < 72 || targetPos > 75) { return -1; }
					break;
				case Players.P4:
					if(startPos < 76 || targetPos > 79) { return -1; }
					break;
				default:
					assert false;
			}
		}
		
		return startPos >= 64 ? targetPos : heavenTargetPosition(targetPos, playerOnTurn);
	}
	
	/**
	 * Method that gives you the target Position in Heaven for an ace as eleven
	 * in this method it is NOT tested if we are going over or to a protected (heaven- or home-) field
	 * @param board the board to test
	 * @param startPos the starting position where you want to move from
	 * @param playerOnTurn the player who's heaven shall be reached
	 * @return -1 if heaven is not reachable, position in heaven otherwise
	 */
	public int getTargetPosInHeavenForAceAsEleven(byte[] board, int startPos, byte playerOnTurn){
		if(!isHeavenReachableForAceAsEleven(board, startPos, playerOnTurn)){ return -1; }
		
		int targetPos = startPos + 11;
		if(startPos < 64 && playerOnTurn == Players.P1){
			targetPos = (startPos + 11) % 64;
		}
		
		return targetPos >= 64 ? targetPos : heavenTargetPosition(targetPos, playerOnTurn);
	}
	
	/**
	 * private method that calculates the targetPosition in Heaven for a given targetPosition (not in heaven)
	 * !!only call after doing isHeavenReachable Test!!
	 * @param targetPos the targetPosition
	 * @param playerOnTurn the player on turn
	 * @return -1 if wrong player is given, otherwise position in heaven.
	 */
	private int heavenTargetPosition(int targetPos, byte playerOnTurn){
		int targetPosInHeaven = -1;
		//targetPosInHeaven = targetPos (see above) - HOMEPOS_PX + firstHeavenField_PX - 1
		switch(playerOnTurn){
			case Players.P1:
				targetPosInHeaven = targetPos - HOMEPOSTION_P1 + 64 - 1;
				break;
			case Players.P2:
				targetPosInHeaven = targetPos - HOMEPOSTION_P2 + 68 - 1;
				break;
			case Players.P3:
				targetPosInHeaven = targetPos - HOMEPOSTION_P3 + 72 - 1;
				break;
			case Players.P4:
				targetPosInHeaven = targetPos - HOMEPOSTION_P4 + 76 - 1;
				break;
			default:
				targetPosInHeaven = -1;
				break;
		}
		return targetPosInHeaven;
	}
	
	/**
	 * Method to test if a protected piece on a position is owned by the given player
	 * @param board the board to test on
	 * @param position the position to test
	 * @param player the player to consider as owner
	 * @return true if the piece is from the given player, false otherwise
	 */
	public boolean isProtectedPieceMine(byte[] board, int position, byte player){
		if(board[position] != Players.ANY_SAVE) { return false; }
		switch(player){
			case Players.P1:
				if(position == HOMEPOSTION_P1) { return true; }
				if(position >= 64 && position < 68){ return true; }
				break;
			case Players.P2:
				if(position == HOMEPOSTION_P2) { return true; }
				if(position >= 68 && position < 72){ return true; }
				break;
			case Players.P3:
				if(position == HOMEPOSTION_P3) { return true; }
				if(position >= 72 && position < 76){ return true; }
				break;
			case Players.P4:
				if(position == HOMEPOSTION_P4) { return true; }
				if(position >= 76 && position < 80){ return true; }
				break;
		}
		return false;
	}
	
	public int modulo(int dividend, int divisor){
		return (dividend + divisor) % divisor; 
	}
	
	
}
