package cn.herodotus.eurynome.bpmn.logic.repository;

import cn.herodotus.eurynome.bpmn.logic.entity.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/9 10:50
 */
public interface TodoListRepository extends JpaRepository<TodoList, String> {

    Page<TodoList> findDistinctTopByCandidateUserIdOrAssigneeIdOrLinkUserId(String candidateUserId, String assigneeId, String linkUserId, Pageable pageable);

    TodoList findFirstByTaskId(String taskId);
}
