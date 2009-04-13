package intelliDOG.ai.evaluators;

public interface Evaluator
{
	/**
	 * Evaluates a game step from a state into another. 
	 * @param actualState the actual gamestate
	 * @param targetState the desired gamestate
	 * @param player the player which turn  it is
	 * @return an integer which represents the value of this game step
	 */
	public int evaluate(byte[] actualState, byte[] targetState, byte player);
	/**
	 * same as above, but the players card can also be considered
	 * @param actualState the actual gamestate
	 * @param targetState the desired gamestate
	 * @param player the player which turn it is
	 * @param card the card on the hand of this player
	 * @return an integer which represents the value of this gamestep
	 */
	public int evaluate(byte[] actualState, byte[] targetState, byte player, int card[]);
	/**
	 * Only the state itself will be evaluated. 
	 * @param targetState the desired gamestate
	 * @param player the player which turn it is
	 * @return an integer which represents the value of this gamestep
	 */
	public int evaluate(byte[] targetState, byte player);
	/**
	 * same as above, but the players card can also be considered
	 * @param targetState the desired gamestate
	 * @param player the player which turn it is
	 * @param card the card on the hand of this player
	 * @return an integer which represents the value of this gamestep
	 */
	public int evaluate(byte[] targetState, byte player, int card[], float fading);

}
