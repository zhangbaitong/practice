var later = require('later');
//1). Basic schedules：基本时间表
//设置每日10:15am , 10:45am启动
var basic = {h: [10], m: [15,45]};
//2). Composite schedules: 组合时间表
//设置每日10:15am , 10:45am, 和17:40pm 启动
var composite = [
    basic,
    {h: [17], m: [30]}
];
//3). Exception schedules: 异常时间表
//用于设置一下无效的日期：设置 每年1月 和 每周一，六，日 时间表无效
var exception = [
    {M: [1]},
    {dw: [1,6,7]}
];

var sched = {
    schedules:composite,
    exceptions:exception
};

later.date.localTime();

console.log("Now:"+new Date());
var occurrences = later.schedule(sched).next(10);
for(var i = 0; i < occurrences.length; i++) {
    console.log(occurrences[i]);
}