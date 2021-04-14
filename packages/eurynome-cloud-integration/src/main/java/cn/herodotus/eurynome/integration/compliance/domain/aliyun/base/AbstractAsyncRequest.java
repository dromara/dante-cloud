package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: AbstractAsyncRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:26
 */
public abstract class AbstractAsyncRequest<T extends AbstractTask> extends SyncRequest<T>{

    /**
     * 检测结果回调通知您的URL，支持使用HTTP和HTTPS协议的地址。该字段为空时，您必须定时轮询检测结果。
     * callback接口必须支持POST方法、UTF-8编码的传输数据，以及表单参数checksum和content。内容安全按照以下规则和格式设置checksum和content，调用您的callback接口返回检测结果。
     * checksum：字符串格式，由用户uid + seed + content拼成字符串，通过SHA256算法生成。用户UID即阿里云账号ID，可以在阿里云控制台查询。为防篡改，您可以在获取到推送结果时，按上述算法生成字符串，与checksum做一次校验。
     * 说明 用户UID必须是阿里云账号的UID，而不是RAM用户的UID。
     * content：JSON字符串格式，请自行解析反转成JSON对象。关于content结果的示例，请参见查询检测结果的返回示例。
     * 说明 您的服务端callback接口收到内容安全推送的结果后，如果返回的HTTP状态码为200，则表示接收成功，其他的HTTP状态码均视为接收失败。接收失败时，内容安全将最多重复推送16次检测结果，直到接收成功。重复推送16次后仍未接收成功，则不再推送，建议您检查callback接口的状态。
     */
    private String callback;
    /**
     * 随机字符串，该值用于回调通知请求中的签名。
     * 由英文字母、数字、下划线（_）组成，不超过64个字符。由您自定义，用于在接收到内容安全的回调通知时校验请求由阿里云内容安全服务发起。
     *
     * 说明 当使用callback时，该字段必须提供。
     */
    private String seed;
    /**
     * 使用回调通知时（callback），设置对回调通知内容进行加密的算法。内容安全会将返回结果（由用户uid + seed + content拼接的字符串）按照您设置的加密算法加密后，再发送到您的回调通知地址。取值：
     * SHA256（默认）：使用SHA256加密算法。
     * SM3：使用国密HMAC-SM3加密算法，返回十六进制的字符串，且字符串由小写字母和数字组成。
     * 例如，abc经国密SM3加密后返回66c7f0f462eeedd9d1f2d46bdc10e4e24167c4875cf2f7a2297da02b8f4ba8e0。
     */
    private String cryptType;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getCryptType() {
        return cryptType;
    }

    public void setCryptType(String cryptType) {
        this.cryptType = cryptType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("callback", callback)
                .add("seed", seed)
                .add("cryptType", cryptType)
                .toString();
    }
}
