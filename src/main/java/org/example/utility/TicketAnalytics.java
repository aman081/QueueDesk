package org.example.utility;

import org.example.tickets.Ticket;
import java.util.*;
import java.util.stream.Collectors;


public class TicketAnalytics {

   public static Map<Enums.Priority, Long> countByPriority(List<Ticket> tickets){
        return tickets
                .stream()
                .collect(Collectors.groupingBy(Ticket::getPriority, Collectors.counting()));
    }

    public static Map<String, List<Ticket>> groupByAssignee(List<Ticket> tickets) {
        return tickets
                .stream()
                .collect(Collectors.groupingBy(t->t.getAssignee()==null? "unassigned" : t.getAssignee(), Collectors.toList()));
    }

    public static double averageEffortHours(List<Ticket> tickets){
        return tickets
                .stream()
                .mapToDouble(Ticket::estimateEffortHours)
                .average()
                .orElse(0.0);
    }

    public static double totalEffortHours(List<Ticket> tickets){
        return tickets
                .stream()
                .map(Ticket::estimateEffortHours)
                .reduce(0.0,Double::sum,Double::sum);
    }

    public static Optional<Ticket> oldestOpenTicket(List<Ticket> tickets){
       return tickets
               .stream()
               .filter(t->t.getStatus()== Enums.Status.OPEN)
               .min(Comparator.comparing(Ticket::getCreatedAt));
    }

    public static String tagLine(List<Ticket> tickets){
       return tickets
               .stream()
               .flatMap(t->t.getTags().stream())
               .distinct()
               .sorted()
               .collect(Collectors.joining(", "));
    }


}
