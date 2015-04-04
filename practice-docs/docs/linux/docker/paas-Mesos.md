paas-Mesos

=============================================================================================================================

使用Mesos和Marathon管理Docker集群
	http://www.jdon.com/artichect/managing-docker-clusters-using-mesos-and-marathon.html

Apache Mesos
	它能够在同样的集群机器上运行多种分布式系统类型，更加动态有效率低共享资源。提供失败侦测，任务发布，任务跟踪，任务监控，低层次资源管理和细粒度的资源共享，可以扩展伸缩到数千个节点。Mesos已经被Twitter用来管理它们的数据中心。

Marathon
　　它是一个mesos框架，能够支持运行长服务，比如web应用等。是集群的分布式Init.d，能够原样运行任何Linux二进制发布版本，如Tomcat Play等等，可以集群的多进程管理。也是一种私有的Pass，实现服务的发现，为部署提供提供REST API服务，有授权和SSL、配置约束，通过HAProxy实现服务发现和负载平衡

=============================================================================================================================

mesosphere下载链接-全部包含
  http://mesosphere.com/downloads/
  http://mesosphere.com/docs/
  framework使用
    http://mesosphere.com/docs/tutorials/
源安装
  centos6
    sudo rpm -Uvh http://repos.mesosphere.io/el/6/noarch/RPMS/mesosphere-el-repo-6-2.noarch.rpm
  centos7
    sudo rpm -Uvh http://repos.mesosphere.io/el/7/noarch/RPMS/mesosphere-el-repo-7-1.noarch.rpm
命令
  yum -y install mesos marathon chronos
mesos
    http://mesosphere.com/downloads/details/index.html#apache-mesos
      注：需要libjvm.so，zk配置/etc/mesos/zk，环境变量/etc/default/mesos, /etc/default/mesos-master and /etc/default/mesos-slave
    rpm包
      http://downloads.mesosphere.io/master/centos/6/mesos-0.21.0-1.0.centos65.x86_64.rpm
Marathon
  代码
    https://github.com/mesosphere/marathon
  docs
    https://mesosphere.github.io/marathon/docs/
    https://mesosphere.github.io/marathon/
Chronos
  代码
    https://github.com/airbnb/chronos

http://downloads.mesosphere.io/master/centos/6/mesos-0.21.0-1.0.centos65.x86_64.rpm
http://downloads.mesosphere.io/master/centos/6/mesos-0.21.0-py2.6.egg

http://downloads.mesosphere.io/master/centos/6/mesos-0.20.1-1.0.centos64.x86_64.rpm
http://downloads.mesosphere.io/master/centos/6/mesos-0.20.1-py2.6.egg


curl -O http://downloads.mesosphere.io/master/centos/6/mesos-0.20.0-1.0.centos64.x86_64.rpm

curl -O http://downloads.mesosphere.com/marathon/v0.7.5/marathon-0.7.5.tgz
$ tar xzf marathon-0.7.5.tgz

curl -O http://mirror.bit.edu.cn/apache/mesos/0.21.0/mesos-0.21.0.tar.gz
tar xzf mesos-0.21.0.tar.gz

/sbin/iptables -I INPUT -p tcp --dport 2181 -j ACCEPT
/sbin/iptables -I INPUT -p tcp --dport 2888 -j ACCEPT
/sbin/iptables -I INPUT -p tcp --dport 3888 -j ACCEPT

./bin/start --master zk://10.122.75.229:2181,10.122.75.231:2181,10.122.75.232:2181/mesos --zk zk://10.122.75.229:2181,10.122.75.231:2181,10.122.75.232:2181/marathon

curl -X POST -H "Content-Type: application/json" http://117.78.19.76:8080/v2/apps -d@Docker.json

curl -X POST -H "Content-Type: application/json" http://117.78.19.76:8080/v2/apps -d@Docker1.json

./bin/start --master zk://10.122.75.229:2181/mesos --zk zk://10.122.75.229:2181/marathon

./bin/start --master local --zk zk://localhost:2181/marathon
./bin/start --master http://10.122.75.229:5050 -h 10.122.75.229

nohup /root/code/zhangtao/marathon-0.7.5/bin/start --master zk://10.122.75.229:2181/mesos --zk zk://10.122.75.229:2181/marathon >marathon.out &

docker run -d busybox

docker run -d centos7-redis3.0-chen

tail -f /root/install.log

=============================================================================================================================

apache-mesos
  官网
    http://mesos.apache.org/
  开始使用
    https://mesos.apache.org/gettingstarted/
  docs
    https://mesos.apache.org/documentation/
  下载
    http://mesos.apache.org/downloads/

Mesos的配置
  mesos-master --help
  mesos-slave --help
参数使用方法
  1.直接传递
    --option_name=value
  2.通过环境变量（优先级高）
    MESOS_OPTION_NAME
Mesos启动
  master
    mesos-master --ip=10.122.75.229 --work_dir=/var/lib/mesos
  slave1
    mesos-slave --master=10.122.75.229:5050
  slave2
    mesos-slave --master=10.122.75.229:5050 --ip=10.122.75.231
  URL
    http://117.78.19.76:5050/
  注：外网IP失败，localhost内部可以访问而外部不可以访问

  打开端口
  /sbin/iptables -I INPUT -p tcp --dport 5050 -j ACCEPT
  /sbin/iptables -I INPUT -p tcp --dport 5051 -j ACCEPT
  /sbin/iptables -I INPUT -p tcp --dport 8080 -j ACCEPT
