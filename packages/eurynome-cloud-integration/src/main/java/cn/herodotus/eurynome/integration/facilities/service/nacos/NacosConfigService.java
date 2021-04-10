package cn.herodotus.eurynome.integration.facilities.service.nacos;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>File: NacosConfigService </p>
 *
 * <p>Description: Nacos Config Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/5 16:57
 */
@Service
public class NacosConfigService extends AbstractNacosService{

    public void getConfig(String dataId, String group) {
        HttpResult result = this.http().sync("/cs/configs")
                .bodyType(OkHttps.JSON)
                .addUrlPara("dataId", dataId)
                .addUrlPara("group", group)
                .get();
        System.out.println( result.getBody());
    }

    public void add(String dataId, String group, String content) {
        HttpResult result = this.http().sync("/cs/configs")
                .bodyType(OkHttps.JSON)
                .addUrlPara("dataId", dataId)
                .addUrlPara("group", group)
                .addUrlPara("content", content)
                .addUrlPara("type", "yaml")
                .post();
        System.out.println( result.getBody());
    }
}
