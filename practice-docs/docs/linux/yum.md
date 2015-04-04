yum

---------------------

Extra Packages for Enterprise Linux (EPEL)官方介绍
https://fedoraproject.org/wiki/EPEL

EPEL installation instructions
https://fedoraproject.org/wiki/EPEL#How_can_I_use_these_extra_packages.3F

---------------------

使用EPEL软件仓库可以安装docker，版本必须在centos6 以后

如果是centos7

下载并安装这个软件包

wget http://mirror.hust.edu.cn/epel/beta/7/x86_64/epel-release-7-0.2.noarch.rpm

rpm -ivh

epel-release-7-0.2.noarch.rpm

如果是centos6

wget http://mirrors.hustunique.com/epel/6/i386/epel-release-6-8.noarch.rpm

rpm -ivh

epel-release-6-8.noarch.rpm

yum install docker-io 用上面这个命令安装就可以了

关于EPEL：https://Fedoraproject.org/wiki/EPEL/zh-cn
 
---------------------
rpm -ivh http://dl.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm
rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-EPEL-6
yum -y install yum -priorities
 

安装Docker
 
yum -y install docker-io
service docker start
chkconfig docker on
---------------------



yum执行日志

	var/log/yum.log

yum查看列表和安装内容（内容太多，极力不推荐）
	
	yum list|grep installed

通过底层rpm查找

	rpm -qa | grep ssh
-----------------------
在dell r410上面装的是centos6,64的操作系统，用的163的源，我一直都是用163的源，比较快。但是我发现这个源里面，根本没有libmcrypt libmcrypt-devel这二个包，装php扩展mcrypt时，又要用到这二个包，所以我手动装了libmcrypt包，但是给我的感觉是163源中的包不全。后来有一个朋友告诉我用epel，用了之后感觉很爽。

一，什么是epel

如果既想获得 RHEL 的高质量、高性能、高可靠性，又需要方便易用(关键是免费)的软件包更新功能，那么 Fedora Project 推出的 EPEL(Extra Packages for Enterprise Linux)正好适合你。EPEL(http://fedoraproject.org/wiki/EPEL) 是由 Fedora 社区打造，为 RHEL 及衍生发行版如 CentOS、Scientific Linux 等提供高质量软件包的项目。

二，使用心得

1，不用去换原来yum源，安装后会产生新repo

2，epel会有很多源地址，如果一个下不到，会去另外一个下

http://mirror.xfes.ru/fedora-epel/6/x86_64/ibus-table-chinese-scj-1.3.4-1.el6.noarch.rpm: [Errno 14] PYCURL ERROR 6 – “”
Trying other mirror.
http://mirrors.ustc.edu.cn/fedora/epel/6/x86_64/ibus-table-chinese-scj-1.3.4-1.el6.noarch.rpm: [Errno 14] PYCURL ERROR 6 – “”
Trying other mirror.
http://repo.boun.edu.tr/epel/6/x86_64/ibus-table-chinese-scj-1.3.4-1.el6.noarch.rpm: [Errno 14] PYCURL ERROR 6 – “”kjs

。。。。。。

3，更新时如果下载的包不全，就不会进行安装。这样的话，依赖关系可以保重

三，安装epel,centos6选6，5就选5

32位系统选择：

rpm -ivh http://dl.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm

rpm -ivh http://dl.fedoraproject.org/pub/epel/5/i386/epel-release-5-4.noarch.rpm

64位系统选择：

rpm -ivh http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm

rpm -ivh http://dl.fedoraproject.org/pub/epel/5/x86_64/epel-release-5-4.noarch.rpm

导入key：

rpm –import /etc/pki/rpm-gpg/RPM-GPG-KEY-EPEL-6

rpm –import /etc/pki/rpm-gpg/RPM-GPG-KEY-EPEL-5

如果用比较新的软件，用epel-test.repo这个文件就行了

别忘了安装yum install yum-priorities

[root@localhost yum.repos.d]# ls |grep epel
epel.repo
epel-testing.repo

----------------------------------------
（原创）RHEL/CentOS 7.x使用EPEL第三方yum源
时间 2014-07-22 19:45:50  服务器运维与网站架构
原文  http://www.ha97.com/5649.html
PS：EPEL的yum源是很稳定的第三方yum安装源，也是我经常使用的，基于CentOS/RHEL7.x的也有了，整理如下：


# wget http://dl.fedoraproject.org/pub/epel/beta/7/x86_64/epel-release-7-0.2.noarch.rpm
# yum install epel-release-7-0.2.noarch.rpm
或者
# rpm -ivh epel-release-7-0.2.noarch.rpm
验证查看是否安装成功：

yum repolist

查看某个包的详细信息：

yum --enablerepo=epel info htop 
列出epel源的所有包列表：

yum --disablerepo="*" --enablerepo="epel" list available | less
附： 1、RHEL/CentOS 6.x的EPEL源安装方法：

## RHEL/CentOS 6 32-Bit ##


# wget http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm
# rpm -ivh epel-release-6-8.noarch.rpm
## RHEL/CentOS 6 64-Bit ##


# wget http://download.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm
# rpm -ivh epel-release-6-8.noarch.rpm
2、RHEL/CentOS 5.x的EPEL源安装方法：

## RHEL/CentOS 5 32-Bit ##


# wget http://download.fedoraproject.org/pub/epel/5/i386/epel-release-5-4.noarch.rpm
# rpm -ivh epel-release-5-4.noarch.rpm
## RHEL/CentOS 5 64-Bit ##


# wget http://download.fedoraproject.org/pub/epel/5/x86_64/epel-release-5-4.noarch.rpm
# rpm -ivh epel-release-5-4.noarch.rpm