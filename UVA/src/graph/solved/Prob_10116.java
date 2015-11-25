package graph.solved;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Prob_10116 {
		static int counter = 0;

		static int numRows = 0; 
		static int numCols = 0;
		static char[][] grid;

		private static Point loopPoint = null;
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			
			while(true) {
				String line = sc.nextLine();
				String [] nums = line.split("\\s");
				numRows = Integer.parseInt(nums[0]);
				numCols = Integer.parseInt(nums[1]);
				grid = new char[numRows] [numCols];
				
				if((numRows + numCols) == 0) break;
				int startCol = Integer.parseInt(nums[2]);
							
				readGrid(sc);
				processGrid(startCol - 1);
				
			}
			sc.close();
		}

		private static void processGrid(int startY) {
			Point startPoint = new Point(0, startY);
			Map<Point, Integer> distance = new HashMap<Point, Integer>();
			dfs(startPoint, distance, 0);
			int answer = getMaxDist(distance);
			
			if(loopPoint != null) {
				//3 step(s) before a loop of 8 step(s)
				System.out.println((distance.get(loopPoint) -1) + getSingPlural((distance.get(loopPoint) - 1)) + 
						" before a loop of " + (answer - distance.get(loopPoint) + 1) + getSingPlural(answer - distance.get(loopPoint) + 1));
			} else {
				
				System.out.println(getMaxDist(distance) + getSingPlural(answer) + " to exit");				
			}
			
			distance = new HashMap<Point, Integer>();
			loopPoint = null;
		}

		private static String getSingPlural(int anw) {
			return " step(s)";
		}

		private static int getMaxDist(Map<Point, Integer> distance) {
			int max = Integer.MIN_VALUE;
			
			for(Point key : distance.keySet()) {
				if(distance.get(key) > max) {
					max = distance.get(key);
				}
			}
			
			return max;
		}

		private static void dfs(Point p, Map<Point, Integer> dist, int currentDist) {
			if(p.x < 0 || p.x >= numRows || p.y < 0 || p.y >= numCols){ 
				return;			
			}
			
			if(dist.get(p) != null) {
				loopPoint = p;
				return;
			}
					
			dist.put(p, ++currentDist);
			Point nextPoint = getNextPoint(p);
			dfs(nextPoint, dist, currentDist);
			
		}

		private static Point getNextPoint(Point p) {
			char nextInstr = grid[p.x][p.y];
			
			switch(nextInstr) {
				case 'N' : return new Point(p.x - 1, p.y);
				case 'W' : return new Point(p.x, p.y - 1);
				case 'S' : return new Point(p.x + 1, p.y);
				case 'E' : return new Point(p.x, p.y + 1);
			}
			return null;
		}

		private static void readGrid(Scanner sc) {
			for(int i = 0; i < numRows; ++i) {
				String line = sc.nextLine();
				for(int j = 0; j < line.length(); ++j) {
					grid[i][j] = line.charAt(j);
				}
				//System.out.println(Arrays.toString(grid[i]));
			}
			//System.out.println(" <===> ");
		}
	}

	class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "[x=" + x + ", y=" + y + "]";
		}
		
		
	}