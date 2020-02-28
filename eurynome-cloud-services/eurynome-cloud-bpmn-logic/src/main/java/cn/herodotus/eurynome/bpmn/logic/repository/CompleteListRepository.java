package cn.herodotus.eurynome.bpmn.logic.repository;

import cn.herodotus.eurynome.bpmn.logic.entity.CompleteList;
import cn.herodotus.eurynome.component.data.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/9 10:02
 */
public interface CompleteListRepository extends BaseRepository<CompleteList, String> {

    Page<CompleteList> findAllByApproverId(String employeeId, Pageable pageable);
}
