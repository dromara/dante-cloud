package cn.herodotus.eurynome.integration.rest.social.logic.wxapp;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.herodotus.eurynome.integration.social.definition.wxapp.SubscribeMessageHandler;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 示例的发送模版消息的 SubscribeMessageHandler</p>
 * <p>
 * 使用了基于Spring Boot的工厂模式，根据具体的模版实现对应的SubscribeMessageHandler，就可以发送订阅消息
 *
 * @author : gengwei.zheng
 * @date : 2021/4/9 16:49
 */
@Component("SM_DEMO")
public class DemoSubscribeMessageHandler extends SubscribeMessageHandler {

    @Override
    public WxMaSubscribeMessage getSubscribeMessage(String toUser, String subscribeId, Map<String, Object> templateParams) {

        WxMaSubscribeMessage wxMaSubscribeMessage = this.getWxMaSubscribeMessage(subscribeId);

        //给谁推送 用户的openid （可以调用根据code换openid接口)
        wxMaSubscribeMessage.setToUser(toUser);


        List<WxMaSubscribeMessage.MsgData> wxMaSubscribeData = new ArrayList<>();

        //第一个内容： 奖品名称
        WxMaSubscribeMessage.MsgData msgDataThing5 = new WxMaSubscribeMessage.MsgData();
        msgDataThing5.setName("thing5");
        msgDataThing5.setValue("halooo");
        //每个参数 存放到大集合中
        wxMaSubscribeData.add(msgDataThing5);

        // 第二个内容：用户昵称
        WxMaSubscribeMessage.MsgData msgDataThing2 = new WxMaSubscribeMessage.MsgData();
        msgDataThing2.setName("thing2");
        msgDataThing2.setValue("你好订阅消息");
        wxMaSubscribeData.add(msgDataThing2);

        // 第三个内容：领取方式
        WxMaSubscribeMessage.MsgData msgDataTime3 = new WxMaSubscribeMessage.MsgData();
        msgDataTime3.setName("time3");
        msgDataTime3.setValue(DateUtil.format(new Date(), "yyyy-MM-dd"));
        wxMaSubscribeData.add(msgDataTime3);

        // 第四个内容：专属老师
        // 第二个内容：用户昵称
        WxMaSubscribeMessage.MsgData msgDataThing7 = new WxMaSubscribeMessage.MsgData();
        msgDataThing7.setName("thing7");
        msgDataThing7.setValue("强撸灰飞烟灭");
        wxMaSubscribeData.add(msgDataThing7);

        //把集合给大的data
        wxMaSubscribeMessage.setData(wxMaSubscribeData);

        return wxMaSubscribeMessage;
    }
}
