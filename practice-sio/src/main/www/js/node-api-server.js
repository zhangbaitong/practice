var server = require('socket.io')();
// or
//var Server = require('socket.io');
//var io = new Server();
server.listen(8001);
console.log('listen on 8001 ...');
//each namespace receives this event
server.on('connection',function(socket){
    console.log('connection by client ...');
    //two method send msg to default namespace '/'
    server.sockets.emit('ok','first');
    server.emit('ok','second');

    socket.on('disconnect', function(){ });

    //name space
    //console.log('connected number :',server.sockets.connected[socket.id]);
    console.log('connected number :',getJSONLength(server.sockets.connected));

    //socket
    socket.rooms;
    socket.client;//client
    socket.conn;//socket
    socket.request;//request:Cookie,User-Agent
    console.log('user-agent',socket.request.headers['user-agent']);
    socket.id;

    //room
    socket.join('myroom');
    socket.join('myroom2',function(err){
        console.log('join room',err);
    });

    socket.leave('myroom');
    socket.leave('myroom2',function(err){
        console.log('leave room',err);
    });

    socket.to('myroom').emit('send msg to special room',{some:'data'});
    socket.in('myroom').emit('send msg to special room',{some:'data'});

});

//namespace

console.log("namespace:",server.of('/').name);
server.of('/').on('connection',function(socket){
    console.log('got socket by default namespace');
});


server.use(function(socket, next){
    console.log('use:',socket.id,'test');
    return next();
    //return next(new Error('haha'));//will send 'error'packets to clients
    //if (socket.request.headers.cookie) return next();
    //next(new Error('Authentication error'));
});

server.use(function(socket, next){
    console.log('use2:',socket.id,'test2');
    return next();
    //if (socket.request.headers.cookie) return next();
    //next(new Error('Authentication error'));
});

server.of('/chat').use(function(socket,next){
    console.log('use by chat:',socket.id,'test');
});

function getJSONLength(json){
    var length = 0;
    for(var item in json){
        length++;
    }
    return length;
};

//namespace test
var nsp = server.of('/my-chat');
nsp.on('connection', function(socket){
    console.log('someone connected');
    nsp.emit('hi', 'everyone!');
    nsp.on('hi2',function(id,data){
        console.log('hi2',id,data);
    });
});
