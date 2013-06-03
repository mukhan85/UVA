import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
	public static void main(String... args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input"));
		
		while(sc.hasNextLine()) {
			String [] line = sc.nextLine().split("\\s");
			if(".".equals(line[0])) {
				break;
			}
			
			if("!".equals(line[0])) {
				processAssertion(line);
			} else if("?".equals(line[0])) {
				processQuery(line);
			}
		}
	}

	private static void processQuery(String[] line) {
		// ? sock = shirt
	}

	private static void processAssertion(String[] line) {
		// ! 6 shirt = 15 sock
		
	}
}

class Digraph {
	Map<String, List<Edge>> adjList;
	
	public Digraph() {
		this.adjList = new HashMap<String , List<Edge>>();
	}
	
	public void addEdge(Edge e) {
		// Add both Vertices into the Vertex set.
		conditionalAddEdge(e.fromVert, e);
		conditionalAddEdge(e.toVert, e);
	}

	private void conditionalAddEdge(String vertName, Edge e) {		
		List<Edge> edges = new ArrayList<Edge>();
		if(!this.adjList.containsKey(vertName)) {			
			edges.add(e);
			this.adjList.put(vertName, edges);
		} else {
			edges = this.adjList.get(vertName);
			edges.add(e);
		}
	}

	public Iterable<Edge> getAdjVerts(String vertName) {
		return this.adjList.get(vertName);
	}
	
}

class Edge {
	String fromVert;
	String toVert;
	Fraction weight;
	
	public Edge(String fromVert, String toVert, Fraction weight) {
		this.fromVert = fromVert;
		this.toVert = toVert;
		this.weight = weight;		
	}
	
}

class Fraction {
	int num;
	int denum;
	
	public Fraction (int num, int denum) {
		this.num = num;
		this.denum = denum;
		normalize();
	}

	private void normalize() {
		// use GCD algo to bring num, denum to ...
		
	}
	
	
}
