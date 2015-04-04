Docker 快速学习

http://zhou123.blog.51cto.com/4355617/1439895

http://docs.docker.com/reference/api/docker_remote_api_v1.16/

1.3.1的源
rpm -ivh http://dl.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm

Docker
操作系统：centos 6.5

一、安装：
1、配置epel源：
1
yum install -y yum-priorities && rpm -ivh http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm && rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-EPEL-6
2、安装 docker-io febootstrap
# febootstrap用来制作centos镜像的工具
1
yum install docker-io febootstrap -y

3、安装完成后挂cgroup文件系统
1
2
3
vim /etc/fstab
添加一行：
none            /sys/fs/cgroup      cgroup  defaults    0 0

4、重启系统：
1
reboot
docker 开机后会自动启动，如果没有启动可以手动启动：docker -d &

二、下面来制作一个Centos 6.5 镜像

1、使用febootstrap制作CentOS镜像文件centos6-image目录
1
febootstrap -i bash -i wget -i yum -i iputils -i iproute -i man -i vim -i openssh-server -i openssh-clients -i tar -i gzip centos6 centos6-image http://mirrors.aliyun.com/centos/6/os/x86_64/

#参数说明：
-i 镜像所需要安装的工具：把-i后面的参数传递给yum来实现安装，上面安装了ssh服务
centos6 是centos版本
centos6-image 是指定目录
命令的执行完成后，会在当前目录下生成一个目录centos6-image
cd centos6-images
ls 
bin  boot  dev  etc  home  lib  lib64  media  mnt  opt  proc  root  sbin  selinux  srv  sys  tmp  usr  var
 
ls -a root
. ..

这时root目录下没有任何文件，也不没有隐藏的点文件，如：.bash_logout  .bash_profile  .bashrc
如果这时制作出来的镜像使用ssh登录，会直接进入根目录下，如下：
bash-4.1.2#
bash-4.1.2#
bash-4.1.2# ls 
bin  boot  dev  etc  home  lib  lib64  media  mnt  opt  proc  root  sbin  selinux  srv  sys  tmp  usr  var
bash-4.1.2# ls -a root
. ..
为了避免这种情况，可以在centos6-image目录的root目录把.bash_logout  .bash_profile  .bashrc这三个文件设置一下
1
cd centos6-image && cp etc/skel/.bash* root/

这样就可以实现远程登录正常了

基于这样的实现，我们把可以以后一些复杂的配置文件事先配置好后放在一个目录下，然后再通过Dockerfile文件来调用，即可快速完成。

2、下面来创建一个基本的镜像：
1
cd centos6-image && tar -c .|docker import - centos6-base

命令完成后，使用docker images来查看
docker images
REPOSITORY            TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
centos6-base          latest              a01c3d440db8        1 minutes ago        311.3 MB

此时一个基本的镜像已完成，
3、下面来用Dockerfile文件来创建一个可以ssh登录的镜像
Dockefile 文件如下：
#Dockerfile
FROM centos6-base
MAINTAINER zhou_mfk <zhou_mfk@163.com>
RUN ssh-keygen -q -N "" -t dsa -f /etc/ssh/ssh_host_dsa_key
RUN ssh-keygen -q -N "" -t rsa -f /etc/ssh/ssh_host_rsa_key
RUN sed -ri 's/session    required     pam_loginuid.so/#session    required     pam_loginuid.so/g' /etc/pam.d/sshd
RUN mkdir -p /root/.ssh && chown root.root /root && chmod 700 /root/.ssh
EXPOSE 22
RUN echo 'root:redhat' | chpasswd
RUN yum install -y yum-priorities && rpm -ivh http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm && rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-EPEL-6
RUN yum install tar gzip gcc vim wget -y
ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8
CMD /usr/sbin/sshd -D
#End

下面使用上面的Dockerfile文件来创建一个可ssh登录的镜像
1
docker build -t centos6-ssh .

build: Build a container from a Dockerfile 这个是创建一个容器从Dockerfile文件

docker build <path> 寻找path路径下名为的Dockerfile的配置文件，使用此配置生成新的image

. 表示在当前目录下
1
docker build -t centos6-ssh /root/images/abc
  这个表示Dockerfile文件在：/root/images/abc下

命令执行完成后，centos6-ssh镜像就创建完成了，
查看一下：
docker images
REPOSITORY            TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
centos6-ssh           latest              b8ca70e7adee        1 hours ago        311.3 MB
centos6-base          latest              a01c3d440db8        2 hours ago        311.3 MB
下面创建一个容器来登录下：
docker run -d -p 127.0.0.1:2222:22 centos6-ssh
 
