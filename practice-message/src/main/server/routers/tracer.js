var fs = require("fs"),
  request = require("request"),
  path = require("path"),
  EE = require("events").EventEmitter,
  util = require("util");

var Tracer = function () {
  var _this = this;
  this.banner = function (req, res) {
    var ua = req.headers['user-agent'];
    if (req.query.traceId) {
      _this.emit("trace", req.query.traceId);
    }
    res.set("Content-Type", "image/jpeg");
    res.set("Content-Length", banner.length);
    res.set("Cache-Control", "public");
    res.send(banner);
  };

  this.mline = function (req, res) {
    if (req.query.traceId) {
      _this.emit("trace", req.query.traceId);
    }
    var ua = req.headers['user-agent'] || "android";
    var key = "gp123WeixinAndriod";
    if (ua.match(/CFNetwork|Darwin|iphone|ipad|ios/i)) {
      key = "gp123WeixinIos";
    }
    if (req.query.big) {
      key = "weixinBig";
    }
    var url = "http://gupiao123.cn/chart/mline/"+key+"/"+req.params.code+".png";
    request.get(url).pipe(res);
  };
};

util.inherits(Tracer, EE);
module.exports = new Tracer();
