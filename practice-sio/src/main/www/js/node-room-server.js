var server = require('socket.io')();
// or
//var Server = require('socket.io');
//var io = new Server();
server.listen(8001);
console.log('listen on 8001 ...');
//each namespace receives this event
server.on('connection',function(socket){
    console.log('connection by client ...');
    socket.join('myroom');
    console.log('join room ...');
    socket.to('myroom').emit('msg','wo le ge qu');
    //socket.emit('msg','wo le ge qu');
});