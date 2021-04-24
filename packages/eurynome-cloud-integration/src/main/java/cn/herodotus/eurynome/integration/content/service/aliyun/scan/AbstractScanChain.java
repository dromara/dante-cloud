package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.ScanRequest;

/**
 * <p>Description:  内容审核责任链抽象基础类</p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 9:46
 */
public abstract class AbstractScanChain {

    public abstract boolean execute(ScanRequest scanRequest);
}
