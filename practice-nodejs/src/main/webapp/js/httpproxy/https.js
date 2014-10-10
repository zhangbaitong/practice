//HTTPS -> HTTP


//
// Create the HTTPS proxy server in front of a HTTP server
//
httpProxy.createServer({
    target: {
        host: 'localhost',
        port: 9009
    },
    ssl: {
        key: fs.readFileSync('valid-ssl-key.pem', 'utf8'),
        cert: fs.readFileSync('valid-ssl-cert.pem', 'utf8')
    }
}).listen(8009);


//HTTPS -> HTTPS

//
// Create the proxy server listening on port 443
//
httpProxy.createServer({
    ssl: {
        key: fs.readFileSync('valid-ssl-key.pem', 'utf8'),
        cert: fs.readFileSync('valid-ssl-cert.pem', 'utf8')
    },
    target: 'https://localhost:9010',
    secure: true // Depends on your needs, could be false.
}).listen(443);


//Proxying WebSockets

//
// Create a proxy server for websockets
//
httpProxy.createServer({
    target: 'ws://localhost:9014',
    ws: true
}).listen(8014);


//another method for websockets

/
// Setup our server to proxy standard HTTP requests
//
var proxy = new httpProxy.createProxyServer({
    target: {
        host: 'localhost',
        port: 9015
    }
});
var proxyServer = http.createServer(function (req, res) {
    proxy.web(req, res);
});

//
// Listen to the `upgrade` event and proxy the
// WebSocket requests as well.
//
proxyServer.on('upgrade', function (req, socket, head) {
    proxy.ws(req, socket, head);
});

proxyServer.listen(8015);



//This will stop the proxy from accepting new connections.

proxy.close();