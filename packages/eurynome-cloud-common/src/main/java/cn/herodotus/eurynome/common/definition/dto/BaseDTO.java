package cn.herodotus.eurynome.common.definition.dto;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;

public abstract class BaseDTO extends AbstractEntity {

    @Override
    public String getLinkedProperty() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
