package intelliDOG.ai.framework;

import java.util.Arrays;

/**
 * This class represents a move that can be made on a <class>Board</class> or <class>BotBoard</class>
 * It contains all information that is used to get from one gamestate to the next
 * or vice versa.
 */
public class Move {

	private int card;
	private byte[] positions;
	private byte[] hits;
	private boolean[] wasProtected;
	
	public Move(int card, byte[] positions, byte[] hits, boolean[] wasProtected){
		this.card = card;
		assert positions.length <= 14;
		assert hits.length <= 14;
		this.positions = positions;
		this.hits = hits;
		this.wasProtected = wasProtected;
	}
	
	public Move(int card, byte[] positions){
		this(card, positions, new byte[]{}, new boolean[]{});
	}

	/**
	 * @return the positions
	 */
	public byte[] getPositions() {
		return positions;
	}

	/**
	 * @return the hits
	 */
	public byte[] getHits() {
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setHits(byte[] hits) {
		assert hits.length <= 14;
		this.hits = hits;
	}

	/**
	 * @return the card
	 */
	public int getCard() {
		return card;
	}

	/**
	 * @return the wasProtected
	 */
	public boolean[] getwasProtected() {
		return wasProtected;
	}

	/**
	 * @param wasProtected the wasProtected to set
	 */
	public void setWasProtected(boolean[] wasProtected) {
		assert wasProtected.length <= 7;
		this.wasProtected = wasProtected;
	}
	
	/**
	 * this method is used to get a copy of a move for a card
	 * with the same type but other suited (same suit works also but makes no sense)
	 * @param card the card that shall be used for the copy
	 * @return the copy of itself with the card given over
	 */
	public Move copy(int card){
		assert card != 100 && card % 13 == this.card % 13;
		
		return new Move(card, Arrays.copyOf(positions, positions.length), 
				Arrays.copyOf(hits, hits.length), 
				Arrays.copyOf(wasProtected, wasProtected.length));
	}
	
}
