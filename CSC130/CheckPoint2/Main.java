package Main;

import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	
	public static boolean isimageDrawn = false;
	public static stopWatchX timer = new stopWatchX(250);
	
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();
	
	public static Vector2D currentVec = new Vector2D(-100, -100);
	public static Vector2D vec1 = new Vector2D(100, 100);
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		
		int speed = 1;
		for(int i=-128; i<1280; i+=speed) {
			vecs1.add(new Vector2D(i, -25));
		}
		
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
		while(timer.isTimeUp()) {
			timer.resetWatch();
		}
		
		currentVec = vecs1.remove();
		vecs2.add(currentVec);
		ctrl.addSpriteToFrontBuffer(currentVec.getX(), currentVec.getY(), "Pokeball");
		if(vecs1.isEmpty()) {
			while(!vecs2.isEmpty()) {
				vecs1.add(vecs2.remove());
			}
		}
	}
}

// Additional Static methods below...(if needed)