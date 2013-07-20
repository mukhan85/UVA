
public class Solved { /* 8 Queens Chess Problem */
	
	private static final int size = 9;
	private static int[] row = new int[size];
	private static int lineCounter; // it is ok to use global
												// variables in competitive
												// programming

	private static boolean place(int col, int tryrow) {
		for (int currentColumn = 0; currentColumn < col; currentColumn++) {
			// check previously placed queens
			int currentRow = row[currentColumn];
		
			if (currentRow == tryrow || isOnSameDiagonal(tryrow, col, currentRow, currentColumn))
				return false; // an infeasible solution if share same row or same diagonal
			
		}
		return true;
	}
	
	private static boolean isOnSameDiagonal(int tryRow, int tryCol, int currentRow, int currentCol) {
		if(Math.abs(tryRow - currentRow) == Math.abs(tryCol - currentCol)) {			
			return true;
		}
			
		return false;
	}


	private static void backtrack(int col) {
		
		for (int tryrow = 0; tryrow < size; tryrow++) { // try all possible row
			if (place(col, tryrow)) { // if can place a queen at this col and row...
				row[col] = tryrow; // put this queen in this col and row
				
				if (col == size - 1) { // a candidate solution
					System.out.printf("%2d      %d", ++lineCounter, row[1]);
					for (int j = 2; j < size; j++) {
						System.out.printf(" %d", row[j]);
					}					
					System.out.printf("\n");
				} else {
					backtrack(col + 1); // recursively try next column
				}
			}			
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < size; i++) {
			row[i] = 0;
		}

		lineCounter = 0;
		System.out.printf("SOLN       COLUMN\n");
		System.out.printf(" #      1 2 3 4 5 6 7 8\n\n");
		backtrack(1); // generate all possible 8! candidate solutions
	}
}