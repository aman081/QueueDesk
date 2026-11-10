package org.example.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.exceptions.InvalidTicketDataException;
import org.example.tickets.Ticket;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class TicketJsonStore {

    private static final ObjectMapper mapper=new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void save(List<Ticket> tickets, Path file) throws InvalidTicketDataException {
        try {
            mapper.writerFor(new TypeReference<List<Ticket>>() {})
                    .writeValue(file.toFile(), tickets);
        }catch(IOException e) {
            throw new InvalidTicketDataException("Failed to save as Json",e);
        }
    }

    public List<Ticket> load(Path file) throws InvalidTicketDataException{
        try {
            return mapper.readValue(file.toFile(), new TypeReference<List<Ticket>>() {});
        } catch(IOException e) {
            throw new InvalidTicketDataException("Failed to load from Json", e);
        }

    }
}
