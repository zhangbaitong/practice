var fs = require('fs');

setInterval(function(){
    fs.appendFile('message.text', 'data to append'+Date.now(), function (err) {
        if (err) throw err;
        console.log('append file :','data to append');
    });
    fs.appendFile('message2.text', 'data to append2'+Date.now(), function (err) {
        if (err) throw err;
        console.log('append file :','data to append2');
    });
},1000);
