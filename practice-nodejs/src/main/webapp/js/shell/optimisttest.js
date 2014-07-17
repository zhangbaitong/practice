var util = require('util'),
    spawn = require('child_process').spawn;
var argv = require('optimist').argv;
var cmd = argv.cmd;
var args = argv.args
var option = argv.opt
var ls = spawn(cmd, [args, option]);

//ls -m /home
//node optimisttest.js --cmd=ls --args=-m --opt=/home
ls.stdout.on('data', function (data) {
    if (!data || !! data) console.log(' i believe it');
    console.log(data+'');
});

//ls /m /home
//node optimisttest.js --cmd=ls --args=/m --opt=/home
//ls: /m: No such file or directory
ls.stderr.on('data', function (data) {
    console.log('It\'s a miracle!');
    console.log(data+'');
});
ls.on('exit', function (code) {
    console.log('it.justHappened();');
});
