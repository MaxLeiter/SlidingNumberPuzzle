import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
	public HashMap<Board, Integer> states;
	int counter = 0;

	public void start() {
		states = new HashMap<Board, Integer>();

	}
	

	/**
	 * Returns the valid moves (unique state), derived from getPossibleMoves()
	 * @param board
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Direction> getValidMoves(Board board, int x, int y) {
		List<Direction> possibleMoves = getPossibleMoves(board, x, y);
		List<Direction> validMoves = new ArrayList<>();
		int rZero = board.getxZero();
		int cZero = board.getyZero();
		int newCZero = rZero;
		int newRZero = cZero;
		
		for (Direction dir : possibleMoves) {
			Board newBoard = board;
			switch (dir) {
				case UP:
					newRZero = rZero - 1;
					break;
				case DOWN:
					newRZero = rZero + 1;
					break;
				case LEFT:
					newCZero = cZero - 1;
					break;
				case RIGHT:
					newCZero = cZero + 1;
					break;
			}
			System.out.println("getValidMoves chose: " + dir);

			try {
				newBoard = new Board(newBoard.swapSquare(newRZero, newCZero));
			} catch (Exception e) {
				System.out.println("This shouldn't be hit, because getPossibleMoves prunes this.");
				e.printStackTrace();
			}
			
			if (!(states.containsKey(newBoard))) {
				validMoves.add(dir);
			}
			
		}
		return validMoves;
	}

	/**
	 * @param board
	 * @param int x
	 * @param int y
	 * @return List of Direction's that are valid moves
	 */
        
        //toDO: change x, y to r,c
	public List<Direction> getPossibleMoves(Board board, int x, int y) {
		int xZero = board.getxZero();
		int yZero = board.getyZero();
		List<Direction> result = new ArrayList<>();
		if (xZero == 0) { // left column
			if (yZero == 0) {
				result.add(Direction.DOWN);
				result.add(Direction.RIGHT);
				return result;
			} else if (yZero == board.getSize() - 1) {
				result.add(Direction.UP);
				result.add(Direction.RIGHT);
				return result;		
			} else {
				result.add(Direction.UP);
				result.add(Direction.DOWN);
				result.add(Direction.RIGHT);
				return result;
			}
		} else if (xZero == board.getSize() - 1) { // right column
			if (yZero == 0) {
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				return result;
			} else if (yZero == board.getSize() - 1) {
				result.add(Direction.UP);
				result.add(Direction.LEFT);
				return result;		
			} else {
				result.add(Direction.UP);
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				return result;
			}
		} else { // not left or right
			if (yZero == 0) { // top
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				result.add(Direction.RIGHT);
				return result;
			} else if (yZero == board.getSize() - 1) { // bottom
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

	private enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}
}
