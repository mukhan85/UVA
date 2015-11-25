package graph.solved;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Prob_200 {

		static Map<Character, List<Character>> adjList = new HashMap<Character, List<Character>>();
		static Stack<Character> sortedVertes = new Stack<Character>();
		static Map<Character, Boolean> isVisited = new HashMap<Character, Boolean>();
		static Set<Character> vertSet = new HashSet<Character>();
		
		public static void main(String... args) throws FileNotFoundException {
			
			Scanner sc = new Scanner(new File("input"));
			//Scanner sc = new Scanner(System.in);
			boolean isFirstRead = true;
			String firstLine = null, secondLine = null;
			
			while(true) {
				
				if(isFirstRead) {
					firstLine = getLineIfExists(sc); 
					secondLine = getLineIfExists(sc);
					isFirstRead = false;
				} else {
					firstLine = secondLine;
					secondLine = getLineIfExists(sc);
				}

				if(firstLine == null || secondLine == null || firstLine.equals("#") || secondLine.equals("#")) {
					break;
				}
				constructGraph(firstLine, secondLine, adjList);			
			}
			
			initIsVisited();
			
			// done constructing the graph, now just run DFS on the constructed graph.		
			for(Character vert : adjList.keySet()) {		
				if(!done()) {
					initIsVisited();
					dfs(vert);
				}
			}
			
			while(!sortedVertes.isEmpty()) {
				System.out.print(sortedVertes.pop());			
			}
		}

		private static void initIsVisited() {
			// Init isVisited map.
			for(Character vert : adjList.keySet()) {
				isVisited.put(vert, Boolean.FALSE);
				List<Character> list = adjList.get(vert);
				vertSet.add(vert);
				for(Character ll : list) {
					isVisited.put(ll, Boolean.FALSE);
					vertSet.add(ll);
				}
			}
			sortedVertes = new Stack<Character>();
			
			for(Character vert : vertSet) {
				if(adjList.get(vert) == null) {
					adjList.put(vert, new ArrayList<Character>());
				}
			}
		}

		// We need to make sure we have visited all vertices.
		// Otherwise re-try DFS from the next vertex.
		private static boolean done() {
			for(Character vert : isVisited.keySet()) {
				if(!isVisited.get(vert)) {
					return false;
				}
			}
			
			return true;
		}

		private static void dfs(Character vert) {
			isVisited.put(vert, Boolean.TRUE);
			for (Character nextVert : adjList.get(vert)) {
				if (!isVisited.get(nextVert)) {
					dfs(nextVert);
				}
			}

			sortedVertes.add(vert);
		}

		private static void constructGraph(String firstLine, String secondLine, Map<Character, List<Character>> adjList) {
			int index = 0; 
			while(index < firstLine.length() && index < secondLine.length()) {
				if(firstLine.charAt(index) != secondLine.charAt(index)) {
					
					if(adjList.containsKey(firstLine.charAt(index))) { // Alredy discovered vertext.
						adjList.get(firstLine.charAt(index)).add(secondLine.charAt(index));
					} else { // Found new vertex
						List<Character> charList = new ArrayList<Character>();
						charList.add(secondLine.charAt(index));
						adjList.put(firstLine.charAt(index), charList);
					}
					return;
				} else {
					++index;
				}
			}
		}

		private static String getLineIfExists(Scanner sc) {
			if(sc.hasNextLine())
				return sc.nextLine();
			else return null;
		}
	}