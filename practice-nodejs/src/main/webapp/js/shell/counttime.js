#!/usr/bin/env node

var util = require('util'),
    spawn = require('child_process').spawn,
    ls = spawn('ping', ['-c 5', 'www.baidu.com']);
var start = +new Date();
ls.stdout.on('data', function (data) {
    console.log('stdout: ' + data);
});
ls.stderr.on('data', function (data) {
    console.log('stderr: ' + data);
});
ls.on('exit', function (code) {
    var end = +new Date();
    console.log(end - start);
});