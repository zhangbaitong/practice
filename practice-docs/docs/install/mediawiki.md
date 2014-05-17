##安装资料参考

官方资料
http://www.mediawiki.org/wiki/Manual:Installing_MediaWiki
http://www.mediawiki.org/wiki/Manual:Contents/zh

##安装

1.下载：wget http://releases.wikimedia.org/mediawiki/1.22/mediawiki-1.22.6.tar.gz

2.解压：tar xvzf mediawiki-1.22.6.tar.

3.修改目录权限：chmod 777 mw-config/

4.放到apache对应的URL的根目录下的一个子目录下：比如localhost对应www,则localhost/wiki对应www/wiki(建议目录名称不要叫wiki)

5.然后访问localhost/wiki进行配置。包括管理员和wiki配置，之后会生成LocalSettings.php，然后放到wiki的根目录和index.php同级。

6.修改是否允许注册用户（之前的配置中设置为非公开wiki）
	
	$wgGroupPermissions['*']['createaccount'] = true;



##一些备注

由于之前虚拟机都包含了Mysql和PHP，Apache的环境，所以上面没有涉及依赖。


1.网络设置
#vi /etc/sysconfig/network-scripts/ifcfg-eth0
DEVICE="eth0"
NM_CONTROLLED="yes"
ONBOOT="yes" no表示设备不启动
HWADDR=******
TYPE=Ethernet
BOOTPROTO=none
IPADDR=你的IP
PREFIX=24
GATEWAY=网关
DNS1=你的DNS
DOMAIN=备用DNS
DEFROUTE=yes
IPV4_FAILURE_FATAL=yes
IPV6INIT=no
NAME="System eth0"
UUID=*******

#service network restart 重启网络
ping 一下DNS（ctrl+z退出ping）

2.安装ssh (centos6好象默认安装了)
#yum install ssh 安装
#service sshd start 设置开机运行
#chkconfig sshd on

3.安装mysql
#yum install mysql mysql-server 安装
#chkconfig --levels 235 mysqld on 设置开机运行
#/etc/init.d/mysqld start 启动
#mysql_secure_installation 安全设置

4.安装apache
#yum install httpd 安装
#chkconfig --levels 235 httpd on 设置开机运行
#/etc/init.d/httpd start 启动

5.修改防火墙规则，开放80端口（若IE能访问apache则不需要）
#/etc/init.d/iptables stop
#/sbin/iptables -I INPUT -p tcp --dport 80 -j ACCEPT
#/etc/rc.d/init.d/iptables save
#service iptables restart

6.安装php5
#yum install php
#/etc/init.d/httpd restart 也可用reboot重启计算机
在PHP5中添加MySQL支持
#yum install php-mysql php-gd php-imap php-ldap php-mbstring php-odbc php-pear php-xml php-xmlrpc

7.安装mediawiki
#cd /var/www/html/
#yum install wget
#wget http://fossies.org/unix/www/mediawiki-1.19.4.tar.gz
#tar zxvf mediawiki-1.19.4.tar.gz
#mv mediawiki-1.19.4/var/www/html/wiki
#rm -f mediawiki-1.19.4.tar.gz
#vi /etc/selinux/config
SELINUX=disabled 开启文件上传
#chmod -R 777 /var/www/html/wiki 文件夹可写权限

8. http://IP/wiki，进入MediaWiki设定

9. 更新php
#yum install php53
提示package-cleanup is found
#yum install yum-utils