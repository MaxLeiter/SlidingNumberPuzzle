public class SlidingPuzzle {
	private static int size;
	public static void main (String[] args) throws Exception {
		boolean d = true;
		final String numberArg = "--numbers"; // 2,3,4,5,6,7
		final String verboseArg = "--verbose";
		Integer[] parsed = null;
		boolean verbose = false;
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case numberArg:
				String[] numbers = args[i+1].split(",");
				parsed = new Integer[numbers.length];

				for (int j = 0; j < numbers.length; j++) {
					parsed[j] = Integer.parseInt(numbers[j]);
				}

				double sqrt = Math.sqrt(parsed.length);
				int x = (int) sqrt;
				size = x;
				if (!(Math.pow(sqrt, 2) == Math.pow(x, 2))) { // not perfect square	
					throw new Exception("Invalid board size. Not a perfect square");
				}

				break;
			case verboseArg:
				verbose = true;
				break;
			}
		}

		run(parsed, verbose);
	}

	public static void run(Integer[] parsed, boolean verbose) {
		Integer[][] numbers = new Integer[size][size];
		if (parsed.length > 0) {
			int counter = 0;
			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {
					numbers[r][c] = parsed[counter++];
				}
			}
		}

		Board board = new Board(numbers);
		AStar solution = new AStar(board, verbose);
	}
}
