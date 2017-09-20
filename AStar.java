import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class AStar {
	private Game game;
	public HashSet<Board> states;
	private MinPQ pq; 
	public static void main(String[] args) {
		AStar finder = new AStar();
		
	}
	
	public AStar() {
		Integer[][] board = {{3,2,8}, 
				{5,1,6}, 
				{7,0,4}};
		Board b = new Board(board);
		b.print();
		game = new Game();
		game.start(this);
		pq = new MinPQ<AStarState>();
		pq.insert(?);
		next();
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
	
	public Board next() {
		// need to iterate through possible moves, add children to PQ, and recursively call next() while board is not won.
		AStarState a = (AStarState) pq.delMin();
		(for (a.getBoard().getPossibleMoves() ))
	}

}
