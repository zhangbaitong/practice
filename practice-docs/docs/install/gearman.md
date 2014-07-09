##资源介绍

http://gearman.org/

跨多种环境部署 Gearman

http://www.ibm.com/developerworks/cn/opensource/os-gearman/index.html

利用开源的Gearman框架构建分布式图片处理平台

http://blog.s135.com/dips/

##简介
Gearman是一个分发任务的程序架构，由三部分组成：
Gearman client：提供gearman client API给应用程序调用。API可以使用C,PHP,PERL,MYSQL UDF等待呢个语言，它是请求的发起者。
Gearman job server：将客户端的请求分发到各个gearman worker的调度者，相当于中央控制器，但它不处理具体业务逻辑。
Gearman worker：提供gearman worker API给应用程序调用，具体负责客户端的请求，并将处理结果返回给客户端。
Mogilefs的分布式文件系统的核心就是用gearman实现的。
这个软件的应用场景很多，比如视频网站的视频处理，分布式日志处理，电子邮件处理，文件同步处理，图片处理等等，只要是可以放开，不影响体验和响应的场 景，需要并行进行大量计算和处理的程序都是可以的。Yahoo在60或更多的服务器上使用gearman每天处理600万个作业。新闻聚合器digg构建 了一个相同规模的gearman网络，每天可处理400000个作业。
Gearman不但可以做为任务分发，还可以做为应用方面的负载均衡。可以让worker放在不同的一堆服务器上，也可以启动放在同一个cpu的多个核 上。比如，应用视频转换程序，不希望web服务器来处理视频格式转换，这时，可以在这一堆服务器上进行任务分发，在上面加载worker处理视频格式，对 外的web服务器就不会被视频转换过程影响。而且扩展方便，加一台服务器到任务调度中心，注册成worker即可，这时job server会在请求到来的时候，将请求发送给空闲的worker。还可以运行多个job server，组成ha架构，如果一个job server当掉了，client和worker会自动迁移到另一台job server上。

##安装
[Job Server (gearmand) -- 172.16.1.183]
1.首先安装libdrizzle
    #yum install libdrizzle libdrizzle-devel
2.安装gearman（两种方法1.yum2.源码包）。（c版的server）
    1）yum安装
    #rpm -ivh http://dl.iuscommunity.org/pub/ius/stable/Redhat/6/x86_64/epel-release-6-5.noarch.rpm
    #yum install -y gearmand
    2）源码包安装
    #cd /opt/build/
    #wget https://launchpad.net/gearmand/trunk/0.34/+download/gearmand-0.34.tar.gz
    #tar zxf gearmand-0.34.tar.gz
    #cd gearmand-0.34
    #./configure
    #make && make install
3.启动gearman服务
    1）yum安装方式
    #/etc/init.d/gearmand start
    2）源码包安装方式
    #/opt/build/gearmand-0.34/sbin/gearmand -d

    #gearmand -vvv -u root 
    INFO Starting up
    INFO Listening on :::4730 (6)
    INFO Creating wakeup pipe
    INFO Creating IO thread wakeup pipe
    INFO Adding event for listening socket (6)
    INFO Adding event for wakeup pipe
    INFO Entering main event loop
4.加入到服务
    chkconfig --add gearmand
    chkconfig  gearmand on
    chkconfig --list
    service gearmand start
5.打开对应端口
    /sbin/iptables -I INPUT -p tcp --dport 4730 -j ACCEPT
    /etc/rc.d/init.d/iptables save
    /etc/init.d/iptables restart

worker&&client以php方式
[worker --  172.16.1.180]
安装gearmand如上所示

##安装 Gearman PHP extension
1.下载gearman-0.8.0.tgz并安装
    #cd /opt/build/
    #wget http://pecl.php.net/get/gearman-0.8.0.tgz
    # yum install -y libgearman-devel.x86_64
    # yum install -y re2c 
    #tar zxf gearman-0.8.0.tgz 
    #cd gearman-0.8.0.tgz
    #phpize
    # ./configure
    # make && make install
