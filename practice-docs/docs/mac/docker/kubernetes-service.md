kubernetes-service

二进制文件安装
	wget https://github.com/GoogleCloudPlatform/kubernetes/releases/download/v0.6.2/kubernetes.tar.gz
	tar -zxvf kubernetes.tar.gz
	tar -zxvf kubernetes/server/kubernetes-server-linux-adm64.tar.gz
	cp kubernetes/server/bin/kube* /usr/bin
书写service自启动服务
	查看服务列表
		chkconfig --list
	服务启动文件目录
		/etc/init.d
	创建启动文件
		touch /etc/init.d/httpd(/etc/rc.d/init.d/)
		chmod 755 /etc/init.d/httpd
		vi /etc/init.d/httpd
		chkconfig --add gearmand


#!/bin/bash    
#    
# chkconfig: 2345 85 85    
# description: httpd is a web server    
# processname: httpd    
    
# source function library    
. /etc/init.d/functions    
    
    
RETVAL=0    
    
start() {    
        echo -n {1}quot;Starting httpd service: "    
        daemon /usr/local/apache2/bin/apachectl start    
        RETVAL=$?    
        echo    
}    
    
stop() {    
        echo -n {1}quot;Shutting down httpd service: "    
        daemon /usr/local/apache2/bin/apachectl stop    
        RETVAL=$?    
        echo    
}    
    
case "$1" in    
  start)    
        start    
        ;;    
  stop)    
        stop    
        ;;    
  restart|reload)    
        stop    
        start    
        ;;
  status)  
      ps -ef | grep "/usr/local/gearmand-1.1.12/sbin/gearmand"  
      ;; 

  *)    
        echo {1}quot;Usage: $0 {start|stop|restart}"    
        exit 1    
esac    
    
exit $RETVAL


/usr/lib/systemd/system/kube-apiserver.service

/etc/kubernetes/config
###
# kubernetes system config
#
# The following values are used to configure various aspects of all
# kubernetes services, including
#
#   kube-apiserver.service
#   kube-controller-manager.service
#   kube-scheduler.service
#   kubelet.service
#   kube-proxy.service

# Comma seperated list of nodes in the etcd cluster
KUBE_ETCD_SERVERS="--etcd_servers=http://192.168.0.30:4001"

# logging to stderr means we get it in the systemd journal
KUBE_LOGTOSTDERR="--logtostderr=true"

# journal message level, 0 is debug
KUBE_LOG_LEVEL="--v=0"

# Should this cluster be allowed to run privleged docker containers
KUBE_ALLOW_PRIV="--allow_privileged=false"

/etc/kubernetes/apiserver
###
# kubernetes system config
#
# The following values are used to configure the kube-apiserver
#

# The address on the local server to listen to.
KUBE_API_ADDRESS="--address=0.0.0.0"

# The port on the local server to listen on.
KUBE_API_PORT="--port=8080"

# How the replication controller and scheduler find the kube-apiserver
KUBE_MASTER="--master=192.168.0.30:8080"

# Port minions listen on
KUBELET_PORT="--kubelet_port=10250"

# Address range to use for services
KUBE_SERVICE_ADDRESSES="--portal_net=10.254.0.0/16"

# Add you own!
KUBE_API_ARGS=""

[Unit]
Description=Kubernetes API Server
Documentation=https://github.com/GoogleCloudPlatform/kubernetes

[Service]
EnvironmentFile=-/etc/kubernetes/config
EnvironmentFile=-/etc/kubernetes/apiserver
User=kube
ExecStart=/usr/bin/kube-apiserver \
            ${KUBE_LOGTOSTDERR} \
            ${KUBE_LOG_LEVEL} \
            ${KUBE_ETCD_SERVERS} \
            ${KUBE_API_ADDRESS} \
            ${KUBE_API_PORT} \
            ${KUBELET_PORT} \
            ${KUBE_ALLOW_PRIV} \
            ${KUBE_SERVICE_ADDRESSES} \
            ${KUBE_API_ARGS}
