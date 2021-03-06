package Scheduling;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends Applet implements ActionListener{
	
	int process, burstTime, temp, totalTurnAroundTime, totalWaitingTime;
	float avgWaitingTime, avgTurnAroundTime;
	int[] processIDs, burstTimes, arrivalTimes, completionTimes, waitingTimes, turnAroundTimes;
	String algorithm, choice;
	TextField t1, t2, t3;
	Label l1, l2, l3, l4;
	Button btn1, btn2;
	Choice algo;
	Graphics g= getGraphics();
	
	public void init(){

		l1 = new Label("Enter number of processes: ");
		l2 = new Label("Choose an algorithm: ");
		l3 = new Label("Arrival times (separated by space): ");
		l4 = new Label("Burst times (separated by space): ");
		t1 = new TextField(5);
		t2 = new TextField(10);
		t3 = new TextField(10);
		btn1 = new Button("Next");
		algo = new Choice();
		add(l1);
		add(t1);
		algo.add("FCFS");
		algo.add("Round Robin");
		algo.add("Shortest Process Next");
		algo.add("Shortest Remaining Time");
		algo.add("HRRN");
		algo.add("Multilevel Feedback");
		add(l2);
		add(algo);
		add(l3);
		add(t2);
		add(l4);
		add(t3);
		add(btn1);
		btn1.addActionListener(this);

		
	}

	public void paint(Graphics g) {
		g.drawString("There are " + process + " process(es)", 10, 100);
		g.drawString("You chose the scheduling algorithm " + algorithm , 10, 120);
		g.drawString("Average waiting time is " + avgWaitingTime, 10, 140);
		g.drawString("Average turn around time is " + avgTurnAroundTime, 10, 160);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		process = Integer.parseInt(t1.getText());
		processIDs = new int[process];
		for (int i = 0; i < process; i++)
			processIDs[i] = i+1;
		algorithm = algo.getSelectedItem();
		completionTimes = new int[process];
		waitingTimes = new int[process];
		turnAroundTimes = new int[process];
		burstTimes = new int[process];
		arrivalTimes = new int[process];
		String[] temp1 = t2.getText().split(" ");
		for (int i = 0; i < temp1.length; i++)
			arrivalTimes[i] = Integer.parseInt(temp1[i]);
		String[] temp2 = t3.getText().split(" ");
		for (int i = 0; i < temp2.length; i++)
			burstTimes[i] = Integer.parseInt(temp2[i]);
		
		switch (algorithm) {
			case "FCFS":
				//sort arrival times
				for(int i = 0 ; i < process; i++)
				{
					for(int  j=0;  j < process-(i+1) ; j++)
					{
						if( arrivalTimes[j] > arrivalTimes[j+1] )
						{
							temp = arrivalTimes[j];
							arrivalTimes[j] = arrivalTimes[j+1];
							arrivalTimes[j+1] = temp;
							temp = burstTimes[j];
							burstTimes[j] = burstTimes[j+1];
							burstTimes[j+1] = temp;
							temp = processIDs[j];
							processIDs[j] = processIDs[j+1];
							processIDs[j+1] = temp;
						}
					}
				}
				
				// find completion times
				for(int  i = 0 ; i < process; i++)
				{
					if( i == 0)
					{	
						completionTimes[i] = arrivalTimes[i] + burstTimes[i];
					}
					else
					{
						if( arrivalTimes[i] > completionTimes[i-1])
						{
							completionTimes[i] = arrivalTimes[i] + burstTimes[i];
						}
						else
							completionTimes[i] = completionTimes[i-1] + burstTimes[i];
					}
					turnAroundTimes[i] = completionTimes[i] - arrivalTimes[i] ;          // turnaround time= completion time- arrival time
					waitingTimes[i] = turnAroundTimes[i] - burstTimes[i] ;          // waiting time= turnaround time- burst time
					totalWaitingTime += waitingTimes[i] ;               // total waiting time
					totalTurnAroundTime += turnAroundTimes[i] ;               // total turnaround time
				}
				avgWaitingTime = totalWaitingTime / process;
				avgTurnAroundTime = totalTurnAroundTime / process;
				break;
			case "Round Robin": // time quantum is 10
				int remain;
				int quantum = 10;
				int temps = 0;

				totalWaitingTime = 0; 
				totalTurnAroundTime = 0;

				remain = process;

				int[] remainTimes = new int[process];
				for (int i = 0; i < remain; i++) {
					remainTimes[i] = burstTimes[i];
				}
				int time = 0, i = 0;
				while (remain != 0) {
					if (remainTimes[i] <= quantum && remainTimes[i] > 0){
						time += remainTimes[i];
						remainTimes[i] = 0;
						temps = 1;
					} else if (remainTimes[i] > 0) {
						remainTimes[i] -= quantum;
						time += quantum;
					}
					
					if (remainTimes[i] == 0 && temps == 1){
						remain--;
						totalWaitingTime += time - arrivalTimes[i] - burstTimes[i];
						totalTurnAroundTime += time - arrivalTimes[i];
						temps = 0;
					}
					
					if (i == process - 1) {
						i = 0;
					} else if (arrivalTimes[i+1] <= time) {
						i++;
					} else {
						i = 0;
					}
				}
				avgWaitingTime = totalWaitingTime / process;
				avgTurnAroundTime = totalTurnAroundTime / process;

				break;
			case "Shortest Process Next":
				int f[] = new int[process];  // f means it is flag it checks process is completed or not
				int systemTime=0, totalProcess =0;
				totalWaitingTime = 0;
				totalTurnAroundTime = 0;
		 
				for (int d =0; d < process; d++) {
					f[d] = 0;
				}
				
				while (true) {
					int c = process , min=999;
					if (totalProcess == process) // total no of process = completed process loop will be terminated
						break;
					
					for (int g =0; g < process; g++) {
						/*
						 * If i'th process arrival time <= system time and its flag=0 and burst<min 
						 * That process will be executed first 
						 */ 
						if ((arrivalTimes[g] <= systemTime) && (f[g] == 0) && (burstTimes[g]<min)) {
							min = burstTimes[g];
							c = g;
						}
					}
					
					/* If c== process means c value can not updated because no process arrival time< system time 
					 * so we increase the system time */
					if (c== process) 
						systemTime++;
					else
					{
						completionTimes[c]=systemTime +burstTimes[c];
						systemTime +=burstTimes[c];
						turnAroundTimes[c]=completionTimes[c] - arrivalTimes[c];
						waitingTimes [c] = turnAroundTimes[c] - burstTimes[c];
						f[c]=1;
						totalProcess++;
					}
					
					for (int b = 0; b < process; b++) {
						totalWaitingTime += waitingTimes[b];
						totalTurnAroundTime += turnAroundTimes[b];
					}
					avgWaitingTime = totalWaitingTime / process;
					avgTurnAroundTime = totalTurnAroundTime / process;
				}
				break;
				
			case "Shortest Remaining Time":
				        
				int totalBurstTime =0, k=0, co=0, small=999, sp=0, sp1=0, x=0, count=0;
				totalWaitingTime = 0;
				totalTurnAroundTime = 0;
				int pro[][]=new int[process][3];
			 	int[] awt1 = new int [20];
			 	processIDs = new int[20];

				for(int v = 0; v < process; v++) {
					pro[v][0]= v;

					pro[v][1] = burstTimes[v];
					totalBurstTime += pro[v][1];

					pro[v][2]= arrivalTimes[v];
				}
				
				// sort arrival times
				for(int l3=0; l3 < process;l3++)
					for(int j1= l3 +1;j1 < process; j1++)
						if(pro[l3][2]>pro[j1][2]) {
							int temp[]=pro[l3];
							pro[l3]=pro[j1];
							pro[j1]=temp;
						}
				
				//do shortest remaining time algorithm
				for(int l4 =1; l4 <= totalBurstTime;l4++) {
					small = 999;
					for(int j = co;j < process;j++)
						if(k >= pro[j][2])
							co++;
					for(int j=0;j < co;j++) {
						if(small>pro[j][1] && pro[j][1]!=0) {
							small=pro[j][1];
							sp=pro[j][0];
							sp1=j;
						}
					}
					
					if(processIDs[x] == sp) {
						awt1[x+1]++;
					} else {
						x++;
						processIDs[x]=sp;
						awt1[x+1]= awt1[x];
						awt1[x+1]++;
						count++;
					}
					
					pro[sp1][1]-=1;
					if(pro[sp1][1]==0)
						turnAroundTimes[sp1] = l4;
					for(int j=0;j < process;j++) {
						if(pro[j][1]!=0 && j!=sp)
							waitingTimes[j]+=1;
					}
					k++;
				}
				
				for(int m =0;m < process; m++)
					for(int j=m+1;j< process;j++)
						if(pro[m][0]>pro[j][0]) {
							int temp[]=pro[m];
							pro[m]=pro[j];
							pro[j]=temp;
							int tem= waitingTimes[m], tem1= turnAroundTimes[m];
							waitingTimes[m] = waitingTimes[j];
							waitingTimes[j] = tem;
							turnAroundTimes [m] = turnAroundTimes[j];
							turnAroundTimes[j] = tem1;
						}

				for(int m=0; m<= count; m++)
					processIDs[m] += 1;
				
				for (int i1 = 0; i1 < process; i1++) {
					totalWaitingTime += waitingTimes [i1]- arrivalTimes[i1];
				}
				
				for (int i2 = 0; i2 < process; i2++) {
					totalTurnAroundTime += waitingTimes[i2] - arrivalTimes[i2] + burstTimes[i2];
				}
				
				avgWaitingTime = totalWaitingTime/process;
				avgTurnAroundTime = totalTurnAroundTime/process;
			
				break;
				
			case "HRRN": 
				Process Pro5[] = HRRN.findavgTime(process, processIDs, burstTimes, arrivalTimes);
				float twt5 = 0;
				for(int m = 0; m < process; m++) {
					twt5 = twt5 + Pro5[m].getWt();
				}	
				avgWaitingTime  = twt5 /process;
		
				//finds average turn around time. 
				float ttt5 = 0;
				for(int j = 0; j < process; j++) {
					ttt5 = ttt5 + Pro5[j].getTt();
				}
				avgTurnAroundTime = ttt5 /process;
				
				break;
			case "Multilevel Feedback": //time quantum is 10
				Process Pro6[] = MultilevelFeedback.findavgTime(process, processIDs, burstTimes, arrivalTimes);
				
				float twt6 = 0;
				for(int n = 0; n < process; n++) {
					twt6 = twt6 + Pro6[n].getWt();
				}	
				avgWaitingTime = twt6 /process;

				//finds average turn around time. 
				float ttt6 = 0;
				for(int k1 = 0; k1 < process; k1++) {
					ttt6 = ttt6 + Pro6[k1].getTt();
				}
				avgTurnAroundTime = ttt6 /process;
				break;
			default: System.out.println("Sorry!");
		}
		
		repaint();
	}

}
