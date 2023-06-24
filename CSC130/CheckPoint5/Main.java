package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import java.util.HashMap;
import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	
	public static Color c = new Color(255, 0, 0);
	public static stopWatchX timer = new stopWatchX(250);
	
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();	
	
	public static Vector2D currentVec = new Vector2D(-100, -100);
	public static Vector2D vec1 = new Vector2D(100, 100);
	
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
	
	public static HashMap<String, String> map = new HashMap<>();
	public static HashMap<String, char[]> mapChar = new HashMap<>();
	
	public static String trigger = "";
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		
		sprites.add(new spriteInfo(new Vector2D(100, 50), "Walk1"));
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
		ctrl.addSpriteToFrontBuffer(100, 50, "Walk1");
		ctrl.drawString(120, 300, trigger, c);
	}
}

// Additional Static methods below...(if needed)