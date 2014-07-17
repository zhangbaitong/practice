var later = require('later');
later.date.localTime();

console.log("Now:"+new Date());

//Later Modifiers - 行为修饰符

var demo1_a = {schedules: [{h_a: [17]}]};
//#等价定义
//var demo1 = {schedules: [{h: [17,18,19,20,21,22,23]}]};

var demo2_b = {schedules: [{h_b: [17]}]};
//#等价定义
//var demo2 = {schedules: [{h: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]}]};

var occurrences = later.schedule(demo1_a).next(3);
for(var i = 0; i < occurrences.length; i++) {
    console.log(occurrences[i]);
}

occurrences = later.schedule(demo2_b).next(3);
for(var i = 0; i < occurrences.length; i++) {
    console.log(occurrences[i]);
}