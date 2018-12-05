package fancySimulation;

/**
 * Created by sandersalahmadibapost on 26/09/2018.
 */

public class Main {

    public static boolean isRunning = true;
    public static void main(String[] args){

        //create simulation and UI

        Simulation sim = new Simulation();
        UI ui = new UI(sim);

    }

}
