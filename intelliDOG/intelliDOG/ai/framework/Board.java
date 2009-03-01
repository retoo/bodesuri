package intelliDOG.ai.framework;

import intelliDOG.ai.utils.DebugMsg;

/**
 * This class represents and holds the board state in a game.
 *
 */
public class Board {

	private byte [] board;
	private byte playerOnTurn;
	
	private DebugMsg msg = DebugMsg.getInstance();
	
	
	public Board(){
		this.board = new byte[80];
		this.playerOnTurn = Players.P1;
	}
	
	public Board(byte[] board, byte playerOnTurn){
		assert board.length == 80;
		assert playerOnTurn > 0 && playerOnTurn < 5;
		this.board = board;
		this.playerOnTurn = playerOnTurn;
	}
	
	/**
	 * This method will execute the move given over on the board
	 * @param move The <class>Move</class> to execute
	 */
	public void makeMove(Move move)
	{
		// First perform the move on the board
		byte[] positions = move.getPositions();
		assert this.board[positions[1]] != Players.ANY_SAVE;
		
		if(move.getCard() % 13 == Cards.HEARTS_JACK){
			move.setHits(new byte[]{this.board[positions[1]], positions[1]});
			byte oldPiece = this.board[positions[0]];
			this.board[positions[0]] = this.board[positions[1]];
			this.board[positions[1]] = oldPiece;
			
		}else if(move.getCard() % 13 == Cards.HEARTS_SEVEN){
			int hitPos = 0;
			byte[] hits = new byte[14];
			boolean [] wasProtected = new boolean[positions.length / 2];
			for(int i = 0; i < positions.length; i+=2){
				byte oldPiece = board[positions[i]];
				if(positions[i] < 64 && board[positions[i]] == Players.ANY_SAVE){
					switch(positions[i]){
						//HOMEPOS_P1
						case 0:
							oldPiece = Players.P1;
							break;
						//HOMEPOS_P2
						case 16:
							oldPiece = Players.P2;
							break;
						//HOMEPOS_P3
						case 32:
							oldPiece = Players.P3;
							break;
						//HOMEPOS_P4
						case 48:
							oldPiece = Players.P4;
							break;
					}
				}
				if(positions[i+1] >= 64){
					int homePosition = Rules.getInstance().getHomePositionForPlayer(oldPiece);
					for(int j = positions[i]; j <= homePosition; j++){
						if(this.board[j] != 0){
							hits[hitPos++] = this.board[j];
							hits[hitPos++] = (byte)j;
							this.board[j] = 0;
						}
					}
				}else if(positions[i] < positions[i+1]){
					for(int j = positions[i] + 1; j <= positions[i+1]; j++){
						if(this.board[j] != 0){
							hits[hitPos++] = this.board[j];
							hits[hitPos++] = (byte)j;
							this.board[j] = 0;
							if(j == positions[i+1]){
								this.board[j] = j > 63 ? Players.ANY_SAVE : oldPiece;
							}
						}
					}
				}else{
					for(int j = positions[i] + 1; j <= 63; j++){
						if(this.board[j] != 0){
							hits[hitPos++] = this.board[j];
							hits[hitPos++] = (byte)j;
							this.board[j] = 0;
						}
					}
					for(int j = 0; j <= positions[i+1]; j++){
						if(this.board[j] != 0){
							hits[hitPos++] = this.board[j];
							hits[hitPos++] = (byte)j;
							this.board[j] = 0;
						}
					}
				}
				wasProtected[i/2] = this.board[positions[i]] == Players.ANY_SAVE;
				this.board[positions[i]] = 0;
				this.board[positions[i+1]] = positions[i+1] > 63 ? Players.ANY_SAVE : oldPiece;
			}
			move.setWasProtected(wasProtected);
			byte[] hitsDynamic = new byte[hitPos];
			for(int i = 0; i < hitPos; i++){
				hitsDynamic[i] = hits[i];
			}
			move.setHits(hitsDynamic);
		}else {
			byte oldPiece = 0;
			if(positions[0] != -1){
				oldPiece = board[positions[0]];
				if(positions[0] < 64 && board[positions[0]] == Players.ANY_SAVE){
					switch(positions[0]){
						//HOMEPOS_P1
						case 0:
							oldPiece = Players.P1;
							break;
						//HOMEPOS_P2
						case 16:
							oldPiece = Players.P2;
							break;
						//HOMEPOS_P3
						case 32:
							oldPiece = Players.P3;
							break;
						//HOMEPOS_P4
						case 48:
							oldPiece = Players.P4;
							break;
					}
				}
				move.setWasProtected(new boolean[] {this.board[positions[0]] == Players.ANY_SAVE});
				this.board[positions[0]] = 0;
			}
			if(this.board[positions[1]] != Players.EMPTY){
				move.setHits(new byte[] {this.board[positions[1]], positions[1]});
			}
			this.board[positions[1]] = (positions[0] == -1 || positions[1] > 63) ? Players.ANY_SAVE : oldPiece;
		}
	}
	
	/**
	 * @return the board
	 */
	public byte[] getBoard() {
		return board;
	}


	/**
	 * @return the playerOnTurn
	 */
	public byte getPlayerOnTurn() {
		return playerOnTurn;
	}


	/**
	 * @param playerOnTurn the playerOnTurn to set
	 */
	public void setPlayerOnTurn(byte playerOnTurn) {
		this.playerOnTurn = playerOnTurn;
	}
	
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer(); 
		
		for(int i=0; i<board.length; i++) 
			buffer.append((board[i] + ",")); 
	
		buffer.append("\n");
		return buffer.toString(); 
	}
	
}
