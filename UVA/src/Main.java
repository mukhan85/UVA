import java.io.IOException;
import java.util.*;


// Next problem is 10067
public class Main {

    public static void main(String[] args) throws NumberFormatException, IOException {

        Scanner sc = new Scanner(Main.class.getClassLoader().getResourceAsStream("input"));
        //Scanner sc= new Scanner(System.in);

        while (sc.hasNextLine()) {
            int numTests = Integer.parseInt(sc.nextLine());
            for(int i = 0; i < numTests; ++i) {
                int start = readStateAsInt(sc);
                int target = readStateAsInt(sc);
                int[] forbiddednCombinations = readForbiddend(sc);

                System.out.println("Start: " + start);
                System.out.println("Target: " + target);
                System.out.println("Forbidden: " + Arrays.toString(forbiddednCombinations));

                System.out.println(solveTestCase(forbiddednCombinations));
            }
        }

        Graph graph = new Graph(5);
        sc.close();
    }

    private static int solveTestCase(int[] forbiddednCombinations) {
        Graph graph = new Graph(forbiddednCombinations);
        graph.printGraph();
        return -1;
    }

    private static int readStateAsInt(Scanner sc) {
        String data = sc.nextLine().replace(" ", "");
        return Integer.parseInt(data);
    }

    private static int[] readForbiddend(Scanner sc) {
        int numForbidden = Integer.parseInt(sc.nextLine());
        int[] forbiddednCombinations = new int[numForbidden];
        for(int j = 0; j < numForbidden; ++j) {
            forbiddednCombinations[j] = readStateAsInt(sc);
        }
        return forbiddednCombinations;
    }
}

class Graph {
    private final Map<Integer, List<Integer>> adjList;
    private int numVerts;

    public Graph(int[] forbiddednCombinations) {
        this.adjList = new HashMap<>();
    }

    public Graph(int numVerts) {
        this.adjList = new HashMap<>();
        for(int i = 0; i < numVerts; ++i) {
            this.adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(Integer fromVert, Integer toVert) {
        this.adjList.get(fromVert).add(toVert);
        this.adjList.get(toVert).add(fromVert);
    }

    public void printGraph() {
        for(Integer eachVert : this.adjList.keySet()) {
            System.out.print(eachVert + " -> ");
            for(Integer toVert : this.adjList.get(eachVert)) {
                System.out.print(toVert + " ");
            }
            System.out.println();
        }
    }

    public int getNumVerts() {
        return numVerts;
    }

    public List<Integer> getAdjVerts(Integer vert) {
        return this.adjList.get(vert);
    }
}

class BreadthFirstSearch {
    private final Graph graph;
    private final boolean [] isVisited;
    private final Queue<Integer> queue;
    private final int [] distTo;

    public BreadthFirstSearch(Graph g) {
        this.graph = g;
        this.isVisited = new boolean[g.getNumVerts()];
        Arrays.fill(this.isVisited, false);

        this.queue = new LinkedList<>();
        this.distTo = new int[g.getNumVerts()];
        Arrays.fill(this.distTo, Integer.MAX_VALUE);
    }

    public void bfs(Integer source) {

        if(!this.isVisited[source]) {
            this.queue.add(source);
            this.distTo[source] = 0;
        }

        while(!this.queue.isEmpty()) {
            Integer nextVert = this.queue.poll();

            for(Integer eachAdjVert : this.graph.getAdjVerts(nextVert)) {
                if(!isVisited[eachAdjVert]) {
                    this.distTo[eachAdjVert] = this.distTo[nextVert] + 1;
                    queue.add(eachAdjVert);
                }
            }
            isVisited[nextVert] = true;
        }
    }
}