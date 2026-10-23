package org.example.utility;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.example.tickets.*;
import org.example.exceptions.*;

import org.example.utility.Enums;




public class ReportGenerator {
    public static void generateEndOfDaySummary(List<Ticket> tickets, Path outputPath) {

        StringBuilder report = new StringBuilder();


        report.append("*** END OF DAY TRIAGE REPORT ***\n");


        int openCount = 0;
        int criticalCount = 0;
        Ticket oldestOpen = null;

        report.append("*OVERDUE CRITICAL TICKETS*\n");

        for (Ticket t : tickets) {
            // Count statuses
            if (t.getStatus() == Enums.Status.OPEN) {
                openCount++;

                // Find oldest open ticket
                if (oldestOpen == null || t.getCreatedAt().isBefore(oldestOpen.getCreatedAt())) {
                    oldestOpen = t;
                }
            }


            if (t.getPriority() == Enums.Priority.CRITICAL) {
                criticalCount++;
                if (t.getStatus() != Enums.Status.CLOSED && t.ageInHours() >= 24) {
                    report.append("[ALERT] Ticket #").append(t.getId())
                            .append(" is overdue! (").append(t.ageInHours()).append(" hrs)\n");
                }
            }
        }

        report.append("\n METRICS:\n");
        report.append("Total Open Tickets: ").append(openCount).append("\n");
        report.append("Total Critical Tickets: ").append(criticalCount).append("\n");

        if (oldestOpen != null) {
            report.append("\nOldest Open Ticket: #").append(oldestOpen.getId())
                    .append(" - ").append(oldestOpen.getTitle());
        }



        //writing
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            writer.write(report.toString());
            System.out.println("Success: EOD Report generated at" + outputPath.toString());
        } catch (IOException e) {
            System.out.println("Encountered Error saving report:" + e.getMessage());
        }
    }


}
