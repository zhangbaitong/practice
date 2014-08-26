var request = require("request"),
  util = require("util"),
  EE = require("events").EventEmitter,
  Store = require("../lib/store"),
  async = require("async"),
  _ = require("underscore"),
  settings = require("../etc/settings"),
  db = require("./db"),
  cacheStore = Store(),
  WeiboConnector = require('../lib/weibo'),
  wxApps = {}, wbApps;

//初始化所有微信应用
for(var app in settings.weixin) {
  wxApps[app] = require("../apps/" + app);
  wxApps[app].init({settings:settings, db:db});
}
var specialWords = ["hi","hello","您好","你好","嗨","?","？"];
var traced = function (traceId, result) {
  if (result != 'success') {
    //db.trace(traceId, 2);
    //todo
    // send a message to user by the backend of weixin
  }
};

var Engine = function () {
  var _this = this;
  this.work = function (msg, reply){
    var app = wxApps[msg.app];
    msg.channel = '';
    var ctx = {keyword : msg.text, originMsg : msg, uid : msg.sender_id, securities : []};
    //没有找到响应的应用
    if (!app) {
      ctx.replyText = "不支持app：" + msg.app;
      reply(ctx);
      return;
    }
    if (msg.type == 'event' || ['subscribe', 'unsubscribe', 'location'].indexOf(msg.data.subtype ) != -1) {
      if (msg.data.subtype == "subscribe") {
        app.welcome(ctx, function (err, ctx) {
          reply(ctx);
        });
      } else {
        ctx.replyText = '';
        if (msg.data.subtype == 'location') {
          db.addWxLocation(msg.sender_id, msg.data.lat, msg.data.lon, msg.data.prec, function () {
          });
        }
        reply(ctx);
      }
      db.logs(ctx, "-", function () {});
    } else if ((msg.type == 'event' && msg.data.subtype == 'follow') || specialWords.indexOf(msg.text.toLowerCase()) != -1) {
      app.welcome(ctx, function (err, ctx) {
        reply(ctx);
      });
    } else if (msg.type == 'text' && msg.text) {
      app.search(ctx, function (err, ctx) {
        if (err || !ctx) {
          ctx.replyText = "";
          reply(ctx);
          db.logs(ctx, "NOT FOUND", function () {});
        } else {
          reply(ctx);
          try {
            ctx.channel = msg.channel;
            db.logs(ctx, "SUCCESS", function (err, ctx) {
              _this.once("trace:" + ctx.traceId, traced);
              setTimeout(function () {
                _this.emit("trace:" + ctx.traceId, ctx.traceId, "timeout"); 
              }, 25000);
              
            });
          } catch (e) {
            console.log(e.stack);
          }
        }
      });  
    } else {
      app.welcome(ctx, function (err, ctx) {
        reply(ctx);
      });
    }
  };

  this.trace = function (traceId) {
    _this.emit("trace:" + traceId, traceId, "success"); 
    db.trace(traceId, 1);
  };
};
util.inherits(Engine, EE);
var engine = module.exports = new Engine();

//初始化新浪微博的长连接
for (var pro in settings.weibos) {
  var conn = new WeiboConnector(settings.weibos[pro]);
  conn.on("msg", engine.work);
  conn.on("error", function (err) {
    
  });
}
