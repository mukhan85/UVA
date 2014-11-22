import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Next problem is 10507
public class Main {
	static int counter = 0;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Scanner sc= new Scanner(new File("input"));
		//Scanner sc= new Scanner(System.in);

		while(sc.hasNextLine()) {
			int size = Integer.parseInt(sc.nextLine());
			int[] id = new int[size];
			int[] sz = new int[size];
			
			Map<Character, Integer> data = new HashMap<Character, Integer>();
			
			for(int i = 0; i < id.length; ++i) {
				id[i] = i;
				sz[i] = 1;
			}
			
			int numConnections = Integer.parseInt(sc.nextLine());
			char[] awake = sc.nextLine().toCharArray();
			addToMap(data, awake);
			
			for(int i = 0; i < numConnections; ++i) {
				char[] newConnection = sc.nextLine().toCharArray();
				addToMap(data, newConnection);
				
				int p =data.get(newConnection[0]); 
				int q =data.get(newConnection[1]);
				
				//System.out.println("Union: " + newConnection[0] + "=" + p + ", " + newConnection[1] + "=" + q);
				union(p, q, id, sz);
			}
			printMap(data, id, sz);
		}
	}

	private static void printMap(Map<Character, Integer> data, int[] id, int[] sz) {
		System.out.println(Arrays.toString(sz));
		System.out.println(Arrays.toString(id));
		for(Character ch : data.keySet()) {
			System.out.println(ch + " -> " + data.get(ch));
		}
	}

	private static void union(int p, int q, int[] id, int [] sz) {
		if(!isConnected(p, q, id)) {
			int idp = root(p, id);
			int idq = root(q, id);

			if(sz[idp] > sz[idq]) {
				id[idp] = id [idq];
				sz[idp] += sz[idq];
			} else {
				id[idq] = id[idp];
				sz[idq] += sz[idp];
			}
		}
	}

	private static boolean isConnected(int p, int q, int[] id) {
		return root(p, id) == root(q, id);
	}

	private static int root(int p, int[] id) {
		while(p != id[p]) {
			p = id[p];
		}
		return p;
	}

	private static void addToMap(Map<Character, Integer> data, char[] newConnection) {
		for(Character ch : newConnection) {
			if(!data.containsKey(ch)) {
				data.put(ch, counter);
				++counter;
			}
		}
	}
}