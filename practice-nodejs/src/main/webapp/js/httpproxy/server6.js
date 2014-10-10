
//Routing proxy with access logging

var httpProxy = require('http-proxy');

var options = { router: {
    '/page1$' : 'localhost:3010',
    '/page2$' : 'localhost:3020',
    // default route
    '.*'      : 'localhost:3020'
}}


var router = new httpProxy.RoutingProxy(options);
var proxy = httpProxy.createServer(function(req,res) {
    console.log("request: " + req.path + "; method: " + req.method);
    router.proxyRequest(req,res);
});

proxy.listen(3000, function() { console.log("Routing proxy listening on " + proxy.address().port); });



//it's remove from v1.0

//the solution is https://blog.nodejitsu.com/node-http-proxy-1dot0/

//http://stackoverflow.com/questions/10930564/default-route-using-node-http-proxy