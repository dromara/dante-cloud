package cn.herodotus.eurynome.upms.api.helper;

import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * 自定义UUID生成器，使得保存实体类时可以在保留主键生成策略的情况下自定义表的主键
 * @author gengwei.zheng
 */
public class SysAuthorityUUIDGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        SysAuthority sysAuthority = (SysAuthority) object;

        if (StringUtils.isEmpty(sysAuthority.getAuthorityId())) {
            return super.generate(session, object);
        } else {
            return sysAuthority.getAuthorityId();
        }
    }
}
