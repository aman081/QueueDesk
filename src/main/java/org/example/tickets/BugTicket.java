package org.example.tickets;

public class BugTicket extends Ticket implements Escalatable {

    public BugTicket(String title, String description, String requester, Priority priority,
                     Severity severity, String stepsToReproduce) {
        super(title,description,requester,priority);
        this.severity = severity;
        this.stepsToReproduce = stepsToReproduce;
    }

    private String stepsToReproduce;
    public enum Severity{
        LOW,MEDIUM,HIGH,CRITICAL;
    }

    public Severity severity;

    @Override
    public double estimateEffortHours() {
        switch (severity) {
            case LOW:return 2;
            case MEDIUM:return 3;
            case HIGH:return 5;
            case CRITICAL:return 6;
            default:return 0;
        }
    }

    private int escalationLevel=0;


    public void escalate(){
        escalationLevel++;
    }
    public boolean isEscalated(){
        return escalationLevel>0;
    };

    public int escalationLevel(){
        return escalationLevel;
    };

    @Override
    public String toString(){
        return super.toString()+"Severity: "+severity+"\n";
    }

}