2.编辑php.ini配置文件加载相应模块并使之生效
    # vim /etc/php.ini
    extension = "gearman.so"
3.查看gearman.so模块是否加载
    # php --info | grep gearman
    gearman
    gearman support => enabled
    libgearman version => 0.14
    PWD => /opt/build/gearman-0.8.0
    _SERVER["PWD"] => /opt/build/gearman-0.8.0
    # php -m | grep gearman
    gearman
4.启动job
gearmand -d
如果当前用户是 root 的话，则需要这样操作：
gearmand -d -u root
缺省会使用 4730 端口，下面会用到。
    注意：如果找不到 gearmand 命令的路径，别忘了用 whereis gearmand 确认

[client -- 172.16.1.181]
    安装如work同。如上所示。

##测试：
[Job Server (gearmand) -- 172.16.1.183]

##启动gearmand

以命令行工具来验证gearman的功能
启动 Worker：gearman -h 172.16.1.183 -w -f wc -- wc -l &
运行Client：gearman -h 172.16.1.183 -f wc < /etc/passwd

启动 Worker：gearman -w -f wc -- wc -l &
运行 Client：gearman -f wc < /etc/passwd


42
可以看到验证成功。

以php验证gearman的功能
编写 Worker
worker.php 文件内容如下：
<?php
$worker= new GearmanWorker();
$worker->addServer('172.16.1.183', 4730);
$worker->addFunction('reverse', 'my_reverse_function');
while ($worker->work());
function my_reverse_function($job) {
return strrev($job->workload());
}
?>
设置后台运行 work
php worker.php &
编写 Client
client.php 文件内容如下：
<?php
$client= new GearmanClient();
$client->addServer('172.16.1.183', 4730);
echo $client->do('reverse', 'Hello World!'), "\n";
?>
运行 client
php client.php
输出：!dlroW olleH

##安装gearman monitor

https://github.com/yugene/Gearman-Monitor

https://github.com/qzio/gearman-monitor

GearmanMonitor 是用来查看 Gearman 服务状态的工具。
包括 运行中/过的队列 Queue，运行中的所有 workers，及服务器 servers。
GearmanMonitor 需要有 Net_Gearman 支持。


下载并直接解压到指定PHP目录即可，然后访问对应的index.php

提示缺少Net_Gearman（http://pear.php.net/package/Net_Gearman/）

Easy Install
pear install Net_Gearman

Pyrus Install
php pyrus.phar install pear/Net_Gearman

这里采用第一种，需要安装php的pear

下载最新版本pear:
wget http://pear.php.net/go-pear.phar
安装：
php go-pear.phar
确认各项配置文件和相关信息，然后确认
然后询问是否同意修改php.ini(这里就是对指定的php使用的php.ini进行追加pear的path，建议先对原有的文件做个备份)
然后安装：pear install Net_Gearman

# 如果提示
# No releases available for package "pear.php.net/Net_Gearman"
# 则执行
pear install  channel://pear.php.net/Net_Gearman-0.2.3

然后重新启动php（如果存在php多版本则需要注意）
kill -USR2 pieced



##安装gearman manager
GearmanManager
GearmanManager 用来统一管理用 PHP 编写的 Gearman workers。需要 PHP 启用 pcntl。用 install/install.sh 安装完成后，根据需要和设置，修改 /etc/init.d/gearman-manager:

##PATH##
DAEMON=/usr/local/bin/gearman-manager
PIDDIR=/tmp
PIDFILE=${PIDDIR}/manager.pid
LOGFILE=/tmp/gearman-manager.log
CONFIGDIR=/data/gearman-manager
GEARMANUSER="gearmand"
PARAMS="-c ${CONFIGDIR}/config.ini"
GearmanManager 安装时，选择的是 PECL library，启动时可能会遇见如下的问题：

