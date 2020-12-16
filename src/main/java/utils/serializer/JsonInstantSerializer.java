package utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

import static utils.DateUtils.wrapToPayloadValue;

/**
 * @author uladzislau.hatsko
 */
public class JsonInstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant date, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(wrapToPayloadValue(date));
    }
}
