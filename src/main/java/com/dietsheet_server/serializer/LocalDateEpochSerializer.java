package com.dietsheet_server.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;


public class LocalDateEpochSerializer extends StdSerializer<LocalDate> {
    public LocalDateEpochSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider sp) throws IOException {
        long epoch = value
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        gen.writeNumber(epoch);
    }
}