docker支持
  安装
    rpm -ivh http://dl.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm
    yum install docker-io -y
  启动支持docker的slave
    mesos-slave –containerizers=docker,mesos

=============================================================================================================================

入门向导
  http://mesosphere.com/docs/getting-started/

  Setting up a Mesosphere Cluster
    http://mesosphere.com/docs/getting-started/datacenter/install/
    http://mesosphere.com/docs/tutorials/
    http://mesosphere.com/docs/getting-started/datacenter/production-setup

  Setting up a Single Node Mesosphere Cluster
    http://mesosphere.com/docs/getting-started/developer/single-node-install/
  Mesosphere Packages(配置)
    http://mesosphere.com/docs/reference/packages/

  rpm -Uvh http://repos.mesosphere.io/el/6/noarch/RPMS/mesosphere-el-repo-6-2.noarch.rpm
  yum -y install mesos marathon
  rpm -Uvh http://archive.cloudera.com/cdh4/one-click-install/redhat/6/x86_64/cloudera-cdh-4-0.x86_64.rpm
  yum -y install zookeeper
  zookeeper-server-initialize --myid=1
  vi /etc/zookeeper/conf/zoo.cfg
  server.1=10.122.75.229:2888:3888
  server.2=10.122.75.231:2888:3888
  server.3=10.122.75.232:2888:3888
  zookeeper-server start
  vi /etc/mesos/zk
  zk://10.122.75.229:2181,10.122.75.231:2181/mesos
  vi /etc/mesos-master/quorum
  2
  可选
  vi /etc/mesos-master/hostname
  Disable mesos-slave service
  service mesos-slave stop
  update-rc.d -f mesos-slave remove
  service mesos-master restart
  service marathon restart
  slave(不需要marathon)
  yum -y install mesos
  vi /etc/mesos/zk
  zk://10.122.75.229:2181,10.122.75.231:2181/mesos
  可选hostname
  不启动master
  web-mesoso
  http://<master-ip>:5050
  web-marathon
  http://<master-ip>:8080
  测试，通过launch a task
  MASTER=$(mesos-resolve `cat /etc/mesos/zk`)
  mesos-execute --master=$MASTER --name="cluster-test" --command="sleep 5"

  mesos-execute --master=10.122.75.229:5050 --name="cluster-test" --command="sleep 5"
  mesos-execute --master=10.122.75.229:5050 --name="zhangtao-test" --command="" --docker_image=centos7-redis3.0-chen
  mesos-execute --master=10.122.75.229:5050 --name="zhangtao-test" --command="sleep 5" --docker_image=busybox

  mesos-execute --master=10.122.75.229:5050 --name="zhangtao-test" --command="while sleep 10; do date -u +%T; done" --docker_image=busybox
  


  #mesos-master  --zk=zk://localhost:2181/mesos --quorum=1 start
  #mesos-slave --master=zk://localhost:2181/mesos start
  #marathon --master 10.122.75.229:5050 --zk zk://10.122.75.229:2181/marathon start


  start marathon
  marathon --master zk://10.122.75.229:2181/mesos --zk zk://10.122.75.229:2181/marathon
  Setting Up And Running Marathon
  https://mesosphere.github.io/marathon/docs/
  command line flags
  https://mesosphere.github.io/marathon/docs/command-line-flags.html
  high availability
  https://mesosphere.github.io/marathon/docs/high-availability.html

  Launching a Docker Container on Mesosphere
  http://mesosphere.com/docs/tutorials/launch-docker-container-on-mesosphere/

  Automating Cluster Provisioning
  http://mesosphere.com/docs/getting-started/datacenter/automation/


问题：
yum安装marathon无法启动
  curl -O http://downloads.mesosphere.com/marathon/v0.7.5/marathon-0.7.5.tgz
  tar xzf marathon-0.7.5.tgz
  ./bin/start --master zk://10.122.75.229:2181/mesos --zk zk://10.122.75.229:2181/marathon
  配置zk的cluster
java.net.NoRouteToHostException: 没有到主机的路由
  2181，2888，3888端口没有打开
  /etc/zookeeper/conf/zoo.cfg配置的server冲突导致cluster没有真正启动
centos7安装yum install mesos marathon(没有解决)
  错误：软件包：subversion-1.8.10-1.x86_64 (WandiscoSVN)
          需要：libsasl2.so.2()(64bit)
删除大量残留容器
  docker rm $(docker ps -a -q)
如何批量杀掉关键字进程
   ps -ef|grep ku|grep -v grep|cut -c 9-15|xargs kil -9
   ps x|grep gas|grep -v grep |awk '{print $1}'|xargs kill -9
注：

在hadoop集群环境(linux系统)中最好关闭防火墙，不然会出现很多问题，例如namenode找不到datanode等。
如果不关闭防火墙，客户端使用API操作HDFS以及ZooKeeper，可能就会出现下面常见的两种异常：
1.使用API操作HDFS时会出现异常：java.net.NoRouteToHostException: No route to host
2.使用API操作ZK时会出现异常：org.apache.zookeeper.KeeperException$ConnectionLossException: KeeperErrorCode = ConnectionLoss for xxxx
 
解决方法:
使用root权限登陆后，输入关闭防火墙命令，每个运行hadoop和zk的都要关(两条命令任选一个):
/etc/init.d/iptables stop
service iptables stop


