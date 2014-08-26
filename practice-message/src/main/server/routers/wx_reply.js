var wxReply = require("../lib/wx_reply"),
  settings = require("../etc/settings"),
  qs = require("querystring"),
  async = require("async");

module.exports.send = function (req, res) {
  if (typeof req.body == 'string') {
    req.body = qs.parse(req.body);
  }
  if (!settings.weixin[req.params.app] || !req.body.openids || !req.body.msg){
    res.json({error : "bad request", result : "failure"});
    return;
  } 
  res.json({result : "success"});
  var ids = req.body.openids.split(",");
  async.each(ids, function (openid, cb) {
    wxReply.send(req.params.app, openid, req.body.msg, function (e, body) {
      console.log('sendmessage', openid, req.body.msg, e, body);
      cb();
    });
  }, function (err) {
    console.log(err);
  });
};
