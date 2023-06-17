package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static stopWatchX timer = new stopWatchX(100);
	public static boolean isImageDrawn = false;
	
	// This will provide the image needed for the character
	public static spriteInfo background;
	public static ArrayList<spriteInfo> wall = new ArrayList<spriteInfo>();
	public static spriteInfo brian;
	public static spriteInfo house;
	public static spriteInfo star;
	
	// Movement for allowing my character to walk with frames/movements
	public static String movement = "walkdown";
	public static int movement1 = 0;
	public static int movement2 = 0;
	public static int movement3 = 0;
	public static int movement4 = 0;
	
	// This will print the text once the character reaches the object
	public static HashMap<String, String> text = new HashMap<>();
	public static String displayText = "";
	public static String inBorders;
	
			
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		brian = new spriteInfo(new Vector2D(600,400),helper.playerWidth,helper.playerHeight,"walkdown1");
		background = new spriteInfo(new Vector2D(0,0), helper.SCREENX, helper.SCREENY, "background");
		star = new spriteInfo(new Vector2D(200,280),helper.starWidth,helper.starHeight,"star");
		house = new spriteInfo(new Vector2D(1400,240),helper.houseWidth,helper.houseHeight,"house");
		
		wall.add(new spriteInfo(new Vector2D(0,0),helper.wallleftWidth,helper.wallleftHeight,"roomleft"));
		wall.add(new spriteInfo(new Vector2D(helper.SCREENX-120,0),helper.wallrightWidth,helper.wallrightHeight,"roomright"));
		wall.add(new spriteInfo(new Vector2D(0,0),helper.walltopWidth,helper.walltopHeight,"roomtop"));
		wall.add(new spriteInfo(new Vector2D(0,helper.SCREENY-120),helper.wallbottomWidth,helper.wallbottomHeight,"roombottom"));
		displayText();
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
			//Adding all the art to the screen
			ctrl.addSpriteToFrontBuffer(0, 0, background.getTag());
			ctrl.addSpriteToFrontBuffer(star.getCoords().getX(), star.getCoords().getY(), star.getTag());
			ctrl.addSpriteToFrontBuffer(house.getCoords().getX(), house.getCoords().getY(), house.getTag());
			for(spriteInfo border:wall) {
				 ctrl.addSpriteToFrontBuffer(border.getCoords().getX(),border.getCoords().getY(),border.getTag());
			}
			ctrl.addSpriteToFrontBuffer(brian.getCoords().getX(),brian.getCoords().getY(), brian.getTag());
				
			//Executes text when player press space and in bound of the items
			if (inBorders != null) {
	            ctrl.drawString(800, 850, displayText, Color.YELLOW);
			}
			
			// Movements for WASD to allow the character to go up, left, down, and right
			if(timer.isTimeUp()) {
				if(isImageDrawn) {
					switch(movement) {
					case "walkup":
						brian.setTag(movement+movement1++);
						if(movement1==4) {
							movement1=1;
						}
						break;
					case "walkleft":
						brian.setTag(movement+movement2++);
						if(movement2==4) {
							movement2=1;
						}
						break;
					case "walkdown":
						brian.setTag(movement+movement3++);
						if(movement3==4) {
							movement3=1;
						}
						break;
					case "walkright":
						brian.setTag(movement+movement4++);
						if(movement4==4) {
							movement4=1;
						}
						break;
					}
					isImageDrawn=false;
				}
				else {
					brian.setTag(movement+1);
				}
				timer.resetWatch();
			}	
			     
	}
	
	// Return -1 if the integer is equal to 0 
	public static int Colliding() {
		if (brian.boundingBox(house)) {
            inBorders = "house";
            return 0;
		}
        if (brian.boundingBox(star)) {
            inBorders = "star"; 													
            return 0;
        }
        if(!brian.boundingBox(house) && !brian.boundingBox(star)) {
        	inBorders = null;
        	displayText = "";
        }
        for (spriteInfo x : wall)
            if (brian.boundingBox(x)) {
            	return 0;
            }
        return -1;
	}

	
	// This is to display the text onto the screen when the character encounter a object
	public static void displayText() {
		EZFileRead ezr = new EZFileRead("Dialogue.txt");
		int i=0;
		while(i<ezr.getNumLines()) {
			String raw = ezr.getNextLine();
			StringTokenizer st = new StringTokenizer(raw,"*");
			String Key = st.nextToken();
			String Value = st.nextToken();
			text.put(Key, Value);
			i++;
		}
	}
	
	// This will allow the text to display (like the function)
	public static void textMessage(){
		if (inBorders != null) {
            if(inBorders=="house") {
            displayText = text.get("house");
            }
            else if(inBorders=="star"){
            	displayText = text.get("star");
            }
        }	
	}
}

