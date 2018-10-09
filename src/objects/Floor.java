package objects;

public class Floor {
	int yCoord;
	int length;
	public Floor(int Y) 
	{
		yCoord = Y;
		length = 1000;
	}
	public int getY() {
		return yCoord;
	}
	
	public int getLength() {
		return length;
	}
	
}
