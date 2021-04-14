package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.ClientInfo;

/**
 * <p>Description: AbstractClientTask </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:47
 */
public class AbstractClientTask extends AbstractTask{

    private ClientInfo clientInfo;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
