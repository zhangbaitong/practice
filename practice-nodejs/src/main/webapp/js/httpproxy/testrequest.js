var http = require('http')
var opt = {
 "port": 80,
  //"host": "www.baidu.com",
  "host": "xunyui.nvwayun.com",
  "hostname": "xunyi.nvwayun.com",
  //"hostname": "www.baidu.com",
  "method": "GET",
  "headers": {
    //"host": "mvip.infobird.nvwayun.com",
    //"host":"www.zol.com.cn",
    //"host":"vip.nvwayun.com",
    //"host":"xunyi-test.nvwayun.com2",
    //"host":"vip-test.nvwayun.com",
    "host":"mvip.infobird.nvwayun.com",
    "connection": "close",
    "cache-control": "max-age=0",
    "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36",
    "accept-encoding": "gzip,deflate,sdch",
    "accept-language": "zh-CN,zh;q=0.8,en;q=0.6",
    "cookie": "pgv_pvi=8374060032; pgv_si=s6039036928; PHPSESSID=6m8n5tgeqbltl0kirrcea6s0g5"
  },
  "agent": false,
  "path": "/"
}
//以下是接受数据的代码
var body = '';
var req = http.request(opt, function(res) {
  console.log("Got response: " + res.statusCode);
  res.on('data',function(d){
  body += d;
 }).on('end', function(){
  console.log(res.headers)
  console.log(body)
 });

}).on('error', function(e) {
  console.log("Got error: " + e.message);
})
req.end();