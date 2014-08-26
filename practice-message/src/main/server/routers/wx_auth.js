var settings = require("../etc/settings")
  , db = require("../lib/db")
  , request = require("request");
var wxAuthApi = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={APPID}&redirect_uri={REDIRECT_URI}&response_type=code&scope=snsapi_base&state=#wechat_redirect";
var wxAccessTokenApi = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code={CODE}&grant_type=authorization_code";
var auth = exports.auth = function (req, res) {
  var app = req.params.app;
  if (!settings.weixin[app]) {
    res.send("app " + app + "not exists!");
    return;
  }
  var backurl = req.query.backurl || '';
  var appSettings = settings.weixin[app];
  var back = encodeURIComponent(appSettings.webServer + "/weixin/auth/" + app + "/authed?backurl=" + encodeURIComponent(backurl));
  var url = wxAuthApi.replace("{APPID}", appSettings.appId).replace('{REDIRECT_URI}', back);
  res.redirect(url);
};

var authed = exports.authed = function (req, res) {
  var app = req.params.app;
  if (!settings.weixin[app]) {
    res.send("app " + app + "not exists!");
    return;
  }
  if (!req.query.code) {
    res.send("auth failed!");
    return;
  }
  var appSettings = settings.weixin[app];
  var url = wxAccessTokenApi.replace("{APPID}", appSettings.appId)
    .replace("{SECRET}", appSettings.secret)
    .replace("{CODE}", req.query.code);
  request.get(url, function (err, r, body) {
    console.log(body)
    try {
      body = JSON.parse(body);
      db.createAccount(body, function (err, info) {
        var backurl = req.query.backurl;
        if (backurl) {
          if (backurl.indexOf('?') != -1) {
            backurl += "&openid=" + body.openid + "&user_hash=" + body.openid;
          } else {
            backurl += "?openid=" + body.openid + "&user_hash=" + body.openid;
          }
          res.redirect(backurl);
        } else {
          res.send("your openid is " + body.openid);
        }
      });
    } catch (e) {
      res.json(body);
    }
  });
};

//access_token package
//var data = {"access_token":"OezXcEiiBSKSxW0eoylIeKPVh2PG-D1UQQPPRIGOzPdVZgy4kE32U-yW1BYARQyWGUgMiJ5bhnqoNeNEJmXr3_99kQzUjYCyvtPjsziLSAf63rEgKSHLnCHmGKfvj_e1eDrVmr_NRAiVMswzIbrpSw","expires_in":7200,"refresh_token":"OezXcEiiBSKSxW0eoylIeKPVh2PG-D1UQQPPRIGOzPdVZgy4kE32U-yW1BYARQyWkS3JPUoUDKKZbL6jLAAvkKwL-KIVkIBkRjVfKF4N_MuHc-uxSh3QfzQHYjxkg2fRfNbtYKDYwnYoYp7okV5NcA","openid":"o_lMBjz1l8J7GQziX6uqFVeYa9KM","scope":"snsapi_base"};

