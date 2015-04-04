coreos


几个重要目录

/Users/zhangtao/.vagrant.d

/Users/zhangtao/Documents/repository/docker/coreos/coreos-vagrant
查看配置文件
vi user-data

Linux黑客车库创业：服务器操作系统CoreOS颠覆互联网
http://www.csdn.net/article/2013-08-22/2816672-coreos-the-new-linux

https://coreos.com/

https://coreos.com/docs/

https://coreos.com/using-coreos/

https://github.com/coreos/docs
https://github.com/cloudcube/coreos-manual-chinese

	第二步
	https://coreos.com/docs/quickstart/

	https://coreos.com/docs/launching-containers/launching/getting-started-with-systemd/
	https://coreos.com/docs/launching-containers/launching/overview-of-systemctl
	https://coreos.com/docs/cluster-management/debugging/reading-the-system-log
	http://www.freedesktop.org/software/systemd/man/systemd.service.html
	http://www.freedesktop.org/software/systemd/man/systemd.unit.html
	http://www.freedesktop.org/software/systemd/man/systemd.target.html


	https://coreos.com/docs/launching-containers/launching/launching-containers-fleet/

	https://coreos.com/docs/launching-containers/building/getting-started-with-docker


	第三步
	https://coreos.com/blog/coreos-clustering-with-vagrant/

	第一步
	https://coreos.com/docs/running-coreos/platforms/vagrant

	VirtualBox 4.3.10以上
	Vagrant 1.6.3以上
	下载vagrant
		http://www.vagrantup.com/downloads.html
		https://dl.bintray.com/mitchellh/vagrant/vagrant_1.7.2.dmg
	Clone Vagrant Repo
		git clone https://github.com/coreos/coreos-vagrant.git
		cd coreos-vagrant
	Starting a Cluster
		 cloud-config format via the user-data file
		 set the number of machines in the cluster in config.rb

		 https://coreos.com/docs/cluster-management/setup/cloudinit-cloud-config/
		 https://coreos.com/docs/cluster-management/setup/etcd-cluster-discovery/

		Your Vagrantfile should copy your cloud-config file to /var/lib/coreos-vagrant/vagrantfile-user-data.

		if you need to update your cloud-config later on, run vagrant reload --provision to reboot your VM and apply the new file.
		....
	Single Machine

		Bringing machine 'core-01' up with 'virtualbox' provider...
		==> core-01: Box 'coreos-beta' could not be found. Attempting to find and install...
	    core-01: Box Provider: virtualbox
	    core-01: Box Version: >= 308.0.1
		You specified a box version constraint with a direct box file
		path. Box version constraints only work with boxes from Vagrant
		Cloud or a custom box host. Please remove the version constraint
		and try again.

	How to install

	/Library/WebServer/Documents

	http://stable.release.core-os.net/amd64-usr/current/coreos_production_vagrant.json

	sudo cp coreos_production_vagrant.json /Library/WebServer/Documents/amd64-usr/current/coreos_production_vagrant.json
	sudo http://stable.release.core-os.net/amd64-usr/557.0.0/coreos_production_vagrant.box

	vagrant up
	vagrant ssh core-01 -- -A

	目前没有操作，留作备用
	$ ssh-add ~/.vagrant.d/insecure_private_key
	Identity added: /Users/core/.vagrant.d/insecure_private_key (/Users/core/.vagrant.d/insecure_private_key)
	$ vagrant ssh core-01 -- -A
	CoreOS (beta)

	Shared Folder Setup

		config.vm.synced_folder ".", "/home/core/share", id: "core", :nfs => true,  :mount_options   => ['nolock,vers=3,udp']

	New Box Versions

		vagrant box update
		vagrant up

		vagrant box remove coreos-alpha vmware_fusion
		vagrant box remove coreos-alpha virtualbox

		vagrant box add coreos-alpha <path-to-box-file>

	查看box
		vagrant box list

	Using CoreOS

		check etcd
		curl -L http://127.0.0.1:4001/v1/keys/message -d value="Hello world"
		curl -L http://127.0.0.1:4001/v1/keys/message

		run docker

		Process Management with fleet

			fleetctl

				Then load and start the unit:

				$ fleetctl load hello.service
				Unit hello.service loaded on 8145ebb7.../172.17.8.105
				$ fleetctl start hello.service
				Unit hello.service launched on 8145ebb7.../172.17.8.105

				fleetctl status hello.service

				fleetctl destroy hello.service



