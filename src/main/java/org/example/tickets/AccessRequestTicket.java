package org.example.tickets;

public class AccessRequestTicket extends Ticket implements Escalatable {
    private String systemName;
    public enum Access{
        ADMIN,MOD,USER
    }
    public Access accessLevel;

    public AccessRequestTicket(String title, String description, String requester, Priority priority,
                               String systemName, Access accessLevel) {
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
