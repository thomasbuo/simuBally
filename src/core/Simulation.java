package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import objects.ArmPart;
import objects.Ball;
import objects.Joint;
import util.Time;

public class Simulation {
	
	private int targetX = 200;
	private int targetY = 481;
	private boolean is_running = false;
	
	private long start_time;
	private double current_time;
	private long total_calculation_time;
	private float simulated_seconds_per_real_second = 1;
	private boolean full_speed = false;
	private int visualization_frequency;
	
	private double realistic_time_in_seconds;
	private int days_simulated;
	
	public Simulation(ArrayList<Joint> joints, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball)
	{
		
		
	}
	public void simulate()
	{
		
			if (this.is_running) {
				System.out.println("Already Running.");
				return;
			}
	
			this.is_running = true;
	
			Thread th = new Thread(() -> {
				// Initialize
	
	
				double delta_t = 0.05; // in seconds
				this.total_calculation_time = 0;
				int step = 0;
	
				while (this.is_running) {
					start_time = System.nanoTime();
	
					// Wait for time step to be over
					double ns_used = (System.nanoTime() - start_time);
					total_calculation_time += ns_used;
					if (!full_speed) {
						double ns_to_wait = Time.secondsToNanoseconds(delta_t/simulated_seconds_per_real_second);
						try {
							TimeUnit.NANOSECONDS.sleep((int) Math.max(0, ns_to_wait - ns_used));
						} catch (InterruptedException e) {
							System.out.println("Simulation sleeping (" + ns_to_wait + "ns) got interrupted!");
						}
					}
	
					this.current_time += delta_t;
	
					// update graphics and statistics
					step++;
					if (step % this.visualization_frequency == 0 && this.current_experiment.isVizualise()) {
						gui.redraw();
						((PopulationPanel) gui.getPopulationPanel()).updateCharts();
					}
					if (this.current_time % this.measurement_interval_realistic_time_seconds < delta_t) this.calcStatistics(); // hacky, but avoids double inprecision porblems
				}

				// Finish this experiment
				this.experiment_wrapper.finishExperiment(current_experiment);
				((ExperimenterPanel) this.gui.experimenterPanel).updateList();
				current_experiment = this.experiment_wrapper.currentExperiment();
				stop();

				if (!experiment_wrapper.isAllFinished() && !(current_experiment == null)) {
					reset();
					start();
				} else {
					this.experiment_wrapper.createFinalReport();
					this.experiment_wrapper.saveAll();
				}
			});
	
			th.start();
		
	}
	

}
