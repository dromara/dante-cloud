package cn.herodotus.eurynome.bpmn.logic.repository;

import cn.herodotus.eurynome.bpmn.logic.entity.LaunchedList;
import cn.herodotus.eurynome.component.data.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p> Description : 我发起的流程事项 Repository</p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 11:21
 */
public interface LaunchedListRepository extends BaseRepository<LaunchedList, String> {

    Page<LaunchedList> findAllByInitiatorId(String employeeId, Pageable pageable);
}
