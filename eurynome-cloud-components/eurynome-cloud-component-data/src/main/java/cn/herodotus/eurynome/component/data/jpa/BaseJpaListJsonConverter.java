package cn.herodotus.eurynome.component.data.jpa;

import cn.herodotus.eurynome.component.common.definition.AbstractDomain;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Description : Jpa JSON字符串字段与List自动转换 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/31 11:11
 */
public abstract class BaseJpaListJsonConverter<T extends AbstractDomain> implements AttributeConverter<List<T>, String> {

    @Override
    public String convertToDatabaseColumn(List<T> ts) {
        return JSON.toJSONString(ts);
    }

    @Override
    public List<T> convertToEntityAttribute(String s) {
        List<T> result = JSON.parseObject(s, new TypeReference<List<T>>() {});
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
