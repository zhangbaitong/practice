var exec = require("child_process").exec;
var querystring = require("querystring"), fs = require("fs"), formidable = require("formidable");

function ls(response) {
	console.log("Request handler 'start' was called.");

	exec("ls -lah", function(error, stdout, stderr) {
		response.writeHead(200, {
			"Content-Type" : "text/plain"
		});
		response.write(stdout);
		response.end();
	});
}
function start(response) {
	console.log("Request handler 'start' was called.");

	var body = '<html>' + '<head>'
			+ '<meta http-equiv="Content-Type" content="text/html; '
			+ 'charset=UTF-8" />' + '</head>' + '<body>'
			+ '<form action="/upload" enctype="multipart/form-data" '
			+ 'method="post">'
			+ '<input type="file" name="upload" multiple="multiple">'
			+ '<input type="submit" value="Upload file" />' + '</form>'
			+ '</body>' + '</html>';

	response.writeHead(200, {
		"Content-Type" : "text/html"
	});
	response.write(body);
	response.end();
}

function upload(response, request) {
	console.log("Request handler 'upload' was called.");

	var form = new formidable.IncomingForm();
	console.log("about to parse");
	form.parse(request, function(error, fields, files) {
		console.log("parsing done");
		fs.renameSync(files.upload.path, "/tmp/test.png");
		response.writeHead(200, {
			"Content-Type" : "text/html"
		});
		response.write("received image:<br/>");
		response.write("<img src='/show' />");
		response.end();
	});
}

function show(response) {
	console.log("Request handler 'show' was called.");
	fs.readFile("/tmp/test.png", "binary", function(error, file) {
		if (error) {
			response.writeHead(500, {
				"Content-Type" : "text/plain"
			});
			response.write(error + "\n");
			response.end();
		} else {
			response.writeHead(200, {
				"Content-Type" : "image/png"
			});
			response.write(file, "binary");
			response.end();
		}
	});
}

function post(response,request) {
	  console.log("Request handler 'post' was called.");

	  var body = '<html>'+
	    '<head>'+
	    '<meta http-equiv="Content-Type" content="text/html; '+
	    'charset=UTF-8" />'+
	    '</head>'+
	    '<body>'+
	    '<form action="/postshow" method="post">'+
	    '<textarea name="text" rows="20" cols="60"></textarea>'+
	    '<input type="submit" value="Submit text" />'+
	    '</form>'+
	    '</body>'+
	    '</html>';

	    response.writeHead(200, {"Content-Type": "text/html"});
	    response.write(body);
	    response.end();
	}

function postshow(response,request) {
	
    var postData = "";
    request.setEncoding("utf8");
    request.addListener("data", function(postDataChunk) {
      postData += decodeURIComponent(postDataChunk);
      console.log("Received POST data chunk '"+
      postDataChunk + "'.");
    });

    request.addListener("end", function() {
  	  console.log("Request handler 'postshow' was called.");
  	  response.write('<head><meta charset="utf-8"/></head>'); 
	  response.write("（测试）You've sent: " + postData);
	  response.end();
    });
}

exports.start = start;
exports.upload = upload;
exports.show = show;
exports.post = post;
exports.postshow = postshow;
exports.ls = ls;