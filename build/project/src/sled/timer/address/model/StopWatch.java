package sled.timer.address.model;

import sled.timer.address.view.PersonOverviewController;

public class StopWatch implements Runnable{

	private Thread runThread; 
	public volatile boolean running = false; 
	public volatile boolean paused = false; 


	private long summedTime = 0; 

	PersonOverviewController personOverviewController; 

	String time = ""; 
	String millis = ""; 
	String sec = ""; 
	String min = ""; 
	//String[] values = new String[3]; 
	int millisNum =0 ; 
	int secNum = 0; 
	int minNum = 0; 
	public void count(){

	}
	public StopWatch(PersonOverviewController personOverviewController){
		this.personOverviewController = personOverviewController; 

	}
	public void startTimer(){
		running  = true; 
		paused = false; 

		runThread = new Thread(this); 
		runThread.setDaemon(true);
		runThread.start(); 
	}

	public void pauseTimer(){
		paused = true; 
	}

	public void stopTimer(){
		running = false; 
		paused = false; 
		millisNum = 0; 
		secNum = 0; 
		minNum = 0; 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis(); 
		long now = System.currentTimeMillis();
		while(running && !paused){
			//millisNum = (int) (System.currentTimeMillis() - startTime);
			//now =  (System.currentTimeMillis() - startTime); 

			if((System.currentTimeMillis() - now) >= 10){
				now = System.currentTimeMillis();

				millisNum++; 
				if(millisNum >= 100){
					//System.out.println("wut");
					millisNum = 0; 
					secNum++; 
					if(secNum >= 60){
						//System.out.println("wut");
						secNum = 0; 
						minNum++; 

					}
				}
			}
			
			millis = Integer.toString(millisNum).length() < 2 ? "0" + millisNum : Integer.toString(millisNum); 
			
			sec = Integer.toString(secNum).length() < 2 ? "0" + secNum : Integer.toString(secNum); 

			min = Integer.toString(minNum).length() < 2 ? "0" +minNum : Integer.toString(minNum); 

			personOverviewController.updateTimer(millis, sec, min);
		}
		if(paused)
			summedTime += System.currentTimeMillis() - startTime; 

		else 
			summedTime = 0; 

	}

	public long getSummedTime(){
		return summedTime; 
	}
}
