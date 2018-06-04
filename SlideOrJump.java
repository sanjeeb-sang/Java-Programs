package cs3005;

import java.util.ArrayList;

/***
 * 
 * @author Sanjeeb Sangraula. This class emulates a game called Slide or Jump.
 *         You stand at one end of a board consisting of several cells, with
 *         each cell containing a non-negative integer that represents the cost
 *         of visiting that cell.
 *
 */

public class SlideOrJump {

	// To store the cost associated with the cells of the board
	private ArrayList<Integer> board = new ArrayList<Integer>();

	private int recMethodCount = 0, dpIterationCount = 0;

	/**
	 * Argument constructor that takes the array containing the cost of the
	 * cells. Note that board will have at least two elements, the first one
	 * always being zero
	 * 
	 * @param board
	 *            the integer array that contains the values in the board
	 */
	public SlideOrJump(int[] board) {

		// Initialize the board array with the given values
		this.addScoresToBoard(board);
	}

	/**
	 * It is a method to initialize the board ArrayList with the given values in
	 * an int[]
	 * 
	 * @param board
	 *            the int[] with the values that the ArrayList board is to be
	 *            initialized with
	 */
	private void addScoresToBoard(int[] board) {

		for (int i = 0; i < board.length; i++) {
			this.board.add(board[i]);
		}

	}

	/**
	 * A method to compute overall cost recursively
	 * 
	 * @return the cost of going through the cells, calculated recursively.
	 */
	public long recSolution() {

		return calcCostRecursively(this.getScoresList());
	}

	/**
	 * A helper method to calculate overall cost recursively
	 * 
	 * @param list
	 *            the list with the elements that were in the board
	 * @return the overall cost of the moving to the last cell from the front of
	 *         the cell
	 */
	private long calcCostRecursively(ArrayList<Integer> list) {

		this.recMethodCount++;

		if (list.size() == 1) {
			// if there is only one element, then return the first element of
			// the ArrayList
			return list.remove(0);

		} else if (list.size() == 2) {

			return list.get(0) + list.get(1);

		} else if (list.size() == 3) {

			return list.get(0) + list.get(2);

		} else if (list.size() > 1) {

			if (this.isAscending(list)) {

				list.remove(1);

				return list.remove(0) + this.calcCostRecursively(list);

			} else if (list.get(1) <= list.get(2)) {

				return list.remove(0) + this.calcCostRecursively(list);

			} else {

				list.remove(1);

				return list.remove(0) + this.calcCostRecursively(list);
			}

		} else {

			return 0;
		}
	}

	/**
	 * A method to compute overall cost using dynamic programming (required for
	 * A-B credit)
	 * 
	 * @return the overall cost using dynamic programming, calculated using
	 *         dynamic programming.
	 */
	public long dpSolution() {

		return this.calcCostDynamically(this.getScoresList());
	}

	/**
	 * A method to calculate the cost of moving through the cells dynamically
	 * 
	 * @param list
	 *            the list containing the cost of the cells
	 * @return the total cost while moving through the cells, calculated
	 *         dynamically
	 */
	private long calcCostDynamically(ArrayList<Integer> list) {

		int index = 0, total = 0;
		
		if (this.isAscending(list) && list.size() > 3) {
			index = 1;
			total = 0;
		}

		while (index < list.size()) {

			this.dpIterationCount++;

			total += list.get(index);

			if (index == (list.size() - 3)) {
				// at the third from last index or last element
				index += 2;

			} else if (index == (list.size() - 2)) {
				// at the second last index
				index += 1;

			} else if (index == (list.size() - 1)) {
				// at the last index
				break;

			} else {

				if (this.isAscending(list)) {

					index += 2;

				} else if (list.get(index + 1) <= list.get(index + 2)) {

					index += 1;

				} else {

					index += 2;
				}
			}
		}
		return total;
	}

	/**
	 * A method to get the cost of moving from cell n
	 * 
	 * @param index
	 *            the index of the board corresponding to the cell n
	 * @return the cost of moving from cell n. This corresponds to the value of
	 *         the board at index i
	 */
	private int cost(int i) {

		return this.board.get(i);
	}

	/**
	 * A method to return sequence of moves required
	 * 
	 * @return the best moves from the first to the last cell
	 */
	public String getMoves() {

		return this.getMovesDP(this.getScoresList());
	}

