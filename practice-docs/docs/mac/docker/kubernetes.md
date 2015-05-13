kubernetes

版本更新介绍
	https://github.com/GoogleCloudPlatform/kubernetes/releases

安装包介绍
	https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/binary_release.md
下载发行版
	https://github.com/GoogleCloudPlatform/kubernetes/releases
开始入门文档
	https://github.com/GoogleCloudPlatform/kubernetes/tree/master/docs/getting-started-guides
基于docker的安装指南
	https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/local_docker.md
linux安装指南
	https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/locally.md
linux手动安装配置指南
	https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/fedora/fedora_manual_config.md
官方网站
	http://kubernetes.io/
wiki
	https://github.com/GoogleCloudPlatform/kubernetes/wiki
Kubernetes-Community
	https://github.com/GoogleCloudPlatform/kubernetes/wiki/Kubernetes-Community
Deploying Kubernetes on CoreOS with Fleet and Flannel
	https://github.com/kelseyhightower/kubernetes-fleet-tutorial
kubernetes docs
	https://github.com/GoogleCloudPlatform/kubernetes/tree/master/docs
	https://godoc.org/github.com/GoogleCloudPlatform/kubernetes/pkg/api

查看ipatables映射
	iptables -nvL -t nat
linux安装指南
	安装docker
		yum install docker
		service docker start
	安装etcd
		curl -L  https://github.com/coreos/etcd/releases/download/v0.4.6/etcd-v0.4.6-linux-amd64.tar.gz -o etcd-v0.4.6-linux-amd64.tar.gz
		tar xzvf etcd-v0.4.6-linux-amd64.tar.gz
		cd etcd-v0.4.6-linux-amd64
		./etcd
	测试etcd
		./etcdctl set mykey "this is awesome"
		./etcdctl get mykey
	设置环境变量
		vi ~/.profile
		export PATH=$PATH:/root/etcd-v0.4.6-linux-amd64
		source ~/.profile
		etcdctl
	安装go
		yum install go
	测试go
		go run hello.go
	安装kubernetes
		curl -L https://github.com/GoogleCloudPlatform/kubernetes/archive/v0.6.1.tar.gz -o v0.6.1.tar.gz
		tar xzvf v0.6.1.tar.gz
		cd kubernetes-0.6.1
		hack/local-up-cluster.sh
	使用kubernetes
		设置环境变量
			export KUBERNETES_PROVIDER=local
			export KUBERNETES_MASTER=http://localhost:8080
		运行容器
			cluster/kubecfg.sh -p 8081:80 run dockerfile/nginx 1 myNginx
			注：pods+1，replicationControllers+1
		查看docker容器状态
			docker images
			docker ps
		查看kubernetes状态
			使用cluster/kubecfg.sh脚本进行交互
				cluster/kubectl.sh get pods
				cluster/kubectl.sh get services
				cluster/kubectl.sh get replicationControllers
		问题小结
			网络问题
				1.关闭iptables
				2.修改hack/local-cluster-up.sh的portal_net
			修改kubernetes代码
				cd kubernetes
				hack/build-go.sh
				hack/local-up-cluster.sh
	用户使用文档
		https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/overview.md
	客户端工具
		PHP
			https://github.com/devstub/kubernetes-api-php-client
		GO
			https://github.com/GoogleCloudPlatform/kubernetes/tree/master/pkg/client
	kubecfg命令行工具
		docs
			https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/cli.md
		运行位置
			cluster/kubecfg.sh
		运行replication controller
			run
				kubecfg -p 8080:80 run dockerfile/nginx 2 myNginxController
			resize
				kubecfg resize myNginxController 3
			stop（通过设置数量为0实现）
				kubecfg stop myNginxController
			remove
				kubecfg rm myNginxController
		RESTful命令
			资源类型：pods，replicationControllers，services，minions
			get
				kubecfg [options] get pods/pod-abc-123
			list
				kubecfg [options] list pods
			create
				kubecfg <-c some/body.[json|yaml]> [options] create pods
			update
				kubecfg <-c some/body.[json|yaml]> [options] update pods/pod-abc-123
				滚动升级？（kubecfg -u 60s rollingupdate kube-demo）
			delete
				kubecfg [options] delete pods/pod-abc-123
		help
			kubecfg -h
	UI
		启动
			cluster/kubecfg.sh -proxy -www $PWD/www
		访问
			http://localhost:8001/static/index.html#/groups//selector
		GroupBy
			/groups/host/selector
			/groups/name/selector
			/groups/name/host/selector
		Select
			/groups/host/selector/name=frontend
		Rebuilding UX
			依赖go-bindata
				https://github.com/jteeuwen/go-bindata
			安装
				go get github.com/jteeuwen/go-bindata/...
			rebuild
				hack/build-ui.sh
	镜像相关
		https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/images.md
		仓库设置和预读取设置
	环境变量相关
		https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/container-environment.md
		容器和分布式的信息
		Hook细节，执行和实现
	日志
		使用golog实现
			https://godoc.org/github.com/golang/glog
		介绍
			https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/logging.md
		使用日志
			https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/logging.md
		使用简介
			fluentd			（http://www.fluentd.org/）
			elasticsearch	（http://www.elasticsearch.org/）
			export ENABLE_NODE_LOGGING=true
			export LOGGING_DESTINATION=elasticsearch
	监控
		cadvisor
			https://github.com/google/cadvisor
		heapster
			https://github.com/GoogleCloudPlatform/heapster
	GuestBook示例
		准备
			hack/dev-build-and-up.sh(450M)
	练习
		地址
			https://github.com/GoogleCloudPlatform/kubernetes/tree/master/examples
		step0
			https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/fedora/fedora_manual_config.md
		step1
			https://github.com/GoogleCloudPlatform/kubernetes/blob/master/examples/walkthrough/README.md
		step2
			https://github.com/GoogleCloudPlatform/kubernetes/blob/master/examples/walkthrough/k8s201.md
		step3
			https://github.com/GoogleCloudPlatform/kubernetes/tree/master/examples/guestbook
		启动
			#hack/dev-build-and-up.sh
			source ~/.profile
			export KUBERNETES_PROVIDER=local
			export KUBERNETES_MASTER=http://localhost:8080
			hack/local-up-cluster.sh
			#kubectl get minions
			#cluster/kubecfg.sh -p 8081:80 run dockerfile/nginx 1 myNginx
			cluster/kubectl.sh get pods
			cluster/kubectl.sh get services
			cluster/kubectl.sh get replicationControllers
		创建pod
			cluster/kubectl.sh create -f examples/guestbook/redis-master.json
			cluster/kubectl.sh get pods
		创建service
			cluster/kubectl.sh create -f examples/guestbook/redis-master-service.json
			cluster/kubectl.sh get services
		创建副本pods
			cluster/kubectl.sh create -f examples/guestbook/redis-slave-controller.json
			cluster/kubectl.sh get replicationControllers
			cluster/kubectl.sh get pods
		创建副本service
			cluster/kubectl.sh create -f examples/guestbook/redis-slave-service.json
			cluster/kubectl.sh get services
		条件查询service
			cluster/kubectl.sh get services -l "name=redisslave"
		创建前端pods
			cluster/kubectl.sh create -f examples/guestbook/frontend-controller.json
			cluster/kubectl.sh get pods
		访问
			http://localhost:8000
		关闭kubernetes
			cluster/kube-down.sh

