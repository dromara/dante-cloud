package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import cn.herodotus.eurynome.integration.content.domain.aliyun.common.ClientInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: AbstractClientTask </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:47
 */
public class AbstractClientTask extends AbstractTask {

    @ApiModelProperty(name = "客户端信息", notes = "请参见公共参数中的公共查询参数。服务器会把全局的clientInfo和此处独立的clientInfo合并。")
    private ClientInfo clientInfo;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
