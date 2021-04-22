package cn.herodotus.eurynome.integration.social.definition.wxapp;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.herodotus.eurynome.integration.social.properties.WxappProperties;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 小程序订阅消息信息处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/9 16:22
 */
public abstract class SubscribeMessageHandler {

    @Autowired
    private WxappProperties wxappProperties;

    /**
     * 组装模版信息并获取WxJava定义的WxMaSubscribeMessage
     *
     * 参数类别	参数说明	参数值限制	说明
     * thing.DATA	事物	20个以内字符	可汉字、数字、字母或符号组合
     * number.DATA	数字	32位以内数字	只能数字，可带小数
     * letter.DATA	字母	32位以内字母	只能字母
     * symbol.DATA	符号	5位以内符号	只能符号
     * character_string.DATA	字符串	32位以内数字、字母或符号	可数字、字母或符号组合
     * time.DATA	时间	24小时制时间格式（支持+年月日），支持填时间段，两个时间点之间用“~”符号连接	例如：15:01，或：2019年10月1日 15:01
     * date.DATA	日期	年月日格式（支持+24小时制时间），支持填时间段，两个时间点之间用“~”符号连接	例如：2019年10月1日，或：2019年10月1日 15:01
     * amount.DATA	金额	1个币种符号+10位以内纯数字，可带小数，结尾可带“元”	可带小数
     * phone_number.DATA	电话	17位以内，数字、符号	电话号码，例：+86-0766-66888866
     * car_number.DATA	车牌	8位以内，第一位与最后一位可为汉字，其余为字母或数字	车牌号码：粤A8Z888挂
     * name.DATA	姓名	10个以内纯汉字或20个以内纯字母或符号	中文名10个汉字内；纯英文名20个字母内；中文和字母混合按中文名算，10个字内
     * phrase.DATA	汉字	5个以内汉字	5个以内纯汉字，例如：配送中
     *
     * {@see :https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html}
     *
     * @param toUser      发送订阅消息的目标用户OpenId
     * @param subscribeId WxappProperties中配置的subscribeId值，这个值需要配置到具体SubscribeMessageHandler实现类的注解上
     * @return WxMaSubscribeMessage {@link WxMaSubscribeMessage}
     */
    public abstract WxMaSubscribeMessage getSubscribeMessage(String toUser, String subscribeId, Map<String, Object> templateParams);

    /**
     * 创建WxMaSubscribeMessage对象，并将WxappProperties中的配置信息提前Set到WxMaSubscribeMessage对象中
     * @param subscribeId WxappProperties中配置的subscribeId值，这个值需要配置到具体SubscribeMessageHandler实现类的注解上
     * @return WxMaSubscribeMessage {@link WxMaSubscribeMessage}
     */
    protected WxMaSubscribeMessage getWxMaSubscribeMessage(String subscribeId) {
        List<WxappProperties.Subscribe> subscribes = this.wxappProperties.getSubscribes();
        WxappProperties.Subscribe subscribe =  subscribes.stream().filter(a -> a.getSubscribeId().equals(subscribeId)).findFirst().orElse(new WxappProperties.Subscribe());

        WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
        //跳转小程序页面路径
        wxMaSubscribeMessage.setPage(subscribe.getRedirectPage());
        //模板消息id
        wxMaSubscribeMessage.setTemplateId(subscribe.getTemplateId());

        return wxMaSubscribeMessage;
    }

    protected String getCurrentDate() {
        return DateUtil.format(new Date(), "yyyy-MM-dd");
    }

    protected String getCurrentTime() {
        return DateUtil.format(new Date(), "HH:mm");
    }

    protected String getCurrentDateTime() {
        return DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
    }
}
