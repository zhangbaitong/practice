// note, io.listen(&lt;port&gt;) will create a http server for you
var io = require('socket.io')(8001);

io.on('connection', function (socket) {
    io.emit('this', { will: 'be received by everyone'});

    socket.on('private message', function (from, msg) {
        console.log('I received a private message by ', from, ' saying ', msg);
    });

    socket.on('disconnect', function () {
        console.log('disconnect is occur');
        io.sockets.emit('user disconnected');
    });
});