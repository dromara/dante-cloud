package cn.herodotus.eurynome.crud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <p> Description : BaseSystemEntity </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 17:43
 */
@MappedSuperclass
public abstract class DefaultSysEntity extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = 64)
    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
