$.ajaxSetup({
    complete: function (xhr, status) {

        switch (xhr.status) {
            case 204:
                console.log('Flowable operation was successful');
                break;
            case 400:
                let title_400 = '请求参数错误，请检查前后台参数！';
                $.confirm.error(title_400);
                break;
            case 404:
                let title_404 = '请求地址不存在，请检查具体代码！';
                $.confirm.error(title_404);
                break;
            case 409:
                let title_409 = '流程已被挂起，如需使用请联系管理员激活！';
                $.confirm.autoCloseWarning('注意', title_409);
                break;
            case 500:
                let code = xhr.status;
                let message = '';
                if (xhr.responseJSON && xhr.responseJSON.code) {
                    code = xhr.responseJSON.code;
                    message = xhr.responseJSON.message;
                }
                let title_500 = "系统出现" + message + "问题，代码【" + code + "】，请稍后再试或者联系管理员！"
                $.confirm.errorWithLogout(title_500);
                break;
            case 200:
                break;
            default:
                console.log('-----please check the status ------');
                console.log(xhr);
                console.log(status);
        }
    }
});

$.confirm = {
    autoCloseSuccess: function (title, text) {
        let that = this;
        that.autoClose(title, text, 'success');
    },
    autoCloseWarning: function (title, text) {
        let that = this;
        that.autoClose(title, text, 'warning');
    },
    autoClose: function (title, text, type) {
        swal({
            title: title,
            text: text,
            type: type,
            timer: 2000,
            showConfirmButton: false
        });
    },
    error: function (text) {
        let that = this;
        that.errorWithConfirmAction(text);
    },
    warning: function (text) {
        let that = this;
        that.withConfirmButton('注意！', text, 'warning', '好的')
    },
    errorWithLogout: function (text) {
        let that = this;
        let confirmAction = function () {
            location.href = '/logout';
        };
        that.errorWithConfirmAction(text, confirmAction);
    },
    errorWithConfirmAction: function (text, confirmAction) {
        let that = this;
        that.withConfirmButton('错误！', text, 'error', '好的', confirmAction)
    },
    defaultDeleteWarning: function (confirmAction) {
        let that = this;

        let cancelAction = function () {
            that.autoCloseSuccess("您已取消!", "这条信息又安全了 :)，此窗口将会在 2 秒钟后自动关闭。");
        };

        that.withConfirmAndCancelButton("你确定要删除这条信息么?", "此条信息删除之后将无法恢复，请慎重操作!", "warning", "是的, 删除!", "再想想, 取消!", confirmAction, cancelAction);
    },
    withConfirmAndCancelButton(title, text, type, confirmButtonText, cancelButtonText, confirmAction, cancelAction) {
        swal({
            title: title,
            text: text,
            type: type,
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: confirmButtonText,
            cancelButtonText: cancelButtonText,
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            if (isConfirm) {
                if (confirmAction) {
                    confirmAction();
                }
            } else {
                if (cancelAction) {
                    cancelAction();
                }
            }
        });
    },
    withConfirmButton: function (title, text, type, confirmButtonText, confirmAction) {
        swal({
            title: title,
            text: text,
            type: type,
            showCancelButton: false,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: confirmButtonText,
            closeOnConfirm: true
        }, function (isConfirm) {
            if (isConfirm) {
                if (confirmAction) {
                    confirmAction();
                }
            }
        });
    }

};

$.http = {

    request: function (url, body, method, type, dataType) {
        let data = body;
        let contentType = "application/x-www-form-urlencoded;charset=UTF-8";
        if (type && type.toLowerCase() === 'json') {
            contentType = 'application/json;charset=utf-8';
            if (method !== 'GET') {
                data = JSON.stringify(body);
            }
        }

        return new Promise((resolve, reject) => {
            $.ajax({
                url: url,
                data: data,
                type: method,
                contentType: contentType,
                dataType: dataType || "json",
                async: true,
                success: function (result) {
                    resolve(result);
                },
                error: function (error) {
                    reject(error);
                }
            });
        });
    },
    get: function (url, params = {}) {
        let that = this;
        return that.request(url, params, 'GET');
    },
    put: function (url, data, type) {
        let that = this;
        return that.request(url, data, 'PUT', type);
    },
    post: function (url, data = {}, type) {
        return this.request(url, data, 'POST', type);
    },
    delete: function (url, data = {}, type) {
        let that = this;
        return that.request(url, data, 'DELETE', type);
    },
    postWithNoResposeBody: function(url, data = {}) {
        let that = this;
        return that.request(url, data, 'POST', 'json', 'text');
    }
};

//所以页面的消息通知方法

$.information = {
    configuration: function (colorName, text, placementFrom, placementAlign, animateEnter, animateExit) {
        if (colorName === null || colorName === '') {
            colorName = 'bg-black';
        }
        if (text === null || text === '') {
            text = 'Turning standard Bootstrap alerts';
        }
        if (animateEnter === null || animateEnter === '') {
            animateEnter = 'animated fadeInDown';
        }
        if (animateExit === null || animateExit === '') {
            animateExit = 'animated fadeOutUp';
        }
        let allowDismiss = true;

        $.notify(
            {
                message: text
            },
            {
                type: colorName,
                allow_dismiss: allowDismiss,
                newest_on_top: true,
                timer: 1000,
                placement: {
                    from: placementFrom,
                    align: placementAlign
                },
                animate: {
                    enter: animateEnter,
                    exit: animateExit
                },
                template: '<div data-notify="container" class="bootstrap-notify-container alert alert-dismissible {0} ' + (allowDismiss ? "p-r-35" : "") + '" role="alert">' +
                    '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                    '<span data-notify="icon"></span> ' +
                    '<span data-notify="title">{1}</span> ' +
                    '<span data-notify="message">{2}</span>' +
                    '<div class="progress" data-notify="progressbar">' +
                    '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                    '</div>' +
                    '<a href="{3}" target="{4}" data-notify="url"></a>' +
                    '</div>'
            });
    },
    success: function (message) {
        this.configuration('alert-success', message, 'top', 'center', 'animated fadeInDown', 'animated fadeOutUp')
    },
    warning: function (message) {
        this.configuration('alert-warning', message, 'top', 'center', 'animated fadeInDown', 'animated fadeOutUp')
    },
    error: function (message) {
        this.configuration('alert-danger', message, 'top', 'center', 'animated fadeInDown', 'animated fadeOutUp')
    },
    info: function (message) {
        this.configuration('alert-info', message, 'top', 'center', 'animated fadeInDown', 'animated fadeOutUp')
    },
    notify: function (type, message) {
        switch (type) {
            case 'success':
                this.success(message);
                break;
            case 'warning':
                this.warning(message);
                break;
            case 'error':
                this.error(message);
                break;
            case 'info':
                this.info(message);
                break;
            default:
                this.info(message);
        }
    }
};
