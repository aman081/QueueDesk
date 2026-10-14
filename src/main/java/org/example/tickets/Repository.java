package org.example.tickets;

import org.example.exceptions.TicketNotFoundException;

import java.util.*;

public class Repository<T extends Ticket> {


    private List<T> items;

    public Repository() {
        this.items=new ArrayList<>();
    }

    public void add(T item) {
        this.items.add(item);
    }


    public List<T> all() {

        return Collections.unmodifiableList(items);
    }


    public T findById(int id) {
        for(T item:items){
            if(item.getId()==id){
                return item;
            }
        }
        throw new TicketNotFoundException(id);
    }


    public Map<String,List<T>> groupByAssignee() {
        Map<String,List<T>> grouped=new HashMap<>();

        for (T item : items) {
            String assignee = item.getAssignee();

            // If this is the first time we've seen this assignee, create an empty list for them
            if (!grouped.containsKey(assignee)) {
                grouped.put(assignee,new ArrayList<>());
            }

            // Add the ticket to that assignee's list
            grouped.get(assignee).add(item);
        }

        return grouped;
    }
}