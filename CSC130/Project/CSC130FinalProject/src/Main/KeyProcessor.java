/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false) {
				Main.isImageDrawn = true;
				return;
			}
		sw.resetWatch();
		Main.isImageDrawn = true;
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		
		case 'w':
			if (last == key) {
				collisionChecker(0,-helper.speed);
				Main.movement = "walkup";
			}
			break;
		
		case 'a':
			if (last == key) {
				collisionChecker(-helper.speed,0);
				Main.movement = "walkleft";
			}
			break;
		
		case 's':
			if (last == key) {
				collisionChecker(0,helper.speed);
				Main.movement = "walkdown";
			}
			break;
			
		case 'd':
			if (last == key) {
				collisionChecker(helper.speed,0);
				Main.movement = "walkright";
			}
			break;
		
		case '$':
			Main.textMessage();
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
		last = key;
	}
	
	// If there is collision x and y will prevent it
	public static void collisionChecker(int x, int y) {
		Main.brian.getCoords().adjustX(x);
		Main.brian.getCoords().adjustY(y);
		int check = Main.Colliding();
		if (check == 0){
			Main.brian.getCoords().adjustX(-x);
			Main.brian.getCoords().adjustY(-y);
		}	
	}
}
