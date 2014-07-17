#!/usr/bin/env node

var fs = require('fs'),
    path = require('path'),
    spawn = require('child_process').spawn;

var NGINX = '/usr/local/bin/nginx',
    LOG_DIR = '/usr/local/nginx/logs',
    BAK_DIR = '/data/backup/logs';

var format = format();

fs.readdir(LOG_DIR, function (err, logs) {
    if (err) {
        return console.log(err);
    }

    logs = logs
        .filter(function (log) {
            return path.extname(log) === '.log';
        })
        .map(function (log) {
            var map = {};
            map.original = LOG_DIR + '/' + log;
            map.target = BAK_DIR + '/' + path.basename(log, '.log') + format + path.extname(log);

            return map;
        });

    logs.forEach(function (map) {
        spawn('mv', ['-f', map.original, map.target]);
    });

    spawn(NGINX, ['-s', 'reload']);
});

function format(callback) {
    var d = new Date(),
        year = d.getFullYear(),
        month = d.getMonth() + 1,
        date = d.getDate();

    return [, year, month, date].join('-');
}
//定时执行可以用 nodejs 的 setInterval 或者系统的 crontab，我用的后者：
//0 3 * * * /this-script-path.js > /dev/null 2>&1 每天凌晨 3 点自动运行