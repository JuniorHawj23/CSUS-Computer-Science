package Brian;

public class Circle {
	int x;
	int y;
	int radius;

	public Circle(int x, int y, int radius) {
		this.x= x;
	    this.y= y;
	    this.radius = radius;
	}

	public Circle() {
		x= 0;
	    y= 0;
	    radius = 1;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	// returns the area of the circle using Math.pi*radius*radius
	public double getArea() {
		return (Math.PI*radius*radius);
	} 

	// returns the area of the circle using Math.pi*radius*2
	public double getCircumference() {
		return (Math.PI*radius*2);
	}

	// returns the string representation of the circle as [(x,y):radius]
	public String toString() {
		return "[("+x+", "+y+"):"+radius+"]";
	}
}