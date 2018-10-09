package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import objects.ArmPart;
import objects.Ball;
import objects.Joint;

public class Panel extends JPanel {
	
	private Joint joint1,joint2;
	private ArmPart part1,part2,part3;
	private Ball ball;
	
	public Panel(Joint joint1, Joint joint2, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball)
	{
		this.joint1 = joint1;
		this.joint2 = joint2;
		this.ball = ball;		
		this.part1 = part1;
		this.part2 = part2;
		this.part3 = part3;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.blue);
		g2.drawLine(part1.getPosX1(), part1.getPosY1(), part1.getPosX2(), part1.getPosY2());
		g2.setColor(Color.red);
		g2.drawLine(part2.getPosX1(), part2.getPosY1(), part2.getPosX2(), part2.getPosY2());
		g2.setColor(Color.green);
		g2.drawLine(part3.getPosX1(), part3.getPosY1(), part3.getPosX2(), part3.getPosY2());
		g2.setColor(Color.black);
		g2.drawOval(ball.getPosX(), ball.getPosY(), ball.getDiameter(), ball.getDiameter());
		g2.fillOval(part1.getPosX2(), part1.getPosY2(), 5, 5);
		g2.fillOval(part2.getPosX2(), part2.getPosY2(), 5, 5);
	}
}