ssh root@127.0.0.1 -p 2222
输入密码：redhat
[root@a856a3c242cf ~]# 
[root@a856a3c242cf ~]# 
[root@a856a3c242cf ~]# ls
[root@a856a3c242cf ~]# 
[root@a856a3c242cf ~]# cat /etc/issue
CentOS release 6.5 (Final)
Kernel \r on an \m

Docker 容器间的使用

1、容器间的链接：
运行一个容器，给它一个名称，例如：
1
docker run -d -p 0.0.0.0:4455:22 -p 0.0.0.0:8080:80 --name one centos6-ssh
再运行另一个容器
1
docker run -d -p 0.0.0.0:4456:22 -p 0.0.0.0:8088:80 --link /one:two centos6-ssh2 env
说明：
/one:two 
one是第一个容器的名称，two是第二个容器的名称，
env是打印出来 第二个容器的环境变量

这样两容器就建立起一个网络通道，one和two容器所开放的端口也就是Dockerfile文件中定义开放的端口就可以连通了，
在宿主机上使用iptables命令来查看，例如：
iptables -L -n
Chain FORWARD (policy ACCEPT)
target     prot opt source               destination         
ACCEPT     tcp  --  172.17.1.28          172.17.1.29         tcp spt:3306 
ACCEPT     tcp  --  172.17.1.29          172.17.1.28         tcp dpt:3306 
ACCEPT     tcp  --  172.17.1.28          172.17.1.29         tcp spt:22 
ACCEPT     tcp  --  172.17.1.29          172.17.1.28         tcp dpt:22
从这里看到两个容器间端口可以互相的访问了，
说明：
这里的端口是以one这个容器所开放的端口，如one开放22，3306，而two容器只开放了22，在two上也会放3306给one，反之就不行了。--link是以连接容器开放的端口为准的。

2、Docker 容器下数据卷的理解

一个数据卷就是经过特殊设计的,在一个或多个容器中通过UFS文件系统提供的一些特性 
实现数据持久化或共享.
数据卷可以在容器之间共享和重复利用
可以对数据卷里的内容直接进行修改
对镜像的更新不会改变数据卷的内容
卷会一直持续到没有容器使用他们


2.1、添加一个数据卷
可以使用带有 -v 参数的 docker run 命令给容器添加一个数据卷.
1
docker run -d -p 0.0.0.0:4445:22 --name data -v /data centos6-ssh

这个在容器里就会有一个/data的卷

在Dockefile中使用VOLUME指令来创建添加一个或多个数据卷

2.2、挂载宿主文件夹到数据卷
使用-v参数也可以挂载宿主的文件夹到容器里
1
docker run -d -p 0.0.0.0:44455:22 --name data1 -v /src/data:/opt/data centos6-ssh
这样会把本地的/src/data文件夹挂在容器/opt/data目录
宿主机上的文件夹必须是绝对路径，而且当文件夹不存在时会自动创建
此功能在Dockerfile文件中无法使用

默认情况下Docker以读写权限挂载数据卷,但是我们也可以以只读方式进行挂载
1
docker run -d -p 0.0.0.0:44455:22 --name data1 -v /src/data:/opt/data:ro centos6-ssh
还是上面的那个命令，只是我们添加了一个ro选项来制定挂载时文件权限应该是只读的

2.3、创建和挂在一个数据卷容器
如果一些数据需要在容器间共享最好的方式来创建一个数据卷容器，然后从数据卷容器中挂载数据
1\创建一个带有命名容器来共享数据
1
docker run -d -v /dbdata --name dbdata centos6-ssh


2\在另一个容器中使用--volumes-from标记挂在/dbdata卷
1
docker run -d --volumes-from dbdata --name db1 centos6-ssh2

3\在另一个容器中同时也挂载/dbdata卷
1
docker run -d --volumes-from dbdata --name db2 centos6-ssh3


可以使用多个 -–volumes-from 参数来把多个容器中的多个数据卷放到一起

可以挂载通过挂载dbdata容器实现的容器db1和db2来扩展关系链,例如：
1
docker run -d --name db2 --volumes-from db1 centos6-ssh4


2.4、备份，恢复，迁移数据

使用它们来进行备份,恢复或迁移数据.如下所示,我们使用 

–volumes-from 标记来创建一个挂载了要备份数据卷的容器.
1
docker run --volumes-from dbdata -v $(pwd):/backup centos6-ssh tar cvf /backup/backup.tar /dbdata

这里我们创建并登录了一个新容器，挂载了dbdata容器中的数据卷，并把本地的一个目录挂载了/backup下，最后再传一条tar命令来备份dbdata卷到/backup下，当命令执行完成后容器就会停止运行，并保留dbdata的备份，在本地目录下会一个备份的文件
注：新创建的容器中要有tar命令，

得到备份数据就可以恢复或迁移数据了