package csc130.pente.team3;

public class Controller {

	// Center position of the current board
	private static int centerX;
	private static int centerY;
	
	private Board board;

	public Controller(Board board) {
		this.board = board;
	}

	public boolean validateMove(Player player, int turnNumber, int positionX, int positionY, Stone[][] locations) {
		centerX = (locations.length / 2);
		centerY =  (locations[0].length / 2);
		
		if (positionX > locations.length - 1 || positionX < 0 || positionY > locations[0].length - 1 || positionY < 0) {
			return false;
		}
		if (locations[positionX][positionY].getOwner() != null) {
			return false;
		}

		// if first player and turn # = 2 follow the rule to place 3 or more further
		// away.
		if (player.isFirstPlayer() && turnNumber == 3) {
			if ((positionX == centerX - 2 && positionY == centerY - 2)
					|| (positionY == centerY + 2 && positionX == centerX + 2)
					|| (positionX == centerX - 2 && positionY == centerY + 2)
					|| (positionX == centerX + 2 && positionY == centerY - 2)) {
				if (board.getBgi() != null) {						
					board.notifyIllegalMove("Illegal Move");
				}
				return false;
			} else if ((positionX >= centerX + 3 || positionX <= centerX - 3)
					|| (positionY <= centerY - 3 || positionY >= centerY + 3)) {
				if (board.getBgi() != null) {						
					board.notifyIllegalMove("");
				}
				return true;
			} else {
				if (board.getBgi() != null) {						
					board.notifyIllegalMove("Illegal Move");
				}
				return false;
			}
		}
		// if first player turn # = 1 place in center only.
		else if (player.isFirstPlayer() && turnNumber == 1) {
			if (locations.length % 2 == 0 && locations[0].length % 2 == 0) {
				if (positionX == (locations.length / 2) - 1 && positionY == (locations[0].length / 2) - 1) {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("");
					}
					return true;
				} else {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("Illegal Move");
					}
					return false;
				}
			} else if (locations.length % 2 != 0 && locations[0].length % 2 == 0) {
				if (positionX == Math.round(locations.length / 2) && positionY == (locations[0].length / 2) - 1) {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("");
					}
					return true;
				} else {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("Illegal Move");
					}
					return false;
				}
			} else if (locations.length % 2 == 0 && locations[0].length % 2 != 0) {
				if (positionX == (locations.length / 2) - 1 && positionY == Math.round(locations[0].length / 2)) {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("");
					}
					return true;
				} else {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("Illegal Move");
					}
					return false;
				}
			} else {
				if (positionX == Math.round(locations.length / 2) && positionY == Math.round(locations[0].length / 2)) {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("");
					}
					return true;
				} else {
					if (board.getBgi() != null) {						
						board.notifyIllegalMove("Illegal Move");
					}
					return false;
				}
			}
		}
		checkForCapture(positionX, positionY, player, locations);
		if (checkForMaxStoneCaptureWin(player)) {
			board.playerWon(player);
		}
		if (checkForInARow(positionX, positionY, player, locations, 5) != null) {
			board.playerWon(player);
		}else if (checkForInARow(positionX, positionY, player, locations, 4) != null) {
			board.updateLabelForInARow("Tessera!", player);
		}else if (checkForInARow(positionX, positionY, player, locations, 3) != null) {
			board.updateLabelForInARow("Tria", player);
		}
		if (board.getBgi() != null) {			
			board.updateLabelForInARow("", player);
		}
		
		return true;
	}

	/**
	 * Checks for adjacent stones
	 * @param positionX
	 * @param positionY
	 * @param player
	 * @param locations
	 * @param inARow
	 * @return the player if the number of stones in a row are found, null otherwise
	 */
	public Player checkForInARow(int positionX, int positionY, Player player, Stone[][] locations, int inARow) {
		Player winner = null;
		int adjacentPieces = 0;
		boolean validStone = true;
		int loops = 1;
		// checking left/right
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionX + loops <= locations.length - 1) {
				if (locations[positionX + loops][positionY].getOwner() != null
						&& locations[positionX + loops][positionY].getOwner().equals(player)) {
					adjacentPieces++;
					if (adjacentPieces == inARow - 1) {
						winner = player;
						return player;
					}
					loops++;
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}
		loops = 1;
		validStone = true;
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionX - loops >= 0) {
				if (locations[positionX - loops][positionY].getOwner() != null
						&& locations[positionX - loops][positionY].getOwner().equals(player)) {
					adjacentPieces++;
					if (adjacentPieces == inARow - 1) {
						winner = player;
						return player;
					}
					loops++;
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}
		adjacentPieces = 0;
		validStone = true;
		loops = 1;
		// Check for vertical win
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionY + loops <= locations[0].length - 1) {
				if (locations[positionX][positionY + loops].getOwner() != null
						&& locations[positionX][positionY + loops].getOwner().equals(player)) {
					adjacentPieces++;
					if (adjacentPieces == inARow - 1) {
						winner = player;
						return player;
					}
					loops++;
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}
		loops = 1;
		validStone = true;
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionY - loops >= 0) {
				if (locations[positionX][positionY - loops].getOwner() != null
						&& locations[positionX][positionY - loops].getOwner().equals(player)) {
					adjacentPieces++;
					if (adjacentPieces == inARow - 1) {
						winner = player;
						return player;
					}
					loops++;
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}

		adjacentPieces = 0;
		validStone = true;
		loops = 1;
		// check for diagonal win (bot-right to top left)
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionY + loops <= locations[0].length - 1) {
				if (positionX + loops <= locations.length - 1) {
					if (locations[positionX + loops][positionY + loops].getOwner() != null
							&& locations[positionX + loops][positionY + loops].getOwner().equals(player)) {
						adjacentPieces++;
						if (adjacentPieces == inARow - 1) {
							winner = player;
							return player;
						}
						loops++;
					} else {
						validStone = false;
					}
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}
		loops = 1;
		validStone = true;
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionY - loops >= 0) {
				if (positionX - loops >= 0) {
					if (locations[positionX - loops][positionY - loops].getOwner() != null
							&& locations[positionX - loops][positionY - loops].getOwner().equals(player)) {
						adjacentPieces++;
						if (adjacentPieces == inARow - 1) {
							winner = player;
							return player;
						}
						loops++;
					} else {
						validStone = false;
					}
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}

		adjacentPieces = 0;
		validStone = true;
		loops = 1;
		// check for diagonal win (bot-left to top-right)
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionY - loops >= 0) {
				if (positionX + loops <= locations.length - 1) {
					if (locations[positionX + loops][positionY - loops].getOwner() != null
							&& locations[positionX + loops][positionY - loops].getOwner().equals(player)) {
						adjacentPieces++;
						if (adjacentPieces == inARow - 1) {
							winner = player;
							return player;
						}
						loops++;
					} else {
						validStone = false;
					}
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}
		loops = 1;
		validStone = true;
		while (validStone && adjacentPieces != inARow - 1) {
			if (positionY + loops <= locations[0].length - 1) {
				if (positionX - loops >= 0) {
					if (locations[positionX - loops][positionY + loops].getOwner() != null
							&& locations[positionX - loops][positionY + loops].getOwner().equals(player)) {
						adjacentPieces++;
						if (adjacentPieces == inARow - 1) {
							winner = player;
							return player;
						}
						loops++;
					} else {
						validStone = false;
					}
				} else {
					validStone = false;
				}
			} else {
				validStone = false;
			}
		}

		return null;
	}

	public void checkForCapture(int positionX, int positionY, Player player, Stone[][] locations) {
		if (positionX >= 3) {
			// do checks left
			if (locations[positionX - 3][positionY].getOwner() != null
					&& locations[positionX - 3][positionY].getOwner().equals(player)) {
				if (locations[positionX - 2][positionY].getOwner() != null
						&& !locations[positionX - 2][positionY].getOwner().equals(player)
						&& locations[positionX - 1][positionY].getOwner() != null
						&& !locations[positionX - 1][positionY].getOwner().equals(player)) {
					removeStones(locations[positionX - 2][positionY], locations[positionX - 1][positionY], player);
				}
			}
		}
		if (positionX <= locations.length - 4) {
			// do checks right
			if (locations[positionX + 3][positionY].getOwner() != null
					&& locations[positionX + 3][positionY].getOwner().equals(player)) {
				if (locations[positionX + 2][positionY].getOwner() != null
						&& !locations[positionX + 2][positionY].getOwner().equals(player)
						&& locations[positionX + 1][positionY].getOwner() != null
						&& !locations[positionX + 1][positionY].getOwner().equals(player)) {
					removeStones(locations[positionX + 2][positionY], locations[positionX + 1][positionY], player);
				}
			}
		}
		if (positionY <= locations[0].length - 4) {
			// do checks down
			if (locations[positionX][positionY + 3].getOwner() != null
					&& locations[positionX][positionY + 3].getOwner().equals(player)) {
				if (locations[positionX][positionY + 2].getOwner() != null
						&& !locations[positionX][positionY + 2].getOwner().equals(player)
						&& locations[positionX][positionY + 1].getOwner() != null
						&& !locations[positionX][positionY + 1].getOwner().equals(player)) {
					removeStones(locations[positionX][positionY + 2], locations[positionX][positionY + 1], player);
				}
			}
		}
		if (positionY >= 3) {
			// do checks up
			if (locations[positionX][positionY - 3].getOwner() != null
					&& locations[positionX][positionY - 3].getOwner().equals(player)) {
				if (locations[positionX][positionY - 2].getOwner() != null
						&& !locations[positionX][positionY - 2].getOwner().equals(player)
						&& locations[positionX][positionY - 1].getOwner() != null
						&& !locations[positionX][positionY - 1].getOwner().equals(player)) {
					removeStones(locations[positionX][positionY - 2], locations[positionX][positionY - 1], player);
				}
			}
		}
		if (positionY <= locations[0].length - 4 && positionX <= locations.length - 4) {
			// do checks down right
			if (locations[positionX + 3][positionY + 3].getOwner() != null
					&& locations[positionX + 3][positionY + 3].getOwner().equals(player)) {
				if (locations[positionX + 1][positionY + 1].getOwner() != null
						&& !locations[positionX + 1][positionY + 1].getOwner().equals(player)
						&& locations[positionX + 2][positionY + 2].getOwner() != null
						&& !locations[positionX + 2][positionY + 2].getOwner().equals(player)) {
					removeStones(locations[positionX + 1][positionY + 1], locations[positionX + 2][positionY + 2],
							player);
				}
			}
		}
		if (positionY <= locations[0].length - 4 && positionX >= 3) {
			// do checks down left
			if (locations[positionX - 3][positionY + 3].getOwner() != null
					&& locations[positionX - 3][positionY + 3].getOwner().equals(player)) {
				if (locations[positionX - 1][positionY + 1].getOwner() != null
						&& !locations[positionX - 1][positionY + 1].getOwner().equals(player)
						&& locations[positionX - 2][positionY + 2].getOwner() != null
						&& !locations[positionX - 2][positionY + 2].getOwner().equals(player)) {
					removeStones(locations[positionX - 1][positionY + 1], locations[positionX - 2][positionY + 2],
							player);
				}
			}
		}
		if (positionY >= 3 && positionX >= 3) {
			// do checks up left
			if (locations[positionX - 3][positionY - 3].getOwner() != null
					&& locations[positionX - 3][positionY - 3].getOwner().equals(player)) {
				if (locations[positionX - 1][positionY - 1].getOwner() != null
						&& !locations[positionX - 1][positionY - 1].getOwner().equals(player)
						&& locations[positionX - 2][positionY - 2].getOwner() != null
						&& !locations[positionX - 2][positionY - 2].getOwner().equals(player)) {
					removeStones(locations[positionX - 1][positionY - 1], locations[positionX - 2][positionY - 2],
							player);
				}
			}
		}
		if (positionY >= 3 && positionX <= locations.length - 4) {
			// do checks up right
			if (locations[positionX + 3][positionY - 3].getOwner() != null
					&& locations[positionX + 3][positionY - 3].getOwner().equals(player)) {
				if (locations[positionX + 1][positionY - 1].getOwner() != null
						&& !locations[positionX + 1][positionY - 1].getOwner().equals(player)
						&& locations[positionX + 2][positionY - 2].getOwner() != null
						&& !locations[positionX + 2][positionY - 2].getOwner().equals(player)) {
					removeStones(locations[positionX + 1][positionY - 1], locations[positionX + 2][positionY - 2],
							player);
				}
			}
		}
	}

	private void removeStones(Stone stone, Stone stone2, Player player) {
		stone.setOwner(null);
		stone2.setOwner(null);
		player.addCapturedStones(2);
		this.board.stonesRemoved(stone.getLocationX(), stone.getLocationY(), stone2.getLocationX(),
				stone2.getLocationY(), player);
	}

	public boolean checkForMaxStoneCaptureWin(Player player) {
		if (player.getCapturedStonesValue() >= 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Controls turn logic
	 * @param player1
	 * @param player2
	 * @param turnNumber
	 * @param posX
	 * @param posY
	 * @param locations
	 * @return true if legal move, false otherwise
	 */
	public boolean turn(Player player1, Player player2, int turnNumber, int posX, int posY, Stone[][] locations) {
		// player 2 turn
		if (turnNumber % 2 == 0) {
			return validateMove(player2, turnNumber, posX, posY, locations);
		} else { // player 1 turn
			return validateMove(player1, turnNumber, posX, posY, locations);
		}

	}

}
