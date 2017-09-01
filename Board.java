public final class Board {
	Integer[][] board;
	int xZero, yZero;
	int size;

	public Board() {
		xZero = yZero = 0;
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
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					setSquare(i, j, args[counter++]);
				}
			}
		}
		setupBoard();
	}

	private final void setupBoard() {
		size = board.length;
		print();
		printZero();
		if (!isValidBoard()) {
			System.out.println("invalid board");
		} else {
			System.out.println("valid board");
		};
	}

	public int getSize() {
		return size;
	}

	private final Board copyBoard() {
		return new Board(board.clone());
	}

	public final void setSquare(int x, int y, String value) {
		try {
			setSquare(x, y, Integer.parseInt(value));
		} catch (Exception NumberFormatException) {
			System.out.println("You dun goofed. " + value + " is not an Integer.");
		}
	}

	public final void setSquare(int x, int y, int value) {
		if (value == 0) {
			xZero = x;
			yZero = y;
		}
		board[x][y] = value;
	}

	public final Integer[][] swapSquare(int x, int y) throws Exception {
		Board newBoard = copyBoard();
		int xDiff = Math.abs(x - xZero);
		int yDiff = Math.abs(y - yZero);
		if (xDiff > 0 || yDiff > 0) {
			throw new Exception("Tried to swap with non-adjacent square");
		}
		switch (xDiff) {
		case 1:
			if (yDiff == 0) {
				newBoard.setSquare(xZero, yZero, getValueAtSquare(x, y));
				newBoard.setSquare(x, y, 0); // Guaranteed to be zero
			} else {
				throw new Exception("Invalid swap.");
			}
			break;
		case 0:
			if (yDiff == 1) {
				newBoard.setSquare(xZero, yZero, getValueAtSquare(x, y));
				newBoard.setSquare(x, y, 0); // Guaranteed to be zero
			} else {
				throw new Exception("Invalid swap.");
			}
		default:
			if (xDiff == 0 && yDiff == 0) {
				throw new Exception("Tried to swap 0 with 0");
			} else {
				// should never reach
			}

		}
		newBoard.printZero();
		return board;
	}

	public final void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public final boolean isWon() {
		Integer[][] wonBoard = {{1, 2, 3}, 
				{4, 5, 6}, 
				{7, 8, 0}};

		if (this.board.equals(wonBoard)) {
			return true;
		} else {
			return false;
		}
	}

	private final int getValueAtSquare(int x, int y){
		return board[x][y];
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
						r = c = size;
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
		System.out.println(xZero + ", " + yZero);
	}

	public final Integer[][] getBoard() {
		return board;
	}
}