https://github.com/coreos


deis
Open-Source PaaS built on CoreOS

memsql
CoreOS powers a 107 machine cluster for automated testing

rackspace
CoreOS orchestrates and provisions machines on the OnMetal platform

modcloth
Utilizes Quay.io for private container hosting


A Minimal Operating System

	Booting CoreOS via PXE
	https://coreos.com/docs/running-coreos/bare-metal/booting-with-pxe/

	Booting CoreOS via iPXE
	https://coreos.com/docs/running-coreos/bare-metal/booting-with-ipxe/

	https://coreos.com/using-coreos/

Painless Updating

	https://coreos.com/using-coreos/updates

Docker Containers

	https://coreos.com/using-coreos/docker

Clustered By Default
	
	Cluster-Level Container Deployment with fleet
		https://coreos.com/blog/cluster-level-container-orchestration/

	https://coreos.com/docs/#cluster-management

Distributed Systems Tools

	https://coreos.com/docs/#distributed-configuration

Service Discovery

	https://coreos.com/docs/distributed-configuration/getting-started-with-etcd/
	
	https://coreos.com/docs/distributed-configuration/etcd-api/
	etcdctl set /message Hello
	etcdctl get /message
	etcdctl rm /message

	curl -L -X PUT http://127.0.0.1:4001/v2/keys/message -d value="Hello"
	curl -L http://127.0.0.1:4001/v2/keys/message
	curl -L -X DELETE http://127.0.0.1:4001/v2/keys/message

		Reading and Writing from Inside a Container
			curl -L http://172.17.42.1:4001/v2/keys/
			curl -L http://10.1.42.1:4001/v2/keys/
			ETCD_ENDPOINT="$(ifconfig docker0 | awk '/\<inet\>/ { print $2}'):4001"

	https://coreos.com/using-coreos/etcd
	https://coreos.com/docs/distributed-configuration/etcd-api/
	https://github.com/coreos/etcd
	https://github.com/coreos/etcd/blob/master/Documentation/libraries-and-tools.md


