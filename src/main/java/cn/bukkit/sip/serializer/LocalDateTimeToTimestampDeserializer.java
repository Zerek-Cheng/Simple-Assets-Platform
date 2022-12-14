package cn.bukkit.sip.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeToTimestampDeserializer extends StdDeserializer<LocalDateTime> {
    public LocalDateTimeToTimestampDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(p.getLongValue()), ZoneId.systemDefault());
    }
}