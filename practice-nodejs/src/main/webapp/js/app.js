var express = require('express');  
var app = express();  
   
console.log(__dirname);

app.use(express.static(__dirname + '/expresstest/static')); 

app.get('/', function(req, res){
	//res.send('hello express');
	res.sendfile('./expresstest/index.html'); 
});  
   
app.listen(3000);