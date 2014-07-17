var p = require('procstreams');
var serviceName = 'tomcat6';
var interval = 1000 * 60 * 2;
setInterval(function () {
    p("ps aux").pipe('grep ' + serviceName).data(function (stdout, stderr) {
        if ( !! stdout && stdout.indexOf(serviceName) == 0) {
            console.log('exist,don\'t need restart');
        } else {
            console.log('restart,waiting...');
            p('sudo /etc/init.d/tomcat6 restart', function (stdout, stderror) {});
        }
    });
}, interval);