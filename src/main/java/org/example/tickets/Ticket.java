package org.example.tickets;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.utility.Enums.Priority;
import org.example.utility.Enums.Status;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = BugTicket.class, name = "bug"),
        @JsonSubTypes.Type(value = AccessRequestTicket.class, name = "access"),
        @JsonSubTypes.Type(value = FeatureRequestTicket.class, name = "feature")
})

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)


public abstract class Ticket implements Comparable<Ticket>{
    private int id;
    private String title;
    private String description;
    private String requester;
    private String assignee=null;
    private Set<String>tags;


    private Priority priority;



    private Status status;
    private  LocalDateTime createdAt;

    private static int nextId=1;

    //short constructor

    public Ticket(String title,String requester){
        this(title,"no description",requester, Priority.LOW);
    }

    //long constructor

    public Ticket(){};

    public Ticket(String title, String description, String requester, Priority priority) {
        this.title = title;
        this.description = description;
        this.requester = requester;
        this.priority = priority;
        id=nextId++;
        status= Status.OPEN;
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
    public Priority getPriority() {
        return priority;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Status getStatus() {
        return status;
    }
    public Set<String> getTags() {return tags;}

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
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setStatus(Status status) {
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
