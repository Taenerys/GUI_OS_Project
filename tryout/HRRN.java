package tryout;

public class HRRN {
	
	
public static Process[] findavgTime(int n, int processes[], int burst_time[], int arrival_time[]) {	
	Process[] p;    
    p = new Process[n];
    
    for(int i=0; i < n; i++) {
    	p[i] = new Process(processes[i], burst_time[i], arrival_time[i]); 
    }
	int time = 0;	
	boolean done = false; 
	
	while(!done) {
		int run = findNext(p, time, n); //finds the next process to run
		if( run == -1) { //test for fringe case where there is no process available to run but there are still processes in the queue
			time ++;			
		}
		else { //adjusts times
			time = time + p[run].getBt();
			p[run].setCom();
			p[run].setCt(time);
			p[run].setTt();	
			p[run].setWt();				
		}		
		done = checkDone(n, p);
	}
	
	
	
	return p;
		
	}

	
	private static int findNext(Process[] p, int time, int n){ // Finds process with the highest response ratio
		int ret = -1;
		float hrr = -9999;
		for(int i=0; i < n; i++) {
			int at = p[i].getAt();
			boolean	comp = p[i].getCom() == 0;
			boolean canRun = at <= time;	
			if(comp && canRun) {
				int wt = wait(time, at);		
				int bt = p[i].getBt();
				float com = getHRR(bt, wt);
				if(com > hrr) {
					hrr = com;
					ret = i;							
				}		
			}							
		} 
		return ret;		
	}

	private static int wait(int time, int ar){
		return time - ar;
	}	
	private static float getHRR(int bt, int wt){ // calculates HHR
		int temp = bt + wt;		
		return temp/bt;
	}	
	private static boolean checkDone(int n, Process[] p){ // checks of the process is done
		int check = 0;
		for(int i = 0; i < n; i++) {
			if(p[i].getCom() == 1) {
				check ++; 
			}	
		}
		if(check == n) {
			return true;
		}
		else {
			return false;
		}		
	}
	
	
}
