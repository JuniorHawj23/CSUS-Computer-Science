package Brian;

import java.util.Scanner;
import java.util.Random;

public class GuessTheNumberHelper {
	
    public static void playGame() {
        // create one scanner object thats used across the full program
        Scanner scan = new Scanner(System.in);

        // loop while user wants to play
        String userPlay = "y";

        /* 
        used string methods here, but it would likely be better to use a char check here but we have not been introduced
        to the toUpperCase method in char so unsure if would be docked points if so
        I can see how this would generate errors tho, as entering a sentence / string isnt the 'really' the same when checking / comparing 
        it to a single character
        */
        while (userPlay.toUpperCase().equals("Y")) {
            randomGame(scan);

            // prompt the user if they would like to play again
            System.out.print("\nWould you like to play again? Enter Y or N ");
            userPlay = scan.next();
        }
    }

    public static void randomGame(Scanner scan) {
        // Create a random and scanner obj
        Random rand = new Random();

        // range of 1 to 100
        int randNum = rand.nextInt(100) + 1;
        // print the number just so game wont last forever
        // System.out.println(randNum);

        // Create an integer for number of tries / could be a class property, thats not a constant
        int totalAttempts = 0;

        // Create a string counter for history of numbers entered by user
        String numberHistory = "";

        // set a boolean variable to have game running
        boolean runGame = true;

        // while game is not over
        while (runGame) {
            // display enter num prompt
            System.out.print("Please Enter an Integer between 1 and 100: ");

            // input number
            int userGuess = scan.nextInt();

            // increment number of tries var
            totalAttempts++;

            // append to array obj here; omitted
            numberHistory += userGuess + " ";

            // check if users # equals secret #
            if (userGuess == randNum) {
            	// declare victory
                System.out.printf("You had %d Tries\n", totalAttempts);
                System.out.println("Here is the history of your attempts");

                // could split the string here and create an array obj
                String[] allGuesses = numberHistory.split(" ");

                for (int i = 0; i < allGuesses.length; i++) {
                    System.out.println(allGuesses[i]);
                }
                // System.out.println(Arrays.toString(allGuesses));

                runGame = false;
            }

            if (userGuess > randNum) {
                // if number greater, display 'too high'
                System.out.println("Your Number is Too High");
            } 
            else if (userGuess < randNum) {
                // if number less,  display 'too low'
                System.out.println("Your Number is Too Low");
            }
        }
    }
}
