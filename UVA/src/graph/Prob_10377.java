package graph;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Got WA, couldn't figure out why...
 * @author kazakh
 *
 */
public class Prob_10377 {
	public static boolean firstCase = true;
	
	public static void main (String ... args) throws FileNotFoundException {
		//Scanner sc = new Scanner(new File("input"));
		Scanner sc = new Scanner(System.in);
		int numTests = Integer.parseInt(sc.nextLine());
		for(int i = 0; i < numTests; ++i) {
			// Read in an empty line.
			sc.nextLine();
			// Read in row/col pair.
			String [] line = sc.nextLine().split("\\s");
			int rows = Integer.parseInt(line[0]);
			int cols = Integer.parseInt(line[1]);
			char[][] playGround = readMatrix(sc, rows, cols);
			
			//print(playGround);
			
			line = sc.nextLine().split("\\s");
			int xStart = Integer.parseInt(line[0]) - 1;
			int yStart = Integer.parseInt(line[1]) - 1;
			Point startingPoint = new Point(xStart, yStart);
			Robot robot = new Robot(playGround, startingPoint);
			
			// Read queries and print answers.
			while(sc.hasNextLine()) {
				String eachQuery = sc.nextLine();
				if(eachQuery == null || eachQuery.length() == 1) {
					break;
				}
		
				processQuery(robot, eachQuery);			
				if(eachQuery.charAt(eachQuery.length() - 1) == 'Q') {
					break;
				}
			}
		}		
	}
	
	private static void processQuery(Robot robot, String query) {
		for(int i = 0; i < query.length(); ++i) { 
			char eachCommand = query.charAt(i);
			robot.processCommand(eachCommand);
		}
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

enum Dir {
	N, E, S, W, INVALID;
	
	public static Dir fromChar(char ch) {
		switch(ch) {
			case 'N': return N;
			case 'E': return E;
			case 'S': return S;
			case 'W': return W;
			default : return INVALID;
		}
	}
	
	public Dir getNextDirection(char inputDir) {
		if(inputDir == 'L') {
			switch(this) {
				case N : return W;
				case E : return N;
				case S : return E;
				case W : return S;
				default : return INVALID;
			}
		} else {
			switch(this) {
				case N : return E;
				case E : return S;
				case S : return W;
				case W : return N;
				default: return INVALID;
			}
		}
	}
}

class Robot {
	
	char [][] playGround;
	Point point;
	Dir direction = Dir.N;
	
	public Robot(char [][] ground, Point startingPoint) {
		this.playGround = ground;
		this.point = startingPoint;
	}
	
	public void processCommand(char eachCommand) {
		if(isDirectionCommand(eachCommand)) {		
			nextDirection(eachCommand);
		} else if(isForwardCommand(eachCommand)) {
			move();
		} else if(isFinishCommand(eachCommand)) {
			if(!Prob_10377.firstCase) {
				System.out.println("\n");
			} else {
				Prob_10377.firstCase = false;
			}
			System.out.print((point.x + 1) + " " + (point.y + 1) + " " + direction.name());
		}
	}

	private boolean isFinishCommand(char eachCommand) {
		return 'Q' == eachCommand;
	}

	public void nextDirection(char command) {
		this.direction = direction.getNextDirection(command);
	}
	
	public boolean isDirectionCommand(char ch) {
		return ch == 'L' || ch == 'R';
	}
	
	public boolean isForwardCommand(char ch) {
		return 'F' == ch;
	}
	
	public void move() {
		switch(this.direction) {
			case N : {
				if(canGoNorth())
				point.x = point.x - 1;			
			} break;
			case E : {
				if(canGoEast())
				point.y = point.y + 1;			
			} break;
			case S : {
				if(canGoSouth())
				point.x = point.x + 1;			
			} break;
			case W : {
				if(canGoWest())
					point.y = point.y - 1;
			} break;
			default: {
				System.out.println(this);
				throw new IllegalArgumentException("Invalid Move case");			
			}
		}
	}
	
	@Override
	public String toString() {
		return "Robot [point=" + point + ", direction=" + direction + "]";
	}

	private boolean canGoWest() {		
		return playGround[point.x][point.y - 1] != '*';
	}

	private boolean canGoSouth() {
		return playGround[point.x + 1] [point.y] != '*' ;
	}

	private boolean canGoEast() {		
		return playGround[point.x] [ point.y + 1 ] != '*';
	}

	private boolean canGoNorth() {
		return playGround[point.x - 1] [point.y] != '*';
	}
}
