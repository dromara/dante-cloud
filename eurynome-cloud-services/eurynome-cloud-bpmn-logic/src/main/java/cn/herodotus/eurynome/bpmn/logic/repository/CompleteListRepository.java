package cn.herodotus.eurynome.bpmn.logic.repository;

import cn.herodotus.eurynome.bpmn.logic.entity.CompleteList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/9 10:02
 */
public interface CompleteListRepository extends JpaRepository<CompleteList, String> {

    Page<CompleteList> findAllByApproverId(String employeeId, Pageable pageable);
}
