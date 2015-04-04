Docker

官网
https://www.docker.com/
https://docs.docker.com/
http://docs.docker.com/examples/
https://www.docker.com/tryit/
https://github.com/docker/docker

docker中文社区（内容很有限）
  http://www.docker.org.cn/

http://my.oschina.net/dmdgeeker/blog/336005

docker从入门到实践
  http://yeasy.gitbooks.io/docker_practice/

仓库查找
https://registry.hub.docker.com/
如何在maxos上安装
http://docs.docker.com/installation/mac/
如何在centos上安装
https://docs.docker.com/installation/centos/
github on boot2docker
https://github.com/boot2docker/boot2docker
docker网络配置
http://www.oschina.net/translate/docker-network-configuration?cmp
http://www.oschina.net/translate/docker-network-configuration

中文指南
http://www.widuu.com/chinese_docker/installation/macos.html
http://docker.widuu.com/(old)

git
https://github.com/docker/docker




提供和构建时间 
目前云平台的愿景要求你，必须维护一个绝对低限度的实例，来全天候地服务于所有应用。具体点来说，作为云服务的使用者，即使现在没有一个用户在使用你的应用，你也需要维护一个最低功能的实例

云程序之所以要做成这样，是因为一些云应用的属性，像程序的提供和构建时间这样的，其重新启动的时间至少需要几分钟。所以，为了以防万一，你必须很好地维护一个实例。

这儿也正是Docker优势所在， docker几乎不存在应用的提供和构建时间，即使是开始一个docker容器，有时还不到一秒种。docker的这个特性几乎可以让使用者完全不需要一个占用着专门处理器，内存和磁盘空间还得全天候运行的实例了。

实例抽象 
云服务提供商通过提供一个独立的虚拟机实例来获利。使用者根据使用这些实例的时间来付费。这些实例都是彼此隔离的，而且资源都是自己的，因此通过虚拟机实例来收费也是合理的。

在使用容器的情况下, 操作系统被许多容器共享。此外，既然这些容器没不会一直需要一个专用的处理器和的内存。通过抽象实例付费的方法可以抛弃了，通过你使用的模型来付费的方法出现了。

专业化服务的提供商，比如数据库托管和图形加工处理提供商，可以大大受益于这样的解耦实例。不是通过分配资源这样的选择服务多少收费，而是通过实际服务使用量这样可插拔式的服务来收费。最后说一下，抽象底层机器将有利于衡量服务使用量。

按小时支付模式 
大多数的云主机提供商要求用户按小时支付（即使你只用了15分钟然后就把你的实例杀掉了），但这已经比原来需要购买专用硬件已经好很多了。但是，这仍然不是你只支付你所用的部分。通过像docker一样的共享资源环境的模式，客户只需要支付他们所用的CPU周期和内存就可以了。

开发的便利 
到目前为止，开发者们被要求维护不同的开发环境和产品环境。这种不一致性不可避免的带来了问题。Docker承诺将会通过使开发和部署在相同的容器中来消除这些情况，这也将使得开发更加容易并且少一些痛苦。


Docker是轻量级的容器+镜像仓库，在LXC（linux轻量级容器）的基础上构建，可以运行任何应用程序。
docker.io的核心层由以下几个部分组成:
1、可执行程序，“/usr/bin/docker”
2、docker.io网站上有一系列创建好的操作系统和应用程序映像
3、AUFS（另一个统一文件系统）来实现文件系统的快照，文件变化，控制只读或读写的需求。
4、LXC（Linux轻量级容器）
5、Cgroups（各种资源和命名空间的管理）



