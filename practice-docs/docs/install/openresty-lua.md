openresty-lua

redis

安装redis开发包

git clone https://github.com/agentzh/lua-resty-redis.git

配置nginx
lua_package_path "/root/lua-resty-redis/lib/?.lua;;";
lua_code_cache off;(lua修改及时生效)
添加脚本路径(存放在work工作目录下)
location /lua {  
	content_by_lua_file lua/test_lua;  
}

user root;
worker_processes  4;
error_log logs/error.log;
events {
    worker_connections 2048;
}
http {
    lua_package_path "/root/lua-resty-redis/lib/resty?.lua;;";
    server {
        listen 8080;
        lua_code_cache off;
        location /test {
            default_type text/html;
            content_by_lua '
                ngx.say("<p>hello, world</p>")
            ';
        }
        location /lua {
                content_by_lua_file lua/test_lua;
        }
    }
}

生成脚本

/root/code/yourname/resty/work/test_lua;

local redis = require "resty.redis"  
  
local cache = redis.new()  
  
local ok, err = cache.connect(cache, '127.0.0.1', '6379')  
  
ngx.say("failed to connect:", err)

local result = cache:get("key")

ngx.say(result)
  

测试

curl http://localhost/lua


启动nginx

PATH=/usr/local/openresty/nginx/sbin:$PATH
export PATH

nginx -p `pwd`/ -c conf/nginx.conf

tail -f /root/code/yourname/resty/work/logs/error.log


nginx文件权限问题
1.查看用户
ps aux | grep "nginx: worker process" | awk '{print $1}'
2.赋权限
chown -R www-data:www-data proxy_temp

测试代码

1、解析16进制编码的中文参数

复制代码
local encodeStr = "%E6%B0%94"
local decodeStr = "";
for i = 2, #encodeStr - 1, 3 do
    local num = encodeStr:sub(i, i + 1);
    num = tonumber(num, 16);
    decodeStr = decodeStr .. string.char(num);
end
ngx.say(decodeStr)
复制代码
2、类似replace

local str = "a1b1c1d"
local result = string.gsub(str,"1","2")   --将1替换成2

local str = "A1B1C1"
local result = string.gsub(str,"1","0",2)    --输出的结果为：A0B0C1
3、直连mysql

复制代码
local mysql = require "resty.mysql"
local db = mysql:new()
db:connect{
    host = "10.10.3.218",
    port = 3306,
    database = "test_db",
    user = "root",
    password = "123456",
    max_packet_size = 1024*1024
}
local result = db:query("SELECT ID,NAME FROM TABLE")
ngx.say(result[1]["ID"])
ngx.say(result[1]["NAME"])
复制代码
4、直接Redis

local redis = require "resty.redis"
local cache = redis.new()
cache.connect(cache,"10.10.3.208", "6379")
local result = cache:get("key")
5、使用管道

复制代码
local redis = require "resty.redis"
local cache = redis.new()
cache.connect(cache,"10.10.3.208", "6379")
cache:init_pipeline()
for i=1,10 do
    cache:get("key")
end
local res = cache:commit_pipeline()
for j=1,#res do
    ngx.say(res[j])
end
复制代码
6、计算一共有多少页

local totalPage = math.floor((totalRow+pageSize-1)/pageSize)
