package csc130.pente.team3;

public interface BoardGUIInterface {
	
	/**
	 * Called when board sends information to the boardGUI
	 * @param player
	 * @param positionX
	 * @param positionY
	 */
	void sendInfo(Player player, int positionX, int positionY);
	
	void clearStones(int positionX1, int positionY1, int positionX2, int positionY2, Player player);
	
	void playerWon(Player player);

	void updateTimers(Player player);

	/**
	 * Displays "tessera" or "tria" when acquired
	 * @param text
	 * @param player
	 */
	void updateInARowLabel(String text, Player player);

	/**
	 * Displays text when illegal move has been attempted
	 * @param text
	 */
	void updateNotificationLabel(String text );
}
