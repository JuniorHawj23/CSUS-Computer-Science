package Brian;

//Name: Brian Hert
//Date: 12/9/2021
//Title: Circle

public class CircleMain {
	public static void main(String[] args) {
	 
		//a
		Circle circleOne = new Circle();
		//b
		Circle circleTwo = new Circle(-5, -6, 9);
    
		//c
		circleOne.getX();
		circleOne.getY();
		circleOne.getRadius();
		System.out.println("CircleOne = "+circleOne);
		circleTwo.getX();
		circleTwo.getY();
		circleTwo.getRadius();
		System.out.println("CircleOne = "+circleTwo);
    
		//e
		System.out.println("Area for CircleOne = "+circleOne.getArea());
		System.out.println("Area for CircleTwo = "+circleTwo.getArea());
    
		//f
		System.out.println("Circumference for CircleOne = "+circleOne.getCircumference());
		System.out.println("Circumference for CircleTwo = "+circleTwo.getCircumference());
    
		//g
		circleOne.setX(4);
		circleOne.setY(5);
		circleTwo.setRadius(11);
    
		//h
		System.out.println("CircleOne = "+circleOne);
		System.out.println("CircleTwo = "+circleTwo);
    
		//4
		Circle [] circleArray = new Circle[2];
		circleArray[0] = circleOne;
		circleArray[1] = circleTwo;
    
		for(int i=0; i<circleArray.length; i++) {
		System.out.println(circleArray[i].toString());
		}
	}
}
