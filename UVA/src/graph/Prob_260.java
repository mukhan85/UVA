package graph;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Prob_260 {

		static int [] dirX = {-1,  0, 1, -1, 0, 1};
		static int [] dirY = {-1, -1, 0,  0, 1, 1};
		
		static int maxRow = 0; 
		static int maxCol = 0;
		static char [][] grid;
		static StringBuilder printedGrid = new StringBuilder();
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			int caseNumber = 0;
			while(true) {
				String line = sc.nextLine();
				int size = Integer.parseInt(line);
				maxRow = size;
				maxCol = size;
				
				grid = new char[size][size];
				
				if(size == 0) break;
				
				for(int i = 0; i < size; ++i) {
					line = sc.nextLine();
					for(int j = 0; j < size; ++j) {
						grid[i][j] = line.charAt(j);
					}
					printedGrid.append(Arrays.toString(grid[i]) + "\n");
				}
				
				for(int i = 0; i < size; ++i) {
					fillFlood(0, i, 'b', '1');
				}
				++caseNumber;
				
				boolean isBlackWinner = false;
				
				for(int i = 0; i < size; ++i) {
					if(grid[size-1][i] == '1') {
						System.out.println(caseNumber + " B");
						isBlackWinner = true;
						break;
					}
				}
				
				if(!isBlackWinner) {
					System.out.println(caseNumber + " W");
				}
			
			}
			
			
			
			sc.close();
		}

		private static void fillFlood(int r, int c, char original, char changeTo) {
			if(c >= maxCol || c < 0 || r <0 || r >= maxRow) {
				return;
			}
			
			if(grid[r][c] != original) {
				return;
			}
			
			grid[r][c] = changeTo;
			//rebuildPrintedGrid();
			
			for(int i = 0; i < dirX.length; ++i) {
				fillFlood(r + dirX[i], c + dirY[i], 'b', '1');
			}
		}

		private static void rebuildPrintedGrid() {
			printedGrid = new StringBuilder();
			for(int i = 0; i < maxRow; ++i) {
				printedGrid.append(Arrays.toString(grid[i]) + "\n");
			}
		}
	}