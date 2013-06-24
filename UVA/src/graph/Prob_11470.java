package graph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prob_11470 {

		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			int caseNum = 1;
			while(true) {
				int size = sc.nextInt();
				if(size == 0) break;
				int [][] grid = new int [size][size];
				
				for(int i = 0; i < size; ++i) {
					for(int j = 0; j < size; ++j) {
						grid[i][j] = sc.nextInt();
					}
				}
				System.out.print("Case " + caseNum + ": ");			
				processGrid(grid);
				++caseNum;
			}
			sc.close();

		}

		private static void processGrid(int[][] grid) {
			int left = 0; 
			int right = grid.length - 1;
			List<Integer> results = new ArrayList<Integer>();
			
			while(left <= right) {			
				results.add((getRowSum(grid, left, right) + getColSum(grid, left, right)));
				++left;
				--right;			
			}
			
			for(int i = 0; i < results.size() - 1; ++i) {
				System.out.print(results.get(i) + " ");
			}
			System.out.println(results.get(results.size() - 1));
		}

		private static int getColSum(int[][] grid, int left, int right) {
			int sum = 0; 
			for(int i = 0; i < grid.length; ++i) {
				sum += grid[i][left];
				 grid[i][left] = 0;
			}
			
			for(int i = 0; i < grid.length; ++i) {
				sum += grid[i][right];
				grid[i][right] = 0;
			}
			return sum;
		}

		private static int getRowSum(int [][] grid, int top, int bottom) {
			int sum = 0; 
			for(int i = 0; i < grid.length; ++i) {
				sum += grid[top][i] ;
				grid[top][i] = 0;		
			}
			for(int i = 0; i < grid.length; ++i) {
				sum += grid[bottom][i];
				grid[bottom][i] = 0;
			}
			return sum;
		}

		private static void printGrid(int[][] grid) {
			for(int i = 0; i < grid.length; ++i) {
				for(int j = 0 ; j < grid[i].length; ++j) {
					System.out.print(grid[i][j] + " ");
				}
				System.out.println();
			}
		}
		
	}