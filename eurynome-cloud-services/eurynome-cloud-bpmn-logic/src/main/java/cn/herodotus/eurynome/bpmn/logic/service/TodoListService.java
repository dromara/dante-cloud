package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.entity.TodoList;
import cn.herodotus.eurynome.bpmn.logic.repository.TodoListRepository;
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
 * @date : 2020/2/9 10:49
 */
@Slf4j
@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public Page<TodoList> findByPageWithEmployeeId(String employeeId, int pageNumber, int pageSize) {
        log.debug("[Herodotus] |- Todo Task Service findByPageWithEmployeeId");
        return todoListRepository.findDistinctTopByCandidateUserIdOrAssigneeIdOrLinkUserId(employeeId, employeeId, employeeId, PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "startTime"));
    }

    public TodoList findOneByTaskId(String taskId) {
        log.debug("[Herodotus] |- Todo Task Service findOneByTaskId");
        return todoListRepository.findFirstByTaskId(taskId);
    }
}
