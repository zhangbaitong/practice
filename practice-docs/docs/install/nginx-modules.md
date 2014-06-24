nginx-modules

##官方介绍

http://wiki.nginx.org/Modules

##安装nginx第三方模块

./configure --prefix=/你的安装目录  --add-module=/第三方模块目录


未安装nginx:

# ./configure --prefix=/usr/local/nginx-1.4.1 \
--with-http_stub_status_module \
--with-http_ssl_module --with-http_realip_module \
--with-http_image_filter_module \
--add-module=../ngx_pagespeed-master --add-module=/第三方模块目录
# make
# make isntall
# /usr/local/nginx-1.4.1/sbin/nginx


已安装nginx:
1.查看nginx安装时的依赖
	nginx/sbin/nginx -V
	configure arguments: --user=www --group=www --prefix=/usr/local/webserver/nginx --with-http_stub_status_module --with-http_ssl_module
2.切换到nginx安装目录,备份objs目录下已经编译过的nginx运行文件
3.重新编译nginx
	./configure --user=www --group=www --prefix=/usr/local/webserver/nginx --with-http_stub_status_module --with-http_ssl_module --add-module=/software/ngx_http_log_request_speed/
# make
# /usr/local/nginx-1.4.1/sbin/nginx -s stop
# cp objs/nginx /usr/local/nginx/sbin/nginx
# /usr/local/nginx-1.4.1/sbin/nginx


http://wiki.nginx.org/HttpLogRequestSpeed


# ./analyzer.pl -h
-h : this help message
# 显示帮助信息
-u : group by upstream
# 按upstream分组
-o : group by host
# 按主机分组
-r : group by request
# 按请求分组，推荐这个


