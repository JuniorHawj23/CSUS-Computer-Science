package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import Data.spriteInfo;
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
	
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		
		String[] transform = new String[4];
		transform[0] = "Walk1";
		transform[1] = "Walk2";
		transform[2] = "Walk3";
		transform[3] = "Walk4";
		
		int i = 0;
		int speed = 25;
		for(int x=-128; x<1280; x+=speed) {
			sprites.add(new spriteInfo(new Vector2D(x, 25), transform[i]));
			i++;
			if(i >= transform.length) {
				i=0;
			}
		}
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
		ctrl.drawString(150, 250, "" + sprites.size(), Color.blue);
		ctrl.drawString(125, 250, "" + currentSpriteIndex, Color.blue);
		
		spriteInfo info = sprites.get(currentSpriteIndex);
		ctrl.addSpriteToFrontBuffer(info.getCoords().getX(), info.getCoords().getY(), info.getTag());
		
		while(timer.isTimeUp()) {
			if(currentSpriteIndex < sprites.size()-1) {
				info = sprites.get(currentSpriteIndex);
				currentSpriteIndex++;
				timer.resetWatch();
			}
			else { 
			currentSpriteIndex = 0;

			}
		}
	}
}

// Additional Static methods below...(if needed)