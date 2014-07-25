var utils = require("./log-utils");
var url=require('url');
var path=require('path');
var fs=require('fs');

var pageHandler = function (request, response) {
    var pathname = url.parse(request.url).pathname;
    if (pathname.charAt(pathname.length - 1) == "/") {
        //access for root path,point to the detail page
        pathname += "index.html";
    }
    var realPath = path.join("assets", pathname);
    utils.infos('realPath',realPath);
    var ext = path.extname(realPath);
    ext = ext ? ext.slice(1) : 'unknown';
    fs.exists(realPath, function (exists) {
        if (!exists) {
            response.writeHead(404, {
                'Content-Type': 'text/plain'
            });
            response.write("This request URL " + pathname + " was not found on this server.");
            response.end();
        } else {
            fs.readFile(realPath, "binary", function (err, file) {
                if (err) {
                    response.writeHead(500, {
                        'Content-Type': 'text/plain'
                    });
                    response.end(err);
                } else {
                    response.writeHead(200, {
                        'Content-Type': "text/html"
                    });
                    response.write(file, "binary");
                    response.end();
                }
            });
        }
    });
}

exports.pageHandler = pageHandler;