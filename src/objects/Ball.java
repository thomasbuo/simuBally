package objects;

public class Ball {

	private int diameter;
	private int weight;
	private double speedX = 0;
	private double speedY = 0;
	private double posX;
	private double posY;
	private double originalPosX;
	private double originalPosY;
	public Ball(int diameter, int weight, int posX, int posY)
	{
		this.posX = posX;
		this.posY = posY;
		originalPosX = posX;
		originalPosY= posY;
		this.diameter = diameter;
		this.weight = weight;
	}
	public int getDiameter() {
		return diameter;
	}
	public int getWeight() {
		return weight;
	}	
	public double getPosX() {
		return posX;
	}
	public double getSpeedX()
	{
		return speedX;
	}
	public double getSpeedY()
	{
		return speedY;
	}
	
	public void setSpeedX(double x)
	{
		this.speedX = x;
	}
	
	public void setSpeedY(double y)
	{
		this.speedY = y;
	}
	
	public void updateSpeedY()
	{
		speedY += 0.11;
		posY += speedY;
		posX += speedX;
	}
	public void nullSpeed()
	{
		speedX = 0;
		speedY = 0;
	}
	
	
	public double getPosY() {
		return posY;
	}
	public void setPos(double d, double e)
	{
		posX = d;
		posY = e;
	}
	public double getOriginalPosX()
	{
		return originalPosX;
	}
	public double getOriginalPosY()
	{
		return originalPosY;
	}
	
}
