package point;

public class testMyPoint { 
public static void main(String[] args) { 
// Create points 

MyPoint p1 = new MyPoint(); // Default (0,0) 
MyPoint p2 = new MyPoint(3, 4); // (3,4) 
// Test toString() 
System.out.println("p1: " + p1); // (0, 0) 
System.out.println("p2: " + p2); // (3, 4) 
// Test setXY() 
p1.setXY(5, 6); 
System.out.println("After setXY, p1: " + p1); // (1, 2) 
// Test getXY() 
int[] coords = p1.getXY(); 
System.out.println("p1 x: " + coords[0] + ", y: " + coords[1]); 
// Test distance(x, y) 
double d1 = p1.distance(4, 6); 
System.out.println("Distance from p1 to (4,6): " + d1); 
// Test distance(MyPoint another) 
double d2 = p1.distance(p2); 
System.out.println("Distance from p1 to p2: " + d2); 
// Test distance() to origin 
double d3 = p2.distance(); 
System.out.println("Distance from p2 to origin: " + d3); 
} 
}