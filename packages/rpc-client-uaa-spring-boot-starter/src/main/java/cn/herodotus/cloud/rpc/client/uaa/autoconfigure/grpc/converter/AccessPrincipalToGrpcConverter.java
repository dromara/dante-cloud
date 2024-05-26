/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.converter;

import cn.herodotus.stirrup.core.definition.constants.SymbolConstants;
import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.grpc.user.GrpcAccessPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: AccessPrincipal 转 GrpcAccessPrincipal 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/19 12:03
 */
public class AccessPrincipalToGrpcConverter implements Converter<AccessPrincipal, GrpcAccessPrincipal> {
    @Override
    public GrpcAccessPrincipal convert(AccessPrincipal source) {

        return GrpcAccessPrincipal.newBuilder()
                .setCode(valid(source.getCode()))
                .setMobile(valid(source.getMobile()))
                .setAppId(valid(source.getAppId()))
                .setEncryptedData(valid(source.getEncryptedData()))
                .setIv(valid(source.getIv()))
                .setOpenId(valid(source.getOpenId()))
                .setSessionKey(valid(source.getSessionKey()))
                .setUnionId(valid(source.getUnionId()))
                .setRawData(valid(source.getRawData()))
                .setSignature(valid(source.getSignature()))
                .setAuthCode(valid(source.getAuthCode()))
                .setState(valid(source.getState()))
                .setAuthorizationCode(valid(source.getAuthorizationCode()))
                .setAuthToken(valid(source.getAuthToken()))
                .setAuthVerifier(valid(source.getAuthVerifier()))
                .build();
    }

    private String valid(String data) {
        return StringUtils.isNotBlank(data) ? data : SymbolConstants.BLANK;
    }
}
