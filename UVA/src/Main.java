import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static List<int[]> validPositions = new ArrayList<int[]>();
	private static final int size = 9;
	private static int [] row = new int [size];
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Scanner sc= new Scanner(new File("input"));
		//Scanner sc= new Scanner(System.in);
		
		int numCases = sc.nextInt();
		generateValidQueenPositions();
		
		for(int i = 0; i < numCases; ++i) {
			readGrid(sc);
			solveGrid();
		}
		
		sc.close();
	}

	
	private static void generateValidQueenPositions() {
		int columnNumber = 1;
		backtracking(columnNumber);
		
		for(int i = 0; i < validPositions.size(); ++i ) {
			System.out.println(Arrays.toString(validPositions.get(i)));
		}
	}

	private static void backtracking(int currentColumn) {
		
		for(int tryRow = 1; tryRow < size; ++tryRow) {
			if(isValidPosition(tryRow, currentColumn)) {
				row[currentColumn] = tryRow;
				
				if(currentColumn == size - 1) {
					validPositions.add(row);
				} else {
					backtracking(currentColumn + 1);
				}
			}
		}
	}


	private static boolean isValidPosition(int tryRow, int tryCol) {
		
		for(int currentCol = 0; currentCol < tryCol; ++currentCol) {
			// No Queen exists on the same row.
			int currentRow = row[currentCol];
			if(currentRow == tryRow || isOnSameDiagonal(tryRow, tryCol, currentRow, currentCol)) {
				return false;
			}
		}
		return true;
	}


	private static boolean isOnSameDiagonal(int tryRow, int tryCol, int currentRow, int currentCol) {
		if(Math.abs(tryRow - currentRow) == Math.abs(tryCol - currentCol)) {			
			return true;
		}
			
		return false;
	}


	private static void solveGrid() {
		
	}

	private static void readGrid(Scanner sc) {
		
	}
}