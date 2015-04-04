官方地址
	https://github.com/docker/swarm
	功能
	过滤器（命令执行node的过滤条件）
	策略（nothing docs）
	需要试下是否会故障转移，指死掉后会不会自动重启

升级最新版docker
	wget https://get.docker.com/builds/Linux/x86_64/docker-latest -O /usr/bin/docker
	wget https://get.docker.com/builds/Linux/x86_64/docker-1.3.2 -O /usr/bin/docker
	wget https://get.docker.com/builds/Linux/x86_64/docker-1.4.0 -O /usr/bin/docker
    service docker restart

安装过kubernetes之后的go环境
	/usr/bin/go
	go env

安装git
	yum install git

安装go
	yum install go

设置GO环境变量
	vi /etc/profile
	export GOPATH=$HOME/go

安装swarm
	go get -u github.com/docker/swarm

安装后命令目录
	cd /root/go/bin
	./swarm

启动node上的docker
	/usr/bin/docker -d -H 192.168.0.30:2195 &

创建cluster（master）
	swarm create
node上加入cluster（minions）
	swarm join --discovery token://d2dd8a1478bc36e21fec377a824ea07f --addr=192.168.0.30:2195
	注：这里使用node自己的IP
任意机器上启动manage（一般用master）
	swarm manage --discovery token://d2dd8a1478bc36e21fec377a824ea07f -H=192.168.0.35:2195
使用类docker命令工具
	docker -H 192.168.0.35:2195 info|run|ps|logs
查看指定cluster的nodes
	swarm list --discovery token://baf70c1358c8d79942c6182d96322a50
	http://<node_ip:2375>
