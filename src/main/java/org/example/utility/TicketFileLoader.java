package org.example.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import org.example.tickets.*;
import org.example.exceptions.*;
import java.io.BufferedReader;
import org.example.utility.Enums;



public class TicketFileLoader {

    public static List<Ticket> load(Path file) throws InvalidTicketDataException {
        List<Ticket> loadedTickets=new ArrayList<>();

        try (BufferedReader reader=Files.newBufferedReader(file)) {
            String line;
            int lineNumber = 0;

            while ((line=reader.readLine())!=null) {
                lineNumber++;

                if (line.isBlank()) continue; // Skip empty lines
                String[] fields = line.split("\\|");

                // Enforce field count rule
                if (fields.length < 4) {
                    throw new InvalidTicketDataException("Expected 4 fields, found " + fields.length, lineNumber);
                }

                try {
                    String title = fields[0].trim();
                    String desc = fields[1].trim();
                    String requester = fields[2].trim();

                    // we are actually using try catch for this... (eg- wromg spelling of priority or illegal priority)
                    Enums.Priority priority = Enums.Priority.valueOf(fields[3].trim().toUpperCase());

                    //new ticket initialize
                    BugTicket parsedTicket = new BugTicket(title, desc, requester, priority,
                            Enums.Severity.LOW, "N/A");
                    loadedTickets.add(parsedTicket);

                } catch (IllegalArgumentException e) {
                    throw new InvalidTicketDataException("Priority value not allowed", lineNumber);
                }
            }
        } catch (IOException e) {
            System.out.println("There's some error reading file, try again:" + e.getMessage());
        }

        return loadedTickets;
    }
}

