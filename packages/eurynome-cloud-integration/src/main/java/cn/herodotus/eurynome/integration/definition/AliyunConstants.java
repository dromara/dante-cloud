package cn.herodotus.eurynome.integration.definition;

/**
 * <p>Description: 阿里第三方内容涉及常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/22 15:42
 */
public class AliyunConstants {

    /**
     * 涉政恐怖言论反垃圾antispam
     */
    public static final String SCENE_ANTISPAM = "antispam";
    /**
     * 含垃圾信息
     */
    public static final String SCENE_SPAM = "spam";
    /**
     * 广告
     */
    public static final String SCENE_AD = "ad";
    /**
     * 涉政
     */
    public static final String SCENE_POLITICS = "politics";
    /**
     * 暴恐
     */
    public static final String SCENE_TERRORISM = "terrorism";
    /**
     * 辱骂
     */
    public static final String SCENE_ABUSE = "abuse";
    /**
     * 色情
     */
    public static final String SCENE_PORN = "porn";
    /**
     * 正常场景
     */
    public static final String SCENE_NORMAL = "normal";
    /**
     * 灌水
     */
    public static final String SCENE_FLOOD = "flood";
    /**
     * 违禁
     */
    public static final String SCENE_CONTRABAND = "contraband";
    /**
     * 无意义
     */
    public static final String SCENE_MEANINGLESS = "meaningless";
    /**
     * 自定义（例如命中自定义关键词）
     */
    public static final String SCENE_CUSTOMIZED = "customized";
    /**
     * 该字段用于标识业务场景。针对不同的业务场景，您可以配置不同的内容审核策略，以满足不同场景下不同的审核标准或算法策略的需求。
     * 您可以通过云盾内容安全控制台创建业务场景（bizType），或者通过工单联系我们帮助您创建业务场景。
     */
    public static final String BIZ_TYPE = "security_Policy";
}
