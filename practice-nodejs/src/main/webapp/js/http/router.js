//路由功能定义：动态根据传递的路由控制定义信息handle进行动态函数调用
function route(handle, pathname, response, request) {
	  console.log("About to route a request for " + pathname);
	  if (typeof handle[pathname] === 'function') {
	    handle[pathname](response, request);
	  } else {
	    console.log("No request handler found for " + pathname);
	    response.writeHead(404, {"Content-Type": "text/html"});
	    response.write("404 Not found");
	    response.end();
	  }
	}


exports.route = route;