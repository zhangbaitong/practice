var io = require('socket.io').listen(8001);

io.sockets.on('connection', function (socket) {
    var tweets = setInterval(function () {
        getBieberTweet(function (tweet) {
            socket.volatile.emit('bieber tweet', tweet);
            //socket.emit('bieber tweet', tweet);
        });
    }, 100);

    socket.on('disconnect', function () {
        console.log('disconnect');
        clearInterval(tweets);
    });
});

function getBieberTweet(cb){
    cb('haha')
}