import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Board {
	private Integer[][] board;
	private static Integer[][] goalState;

	private int rZero, cZero;

	public int getcZero() {
		return cZero;
	}

	public int getrZero() {
		return rZero;
	}

	/**
	 * Size of the board.
	 */
	private static int size;

	public Board() {
		rZero = cZero = 0;
		Integer[][] defaultBoard = {{1, 2, 3}, 
				{4, 8, 5}, 
				{7, 0, 6}};
		board = defaultBoard;
		setupBoard();
	}

	public Board(int n) {
		super();
		board = new Integer[n][n];
		setupBoard();
	}


	public Board(Integer[][] board) {
		super();
		this.board = board;
		setupBoard();
	}

	public Board(String[] args) throws InvalidBoardException {
		super();
		int counter = 0;
		if (Math.pow(((int) Math.sqrt(args.length)), 2) == args.length) {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[0].length; c++) {
					setSquare(r, c, args[counter++]);
				}
			}
		}
		setupBoard();
	}

	/**
	 * Called by constructors.
	 * Constructs goal state, this.size, calls isValidBoard(), sets up rZero and cZero, and checks if a zero is found.
	 */
	private final void setupBoard() {
		size = board.length;
		goalState = new Integer[size][size];

		if (!isValidBoard()) {
			try {
				throw new InvalidBoardException("Invalid board passed to setupBoard()");
			} catch (InvalidBoardException e) {
				e.printStackTrace();
			}
		}

		// setup rZero, cZero
		boolean foundZero = false;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] == 0) {
					foundZero = true;
					rZero = r;
					cZero = c;
				}
			}
		}

		if (!foundZero) {
			try {
				throw new InvalidBoardException("Invalid board configuration: zero not found.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		// Construct goal state for use in isWon()
		int counter = 1;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				goalState[r][c] = counter++;
			}
		}
		goalState[size-1][size-1] = 0;
	}

	/**
	 * Length of board.
	 * @return length of board
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns a new deep copy of this object.
	 * @return
	 */
	public Board copyBoard() {
		Integer[][] copy = new Integer[size][size];
		for (int r = 0; r < Board.size; r++) {
			for (int c = 0; c < Board.size; c++) {
				copy[r][c] = this.board[r][c];
			}
		}
		return new Board(copy);

	}

	/**
	 * calls setSquare with the parsed value String. Throws 
	 * @param row
	 * @param column
	 * @param String value
	 * @throws InvalidBoardException
	 */
	public final void setSquare(int r, int c, String value) throws NumberFormatException {
		try {
			setSquare(r, c, Integer.parseInt(value));
		} catch (Exception NumberFormatException) {
			throw new NumberFormatException(value + " is not an Integer.");
		}
	}

	/**
	 * Sets a squares value.
	 * Updates rZero and cZero if `value` == 0
	 * @param row
	 * @param column
	 * @param value
	 */
	public final void setSquare(int r, int c, int value) {
		if (value == 0) {
			rZero = r;
			cZero = c;
		}
		board[r][c] = value; 
	}


	/**
	 * Returns a new board based on `this` with the given Direction applied.
	 * The direction is not checked to be a valid move - it's assumed the direction passed
	 * is a result from getPossibleMoves()
	 * @param Direction d
	 * @return Integer[][] board
	 */
	public final Integer[][] moveDirection(Direction d) {
		Board newBoard = copyBoard();
		int r = this.rZero;
		int c = this.cZero;
		System.out.println("----------");
		System.out.println("Direction: " + d);
		switch (d) {
		case UP:
			r = r - 1;
			break;
		case DOWN:
			r = r + 1;
			break;
		case LEFT:
			c = c - 1;
			break;
		case RIGHT:
			c = c + 1;
			break;
		default:
			System.out.println("Please don't hit this");
			break;
		}
		try {
			newBoard.board = newBoard.swapSquare(r, c);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		return newBoard.getBoard();
	}

	public final Integer[][] swapSquare(int r, int c) throws InvalidMoveException {
		Board newBoard = copyBoard();
		int rDiff = Math.abs(r - rZero);
		int cDiff = Math.abs(c - cZero);
		if (rDiff > 1 || cDiff > 1) {
			throw new InvalidMoveException("Tried to swap with non-adjacent square");
		}
		switch (rDiff) {
		case 1: //going up or down
			if (cDiff == 0) {
				newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
				newBoard.setSquare(r, c, 0); // Guaranteed to be zero
			} else {
				throw new InvalidMoveException("rDiff == " + rDiff + ", cDiff == " + cDiff);
			}
			break;
		case 0: //going left or right
			if (cDiff == 1) {
				newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
				newBoard.setSquare(r, c, 0); // Guaranteed to be zero
			} else {
				throw new InvalidMoveException("rDiff == " + rDiff + ", cDiff == " + cDiff);
			}
		default:
			if (rDiff == 0 && cDiff == 0) {
				throw new InvalidMoveException("Tried to swap 0 with 0");
			} else {
				// should never reach
			}

		}
		return newBoard.getBoard();
	}

	public final void print() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}

	public final void print(Integer[][] arr) {
		for (int r = 0; r < arr.length; r++) {
			for (int c = 0; c < arr[0].length; c++) {
				System.out.print(arr[r][c] + " ");
			}
			System.out.println();
		}
	}


	public final boolean isWon() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (board[r][c] != goalState[r][c]) {
					return false;
				}
			}
		}
		return true;

	}

	private final int getValueAtSquare(int r, int c){
		return board[r][c];
	}

	private final boolean isValidBoard() {
		if (board.length != board[0].length) {
			System.out.println("Board is not valid due to mismatch in lengths");

		}
		boolean flag = false;

		for (int i = 0; i < size * size - 1; i++) {
			flag = false;

			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {

					if (board[r][c] == i) {
						flag = true;
						r = c = size; // breaks out of both loops
					}
				}

			}
			if (flag == false) { // didn't find that number
				System.out.println("Board is not valid due to duplicate input value.");
				this.print();
			}
		}
		return true;
	}

	public final void printZero() {
		System.out.println("Zero: " + rZero + ", " + cZero + " r,c");
	}

	public final Integer[][] getBoard() {
		return board;
	}

	/**
	 * Must be unique. Concatenated string.
	 */
	@Override
	public int hashCode() {
		String code = "";
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				code += board[r][c] + "";
			}
		}
		return new BigInteger(code).hashCode();
	}

	/**
	 * Returns a list of valid and possible moves for the current board objet based on zero location.
	 * @see Direction
	 * @return List<Direction> directions
	 */
	public List<Direction> getPossibleMoves() {
		int cZero = this.getcZero();
		int rZero = this.getrZero();
		List<Direction> result = new ArrayList<>();
		if (cZero == 0) { // left column
			if (rZero == 0) { //top left corner
				result.add(Direction.DOWN);
				result.add(Direction.RIGHT);
				return result;
			} else if (rZero == this.getSize() - 1) { // bottom left corner
				result.add(Direction.UP);
				result.add(Direction.RIGHT);
				return result;		
			} else { // middle rows, left column
				result.add(Direction.UP);
				result.add(Direction.DOWN);
				result.add(Direction.RIGHT);
				return result;
			}
		} else if (cZero == this.getSize() - 1) { // right column
			if (rZero == 0) { // top right corner
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				return result;
			} else if (rZero == this.getSize() - 1) { //bottom right corner
				result.add(Direction.UP);
				result.add(Direction.LEFT);
				return result;		
			} else { // middle rows of right column
				result.add(Direction.UP);
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				return result;
			}
		} else { // not left or right
			if (rZero == 0) { // top
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				result.add(Direction.RIGHT);
				return result;
			} else if (rZero == this.getSize() - 1) { // bottom
				result.add(Direction.UP);
				result.add(Direction.LEFT);
				result.add(Direction.RIGHT);
				return result;		
			} else { // all possible
				result.add(Direction.UP);
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				result.add(Direction.RIGHT);
				return result;
			}
		}
	}


	public static enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Board) {
			return Arrays.deepEquals(this.getBoard(), ((Board) other).getBoard());
		}
		return false;
	}

	class InvalidMoveException extends Exception {
		public InvalidMoveException(String message) {
			super(message);
		}
	}

	class InvalidBoardException extends Exception {
		public InvalidBoardException(String message) {
			super(message);
		}
	}

}
