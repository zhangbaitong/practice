var http = require('http');
 	
/** 
 * 新建一个 HTTP 80 端口的服务器
 * 在每次请求中，调用 proxy.web(req, res config) 方法进行请求分发
 */
var server = require('http').createServer(function(req, res){   
	res.end("hello world");
});
 
server.listen(8099);
console.log("111")