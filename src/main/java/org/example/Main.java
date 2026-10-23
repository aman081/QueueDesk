package org.example;
import org.example.tickets.*;
import java.util.List;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.nio.file.Path;
import org.example.exceptions.*;
import org.example.utility.*;
import org.example.utility.Enums;





//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static <T extends Ticket> T oldest(List<T> tickets){
        if (tickets == null||tickets.isEmpty()){
            return null;
        }

        T oldestTicket = tickets.get(0);
        for (T ticket : tickets) {
            // isBefore() i have learnt that it is a built-in method in localDateTime
            if(ticket.getCreatedAt().isBefore(oldestTicket.getCreatedAt())) {
                oldestTicket = ticket;
            }
        }
        return oldestTicket;
    }

    public static void printTriageBoard(List<Ticket>tickets){
        for(Ticket ticket:tickets){
            System.out.println(ticket.toString());
            System.out.println("Estimated Effort Hours is: "+ticket.estimateEffortHours()+" hours");

            if(ticket instanceof Escalatable e){
                String badge=e.escalationBadge();

                if(!badge.isEmpty()){
                    System.out.println(badge);
                }
            }
        }


    }



    public static void main(String[] args) {
//        Scanner sc=new Scanner(System.in);
//
//         int ticketNumber=sc.nextInt();
//         int customerCount=sc.nextInt();
//
//         int served=0;
//         int vip=0;
//         int priority_recheck=0;
//
//         for(int i=1;i<=customerCount;i++){
//             int currTicket=ticketNumber+i-1;
//
//             System.out.println("Now serving ticket :"+ currTicket);
//             if(i%10==0){
//                 vip++;
//                 System.out.println("This is a VIP ticket. VIP count: "+vip);
//                 priority_recheck++;
//                 System.out.println("Priority-recheck: "+ priority_recheck);
//             }
//             else if(i%5==0){
//                 vip++;
//                 System.out.println("This is a VIP ticket. VIP count: "+vip);
//             }
//             served++;
//
//         }
//
//         System.out.println("Total served tickets: "+served);
//         System.out.println("Total VIPs served: "+vip);
//         System.out.println("Total priority recheck: "+priority_recheck);




       //pq1 natural ordering
        PriorityQueue<Ticket> triageQueue = new PriorityQueue<>();

        //pq2 - assignee then priority
        PriorityQueue<Ticket> assigneeQueue = new PriorityQueue<>(TicketComparators.BY_ASSIGNEE_PRIORITY);

        // llm genertaed tickets
        BugTicket bug1 = new BugTicket("Server crash", "App won't start", "Alice",
                Enums.Priority.CRITICAL, Enums.Severity.CRITICAL, "Restart app");
        bug1.setAssignee("Zack");

        AccessRequestTicket access1 = new AccessRequestTicket("Need DB access", "Read only", "Bob",
                Enums.Priority.LOW, "Postgres", Enums.Access.USER);
        access1.setAssignee("Alice");


        triageQueue.offer(bug1);
        triageQueue.offer(access1);

        assigneeQueue.offer(bug1);
        assigneeQueue.offer(access1);



        System.out.println("Serving from Natural Order Queue-");

        Ticket nextNatural = triageQueue.poll();
        System.out.println("Next up:" + nextNatural.getTitle());

        System.out.println("\nServing from Assignee Queue-");

        Ticket nextAssigned = assigneeQueue.poll();
        System.out.println("Next up:" + nextAssigned.getTitle());



        //build 5 entry point

        Repository<Ticket> repo = new Repository<>();


        try {
            Path inputPath = Path.of("src/main/java/org/example/tickets.txt");
            List<Ticket>loaded= TicketFileLoader.load(inputPath);
            for (Ticket t : loaded) repo.add(t);

        } catch (InvalidTicketDataException e) {
            System.out.println("CRITICAL FAILURE: Corrupt data file.");
            System.out.println(e.getMessage());
            return;
        }

    //missing ticket
        try {
            Ticket missing = repo.findById(999);
            System.out.println(missing.getTitle());

        } catch (TicketNotFoundException e) {
            System.out.println("Search Error: " + e.getMessage());
        }


        Path outputPath = Path.of("src/main/java/org/example/summary.txt");
        ReportGenerator.generateEndOfDaySummary(repo.all(), outputPath);
    }

    }
