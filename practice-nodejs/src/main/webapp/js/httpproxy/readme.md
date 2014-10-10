
https://github.com/nodejitsu/node-http-proxy

Options

httpProxy.createProxyServer supports the following options:

target: url string to be parsed with the url module
forward: url string to be parsed with the url module
agent: object to be passed to http(s).request (see Node's https agent and http agent objects)
secure: true/false, if you want to verify the SSL Certs
xfwd: true/false, adds x-forward headers
toProxy: passes the absolute URL as the path (useful for proxying to proxies)
If you are using the proxyServer.listen method, the following options are also applicable:

ssl: object to be passed to https.createServer()
ws: true/false, if you want to proxy websockets