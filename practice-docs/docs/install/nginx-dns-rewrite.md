nginx基本配置信息：

    server{
        listen 80;
        server_name domain.yourname.com;
        location / {
                rewrite (.*) http://ip/domain;
        }
    }

注：也可以通过配置多个server直接映射web目录，但这样需要独立配置相关信息，比较麻烦，但性能最好。

nginx测试命令

/usr/local/webserver/nginx/sbin/nginx -t
/usr/local/webserver/nginx/sbin/nginx -t -c /usr/local/webserver/nginx/conf/nginx.conf

nginx自动更新配置

/usr/local/webserver/nginx/sbin/nginx -s reload