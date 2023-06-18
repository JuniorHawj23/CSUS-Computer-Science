package Brian;

/*
*@author Brian
*Elf class which is a sub class of MagicalCreatures
*/
public class Elf extends MagicalCreature {
    public boolean shield;
    // Constructor which calls the parent class constructor.
    
    public Elf(String name, String type, String color, int age) {
    super(name, type, color, age);
    this.shield = false;
    }

    /*
    *returns the super class of kill method.
    */
    public void kill(MagicalCreature other) {
    super.kill(other);
    }
    
    // returns the super class of die method.
    public void die() {
    super.die();
    }
    // sets the shield.
    
    public void setShield(boolean shield){
    this.shield = shield;
    }
    // returns the super class of toString and additional print for Elf. 
    
    public String toString() {
    String result = (this.shield)?" I eat leaves, I have a shield" : " I eat leaves, I have no shield";
    return super.toString()+result;
    
    }
}