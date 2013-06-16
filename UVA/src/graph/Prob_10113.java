package graph;
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
import java.util.Stack;


public class Prob_10113{
	static Digraph graph = new Digraph();
	
	public static void main(String... args) throws FileNotFoundException {
		//Scanner sc = new Scanner(new File("input"));
		Scanner sc = new Scanner(System.in);
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
		
		sc.close();
	}

	private static void processQuery(String[] line) {
		// ? sock = shirt
		// 0   1  2   3
		String fromVert = line[1];
		String toVert = line[3];
		GraphProcessor processor = new GraphProcessor(graph, fromVert, toVert);
		// 5 sock = 2 shirt
		// ? shirt = ? pant
		// 45 pant = 188 shirt
		if(fromVert.equals(toVert)) {
			System.out.println("1 " + fromVert + " = 1 " + toVert);
		} else if(processor.hasPath()) {
			processor.getPath();
			System.out.println(processor.pathCost.denum + " " + fromVert + " = " + processor.pathCost.num + " " + toVert);
		} else {
			//System.out.println("No path: " + fromVert + " -> " + toVert);
			System.out.println("? " + fromVert + " = ? " + toVert);
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
	Map<String, Edge> fromToEdge;
	
	public Digraph() {
		this.adjList = new LinkedHashMap<String , List<Edge>>();
		this.fromToEdge = new HashMap<String, Edge>();
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
		this.fromToEdge.put(e.fromVert + e.toVert, e);
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
		return "Edge [fromVert=" + fromVert + ", toVert=" + toVert + ", weight=" + weight + "]";
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
	
	void normalize() {
		// use GCD algo to bring num, denum to ...
		int commonGcd = gcd(num, denum);
		if(commonGcd > 1) {
			this.num = num/commonGcd;
			this.denum = denum/commonGcd;
		}
	}
	
	public int gcd(int a, int b) {
		if(b == 0){
			return a;
		} else {
			return gcd(b, a % b);
		}
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
	Fraction pathCost = new Fraction(1, 1);
	
	public Fraction multiply(Fraction one, Fraction other) {
		Fraction result = new Fraction(1,1);
		result.num = one.num * other.num;
		result.denum = one.denum * other.denum;
		result.normalize();
		return result;
	}
	
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
		if(marked.get(sourceVert) != null && !this.marked.get(sourceVert)) {
			this.marked.put(sourceVert, Boolean.TRUE);
			if(this.dest.equals(sourceVert)) return;
			for(Edge nextVert : graph.getAdjVerts(sourceVert)) {
				if(!this.marked.get(nextVert.toVert)) {
					this.edgeTo.put(nextVert.toVert, sourceVert);
					dfs(nextVert.toVert);					
				}
			}
		}
	}
	
	public Boolean hasPath() {
		return marked.get(dest) != null && this.marked.get(dest);
	}
	
	public void getPath() {
		String pathPointer = this.dest;
		Stack<String> path = new Stack<String>();

		while(pathPointer != null) {
			path.push(pathPointer);
			pathPointer = this.edgeTo.get(pathPointer);
		}
		
		while(!path.isEmpty()) {			
			String fromVert = path.pop();
			if(path.isEmpty()) {
				break;
			}
			String toVert = path.peek();
			//System.out.println(this.graph.fromToEdge.get(fromVert + toVert));
			this.pathCost = multiply(this.pathCost, graph.fromToEdge.get(fromVert + toVert).weight);
		}
	}
}
