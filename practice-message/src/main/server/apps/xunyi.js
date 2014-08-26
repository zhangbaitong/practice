var request = require("request"),
  Store = require("../lib/store"),
  async = require("async"),
  _ = require("underscore"),
  settings = require("../etc/settings"),
  cacheStore = Store(),
  db, opts,
  welcomeTxt = "讯鸟易名片,最快速的云端名片交换.<a href='{host}/default.html?user_hash={uid}'>点击开始交换</a>";

exports.init = function (options) {
  opts = options;
  db = options.db;
  welcomeTxt = welcomeTxt.replace("{host}", settings.weixin.xunyi.webServer);
};

var welcome = exports.welcome = function (ctx, cb) {
  ctx.replyText = welcomeTxt.replace("{uid}", ctx.uid);
  cb(null, ctx);
};

exports.search = function (ctx, cb){
  var kw = ctx.originMsg.text;
  if (kw == "s") {
    ctx.replyText = "讯鸟易名片,最快速的云端名片交换.<a href='"+settings.weixin.xunyi.webServer+"/search.html?user_hash="+ctx.uid+"'>点击查看名片</a>";
    cb(null, ctx);
  } else if( kw == "tyb"){
    ctx.replyText = "<a href='http://103.31.202.7:8001/test.html?user_hash="+ctx.uid+"'>点击查看</a>";
    cb(null, ctx);
  } else {
    welcome(ctx, cb);
  }
};
