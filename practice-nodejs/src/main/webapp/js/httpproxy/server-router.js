var httpProxy = require('http-proxy'),
    http = require('http'),
    addresses;

// routing hash
addresses = {
    'localhost:8009': {
        host: 'localhost',
        port: 8081
    },
    'local.dev:8009': {
        host: 'localhost',
        port: 8082
    },
    'default': {
        host: 'xkcd.com',
        port: 80
    }
};

// create servers on localhost on ports specified by param
function createLocalServer(ports) {
    ports.forEach(function(port) {
        http.createServer(function (req, res) {
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end('<h1>Hello from ' + port + '</h1');
        }).listen(port);
    });
    console.log('Servers up on ports ' + ports.join(',') + '.');
}
createLocalServer([8081, 8082]);

console.log('======================================\nRouting table:\n---');
Object.keys(addresses).forEach(function(from) {
    console.log(from + ' ==> ' + addresses[from].host + ':' + addresses[from].port);
});

httpProxy.createServer(function (req, res, proxy) {
    var target;

    // if the host is defined in the routing hash proxy to it
    // else proxy to default host
    console.log("===================");
    console.log(req.headers.host);
    console.log(addresses[req.headers.host]);

    target = (addresses[req.headers.host]) ? addresses[req.headers.host] : addresses.default;

    proxy.proxyRequest(req, res, target);
}).listen(8009);