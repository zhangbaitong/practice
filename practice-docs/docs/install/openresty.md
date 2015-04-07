openresty

自带ngx_lua_module

nginx-httpmodule
http://wiki.nginx.org/HttpLuaModule

其他方式

http://blog.csdn.net/vboy1010/article/details/7868645

http://openresty.org/

1.下载

wget http://openresty.org/download/ngx_openresty-1.7.10.1.tar.gz

2.安装(centos6.5)

tar xzvf ngx_openresty-1.7.10.1.tar.gz
cd ngx_openresty-1.7.10.1/
yum install readline-devel pcre-devel openssl-devel
./configure
make
make install

3.test code

创建工作空间目录

mkdir ~/work
cd ~/work
mkdir logs/ conf/

创建配置文件

conf/nginx.conf

worker_processes  1;
error_log logs/error.log;
events {
    worker_connections 1024;
}
http {
    server {
        listen 8080;
        location / {
            default_type text/html;
            content_by_lua '
                ngx.say("<p>hello, world</p>")
            ';
        }
    }
}

4.启动nginx

暴露环境变量

PATH=/usr/local/openresty/nginx/sbin:$PATH
export PATH

工作目录下执行nginx

nginx -p `pwd`/ -c conf/nginx.conf

5.访问服务

curl http://localhost:8080/

6.banchmark

ab -n 10000 -c 1000 http://localhost:8080/
