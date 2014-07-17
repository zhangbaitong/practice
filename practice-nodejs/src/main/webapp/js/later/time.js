var later = require('later');
later.date.localTime();

var d = new Date();

//当前时间：2013-12-26 12:30:42
console.log(d);
//以小时定义时间
console.log(later.hour.name);
//每小时3600个计数点
console.log(later.hour.range);
//当前时间段的的值是12
console.log(later.hour.val(d));
//检查2不是当前时间段的值
console.log(later.hour.isValid(d, 2));
//检查12不是当前时间段的值
console.log(later.hour.isValid(d, 12));
//取值范围[0，23]
console.log(later.hour.extent());
//当前时间段的开始时间点：12:00:00
console.log(later.hour.start(d));
//当前时间段的结束时间点：12:59:59
console.log(later.hour.end(d));
//下一个周期第5个时间段开始点：2013-12-27 05:00:00
console.log(later.hour.next(d, 5));
//上一个周期第21个时间段结束点：2013-12-25 21:59:59
console.log(later.hour.prev(d, 21));