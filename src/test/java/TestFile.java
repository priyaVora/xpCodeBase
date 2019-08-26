import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import csc130.pente.team3.Board;
import csc130.pente.team3.Controller;
import csc130.pente.team3.Player;
import csc130.pente.team3.Stone;

public class TestFile {

	private enum Direction {
		UP, DOWN, LEFT, RIGHT, DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT, DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT
	}

	private final int FIRST_PLAYER_UNRESTRICTED_TURN = 5;

	@Test
	public void setPlayerName() {
		Player newPerson = new Player("Priya", true);
		String nameOfUser = "Priya";
		assertTrue(newPerson.getName().equals(nameOfUser));
	}

	@Test
	public void testOverlappingPlacements() {
		int xDimension = 5, yDimension = 5;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[][] presetBoard = new Stone[xDimension][yDimension];
		for (int i = 0; i < presetBoard.length; i++) {
			for (int j = 0; j < presetBoard[0].length; j++) {
				presetBoard[i][j] = new Stone();
			}
		}

		Stone stone = new Stone();
		stone.setOwner(gameBoard.getPlayer1());
		presetBoard[0][0] = stone;
		gameBoard.setLocations(presetBoard);
		boolean isValid = testPlayerMove(gameBoard, 0, 0, gameBoard.getPlayer2(), 2);
		assertFalse(isValid);
	}

	@Test
	public void testHorizontalNegativeOutOfBounds() {
		Board gameBoard = createBoard(5, 5);
		Controller controller = new Controller(gameBoard);
		boolean isValidMove = controller.validateMove(gameBoard.getPlayer1(), FIRST_PLAYER_UNRESTRICTED_TURN, -1, 0,
				gameBoard.getLocations());
		assertFalse(isValidMove);
	}

	@Test
	public void testVerticalNegativeOutOfBounds() {
		Board gameBoard = createBoard(5, 5);
		Controller controller = new Controller(gameBoard);
		boolean isValidMove = controller.validateMove(gameBoard.getPlayer1(), FIRST_PLAYER_UNRESTRICTED_TURN, 0, -1,
				gameBoard.getLocations());
		assertFalse(isValidMove);
	}

	@Test
	public void testHorizontalPositiveOutofBounds() {
		int xDimension = 5, yDimension = 5;
		Board gameBoard = createBoard(xDimension, yDimension);
		boolean isValidMove = testPlayerMove(gameBoard, xDimension + 1, 0, gameBoard.getPlayer1(),
				FIRST_PLAYER_UNRESTRICTED_TURN);
		assertFalse(isValidMove);
	}

