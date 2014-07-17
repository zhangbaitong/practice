//Later Executing - 启动运行

/**
 Executing模块定义了setTimeout和setInterval两种方式，实现运行。
 setTimeout: 设置一段时间后运行，只运行1次
 setInterval: 循环运行，直到clear

 js方法
 Timers
 setTimeout(callback, delay, [arg], [...])
 clearTimeout(timeoutId)
 setInterval(callback, delay, [arg], [...])
 clearInterval(intervalId)
 **/


var later = require('later');
later.date.localTime();

console.log("Now:"+new Date());

var sched = later.parse.recur().every(5).second(),
    t = later.setTimeout(function() {
        test(5);
    }, sched);

setTimeout(function(){
    console.log('==========');
},10000);

function test(val) {
    console.log(new Date());
    console.log(val);
}