var io = require('socket.io').listen(8001);

io.sockets.on('connection', function (socket) {
    console.log('connection...');
    socket.broadcast.emit('user connected');
    //io.sockets.emit('user connected','broadcast...');
    //io.emit('user connected','broadcast...');
});