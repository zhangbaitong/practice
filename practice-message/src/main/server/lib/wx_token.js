var request = require("request"),
  path = require("path"),
  fs = require("fs"),
  settings = require("../etc/settings");
var tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";
var tokenFile = path.dirname(__dirname) + "/data/";
var fetchToken = function (app, cb) {
  var url = tokenUrl.replace("{appid}", settings.weixin[app].appId).replace("{secret}", settings.weixin[app].secret);
  request.get(url, function (err, response, body) {
    if (!err) {
      var json = JSON.parse(body);
      json.expire = Date.now() + json.expires_in * 1000 - 30000;
      fs.writeFileSync(tokenFile + app + ".json", JSON.stringify(json));
      cb(null, json);
    } else {
      cb(err);
    }
  });
};

var get = exports.get = function (app, cb) {
  var tf = tokenFile + app + '.json';
  if (!fs.existsSync(tf)) {
    fetchToken(app, cb);
  } else {
    try {
      var token = JSON.parse(fs.readFileSync(tf, 'binary'));
      if (token.expire > Date.now()) {
        cb(null, token);
      } else {
        fetchToken(app, cb);
      }
     }catch (e) {
      fetchToken(app, cb);
    }
  }
};
//get('xunyi', function () {});
