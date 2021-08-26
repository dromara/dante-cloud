package cn.herodotus.eurynome.upms.logic.service.development;

import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.development.Supplier;
import cn.herodotus.eurynome.upms.logic.repository.development.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> Description : SupplierService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 13:58
 */
@Service
public class SupplierService extends BaseLayeredService<Supplier, String> {

    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public BaseRepository<Supplier, String> getRepository() {
        return supplierRepository;
    }
}
