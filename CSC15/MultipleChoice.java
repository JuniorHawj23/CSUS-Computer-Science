package Brian;

import java.util.Scanner;

public class MultipleChoice {

	public static void main(String[] args) {
			
		Scanner scan = new Scanner(System.in);
		String[] answers = {"c", "a"};
		String[] responses = {"", ""};

		System.out.println("What year was basketball invented?");
		System.out.println("a. 1887");
		System.out.println("b. 1883");
		System.out.println("c. 1891");
		System.out.println("d. 1893");
		responses[0] = scan.next();
		
		System.out.println("When did the NBA add the 3 point line?");
		System.out.println("a. 1979");
		System.out.println("b. 1984");
		System.out.println("c. 1989");
		System.out.println("d. 1975");
		responses[1] = scan.next();
		
		int score = 0;
		for(int i=0; i<2; i++) {
			
			if(answers[i].equalsIgnoreCase(responses[i])) {
			score++;
			}
		}
		System.out.println("Your score is " +score+ "/2");
	}
}
