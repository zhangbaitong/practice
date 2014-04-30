安装rzsz功能

yum install lrzsz

发现不能安装，后来发现wget有问题，原来是公司网络验证导致

然后开始安装，首先去找了一个163的镜像文件放到目录：/etc/yum.repos.d/CentOS-Base.repo（替换，记得备份）

然后执行

yum makecache

yum --enablerepo=base clean metadata

yum install lrzsz

使用：sz发送文件，rz接受文件