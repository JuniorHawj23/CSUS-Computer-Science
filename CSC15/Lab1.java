package Brian;

public class Lab1 {

	public static void main(String[] args) {
		drawTopSlash();
		writeMessageOne();
		drawBottomSlashOne();
		System.out.println();
		drawTopSlash();
		writeMessageTwo();
		drawBottomSlashTwo();
	}
	
	public static void drawTopSlash() {
		System.out.println("/////////////////////");
	}
	
	public static void writeMessageOne() {
		System.out.println("|| Victory is Near ||");
	}
	
	public static void drawBottomSlashOne() {
		System.out.println("/////////////////////");
	}
	
	public static void writeMessageTwo() {
		System.out.println("\"   Victory is Near \"");
	}
	public static void drawBottomSlashTwo() {
		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
	}	
}