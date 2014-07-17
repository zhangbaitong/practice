var later = require('later');
var sched = later.parse.text('every 5 mins'),
    occurrences = later.schedule(sched).next(10);

for(var i=0;i<10;i++){
    console.log(occurrences[i]);
}