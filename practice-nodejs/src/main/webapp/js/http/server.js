//服务器内容定义：提供服务器启动方法，提供HTTP请求响应函数，提供路由调用功能
//服务器地址：http://localhost:8888/
var http = require("http");
var url = require("url");

function start(route, handle) {
	  function onRequest(request, response) {
	    var pathname = url.parse(request.url).pathname;
	    console.log("Request for " + pathname + " received.");
	    route(handle, pathname, response, request);
	  }

	  http.createServer(onRequest).listen(8888);
	  console.log("Server has started.");
	}

exports.start = start;