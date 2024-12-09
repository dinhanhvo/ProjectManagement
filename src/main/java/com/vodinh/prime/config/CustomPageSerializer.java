package com.vodinh.prime.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.Page;

import java.io.IOException;

public class CustomPageSerializer extends StdSerializer<Page<?>> {

    public CustomPageSerializer() {
        super((Class<Page<?>>) null);
    }

    @Override
    public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("content", page.getContent());
        gen.writeNumberField("page", page.getNumber() + 1); // Điều chỉnh từ 0-based sang 1-based
        gen.writeNumberField("size", page.getSize());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeEndObject();
    }
}
