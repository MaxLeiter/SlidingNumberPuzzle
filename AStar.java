import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStar {
	public HashMap<Board, AStarState> states;
	private PriorityQueue<AStarState> pq; 
	private boolean verbose = false;
	public static void main(String[] args) {
		Integer[][] board = {{1,2,3, 4, 5}, 
							{6, 7, 8, 9, 10 }, 
							{11, 0, 13, 14, 15},
							{16, 17, 18, 19, 20},
							{21, 12, 22, 23, 24}};
		Board b = new Board(board);
		AStar finder = new AStar(b);
	}

	public AStar(Board initialBoard, boolean verbose) {
		this.verbose = verbose;
		pq = new PriorityQueue<AStarState>();
		states = new HashMap<Board, AStarState>();
		AStarState initial = new AStarState(null, initialBoard, null);
		// Populate PriorityQueue and HashMap
		pq.add(initial);
		states.put(initial.getBoard(), initial);
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

	/**
	 * The actual A* algorithm.
	 * Pop the min, fetch possible moves, if moves not seen before, add them to PQ. 
	 * Repeat with some magic and you've solved sliding block puzzles.
	 */
	public void run() {
		//TODO: check for unsolvable (Queue is empty, and no solution yet)
		//		print solution path
		while (!pq.isEmpty()) {
			AStarState prev = (AStarState) pq.remove();

			if (verbose) {
				System.out.println("---------");
				prev.getBoard().print();
				System.out.printf("Removed min.\nWeight: %d\nDist: %d\n", prev.getWeight(), prev.getDist());
			}

			ArrayList<Board.Direction> dirs = (ArrayList<Board.Direction>) prev.getBoard().getPossibleMoves();
			for (int i = 0; i < dirs.size(); i++) {
				AStarState current = new AStarState(prev, new Board(prev.getBoard().moveDirection(dirs.get(i))), dirs.get(i));

				// The case where we find the same board state that already exists in the queue but we found a faster path.
				if (states.containsKey(current.getBoard())) { // containsKey uses equals() in AStarState
					if (states.get(current.getBoard()).getWeight() > current.getWeight()) {
						states.remove(current.getBoard());
						states.put(current.getBoard(), current);
						pq.remove(current);
						pq.add(current);
					}
					if (verbose) {
						System.out.printf("Found existing state in map. \nWeight: %d\nDist: %d\n", current.getWeight(), current.getDist());
					}
				}

				if (!current.getBoard().isWon()) {
					if (!states.containsKey(current.getBoard())) {
						pq.add(current);
						if (verbose) {
							System.out.printf("Added to PQ.\nWeight: %d\nDist: %d\n", current.getWeight(), current.getDist());
						}
						states.put(current.getBoard(), current);

					}
				} else {
					current.getBoard().print();
					System.out.println("It works! Moves: " + current.getDist());
					return; 
				}

			}
		}
	}

}
