package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.entity.CompleteList;
import cn.herodotus.eurynome.bpmn.logic.repository.CompleteListRepository;
import cn.herodotus.eurynome.data.base.service.ReadableService;
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
 * @date : 2020/2/9 10:03
 */
@Slf4j
@Service
public class CompleteListService {

    private final CompleteListRepository completeListRepository;

    @Autowired
    public CompleteListService(CompleteListRepository completeListRepository) {
        this.completeListRepository = completeListRepository;
    }

    public Page<CompleteList> findByPageWithEmployeeId(String employeeId, int pageNumber, int pageSize) {
        log.debug("[Herodotus] |- Complete Task Service findByPageWithEmployeeId.");
        return completeListRepository.findAllByApproverId(employeeId, PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "startTime"));
    }
}
