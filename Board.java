import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Board {
	private Integer[][] board;
	private int rZero, cZero;

	public int getcZero() {
		return cZero;
	}

	public int getrZero() {
		return rZero;
	}

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

	public Board(String[] args) {
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

	private final void setupBoard() {
		size = board.length;
		if (!isValidBoard()) {
			System.out.println("invalid board: ");
		} else {
			//System.out.println("valid board");
		};

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
				throw new Exception("Invalid board configuration");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Length of board
	 * @return length of board
	 */
	public int getSize() {
		return size;
	}

	public Board copyBoard() {
		Integer[][] copy = new Integer[size][size];
		for (int r = 0; r < Board.size; r++) {
			for (int c = 0; c < Board.size; c++) {
				copy[r][c] = this.board[r][c];
			}
		}
		return new Board(copy);
		
	}

	public final void setSquare(int r, int c, String value) {
		try {
			setSquare(r, c, Integer.parseInt(value));
		} catch (Exception NumberFormatException) {
			System.out.println("You dun goofed. " + value + " is not an Integer.");
		}
	}

	public final void setSquare(int r, int c, int value) {
		if (value == 0) {
			rZero = r;
			cZero = c;
		}
		board[r][c] = value; 
	}

	public final Integer[][] moveDirection(Direction d) {
		Board newBoard = copyBoard();
		int r = this.rZero;
		int c = this.cZero;
		System.out.println("moveDirection: " + r + ", " + c + " r, c");
		System.out.println(d);
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
		/*newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
		newBoard.setSquare(r, c, 0); // Guaranteed to be zero*/
		try {
			System.out.println("moveDirection: " + r + ", " + c + " new r, new c");
			newBoard.board = newBoard.swapSquare(r, c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newBoard.getBoard();
	}

	public final Integer[][] swapSquare(int r, int c) throws Exception {
		System.out.println("swapSquare called with r = " + r + " and c = " + c);
		Board newBoard = copyBoard();
		int rDiff = Math.abs(r - rZero);
		int cDiff = Math.abs(c - cZero);
		System.out.print("oldZero");
		this.printZero();
		System.out.println("newZero: " + r + ", " + c);
		/*System.out.println("rDiff: " + rDiff);
		System.out.println("cDiff: " + cDiff);*/
		if (rDiff > 1 || cDiff > 1) {
			throw new Exception("Tried to swap with non-adjacent square");
		}
		switch (rDiff) {
		case 1: //going up or down
			if (cDiff == 0) {
				newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
				newBoard.setSquare(r, c, 0); // Guaranteed to be zero
			} else {
				throw new Exception("rDiff == " + rDiff + ", cDiff == " + cDiff);
			}
			break;
		case 0: //going left or right
			if (cDiff == 1) {
				newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
				newBoard.setSquare(r, c, 0); // Guaranteed to be zero
			} else {
				throw new Exception("rDiff == " + rDiff + ", cDiff == " + cDiff);
			}
		default:
			if (rDiff == 0 && cDiff == 0) {
				throw new Exception("Tried to swap 0 with 0");
			} else {
				// should never reach
			}

		}
		System.out.println("Board in swapSquare():");
		newBoard.print();
		System.out.print("newZero in swapSquare:");
		newBoard.printZero();
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

	public final boolean isWon() {
		//		Integer[][] wonBoard = {{1, 2, 3}, 
		//				       {4, 5, 6}, 
		//				       {7, 8, 0}};

		int counter = 1;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (board[r][c] != counter) {
					return false;
				}
				counter = counter % (size*size);
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
						r = c = size; //breaks out of both loops
					}
				}

			}
			if (flag == false) { //didn't find that number
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


}
