package intelliDOG.ai.framework;

/**
 * This class is containing the rules of the dog.
 */
public final class Rules {

	private static final int HOMEPOSTION_P1 = 0;
	private static final int HOMEPOSTION_P2 = 16;
	private static final int HOMEPOSTION_P3 = 32;
	private static final int HOMEPOSTION_P4 = 48;
	
	private static Rules instance;
	
	private Rules(){
		
	}
	
	/**
	 * get the only instance of the Rules class
	 * This is the thread-safe but yet performant implementation of a singleton.
	 * @return return an instance of the Rules class
	 */
	public synchronized static Rules getInstance() {
        if (instance == null) {
        	synchronized (Rules.class) {
				if(instance == null){
					instance = new Rules();
				}
			}
        }
        return instance;
    }
	
	/**
	 * Method to test if a planned move is rules conform (for all cards except: ace, jack, four (and seven, if splitted))
	 * @param board the actual situation
	 * @param cardToUse the card that will be used
	 * @param startPos the position of the piece to be moved
	 * @param playerOnTurn the player who's on turn
	 * @param forHeaven saying whether player wants to go to heaven with this move or not
	 * @return true if the move is rules conform, false otherwise
	 */
	public boolean isRulesConform(byte[] board, int cardToUse, int startPos, byte playerOnTurn, boolean forHeaven){
		int cardType = cardToUse % 13;
		if(cardType == 0) { cardType = 13; }
		int[] pieces, partnerPieces;
		//test if cardType is one of the specified
		if(cardType == Cards.HEARTS_ACE || cardType == Cards.HEARTS_JACK || cardType == Cards.HEARTS_FOUR){
			return false;
		}
		int homefield, partnerHomefield;
		byte partner;
		try{
			pieces = getPiecesInGameForPlayer(board, playerOnTurn);
			homefield = getHomePositionForPlayer(playerOnTurn);
			partner = getPartnerForPlayer(playerOnTurn);
			partnerHomefield = getHomePositionForPlayer(partner);
			partnerPieces = getPiecesInGameForPlayer(board, partner);
		}catch(Exception ex){
			return false;
		}
		//wants to go to heaven from his home field before moving the piece
		if(forHeaven && startPos == homefield && board[homefield] == Players.ANY_SAVE) return false;
		
		//if the start position == -1 we wan't to take a new piece to the board
		if(startPos == -1){
			if((cardType == Cards.HEARTS_ACE || cardType == Cards.HEARTS_KING || cardToUse == Cards.JOKER)
					&& !isProtected(board, homefield) && pieces.length != 4){
			//if((cardType == Cards.HEARTS_ACE || cardType == Cards.HEARTS_KING) && targetPos == homefield && !isProtected(board, homefield) && pieces.length != 4){ 
				return true;
			}
			if((cardType == Cards.HEARTS_ACE || cardType == Cards.HEARTS_KING || cardToUse == Cards.JOKER) 
					&& allPiecesInHeavenOfPlayer(board, playerOnTurn) 
					&& !isProtected(board, partnerHomefield) 
					&& partnerPieces.length != 4 ) { return true; }
			return false;
		}
		int targetPos = 0;
		if(forHeaven){
			targetPos = getTargetPosInHeaven(board, startPos, cardToUse, playerOnTurn);
			if(targetPos == -1) { return false; }
		}else{
			//targetPos will be startPos + cardValue
			if(startPos >= 64){ return false; }
			targetPos = (startPos + cardType) % 64;
		}
		
		//test if the right players piece will be moved
		if(board[startPos] != playerOnTurn && !isProtectedPieceMine(board, startPos, playerOnTurn)){
			if(!(allPiecesInHeavenOfPlayer(board, playerOnTurn) && (board[startPos] == partner || isProtectedPieceMine(board, startPos, partner))))
			return false;
		}
		
		//targetPos is occupied by protected piece
		if(isProtected(board, targetPos)){ return false; }
		
		//going over a homefield (that could be protected) and card is not jack!
		//home field p1 (0)
		if(((targetPos < 16 && targetPos > 0) || (targetPos >= 64 && targetPos <= 67)) && startPos < 64 && startPos > 48){
			if(isProtected(board, HOMEPOSTION_P1)){ return false; }
		}
		//homefield p2 (16)
		if(((targetPos < 32 && targetPos > 16) || (targetPos >= 68 && targetPos <= 71)) && startPos < 16 && startPos > 0){
			if(isProtected(board, HOMEPOSTION_P2)){ return false; }
		}
		//homefield p3 (32)
		if(((targetPos < 48 && targetPos > 32) || (targetPos >= 72 && targetPos <= 75)) && startPos < 32 && startPos > 16){
			if(isProtected(board, HOMEPOSTION_P3)){ return false; }
		}
		//homefield p4 (48)
		if(((targetPos < 64 && targetPos > 48) || (targetPos >= 76 && targetPos <= 79)) && startPos < 48 && startPos > 32){
			if(isProtected(board, HOMEPOSTION_P4)){ return false; }
		}
		
		//targetPos is in heaven
		if(targetPos >= 64){
			//player 1
			if(playerOnTurn == Players.P1){
				if((startPos != 0 && (startPos <= 48 || startPos >= 68)) || targetPos >= 68){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 64 ? startPos + 1 : 64;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(cardType == Cards.HEARTS_ACE && (targetPos - startPos) + 1 == 11){ return true; }
				if(startPos == 0) { return targetPos - 63 == cardType;}
				if(startPos < 64) { return targetPos - startPos + 1 == cardType; }
				return Math.abs(targetPos - startPos)  == cardType;
			}
			//player 2
			if(playerOnTurn == Players.P2){
				if((startPos > 16 && startPos < 68) || startPos >= 72 || targetPos < 68 || targetPos >= 72){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 68 ? startPos + 1 : 68;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(startPos < 64){
					if(cardType == Cards.HEARTS_ACE && (targetPos - 51 - startPos) == 11){ return true; }
					return Math.abs(targetPos - 51 - startPos) == cardType;
				}
			}
			//player 3
			if(playerOnTurn == Players.P3){
				if(startPos < 16 || (startPos > 32 && startPos < 72) || startPos >= 76 || targetPos < 72 || targetPos >= 76){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 72 ? startPos + 1 : 72;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(startPos < 64){
					if(cardType == Cards.HEARTS_ACE && (targetPos - 39 - startPos) == 11){ return true; }
					return Math.abs(targetPos - 39 - startPos) == cardType;
				}
			}
			//player 4
			if(playerOnTurn == Players.P4){
				if(startPos < 32 || (startPos > 48 && startPos < 76) || startPos >= 80 || targetPos < 76 || targetPos >= 80){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 76 ? startPos + 1 : 76;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(startPos < 64){
					if(cardType == Cards.HEARTS_ACE && (targetPos - 27 - startPos) == 11){ return true; }
					return Math.abs(targetPos - 27 - startPos) == cardType;
				}
			}
		}
		return modulo(targetPos - startPos, 64) == cardType;
	}
	
	
	/**
	 * Method to test if a planned move is rules conform (for ace, jack and four)
	 * @param board the actual situation
	 * @param cardToUse the card that will be used
	 * @param startPos the position of the piece to be moved
	 * @param targetPos the position where the piece shall be placed after the move
	 * @param playerOnTurn the player who's on turn
	 * @param forHeaven saying whether player wants to go to heaven with this move or not
	 * @return true if the move is rules conform, false otherwise
	 */
	public boolean isRulesConform(byte[] board, int cardToUse, int startPos, int targetPos, byte playerOnTurn, boolean forHeaven){
		int cardType = cardToUse % 13;
		if(cardType == 0) { cardType = 13; }
		int[] pieces, partnerPieces;
		//test if cardType is one of the specified
		if(cardType != Cards.HEARTS_ACE && cardType != Cards.HEARTS_JACK && cardType != Cards.HEARTS_FOUR){
			return false;
		}
		if(forHeaven && targetPos < 64){ return false; }
		if(!forHeaven && (targetPos >= 64 || startPos >= 64)){ return false; }
		int homefield, partnerHomefield;
		byte partner;
		try{
			pieces = getPiecesInGameForPlayer(board, playerOnTurn);
			homefield = getHomePositionForPlayer(playerOnTurn);
			partner = getPartnerForPlayer(playerOnTurn);
			partnerHomefield = getHomePositionForPlayer(partner);
			partnerPieces = getPiecesInGameForPlayer(board, partner);
		}catch(Exception ex){
			return false;
		}
		//wants to go to heaven from his home field before moving the piece
		if(forHeaven && startPos == homefield && board[homefield] == Players.ANY_SAVE) return false;
		
		//test if starting with ace
		if(startPos == -1){
			if(cardType == Cards.HEARTS_ACE && targetPos == homefield && !isProtected(board, targetPos) && pieces.length != 4){ return true; }
			if(cardType == Cards.HEARTS_ACE && allPiecesInHeavenOfPlayer(board, playerOnTurn) 
					&& targetPos == partnerHomefield && !isProtected(board, targetPos) 
					&& partnerPieces.length != 4 ) { return true; }
			return false;
		}
		//test if the right players piece will be moved
		if(board[startPos] != playerOnTurn && !isProtectedPieceMine(board, startPos, playerOnTurn)){
			if(!(allPiecesInHeavenOfPlayer(board, playerOnTurn) && (board[startPos] == partner || isProtectedPieceMine(board, startPos, partner))))
			return false;
		}
		
		//card is a jack
		if(cardType == Cards.HEARTS_JACK){
			//target is a heaven field or a protected field
			if(startPos > 63 || targetPos > 63 || isProtected(board, targetPos)) { return false; }
			//the piece on the startPos is ours and the piece on the targetPos is from another player 
			//!!ASSUMING THAT OWN PROTECTED PIECES CAN ALSO NOT BEEING EXCHANGED WITH A JACK!!
			if(board[startPos] == playerOnTurn && board[targetPos] != playerOnTurn &&  board[targetPos] > 0 && board[targetPos] < 5){
				return true;
			}
			return false;
		}
		//targetPos is occupied by protected piece
		if(isProtected(board, targetPos)){ return false; }
		
		//going over a homefield (that could be protected) and card is not jack!
		//home field p1 (0)
		if(((targetPos < 16 && targetPos > 0) || (targetPos >= 64 && targetPos <= 67)) && (startPos < 64 && startPos > 48)){
			if(isProtected(board, HOMEPOSTION_P1)){ return false; }
		}
		//homefield p2 (16)
		if(((targetPos < 32 && targetPos > 16) || (targetPos >= 68 && targetPos <= 71)) && startPos < 16 && startPos > 0){
			if(isProtected(board, HOMEPOSTION_P2)){ return false; }
		}
		//homefield p3 (32)
		if(((targetPos < 48 && targetPos > 32) || (targetPos >= 72 && targetPos <= 75)) && startPos < 32 && startPos > 16){
			if(isProtected(board, HOMEPOSTION_P3)){ return false; }
		}
		//homefield p4 (48)
		if(((targetPos < 64 && targetPos > 48) || (targetPos >= 76 && targetPos <= 79)) && startPos < 48 && startPos > 32){
			if(isProtected(board, HOMEPOSTION_P4)){ return false; }
		}
		
		//going possibly backwards over protected homefield!
		if(cardType == Cards.HEARTS_FOUR && (targetPos < startPos || (startPos <= 4 && targetPos > 59 && targetPos < 64)) && startPos < 64){
			if(startPos - targetPos != 4 && Math.abs(startPos - targetPos) != 60){ return false; }
			if(startPos <= 4 && startPos >= 1){ return !isProtected(board, HOMEPOSTION_P1); }
			if(startPos <= 20 && startPos >= 17){ return !isProtected(board, HOMEPOSTION_P2); }
			if(startPos <= 36 && startPos >= 33){ return !isProtected(board, HOMEPOSTION_P3); }
			if(startPos <= 52 && startPos >= 49){ return !isProtected(board, HOMEPOSTION_P4); }
			if((startPos - targetPos == 4 || Math.abs(startPos - targetPos) == 60) && targetPos < 64) { return true; }
		}
		//going backwards out of heaven! NOT Possible!!
		
		//targetPos is in heaven
		if(targetPos >= 64){
			//player 1
			if(playerOnTurn == Players.P1){
				if((startPos != 0 && (startPos <= 48 || startPos >= 68)) || targetPos >= 68){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 64 ? startPos + 1 : 64;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(cardType == Cards.HEARTS_ACE && (targetPos - startPos) + 1 == 11){ return true; }
				if(startPos == 0) { return targetPos - 63 == cardType;}
				if(startPos < 64) { return targetPos - startPos + 1 == cardType; }
				return Math.abs(targetPos - startPos)  == cardType;
			}
			//player 2
			if(playerOnTurn == Players.P2){
				if((startPos > 16 && startPos < 68) || startPos >= 72 || targetPos < 68 || targetPos >= 72){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 68 ? startPos + 1 : 68;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(startPos < 64){
					if(cardType == Cards.HEARTS_ACE && (targetPos - 51 - startPos) == 11){ return true; }
					return Math.abs(targetPos - 51 - startPos) == cardType;
				}
			}
			//player 3
			if(playerOnTurn == Players.P3){
				if(startPos < 16 || (startPos > 32 && startPos < 72) || startPos >= 76 || targetPos < 72 || targetPos >= 76){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 72 ? startPos + 1 : 72;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(startPos < 64){
					if(cardType == Cards.HEARTS_ACE && (targetPos - 39 - startPos) == 11){ return true; }
					return Math.abs(targetPos - 39 - startPos) == cardType;
				}
			}
			//player 4
			if(playerOnTurn == Players.P4){
				if(startPos < 32 || (startPos > 48 && startPos < 76) || startPos >= 80 || targetPos < 76 || targetPos >= 80){ return false; }
				//test if going over protected heaven field
				int start = startPos >= 76 ? startPos + 1 : 76;
				for(int i = start; i < targetPos; i++){
					if(board[i] == Players.ANY_SAVE) { return false; }
				}
				if(startPos < 64){
					if(cardType == Cards.HEARTS_ACE && (targetPos - 27 - startPos) == 11){ return true; }
					return Math.abs(targetPos - 27 - startPos) == cardType;
				}
			}
		}
		int typeTest = modulo(targetPos - startPos, 64);
		return (typeTest == 11 && cardType == Cards.HEARTS_ACE) ? true : typeTest == cardType;
	}
	
	
	
	/**
	 * Method to test if a planned split move with seven is rules conform
	 * @param board the actual situation
	 * @param startPos array of the position of the piece to be moved
	 * @param targetPos array of the position where the piece shall be placed after the move
	 * @param playerOnTurn the player who's on turn
	 * @param forHeaven array saying whether player wants to go to heaven with this move or not
	 * @return true if the move is rules conform, false otherwise
	 */
	public boolean isRulesConform(byte[] board, int [] startPos, int [] targetPos, byte playerOnTurn, boolean [] forHeaven){
		//test params
		if(startPos.length != targetPos.length || startPos.length != forHeaven.length || startPos.length > 7){ return false; }
		
		byte[] boardToTest = new byte[board.length];
		for(int i = 0; i < board.length; i++){
			boardToTest[i] = board[i];
		}
		
		int sum = 0;
		int[] diff = new int [startPos.length];
		for(int i = 0; i < startPos.length; i++){
			if(targetPos[i] >= 64 && startPos[i] < 64){
				switch(playerOnTurn){
					case Players.P1:
						diff[i] = targetPos[i] - startPos[i] + 1;
						sum += diff[i];
						break;
					case Players.P2:
						diff[i] = targetPos[i] - 67 + 16 - startPos[i];
						sum += diff[i];
						break;
					case Players.P3:
						diff[i] = targetPos[i] - 71 + 32 - startPos[i]; 
						sum += diff[i];
						break;
					case Players.P4:
						diff[i] = targetPos[i] - 75 + 48 - startPos[i];
						sum += diff[i];
						break;
				}
			}
			else if(targetPos[i] < startPos[i]){
				diff[i] = 64 - startPos[i] + targetPos[i];
				sum += diff[i];
			}
			else{
				diff[i] = targetPos[i] - startPos[i];
				sum += diff[i];
			}
		}
		if(sum != 7) { return false; }
		
		boolean rulesConform = true;
		for(int i = 0; i < startPos.length; i++){
			boolean doubleMove = false;
			for(int j = 0; j < i; j++){
				if(targetPos[j] == startPos[i]){
					doubleMove = true;
				}
			}
			if(doubleMove) { return false; }
			if(diff[i] == 0) { return false; }
			if(diff[i] == 1 || diff[i] == 4){
				rulesConform = isRulesConform(boardToTest, diff[i], startPos[i], targetPos[i], playerOnTurn, forHeaven[i]);
			}
			else{
				rulesConform = isRulesConform(boardToTest, diff[i], startPos[i], playerOnTurn, forHeaven[i]);
			}
			if(!rulesConform) return false;
			if(startPos[i] > targetPos[i]){
				for(int j = startPos[i]; j < 64; j++){
					boardToTest[j] = 0;
				}
				for(int j = 0; j < targetPos[i]; j++){
					boardToTest[j] = 0;
				}
			}else{
				for(int j = startPos[i]; j < targetPos[i]; j++){
					boardToTest[j] = 0;
				}
			}
			boardToTest[targetPos[i]] = playerOnTurn;
			if(targetPos[i] >= 64){ boardToTest[targetPos[i]] = Players.ANY_SAVE; }
		}
		//must be true here
		return rulesConform;
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
	
	/*
	/**
	 * Method to test if a Homefield is blocked by the respective player's piece.
	 * This is the case whenever a piece of a player is on it's own home field
	 * it has not to be protected to block other pieces from getting through.
	 * @param board the actual situation
	 * @param position the position on the board to test (Must be a home field)
	 * @return true if this field is blocked, false otherwise
	 /
	public boolean isBlocked(byte[] board, int position){
		assert !(position >= board.length || position < 0);
		assert isAHomePosition(position);
		switch(position){
		case HOMEPOSTION_P1:
			return board[position] == Players.P1;
		case HOMEPOSTION_P2:
			return board[position] == Players.P2;
		case HOMEPOSTION_P3:
			return board[position] == Players.P3;
		case HOMEPOSTION_P4:
			return board[position] == Players.P4;
		default:
			return false;
		}
	}*/
	
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
				//assert false; //FIXME:find the caller that released the assert here!
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
	public int [] getPiecesInGameForPlayer(byte[] board, byte player){
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
				assert (pieceCount != 4);
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
	private boolean isProtectedPieceMine(byte[] board, int position, byte player){
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
	
	/**
	 * this is an implementation of the modulo operator (%) which also covers
	 * negative numbers
	 * @param dividend
	 * @param divisor
	 * @return dividend % divisor (but negatives aware) 
	 */
	private int modulo(int dividend, int divisor){
		return (dividend + divisor) % divisor; 
	}
	
}