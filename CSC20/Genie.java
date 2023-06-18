package Brian;

/*
*@author Brian
*Genie class which is a sub class of MagicalCreatures
*/
public class Genie extends MagicalCreature {
   public boolean wand;
   
   // Constructor which calls the parent class constructor.
   public Genie(String name, String type, String color, int age) {
      super(name, type, color, age);
      this.wand = false;
   }
   
   public void kill(MagicalCreature other) {
      if(this==other) {
         System.out.println("I cannot kill myself!");
      }
      else{
         boolean elim = (this.getWand())? true:false;
         if(elim) {
            other.die();
            System.out.println(other.name+" has been killed!");
         }
         else {
            System.out.println("I cannot kill without my wand!");
         }
      }
   }
   
   // returns the super class of die method.
   public void die() {
     super.die();
   }
   
   // returns the wand variable.
   public boolean getWand() {
      return wand;
   }
   
   // sets the wand variable.
   public void setWand(boolean wand){
      this.wand = wand;
   }
   
   // returns the super class of toString and additional print for Genie.
   public String toString() {
      String result = (this.wand)?" I eat leaves, I have a wand" : " I eat leaves, I have no wand";
      return super.toString()+result;
   }
}