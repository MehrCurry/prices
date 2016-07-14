package com.example.converter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime>
{
    public LocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime date, JsonGenerator json,
                          SerializerProvider provider) throws IOException,
            JsonGenerationException {
        // The client side will handle presentation, we just want it accurate
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String out = formatter.format(date);
        json.writeString(out);
    }}
