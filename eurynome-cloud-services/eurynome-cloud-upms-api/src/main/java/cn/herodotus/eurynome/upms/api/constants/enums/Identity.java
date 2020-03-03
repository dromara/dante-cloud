/*
 * Copyright (c) 2019. All Rights Reserved
 * ProjectName: hades-multi-module
 * FileName: Identity
 * Author: gengwei.zheng
 * Date: 19-2-15 下午2:12
 * LastModified: 19-2-15 下午2:12
 */

package cn.herodotus.eurynome.upms.api.constants.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: </p>
 *
 * @author gengwei.zheng
 * @date 2019/2/15
 */
public enum Identity {
    /**
     * enum
     */
    LEADERSHIP(0, "领导"),
    SECTION_LEADER(1, "部所负责人"),
    STAFF(2, "员工");

    private int index;
    private String name;

    private static Map<Integer, Identity> map = new HashMap<>();

    static {
        for (Identity identity : Identity.values()) {
            map.put(identity.index, identity);
        }
    }

    Identity(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static Identity getIdentity(int index) {
        return map.get(index);
    }
}
