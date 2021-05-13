/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-oauth
 * File Name: system-security.js
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

$.SECURITY = {
    informations: function () {
        let href = location.href;
        if (href.indexOf("kickout") > 0) {
            $.information.warning('您的账号在另一台设备上登录,如非本人操作，请立即修改密码！');
        }
        if (href.indexOf("invalid") > 0) {
            $.information.warning('登录超时，请重新登录');
        }
    },
    encrypt: function (content, key) {
        if (content) {
            let byteContent = CryptoJS.enc.Utf8.parse(content);
            let byteKey = CryptoJS.enc.Utf8.parse(key);
            let encryptContent = CryptoJS.AES.encrypt(byteContent, byteKey, {
                mode: CryptoJS.mode.ECB,
                padding: CryptoJS.pad.Pkcs7
            });
            return encryptContent.ciphertext.toString();
        }

        return content;
    },

    decrypt: function (content, key) {
        let hexContent = CryptoJS.enc.Hex.parse(content);
        let base64Content = CryptoJS.enc.Base64.stringify(hexContent);

        let byteKey = CryptoJS.enc.Utf8.parse(key);

        let decryptContent = CryptoJS.AES.decrypt(base64Content, byteKey, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });

        return decryptContent.toString(CryptoJS.enc.Utf8);
    },

    decryptSymmetric: function(symmetric) {
        let that = this;
        let keys = symmetric.split('/');
        let ninjutsu = keys[0];
        let ninja = keys[1];

        return that.decrypt(ninja, ninjutsu);
    },

    encryptFormData: function (tank, fighter, missile, symmetric) {
        let that = this;
        let darts = that.decryptSymmetric(symmetric);

        return {
            encryptTank: this.encrypt(tank, darts),
            encryptFighter: this.encrypt(fighter, darts),
            encryptMissile: this.encrypt(missile, darts)
        };
    }
};