Restart=on-failure

[Install]
WantedBy=multi-user.target


/etc/kubernetes/controller-manager
###
# The following values are used to configure the kubernetes controller-manager

# defaults from config and apiserver should be adequate

# Comma seperated list of minions
KUBELET_ADDRESSES="--machines=192.168.0.30"

# Add you own!
KUBE_CONTROLLER_MANAGER_ARGS=""



[Unit]
Description=Kubernetes Controller Manager
Documentation=https://github.com/GoogleCloudPlatform/kubernetes

[Service]
EnvironmentFile=-/etc/kubernetes/config
EnvironmentFile=-/etc/kubernetes/apiserver
EnvironmentFile=-/etc/kubernetes/controller-manager
User=kube
ExecStart=/usr/bin/kube-controller-manager \
            ${KUBE_LOGTOSTDERR} \
            ${KUBE_LOG_LEVEL} \
            ${KUBELET_ADDRESSES} \
            ${KUBE_MASTER} \
            ${KUBE_CONTROLLER_MANAGER_ARGS}
Restart=on-failure

[Install]
WantedBy=multi-user.target



/etc/kubernetes/kubelet
###
# kubernetes kubelet (minion) config

# The address for the info server to serve on (set to 0.0.0.0 or "" for all interfaces)
KUBELET_ADDRESS="--address=0.0.0.0"

# The port for the info server to serve on
KUBELET_PORT="--port=10250"

# You may leave this blank to use the actual hostname
KUBELET_HOSTNAME="--hostname_override=192.168.0.30"

# Add your own!
KUBELET_ARGS=""

[Unit]
Description=Kubernetes Kubelet Server
Documentation=https://github.com/GoogleCloudPlatform/kubernetes
After=docker.socket cadvisor.service
Requires=docker.socket

[Service]
EnvironmentFile=-/etc/kubernetes/config
EnvironmentFile=-/etc/kubernetes/kubelet
ExecStart=/usr/bin/kubelet \
            ${KUBE_LOGTOSTDERR} \
            ${KUBE_LOG_LEVEL} \
            ${KUBE_ETCD_SERVERS} \
            ${KUBELET_ADDRESS} \
            ${KUBELET_PORT} \
            ${KUBELET_HOSTNAME} \
            ${KUBE_ALLOW_PRIV} \
            ${KUBELET_ARGS}
Restart=on-failure

[Install]
WantedBy=multi-user.target


/etc/kubernetes/proxy
###
# kubernetes proxy config

# default config should be adequate

# Add your own!
KUBE_PROXY_ARGS=""

[Unit]
Description=Kubernetes Kube-Proxy Server
Documentation=https://github.com/GoogleCloudPlatform/kubernetes

[Service]
EnvironmentFile=-/etc/kubernetes/config
EnvironmentFile=-/etc/kubernetes/proxy
ExecStart=/usr/bin/kube-proxy \
            ${KUBE_LOGTOSTDERR} \
            ${KUBE_LOG_LEVEL} \
            ${KUBE_ETCD_SERVERS} \
            ${KUBE_PROXY_ARGS}
Restart=on-failure

[Install]
WantedBy=multi-user.target

/etc/kubernetes/scheduler
###
# kubernetes scheduler config

# default config should be adequate

# Add your own!
KUBE_SCHEDULER_ARGS=""

[Unit]
Description=Kubernetes Scheduler Plugin
Documentation=https://github.com/GoogleCloudPlatform/kubernetes

[Service]
EnvironmentFile=-/etc/kubernetes/config
EnvironmentFile=-/etc/kubernetes/apiserver
EnvironmentFile=-/etc/kubernetes/scheduler
User=kube
ExecStart=/usr/bin/kube-scheduler \
            ${KUBE_LOGTOSTDERR} \
            ${KUBE_LOG_LEVEL} \
            ${KUBE_MASTER} \
            ${KUBE_SCHEDULER_ARGS}
Restart=on-failure

[Install]
WantedBy=multi-user.target