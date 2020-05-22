package cn.herodotus.eurynome.common.jackson.deserializer;

import cn.herodotus.eurynome.common.enums.StatusEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author gengwei.zheng
 */
public class StatusEnumDeserializer extends JsonDeserializer<StatusEnum> {

    @Override
    public StatusEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
//        Integer status = (Integer) node.get("status").numberValue();
        Integer status = (Integer) node.numberValue();
        return StatusEnum.getStatus(status);
    }
}
