var args = process.argv.slice(2);
//console.log(args);
if(!args.length){
    console.log("请输入参数!格式为：nodejs [path]/baidu.js [关键字] [页码]");
    return;
}
var key = args[0];
var pn = parseInt(args[1])?parseInt(args[1]):1;
pn=(pn>1)?(pn-1):0;
var jsdom = require("jsdom");
var url = "http://www.baidu.com/baidu?word="+key+"&pn="+10*pn;
console.log("正在访问："+url+"，请稍候....");
jsdom.env(url,["http://code.jquery.com/jquery.js"],
    function (errors, window) {
        var $ = window.jQuery;
        console.log($("title").text());
        var linkList=$("table.result td.f h3.t a");
        linkList.each(function(i,link){
            // console.log(link);
            console.log("标题["+(i+1)+"]Title:",$(link).text());
            console.log("链接["+(i+1)+"]Link:",$(link).attr("href"));
            var td = $("table.result h3 a").eq(0).closest("td.f");
            $(link).remove();
            console.log("概要["+(i+1)+"]Tip:",td.find("a.m").remove().end().find("span.g").remove().end().text().replace(/\s*?\-\s*?$/,""));
            console.log("=================================");
        });
        console.log("访问url：",url,",找到"+linkList.length+"条结果");
        console.log("关键字:",key,"第"+(pn+1)+"页");
    }
);