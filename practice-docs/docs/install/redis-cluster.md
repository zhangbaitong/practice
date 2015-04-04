redis-cluster


http://redis.io/documentation

http://redis.io/topics/cluster-tutorial
http://redis.io/topics/cluster-spec

a pragmatic approach to distribution
http://redis.io/presentation/Redis_Cluster.pdf

版本变更记录
https://raw.githubusercontent.com/antirez/redis/3.0/00-RELEASENOTES

github代码
https://github.com/antirez/redis

https://github.com/antirez/redis/archive/3.0.0-rc1.tar.gz
http://download.redis.io/redis-stable.tar.gz

中文网
http://www.redis.cn/

练习
http://try.redis.io/

安装
$ wget https://github.com/antirez/redis/archive/3.0.0-rc1.tar.gz
$ tar xzf 3.0.0-rc1.tar.gz
$ cd 3.0.0-rc1
$ make

启动
$ src/redis-server

使用
$ src/redis-cli
redis> set foo bar
OK
redis> get foo
"bar"


基于docker的顶级centos镜像创建redis集群

运行容器并进入shell
	docker run -d -i --name mytemp centos /bin/bash
	docker exec -it mytemp /bin/bash
安装配置环境
	cd /root
	yum install wget -y（获取redis发行包）
	yum install tar -y（获取解压软件）
	yum install make -y（获取编译命令）
	yum install gcc cc -y（获取编译器）
	yum install ruby -y（获取ruby环境）
	gem install redis（在ruby中安装redis库）
安装redis
	wget https://github.com/antirez/redis/archive/3.0.0-rc1.tar.gz
	tar xzf 3.0.0-rc1.tar.gz
	cd 3.0.0-rc1
	make(make distclean解决编译中途出现错误需要重新编译)
启动redis
	src/redis-server
测试redis
	src/redis-cli
	set foo bar
	get foo
	keys *
	flushall
清理环境
	ps -ef|grep redis
	kill pid
	rm dump.rdb
配置cluster
	vi redis.conf
	cluster-enabled yes(打开cluster)
	cluster-config-file nodes.conf（指定cluster的配置信息，nodeID，状态，slots信息）
	cluster-node-timeout 5000（官方设置的超时时间）
	appendonly yes（持久化追加方式）
制作docker镜像
	docker commit cid rediscluster/centos
从镜像启动容器并进入shell
	docker run -d -i --name node1 rediscluster/centos /bin/bash（需要启动6个）
	docker exec -it node1 /bin/bash
查看相关IP
	boot2docker ip(ifconfig -a)（docker宿主机ip）
	docker inspect node1（容器IP）（有6个，递增）
得到IP列表
	host : 172.17.42.1
	node1: 172.17.0.14
	node2: 172.17.0.15
	node3: 172.17.0.16
	node4: 172.17.0.17
	node5: 172.17.0.18
	node6: 172.17.0.19
启动cluster-node
	src/redis-server ./redis.conf &（启动6个）
创建cluster（登陆其中一个node）
	单主集群
	src/redis-trib.rb create --replicas 0 172.17.0.14:6379 172.17.0.15:6379 172.17.0.16:6379
	主从集群
	src/redis-trib.rb create --replicas 1 172.17.0.14:6379 172.17.0.15:6379 172.17.0.16:6379 172.17.0.17:6379 172.17.0.18:6379 172.17.0.19:6379
使用cluster
	src/redis-cli -c -p 6379
重新切分
	./redis-trib.rb reshard 127.0.0.1:7000（任意节点即可，自动发现其他，之后会询问总slots数，移动多少个等问题）
查看nodes信息
	redis-cli -p 7000 cluster nodes | grep myself
测试node健康信息
	./redis-trib.rb check 127.0.0.1:7000
关闭一个node
	redis-cli -p 7002 debug segfault
failover（故障转移）
添加一个新node
	./redis-trib.rb add-node 127.0.0.1:7006 127.0.0.1:7000（第一个为添加的node，第二个为集群中任意node）
	redis-cli -p 7000 cluster nodes（确认状态）
	区别其他已经在集群中的node的特点
	1.这个node没有包含数据，没有指定slots。
	2.因为它没有指定slots，它不参加slave变成master的投票。
	./redis-trib.rb reshard 127.0.0.1:7000（重新切分）
添加一个replica（副本，即从节点）
	自动分配到slave最少的master上
	./redis-trib.rb add-node --slave 127.0.0.1:7006 127.0.0.1:7000
	指定分配到master
	./redis-trib.rb add-node --slave --master-id 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e 127.0.0.1:7006 127.0.0.1:7000
	手动操作-主转从
	1.先添加一个新的master到集群
	2.转化这个master为replica
	redis 127.0.0.1:7006> cluster replicate 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e（新的master的nodeid）
	手动操作-从转主
	1.先添加一个slave
	2.转化这个slave为其他master的replica
	redis 127.0.0.1:7006> cluster replicate 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e
	验证
	redis-cli -p 7000 cluster nodes | grep slave | grep 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e
移除一个node
	移除slave-node
	./redis-trib del-node 127.0.0.1:7000 `<node-id>`（第一个为集群中任意node，第二个为移除的nid）
	移除master-node（必须为空，可以对已经存在数据的进行reshard到其他node，一般适用手动故障转移，slave-node转到其他master，然后重新reshard）
	./redis-trib del-node 127.0.0.1:7000 `<node-id>`
Replicas migration（副本迁移）
	CLUSTER REPLICATE <master-node-id>
	允许通过上面命令使任何slave转为其他master的slave
	另外支持多slave得node自动切换到没有slave的master上
	配置replica-migration-barrier（redis.conf）
	cluster-migration-barrier 1(0为不需要，一般用于开发调试，如果设置特别大则禁用这个特性)
Upgrading nodes in a Redis Cluster
	1.手动使master故障转移到它的slave节点
	2.等待转移完成
	3.更新这个节点（这个时候应该已经更新完成他的子节点）
	4.通过手动使这个节点变为master（直到所有的节点都完成升级）
Migrating（迁移） to Redis Cluster
	多key操作是个复杂情况
	redis-trib fix|import
	参考官方文档，暂时不做考虑
其他命令	
	cluster nodes
	cluster info
	cluster meet
		cluster meet ip port(将当前node告知ip上的node)
	hash slots
		cluster addslots、cluster delslots和cluster setslot
	在nodes.conf文件中会指定
		 master - 0 1415695729605 7 connected 0-5460
QA
	Either the node already knows other nodes
	清理下redis即可：dump.rdb和nodes.conf
注意事项
	节点变更会有数秒延迟
	

演示

	docker start node1
	docker exec -it node1 /bin/bash

	root/redis-3.0.0-rc1/src/redis-server 


	docker start node2
	docker exec -it node2 /bin/bash

	docker start node4
	docker exec -it node4 /bin/bash



	docker exec -it node1 /bin/bash

	docker inspect node4
	/root/redis-3.0.0-rc1/src/redis-trib.rb create --replicas 0 172.17.0.5:6379 172.17.0.3:6379 172.17.0.6:6379

