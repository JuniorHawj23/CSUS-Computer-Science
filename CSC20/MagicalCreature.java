package Brian;

/*
*@author Brian
*A class that can be used to create a magical creature
*that is defined by a name, type, color, and age.
*/

public class MagicalCreature {
   
   // variables for creatures
   public String name;
   public String type;
   public String color;
   public int age;
   public boolean alive;

   // Constructor that sets the attribute valeus as recievved in the parameters
   public MagicalCreature(String name, String type, String color, int age) {
      this.name = name;
      this.type = type;
      this.color = color;
      this.age = age;
      this.alive = true;
   }

   // Getters for each of the attributes
   public String getName() {
      return name;
   }
   public String getType() {
      return type;
   }
   public String getColor() {
      return color;
   }
   public int getAge() {
      return age;
   }
   public boolean getAlive() {
      return alive;
   }

   // Setters for each of the attributes
   public void setName(String name) {
      this.name = name;
   }
   public void setType(String type) {
      this.type = type;
   }
   public void setColor(String color) {
      this.color = color;
   }
   public void setAge(int age) {
      this.age = age;
   }
   public void setAlive(boolean alive) {
      this.alive = alive;
   }
   
   // Prints the string "I do not have the license to kill"
   public void kill(MagicalCreature other) {
      System.out.println("I do not have the license to kill");
   }
   
   // Sets the attribute alive to false causing the creatures to die
   public void die() {
      this.alive=false;
   }
   
   // Returns a string of the name, color, type, and age.
   public String toString() {
      return "My name is "+name+" I am a "+color+" "+type+" I am "+age+" years old ";
   }
}