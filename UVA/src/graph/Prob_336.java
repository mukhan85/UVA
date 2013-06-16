package graph;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Prob_336 {
		
		public static void main(String... args) throws FileNotFoundException {
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			int caseCount = 1;
			while(true) {			
				int numPairs = sc.nextInt();
				if(numPairs == 0) break;
				Graph g = new Graph();
				
				for(int i = 0; i < numPairs; ++i) {
					int fromVert = sc.nextInt(); 
					int toVert = sc.nextInt();
					g.addEdge(fromVert, toVert);
				}

				// Read the queries.
				while(true) {
					int sourceVert = sc.nextInt();
					int distFromSourceVert = sc.nextInt();
					if(sourceVert == 0 && distFromSourceVert == 0 ) {
						break;
					}
					g.bfs(sourceVert);
					
					//Case 1: 5 nodes not reachable from node 35 with TTL = 2.
					System.out.println("Case " + caseCount + ": " + g.getNumUnvisitedVerts(distFromSourceVert) +
							" nodes not reachable from node " + sourceVert + " with TTL = " + distFromSourceVert + ".");
					++caseCount;
				}
			}
			sc.close();
		}
	}

	class Graph {

		private Map<Integer, List<Integer>> adjList;	
		private Map<Integer, Integer> distToVert;	
		private Queue<Integer> queue;
		
		public Graph() {
			this.queue = new LinkedList<Integer>();		
			this.adjList = new HashMap<Integer, List<Integer>>();
		}
			
		public int getNumUnvisitedVerts(int distFromSourceVert) {
			int counter = 0;
			for(Integer vertsDist : this.distToVert.keySet()) {
				if(this.distToVert.get(vertsDist) > distFromSourceVert) {
					//System.out.println("Unreachable Verts: " + vertsDist);
					++counter;
				}
			}
			
			return counter;
		}

		public void addEdge(int fromVert, int toVert) {
			if(this.adjList.get(fromVert) == null) {
				this.adjList.put(fromVert, new ArrayList<Integer>());
			}
			if(this.adjList.get(toVert) == null) {
				this.adjList.put(toVert, new ArrayList<Integer>());
			}
			
			this.adjList.get(fromVert).add(toVert);		
			this.adjList.get(toVert).add(fromVert);
		}
		
		public List<Integer> getAdjList(int vert) {
			return this.adjList.get(vert);
		}
		
		public void bfs(int sourceVert) {
			reset();
			this.queue.add(sourceVert);
			this.distToVert.put(sourceVert, 0);
			
			while(!queue.isEmpty()) {
				int currentVert = this.queue.poll();
				
				for(int nextVert : getAdjList(currentVert)) {
					if(this.distToVert.get(nextVert) == null) {
						queue.add(nextVert);
						this.distToVert.put(nextVert, this.distToVert.get(currentVert) + 1);
					}
				}			
			}
			
			// Check for unconnected components.
			for(int verts: this.adjList.keySet()) {
				if(distToVert.get(verts) == null) {
					this.distToVert.put(verts, Integer.MAX_VALUE);
				}
			}
		}

		private void reset() {
			this.distToVert = new HashMap<Integer, Integer>();
		}
		
	}

	class Pair {
		int fromVert, toVert;
		
		public Pair(int from, int to) {
			this.fromVert = from;
			this.toVert = to;
		}

		@Override
		public String toString() {
			return "[" + fromVert + " -> " + toVert + "]";
		}	
	}

