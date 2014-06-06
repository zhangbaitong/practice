##资料

官网
http://www.zabbix.com/

http://www.zabbix.com/download.php

http://jaist.dl.sourceforge.net/project/zabbix/ZABBIX%20Latest%20Stable/2.2.3/zabbix-2.2.3.tar.gz

https://www.zabbix.com/documentation/doku.php?id=2.2/manual

https://www.zabbix.com/documentation/2.2/manual/installation

http://bguncle.blog.51cto.com/3184079/1330247

http://zabbix-manual-in-chinese.readthedocs.org/en/latest/index.html

##安装

agent

ps -ef | grep zabbix

rpm -ivh http://repo.zabbix.com/zabbix/2.2/rhel/6/x86_64/zabbix-release-2.2-1.el6.noarch.rpm

yum install zabbix-agent -y

service zabbix-agent start

server

rpm -ivh http://repo.zabbix.com/zabbix/2.2/rhel/6/x86_64/zabbix-release-2.2-1.el6.noarch.rpm


yum install zabbix-server-mysql zabbix-web-mysql -y

# mysql -uroot
mysql> create database zabbix character set utf8 collate utf8_bin;
mysql> grant all privileges on zabbix.* to zabbix@localhost identified by 'zabbix';
mysql> exit

# cd /usr/share/doc/zabbix-server-mysql-2.2.0/create
# mysql -uroot zabbix < schema.sql
# mysql -uroot zabbix < images.sql
# mysql -uroot zabbix < data.sql

# vi /etc/zabbix/zabbix_server.conf
DBHost=localhost
DBName=zabbix
DBUser=zabbix
DBPassword=zabbix

# service zabbix-server start

php_value max_execution_time 300
php_value memory_limit 128M
php_value post_max_size 16M
php_value upload_max_filesize 2M
php_value max_input_time 300

配置文件修改：

vi /etc/zabbix/web/zabbix.conf.php

日志查看：

tail -f /var/log/zabbix/zabbix_server.log

通过日志发现数据库连接soket有问题，设置为tmp目录下默认即可

vi /etc/zabbix/zabbix_server.conf

文件权限设置

chmod 755 /etc/zabbix/web

启动关闭相关服务：

service zabbix-agent start

service zabbix-server start

查看服务状态：

service zabbix-agent status

查看端口监听状态：

netstat  -ntlp  | grep -i 10051

设置nginx代理：

webroot:/usr/share/zabbix

访问地址：

http://ip:port/

安装依赖（gettext）：

cd /software/lnmp/php-5.3.10/ext/gettext

/usr/local/webserver/php/bin/phpize

./configure --with-php-config=/usr/local/webserver/php/bin/php-config

make & make install

cd /usr/local/webserver/php/lib/php/extensions/no-debug-non-zts-20090626/

添加gettext.so

vi /usr/local/webserver/php/etc/php.ini

extension="gettext.so"

重启php

cat /usr/local/webserver/php/var/run/php-fpm.pid

kill -USR2 pid
