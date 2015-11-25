package graph.solved;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class Prob_12244 {

		static int [] dirX = {0, -1, -1, -1, 0, 1, 1,  1};
		static int [] dirY = {-1,-1 , 0,  1, 1, 1, 0, -1};
		static final char REPLACE_CHAR = '.';
		
		static int numRows;
		static int numCols;
		
		static char[][] grid;
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			
			while(true) {
				String line = sc.nextLine();
				String [] nums = line.split("\\s");
				numRows = Integer.parseInt(nums[0]);
				numCols = Integer.parseInt(nums[1]);
				
				if((numRows + numCols) == 0) break;
				
				grid = new char[numRows][numCols];
				
				for(int r = 0; r < numRows; ++r) {
					line = sc.nextLine();
					grid[r] = line.toCharArray();
				}
				processGrid();
			}		
			sc.close();	
		}
		
		private static void processGrid() {
			
			int numStar = 0;
			
			for(int i = 0; i < numRows; ++i) {
				for(int j = 0; j < numCols; ++j) {
					if(grid[i][j] != REPLACE_CHAR) {
						char original = grid[i][j];
						Integer rankCount = fillFlood(i, j, original, REPLACE_CHAR);
						if(rankCount == 1) {
							++numStar;
						}
					}				
				}
			}
			System.out.println(numStar);
		}
		
		public static String printGrid() {
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < numRows; ++i ) {
				sb.append(Arrays.toString(grid[i]) + "\n");
			}
			
			return sb.toString();
		}
		private static Integer fillFlood(int r, int c, char original, char changeTo) {
			if(r < 0 || r >= numRows || c < 0 || c >= numCols ) {
				return 0;
			}
			
			if(original != grid[r][c] || grid[r][c] == REPLACE_CHAR) {
				return 0;
			}
			
			grid[r][c] = changeTo;
			
			int answer = 1;
			for(int i = 0; i < dirX.length; ++i) {
				answer += fillFlood(r + dirX[i], c + dirY[i], original, changeTo);
			}
			
			return answer;
		}
	}
