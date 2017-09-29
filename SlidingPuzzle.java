import Board.InvalidBoardException;

public class SlidingPuzzle {
	public static void main (String[] args) throws Exception {
		final String defaultArg = "--default";
		boolean d = true;
		final String numberArg = "--numbers"; // 2,3,4,5,6,7
		final String verboseArg = "--verbose";
		final String onlyGoalPathArg = "--print-goal";
		boolean verbose = false;
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case defaultArg:
				break;
			case numberArg:
				String[] numbers = numberArg.split(",");
				Integer[] parsed = new Integer[numbers.length];

				for (int j = 0; j < numbers.length; j++) {
					parsed[j] = Integer.parseInt(numbers[j]);
				}

				double sqrt = Math.sqrt(parsed.length);
				int x = (int) sqrt;
				if (!(Math.pow(sqrt, 2) == Math.pow(x,2))) { // not perfect square	
					throw new Exception("Invalid board size. Not a perfect square");
				}

				break;
			case verboseArg:
				verbose = true;
				break;
			case onlyGoalPathArg:
				verbose = false;
				break;
			}
		}

		run();
	}

	public static void run() {

	}
}
