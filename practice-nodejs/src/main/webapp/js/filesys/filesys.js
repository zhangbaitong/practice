var fs = require('fs');

////fs.watchFile('message.text', function (curr, prev) {
//fs.watch('message.text', function (stat) {
//    //console.log('the current mtime is: ' + curr.mtime);
//    //console.log('the previous mtime was: ' + prev.mtime);
//    console.log('the previous mtime was: ' + stat);
//});

var fs = require('fs');

//message.text

//fs.stat('message.text2', function (err, stats) {
//    if (err) throw err;
//    console.log('stats: ' + JSON.stringify(stats));
//});



//console.log(process.cwd());

//fs.rename('message.text', 'message.text2', function (err) {
//    if (err) throw err;
//    fs.stat('message.text2', function (err, stats) {
//        if (err) throw err;
//        console.log('stats: ' + JSON.stringify(stats));
//    });
//});

//fs.readFile('message.text', {encoding:'utf-8',flag:'r'},function (err, data) {
//    if (err) throw err;
//    console.log(data);
//});

fs.watchFile('message.text', function (curr, prev) {
    if(curr.mtime == prev.mtime){
        console.log("no change ...");
    }else{
        console.log("yes change ...");
        console.log(mylogfile.read());
        console.log("yes change end ...");
    }
});

//var mylogfile = fs.createReadStream('message.text', {start: 0, end: 99,autoClose: false});

var mylogfile = fs.createReadStream('message.text', {autoClose: false});

mylogfile.resume();

mylogfile.on('open', function(fd) {
    console.log('open ...',fd);
});

mylogfile.on('readable', function() {
    // there is some data to read now
    console.log('readable ...');
    var chunk;
    while (null !== (chunk = mylogfile.read())) {
        console.log('got %d bytes of data', chunk.length);
    }
    console.log('readable end...');
})

mylogfile.on('data', function(chunk) {
    console.log('got %d bytes of data', chunk.length);
})

mylogfile.on('end', function() {
    console.log('there will be no more data.');
});

mylogfile.on('close', function() {
    console.log('close ...');
});

mylogfile.on('error', function() {
    console.log('error ...');
});

setTimeout(function(){
    console.log('-------');
},1000*100000);