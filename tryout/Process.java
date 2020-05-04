
package Scheduling;

public class Process { 
	
	int num; // number process
	int bt; // burst time
	int at; // arrival time
	int completed; // 0 = no, 1 = yes
	int wt; // wait time
	int ct; // completion time	
	int tt;	// turn around time
	int tl; // time left

	public Process(int num, int bt, //creates process object
	            int at) 
	{ 
	 this.num = num; 
	 this.bt = bt;  
	 this.at = at;
	 this.completed = 0;
	 this.tl = bt;
	} 
	public void adgTl(int q){ //adjusts time left 
		this.tl -= q;		
	}
	public int getTl(){ //returns time left
		return tl;		
	}
	public int getNum(){ //gets process number
		return num;		
	}
	public int getAt(){ //gets arrival time
		return at;		
	}
	public int getBt(){ //gets burst time
		return bt;		
	}
	public int getTt(){ //gets turn around time
		return tt;		
	}
	public int getWt(){ //gets wait time
		return wt;		
	}
	public int getCom(){ //returns the binary completed variable
		return completed;		
	}
	public void setCom(){ //sets completed to 1 or yes
		this.completed = 1;		
	}
	public void setWt(){ // sets wait time
		int temp = this.tt - this.bt;		
		this.wt = temp;		
	}
	public void setCt(int n){ //sets completion time
		this.ct = n;		
	}
	public void setTt(){ // sets turn around time
		int temp = this.ct - this.at;
		this.tt = temp;		
	}	
}

