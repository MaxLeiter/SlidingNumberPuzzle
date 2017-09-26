import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStar {
	public HashMap<Board, AStarState> states;
	private PriorityQueue<AStarState> pq; 
	private int counter = 0;
	public static void main(String[] args) {
		Integer[][] board = {{3,2,8}, 
				{5,1,6}, 
				{7,0,4}};
		Board b = new Board(board);
		b.print();

		AStar finder = new AStar(b);
	}

	public AStar(Board initialBoard) {

		pq = new PriorityQueue<AStarState>();
		states = new HashMap<Board, AStarState>();
		pq.add(new AStarState(null, initialBoard));
		this.run();
	}

	/**
	 * Cost of a single move. Check the AStarState for cumulative.
	 * @param previous
	 * @param current
	 * @return
	 */
	public static int cost(AStarState previous, Board current) {
		return 1;
	}

	public void run() {
		while (!pq.isEmpty()) {
			counter++;
			if (counter > 10) {
				return;
			}
			AStarState prev = (AStarState) pq.remove();
			ArrayList<Board.Direction> dirs = (ArrayList<Board.Direction>) prev.getBoard().getPossibleMoves();
			for (int i = 0; i < dirs.size(); i++) {
				AStarState current = new AStarState(prev, new Board(prev.getBoard().moveDirection(dirs.get(i))));
			
				// The case where we find the same board state that already exists in the queue but we found a faster path.
				if (states.containsKey(current.getBoard())) {
					if (states.get(current.getBoard()).getWeight() > current.getWeight()) {
						states.remove(current.getBoard());
						states.put(current.getBoard(), current);
					}
				}

				if (!current.getBoard().isWon()) {
					if (!states.containsKey(current.getBoard())) {
						pq.add(current);
						states.put(current.getBoard(), current);
					}
				} else {
					System.out.println("It works!");
				}
			}
		}
	}

}
