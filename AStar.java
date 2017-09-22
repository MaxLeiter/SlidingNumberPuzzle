import java.util.ArrayList;
import java.util.HashSet;

import edu.princeton.cs.algs4.MinPQ;

public class AStar {
	public HashSet<Board> states;
	private MinPQ<AStarState> pq; 
	public static void main(String[] args) {
		Integer[][] board = {{3,2,8}, 
				{5,1,6}, 
				{7,0,4}};
		Board b = new Board(board);
		b.print();

		AStar finder = new AStar(b);
	}

	public AStar(Board initialBoard) {

		pq = new MinPQ<AStarState>();
		states = new HashSet<Board>();
		pq.insert(new AStarState(null, initialBoard));
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
			AStarState prev = (AStarState) pq.delMin();
			ArrayList<Board.Direction> dirs = (ArrayList<Board.Direction>) prev.getBoard().getPossibleMoves();
			for (int i = 0; i < dirs.size(); i++) {
				AStarState current = new AStarState(prev, new Board(prev.getBoard().moveDirection(dirs.get(i))));
				if (!current.getBoard().isWon()) {
					if (!states.contains(current.getBoard())) {
						pq.insert(current);
					}
				} else {
					System.out.println("It works!");
				}
			}
		}
	}

}
