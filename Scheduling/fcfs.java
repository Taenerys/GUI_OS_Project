package Scheduling;

import java.text.ParseException;

public class fcfs {
	
	static void findWaitingTime(int processes[], int n, int burstTimes[], int waitingTimes[]){
		waitingTimes[0] = 0;
		
		for (int i = 1; i < n; i++) {
			waitingTimes[i] = burstTimes[i - 1] + waitingTimes[i - 1];
		}
	}
	
	static void findTurnAroundTime(int processes[], int n, int burstTimes[], int waitingTimes[], int turnAroundTimes[]) {
		for (int i = 0; i < n; i++) {
			turnAroundTimes[i] = burstTimes[i] + waitingTimes[i];
		}
	}
	
	static void findAvgTime(int processes[], int n, int burstTimes[]) {
		int waitingTimes[] = new int[n];
		int turnAroundTimes[] = new int[n];
		
		int total_waitingTime = 0;
		int total_turnAroundTime = 0;
		
		findWaitingTime(processes, n, burstTimes, waitingTimes);
		findTurnAroundTime(processes, n, burstTimes, waitingTimes, turnAroundTimes);
		
		System.out.printf("Processes Burst_time Waiting_time Turnaround_time\n" );
		
		for (int i = 0; i < n; i++) {
			total_waitingTime += waitingTimes[i];
			total_turnAroundTime += turnAroundTimes[i];
			System.out.printf(" %d ", (i + 1));
			System.out.printf("        %d ", burstTimes[i]);
			System.out.printf("            %d", waitingTimes[i]);
			System.out.printf("                %d\n", turnAroundTimes[i]);
		}
		
		float s = (float) total_waitingTime / (float) n;
		int t = total_turnAroundTime / n;
		System.out.printf("Average waiting time = %f", s);
		System.out.printf("\n");
		System.out.printf("Average turnaround time = %d ", t);
	}

	public static void main(String[] args) throws ParseException{
		int processes[] = {1, 2, 3, 4, 5};
		int n = processes.length;
		
		int burstTimes[] = {10, 4, 5, 8, 6};
		
		findAvgTime(processes, n, burstTimes);
		
	}

}