http://dockerone.com/article/66
从头到尾在CoreOS上构建你的第一个应用
	fleetctl 和 etcdctl 是原生的控制CoreOS集群的工具
		$ brew install go etcdctl $ git clone https://github.com/coreos/fleet.git 
		$ cd fleet 
		$ ./build 
		$ mv bin/fleetctl /usr/local/bin/
	安装一个本地的CoreOS集群，并运行
		Vagrant是非常简单的(启动三个)
			$ git clone https://github.com/CenturyLinkLabs/coreos-vagrant $ cd coreos-vagrant/cluster 
			$ vagrant up --provision 
	fleetctl来检查
		$ fleetctl list-machines MACHINE IP METADATA 
		09fd0a88... 10.0.2.15 - 
		77763947... 10.0.2.15 - 
		f31c383c... 10.0.2.15 - 
	使用fleet在CoreOS集群上部署应用
		通过YAML来生成service文件，然后用fleet部署
			$ sudo gem install bundler fig2coreos 
 
			$ cat fig.yml 
			web: 
			image: ctlc/wordpress 
			ports: 
			- 80:80 
			environment: 
			DB_USER: root 
			DB_PASSWORD: qa1N76pWAri9 
			links: 
			- db 
			db: 
			image: ctlc/mysql 
			ports: 
			- 3306:3306 
			environment: 
			MYSQL_DATABASE: wordpress 
			MYSQL_ROOT_PASSWORD: qa1N76pWAri9 
			 
			$ fig2coreos myapp fig.yml coreos-files 
			 
			$ cd coreos-files 
			 
			$ ls 
			db-discovery.1.service 
			db.1.service 
			web-discovery.1.service 
			web.1.service  
	部署（通过etcd）
		$ fleetctl start db.1.service 
		 
		$ fleetctl list-units 
		UNIT        LOAD    ACTIVE  SUB DESC        MACHINE 
		db.1.service    loaded  active  running Run db_1    9c008961.../10.0.2.15 
		 
		$ fleetctl start web.1.service 
		 
		$ fleetctl list-units 
		UNIT        LOAD    ACTIVE  SUB DESC        MACHINE 
		db.1.service    loaded  active  running Run db_1    9c008961.../10.0.2.15 
		web.1.service   loaded  active  running Run web_1   9c008961.../10.0.2.15 

	现在你的程序运行起来了，但是服务还没有注册到etcd。幸运的是，fig2coreos已经为我们自动生成服务文件。
		$ fleetctl start db-discovery.1.service 
		$ fleetctl start web-discovery.1.service 
		 
		$ fleetctl list-units 
		UNIT            LOAD    ACTIVE  SUB DESC        MACHINE 
		db-discovery.1.service  loaded  active  running Announce db_1   9c008961.../10.0.2.15 
		db.1.service        loaded  active  running Run db_1    9c008961.../10.0.2.15 
		web-discovery.1.service loaded  active  running Announce web_1  9c008961.../10.0.2.15 
		web.1.service       loaded  active  running Run web_1   9c008961.../10.0.2.15 
		 
		$ etcdctl ls --recursive 
		/services 
		/services/web 
		/services/web/web_1 
		/services/db 
		/services/db/db_1 
		 
		$ etcdctl get /services/web/web_1 
		{ "host": "core-03", "port": 80, "version": "52c7248a14" } 
		 
		$ etcdctl get /services/db/db_1 
		{ "host": "core-03", "port": 3306, "version": "52c7248a14" } 
	部署完成
	就这样，搞定了。在Vagrant 1.5使用Vagrant Cloud账号，你可以访问你的WordPress应用。如下:
		$ cd ~/coreos-vagrant/cluster/ 
		find out which box is hosting your port 80 
		$ etcdctl get /services/web/web_1 
		{ "host": "core-03", "port": 80, "version": "52c7248a14" } 
		 
		$ vagrant share core-03 --http 80 
		==> core-03: Detecting network information for machine... 
		core-03: Local machine address: 192.168.65.2 
		core-03: Local HTTP port: 80 
		core-03: Local HTTPS port: disabled 
		==> core-03: Checking authentication and authorization... 
		==> core-03: Creating Vagrant Share session... 
		core-03: Share will be at: quick-iguana-4689 
		==> core-03: Your Vagrant Share is running! Name: quick-iguana-4689 
		==> core-03: URL: http://quick-iguana-4689.vagrantshare.com 
	如果你打算在生产环境使用多主机的Coreos集群。需要你在系统中增加ambassador容器。事实上，你可以通过ambassador容器连接etc服务器

http://try.dockerboard.com/#/

fig2coreos

	https://github.com/centurylinklabs/fig2coreos

	把fig.yml 自动转换成 CoreOS 格式的 systemd 配置文件

$ sudo gem install fig2coreos
$ fig2coreos wordpress-app fig.yml coreos-dir
$ cd coreos-dir && vagrant up

https://www.tutum.co/
The Docker Platform for Dev and Ops
Build, deploy, and manage your apps across any cloud


使用 Serf 来实现 Docker 容器的自省，但是 在 CoreOS 容器中运行的 Serf 都可被 etcd 替换。

serf在容器内部，etcd在容器外部

Flynn
	https://flynn.io/
Deis
	http://deis.io/

beego 是一个快速开发 Go 应用的 HTTP 框架，他可以用来快速开发 API、Web 及后端服务等各种应用，是一个 RESTful 的框架，主要设计灵感来源于 tornado、sinatra 和 flask 这三个框架，但是结合了 Go 本身的一些特性（interface、struct 嵌入等）而设计的一个框架。
http://beego.me/docs/quickstart/new.md

Record and share your terminal sessions, the right way.
https://asciinema.org/

谢公屐
http://www.xiegongji.cn/

js云标签
http://www.17sucai.com/pins/demoshow/1228
