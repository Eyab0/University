package application;

import java.util.ArrayList;


public class Process {

	String processName;
	double startTime = -1;
	double finishTime = -1;
	double arrivalTime;
	double burstTime;
	int size;
	double remainingTime;
	int numberOfFaults = 0;
	int pageLocation; 
	double waitTime;
	double turnaround;
	ArrayList<Page> pages = new ArrayList<Page>();

	public Process(String processName, double arrivalTime, double burstTime, int size) {
		this.processName = processName;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.size = size;
		this.remainingTime = burstTime;
	}

	public String getProcessName() {
		return processName;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getFinishTime() {
		return finishTime;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public double getBurstTime() {
		return burstTime;
	}

	public int getSize() {
		return size;
	}

	public double getRemainingTime() {
		return remainingTime;
	}

	public int getNumberOfFaults() {
		return numberOfFaults;
	}

	public int getPageLocation() {
		return pageLocation;
	}

	public double getWaitTime() {
		return waitTime;
	}

	public double getTurnaround() {
		return turnaround;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

}