Docker.io和一个完全的虚拟机的区别？
答：现在docker内部使用的技术是Linux容器（LXC技术），运行在与它宿主机同样的操作系统上，准许它可以和宿主机共享许多系统资源，它也会使用AuFS作为文件系统，也为你管理网络。AuFS是一个层状的文件系统，因此你可以有一个只读和一个只写部分，二者结合起来，可以使系统的共同部分用做只读，那块被所有容器共享，并且给每个容器自己的可写区域
让我们假设你有一个容器镜像（image）容量是1GB，如果你想用一个完整的虚拟机来装载，你得需要容量的大小是1GB乘上你需要虚拟机的数量。但使用Linux容器虚拟化技术（LXC）和AuFS，你可以共享1GB容量，如果你需要1000个容器，假设他们都运行在同样的系统影像上，你仍然可以用稍微比1GB多一点的空间来给容器系统
docker想比完全的虚拟机，可以实现基础共享，一个完整的虚拟化系统得到了分给它的自有全部资源，只有最小的共享。你获得了更多的隔离，但是这是很庞大的（需要更多的资源）

应用场景：
一个完整的虚拟系统通常得用几分钟去启动，linux容器虚拟技术（LXC）只要数秒，甚至有时时间更短。

对于每种虚拟系统都有反对者和支持者。如果你希望一个完全隔离的和资源有保障的环境，那么完全的虚拟机是你的选择。如果你只希望进程之间相互隔离，并且希望大量运行他们在一个合理大小的宿主机器上。那么linux容器虚拟技术（LXC）是你的选择。

部署一个一致的生产环境说起来容易做起来难。即使你使用了chef 和puppet之类的工具，像操作系统升级，还有一些其它的事情而造成的主机及环境之间的改变，往往是常有的事。

docker所做的事情就是赋予你一种能力，使你可以将OS快照存入一个通用的镜像，并使得在往其它的docker主机上部署时变得容易。对于本地，开发、质量管理、产品等等，都是用的同一个镜像。当然你也可以用其它的工具来做到这一点，但是可能没有这么容易或者这么快。

这对于单元测试是非常棒的。让我们来看看你有1000个测试，而且都需要连接数据库。为了不破坏任何事情，你需要一个接着一个的运行，以便这些测试不会相互影响（每个测试都在事务中，然后回滚回去）。使用Docker，那么你可以创建一个数据库的镜像（image），既然你知道这些测试会运行在相同的的数据库快照下，那么就可以并行地运行所有测试。既然这些测试都是并行运行在linux容器中，那么他们可以同时运行在同样的环境中。这样你的测试会完成的非常快。试着用完整的虚拟机来做这件事。
通俗的讲就是要做什么事，先做一个镜像，然后大家一起同时用这个通用镜像
你开始有个基础 镜像（image），然后进行改变数据，并且使用docker提交这些改变,这个会建立一个镜像（image），这个 镜像（image）只包含数据改变的部分。当你想运行你的这个 镜像（image）你仍然需要这基础 镜像（image），然后使用层式的文件系统，将你的映像置于基础映像之上，这个例子中用AUFS，AUFS将不同层融合起来，然后你就会得到你想要的，你只要简单运行就可以了。你可以增加许多的 镜像（image），这些 镜像（image）只会记录改变的地方。




cnetos7安装docker V1.0

rpm -Uvh http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm
 
yum -y install docker-io                       # 仅此一条命令就可以搞定；
 
service docker start                           # 启动docker
chkconfig docker on                            # 加入开机启动
 
docker pull centos:latest                      #从docker.io中下载centos镜像到本地 /var/lib/docker/graph
 
docker images                                  #查看已下载的镜像
docker run -i -t centos /bin/bash              #启动一个容器
 
docker imr image_id                            #删除镜像
docker rmi $(docker images | grep none | awk '{print $3}' | sort -r)   #删除所有镜像
docker ps -a                                   #查看所有容器(包括正在运行和已停止的)
 
docker start container                         #开启一个容器（注意container_id和image_id是完全不一样de）
 
docker logs <容器名orID> 2>&1 | grep '^User: ' | tail -n1 #查看容器的root用户密码,因为docker容器启动时的root用户的密码是随机分配的。所以，通过这种方式就可以得到redmine容器的root用户的密码了
 
 
docker logs -f <容器名orID>                     #查看容器日志
 
 
docker rm $(docker ps -a -q)                    #删除所有容&删除单个容器docker rm <容器名orID>
 
