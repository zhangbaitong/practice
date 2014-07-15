var io = require('socket.io').listen(8001);

io.sockets.on('connection', function (socket) {
    socket.on('message', function (data) {
        console.log('yes hi',data);
    });
    socket.on('disconnect', function () { });
});