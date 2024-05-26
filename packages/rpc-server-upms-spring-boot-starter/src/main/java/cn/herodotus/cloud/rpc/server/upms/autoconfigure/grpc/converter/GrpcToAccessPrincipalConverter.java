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

package cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.converter;

import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.grpc.user.GrpcAccessPrincipal;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Grpc AccessPrincipal 转 AccessPrincipal 转换器  </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/18 15:46
 */
public class GrpcToAccessPrincipalConverter implements Converter<GrpcAccessPrincipal, AccessPrincipal> {
    @Override
    public AccessPrincipal convert(GrpcAccessPrincipal source) {
        AccessPrincipal destination = new AccessPrincipal();
        destination.setCode(source.getCode());
        destination.setAppId(source.getAppId());
        destination.setEncryptedData(source.getEncryptedData());
        destination.setIv(source.getIv());
        destination.setOpenId(source.getOpenId());
        destination.setSessionKey(source.getSessionKey());
        destination.setUnionId(source.getUnionId());
        destination.setRawData(source.getRawData());
        destination.setSignature(source.getSignature());
        destination.setAuthCode(source.getAuthCode());
        destination.setState(source.getState());
        destination.setAuthorizationCode(source.getAuthorizationCode());
        destination.setAuthToken(source.getAuthToken());
        destination.setAuthVerifier(source.getAuthVerifier());
        destination.setMobile(source.getMobile());
        return destination;
    }
}
