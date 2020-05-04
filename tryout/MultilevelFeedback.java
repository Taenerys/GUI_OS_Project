package Scheduling;

public class MultilevelFeedback {


public static Process[] findavgTime(int n, int processes[], int burst_time[], int arrival_time[]) {	
	Process[] p;    
    p = new Process[n];
    
    for(int i=0; i < n; i++) {
    	p[i] = new Process(processes[i], burst_time[i], arrival_time[i]); //creates and array of process objects
    }
	int time = 0; //sets time to 0
	
	int Quant = 10; //sets time quantum for RR
	
	//round robin x2
	for(int j = 0; j < 2; j++) {
		for (int i = 0 ; i < n; i++) { 
			boolean check = p[i].getAt() <= time;
			boolean check2 = p[i].getCom() == 0;
			if(check2 && check) { //checks that the process can run. 
				if(p[i].getTl() > Quant){ //adjusts time for processes with burst time > the time quantum
					p[i].adgTl(Quant);
					time = time + Quant; 
				}
				else { //adjusts time for processes with burst time < the time quantum and sets them to complete
					time = time + p[i].getTl();
					p[i].setCom();
					p[i].setCt(time);
					p[i].setTt();	
					p[i].setWt();	
					
				}				
			}
		}		
	}
	//first come first serve
	for (int i = 0 ; i < n; i++) { 
		if(p[i].getCom() == 0) {
			time = time + p[i].getTl();
			p[i].setCom();
			p[i].setCt(time);
			p[i].setTt();	
			p[i].setWt();
			
		}
	}
	
	return p;
	
	
	
}
}
