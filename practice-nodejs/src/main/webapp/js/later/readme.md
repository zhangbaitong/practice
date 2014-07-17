

http://bunkat.github.io/later/index.html

https://github.com/bunkat/later

http://bunkat.github.io/later/getting-started.html

http://bunkat.github.io/schedule/

npm install later

node app.js


时间定义完整列表：

Second, s: 秒, 取值范围:[0-59]
minute, m：分, 取值范围:[0-59]
hour, h: 时, 取值范围:[0-23]
time, t: 秒每日, 取值范围:[0-86399]
day, D: 日, 取值范围:[1-31]
dayOfWeek, dw, d: 日每周, 取值范围:[1-7]
dayOfYear, dy: 日每年，取值范围:[1-365]
weekOfMonth, wm: 周每月，取值范围:[1-5]
weekOfYear, wy: 周每年，取值范围:[1-52]
month, M: 月，取值范围:[1-12]
year, Y: 年，取值范围:[1970-2099]

时间计算

name: 名称
range: 取值范围计数
val(date): 当前时间段的值
isValid(date, value): 检验输入是否是当前时间段的值
extent(date): 取值范围
start(date): 开始时间点
end(date): 结束时间点
next(date, value): value之后的时间点
prev(date, value): value之前的时间点

Later Parsers - 规则解释器

Parsers模块提供了3种规则解释器，方便定义时间表。

Recur: 链式API定义
Cron Parser: CRON格式定义
Text Parser：自然语言定义
1). Recur: 链式API定义

设置每小时第5分0秒启动


var sched = later.parse.recur().on(5).minute();
时间定义API


  second();
  minute();
  hour();
  time();
  dayOfWeek();
  dayOfWeekCount();
  dayOfMonth();
  dayOfYear();
  weekOfMonth();
  weekOfYear();
  month();
  year();
时间计算API

on(vals): 设置时间值
first(): 最小的时间值
last(): 最大的时间值
onWeekend(): 周末，等价于on(1,7).dayOfWeek()
onWeekday(): 工作日，等价于on(2,3,4,5,6).dayOfWeek()
every(val): 循环每个时间
after(val): 在之后
before(val): 在之前
startingOn(val): 每个时间段开始的偏移量
between(start, end): 在两个时间之间
and():
except():
customPeriod(id):
customModifier(id, vals):
2). Cron Parser: CRON格式定义

通过原CRON格式进行定义。

设置每小时第5分0秒启动


var cron = later.parse.cron('5 * * * *');
3). Text Parser：自然语言定义

通过关键字格式进行定义。


var text = later.parse.text('every 5th mins');


Later Occurrences - 时间控制

时区设置


//默认UTF时区
later.date.UTC();

//设置本地时区
later.date.localTime();
构造对象


var schedule = {schedules: [{m: [5]}]};
var occurrences = later.schedule(schedule);
时间控制API

later.schedule(schedule).next(count, start, end): 取下N个有效时间点
later.schedule(schedule).prev(count, start, end): 取上N个有效时间点
later.schedule(schedule).nextRange(count, start, end): 取下N个有效时间段
later.schedule(schedule).prevRange(count, start, end): 取上N个有效时间段