	@Test
	public void testVerticalPositiveOutOfBounds() {
		int xDimension = 5, yDimension = 5;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);
		boolean isValidMove = controller.validateMove(gameBoard.getPlayer1(), FIRST_PLAYER_UNRESTRICTED_TURN, 0,
				yDimension + 1, gameBoard.getLocations());
		assertFalse(isValidMove);
	}

	@Test
	public void testInvalidFirstPlayerFirstMove() {
		int xDimension = 5, yDimension = 5;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);
		boolean isValidMove = controller.validateMove(gameBoard.getPlayer1(), 1, 0, 0, gameBoard.getLocations());
		assertFalse(isValidMove);
	}

	@Test
	public void testValidFirstPlayerFirstMove() {
		int xDimension = 5, yDimension = 5;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);
		boolean isValidMove = controller.validateMove(gameBoard.getPlayer1(), 1, findCenter(xDimension),
				findCenter(yDimension), gameBoard.getLocations());
		assertTrue(isValidMove);
	}

	@Test
	public void testFirstPlayerInvalidSecondMove() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		boolean isValid = testPlayerMove(gameBoard, findCenter(xDimension) + 1, findCenter(yDimension),
				gameBoard.getPlayer1(), 3);
		assertFalse(isValid);
	}

	@Test
	public void testFirstPlayerValidSecondMove() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		boolean isValid = testPlayerMove(gameBoard, findCenter(xDimension) + 3, findCenter(yDimension),
				gameBoard.getPlayer1(), 3);
		assertTrue(isValid);
	}

	@Test
	public void testCaptureVerticallyDownToUp() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[] firstPlayerInitPos = new Stone[1];
		firstPlayerInitPos[0] = new Stone();
		firstPlayerInitPos[0].setLocationX(5);
		firstPlayerInitPos[0].setLocationY(3);

		Stone[] secondPlayerInitPos = new Stone[2];
		for (int i = 0; i < secondPlayerInitPos.length; i++) {
			secondPlayerInitPos[i] = new Stone();
			secondPlayerInitPos[i].setLocationX(5);
			secondPlayerInitPos[i].setLocationY(i + 4);
		}

		Stone finalPlacement = new Stone();
		finalPlacement.setLocationX(5);
		finalPlacement.setLocationY(6);
		finalPlacement.setOwner(gameBoard.getPlayer1());

		boolean validTaken = testCaptures(gameBoard, firstPlayerInitPos, secondPlayerInitPos, finalPlacement,
				xDimension, yDimension);
		assertTrue(validTaken);
	}

	@Test
	public void testCaptureVerticallyUpToDown() {
		int xDimension = 9;
		int yDimension = 9;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);

		Stone[][] presetStones = new Stone[xDimension][yDimension];
		for (int i = 0; i < xDimension; i++) {
			for (int j = 0; j < yDimension; j++) {
				presetStones[i][j] = new Stone();
			}
		}

		Stone firstPlayerRightStone = new Stone();
		firstPlayerRightStone.setOwner(gameBoard.getPlayer1());
		presetStones[5][6] = firstPlayerRightStone;

		for (int i = 0; i < 2; i++) {
			Stone secondPlayerStones = new Stone();
			secondPlayerStones.setOwner(gameBoard.getPlayer2());
			presetStones[5][i + 4] = secondPlayerStones;
		}

		Stone firstPlayerLeftStone = new Stone();
		firstPlayerLeftStone.setOwner(gameBoard.getPlayer1());
		presetStones[5][3] = firstPlayerLeftStone;

		gameBoard.setLocations(presetStones);

		controller.checkForCapture(5, 3, gameBoard.getPlayer1(), presetStones);
		for (int i = 0; i < 2; i++) {
			boolean wereStonesTaken = gameBoard.getLocations()[5][i + 4].getOwner() == null;
			assertTrue(wereStonesTaken);
		}
	}

	@Test
	public void testCaptureHorizontallyRightToLeft() {
		int xDimension = 9;
		int yDimension = 9;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);

		Stone[][] presetStones = new Stone[xDimension][yDimension];
		for (int i = 0; i < xDimension; i++) {
			for (int j = 0; j < yDimension; j++) {
				presetStones[i][j] = new Stone();
			}
		}

		Stone firstPlayerLeftStone = new Stone();
		firstPlayerLeftStone.setOwner(gameBoard.getPlayer1());
		presetStones[3][5] = firstPlayerLeftStone;

		for (int i = 0; i < 2; i++) {
			Stone secondPlayerStones = new Stone();
			secondPlayerStones.setOwner(gameBoard.getPlayer2());
			presetStones[i + 4][5] = secondPlayerStones;
		}

		Stone firstPlayerRightStone = new Stone();
		firstPlayerRightStone.setOwner(gameBoard.getPlayer1());
		presetStones[6][5] = firstPlayerRightStone;

		gameBoard.setLocations(presetStones);

		controller.checkForCapture(6, 5, gameBoard.getPlayer1(), presetStones);
		for (int i = 0; i < 2; i++) {
			boolean wereStonesTaken = gameBoard.getLocations()[i + 4][5].getOwner() == null;
			assertTrue(wereStonesTaken);
		}

	}

	@Test
	public void testCaptureDiagonallyLeftToRightDown() {
		int xDimension = 9;
		int yDimension = 9;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);

		Stone[][] presetStones = new Stone[xDimension][yDimension];
		for (int i = 0; i < xDimension; i++) {
			for (int j = 0; j < yDimension; j++) {
				presetStones[i][j] = new Stone();
			}
		}

		Stone firstPlayerLeftStone = new Stone();
		firstPlayerLeftStone.setOwner(gameBoard.getPlayer1());
		presetStones[3][5] = firstPlayerLeftStone;

		for (int i = 0; i < 2; i++) {
			Stone secondPlayerStones = new Stone();
			secondPlayerStones.setOwner(gameBoard.getPlayer2());
			presetStones[i + 4][i + 6] = secondPlayerStones;
		}

		Stone firstPlayerRightStone = new Stone();
		firstPlayerRightStone.setOwner(gameBoard.getPlayer1());
		presetStones[6][8] = firstPlayerRightStone;

		gameBoard.setLocations(presetStones);

		controller.checkForCapture(6, 8, gameBoard.getPlayer1(), presetStones);
		for (int i = 0; i < 2; i++) {
			boolean wereStonesTaken = gameBoard.getLocations()[i + 4][i + 6].getOwner() == null;
			assertTrue(wereStonesTaken);
		}

	}

	@Test
	public void testCaptureDiagonallyLeftToRightUp() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[] firstPlayerInitPos = new Stone[1];
		firstPlayerInitPos[0] = new Stone();
		firstPlayerInitPos[0].setLocationX(7);
		firstPlayerInitPos[0].setLocationY(7);

		Stone[] secondPlayerInitPos = new Stone[2];
		for (int i = 0; i < secondPlayerInitPos.length; i++) {
			secondPlayerInitPos[i] = new Stone();
			secondPlayerInitPos[i].setLocationX(6 - i);
			secondPlayerInitPos[i].setLocationY(6 - i);
		}

		Stone finalPlacement = new Stone();
		finalPlacement.setLocationX(4);
		finalPlacement.setLocationY(4);
		finalPlacement.setOwner(gameBoard.getPlayer1());

		boolean validTaken = testCaptures(gameBoard, firstPlayerInitPos, secondPlayerInitPos, finalPlacement,
				xDimension, yDimension);
		assertTrue(validTaken);
	}

	@Test
	public void testCaptureDiagonallyRightToLeftUp() {
		int xDimension = 9;
		int yDimension = 9;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);

		Stone[][] presetStones = new Stone[xDimension][yDimension];
		for (int i = 0; i < xDimension; i++) {
			for (int j = 0; j < yDimension; j++) {
				presetStones[i][j] = new Stone();
			}
		}

		Stone firstPlayerLeftStone = new Stone();
		firstPlayerLeftStone.setOwner(gameBoard.getPlayer1());
		presetStones[6][8] = firstPlayerLeftStone;

		for (int i = 0; i < 2; i++) {
			Stone secondPlayerStones = new Stone();
			secondPlayerStones.setOwner(gameBoard.getPlayer2());
			presetStones[5 - i][7 - i] = secondPlayerStones;
		}

		Stone firstPlayerRightStone = new Stone();
		firstPlayerRightStone.setOwner(gameBoard.getPlayer1());
		presetStones[3][5] = firstPlayerRightStone;

		gameBoard.setLocations(presetStones);

		controller.checkForCapture(3, 5, gameBoard.getPlayer1(), presetStones);
		for (int i = 0; i < 2; i++) {
			boolean wereStonesTaken = gameBoard.getLocations()[5 - i][7 - i].getOwner() == null;
			assertTrue(wereStonesTaken);
		}
	}

	@Test
	public void testCaptureDiagonallyRightToLeftDown() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[] firstPlayerInitPos = new Stone[1];
		firstPlayerInitPos[0] = new Stone();
		firstPlayerInitPos[0].setLocationX(4);
		firstPlayerInitPos[0].setLocationY(4);

		Stone[] secondPlayerInitPos = new Stone[2];
		for (int i = 0; i < secondPlayerInitPos.length; i++) {
			secondPlayerInitPos[i] = new Stone();
			secondPlayerInitPos[i].setLocationX(3 - i);
			secondPlayerInitPos[i].setLocationY(5 + i);
		}

		Stone finalPlacement = new Stone();
		finalPlacement.setLocationX(1);
		finalPlacement.setLocationY(7);
		finalPlacement.setOwner(gameBoard.getPlayer1());

		boolean validTaken = testCaptures(gameBoard, firstPlayerInitPos, secondPlayerInitPos, finalPlacement,
				xDimension, yDimension);
		assertTrue(validTaken);
	}

	@Test
	public void testNonCapture() {
		// puts 2 pieces in between 2 enemy pieces
		// aforementioned pieces should not be captured

		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[] firstPlayerInitPos = new Stone[1];
		firstPlayerInitPos[0] = new Stone();
		firstPlayerInitPos[0].setLocationY(0);
		firstPlayerInitPos[0].setLocationX(1);

		Stone[] secondPlayerInitPos = new Stone[2];
		for (int i = 0; i < secondPlayerInitPos.length; i++) {
			Stone secondPlayerPlacement = new Stone();
			secondPlayerPlacement.setLocationY(0);
			secondPlayerPlacement.setLocationX(3 * i);
			secondPlayerInitPos[i] = secondPlayerPlacement;
		}

		Stone finalPlacement = new Stone();
		finalPlacement.setLocationY(0);
		finalPlacement.setLocationX(2);
		finalPlacement.setOwner(gameBoard.getPlayer1());
		boolean wereStonesTaken = testCaptures(gameBoard, firstPlayerInitPos, secondPlayerInitPos, finalPlacement,
				xDimension, yDimension);
		assertFalse(wereStonesTaken);
	}

	@Test
	public void testFiveCapturesWinningCondition() {
		// if player has five captures does the game end?
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Controller controller = new Controller(gameBoard);
		Stone[][] locations = new Stone[xDimension][yDimension];
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations[0].length; j++) {
				locations[i][j] = new Stone();
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {
				locations[3 * j][0].setLocationX(3 * j);
				locations[3 * j][0].setLocationY(0);
				locations[3 * j][0].setOwner(gameBoard.getPlayer1());
			}

			for (int j = 0; j < 2; j++) {
				locations[1 + j][0].setLocationX(1 + j);
				locations[1 + j][0].setLocationY(0);
				locations[1 + j][0].setOwner(gameBoard.getPlayer2());
			}

			controller.checkForCapture(3, 0, gameBoard.getPlayer1(), locations);
		}

		boolean wasCaptureWin = controller.checkForMaxStoneCaptureWin(gameBoard.getPlayer1());
		assertTrue(wasCaptureWin);
	}

	@Test
	public void doubleTestCapture() { // test for double captures
		assertTrue(testRandomDirections(2));
	}

	@Test
	public void tripleCaptureTest() {
		assertTrue(testRandomDirections(3));
	}

	@Test
	public void quadrupleTestCapture() {
		assertTrue(testRandomDirections(4));
	}

	@Test
	public void quintupleTestCapture() {
		assertTrue(testRandomDirections(5));
	}

	@Test
	public void sextupleTestCapture() {
		assertTrue(testRandomDirections(6));
	}

	@Test
	public void septupleCapture() {
		assertTrue(testRandomDirections(7));
	}

	@Test
	public void octupleTestCapture() {
		assertTrue(testRandomDirections(8));
	}

	@Test
	public void testPlacementAfterCapture() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[][] locations = new Stone[xDimension][yDimension];
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations[0].length; j++) {
				locations[i][j] = new Stone();
			}
		}

		for (int i = 0; i < 2; i++) {
			locations[3 * i][0].setLocationX(3 * i);
			locations[3 * i][0].setLocationY(0);
			locations[3 * i][0].setOwner(gameBoard.getPlayer1());
		}

		for (int i = 0; i < 2; i++) {
			locations[1 + i][0].setLocationX(1 + i);
			locations[1 + i][0].setLocationY(0);
			locations[1 + i][0].setOwner(gameBoard.getPlayer2());
		}

		Controller controller = new Controller(gameBoard);
		controller.checkForCapture(3, 0, gameBoard.getPlayer1(), locations);

		for (int i = 0; i < 2; i++) {
			boolean isValidPlacement = controller.validateMove(gameBoard.getPlayer1(), FIRST_PLAYER_UNRESTRICTED_TURN,
					1 + i, 0, locations);
			assertTrue(isValidPlacement);
		}
	}

	@Test
	public void declareTriaUp() {
		boolean isTria = testStonesInARow(Direction.UP, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaDiagonalUpRight() {
		boolean isTria = testStonesInARow(Direction.DIAGONAL_UP_RIGHT, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaRight() {
		boolean isTria = testStonesInARow(Direction.RIGHT, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaDiagonalDownRight() {
		boolean isTria = testStonesInARow(Direction.DIAGONAL_DOWN_RIGHT, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaDown() {
		boolean isTria = testStonesInARow(Direction.DOWN, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaDownLeft() {
		boolean isTria = testStonesInARow(Direction.DIAGONAL_DOWN_LEFT, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaLeft() {
		boolean isTria = testStonesInARow(Direction.LEFT, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTriaDiagonalUpLeft() {
		boolean isTria = testStonesInARow(Direction.DIAGONAL_UP_LEFT, 3);
		assertTrue(isTria);
	}

	@Test
	public void declareTesseraUP() {
		boolean isTessera = testStonesInARow(Direction.UP, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraDiagonalUpRight() {
		boolean isTessera = testStonesInARow(Direction.DIAGONAL_UP_RIGHT, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraRight() {
		boolean isTessera = testStonesInARow(Direction.RIGHT, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraDiagonalDownRight() {
		boolean isTessera = testStonesInARow(Direction.DIAGONAL_DOWN_RIGHT, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraDown() {
		boolean isTessera = testStonesInARow(Direction.DOWN, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraDiagonalDownLeft() {
		boolean isTessera = testStonesInARow(Direction.DIAGONAL_DOWN_LEFT, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraLeft() {
		boolean isTessera = testStonesInARow(Direction.LEFT, 4);
		assertTrue(isTessera);
	}

	@Test
	public void declareTesseraDiagonalUpLeft() {
		boolean isTessera = testStonesInARow(Direction.DIAGONAL_UP_LEFT, 4);
		assertTrue(isTessera);
	}

	@Test
	public void testWinnerUp() {
		boolean isWinner = testStonesInARow(Direction.UP, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerDiagonalUpRight() {
		boolean isWinner = testStonesInARow(Direction.DIAGONAL_UP_RIGHT, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerRight() {
		boolean isWinner = testStonesInARow(Direction.RIGHT, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerDiagonalDownRight() {
		boolean isWinner = testStonesInARow(Direction.DIAGONAL_DOWN_RIGHT, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerDown() {
		boolean isWinner = testStonesInARow(Direction.DOWN, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerUpDiagonalDownLeft() {
		boolean isWinner = testStonesInARow(Direction.DIAGONAL_DOWN_LEFT, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerLeft() {
		boolean isWinner = testStonesInARow(Direction.LEFT, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testWinnerDiagonalUpLeft() {
		boolean isWinner = testStonesInARow(Direction.DIAGONAL_UP_LEFT, 5);
		assertTrue(isWinner);
	}

	@Test
	public void testLoading() {
		Board b = new Board();
		Board gameBoard = createBoard(19, 19);
		gameBoard.saveBoard("load");

		b.loadBoard("load.ser");

		for (int i = 0; i < b.getLocations().length; i++) {
			for (int j = 0; j < b.getLocations()[0].length; j++) {
				assertTrue(b.getLocations()[i][j].getLocationX() == gameBoard.getLocations()[i][j].getLocationX());
				assertTrue(b.getLocations()[i][j].getLocationY() == gameBoard.getLocations()[i][j].getLocationY());
				assertTrue(b.getLocations()[i][j].getOwner() == gameBoard.getLocations()[i][j].getOwner());
			}
		}

	}

	@Test
	public void saveBoard() {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		String fileName = "PlayerWon";

		gameBoard.saveBoard(fileName);
		File f = new File("saves/" + fileName + ".ser");
		assertTrue(f.exists());

	}

	private boolean testStonesInARow(Direction directionType, int numOfStonesInRow) {
		int xDimension = 19, yDimension = 19;
		Board gameBoard = createBoard(xDimension, yDimension);
		Stone[][] locations = gameBoard.getLocations();
		ArrayList<Stone> playerStones = new ArrayList<>();

		Controller control = new Controller(gameBoard);
		int xCenter = findCenter(xDimension), yCenter = findCenter(yDimension);

		playerStones.clear();

		switch (directionType) {
		case UP:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter);
				stonePlaced.setLocationY(yCenter - i);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}
			break;

		case DIAGONAL_UP_RIGHT:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter + i);
				stonePlaced.setLocationY(yCenter - i);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}

			break;

		case RIGHT:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter + i);
				stonePlaced.setLocationY(yCenter);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}

			break;

		case DIAGONAL_DOWN_RIGHT:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter + i);
				stonePlaced.setLocationY(yCenter + i);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}
			break;

		case DOWN:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter);
				stonePlaced.setLocationY(yCenter + i);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}
			break;

		case DIAGONAL_DOWN_LEFT:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter - i);
				stonePlaced.setLocationY(yCenter + i);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}
			break;

		case LEFT:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter - i);
				stonePlaced.setLocationY(yCenter);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}
			break;

		case DIAGONAL_UP_LEFT:
			for (int i = 0; i < numOfStonesInRow; i++) {
				Stone stonePlaced = new Stone();
				stonePlaced.setLocationX(xCenter - i);
				stonePlaced.setLocationY(yCenter - i);
				stonePlaced.setOwner(gameBoard.getPlayer1());
				playerStones.add(stonePlaced);
			}
			break;
		}

		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations[0].length; j++) {
				locations[i][j] = new Stone();
			}
		}

		for (Stone s : playerStones) {
			locations[s.getLocationX()][s.getLocationY()] = s;
		}
		Player playerInARow = control.checkForInARow(playerStones.get(playerStones.size() - 1).getLocationX(),
				playerStones.get(playerStones.size() - 1).getLocationY(), gameBoard.getPlayer1(), locations,
				numOfStonesInRow);

		return playerInARow != null;
	}

	private Board createBoard(int xDimension, int yDimension) {
		Player firstPlayer = new Player("First", true);
		Player secondPlayer = new Player("Second", false);
		return new Board(xDimension, yDimension, firstPlayer, secondPlayer);
	}

	private boolean testPlayerMove(Board gameBoard, int xPos, int yPos, Player player, int turnNumber) {
		Controller controller = new Controller(gameBoard);

		// this is so that the center gets set
		controller.validateMove(gameBoard.getPlayer1(), 1, findCenter(gameBoard.getLocations().length),
				findCenter(gameBoard.getLocations()[0].length), gameBoard.getLocations());

		return controller.validateMove(player, turnNumber, xPos, yPos, gameBoard.getLocations());
	}

	private int findCenter(int dimension) {
		return Math.round(dimension / 2);
	}

	private boolean testCaptures(Board gameBoard, Stone[] firstPlayerInitPlacements, Stone[] secondPlayerInitPlacements,
			Stone finalPlacement, int xDimension, int yDimension) {
		Controller controller = new Controller(gameBoard);

		Stone[][] presetStones = new Stone[xDimension][yDimension];
		for (int i = 0; i < xDimension; i++) {
			for (int j = 0; j < yDimension; j++) {
				presetStones[i][j] = new Stone();
			}
		}

		for (int i = 0; i < firstPlayerInitPlacements.length; i++) {
			int xPos = firstPlayerInitPlacements[i].getLocationX();
			int yPos = firstPlayerInitPlacements[i].getLocationY();
			presetStones[xPos][yPos] = firstPlayerInitPlacements[i];
			presetStones[xPos][yPos].setOwner(gameBoard.getPlayer1());
		}

		for (int i = 0; i < secondPlayerInitPlacements.length; i++) {
			int yPos = secondPlayerInitPlacements[i].getLocationY();
			int xPos = secondPlayerInitPlacements[i].getLocationX();
			presetStones[xPos][yPos] = secondPlayerInitPlacements[i];
			presetStones[xPos][yPos].setOwner(gameBoard.getPlayer2());
		}

		int finalXPos = finalPlacement.getLocationX();
		int finalYPos = finalPlacement.getLocationY();
		presetStones[finalXPos][finalYPos] = finalPlacement;

		gameBoard.setLocations(presetStones);
		controller.checkForCapture(finalPlacement.getLocationX(), finalPlacement.getLocationY(),
				finalPlacement.getOwner(), presetStones);
		presetStones = gameBoard.getLocations();

		for (int i = 0; i < secondPlayerInitPlacements.length; i++) {
			Stone stoneToCheck = secondPlayerInitPlacements[i];
			boolean wereStonesTaken = presetStones[stoneToCheck.getLocationX()][stoneToCheck.getLocationY()]
					.getOwner() == null;
			if (!wereStonesTaken)
				return false;
		}
		return true;
	}

	private boolean testRandomDirections(int numOfDirections) {
		ArrayList<Direction> directionsToTest = new ArrayList<>();
		Direction[] directions = Direction.values();
		Random gen = new Random();
		for (int i = 0; i < 50; i++) {
			directionsToTest.clear();
			for (int j = 0; j < numOfDirections; j++) {
				while (true) {
					Direction validDirection = directions[gen.nextInt(directions.length)];
					if (directionsToTest.contains(validDirection))
						continue;
					directionsToTest.add(validDirection);
					break;
				}
			}
			boolean validCaptures = testMultipleDirectionalCaptures(directionsToTest, 19, 19);
			if (!validCaptures)
				return false;
		}
		return true;
	}

	private boolean testMultipleDirectionalCaptures(ArrayList<Direction> directionsToTest, int xDimension,
			int yDimension) {
		ArrayList<Stone> firstPlayerInitPos = new ArrayList<Stone>();
		ArrayList<Stone> secondPlayerInitPos = new ArrayList<Stone>();
		Board gameBoard = createBoard(xDimension, yDimension);

		for (Direction d : directionsToTest) {
			Stone firstPlayerEndStone = null;
			switch (d) {
			case UP:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension));
					stonePlaced.setLocationY(findCenter(yDimension) - 1 - i);
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension));
				firstPlayerEndStone.setLocationY(findCenter(yDimension) - 3);
				break;

			case DIAGONAL_UP_RIGHT:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension) + 1 + i);
					stonePlaced.setLocationY(findCenter(yDimension) - 1 - i);
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension) + 3);
				firstPlayerEndStone.setLocationY(findCenter(yDimension) - 3);
				break;

			case RIGHT:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension) + 1 + i);
					stonePlaced.setLocationY(findCenter(yDimension));
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension) + 3);
				firstPlayerEndStone.setLocationY(findCenter(yDimension));
				break;

			case DIAGONAL_DOWN_RIGHT:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension) + 1 + i);
					stonePlaced.setLocationY(findCenter(yDimension) + 1 + i);
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension) + 3);
				firstPlayerEndStone.setLocationY(findCenter(yDimension) + 3);
				break;

			case DOWN:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension));
					stonePlaced.setLocationY(findCenter(yDimension) + 1 + i);
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension));
				firstPlayerEndStone.setLocationY(findCenter(yDimension) + 3);
				break;

			case DIAGONAL_DOWN_LEFT:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension) - 1 - i);
					stonePlaced.setLocationY(findCenter(yDimension) + 1 + i);
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension) - 3);
				firstPlayerEndStone.setLocationY(findCenter(yDimension) + 3);
				break;

			case LEFT:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension) - 1 - i);
					stonePlaced.setLocationY(findCenter(yDimension));
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension) - 3);
				firstPlayerEndStone.setLocationY(findCenter(yDimension));
				break;

			case DIAGONAL_UP_LEFT:
				for (int i = 0; i < 2; i++) {
					Stone stonePlaced = new Stone();
					stonePlaced.setLocationX(findCenter(xDimension) - 1 - i);
					stonePlaced.setLocationY(findCenter(yDimension) - 1 - i);
					secondPlayerInitPos.add(stonePlaced);
				}
				firstPlayerEndStone = new Stone();
				firstPlayerEndStone.setLocationX(findCenter(xDimension) - 3);
				firstPlayerEndStone.setLocationY(findCenter(yDimension) - 3);
				break;
			}
			firstPlayerInitPos.add(firstPlayerEndStone);
		}

		Stone finalStonePlacement = new Stone();
		finalStonePlacement.setLocationX(findCenter(xDimension));
		finalStonePlacement.setLocationY(findCenter(yDimension));
		finalStonePlacement.setOwner(gameBoard.getPlayer1());

		return testCaptures(gameBoard, firstPlayerInitPos.toArray(new Stone[firstPlayerInitPos.size()]),
				secondPlayerInitPos.toArray(new Stone[secondPlayerInitPos.size()]), finalStonePlacement, xDimension,
				yDimension);
	}
}
