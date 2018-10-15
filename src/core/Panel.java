package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import objects.ArmPart;
import objects.Ball;
import objects.Floor;
import objects.Joint;
import objects.Target;

public class Panel extends JPanel {
	
	private Joint joint1,joint2;
	private ArmPart part1,part2,part3;
	private Ball ball;
	private Floor floor;
	private Target target;
	
	public Panel(Joint joint1, Joint joint2, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball, Floor floor)
	{
		this.joint1 = joint1;
		this.joint2 = joint2;
		this.ball = ball;		
		this.part1 = part1;
		this.part2 = part2;
		this.part3 = part3;
		this.floor = floor;
		this.target = new Target();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawLine(0, floor.getY(), 0+floor.getLength(), floor.getY());
		g2.setColor(Color.blue);
		g2.drawLine(part1.getPosX1(), part1.getPosY1(), part1.getPosX2(), part1.getPosY2());
		g2.setColor(Color.red);
		g2.drawLine(part2.getPosX1(), part2.getPosY1(), part2.getPosX2(), part2.getPosY2());	
		g2.drawLine(target.getX()-10, floor.getY()-2, target.getX()+10, floor.getY()-2);
		g2.setColor(Color.green);
		g2.drawLine(part3.getPosX1(), part3.getPosY1(), part3.getPosX2(), part3.getPosY2());
		g2.setColor(Color.black);
		g2.drawOval((int)(ball.getPosX()), (int)(ball.getPosY()), ball.getDiameter(), ball.getDiameter());
		g2.fillOval(part1.getPosX2(), part1.getPosY2(), 5, 5);
		g2.fillOval(part2.getPosX2(), part2.getPosY2(), 5, 5);
	}
	
	public Joint getJoint1() {
		return joint1;
	}

	public void setJoint1(Joint joint1) {
		this.joint1 = joint1;
	}

	public Joint getJoint2() {
		return joint2;
	}

	public void setJoint2(Joint joint2) {
		this.joint2 = joint2;
	}

	public Target getTarget() {
		return target;
	}
	public ArmPart getPart1() {
		return part1;
	}

	public void setPart1(ArmPart part1) {
		this.part1 = part1;
	}

	public ArmPart getPart2() {
		return part2;
	}

	public void setPart2(ArmPart part2) {
		this.part2 = part2;
	}

	public ArmPart getPart3() {
		return part3;
	}

	public void setPart3(ArmPart part3) {
		this.part3 = part3;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

}
