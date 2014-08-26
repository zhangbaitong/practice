var xml2json = require("xml2json"),
  fs = require("fs");


var replyText = function (ctx, text) {
  var time = parseInt(Date.now() / 1000);
  var xml = "<xml>" +
    "<ToUserName><![CDATA["+ctx.originMsg.sender_id+"]]></ToUserName>" +
    "<FromUserName><![CDATA["+ctx.originMsg.receiver_id+"]]></FromUserName>" +
    "<CreateTime>"+time+"</CreateTime>" +
    "<MsgType><![CDATA[text]]></MsgType>" +
    "<Content><![CDATA["+text+"]]></Content>" +
    "<FuncFlag>0</FuncFlag>" +
    "</xml>";
  return xml;
}

var replyImageText = function (ctx, articles) {
  var time = parseInt(Date.now() / 1000);
  var xml = "<xml>" +
    "<ToUserName><![CDATA["+ctx.originMsg.sender_id+"]]></ToUserName>" +
    "<FromUserName><![CDATA["+ctx.originMsg.receiver_id+"]]></FromUserName>" +
    "<CreateTime>"+time+"</CreateTime>" +
    "<MsgType><![CDATA[news]]></MsgType>" +
    "<ArticleCount>"+articles.length+"</ArticleCount><Articles>";

  articles.forEach(function (article) {
     xml += "<item>" +
       "<Title><![CDATA["+article.display_name+"]]></Title>" +
       "<Description><![CDATA["+article.summary+"]]></Description>" +
       "<PicUrl><![CDATA["+article.image + "]]></PicUrl>" +
       "<Url><![CDATA["+article.url+"]]></Url></item>";
  });
  xml += "</Articles></xml>";
  return xml;
}

var debugXml = function () {
  return "<xml>" +
"<ToUserName><![CDATA[toUser]]></ToUserName>" +
"<FromUserName><![CDATA[oHSLTjtxif214FEi2NKzhNPVlYc8]]></FromUserName>" +
"<CreateTime>1351776360</CreateTime>" +
"<MsgType><![CDATA[text]]></MsgType>" +
"<Title><![CDATA[公众平台官网链接]]></Title>" +
"<Description><![CDATA[公众平台官网链接]]></Description>" +
"<Content><![CDATA[hlgf]]></Content>" +
"<Event><![CDATA[LOCATION]]></Event>" +
"<MsgId>1234567890123456</MsgId>" +
"<Latitude>23.137466</Latitude>" +
"<Longitude>113.352425</Longitude>" +
"<Precision>119.385040</Precision>" +
"</xml>";
};

var adapt = function (xml) {
  var json = xml2json.toJson(xml, {coerce : false,trim : true, arrayNotation :false});
  json = JSON.parse(json).xml;
  var t = {
    sender_id : json.FromUserName,
    receiver_id : json.ToUserName,
    created_at : new Date(parseInt(json.CreateTime) * 1000),
    type : json.MsgType,
    id : json.MsgId,
    text : "",
    data : {},
    channel : "",
    app : ""
  };
  if (t.type == 'text') {
    t.text = json.Content.replace(/&amp;#35;/g, "#");
  } else if (t.type == "event") {
    t.data.subtype = json.Event.toLowerCase();
  }
  if (json.Event == 'LOCATION') {
    t.data.lat = json.Latitude;
    t.data.lon = json.Longitude;
    t.data.prec = json.Precision;
  }
console.log(t);
  return t;
};
var engine;
exports.setEngine = function (ng) {
  engine = ng;
};
var response = function (req, res, app, channel) {
  var xml;
  if (req.query.debug) {
    xml = debugXml();
  } else {
    xml = req.body;
  }
  try { 
    var msg = adapt(xml);
    msg.channle = channel || msg.channel;
    msg.app = app || msg.app;
    engine.work(msg, function (ctx) {
      if( typeof ctx.replyText == "string" || !ctx.articles) {
         res.send(replyText(ctx, ctx.replyText));
      }  else {
        res.send(replyImageText(ctx, ctx.articles));
      }
    });
  } catch (e) {
    console.log(e.stack);
    res.send("");
  }
};

exports.weixin = function (req, res) {
  response(req, res, req.params.app, "");
};
