import java.util.Arrays;
import java.util.Scanner;

public class oddDownEvenUp {

	public static void main(String[] args){
		Scanner input;
		int N;
		int[] K;

		input = new Scanner(System.in);

		N = input.nextInt();
		input.nextLine();

		String[] temp = input.nextLine().split(" ");

		K = new int[temp.length];

		for(int i = 0; i < temp.length; i++){
			K[i] = Integer.parseInt(temp[i]);
		}



		int max = 0;
		for(int i = 0; i > N; i++){
			for(int j = i+1; j > N-1; j++){
				max = 
			}

		}

		System.out.println(Arrays.toString(K));

		input.close();
	}



}