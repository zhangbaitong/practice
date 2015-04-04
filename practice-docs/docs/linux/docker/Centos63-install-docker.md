Centos63-install-docker

http://www.linuxidc.com/Linux/2014-01/95513.htm

1.禁用selinux（和LXC有冲突）

	cat /etc/selinux/config 

	SELINUX=disabled
	SELINUXTYPE=targeted

2.配置Fedora的EPEL源

	//yum install http://ftp.riken.jp/Linux/fedora/epel/6/i386/epel-release-6-8.noarch.rpm
	yum install http://ftp.riken.jp/Linux/fedora/epel/6/x86_64/epel-release-6-8.noarch.rpm

3.配置hop5.in源

	cd /etc/yum.repos.d
	wget http://www.hop5.in/yum/el6/hop5.repo

4.安装docker-io

	yum install docker-io
	(有干扰的EPEL库，删除了重新yum一下开始的EPEL源即可)
	Installing:
		docker-io	0.6.2-1.el6
	Installing for dependencies:
		kernel-ml-aufs	3.10.5-3.el6
		lxc 0.9.0-1.el6
 		lxc-libs 0.9.0-1.el6

5.检查安装情况

 	docker -h

6.配置cgroup文件系统

	vi /etc/fstab
	增加：none                    /sys/fs/cgroup          cgroup  defaults        0 0
 
 	mount /sys/fs/cgroup
	只有重新启动才能挂载/sys/fs/cgroup（因为当前运行的内核不支持cgroup），所以上面挂载的命令也可以不执行，但系统需要重新启动。

7.重启系统，选择“3.10.5-3.el6.x86_64”内核
	
	vi /boot/grub/grub.conf

8.系统启动后

	确认当前运行的内核
	uname -r
	确认文件系统
	grep aufs /proc/filesystems
	nodev   aufs

9.以守护模式运行docker.io

	docker -d &
	代理上网配置
		HTTP_PROXY=http://xxx:port docker -d &

10.在centos6.4容器里输出hello world

	docker run busybox /bin/echo hello world
	docker run centos:6.4 echo "hello world"

11.从容器里测试ping

	docker -dns '8.8.8.8' run centos:6.4 ping -c 3 yahoo.com

12.下载ubuntu镜像

	docker pull ubuntu

13.运行hello world

	docker run ubuntu /bin/echo hello world

常见错误：
"DNS/Networking Errors inside the docker"
 [root@localhost ~]# docker -dns="8.8.8.8" run centos:6.4 yum install hiphop-php
2013/08/21 07:53:05 POST /v1.4/containers/create
2013/08/21 07:53:05 POST /v1.4/containers/6d9fef14bd1a/start
2013/08/21 07:53:05 POST /v1.4/containers/6d9fef14bd1a/attach?logs=1&stderr=1&stdout=1&stream=1
Loaded plugins: fastestmirror
Error: Cannot retrieve repository metadata (repomd.xml) for repository: base. Please verify its path and try again
Could not retrieve mirrorlist http://mirrorlist.centos.org/?release=6&arch=x86_64&repo=os error was
14: PYCURL ERROR 6 - "Couldn't resolve host 'mirrorlist.centos.org'"
可以执行下面的命令来重置docker的运行环境，从而解决上述问题。
 pkill docker
iptables -t nat -F
ifconfig docker0 down
brctl delbr docker0
docker -d
感谢sciurus在创建kernel-ml-aufs的相关文件时付出的辛勤工作。



##挂载cgroup

在RedHat/CentOS环境中运行docker、lxc，需要手动重新挂载cgroup

1.我们首选禁用cgroup对应服务cgconfig。

	service cgconfig stop # 关闭服务 
	chkconfig cgconfig off # 取消开机启动

2.然后挂载cgroup，可以命令行挂载

	mount -t cgroup none /cgroup # 仅本次有效
  	或者修改配置文件，编辑/etc/fstab，加入
	none /cgroup cgroup defaults 0 0 # 开机后自动挂载，一直有效

3.调整lxc版本

  Docker0.7默认使用的是lxc-0.9.0，该版本lxc在redhat上不能正常运行，需要调整lxc版本为lxc-0.7.5或者lxc-1.0.0Beta2。前者可以通过lxc网站（http://sourceforge.net/projects/lxc/files/lxc/）下载，后者需要在github上下载最新的lxc版本（https://github.com/lxc/lxc，目前版本是lxc-1.0.0Beta2）。
这里特别说明一点，由于Docker安装绝对路径/usr/bin/lxc-xxx调用lxc相关命令，所以需要将lxc-xxx安装到/usr/bin/目录下。


4.启动docker服务

	service docker start # 启动服务 
	chkconfig docker on  # 开机启动

5。试运行
	
	docker run -i -t Ubuntu /bin/echo hello world
    初次执行此命令会先拉取镜像文件，耗费一定时间。最后应当输出hello world。

