package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import java.util.HashMap;
import java.util.StringTokenizer;
import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	
	public static stopWatchX timer = new stopWatchX(250);
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();
	
	public static Vector2D currentVec = new Vector2D(-100, -100);
	public static Vector2D vec1 = new Vector2D(100, 100);
	
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
	
	public static HashMap<String, String> map = new HashMap<>();
	public static HashMap<String, char[]> mapChar = new HashMap<>();
	public static String currentText = "";
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();					// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		
		EZFileRead ezr = new EZFileRead("Dialog.txt");
		int index = 0;
		while(index < ezr.getNumLines()) {
			
			String line = ezr.getNextLine();
			StringTokenizer st = new StringTokenizer(line, "*");
			String key = st.nextToken();

			String Value = st.nextToken();
			// this will add key and value into the hashmap
			map.put(key, Value);
			index++;
		}
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
		// display the number of map/string we have
		ctrl.drawString(25, 390, "" + map.size(), Color.blue);
		// display walking frame one
		spriteInfo info = new spriteInfo(new Vector2D(60, 260), "Walk1");
		
		ctrl.addSpriteToFrontBuffer(info.getCoords().getX(), info.getCoords().getY(), info.getTag());
		
		// display the string one
		String string1 = map.get("String1");
		ctrl.drawString(25, 250, string1, Color.blue);
	}
}

// Additional Static methods below...(if needed)
