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
		int rZero = board.getcZero();
		int cZero = board.getrZero();
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
	 * @param int c
	 * @param int r
	 * @return List of Direction's that are valid moves
	 */
        
        //toDO: change c, r to r,c
	public List<Direction> getPossibleMoves(Board board, int c, int r) {
		int cZero = board.getcZero();
		int rZero = board.getrZero();
		List<Direction> result = new ArrayList<>();
		if (cZero == 0) { // left column
			if (rZero == 0) { //top left corner
				result.add(Direction.DOWN);
				result.add(Direction.RIGHT);
				return result;
			} else if (rZero == board.getSize() - 1) { // bottom left corner
				result.add(Direction.UP);
				result.add(Direction.RIGHT);
				return result;		
			} else { //middle rows, left column
				result.add(Direction.UP);
				result.add(Direction.DOWN);
				result.add(Direction.RIGHT);
				return result;
			}
		} else if (cZero == board.getSize() - 1) { // right column
			if (rZero == 0) { //top right corner
				result.add(Direction.DOWN);
				result.add(Direction.LEFT);
				return result;
			} else if (rZero == board.getSize() - 1) { //bottom right corner
				result.add(Direction.UP);
				result.add(Direction.LEFT);
				return result;		
			} else { //middle rows of right column
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
			} else if (rZero == board.getSize() - 1) { // bottom
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
