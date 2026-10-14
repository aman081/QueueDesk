package org.example.exceptions;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(int id) {
        super("No ticket found with ID [" + id + "]");
    }
}