注：
go环境查看
	go env
cluster/../cluster/gce/util.sh:行175: gcloud: 未找到命令
	启动replication controller才可以
systemctl stop firewall.service
service firewalld stop
docker rm $(docker ps -a -q)
docker rm -f $(docker ps -a -q)
docker save -o kubeimage.tar kubernetes/pause:go

redis-server --port 6380 --slaveof ${REDIS_MASTER_SERVICE_HOST:-$SERVICE_HOST} $REDIS_MASTER_SERVICE_PORT

查看磁盘挂载状态
	df -h

master
172.17.0.9 		10.0.0.208
slave
172.17.0.12 	10.0.0.182
php
172.17.0.13

shipyard

systemctl disable iptables-services firewalld
systemctl stop iptables-services firewalld

pod间如何通信，是否受限于单机环境

1.访问go的资源被墙
2.资源启动重复
3.容器启动端口冲突
4.镜像无法pull

先把镜像pull到本地，然后操作
docker pull brendanburns/php-redis
守护进程需要定期清理，因为每次都使用最新的
如果中途失败了，处于pending状态，controller会自动同步pending的minion机器

http://grafana.org/docs/features/influxdb/

hack/local-up-cluster.sh
+++ Building go targets for linux/amd64:
    cmd/kube-proxy
    cmd/kube-apiserver
    cmd/kube-controller-manager
    cmd/kubelet
    plugin/cmd/kube-scheduler
    cmd/kubecfg
    cmd/kubectl
    cmd/kubernetes
    cmd/e2e
    cmd/integration
