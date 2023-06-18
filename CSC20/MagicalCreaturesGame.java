package Brian;

/*
*@author Brian
*A client or driver program which allows the uses to play a war game using the MagicalCreature class.
*/

import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

// Reads the creature information from a text file called creatures.txt
public class MagicalCreaturesGame {
   public static File file = new File("creatures.txt");
   static Scanner scan;
   public static ArrayList<MagicalCreature> creatures = new ArrayList<>();
   public static int moves=50;
   public static int alive;

   /*
   *Creates and populate an Arraylist of Magical Creatures by processing one line at a time of the data file.
   *The line of text is to be split using the split method into an array of String containing four String tokens 
   *representing the four attributes.
   */
   public static void main(String[] args) {
      String line;
      String[] temp;
      try {
         scan = new Scanner(file);
      }
      catch(Exception e) {
         System.out.println("Error opening the file!");
      }
      while(scan.hasNextLine()) {
         line = scan.nextLine();
         temp = line.split(",");
         switch(temp[1]) {
         case "Dragon":
         creatures.add(new MagicalCreature(temp[0], temp[1], temp[2], Integer.valueOf(temp[3])));
         break;
         case "Elf":
         creatures.add(new MagicalCreature(temp[0], temp[1], temp[2], Integer.valueOf(temp[3])));
         break;
         case "Goblin":
         creatures.add(new MagicalCreature(temp[0], temp[1], temp[2], Integer.valueOf(temp[3])));
         break;
         case "Genie":
         creatures.add(new MagicalCreature(temp[0], temp[1], temp[2], Integer.valueOf(temp[3])));
         break;
            default:
               creatures.add(new MagicalCreature(temp[0], temp[1], temp[2], Integer.valueOf(temp[3])));
         }
         alive++;
      }
      war();
   }
   
   /*
   *The number of moves is decided by the user. Each move consists of an 
   *attack by one magical creature on another. The attacker and victim both need to adhere to the rules of the 
   *game which will decide if the attacker is eligible to kill or the victim is a candidate that can die. In each 
   *move, the selection of the attacker and victim is done randomly using the Random class.
   */
   public static void war() {
      Random rand = new Random();
      int alpha, beta, i=0;
      System.out.print("How many moves ? ");
      scan = new Scanner(System.in);
      moves = scan.nextInt();
      while(i<=moves) {
      alpha = rand.nextInt(creatures.size());
      beta = rand.nextInt(creatures.size());
      
      System.out.println("Killer = "+creatures.get(alpha).getType()+" "+creatures.get(alpha).getName());
      System.out.println("Victim = "+creatures.get(beta).getType()+" "+creatures.get(alpha).getName());
      creatures.get(alpha).kill(creatures.get(beta));
      
      if(creatures.get(alpha).getAlive()==false) {
      System.out.println("Removing the Dead");
      creatures.remove(alpha);
      }
      
      if(creatures.get(beta).getAlive()==false) {
      System.out.println("Removing the Dead");
      creatures.remove(beta);
      }
      displayCreatures();
      i++;
      }
   }
   
   // The list of creatures that are still alive is printed.
   public static void displayCreatures() {
      System.err.println("----------------------------------------");
      System.err.println("Creatures Still Alive after this round:"+creatures.size());
      System.err.println("----------------------------------------");
      
      for(MagicalCreature e: creatures) {
         System.err.println(e.toString());
      }
   }
}