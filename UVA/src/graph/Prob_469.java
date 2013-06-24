package graph;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Prob_469 {

		static int [] dirX = {0, -1, -1, -1, 0, 1, 1,  1};
		static int [] dirY = {-1,-1 , 0,  1, 1, 1, 0, -1};
		
		static int maxRow = 0; 
		static int maxCol = 0;
		static ArrayList<char[]> grid = new ArrayList<char[]>();
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			String line = sc.nextLine();
			int numCases = Integer.parseInt(line);
			sc.nextLine();
			
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				if(isGridLine(line)) {
					grid.add(getNewGridLine(line));
				} else if(isQueryLine(line)) {
					processQuery(line);
				} else {
					// empty line, reset the grid.
					grid = new ArrayList<char[]>();
					System.out.println();
				}
			}
			
			sc.close();
		}

		private static void processQuery(String line) {
//			System.out.println("Processing query: " + line);
//			for(int i = 0; i < grid.size(); ++i) {
//				System.out.println(Arrays.toString(grid.get(i)));
//			}
			
			String [] nums = line.split("\\s");
			int row = Integer.parseInt(nums[0]);
			int col = Integer.parseInt(nums[1]);
			
			int answer = fillFlood(row-1, col-1, 'W', '.');
			fillFlood(row-1, col-1, '.', 'W');
			System.out.println(answer);
		}

		private static int fillFlood(int r, int c, char original, char changeTo) {
			if(r < 0 || r >= grid.size() || c < 0 || c >= grid.get(0).length) {
				return 0;
			}
			char [] arr = grid.get(r);
			if(arr[c] != original) {
				return 0;
			}
			int answer = 1;
			arr[c] = changeTo;
			grid.set(r, arr);
			
			for(int i = 0; i < dirX.length; ++i) {
				answer += fillFlood(r + dirX[i], c + dirY[i], original, changeTo);
			}
			
			return answer;
		}

		private static boolean isQueryLine(String line) {
			String [] nums = line.split("\\s");
			return nums.length == 2 && Character.isDigit(nums[0].charAt(0)) && Character.isDigit(nums[1].charAt(0));
		}

		private static char[] getNewGridLine(String line) {
			return line.toCharArray();
		}

		private static boolean isGridLine(String line) {
			return line.length() > 1 && (line.charAt(0) == 'L' || line.charAt(0) == 'W');
		}
	}
