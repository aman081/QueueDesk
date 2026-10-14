package org.example.exceptions;


public class InvalidTicketDataException extends Exception {
    public InvalidTicketDataException(String message, int lineNumber) {
        super("Error parsing at line " + lineNumber + ":" + message);
    }
}
