//HTTP服务器
var server = require("./http/server");
//路由功能
var router = require("./http/router");
//请求控制器
var requestHandlers = require("./http/requestHandlers");

//URL映射定义
var handle = {}
handle["/"] = requestHandlers.start;
handle["/start"] = requestHandlers.start;
handle["/upload"] = requestHandlers.upload;
handle["/show"] = requestHandlers.show;
handle["/ls"] = requestHandlers.ls;
handle["/post"] = requestHandlers.post;
handle["/postshow"] = requestHandlers.postshow;
//启动服务器
server.start(router.route,handle);