package cn.herodotus.eurynome.bpmn.logic.constants;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/4 10:19
 */
public enum CommentType {
    /**
     * 备注事项
     */
    APPROVAL(0, "审批"),
    REJECT(1, "驳回"),
    REVOKE(2, "撤回"),
    TEMPORARY(3, "暂存"),
    CLAIM(4, "签收"),
    DELEGATE(5, "委派"),
    INFORM(6, "知会"),
    TURN(7, "转阅"),
    READ(8, "已阅"),
    TURN_TODO(9, "转办"),
    PRE_SIGNATURE(10, "前加签"),
    POST_SIGNATURE(11, "后加签"),
    EXECUTION(12, "执行"),
    SUBMIT(13, "提交"),
    RESUBMIT(14, "重新提交"),
    END_OF_APPROVAL(15, "审批结束"),
    PROCESS_TERMINATED(16, "流程终止"),
    AUTHORIZATION(17, "授权"),
    REPEAT_SKIP(18, "重复跳过"),
    COLLABORATION(19, "协同"),
    REVIEW(20,"评审");

    /**
     * 请假类型索引
     */
    private Integer type;
    /**
     * 请假类型描述
     */
    private String message;

    private static Map<Integer, CommentType> indexMap = new HashMap<>();
    private static Map<String, String> messageMap = new HashMap<>();
    private static List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (CommentType commentType : CommentType.values()) {
            indexMap.put(commentType.type, commentType);
            messageMap.put(commentType.name(), commentType.message);
            toJsonStruct.add(commentType.type,
                    ImmutableMap.<String, Object>builder()
                            .put("value", commentType.getType())
                            .put("key", commentType.name())
                            .put("text", commentType.getMessage())
                            .build());
        }
    }

    CommentType(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public static CommentType getCommentType(Integer type) {
        return indexMap.get(type);
    }

    public static String getMessageByName(String name) {
        return messageMap.get(name);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
