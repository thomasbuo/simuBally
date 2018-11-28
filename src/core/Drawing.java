package core;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Drawing extends JFrame {

	private JPanel contentPane;
	private JTextField txtAngel;
	private JTextField txtAngel_1;
	private JTextField txtTarget;
	private JFrame closeFrame = new JFrame();
	
	public Drawing(Panel panel, Simulation simulation) 
	{
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		        if (JOptionPane.showConfirmDialog(closeFrame, 
		            "Would you like to Save?", "save?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		        {
		        	simulation.getNeuralCore().getPopulation().get(0).getNN().saveToFile("neuralNetwork");
		        }
		        System.exit(0);
		    }
		});
		
		setBounds(100, 100, 869, 586);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JPanel panel_1 = panel;
		panel_1.setBounds(10, 11, 834, 453);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 475, 834, 62);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnShoot = new JButton("Shoot");
		btnShoot.setBounds(735, 11, 89, 40);
		panel_2.add(btnShoot);
		
		txtAngel = new JTextField();
		txtAngel.setText("-30");
		txtAngel.setBounds(90, 21, 96, 20);
		panel_2.add(txtAngel);
		txtAngel.setColumns(10);
		
		txtAngel_1 = new JTextField();
		txtAngel_1.setText("0");
		txtAngel_1.setBounds(209, 21, 96, 20);
		panel_2.add(txtAngel_1);
		txtAngel_1.setColumns(10);
		
		txtTarget = new JTextField();
		txtTarget.setText("300");
		txtTarget.setBounds(333, 21, 96, 20);
		panel_2.add(txtTarget);
		txtTarget.setColumns(10);
		btnShoot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String angel1 = txtAngel.getText();
				String angel2 = txtAngel_1.getText();
				if(!angel1.equals("") && !angel2.equals(""))
				{
					panel.getJoint1().setTargetAngle(Integer.parseInt(angel1));
					panel.getJoint2().setTargetAngle(Integer.parseInt(angel2));
					simulation.setTarget(200);
					panel.getTarget().setX(simulation.getTarget());
					simulation.simulate();
				}
				
			}
		});
		
	
		
		repaint();
	}
	public void redraw()
	{
		repaint();
	}
}
