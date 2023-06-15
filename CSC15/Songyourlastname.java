package Brian;

public class Songyourlastname {    
    public static final int COOL_BOTTLES = 10;  // Class constant
    
    public static void lyrics() {               // lyrics() method for displaying lyrics of the song
      int bottles = COOL_BOTTLES;               // Variable to store no of bottles since class constant can't be modified
      int bottlesOnWall = bottles - 1;          // Variable to store no of bottles after buying from the store
         
         for(int i = bottles; i > 0; i--) {            
            System.out.println(bottles + " bottles of the juice on the wall, " + bottles + " bottles of juice");
           
         if(i == 1) {
            System.out.println("Take one down and pass it around, no more bottles of juice on the wall.");
            System.out.println("Go to the store and buy some more, " + bottlesOnWall + " bottles of juice on the wall.");
         }
         bottles--;     
      }
   }
         
   @SuppressWarnings("static-access")
   public static void main(String args[]) {
      Songyourlastname s = new Songyourlastname();
      s.lyrics();
   }
}   
