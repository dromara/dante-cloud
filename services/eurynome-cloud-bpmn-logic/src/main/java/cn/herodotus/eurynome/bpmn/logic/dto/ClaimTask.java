package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;

/**
 * <p> Description : 签收参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 13:30
 */
public class ClaimTask extends BaseProcessDTO {

    public ClaimTask() {
        this.setType(CommentType.CLAIM.name());
    }
}
