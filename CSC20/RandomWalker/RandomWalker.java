package Brian;

import java.util.*;

/**
*@author Brian 
*A class object which keeps track of it's x and y coordinates
*and should move to a random location exactly one unit to four directions when asked.
*/
public class RandomWalker {
	public int x;
	public int y;
	public int steps;

	// Class constructor setting x and y coordinates to zero.
	public RandomWalker() {
	x = 0;
	y = 0;
	}

	// Class constructor specifying the x and y coordinates.
	public RandomWalker(int x, int y) {
	this.x = x;
	this.y = y;
	}

	// Instructs the random walker to update it's coordinate by randomly making one of four moves.
	public void move() {
	Random random = new Random();
	int randomWalk = random.nextInt(4)+1;
		if(randomWalk==1) {
		x+=1;
		}
	
		else if(randomWalk==2) {
		y+=1;
		}
	
		else if(randomWalk==3) {
		x-=1;
		}
	
		else if(randomWalk==4) {
		y-=1;
		}
		steps++;
	}

	// return current x-coordinate.
	public int getX() {
	return x;
	}

	// return current y-coordinate.
	public int getY() {
	return y;
	}

	// return the number of time move was called.
	public int getSteps() {
	return steps;
	}
}