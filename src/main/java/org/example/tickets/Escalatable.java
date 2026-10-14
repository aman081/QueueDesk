package org.example.tickets;

public interface Escalatable {
    public void escalate();
    public boolean isEscalated();
    public int escalationLevel();

    default String escalationBadge() {
        if(escalationLevel() == 0) return "";
        return "Ticket has been escalated to level " + escalationLevel();
    }
}