+++ Placing binaries
Starting etcd
+++ etcd:
    {"action":"get","node":{"key":"/","dir":true}}
+++ apiserver:
    {
    "kind":
    "PodList",
    "creationTimestamp":
    null,
    "selfLink":
    "/api/v1beta1/pods",
    "resourceVersion":
    6,
    "apiVersion":
    "v1beta1",
    "items":
    []
    }
Local Kubernetes cluster is running. Press Ctrl-C to shut it down.

Logs: 
  /tmp/kube-apiserver.log
  /tmp/kube-controller-manager.log
  /tmp/kubelet.log
  /tmp/kube-proxy.log
  /tmp/kube-scheduler.log


/usr/bin/docker -d --selinux-enabled -H fd://
/bin/bash hack/local-up-cluster.sh
etcd -name test -data-dir /tmp/test-etcd.iUC9gV -addr 127.0.0.1:4001
/root/kubernetes-0.6.1/_output/local/bin/linux/amd64/kube-apiserver -v=3 --address=127.0.0.1 --port=8080 --etcd_servers=http://127.0
/root/kubernetes-0.6.1/_output/local/bin/linux/amd64/kube-controller-manager -v=3 --machines=127.0.0.1 --master=127.0.0.1:8080
/root/kubernetes-0.6.1/_output/local/bin/linux/amd64/kubelet -v=3 --etcd_servers=http://127.0.0.1:4001 --hostname_override=127.0.0.1
/root/kubernetes-0.6.1/_output/local/bin/linux/amd64/kube-proxy -v=3 --master=http://127.0.0.1:8080
/root/kubernetes-0.6.1/_output/local/bin/linux/amd64/kube-scheduler -v=3 --master=http://127.0.0.1:8080

docker-applyLayer /var/lib/docker/devicemapper/mnt/01bf15a18638145eb44f0363ece1845b0f0dcf24adc03700ca519ea3d5fe6b0e/rootfs

docker-proxy -proto tcp -host-ip 0.0.0.0 -host-port 8081 -container-ip 172.17.0.2 -container-port 80

  kernel.x86_64 0:3.10.0-123.13.2.el7                                                                                                                                               

更新完毕:
  glibc.x86_64 0:2.17-55.el7_0.3                            glibc-common.x86_64 0:2.17-55.el7_0.3                   glibc-devel.x86_64 0:2.17-55.el7_0.3                            
  glibc-headers.x86_64 0:2.17-55.el7_0.3                    golang.x86_64 0:1.3.3-2.el7_0                           golang-pkg-bin-linux-amd64.x86_64 0:1.3.3-2.el7_0               
  golang-pkg-linux-amd64.noarch 0:1.3.3-2.el7_0             golang-src.noarch 0:1.3.3-2.el7_0                       jasper-libs.x86_64 0:1.900.1-26.el7_0.2                         
  kernel-headers.x86_64 0:3.10.0-123.13.2.el7               kernel-tools.x86_64 0:3.10.0-123.13.2.el7               kernel-tools-libs.x86_64 0:3.10.0-123.13.2.el7                  
  mailx.x86_64 0:12.5-12.el7_0                              selinux-policy.noarch 0:3.12.1-153.el7_0.13             selinux-policy-targeted.noarch 0:3.12.1-153.el7_0.13  

