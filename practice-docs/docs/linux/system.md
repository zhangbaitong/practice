##SYSTEM

内存使用情况

	free -m
	top
	注:只要交换空间没有过度使用就没问题，另外参考-/+ buffers/cache:第一个为使用的，第二个为可用的。

硬盘及分区

	fdisk -l

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
