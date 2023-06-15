package Brian;

public class SailBoatHelper {

	public static void drawSailBoat() {
	stars();
	line();
	boat();
	line();
	line();	
	}
	
	public static void stars() {
		System.out.println("       *");
		System.out.println("       **");
		System.out.println("       ***");
		System.out.println("       ****");
		System.out.println("       *****");
		System.out.println("       ******");	
	}
	
	public static void boat() {
		System.out.println(" \\          /");
		System.out.println("  \\        / ");
		System.out.println("   --------  ");
	}
	
	public static void line() {
		System.out.println(" ------------");
	}
	
}
