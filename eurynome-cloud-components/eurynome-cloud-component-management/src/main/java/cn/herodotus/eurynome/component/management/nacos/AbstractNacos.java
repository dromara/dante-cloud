package cn.herodotus.eurynome.component.management.nacos;

import cn.herodotus.eurynome.component.management.domain.Config;

/**
 * <p> Description : AbstractNacos </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 10:49
 */
public abstract class AbstractNacos {

    protected static final String SERVER_STATUS_UP = "UP";
    protected static final String DEFAULT_GROUP = Config.DEFAULT_GROUP;
    protected static final long DEFAULT_TIMEOUT = 3000;

    private String serverAddress;

    public AbstractNacos(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    protected String getServerAddress() {
        return serverAddress;
    }

    protected void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
}
