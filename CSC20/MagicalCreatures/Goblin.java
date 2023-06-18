package Brian;

/*
*@author Brian
*Goblin class which is a sub class of MagicalCreatures
*/

// Constructor which calls the parent class constructor.
public class Goblin extends MagicalCreature {
	public Goblin(String name, String type, String color, int age) {
		super(name, type, color, age);
	}


	// returns the super class of kill method.
	public void kill(MagicalCreature other) {
		super.kill(other);
	}

	// returns the super class of die method.
	public void die() {
	super.die();
	}

	// returns the super class of toString and additional print for Goblin.
	public String toString() {
	return super.toString()+" I kill elves if they do not have shields ";
	}
}