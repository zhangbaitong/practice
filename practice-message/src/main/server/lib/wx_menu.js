var request = require("request"),
  wxToken = require("./wx_token"),
  path = require("path"),
  fs = require("fs");
var mdir = path.dirname(__dirname) + "/menus/";
var menuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

var set = exports.setMenu = function (app, cb) {
  var menu = require(mdir + app + ".json");
  wxToken.get(app, function (err, token) {
    if (!err) {
      var opts = {
        url : menuUrl + token.access_token,
        body : JSON.stringify(menu),
        json : true
      };
      request.post(opts, function (err, response, body) {
        console.log(err, body);
      });
    }
  });
};
set('xunyi');