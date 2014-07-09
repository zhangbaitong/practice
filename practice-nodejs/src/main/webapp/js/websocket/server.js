var http = require("http")
    ,fs = require("fs")
    ,path = require("path")
    ,ws = require('websocket-server');

var spawn = require('child_process').spawn;
var iostat = spawn("iostat",["-c","2"]); //我们的命令 iostat -c  2

console.log("start");
//一个Http Server 将Html页面发送出去。
var httpServer = http.createServer(function(req, res){
  if(req.method == "GET"){
    if( req.url.indexOf("favicon") > -1 ){
      res.writeHead(200, {'Content-Type': 'image/x-icon', 'Connection': 'close'});
      res.end("");
    } else {
      res.writeHead(200, {'Content-Type': 'text/html', 'Connection': 'close'});
      fs.createReadStream( path.normalize(path.join(__dirname, "client.html")), {
        'flags': 'r',
        'encoding': 'binary',
        'mode': 0666,
        'bufferSize': 4 * 1024
      }).addListener("data", function(chunk){
        res.write(chunk, 'binary');
      }).addListener("end",function() {
        res.end();
      });
    }
  } else {
    res.writeHead(404);
    res.end();
  }
});
//Web Socket server
var server = ws.createServer({
        debug: true,
        server: httpServer});

// Handle WebSocket Requests
server.addListener("connection", function(conn){
  iostat.stdout.on('data',function(data) {
        console.log(data);
        if(conn._state==4)
        conn.send(data); //发送后台数据
  });
});

server.addListener("error", function(){
 console.log(Array.prototype.join.call(arguments, ", "));
});

server.addListener("disconnected", function(conn){
  console.log("disconnected");
});

server.listen(8888);