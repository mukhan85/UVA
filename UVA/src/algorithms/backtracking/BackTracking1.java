package algorithms.backtracking;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Problem # 331
public class BackTracking1 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Scanner sc= new Scanner(new File("input"));
		//Scanner sc= new Scanner(System.in);
		int caseNumber = 1;
		while(sc.hasNextInt()) {
			int numElems = sc.nextInt();
			
			if(numElems == 0) {
				break;
			}
			int [] arr = new int[numElems];
			
			for(int i = 0; i < numElems; ++i) {
				arr[i] = sc.nextInt();
			}

			int answer = 0;
			
			if(!isSorted(arr)) {
				answer = dfs(arr);
			}
			
			System.out.printf("There are %d swap maps for input data set %d.\n", answer, caseNumber);
			++caseNumber;
		}
		
		sc.close();
	}

	private static int dfs(int[] arr) {
		if(isSorted(arr)) {			
			return 1;
		}
		int count = 0;
		
		for(int i = 0; i < arr.length - 1; ++i) {
			if(arr[i] > arr[i+1]) {
				swap(i, i+1, arr);
				count += dfs(arr);
				swap(i, i+1, arr);
			}
		}
		
		return count;
	}

	private static void swap(int i, int j, int[] arr) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	private static boolean isSorted(int[] arr) {
		for(int i = 0; i < arr.length - 1;++i) {
			if(arr[i] > arr[i+1]) {
				return false;
			}
		}
		return true;
	}	
}
