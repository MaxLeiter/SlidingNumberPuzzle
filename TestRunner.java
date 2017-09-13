
public class TestRunner {
	public static void main(String[] args) {
		Integer[][] board = {{1,2,3}, 
							{5,0,6}, 
							{7,8,4}};
		Board obj = new Board(board);
		obj.print();
		Game game = new Game();
		game.start();
		System.out.print("possibleMoves: ");
		System.out.println(game.getPossibleMoves(obj, obj.getxZero(), obj.getyZero()));
		System.out.print("validMoves: ");
		System.out.println(game.getValidMoves(obj, obj.getxZero(), obj.getyZero()));
	}
}
