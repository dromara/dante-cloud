package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.entity.LaunchedList;
import cn.herodotus.eurynome.bpmn.logic.repository.LaunchedListRepository;
import cn.herodotus.eurynome.component.data.service.BaseReadableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 11:25
 */
@Slf4j
@Service
public class LaunchedListService extends BaseReadableService<LaunchedList, String> {

    private final LaunchedListRepository launchedListRepository;

    @Autowired
    public LaunchedListService(LaunchedListRepository launchedListRepository) {
        this.launchedListRepository = launchedListRepository;
    }

    @Override
    public Page<LaunchedList> findByPageWithEmployeeId(String employeeId, int pageNumber, int pageSize) {
        log.debug("[Luban] |- Launched Task Service findByPageWithEmployeeId");
        return launchedListRepository.findAllByInitiatorId(employeeId, PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "startTime"));
    }
}
