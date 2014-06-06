##PHP学习

###入门

参考资料：

	php手册：
	http://www.php.net/manual/zh/index.php
	下载地址：
	http://www.php.net/download-docs.php

	zendframework2项目地址
	https://github.com/zendframework/zf2/
	zf2官方地址
	http://framework.zend.com/
	zf2框架开发指南
	http://framework.zend.com/manual/2.2/en/index.html
	安装指南
	https://github.com/zendframework/zf2/blob/master/INSTALL.md
	zf2skeleton-application安装
	http://framework.zend.com/manual/2.2/en/user-guide/skeleton-application.html

	eclipse pdt（php dev tool）
	http://www.eclipse.org/pdt/downloads/
	http://download.eclipse.org/tools/pdt/updates/release



##一些工具简介

	composer：php的扩展管理工具
		Composer 项目官方：http://getcomposer.org
		Composer Github项目：https://github.com/composer/composer
		PHP Composer软件包列表：https://packagist.org/
		
	phpredis：php的redis扩展
		https://github.com/nicolasff/phpredis/


##php的关闭和重启

	注：php5.3.3下的php-fpm不支持/usr/local/php/sbin/php-fpm (start|stop|reload)需要使用信号控制

	master进程可以理解以下信号

	INT, TERM 立刻终止
	QUIT 平滑终止
	USR1 重新打开日志文件
	USR2 平滑重载所有worker进程并重新载入配置和二进制模块

	关闭：

	kill -INT `cat /usr/local/php/var/run/php-fpm.pid`

	重启：

	kill -USR2 `cat /usr/local/php/var/run/php-fpm.pid`


	查看进程数：

	ps aux | grep -c php-fpm

	配置文件常见目录：

	/usr/local/webserver/php/etc/php.ini

	/etc/php.ini

	php -i | grep php.ini

##php模块安装

	方法一（重新编译）：

	重新编译php，加上--with-gettext

	方法二（动态安装）：

	1.下载同版本的php原包，解压后进入ext目录，目录下便是模块

	2.进入gettext目录下执行

		/usr/local/php/bin/phpize
		./configure --with-php-config=/usr/local/php/bin/php-config(以上两个命令的路径根据自己系统情况而定）
		make
		make install
	3、在php.ini里添加上gettext.so

		extension = "gettext.so"
	4.重启php

##Strict Standards设置（php.ini）


	error_reporting = E_ALL

	error_reporting = E_ALL&~E_NOTICE

	去掉E_STRICT