package org.example.tickets;
import org.example.utility.Enums;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;


public abstract class Ticket implements Comparable<Ticket>{
    private int id;
    private String title;
    private String description;
    private String requester;
    private String assignee=null;
    private Set<String>tags;


    private Enums.Priority priority;



    private Enums.Status status;
    private final LocalDateTime createdAt;

    private static int nextId=1;

    //short constructor

    public Ticket(String title,String requester){
        this(title,"no description",requester, Enums.Priority.LOW);
    }

    //long constructor

    public Ticket(String title, String description, String requester, Enums.Priority priority) {
        this.title = title;
        this.description = description;
        this.requester = requester;
        this.priority = priority;
        id=nextId++;
        status= Enums.Status.OPEN;
        this.createdAt = LocalDateTime.now();

        tags=new HashSet<>();
    }

    //all getters

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getRequester() {
        return requester;
    }
    public Enums.Priority getPriority() {
        return priority;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Enums.Status getStatus() {
        return status;
    }

    public String getAssignee() {
        return assignee;
    }
    //required setters

    public void setDescription(String description) {
        if(description==null || description.isBlank()) description="No description provided";

        String trimmed=description.trim();
        int reqLen= Math.min(trimmed.length(),50);
        this.description = trimmed.substring(0,reqLen);

        if(trimmed.length()>50){
            this.description+="..";
        }
    }
    public void setPriority(Enums.Priority priority) {
        this.priority = priority;
    }
    public void setStatus(Enums.Status status) {
        this.status = status;
    }

    public long ageInHours(){
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        return duration.toHours();
    }

    public static int totalCreated(){
        return Ticket.nextId-1;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void addTags(String tag) {
        tags.add(tag);
    }

    public abstract double estimateEffortHours();


    public String toString() {
        return "Ticket id: " +id+ " Status: "+status+" priority: "+this.priority+"\n";
    }

    public int compareTo(Ticket ticket) {
        if(priority==ticket.priority) return createdAt.compareTo(ticket.createdAt);
        return ticket.priority.compareTo(priority);
    }


}
