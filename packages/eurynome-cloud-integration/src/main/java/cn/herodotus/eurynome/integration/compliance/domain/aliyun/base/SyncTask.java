package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

/**
 * <p>Description: SyncTask </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:50
 */
public class SyncTask extends AbstractClientTask{

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