[root@www gearman-manager]# /etc/init.d/gearman-manager start
Starting gearman-manager: [  OK  ]
[root@www  gearman-manager]# php: libgearman/universal.cc:553: bool gearman_request_option(gearman_universal_st&, gearman_string_t&): Assertion `con->recv_state == GEARMAN_CON_RECV_UNIVERSAL_NONE' failed.
php: libgearman/universal.cc:553: bool gearman_request_option(gearman_universal_st&, gearman_string_t&): Assertion `con->recv_state == GEARMAN_CON_RECV_UNIVERSAL_NONE' failed.
.....
解决办法如 Bug #60764 Enabling Non-Blocking Mode causes asseration 这里所提， 修改 pecl-manager.php ：

1
2
//注释下面这句
//$thisWorker->addOptions(GEARMAN_WORKER_NON_BLOCKING);



##Gearman Java

两个版本：

java-gearman-service（官方）

    https://code.google.com/p/java-gearman-service/

    java-gearman-servic.jar

    包括gearman server，还包括client和work客户端API

gearman-java（不太推荐）

    https://launchpad.net/gearman-java

    gearman-java.jar

    包括 work和client的API，没有server。

gearman server（johnewar）

    https://github.com/johnewart/gearman-java

    http://code.johnewart.net/maven/org/gearman/gearman-server/0.6.0/gearman-server-0.6.0.jar

    gearman-server-0.6.0.jar

    包括jetty，redis和postpresql，gearman-java-master.zip中还有client和work 的客户端api





Q:
I've been trying to get Gearman compiled on CentOS 5.8 all afternoon. Unfortunately I am restricted to this version of CentOS by my CTO and how he has our entire network configured. I think it's simply because we don't have enough resources to upgrade our network... But anyways, the problem at hand.

I have searched through Server Fault, Stack Overflow, Google, and am unable to locate a working solution. What I have below is stuff I have pieced together from my searching.

Searches have told said to install the following via yum:

yum -y install --enablerepo=remi boost141-devel libgearman-devel e2fsprogs-devel e2fsprogs gcc44 gcc-c++ 
To get the Boost headers working correctly I did this:

cp -f /usr/lib/boost141/* /usr/lib/ cp -f /usr/lib64/boost141/* /usr/lib64/ rm -f /usr/include/boost ln -s /usr/include/boost141/boost /usr/include/boost 
With all of the dependancies installed and paths setup I then download and compile gearmand-1.1.2 just fine.

wget -O /tmp/gearmand-1.1.2.tar.gz https://launchpad.net/gearmand/1.2/1.1.2/+download/gearmand-1.1.2.tar.gz cd /tmp && tar zxvf gearmand-1.1.2.tar.gz ./configure && make -j8 && make install 
That works correctly. So now I need to install the Gearman library for PHP. I have attempted through PECL and downloading the source directly, both result in the same error:

checking whether to enable gearman support... yes, shared not found configure: error: Please install libgearman 
What I don't understand is I installed the libgearman-devel package which also installed the core libgearman. The installation installs libgearman-devel-0.14-3.el5.x86_64, libgearman-devel-0.14-3.el5.i386, libgearman-0.14-3.el5.x86_64, and libgearman-0.14-3.el5.i386.

Is it possible the package version is lower than what is required? I'm still poking around with this, but figured I'd throw this up to see if anyone has a solution while I continue to research a fix.

Thanks!


A:
This should do the trick:

export GEARMAN_LIB_DIR=/usr/include/libgearman 
export GEARMAN_INC_DIR=/usr/include/libgearman 
That should work, if not you'll have to do some minor edits to config.m4.



other:

http://gearman.org/gearman_php_extension
http://blog.csdn.net/aidenliu/article/details/7406390
http://www.php.net/manual/en/gearmanclient.dobackground.php
http://www.wenzizone.com/2012/09/27/how_to_fix_rpm_filedigests_payloadisxz_is_needed.html
http://www.2cto.com/os/201206/136785.html
http://blog.s135.com/dips
http://blog.csdn.net/hfahe/article/details/5519582
http://hi.baidu.com/sunjiujiu/item/4406281c952cf47a7b5f2594


