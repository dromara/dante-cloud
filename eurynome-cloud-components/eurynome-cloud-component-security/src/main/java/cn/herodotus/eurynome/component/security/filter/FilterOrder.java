package cn.herodotus.eurynome.component.security.filter;

/**
 * <p> Description : 统一管理filter顺序 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/6 12:10
 */
public class FilterOrder {

    private static final int INITIAL_ORDER = -105;
    private static final int ORDER_STEP = 1;

    public static final int TENANT_IDENTIFICATION_FILTER = INITIAL_ORDER + ORDER_STEP;
}
