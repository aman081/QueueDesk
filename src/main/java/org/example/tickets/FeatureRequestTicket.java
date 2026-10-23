package org.example.tickets;
import org.example.utility.Enums;

public class FeatureRequestTicket extends Ticket {

    private int votes;
    private String businessJustification;

    public FeatureRequestTicket(String title, String description, String requester, Enums.Priority priority,
                                int votes,String businessJustification){
        super(title,description,requester,priority);
        this.votes = votes;
        this.businessJustification = businessJustification;
    }

    @Override
    public double estimateEffortHours() {
    //base time + feature dependent time
        return 6+ votes*2;
    }

    @Override
    public String toString(){
        return super.toString()+" votes: "+votes+" businessJustification: "+businessJustification;
    }
}
