var mysql = require("mysql")
  settings = require("../etc/settings.json");
var mcli;
var connect = function () {
  mcli = mysql.createConnection(settings.mysql);
  mcli.on("error", function (err) {
    if (err.code == 'PROTOCOL_CONNECTION_LOST') {
      connect();
    }
  });
}
connect();

var logs = exports.logs = function (ctx, reply, cb) {
  var sql = "INSERT INTO wx_logs (toUser, fromUser, msgId, msgType, msgBody, msgTime, reply, app)"
    + "VALUES(?, ?, ? , ?, ?, ?, ?, ?)";
  
  var msg = ctx.originMsg;
  var mt = parseInt(new Date(msg.created_at).getTime() / 1000);
  var values = [msg.receiver_id, msg.sender_id, msg.id || "", msg.type, msg.text, mt, reply, msg.app];
  if (msg.type =='event') {
    values[3] = msg.data.subtype;
  }

  mcli.query(sql, values, function (err, info) {
    err && console.log(err);
    ctx.logId = info.insertId;
    if (ctx.securities.length > 0) {
      accessCache(ctx, cb);
    } else {
      cb(null, ctx);
    }
  });
};

var accessCache = exports.accessCache = function (ctx, cb) {
  var time = parseInt(Date.now() / 1000),
    replyType = 'x';
  
  var msg = ctx.originMsg;
  var sql = "INSERT INTO access_cache (userId, `time`, keyword, app, replyType, inTime, logId)"
    + "VALUES(?, ?, ?, ?, ?, ?, ?)";
  var vs = [msg.sender_id, time, ctx.keyword, ctx.originMsg.app, replyType, new Date(), ctx.logId];
  mcli.query(sql, vs, function (err, info) {
    err && console.log(err);
    ctx.traceId = info.insertId;
    cb(null, ctx);
  });
};


var createAccount = exports.createAccount = function (data, cb) {
  var expire = new Date(Math.floor(Date.now() + data.expires_in * 1000));
  var sql = "INSERT wx_account (openid, access_token, refresh_token, expire) "
    + " VALUES(?, ?, ?, ?) "
    + " ON DUPLICATE KEY UPDATE expire = VALUES(expire), access_token = VALUES(access_token), expire = VALUES(expire)";
  var d = [data.openid, data.access_token, data.refresh_token, expire];
  mcli.query(sql, d, cb);
};

var addWxLocation = exports.addWxLocation = function (openid, lat, lon, prec, cb) {
  var sql = "INSERT INTO wx_location(openid, lat, lon, prec) VALUES( ?, ?, ?, ?)";
  var data = [openid, lat, lon, prec];
  mcli.query(sql, data, cb);
};

var getWxLocation = exports.getWxLocation = function (openid, limit, cb) {
  var sql = "SELECT * FROM wx_location WHERE openid = ? ORDER BY id DESC LIMIT " + limit;
  mcli.query(sql, [openid], function (err, ret) {
    cb(err, ret);
  });
};

