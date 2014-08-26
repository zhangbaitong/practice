var request = require("request"),
  Store = require("../lib/store"),
  async = require("async"),
  _ = require("underscore"),
  settings = require("../etc/settings"),
  cacheStore = Store();

var formatNumber = function (num) {
  if (num > 10000000) {
    return (num / 100000000).toFixed(2) + "亿";
  }
  
  if (num > 1000) {
    return (num / 10000).toFixed(2) + "万";
  }
  return num;
};

var weibo = exports.weibo = function (ctx) {
  var createDesc = function (detail) {
    if (detail.amount == 0) {
      return {title : detail.stockName + "停牌中", summary : "停牌中"};
    }
    var title = detail.stockName + "("+detail.stockID+"."+detail.marketType+")最新" + detail.newPrice.toFixed(2); 
    if (detail.upDown > 0) {
      title += "涨";
    } else {
      title += "跌";
    }
    title += Math.abs(detail.markUp) + "%";
    summary = "最高" + detail.highPrice.toFixed(2) + "，最低" + detail.lowPrice.toFixed(2) + "，";
    summary += "成交额" + formatNumber(detail.amount);

    return {title : title, summary : summary};
  };
  var getUrl = function (detail) {
    var code = detail.stockID + "."+detail.marketType
    var url = "http://gupiao123.cn/security/" + code + "/?c=" + ctx.originMsg.channel;
    if (detail.stockType.match(/\,(HA|ZA)\,/)) {
      url = "http://co.gupiao123.cn/weixin/security/" + code +"/?uid="+ctx.originMsg.sender_id+"&c=" + ctx.originMsg.channel
    } 
    return url;
  };
  var title, desc, articles = [], traceId = ctx.traceId || "0";
  if (ctx.securities.length > 1 || ctx.forceList) {
    title = "搜索" + ctx.keyword + "的结果";
    if(ctx.title) {
      title = ctx.title;
    }
    articles.push({
      display_name : title,
      summary : title,
      url : "http://gupiao123.cn/search/?code=" + encodeURIComponent(ctx.keyword),
      image : settings.imgServer + "/weixin/trace/banner.jpg?traceId=" + traceId
    });
    for (var i = 0; i < ctx.securities.length; i ++) {
      if (!ctx.details[ctx.securities[i]]) {
        continue;
      }
      var detail = ctx.details[ctx.securities[i]];
      desc = createDesc(detail);
      var url = getUrl(detail);
      articles.push({
        display_name : desc.title,
        summary : desc.summary,
        image : "http://gupiao123.cn/chart/mline/WeixinMini/"+detail.stockID+".png?" + parseInt((Date.now() / 1000)),
        url : url
      });
    }
  } else {
    var detail = ctx.details[ctx.securities[0]]; 
    desc = createDesc(detail);
    var url = getUrl(detail);
    articles.push({
      display_name : desc.title,
      summary : desc.summary,
      image : settings.imgServer + "/weixin/trace/mline/"+detail.stockID+".png?traceId=" + traceId,
      url : url
    });
    
    if (ctx.article) {
      var article = {
        display_name : ctx.article.title,
        summary : ctx.article.title,
        image : "http://gupiao123.cn/weixin/images/gp123/related_news.png",  
        url : "http://gupiao123.cn/news/" + ctx.article.id + "/?c=" + ctx.channel
      };
      if (detail.stockType.match(/\,(HA|ZA)\,/)) {
        article.url = "http://co.gupiao123.cn/weixin/security/"+ctx.securities[0]+"/?uid="+ctx.originMsg.sender_id+"&tab=article&c=" + ctx.channel
      }
      articles.push(article);
    }
    if (ctx.sm) {
      var st = [];
      _.keys(ctx.sm).forEach(function (code) {
        if (ctx.details[code]) {
          st.push(ctx.details[code].stockName);
        } 
      });
      if (st.length > 0) {
        var title = "最新分时走势与" + st.join("、") + "等相似，点击查看";
        articles.push({
          display_name : title,
          summary : title,
          image : "http://gupiao123.cn/weixin/images/gp123/similar_mline.png",
          url : "http://gupiao123.cn/security/"+ctx.securities[0]+"/?similar=mline&c=" +  ctx.channel
        });
      }
    }

    if (ctx.sk) {
      var st = [];
      _.keys(ctx.sk).forEach(function (code) {
        if (ctx.details[code]) {
          st.push(ctx.details[code].stockName);
        }
      });
      if (st.length > 0) {
        var title = "近20日K线与" + st.join("、") + "等个股历史相似，点击查看";
        articles.push({
          display_name : title,
          summary : title,
          image : "http://gupiao123.cn/weixin/images/gp123/similar_kline.png",
          url : "http://gupiao123.cn/security/"+ctx.securities[0]+"/?similar=kline&c=" +  ctx.channel
        });
      }
    }
    if (articles.length == 1) {
      articles[0].image += "&big=1";
    }
  }
  return articles;
};
exports.base = weibo;
