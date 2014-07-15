var io = require('socket.io').listen(8001);

io.sockets.on('connection', function (socket) {
    socket.on('ferret', function (name, fn) {
        fn('woot');
    });
});