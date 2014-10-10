var http = require('http');

var proxy = http.createServer(function(request, response) {

    var options = {
        host: 'localhost', // 这里是代理服务器
        port: 80,             // 这里是代理服务器端口
        path: request.url,
        method: request.method,
        headers: {
            // 如果代理服务器需要认证
            //'Proxy-Authentication': 'Base ' + new Buffer('user:password').toString('base64')    // 替换为代理服务器用户名和密码
        }
    };

    var req = http.request(options, function(req, res) {
        req.pipe(response);
        console.log(req.url);
    }).end();

}).listen(8080);
