var io = require('socket.io-client');
//namespace http://localhost:8001/chat
socket = io('http://localhost:8001',{reconnection:true,reconnectionDelay:1000,reconnectionDelayMax:5000,timeout:20000});

console.log('info','connect on 8001 ...');

//manager events
socket.on('connect',function(){
    console.log('manager connected ...');

    var socketNSP = io('http://localhost:8001/my-chat');
    socketNSP.on('connect',function(socket){
        console.log('my chat namespace connection ...');
        socketNSP.on('hi',function(data){
            console.log('socketNSP my chat:',data);
            socketNSP.emit('hi2','hi2 test');
        });
    });

});

socket.on('connect_error',function(Object){
    console.log('manager connect_error ...' + Object);
});

socket.on('connect_timeout',function(){
    console.log('manager connect_timeout ...');
});

socket.on('reconnect',function(Number){
    console.log('manager reconnect ...',Number);
});


socket.on('reconnect_attempt',function(){
    console.log('has reconnect_attempt ...');
});


socket.on('reconnecting',function(){
    console.log('manager reconnecting ...');
});


socket.on('reconnect_error',function(Object){
    console.log('manager reconnect_error ...',Object);
});


socket.on('reconnect_failed',function(){
    console.log('manager reconnect_failed ...');
});


//socket events

socket.on('connect',function(){
    console.log('socket connected ...');
});

socket.on('error',function(Object){
    console.log('socket error ...',Object);
});

socket.on('disconnect',function(){
    console.log('socket disconnect ...');
});

socket.on('reconnect',function(Number){
    console.log('socket reconnect ...',Number);
});

socket.on('reconnect_attempt',function(){
    console.log('socket reconnect_attempt ...');
});

socket.on('reconnecting',function(Number){
    console.log('socket reconnecting ...',Number);
});

socket.on('reconnect_error',function(Object){
    console.log('socket reconnect_error ...',Object);
});

socket.on('reconnect_failed',function(Object){
    console.log('socket reconnect_failed ...',Object);
});

socket.on('ok',function(data){
   console.log(data);
});

