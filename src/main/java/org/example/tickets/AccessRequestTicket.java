package org.example.tickets;

import org.example.utility.Enums;

public class AccessRequestTicket extends Ticket implements Escalatable {
    private String systemName;

    private Enums.Access accessLevel;

    public AccessRequestTicket(String title, String description, String requester, Enums.Priority priority,
                               String systemName, Enums.Access accessLevel) {
        super(title,description,requester,priority);
        this.systemName = systemName;
        this.accessLevel = accessLevel;
    }

    @Override
    public double estimateEffortHours() {
        switch (accessLevel){
            case ADMIN: return 2;
            case MOD: return 3;
            case USER: return 5;
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
        return super.toString()+" accessLevel: "+accessLevel+" systemName: "+systemName+"\n";
    }
}