docker run --name redmine -p 9003:80 -p 9023:22 -d -v /var/redmine/files:/redmine/files -v     /var/redmine/mysql:/var/lib/mysql sameersbn/redmine
                                                 #运行一个新容器，同时为它命名、端口映射、文件夹映射。以redmine镜像为例
 
 
docker
 run -i -t --name sonar -d -link mmysql:db   tpires/sonar-server 
 # 一个容器连接到另一个容器&sonar容器连接到mmysql容器，并将mmysql容器重命名为db。这样，sonar容器就可以使用
db的相关的环境变量了。
 
 
#当需要把一台机器上的镜像迁移到另一台机器的时候，需要保存镜像与加载镜像。
机器a
docker save busybox-1 > /home/save.tar
使用scp将save.tar拷到机器b上，然后：
 
docker load < /home/save.tar
 
docker build -t <镜像名> <Dockerfile路径>        #构建自己的镜像

应用Docker
 
1，获取Centos镜像
 >docker pull centos:latest
 
2，查看镜像运行情况
 >docker images centos
 
3，在容器下运行 shell bash
 >docker run -i -t centos /bin/bash
 
4，停止容器
 >docker stop <CONTAINER ID>
 
5，查看容器日志
 >docker logs -f <CONTAINER ID>
 
6，删除所有容器
 >docker rm $(docker ps -a -q)
 
7，删除镜像
 >docker rmi <image id/name>
 
8，提交容器更改到镜像仓库中
 >docker run -i -t centos /bin/bash
 >useradd myuser
 >exit
 >docker ps -a |more
 >docker commit <CONTAINER ID> myuser/centos
 
9，创建并运行容器中的 hello.sh
 >docker run -i -t myuser/centos /bin/bash
 >touch /home/myuser/hello.sh
 >echo "echo \"Hello,World!\"" > /home/myuser/hello.sh
 >chmod +x /home/myuser/hello.sh
 >exit
 >docker commit <CONTAINER ID> myuser/centos
 >docker run -i -t myuser/centos /bin/sh /home/myuser/hello.sh
 
10，在容器中运行Nginx
 
在容器中安装好Nginx，并提交到镜像中
 >docker run -t -i -p 80:80 nginx/centos /bin/bash
 启动Nginx
 >/data/apps/nginx/sbin/nginx
 (还不清楚如何在后台运行!!!)
 
在浏览器访问宿主机80端口。
 
11，映射容器端口
 >docker run -d -p 192.168.9.11:2201:22 nginx/centos /usr/sbin/sshd -D
 
用ssh root@192.168.9.11 -p 2201 连接容器，提示：
 
Connection to 192.168.1.205 closed.(此问题还未解决!!!)
 

可能会遇到的问题：
 ##在容器里面修改用户密码的时候报错：
 /usr/share/cracklib/pw_dict.pwd: No such file or directory
 PWOpen: No such file or directory
 
解决：
 yum -y reinstall cracklib-dicts

开源项目Docker，Red Hat新的虚拟化选择 http://www.linuxidc.com/Linux/2013-10/91051.htm

dockerlite: 轻量级 Linux 虚拟化 http://www.linuxidc.com/Linux/2013-07/87093.htm

Docker的搭建Gitlab CI 全过程详解 http://www.linuxidc.com/Linux/2013-12/93537.htm

Docker 和一个正常的虚拟机有何区别? http://www.linuxidc.com/Linux/2013-12/93740.htm

在 Docker 中使用 MySQL http://www.linuxidc.com/Linux/2014-01/95354.htm

Docker 将改变所有事情 http://www.linuxidc.com/Linux/2013-12/93998.htm

Docker 1.0 正式版发布下载 http://www.linuxidc.com/Linux/2014-06/102941.htm