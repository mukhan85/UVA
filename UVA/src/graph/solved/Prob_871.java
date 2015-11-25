package graph.solved;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Prob_871 {

		static int [] dirX = {0, -1, -1, -1, 0, 1, 1,  1};
		static int [] dirY = {-1,-1 , 0,  1, 1, 1, 0, -1};
		static final char REPLACE_CHAR = '0';
		
		static int numRows = 25;
		static int numCols = 25;
		
		static char[][] grid ;
		
		static {
			initGrid();
		}
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			
			String line = sc.nextLine(); // Read in first Number.
			sc.nextLine(); // read in the empty line.
			int rowCount = 0;
			boolean isFirstOutput = true;
			
			while(sc.hasNextLine()) {	
				line = sc.nextLine();
				if(!isEmptyLine(line)) {
					
					for(int i = 0; i < line.length(); ++i) {
						grid[rowCount][i] = line.charAt(i);	
					}				
					++rowCount;
				} else {
					// Empty line, so process the grid.				
					rowCount = 0;
					if(isFirstOutput) {					
						isFirstOutput = false;
					} else {
						System.out.println();
					}
					printGrid();
					int maxBlock = 0;
					maxBlock = processGrid(maxBlock);
					System.out.println(maxBlock);
					initGrid();				
				}
			}
			
			System.out.println();
			int maxBlock = 0;
			System.out.println(processGrid(maxBlock));
			
			sc.close();	
		}
		
		public static String printGrid() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < numRows; ++i) {
				sb.append(Arrays.toString(grid[i]) + "\n");
			}
			return sb.toString();
		}
		
		private static void initGrid() {
			grid = new char[numRows][numCols];
			for(int i = 0; i < numRows; ++i) {
				Arrays.fill(grid[i], REPLACE_CHAR);
			}
		}

		private static int processGrid(int maxBlock) {
			int currentCount = 0;
			for(int i = 0; i < numRows; ++i) {
				for(int j = 0; j < numCols; ++j) {
					if(grid[i][j] != REPLACE_CHAR) {
						currentCount = fillFlood(i, j, grid[i][j]);
						if(currentCount > maxBlock) {
							return currentCount;
						}
					}
				}
			}
			return maxBlock;
		}
		
		private static int fillFlood(int r, int c, char original) {
			if(r < 0 || r >= numCols || c < 0 || c >= numCols) {
				return 0;
			}
			
			if(original != grid[r][c] || original == REPLACE_CHAR) {
				return 0;
			}
			
			grid[r][c] = REPLACE_CHAR;
			
			int answer = 1;
			for(int i = 0; i < dirX.length; ++i) {
				answer += fillFlood(r + dirX[i], c + dirY[i], original);
			}
			
			return answer;
		}

		private static boolean isEmptyLine(String line) {
			return line.length() < 1;
		}
	}