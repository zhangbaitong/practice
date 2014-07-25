var fs = require('fs'),
    net = require('net'),
    events = require('events'),
    //create http server
    app = require('http').createServer(handler),
    io = require('socket.io')(app);

app.listen(8765);

console.log('process.browser',process.browser);

function handler (req, res) {
    fs.readFile(__dirname + '/index.html',
        function (err, data) {
            if (err) {
                res.writeHead(500);
                return res.end('Error loading index.html');
            }

            res.writeHead(200);
            res.end(data);
        });
}


io.on('connection', function (socket) {
    logsocket = socket;
    socket.emit('log', 'welcome');
    socket.on('clients', function (data) {
        console.log(data);
    });
});




var path = 'message.text';

var watchFile = function (){
    var result = fs.existsSync(path);
    if(!result){
        console.log('file not exist!',path)
        setTimeout(watchFile,1000);
        return;
    }else{
        console.log('watch file ...',path);
        var currSize = fs.statSync(path).size;
        console.log('currSize is ',currSize);
//        var watcher = fs.watch(path,function(event,filename){
//            console.log('watching ...','event:',event);
//            if(event == 'rename'){
//                watcher.close();
//                  watchFile();
//            };
//            if(event == 'change'){
//                fs.stat(path,function(err,stat){
//                    console.log('new currSize is ',stat.size);
//                    readNewLogs(path,stat.size,currSize);
//                    currSize = stat.size;
//                });
//            }
//        });
        fs.watchFile(path, { persistent: true, interval: 1000 },function (curr, prev) {
            if(curr.mtime == prev.mtime){
                console.log("no change ...");
            }else{
                console.log("yes change ...");
                fs.stat(path,function(err,stat){
                    console.log('new currSize is ',stat.size);
                    readNewLogs(path,stat.size,currSize);
                    currSize = stat.size;
                });
            }
        });
    };
};

var readNewLogs = function(path,curr,prev){
    console.log('readNewLogs ...',path,curr,prev);
    if(curr < prev)return;
    var rstream = fs.createReadStream(path,{encoding:'utf8',start: prev,end: curr});
    rstream.on('data',function(data){
        console.log('change content:',data);
        var lines = data.split('\n');
        for(var i=0;i<lines.length;i++){
            console.log('line:',lines[i]);
            logsocket.emit('log',lines[i]);
        }
    });
};

watchFile();
