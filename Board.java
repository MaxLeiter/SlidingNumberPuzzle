import java.math.BigInteger;

public final class Board {
	private Integer[][] board;
	private int rZero, cZero;
	
	public int getcZero() {
		return cZero;
	}

	public void setcZero(int cZero) {
		this.cZero = cZero;
	}

	public int getrZero() {
		return rZero;
	}

	public void setrZero(int rZero) {
		this.rZero = rZero;
	}

	int size;

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
			System.out.println("invalid board");
		} else {
			System.out.println("valid board");
		};
		
		// setup rZero, cZero
		boolean foundZero = false;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] == 0) {
					foundZero = true;
					rZero = c;
					cZero = r;
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
		
		printZero();
	}

	
	/**
	 * Length of board
	 * @return length of board
	 */
	public int getSize() {
		return size;
	}

	private final Board copyBoard() {
		return new Board(board.clone());
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

	public final Integer[][] swapSquare(int r, int c) throws Exception {
            System.out.println("swapSquare called with r = " + r + " and c = " + c);
		Board newBoard = copyBoard();
		int rDiff = Math.abs(r - rZero);
		int cDiff = Math.abs(c - cZero);
		System.out.println("newZero: " + r + ", " + c);
		System.out.println("rDiff: " + rDiff);
		System.out.println("cDiff: " + cDiff);
		if (rDiff > 1 || cDiff > 1) {
			throw new Exception("Tried to swap with non-adjacent square");
		}
		switch (rDiff) {
		case 1: //going up or down
			if (cDiff == 0) {
				newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
				newBoard.setSquare(r, c, 0); // Guaranteed to be zero
			} else {
				throw new Exception("rDiff == 1 case, cDiff should be 0; Invalid swap.");
			}
			break;
		case 0: //going lrft or right
			if (cDiff == 1) {
				newBoard.setSquare(rZero, cZero, getValueAtSquare(r, c));
				newBoard.setSquare(r, c, 0); // Guaranteed to be zero
			} else {
				throw new Exception("rDiff == 0 case, cDiff should be 1; Invalid swap.");
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
			return false;
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
				return false;
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
}
