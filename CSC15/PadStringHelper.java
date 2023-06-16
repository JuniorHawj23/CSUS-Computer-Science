package Brian;

import java.util.Scanner;

public class PadStringHelper {

   public static void runThree() {
      for(int i=0; i<3; i++) {
         startStringBuildingProgram();
      }
   }

   public static void startStringBuildingProgram() {
      StringBuffer sb = new StringBuffer();
      Scanner sc = new Scanner(System.in);
      // to store result string
      String word = inputString(sc);
      //if length given by user is more than word's length then append '-'
      int length = inputLength(sc);
            
      if (length > word.length()) {
         for(int i = 0; i < length - word.length(); i++) {
         sb.append("-");
         }
         sb.append(word);
      } else {
      sb.append(word);
      }
      System.out.println("The resulting string is " + sb.toString());
   }

   public static String inputString(Scanner sc) {
      // user input for a string
      System.out.print("Please enter a string ");
      return sc.nextLine();
   }

   public static int inputLength(Scanner sc) {
      //user input for a length
      System.out.print("Please enter the length of the result string ");
      return sc.nextInt();
   }   
}
