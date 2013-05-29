import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
	public static void main (String ... args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input"));
		
		int numTests = Integer.parseInt(sc.nextLine());
		for(int i = 0; i < numTests; ++i) {
			// Read in an empty line.
			sc.nextLine();
			// Read in row/col pair.
			String [] line = sc.nextLine().split("\\s");
			int rows = Integer.parseInt(line[0]);
			int cols = Integer.parseInt(line[1]);
			char[][] playGround = readMatrix(sc, rows, cols);
			
			print(playGround);
			
			line = sc.nextLine().split("\\s");
			int xStart = Integer.parseInt(line[0]);
			int yStart = Integer.parseInt(line[1]);
			
			// Read queries and print answers.
			while(sc.hasNextLine()) {
				String eachQuery = sc.nextLine();
				if(eachQuery == null || eachQuery.length() == 1) {
					break;
				}
				
				processQuery(playGround, line);
			}
		}		
	}
	

	private static void processQuery(char[][] playGround, String[] line) {
				
	}


	private static void print(char[][] playGround) {
		for(int i = 0; i < playGround.length; ++i) {
			for(int j = 0; j < playGround[i].length; ++j) {
			System.out.print(playGround[i][j]);	
			}
			System.out.println();
		}
	}


	private static char[][] readMatrix(Scanner sc, int rows, int cols) {
		char[][] grid = new char[rows][cols];
		for(int i = 0; i < rows; ++i) {
			String line = sc.nextLine();
			for(int j = 0; j < line.length(); ++j) {
				grid[i][j] = line.charAt(j);
			}
		}
		
		return grid;
	}
}
