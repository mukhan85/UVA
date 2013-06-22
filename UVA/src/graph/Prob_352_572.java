package graph;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Prob_352_572 {

		static int [] dirX = {0, -1, -1, -1, 0, 1, 1,  1};
		static int [] dirY = {-1,-1 , 0,  1, 1, 1, 0, -1};
		static int maxRow = 0; 
		static int maxCol = 0;
		static char [][] grid;
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			int caseNumber = 1;
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				int size = Integer.parseInt(line);
				grid = new char[size][size];
				maxRow = size;
				maxCol = size;
				
				for(int i = 0; i < size; ++i) {
					line = sc.nextLine();
					for(int j = 0; j < size; ++j) {
						grid[i][j] = line.charAt(j);
					}				
				}
				
				int count = 0;
				for(int i = 0; i < size; ++i) {
					for(int j = 0; j < size; ++j) {
						int res = fillFlood(i, j, '1', '2');
						if(res > 0) {
							++count;
						}
					}
				}
				
				System.out.println("Image number " + caseNumber + " contains " + count + " war eagles.");
				++caseNumber;
			}
			sc.close();
		}

		private static int fillFlood(int r, int c, char orig, char change) {
			if(r < 0 || c < 0 || r >= maxRow || c >= maxCol) {
				return 0;	
			}
			
			if(grid[r][c] != orig) {
				return 0;
			}
			grid[r][c] = change;
			
			int answer = 1;
			for(int i = 0; i < dirX.length; ++i) {
				answer += fillFlood(r + dirX[i], c + dirY[i], orig, change);
			}
			
			return answer;
		}
		
	}