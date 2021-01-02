package cn.herodotus.eurynome.common.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>Description: 空 List Json序列化器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/8 16:48
 */
public class NullListJsonSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartArray();
        jgen.writeEndArray();
    }
}
