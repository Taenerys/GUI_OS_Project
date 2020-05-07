package Scheduling;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;

class Job {


    //field variables for the job
    private int processTime;
    private int timeRemaining;
    private int timeArrival;
    private int id;


    //constructor to intialize time remaining and arrival time
    public Job(int id, int processTime, int timeArrival) {
        this.id = id;
        this.processTime = processTime;
        this.timeRemaining = processTime;
        this.timeArrival = timeArrival;
    }

    public int getId(){
        return id;
    }

    public int getProcessTime() {
        return processTime;
    }

    //setter for time remaining
    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    //getter for time remaining
    public int getTimeRemaining() {
        return timeRemaining;
    }

    //getter for time arrival
    public int getTimeArrival() {
        return timeArrival;
    }

    @Override
    public String toString() {
        return "Job{" +
                "processTime=" + processTime +
                ", timeRemaining=" + timeRemaining +
                ", timeArrival=" + timeArrival +
                '}';
    }
}

/**
 * This class extends Scheduler using shortest process next
 */
class ShortestProcessNext extends Scheduler {

    public ShortestProcessNext(){
        super();
    }

    //implement the sort method for the shortest time remaining scheduler
    protected void sort(){
        //sorts the queued jobs on custom comparator (time remaining)
        queued_jobs.sort(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if(o1.getProcessTime() < o2.getProcessTime()){
                    return -1;
                }
                else if(o1.getProcessTime() > o2.getProcessTime()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
    }
}

/**
 * This class extends Scheduler using shortest time remaining
 */
class ShortestTimeRemaining extends Scheduler{
    public ShortestTimeRemaining(){
        //intialize all variables
        super();
    }


    //implement the sort method for the shortest time remaining scheduler
    protected void sort(){
        //sorts the queued jobs on custom comparator (time remaining)
        queued_jobs.sort(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if(o1.getTimeRemaining() < o2.getTimeRemaining()){
                    return -1;
                }
                else if(o1.getTimeRemaining() > o2.getTimeRemaining()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
    }


}

/**
 * This class represents a scheduler
 * It uses a priority queue under the hood
 * Schedulers should inherit from this one to implement the priority queue
 */
public class Scheduler {

    //instance variables
    protected ArrayList<Job> all_jobs;
    protected ArrayList<Job> queued_jobs;
    protected ArrayList<Job> finished_jobs;
    protected int cycleCount;
    private ArrayList<Job> gant;

    //Constructor initializes everything
    public Scheduler() {
        all_jobs = new ArrayList<Job>();
        queued_jobs = new ArrayList<Job>();
        finished_jobs = new ArrayList<Job>();
        gant = new ArrayList<Job>();
        cycleCount = 0;

    }

    /**
     * Adds a job to the future jobs array
     * @param j the job to add
     */
    public void addJob(Job j){
        all_jobs.add(j);
    }

    public ArrayList<Job> getAll_jobs() {
        return all_jobs;
    }
