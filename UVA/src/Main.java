import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
	static Digraph graph = new Digraph();
	
	public static void main(String... args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input"));
		
		while(sc.hasNextLine()) {
			String [] line = sc.nextLine().split("\\s");
			if(".".equals(line[0])) {
				break;
			}
			
			if("!".equals(line[0])) {
				addNewEdges(line);
			} else if("?".equals(line[0])) {
				processQuery(line);
			}
		}
	}

	private static void processQuery(String[] line) {
		// ? sock = shirt
		// 0   1  2   3
		String fromVert = line[1];
		String toVert = line[3];
		GraphProcessor processor = new GraphProcessor(graph, fromVert, toVert);
		
		if(processor.hasPath()) {
			System.out.println(processor.getPath());
		} else {
			System.out.println("No path: " + fromVert + " -> " + toVert);
		}
	}

	private static void addNewEdges(String[] line) {
		// ! 6 shirt = 15 sock
		// 0 1   2   3  4   5
		Fraction weight = new Fraction(Integer.parseInt(line[4]), Integer.parseInt(line[1]));
		Edge e = new Edge(line[2], line[5], weight);
		graph.addEdge(e);
		
		// Add the reverse direction edge as well.
		weight = new Fraction(Integer.parseInt(line[1]), Integer.parseInt(line[4]));
		e = new Edge(line[5], line[2], weight);
		graph.addEdge(e);
	}
}

class Digraph {
	Map<String, List<Edge>> adjList;
	boolean marked[] ;
	
	public Digraph() {
		this.adjList = new LinkedHashMap<String , List<Edge>>();
	}
	
	public void addEdge(Edge e) {
		// Add both Vertices into the Vertex set.
		conditionalAddEdge(e);
	}

	private void conditionalAddEdge(Edge e) {		
		List<Edge> edges = new ArrayList<Edge>();
		if(!this.adjList.containsKey(e.fromVert)) {			
			edges.add(e);
			this.adjList.put(e.fromVert, edges);
		} else {
			edges = this.adjList.get(e.fromVert);
			edges.add(e);
		}
	}

	public Iterable<Edge> getAdjVerts(String vertName) {
		return this.adjList.get(vertName);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String verts: this.adjList.keySet()) {
			sb.append(verts + " : " + getAdjVerts(verts) + "\n");
		}
		
		return sb.toString();
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

	@Override
	public String toString() {
		return "Edge [fromVert=" + fromVert + ", toVert=" + toVert
				+ ", weight=" + weight + "]";
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

	@Override
	public String toString() {
		return "[" + num + "/" + denum + "]";
	}
}

class GraphProcessor {
	Digraph graph;
	String source;
	String dest;
	Map<String, Boolean> marked;
	Map<String, String> edgeTo;
	
	public GraphProcessor(Digraph graph, String source, String dest) {
		this.graph = graph;
		this.source = source;
		this.dest = dest;
		this.marked = new HashMap<String, Boolean>(graph.adjList.size());
		this.edgeTo = new HashMap<String, String>(graph.adjList.size());
		
		for(String eachVert : graph.adjList.keySet()) {
			this.marked.put(eachVert, Boolean.FALSE);
		}
		this.edgeTo.put(source, null);
		
		dfs(source);
	}
	
	public void dfs(String sourceVert) {
		if(!this.marked.get(sourceVert)) {
			this.marked.put(sourceVert, Boolean.TRUE);
			for(Edge nextVert : graph.getAdjVerts(sourceVert)) {
				if(!this.marked.get(nextVert.toVert)) {
					this.edgeTo.put(nextVert.toVert, sourceVert);
					dfs(nextVert.toVert);					
				}
			}
		}
	}
	
	public Boolean hasPath() {
		return this.marked.get(dest);
	}
	
	public String getPath() {
		StringBuilder sb = new StringBuilder();
		String pathPointer = this.dest;
		while(pathPointer != null) {
			sb.append(pathPointer + " -> ");
			pathPointer = this.edgeTo.get(pathPointer);
		}
		return sb.toString();
	}
}
