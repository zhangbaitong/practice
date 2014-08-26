var request = require("request"),
  token = require("./wx_token");
var api = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
var send = function(app, openid, msg, cb) {
  token.get(app, function (err, token) {
    var opt = {
      url : api + token.access_token,
      json : {
        "touser": openid,
        "msgtype":"text",
        "text" : {"content" : msg.toString()}
      }
    };
    try {
      request.post(opt, function (e, response, body) {
        cb && cb(e, body);  
      });
    } catch(e) {
      cb && cb(e);
    }
  });
};
module.exports.send = send;
