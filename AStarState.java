
public class AStarState implements Comparable<AStarState> {
	private Board board;
	private int distTo;
	private AStarState previous;
	
	public AStarState(AStarState previous, Board board) {
		this.board = board;
		this.previous = previous;
		this.distTo = previous.getDist() + AStar.cost(previous, board);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getDist() {
		return distTo;
	}
	
	public AStarState getPrevious() {
		return previous;
	}
	
	public int getWeight() {
		return heuristic(board) + getDist();
	}

	private int heuristic(Board b) {
		Integer[][] board = b.getBoard();
		int size = b.getSize();
		
		int count = 0;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				int num = board[r][c];
				if (num == 0) {
					count += Math.abs(size - 1 - c);
					count += Math.abs(size - 1 - r);
				} else {
					count += Math.abs(((num - 1) / size) - r); // row difference
					count += Math.abs(Math.abs((num - 1)) % size - c); // column difference	
				}
				if (Game.DEBUG) {
					System.out.println("r:" + r + ", c: " + c + ", num: " + num);
					System.out.println("count:" + count);
				}
			}
		}

		return count;
	}

	@Override
	public int compareTo(AStarState o) {
		int a = this.getWeight();
		int b = o.getWeight();
		return b - a;
	}
}
