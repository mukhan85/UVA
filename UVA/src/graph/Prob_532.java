package graph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Prob_532 {
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			int level, row, column;
			while(true) {
				String [] line = sc.nextLine().split("\\s");
				if(line.length != 3) {
					continue;
				}
				level = Integer.parseInt(line[0]);
				row = Integer.parseInt(line[1]);
				column = Integer.parseInt(line[2]);
				if(level == 0 && row == 0 && column == 0) {
					break;
				}
				
				Graph2 g = new Graph2(level, row, column);
				for(int i = 0; i < level; ++i) {
					List<List<Character>> levelPlane = new ArrayList<List<Character>>();
					g.grid.add(levelPlane);
					
					for(int j = 0 ; j < row; ++j) {
						String nextRow = sc.nextLine();
						if(nextRow.length() == column) {
							g.addEdge(i, j, nextRow);	
						} else {					
							--j;
						}
					}
				}
				int result = g.bfs();
				if(result == -1) {
					System.out.println("Trapped!");
				} else {
					System.out.println("Escaped in " + result + " minute(s).");
				}
			}
			sc.close();
		}
	}

	class Graph2 {
		
		List<List<List<Character>>> grid;
		Vertex startPoint;
		Vertex endPoint;
		Queue<Vertex> queue;
		int maxLevel, maxRow, maxColumn;
		
		int [][][] distTo;
				
		int [] posX = {0,  0, -1, 1,  0, 0};
		int [] posY = {0,  0,  0, 0, -1, 1};
		int [] posL = {1, -1,  0, 0,  0, 0};
		
		public Graph2 (int maxLevel, int maxRow, int maxColumn) {
			grid = new ArrayList<List<List<Character>>>();
			distTo = new int [30][30][30];
			for(int[][] eachLevel : distTo) {
				for(int [] rowLevel : eachLevel) {
					Arrays.fill(rowLevel, -1);
				}
			}
			
			queue = new LinkedList<Vertex>();
			this.maxColumn = maxColumn;
			this.maxLevel = maxLevel;
			this.maxRow = maxRow;
		}
		
		public int bfs() {
			queue.add(startPoint);
			distTo[startPoint.level][startPoint.row][startPoint.column] = 0;
			
			while(!queue.isEmpty()) {
				Vertex currentVertex = queue.poll();
				if(currentVertex.equals(endPoint)) {
					return distTo[currentVertex.level][currentVertex.row][currentVertex.column];
				}
				
				for(int i = 0; i < 6; ++i) {
					Vertex nextVertex = move(currentVertex, i);
					if(isValidVertex(nextVertex) && !isVisited(nextVertex)) {
						nextVertex.value = getValue(nextVertex);
						queue.add(nextVertex);
						setDistTo(currentVertex, nextVertex);
					}
				}
			}
			return -1;
		}
		
		private boolean isVisited(Vertex nextVertex) {
			return distTo[nextVertex.level][nextVertex.row][nextVertex.column] != -1;
		}

		private void setDistTo(Vertex currentVertex, Vertex nextVertex) {
			distTo[nextVertex.level][nextVertex.row][nextVertex.column] = 
					distTo[currentVertex.level][currentVertex.row][currentVertex.column] + 1;
		}

		private boolean isValidVertex(Vertex vert) {
			if(vert.level < this.maxLevel && vert.level >= 0
			   && vert.row < this.maxRow && vert.row >= 0 
			   && vert.column < this.maxColumn && vert.column >= 0) {
				return grid.get(vert.level).get(vert.row).get(vert.column) != '#';
			}
				
			return false;
		}

		private Vertex move(Vertex currentPoint, int i) {
			Vertex v = new Vertex();
			v.level = currentPoint.level + posL[i];
			v.row = currentPoint.row + posX[i];
			v.column = currentPoint.column + posY[i];
			
			return v;
		}

		private char getValue(Vertex v) {
			return grid.get(v.level).get(v.row).get(v.column);
		}

		public void addEdge(int level, int row, String nextRow) {
			//.###.
			List<Character> rowList = new ArrayList<Character>();
			for(int col = 0; col < nextRow.length(); ++col) {
				if(nextRow.charAt(col) == 'S') {
					startPoint = new Vertex(level, row, col, 'S');
				} else if(nextRow.charAt(col) == 'E') {
					endPoint = new Vertex(level, row, col, 'E');
				}
				rowList.add(nextRow.charAt(col));
			}
			grid.get(level).add(rowList);
		}
		
		
	}

	class Vertex {
		int level;
		int row;
		int column;
		char value;
		
		public Vertex(int level, int row, int column, char value) {
			this.level = level;
			this.row = row;
			this.column = column;
			this.value = value;
		}

		public Vertex() {}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + column;
			result = prime * result + level;
			result = prime * result + row;
			result = prime * result + value;
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
			Vertex other = (Vertex) obj;
			if (column != other.column)
				return false;
			if (level != other.level)
				return false;
			if (row != other.row)
				return false;
			if (value != other.value)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "[" + level + ", " + row + ", " + column
					+ ", " + value + "]";
		}
		
	}