	/**
	 * A helper method to calculate the best sequence of moves to move across
	 * the cells with the minimum costs
	 * 
	 * @param list
	 *            the ArrayList with the costs associated with the cells
	 * @return a String containing the costs after moving through the cells
	 */
	private String getMovesDP(ArrayList<Integer> list) {

		int index = 0;
		String moves = "";

		if (this.isAscending(list) && list.size() > 3) {
			index = 1;
			moves += "S";
		}
		
		while (index < list.size()) {
			
			if (index == (list.size() - 3)) {
				// at the third from last index or last element
				index += 2;
				moves += "J";

			} else if (index == (list.size() - 2)) {
				// at the second last index
				index += 1;
				moves += "S";

			} else if (index == (list.size() - 1)) {
				// at the last index
				break;

			} else if (list.size() > 3) {

				if (this.isAscending(list)) {

					index += 2;
					moves += "J";

				} else if (list.get(index + 1) <= list.get(index + 2)) {

					index += 1;
					moves += "S";

				} else {

					index += 2;
					moves += "J";
				}
			} else {

				return "";
			}
		}
		return moves;
	}

	
	/**
	 * A method to get the scores ArrayList. It sends a copy of the board
	 * ArrayList, that contains the costs associated with the cells.
	 * 
	 * @return a replica of the ArrayList with the cost associated with the
	 *         cells.
	 */
	private ArrayList<Integer> getScoresList() {

		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < this.board.size(); i++) {
			list.add(this.board.get(i));
		}
		return list;
	}

	/**
	 * A public method to get the number of operations that the recursive method
	 * performs
	 * 
	 * @return the number of operations that the recursive algorithm performs
	 */
	public long countRecOperations() {

		return this.getRecOperationsCount(this.getScoresList());

	}

	/**
	 * A method to count the number of operations that the dynamic programming
	 * method performs while finding the solution
	 * 
	 * @return the number of operations performed by the dynamic programming
	 *         algorithm
	 */
	public long countDpOperations() {

		return this.getDpOperationsCount(this.getScoresList());

	}

	/**
	 * A public method to get the number of operations required for the
	 * recursive algorithm to find the cost of the cells, calculated recursively
	 * 
	 * @return the number of operations performed by the countRecOperations()
	 *         method
	 */
	public long recOperationsCount() {

		return this.getRecOperationsCount(this.board);

	}

	/**
	 * A public method to count the number of operations performed by the
	 * dpOperationsCount() method
	 * 
	 * @return the number of operations performed by the countDpOperations
	 *         method
	 */
	public long dpOperationsCount() {

		return this.getDpOperationsCount(this.board);

	}

	/**
	 * A method to calculate the number of operations performed by the
	 * getMoves() method to find the moves to take in the cells
	 * 
	 * @return the number of operations performed
	 */
	public long movesOperationsCount() {

		return this.getMovesOperationsCount(this.board);

	}

	/**
	 * A helper method to get the number of operations for the dynamic
	 * programming algorithm
	 * 
	 * @param list
	 *            the list array that contains the costs for the cells
	 * @return the number of operations performed by the dynamic programming
	 *         operation
	 */
	private long getDpOperationsCount(ArrayList<Integer> list) {

		return this.dpIterationCount;		
	}

	/**
	 * A helper method to find the number of recursive operations performed by
	 * the recursive algorithm
	 * 
	 * @param list
	 *            the array containing the costs of all the cells
	 * @return the number of operations that the recursive algorithm performs
	 */
	private int getRecOperationsCount(ArrayList<Integer> list) {

		return this.recMethodCount;
	}

	/**
	 * A method to get the number of operations in finding the number of
	 * operations required to find the moves
	 * 
	 * @param list
	 *            the list with the costs of the cells
	 * @return the number of operations the method executes while finding the
	 *         moves required to move in the cells
	 */
	private long getMovesOperationsCount(ArrayList<Integer> list) {

		if (list.size() == 2) {
			return 1;
		}

		if (list.size() > 1) {

			list.remove(0);

			if (list.get(0) <= list.get(1)) {

				return 1 + this.getMovesOperationsCount(list);

			} else {

				list.remove(0);

				return 1 + this.getMovesOperationsCount(list);
			}
		}
		return 0;
	}

	/**
	 * A private helper method to find if an ArrayList<Integer> is ascending
	 * 
	 * @param list
	 *            the ArrayList which is to be checked if it is ascending
	 * @return true if the ArrayList<Integer> is ascending, false otherwise
	 */
	private boolean isAscending(ArrayList<Integer> list) {

		boolean ascending = true;

		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) > list.get(i + 1)) {
				ascending = false;
				break;
			}
		}
		return ascending;
	}
}
