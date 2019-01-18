package fancySimulation;

/**
 * Created by sandersalahmadibapost on 26/09/2018.
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Maths.NeuralCore;
import NeuralNetwork.NeuralNetwork;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.DoubleSummaryStatistics;
import java.util.ArrayList;

public class UI extends JFrame{

    // create JFrame
    private static JFrame frame;
    private JFrame closeFrame = new JFrame();

    public PhysicsEngine physicsEngine;
    // create sliders
//    private final JSlider timeSlider;

    // create buttons
    private static JButton simulateButton;
    private static JButton resetButton;

    // create control panel
    private static JPanel control;
    private static JPanel controlN;
    private static JPanel controlS;

    //create labels
    private static JLabel angleLabel1;
    private static JLabel angleLabel2;
    private static JLabel triggerAngleText;
//    private static JLabel timeSliderText;
//    private static JLabel timeSliderValueText;
    private static JLabel targetDistanceText;
    private static JLabel targetHeightText;
    private static JLabel targetWidthText;
    private static JLabel blank;

    // create text fields
    private static JTextField startAngle1;
    private static JTextField startAngle2;
    private static JTextField endAngle1;
    private static JTextField endAngle2;
    private static JTextField triggerAngle;
    private static JTextField targetDistance;
    private static JTextField targetHeight;
    private static JTextField targetWidth;

    // final variables text field dimensions
    private static final int DIMWIDTH = 60;
    private static final int DIMHEIGHT = 25;
    private Simulation sim;

    // angle correction
    private final int CORRECT = 0;

    public UI(Simulation sim){
    	
    	
    	this.sim = sim;
        //Create labels
        angleLabel1 = new JLabel("Initial and final angles lower: ");
        angleLabel2 = new JLabel("Initial and final angles upper: ");
        triggerAngleText = new JLabel("Trigger Angle: ");
//        timeSliderText = new JLabel("Time Value: ");
//        timeSliderValueText = new JLabel("0");
        targetDistanceText = new JLabel("Target distance: ");
        targetHeightText = new JLabel("Target height: ");
        targetWidthText = new JLabel("Target width: ");
        blank = new JLabel("            ");


        //text field dimension
        Dimension inputDim = new Dimension(DIMWIDTH,DIMHEIGHT);

        //Create text fields
        startAngle1 = new JTextField("-90");
        startAngle1.setPreferredSize(inputDim);
        startAngle2 = new JTextField("20");
        startAngle2.setPreferredSize(inputDim);
        endAngle1 = new JTextField("90");
        endAngle1.setPreferredSize(inputDim);
        endAngle2 = new JTextField("-20");
        endAngle2.setPreferredSize(inputDim);
        triggerAngle = new JTextField("-10");
        triggerAngle.setPreferredSize(inputDim);
        targetDistance = new JTextField("0.80");
        targetDistance.setPreferredSize(inputDim);
        targetHeight = new JTextField("0.1");
        targetHeight.setPreferredSize(inputDim);
        targetWidth = new JTextField("0.2");
        targetWidth.setPreferredSize(inputDim);


        //create buttons
        simulateButton = new JButton("Simulate");
        resetButton = new JButton("Reset");

        //create time slider
//        timeSlider = new JSlider(0, 250, 0);

        //create control panel
        control = new JPanel(new BorderLayout());
        controlN = new JPanel();
        controlS = new JPanel();

        //set control panel background
        control.setBackground(Color.WHITE);
        controlN.setBackground(Color.WHITE);
        controlS.setBackground(Color. WHITE);

        //set control panel layout
        controlN.setLayout(new FlowLayout());
        controlS.setLayout(new FlowLayout());

        //add all control panel elements
//        controlN.add(timeSliderText);
//        controlN.add(timeSlider);
//        controlN.add(timeSliderValueText);
        controlN.add(blank);
        controlN.add(targetDistanceText);
        controlN.add(targetDistance);
        controlN.add(targetHeightText);
        controlN.add(targetHeight);
        controlN.add(targetWidthText);
        controlN.add(targetWidth);
        controlS.add(angleLabel1);
        controlS.add(startAngle1);
        controlS.add(endAngle1);
        controlS.add(angleLabel2);
        controlS.add(startAngle2);
        controlS.add(endAngle2);
        controlS.add(triggerAngleText);
        controlS.add(triggerAngle);
        controlS.add(simulateButton);
        controlS.add(resetButton);
        control.add(controlN,BorderLayout.PAGE_START);
        control.add(controlS,BorderLayout.PAGE_END);

        //create frame and add elements
        frame = new JFrame();
        frame.setTitle("Robotic Arm Simulator");
        frame.setLayout(new BorderLayout());
        frame.add(sim,BorderLayout.PAGE_START);
        frame.add(control,BorderLayout.PAGE_END);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        int pop = 40;
        NeuralCore neural = new NeuralCore(pop);
        
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
		        	neural.getPopulation().get(0).getNN().saveToFile("neuralNetwork");
		        }
		        System.exit(0);
		    }
		});
        
        
        //simulate button functionality
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	int combined =0;
                int startAngle1Value =  Integer.parseInt(startAngle1.getText());
                int startAngle2Value =  -Integer.parseInt(startAngle2.getText());
                int endAngle1Value =  Integer.parseInt(endAngle1.getText());
                int endAngle2Value =  -Integer.parseInt(endAngle2.getText());
                int triggerAngleValue = Integer.parseInt(triggerAngle.getText());
                double targetDistanceValue = Double.parseDouble(targetDistance.getText());
                double targetHeightValue = Double.parseDouble(targetHeight.getText());
                double targetWidthValue = Double.parseDouble(targetWidth.getText());

                
//                System.out.println("THIS IS SIM TARGET WIDTH" + sim.getTargetWidth());

               /* sim.setStartAngle1Value(startAngle1Value);
                sim.setStartAngle2Value(startAngle2Value);
                sim.setEndAngle1Value(endAngle1Value);
                sim.setEndAngle2Value(endAngle2Value);
                sim.setTriggerAngle(triggerAngleValue);
                sim.setTargetDistance(targetDistanceValue);
                sim.setTargetHeight(targetHeightValue);
                sim.setTargetWidth(targetWidthValue);

                physicsEngine = new PhysicsEngine(sim);
                physicsEngine.setStartAngle1Value(startAngle1Value);
                physicsEngine.setStartAngle2Value(startAngle2Value);
                physicsEngine.setEndAngle1Value(endAngle1Value); //toLearn
                physicsEngine.setEndAngle2Value(endAngle2Value); //toLearn
                physicsEngine.setTriggerAngle(triggerAngleValue); //toLearn
                //		                System.out.println("die3 "+angles);
                physicsEngine.setTargetDistance(targetDistanceValue);
                physicsEngine.setTargetHeight(targetHeightValue);
                physicsEngine.setTargetWidth(targetWidthValue);
                physicsEngine.run();
                //                simulation.updateSim(0);
                physicsEngine.sim.repaint();*/
               
                
                ArrayList<Double> target = new ArrayList<Double>();
                
                for(int j = 0; j < 1000; j++)
                {
                	target.clear();
                	target.add(targetDistanceValue);
//                	System.out.println("new gen");
                	for(int p =0 ;p <3;p++) 
                	{
		                for(int i = 0; i < pop; i++)
		                {
	//	                	System.out.println("die "+i);
		                	
		                	
		                	ArrayList<Double> angles = neural.getPopulation().get(i).getNN().guess(target);
	//	                	System.out.println("die2");
		                	
		      		                	
		                	int endAngle1ValueTemp = (int)(180 * angles.get(0)) - 90;
		                	int endAngle2ValueTemp = (int)((40 * angles.get(2)) - 20)*-1;
		                	int triggerAngleValueTemp = (int)(-90 + (90 + endAngle1ValueTemp) * angles.get(1));
		                	System.out.println("ANGLE1 "+endAngle1ValueTemp+" ANGLE2 "+triggerAngleValueTemp+" ANGLE3 "+endAngle2ValueTemp);
		                	System.out.println("THIS IS THE GENERATION : " + j +" INDIVIDUAL: "+i+" run "+p);
		                	System.out.println("THIS IS THE END ANGLE 1 VALUE : " + endAngle1ValueTemp);
		                	System.out.println("THIS IS THE END ANGLE 2 VALUE : " + endAngle2ValueTemp);
		                	System.out.println("THIS IS THE TRIGGER ANGLE VALUE : " + triggerAngleValueTemp);
		                	System.out.println();
	
		                	
		                	sim.setStartAngle1Value(startAngle1Value);
		                    sim.setStartAngle2Value(startAngle2Value);
		                    sim.setEndAngle1Value(endAngle1ValueTemp);
		                    sim.setEndAngle2Value(endAngle2ValueTemp);
		                    sim.setTriggerAngle(triggerAngleValueTemp);
		                    sim.setTargetDistance(targetDistanceValue);
		                    sim.setTargetHeight(targetHeightValue);
		                    sim.setTargetWidth(targetWidthValue);
		                    
			                physicsEngine = new PhysicsEngine(sim);
			                physicsEngine.setStartAngle1Value(startAngle1Value);
			                physicsEngine.setStartAngle2Value(startAngle2Value);
			                physicsEngine.setEndAngle1Value(endAngle1ValueTemp); //toLearn
			                physicsEngine.setEndAngle2Value(endAngle2ValueTemp); //toLearn
			                physicsEngine.setTriggerAngle(triggerAngleValueTemp); //toLearn
	//		                System.out.println("die3 "+angles);
			                physicsEngine.setTargetDistance(targetDistanceValue);
			                physicsEngine.setTargetHeight(targetHeightValue);
			                physicsEngine.setTargetWidth(targetWidthValue);
			                physicsEngine.run();
			//                simulation.updateSim(0);
			                physicsEngine.sim.repaint();
			                neural.getPopulation().get(i).setScore(physicsEngine.getScore());
			                
			                
		                }
		                neural.train(neural.getPopulation());
		                
	                
	               
                	}
                	neural.storePopulation();
                	if(combined==0 && !(targetDistanceValue>=0.80)) 
                	{
                		System.out.println("refresh");
                		neural.refresh(pop);
                		targetDistanceValue+=0.05;
                	}
                	else 
                	{
	                double maxScore = 0;
	                if(targetDistanceValue>=0.80)
	                {
	                	neural.trainOnPreviousBest(neural.getPopulation());
	                	
	                	combined=1;
	                	
	                	for(int i=0 ;i< neural.getPopulation().size();i++)
	                	{
	                		targetDistanceValue = 0.20;
	                		System.out.println("run "+i+" tdv "+targetDistanceValue);            		
	                		
	                    	
	                		while(targetDistanceValue <0.8 )
	                		{ 			
	                			target.clear();
		                    	target.add(targetDistanceValue);
	    	                	ArrayList<Double> angles = neural.getPopulation().get(i).getNN().guess(target);
//	    	                	System.out.println("die2");
	    	                	
	    	                	int endAngle1ValueTemp = (int)Math.round(angles.get(0)*179-89);
	    	                	int endAngle2ValueTemp = (int)Math.round(angles.get(1)*39-19);
	    	                	int triggerAngleValueTemp = - 89 + (int)(Math.round(angles.get(2)*(endAngle1ValueTemp+89)));
	    	                	    	                	
	    	                	
	    	                    sim.setEndAngle1Value(endAngle1ValueTemp);
	    	                    sim.setEndAngle2Value(endAngle2ValueTemp);
	    	                    sim.setTriggerAngle(triggerAngleValueTemp);
	    	                    sim.setTargetDistance(targetDistanceValue);
	    	          
	    	                    
	    		                physicsEngine = new PhysicsEngine(sim);
	    		                physicsEngine.setStartAngle1Value(startAngle1Value);
	    		                physicsEngine.setStartAngle2Value(startAngle2Value);
	    		                physicsEngine.setEndAngle1Value(endAngle1ValueTemp); //toLearn
	    		                physicsEngine.setEndAngle2Value(endAngle2ValueTemp); //toLearn
	    		                physicsEngine.setTriggerAngle(triggerAngleValueTemp); //toLearn

	    		                physicsEngine.setTargetDistance(targetDistanceValue);
	    		                physicsEngine.setTargetHeight(targetHeightValue);
	    		                physicsEngine.setTargetWidth(targetWidthValue);
	    		                physicsEngine.run();
	    		
	    		                physicsEngine.sim.repaint();
	    		                neural.getPopulation().get(i).setScore( neural.getPopulation().get(i).getScore()+physicsEngine.getScore());
	                			if(i == (int)(neural.getPopulation().size()*0.1)-1) 
	                			{
	                				System.out.println("addscore");
		                			maxScore+=Math.pow(((targetDistanceValue + targetWidthValue/2)*1000),2);
	                			}
	                			targetDistanceValue+=0.05;
	                		}
	                		
	                		
	                	}
	                	System.out.println("maxScore: "+maxScore);
		                	for(int i=0 ;i< neural.getPopulation().size();i++)
		                	{
		                		System.out.println("individual " + i + ": " +neural.getPopulation().get(i).getScore());
		                	}
		                	neural.sort();
		                	neural.getPopulation().get(0).getNN().saveToFile("neural");
		                	for(int i=0 ;i< (int)(neural.getPopulation().size()*0.1);i++)
		                	{
		                		neural.getPopulation().get(i).setScore(0);
		                		System.out.println("reset");
		                	}
		                targetDistanceValue = 0.20;
		                target.clear();
                    	target.add(targetDistanceValue);
	                }                
	                else
	                {
	                	System.out.println("increase");
	                	targetDistanceValue+=0.05;
	                }
	                
	                
	                System.out.println("nextgen");
                }
                }
            }
        });

        //reset button functionality
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                physicsEngine.stop();

                int startAngle1Value =  Integer.parseInt(startAngle1.getText())+CORRECT;
                int startAngle2Value =  -(Integer.parseInt(startAngle2.getText())-CORRECT);
                int endAngle1Value =  Integer.parseInt(endAngle1.getText())+CORRECT;
                int endAngle2Value =  -(Integer.parseInt(endAngle2.getText())-CORRECT);
                int triggerAngleValue = Integer.parseInt(triggerAngle.getText());
                double targetDistanceValue = Double.parseDouble(targetDistance.getText());
                double targetHeightValue = Double.parseDouble(targetHeight.getText());
                double targetWidthValue = Double.parseDouble(targetWidth.getText());


                sim.setStartAngle1Value(startAngle1Value);
                sim.setStartAngle2Value(startAngle2Value);
                sim.setEndAngle1Value(endAngle1Value);
                sim.setEndAngle2Value(endAngle2Value);
                sim.setTriggerAngle(triggerAngleValue);
                sim.setTargetDistance(targetDistanceValue);
                sim.setTargetHeight(targetHeightValue);
                sim.setTargetWidth(targetWidthValue);

                physicsEngine = new PhysicsEngine(sim);
               // simulation.updateSim(0);
                physicsEngine.setStartAngle1Value(startAngle1Value);
                physicsEngine.setStartAngle2Value(startAngle2Value);
                physicsEngine.setEndAngle1Value(endAngle1Value);
                physicsEngine.setEndAngle2Value(endAngle2Value);
                physicsEngine.setTriggerAngle(triggerAngleValue);
                physicsEngine.setTargetDistance(targetDistanceValue);
                physicsEngine.setTargetHeight(targetHeightValue);
                physicsEngine.setTargetWidth(targetWidthValue);
                physicsEngine.run();
                physicsEngine.sim.repaint();

            }
        });
        
      
        /*//time slider functionality
        timeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int time = timeSlider.getValue();
                timeSliderValueText.setText(time+"");
                simulation.updateSim(time);
                simulation.repaint();

            }
        });*/


    }
    public void resetpls() 
    {
	    int startAngle1Value =  Integer.parseInt(startAngle1.getText())+CORRECT;
	    int startAngle2Value =  -(Integer.parseInt(startAngle2.getText())-CORRECT);
	    int endAngle1Value =  Integer.parseInt(endAngle1.getText())+CORRECT;
	    int endAngle2Value =  -(Integer.parseInt(endAngle2.getText())-CORRECT);
	    int triggerAngleValue = Integer.parseInt(triggerAngle.getText());
	    double targetDistanceValue = Double.parseDouble(targetDistance.getText());
	    double targetHeightValue = Double.parseDouble(targetHeight.getText());
	    double targetWidthValue = Double.parseDouble(targetWidth.getText());
	
	
	    sim.setStartAngle1Value(startAngle1Value);
	    sim.setStartAngle2Value(startAngle2Value);
	    sim.setEndAngle1Value(endAngle1Value);
	    sim.setEndAngle2Value(endAngle2Value);
	    sim.setTriggerAngle(triggerAngleValue);
	    sim.setTargetDistance(targetDistanceValue);
	    sim.setTargetHeight(targetHeightValue);
	    sim.setTargetWidth(targetWidthValue);
	
	    physicsEngine = new PhysicsEngine(sim);
	   // simulation.updateSim(0);
	    physicsEngine.setStartAngle1Value(startAngle1Value);
	    physicsEngine.setStartAngle2Value(startAngle2Value);
	    physicsEngine.setEndAngle1Value(endAngle1Value);
	    physicsEngine.setEndAngle2Value(endAngle2Value);
	    physicsEngine.setTriggerAngle(triggerAngleValue);
	    physicsEngine.setTargetDistance(targetDistanceValue);
	    physicsEngine.setTargetHeight(targetHeightValue);
	    physicsEngine.setTargetWidth(targetWidthValue);
	    physicsEngine.run();
	    physicsEngine.sim.repaint();
    }

}
