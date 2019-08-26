package csc130.pente.team3;

public interface BoardInterface {

	/**
	 * Called when a button on the boardGUI is clicked
	 * @param positionX
	 * @param positionY
	 */
	void buttonClicked(int positionX, int positionY);

	void saveBoard(String fileName);
	
	void updateTurn(boolean firstTurnOfGame);
}