已安装:
  kubernetes.x86_64 0:0.5-105.0.git3f74a1e.el7.centos                                                                                                                               

作为依赖被安装:
  cadvisor.x86_64 0:0.4.1-0.1.git6906a8ce.el7.centos                                                etcd.x86_64 0:0.4.6-3.el7.centos  


CentOS7安装kubernetes

https://github.com/GoogleCloudPlatform/kubernetes/blob/master/docs/getting-started-guides/fedora/fedora_manual_config.md

curl https://copr.fedoraproject.org/coprs/eparis/kubernetes-epel-7/repo/epel-7/eparis-kubernetes-epel-7-epel-7.repo -o /etc/yum.repos.d/eparis-kubernetes-epel-7-epel-7.repo

yum update -y

yum install kubernetes -y

zt1.cloudinfobird.cn:2233
zt2.cloudinfobird.cn:2234
zt3.cloudinfobird.cn:2235

root / zhangt@infobird

master
192.168.0.32
minion1
192.168.0.33
minion2
192.168.0.35

echo "192.168.0.32 fed-master
192.168.0.33  fed-minion" >> /etc/hosts

vi /etc/kubernetes/config
	KUBE_ETCD_SERVERS="--etcd_servers=http://fed-master:4001"

systemctl disable iptables-services firewalld
systemctl stop iptables-services firewalld

master

vi /etc/kubernetes/apiserver
	KUBE_API_ADDRESS="--address=0.0.0.0"
	KUBE_MASTER="--master=fed-master:8080"

vi /etc/kubernetes/controller-manager
	KUBELET_ADDRESSES="--machines=fed-minion"

echo "for SERVICES in etcd kube-apiserver kube-controller-manager kube-scheduler; do 
    systemctl restart \$SERVICES
    systemctl enable \$SERVICES
    systemctl status \$SERVICES 
done" >> ~/start.sh


chmod 755 start.sh
~/start.sh

minion

vi /etc/kubernetes/kubelet
	KUBELET_ADDRESS="--address=0.0.0.0"
	KUBELET_HOSTNAME="--hostname_override=fed-minion"

注：使用IP没有问题


echo "for SERVICES in kube-proxy kubelet docker; do 
    systemctl restart \$SERVICES
    systemctl enable \$SERVICES
    systemctl status \$SERVICES 
done" >> ~/start.sh

chmod 755 start.sh
~/start.sh

check minion on master
	kubectl get minions

tail -f /var/log/

scp redis-master.json root@192.168.0.32:/root

docker save -o brendanburns-php-redis.tar brendanburns/php-redis

scp dockerfile-redis.tar root@192.168.0.35:/root

docker load --input dockerfile-redis.tar

https://github.com/google/cadvisor

standalone
	cadvisor -port=4194 &
with docker(boot2docker on mac osx)
	docker run \
	  --volume=/:/rootfs:ro \
	  --volume=/var/run:/var/run:rw \
	  --volume=/sys:/sys:ro \
	  --volume=/var/lib/docker/:/var/lib/docker:ro \
	  --publish=8080:8080 \
	  --detach=true \
	  --name=cadvisor \
	  google/cadvisor:latest
with pods
	cluster/kubecfg.sh -c examples/walkthrough/pod1.yaml create pods
	cluster/kubecfg.sh list pods




https://github.com/GoogleCloudPlatform/heapster

heapster-pod.json

{
  "id": "heapsterController",
  "kind": "ReplicationController",
  "apiVersion": "v1beta1",
  "desiredState": {
    "replicas": 1,
    "replicaSelector": {"name": "heapster"},
    "podTemplate": {
      "desiredState": {
         "manifest": {
           "version": "v1beta1",
           "id": "heapsterController",
           "containers": [{
             "name": "heapster",
             "image": "kubernetes/heapster",
           }]
         }
      },
      "labels": {
        "name": "heapster",
        "uses": "influx-master"
      }
    }
  },
  "labels": {"name": "heapster"}
}


https://github.com/docker/docker-registry



