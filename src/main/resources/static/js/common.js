function ajax(url, data, type, options) {
    var options = options || {};
    var traditional = ('undefined' != typeof options.traditional) ? options.traditional : false;
    var async = ('undefined' != typeof options.async) ? options.async : true;
    var data = data || '';
    if (data != '') {
        data = JSON.stringify(data);
    }
    console.log(data);
    return new Promise(function(resolve, reject) {
        var reject = defaultRejectHandler;
        $.ajax({
            url: url,
            data: data,
            type: type || 'GET',
            contentType: 'application/json;charset=utf-8',
            // 使用传统方式来序列化数据,默认值:false
            // 例如params : {"type":"advanced","cargoType":["dangerous","living","express"]} 此时就需要用传统方式来序列化数据
            traditional:traditional,
            // 异步,默认值:true
            async:async,
            success: function(data) {
                if (data.code === 0) {
                    resolve(data);
                } else {
                    reject(data);
                }
            },
            error: function(e) {
                reject(e);
            }
        });
    });
}

/**
 * 默认错误处理函数
 * @param e
 */
function defaultRejectHandler(e) {
    error(e.message);
}

function log(message) {
    console.log(message);
}

/**
 * 消息提示
 * @param message
 */
function message(message) {
    index.$message(message);
}

/**
 * 成功提示
 * @param message
 */
function success(message) {
    index.$message({
        type: 'success',
        message: message
    });
}

/**
 * 错误提示
 * @param message
 */
function error(message) {
    index.$message.error(message);
}

/**
 * 警告提示
 * @param message
 */
function warning(message) {
    index.$message({
        type: 'warning',
        message: message
    })
}