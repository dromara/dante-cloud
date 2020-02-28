package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.mapper.HistoryActivityInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> Description : HistoryActivityInstanceService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 16:14
 */

@Service
public class HistoryActivityInstanceService {

    @Autowired
    private HistoryActivityInstanceRepository historyActivityInstanceRepository;

    public void deleteHistoryActivityInstance(String processInstanceId, String activityInstanceId) {
        historyActivityInstanceRepository.deleteHistoryActivityInstance(processInstanceId, activityInstanceId);
    }
}
