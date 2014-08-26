var settings = require("./etc/settings"),
  argv = require("optimist").argv,
  express = require("express"),
  xxin = require("./routers/xxin"),
  wxAuth = require("./routers/wx_auth"),
  engine = require("./lib/engine"),
  wxLocation = require('./routers/wx_location'),
  wxReply = require("./routers/wx_reply");

xxin.setEngine(engine);


var secure = function (req, res, next) {
  var ip = req.get('x-real-ip') || req.ip;
  if (!settings.allow || settings.allow.length == 0) {
    next();
  } else if (settings.allow.indexOf(ip) !== -1) {
    next();
  } else {
    res.send(403, "Forbbiden");
  }
};

var app = express();
app.use(express.logger());

//获取原始报文
app.use (function(req, res, next) {
    var data = '';
    req.setEncoding('utf8');
    req.on('data', function(chunk) { 
       data += chunk;
    });

    req.on('end', function() {
      req.body = data;
      next();
    });
});

app.post("/weixin/wxapi/:app", xxin.weixin);
app.get("/weixin/wxapi/:app", function (req, res) {
  if (req.query.echostr) {
    res.send(req.query.echostr);
  } else {
    res.send("");
  }
});

app.get("/weixin/auth/:app/auth", wxAuth.auth);
app.get("/weixin/auth/:app/authed", wxAuth.authed);

//通过openid取最近五条坐标
app.get("/weixin/api/:app/location", secure, wxLocation.location);

app.post("/weixin/api/:app/send", secure, wxReply.send);

app.listen(argv.p || 4000);
