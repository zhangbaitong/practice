##SYSTEM

查看CentOS版本信息

	cat /etc/redhat-release

CentOS yum为163源
	
	cd /etc/yum.repos.d/
	wget http://mirrors.163.com/.help/CentOS-Base-163.repo
	mv CentOS-Base.repo CentOS-Base.repo.bak
	mv CentOS-Base-163.repo CentOS-Base.repo
	生成缓存
	yum makecache

EPEL安装(Extra Packages for Enterprise Linux，企业版Linux的额外软件包)

	https://fedoraproject.org/wiki/EPEL
	RHEL/CentOS系统有许多第三方源，比较流行的比如RpmForge，RpmFusion，EPEL，Remi等等
	多个第三方源，可能会因此产生冲突——一个软件包可以从多个源获取，一些源会替换系统的基础软件包，从而可能会产生意想不到的错误。已知的就有Rpmforge与EPEL会产生冲突。
	6.x
	http://download.fedoraproject.org/pub/epel/6/i386/repoview/epel-release.html
	http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm
	5.x
	http://download.fedoraproject.org/pub/epel/5/i386/repoview/epel-release.html
	http://download.fedoraproject.org/pub/epel/5/i386/epel-release-5-4.noarch.rpm
	注意EPEL 的安装包是独立编译的，所以它可以安装在32位和64位系统中。

	安装
	cat /etc/RedHat-release
	wget http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm
	rpm -ivh epel-release-6-8.noarch.rpm|rpm -ivh epel-release*
	查看源列表
	yum repolist
	repo文件位置
	/etc/yum.repos.d/epel.repo

	优先级插件Yum Priorities
	http://wiki.centos.org/PackageManagement/Yum/Priorities
	安装
	yum install yum-priorities
	修改配置
	vim /etc/yum/pluginconf.d/priorities.conf
	添加
	[main]
	enabled=1
	修改repo文件
	vi /etc/yum.repos.d/CentOS-Base.repo
	将下面的文本分别添加到 Base, Updates, Addons, 和 Extras 这几个源记录的后面*
	priority=1
	将下面的文本添加到 CentOSPlus 源记录的后面*
	priority=2
	* 源记录（repository entry）是由 [ 和 ] 括起来的源名称。例如，Base 源记录就标记为 [base] 。
	** 优先级由 1 ~ 99 的 99 个数表示，1 的优先级最高。优先级小的源即使有某软件的较新版本，如果优先级高的源中没有，在启用该插件的情况下，系统也无法安装/升级到该较新版本。图形界面的 YUM 工具一般默认就已经包含了优先级插件。
	*** 要禁用 YUM 优先级功能，只需要在（1.A.） /etc/yum/pluginconf.d/priorities.conf 中将 enable=1 改为 enable=0 即可。不建议！

查看当前登录者
	tty为直接连接电脑
	pts为远程连接电脑
	谁登录了
		who
	我的登录信息
		who am i(who -m)
	显示标题
		who -H
	显示本地系统节点的运行级别
		who -r
	显示所有用户的所有信息
		who -aH
	显示当前用户名
		whoami
	计算登录人数
		who |wc -l
	计算登录并使用人数
		w |wc -l
	显示登录和使用状态
		w
	显示上次重启时间
		who -b
	统计登录人数
		who -q
	显示登录人得空闲状态
		who -uH

内存使用情况

	free -m
	top
	注:只要交换空间没有过度使用就没问题，另外参考-/+ buffers/cache:第一个为使用的，第二个为可用的。

硬盘及分区

	fdisk -l

挂载数据磁盘
	查看磁盘分区（并获取磁盘代号）
		fdisk -l
	查看磁盘格式
		df -Th
	创建分区
		fdisk /dev/xvde
		n（创建）
		e(扩展)
		1（第几个扩展分区）
		回车（选择默认起始块数）
		回车（选择默认结束块数）
		w（保存）
	确认信息
		fdisk -l
	格式化
		mkfs -t ext3 -c /dev/xvde(会执行check，这里可以跳过)
	创建目录
		mkdir /data
	挂载
		mount /dev/xvde /data
	查看
		df -Th
	设置自动挂载
		vi /etc/fstab
		/dev/xvde /data ext3 defaults 0 3


硬盘空间使用情况

	df -h

当前文件夹下磁盘使用情况

	du --max-depth=1 -h
	du --max-depth=0 -h （当前目录）
	du -sh /opt/temp

显示当前目录总大小

	du -sh

硬盘IO分析

	iostat -d -x -k 1 10
		%util如果接近100%说明IO请求过多，已经满负荷，有瓶颈
		svctm应该小于await（如果他们很接近表示没有IO等待时间，如果await远大于svctm则表示IO队列太长，等待响应时间也长）
		avgqu-sz队列长度，如果过长有问题（但这个参数表示的单位时间的平均，不能反映瞬时洪水）
	iostat - x 1 5（%idle低于70%说明压力大，读取进程有过多的wait	）

查看目录大小

	du -sh business_card

查看平均负载

	uptime
	w(包含登陆用户信息)
	top
	注：load average: 0.00, 0.00, 0.00表示1，5，15分钟的系统进程数平均值，如果大于5则问题严重，不大于4可以接受

	 vmstat 1 4
	 r经常大于3或4，且id经常少于50，表示cpu负荷很重
	 bi，bo长期不等于0表示内存不足
	 disk经常不等于0，且在b中的队列大于2或3，表示io性能不好

查看系统内核

	uname -a(简化命令：uname -r)

查看OS位数

	ls -1F /|grep /$（查看是否存在lib64）
	file /sbin/init

查看系统载入模块

	lsmod | grep ip_vs(查看lvs模块是否加入)

查看物理网卡

	lspci | grep Ether（虚拟机没发现）
	vi /etc/sysconfig/network-scripts/ifcfg-eth0(查看网卡配置信息)

查看网络连接

	ifconfig -a(所有)
	ifconfig eth0（eth0网卡）
	ifconfig eth0 | grep "inet addr"(查看IP)

	ping -c 5 www.baidu.com(5个数据包探测)
	netstat  -an | grep 8080
	netstat  -r或route -n(查看路由确认网关)
	traceroute www.baidu.com(跟踪网络地址)
	nslookup>www.baidu.com>exit(查找域名)
	lsof -i或指定端口lsof -i:8080（当前系统打开文件)

查看进程
	
	ps -aux | grep nginx

	top->q
		->space(刷新)
		->P：CPU排序，T：时间排序，M：内存大小排序，W：将当前设置写入~/.toprc文件
		->m：切换内存信息，t：切换CPU和内存显示状态，c：切换完整名称和命令行
	kill(默认15)
	kill -9(强制kill)
	killall(killall nginx)

系统日志

	tail -n10 /var/log/messages(系统)
	tail -n10 /var/log/secure（安全）
	tail -n10 /var/log/wtmp或需要用last(用户)
	last / lastlog

处理日志

	清空文件
		>test.log
	保留文件最后一行
		tail -1 test.log > test2.log
		mv test2.log test.log

	awk
	sed

服务器优化
