package objects;

public class Ball {

	private int diameter;
	private int weight;
	private int speedX = 0;
	private int speedY = 0;
	private int posX;
	private int posY;
	public Ball(int diameter, int weight, int posX, int posY)
	{
		this.posX = posX;
		this.posY = posY;
		this.diameter = diameter;
		this.weight = weight;
	}
	public int getDiameter() {
		return diameter;
	}
	public int getWeight() {
		return weight;
	}	
	public int getPosX() {
		return posX;
	}
	public int getSpeedX()
	{
		return speedX;
	}
	public int getSpeedY()
	{
		return speedY;
	}
	public void setSpeedY()
	{
		speedY -= 10;
	}
	public void nullSpeed()
	{
		speedX = 0;
		speedY = 0;
	}
	public int getPosY() {
		return posY;
	}
	public void setPos()
	{
		
	}
	
	
}
