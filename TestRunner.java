
public class TestRunner {
	public static void main(String[] args) {
		Integer[][] board = {{3,2,8}, 
							{5,1,6}, 
							{7,0,4}};
		Board obj = new Board(board);
		obj.print();
		Game game = new Game();
		game.start();
		
	}
	
	private static int heuristic(Board b) {
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
}
