package graph;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Prob_657 {

		private static final char DOT = '.';
		private static final char EKS = 'X';
		private static final char STAR = '*';
		
		static int [] dirX = { 0, -1, 0, 1};
		static int [] dirY = {-1,  0, 1, 0};
		
		static int counter = 0;
		static int numRow = 0; 
		static int numCol = 0;
		static char[][] grid;
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			int caseNumber = 1;
			
			while(true) {
				String line = sc.nextLine();
				String [] nums = line.split("\\s");
				numCol = Integer.parseInt(nums[0]);
				numRow = Integer.parseInt(nums[1]);
				
				if((numRow + numCol) == 0) break;
				
				readGrid(sc);
				processGrid(caseNumber);
				++caseNumber;
			}
			
			sc.close();
		}

		private static void processGrid(int caseNumber) {
			List<Integer> results = new ArrayList<Integer>();
			
			for(int i = 0 ;i < numRow; ++i) {
				for(int j = 0; j < numCol; ++j) {
					if(grid[i][j] != DOT) {
						counter = 0;
						dfsStar(i, j);
						results.add(counter);
					}
				}
			}
			Collections.sort(results);
			System.out.println("Throw " + caseNumber);
			for(int i = 0; i < results.size() - 1; ++i) {
				System.out.print(results.get(i) + " ");
			}
			System.out.println(results.get(results.size() - 1) + "\n");
			//Throw 1
			//1 2 2 4
		}

		private static void dfsStar(int r, int c) {
			if(r < 0 || r >= numRow || c < 0 || c >= numCol || grid[r][c] == DOT) {
				return;
			}
			
			if(grid[r][c] == EKS) {
				dfsX(r,c);
				++counter;
			}
			
			grid[r][c] = DOT;
			
			for(int i = 0; i < dirX.length; ++i) {
				dfsStar(r + dirX[i], c + dirY[i]);
			}
		}

		private static void dfsX(int r, int c) {
			if(r < 0 || r >= numRow || c < 0 || c >= numCol || grid[r][c] != EKS) {
				return;
			}
			
			grid[r][c] = STAR;
			
			for(int i = 0; i < dirX.length; ++i) {
				dfsX(r + dirX[i], c + dirY[i]);
			}
		}

		private static void readGrid(Scanner sc) {
			grid = new char[numRow][numCol];		
			for(int i = 0; i < numRow; ++i) {
				String line = sc.nextLine();
				for(int j = 0; j < line.length(); ++j) {
					grid[i][j] = line.charAt(j);
				}
				//System.out.println(Arrays.toString(grid[i]) + "\n");
			}
			//System.out.println("  ====  ");
		}	
	}