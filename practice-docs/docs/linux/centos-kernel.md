centos-kernel

centos 内核的选择

更新

	yum update

查看当前kernel

	rpm -qa kernel

	uname -a

查看kernel列表

	rpm -qa | grep kernel

修改启动顺序

vi /boot/grub/grub.conf
(/boot/grub/menu.lst)

default=0既操作系统的启动顺序，将其修改后保存，重新启动，就OK了

删除不需要的kernel
	
	rpm -e kernel***
	或者
	yum remove kernel-2.6.32-71.el6.i686


yum update避免升级内核的方法

	cp /etc/yum.conf /etc/yum.conf.bak

	方法一 修改yum的配置文件

		vi /etc/yum.conf 在[main]的最后添加 exclude=kernel*

	方法二 直接在yum的命令后面加上如下的参数：

		yum –exclude=kernel* update