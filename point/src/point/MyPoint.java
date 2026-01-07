package point;

public class MyPoint { 
	// Instance variables 
	private int x; 
	private int y; 
	// Default constructor (0, 0) 
	public MyPoint() { 
	this.x = 0; 
	this.y = 0; 
	} 
	// Overloaded constructor with parameters 
	public MyPoint(int x, int y) { 
	this.x = x; 
	this.y = y; 
	} 
	// Setter method to set both x and y 
	public void setXY(int x, int y) { 
	this.x = x; 
	this.y = y; 
	
	} 
	// Getter method that returns x and y in an array 
	public int[] getXY() { 
	return new int[] { x, y }; 
	} 
	// toString method 
	public String toString() { 
	return "(" + x + ", " + y + ")"; 
	} 
	// Method to calculate distance to another point with (x, y) 
	public double distance(int x, int y) { 
	int dx = this.x - x; 
	int dy = this.y - y; 
	return Math.sqrt(dx * dx + dy * dy); 
	} 
	// Overloaded distance method to another MyPoint object 
	public double distance(MyPoint another) { 
	int dx = this.x - another.x; 
	int dy = this.y - another.y; 
	return Math.sqrt(dx * dx + dy * dy); 
	} 
	// Overloaded distance method to the origin (0, 0) 
	public double distance() { 
	return Math.sqrt(x * x + y * y); 
	} 
	}