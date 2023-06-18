package Brian;

/*
*@author Brian
*Dragon class which is a sub class of MagicalCreatures
*/

public class Dragon extends MagicalCreature{

   /*
   *Constructor which calls the constructor of the parent class
   */
   public Dragon(String name, String type, String color, int age){
      super(name, type, color, age);
   }
   
   /*
   *Overrides the MagicalCreatures method and prints other text.
   */
   public void kill(MagicalCreature other){
      if(this == other){
         System.out.println("I cannot kill myself");
      }
      else{
         if(other.type.equals("Dragon")){
            System.out.println("Dragons cannot die!");
         }
         else{
            if(this.age>=40){
               other.die();
               System.out.println(other.name+" has been killed!");
            }
            else{
               System.out.println("I am too young to kill!");
            }
         }
      }
   }
   
   /*
   *Overrides the MagicalCreature method.
   */
   public void die(){
      System.out.println("I am a dragon - nobody gets to kill me!");
   }
   
   /*
   *Returns the MagicalCreatures toString information.
   */
   public String toString(){
      return super.toString()+" I breathe fire!";
   }
}