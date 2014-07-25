var io = require('socket.io-client');
socket = io('http://localhost:8001',{reconnection:true,reconnectionDelay:1000,reconnectionDelayMax:5000,timeout:20000});

console.log('info','connect on 8001 ...');

socket.on('connect',function(){
    console.log('connected ...');
    socket.on('msg',function(data){
        console.log(data);
    });
});




