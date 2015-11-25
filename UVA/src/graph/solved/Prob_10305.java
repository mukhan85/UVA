package graph.solved;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class Prob_10305 {
		static List<Integer> sortedVerts = new ArrayList<Integer>();
		static List<List<Integer>> adjList = new ArrayList<List<Integer>>();
		static boolean [] marked ;
		
		public static void main(String... args) throws FileNotFoundException {
			
			//Scanner sc = new Scanner(new File("input"));
			Scanner sc = new Scanner(System.in);
			while(true) {
				int numTask = sc.nextInt();
				int numEdges = sc.nextInt();
				
				if(numEdges == 0 && numTask == 0) {
					break;
				}
				
				marked = new boolean[numTask];
				Arrays.fill(marked, false);
				
				for(int i = 0; i < numTask; ++i) {
					adjList.add(new ArrayList<Integer>());
				}
				
				// construct adjVert			
				for(int i = 0; i < numEdges; ++i) {				 
					int fromVert = sc.nextInt() - 1;
					int toVert = sc.nextInt() - 1;

					adjList.get(fromVert).add(toVert);
				}
						
				for(int i = 0; i < marked.length; ++i) {
					if(!marked[i]) {
						dfs(i);
					}
				}

				Collections.reverse(sortedVerts);
				for(int i = 0; i < sortedVerts.size() -1 ; ++i) {
					System.out.print((sortedVerts.get(i) + 1) + " ");
				}
				System.out.println(sortedVerts.get(sortedVerts.size() - 1) + 1);
				reset();
			}
		}

		private static void reset() {
			sortedVerts = new ArrayList<Integer>();
			adjList = new ArrayList<List<Integer>>();
		}

		private static void dfs(int vert) {
			marked[vert] = true;
			for(int nextVert : adjList.get(vert)) {
				if(!marked[nextVert]) {
					dfs(nextVert);
				}
			}
		
			sortedVerts.add(vert);
		}	
	}