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
        
        // We are actually modifying the board, which means that if we try to see
        //if both up and down are valid moves then checking up is fine, but then checking down 
        // means the zero is trying to go from (0,1) to (2,1) which is an rdiff of 2
        // we need to change it so that it tests the moves on separate boards 
	public List<Direction> getValidMoves(Board board, int r, int c) {
		List<Direction> possibleMoves = getPossibleMoves(board, r, c);
		List<Direction> validMoves = new ArrayList<>();
		int rZero = board.getrZero();
		int cZero = board.getcZero();
		int newCZero = cZero;
		int newRZero = rZero;
                
                System.out.println("rZero, cZero at the beginning of getValidMoves: " + rZero + ", " +cZero);
		
		for (Direction dir : possibleMoves) {
			Board newBoard = board.copyBoard(); //clones board
                        System.out.println("newRZero, newCZero in the for loop of getValidMoves: " + newRZero + ", " + newCZero);
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
	public List<Direction> getPossibleMoves(Board board, int r, int c) {
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
