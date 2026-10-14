package org.example.tickets;
import java.util.Comparator;

public class TicketComparators {
    public static final Comparator<Ticket> BY_ASSIGNEE_PRIORITY =
            Comparator.comparing(Ticket::getAssignee, Comparator.nullsLast(String::compareTo)).thenComparing(Ticket::getPriority, Comparator.reverseOrder());

    public static final Comparator<Ticket> BY_STATUS_AGE =
            Comparator.comparing(Ticket::getStatus).thenComparing(Ticket::getCreatedAt);


}
