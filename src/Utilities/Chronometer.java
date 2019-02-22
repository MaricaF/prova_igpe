package Utilities;

import java.util.Timer;

public class Chronometer {
	
	private Timer timer;
	private long tempo_trascorso;
	private long start_time;
	private int decSeconds;
	private int seconds;
	private int minutes;
	private int hours;
	
	public Chronometer() {
		// TODO Auto-generated constructor stub
		this.timer = new Timer();
		this.tempo_trascorso = 0;
		this.decSeconds = this.seconds = this.minutes = this.hours = 0;
		this.start();
	}
	
	public synchronized void reset()
	{
		this.tempo_trascorso = 0;
		this.decSeconds = this.seconds = this.minutes = this.hours = 0;
		this.start();
	}
	
	public String getCurrentTime()
	{
		//"0:00:00.0"
		return this.getHours()+":"+this.getMinutes()+":"+this.getSeconds()+"."+this.getDecSeconds();
	}
	
	private void start()
	{
		this.start_time = System.currentTimeMillis();
	}
	
	public synchronized long getTempoTrascorso()
	{
		return System.currentTimeMillis () - start_time; 
	}

	private int getDecSeconds() {
		return (int)(this.getTempoTrascorso() % 1000 / 100);
	}

	private int getSeconds() {
		return (int)(this.getTempoTrascorso()/ 1000 % 60);
	}

	private int getMinutes() {
		return (int)(this.getTempoTrascorso()/ 60000 % 60);
	}

	private int getHours() {
		return (int)(this.getTempoTrascorso()/ 3600000);
	}
	
	

}
