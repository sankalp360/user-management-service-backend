package org.publicis.users.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-M-d")  // This will handle dates without leading zeros
            .toFormatter();

    public CustomLocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String date = parser.getText();
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            // If the custom format fails, try the standard ISO format
            try {
                return LocalDate.parse(date);
            } catch (DateTimeParseException e2) {
                throw new IOException("Unable to parse date: " + date, e2);
            }
        }
    }
}