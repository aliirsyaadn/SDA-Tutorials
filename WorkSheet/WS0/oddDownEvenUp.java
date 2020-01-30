import java.util.Scanner;

public class oddDownEvenUp {

	public static void main(String[] args){
		Scanner input;
		String text, resultText;

		input = new Scanner(System.in);

		text = input.next();

		resultText = "";

		for(int i = 0; i < text.length(); i++){
			char chText = text.charAt(i);

			resultText += (i % 2 == 0) ? Character.toLowerCase(chText) : Character.toUpperCase(chText);
		}

		System.out.print(resultText);
	}



}