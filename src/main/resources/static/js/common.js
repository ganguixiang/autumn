function ajax(url, data, type, options) {
    var traditional = ('undefined' != typeof options.traditional)?options.traditional:false;
    var async = ('undefined' != typeof options.async)?options.async:true;
    return new Promise(function(resolve, reject) {
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