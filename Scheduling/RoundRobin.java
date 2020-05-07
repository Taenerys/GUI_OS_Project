package Scheduling;

public class RoundRobin {
	static void findWaitingTime(int processes[], int n, int burstTimes[], int waitingTimes[], int quantum) {
		int remainingBurstTimes[] = new int[n];
		for (int i = 0; i < n; i ++) {
			remainingBurstTimes[i] = burstTimes[i];
		}
		int t = 0;
		
		while (true) {
			boolean finished = true;
			
			for (int i = 0; i < n; i++) {
				if (remainingBurstTimes[i] > 0) {
					finished = false;
				
					if (remainingBurstTimes[i] > quantum) {
						t += quantum;
						remainingBurstTimes[i] -= quantum;
					} else {
						t += remainingBurstTimes[i];
						waitingTimes[i] = t - burstTimes[i];
						remainingBurstTimes[i] = 0;
					}
				}
			}
			if (finished == true)
				break;
		}
	}
	
	static void findTurnAroundTime(int processes[],int n, int burstTimes[], int waitingTimes[], int turnAroundTimes[] ) {
		for (int i = 0; i < n; i++) {
			turnAroundTimes[i] = burstTimes[i] + waitingTimes[i];
		}
	}
	
	static void findAvgTime(int processes[],int n, int burstTimes[], int quantum) {
		int waitingTimes[] = new int[n];
		int turnAroundTimes[] = new int[n];
		
		int totalWaitingTime = 0;
		int totalTurnAroundTime = 0;
		
		findWaitingTime(processes, n, burstTimes, waitingTimes, quantum);
		
		findTurnAroundTime(processes, n, burstTimes, waitingTimes, turnAroundTimes);
		
		System.out.println("Processes " + "  Burst time " + " Waiting time " + " Turn around time");
		
		for (int i = 0; i < n; i++){
			totalWaitingTime += waitingTimes[i];
			totalTurnAroundTime += turnAroundTimes[i];
			System.out.println(" " + (i + 1) + "\t\t" + burstTimes[i] + "\t" + waitingTimes[i] + "\t\t " + turnAroundTimes[i]);
		}
		
		System.out.println("Average waiting time = " + (float)totalWaitingTime / (float)n);
		System.out.println("Average turn around time = " + (float)totalTurnAroundTime / (float)n);
	}

	public static void main(String[] args) {
		int processes[] = {1,2,3};
		int n = processes.length;
		
		int burstTimes[] = {9, 4, 10};
		int quantum = 10;
		findAvgTime(processes, n, burstTimes, quantum);

	}

}
