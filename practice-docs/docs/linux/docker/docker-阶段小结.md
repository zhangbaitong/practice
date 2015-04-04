docker-阶段小结

2014-1-5书写

1.docker基础应用
	易名片分解
		web
		nodejs
		worker
		redis
		mysql
	测试宿主机运行一个月
2.相关生态研究
	docker编排服务
		swarm
			基于centos7,docker1.4
			根据minions的负载自动分配容器
			提供相关类docker的api使用和查询
	dockerui
		单纯ui
		过于简单
	shipyard
		ui
		管理host，容器，镜像
		基于coreos
	deis
		ui
		基于coreos
	flynn
	fig
	panamax
3.mesos+marathon
	偏向于分布式任务分发系统
	非docker容器专业定制
	不直观，没有复杂管理
	能执行命令（提供restfulapi，json数据）
	问题：容器相关命令不能执行
4.kubernetes
	容错性能好
	分布式扩展简单
	监控齐全
	概念和操作都非常简单清晰
	偏向于基于分布式的使用
	实践小结
		学习
		实践
		踩雷
		部署
			1pod（但失去了HA能力）
			npod（不推荐，因为分发采用轮询不可控）
		服务暴漏
			同一主机多个POD（通过服务交互）
			多个主机同一POD（通过VIP使用统一服务）（是否专门为GCE适配，没有求证）
5.registry
	搭建私有镜像仓库
	提供查询接口



结论：

在docker整个生态体系里，发现了很多开源的项目，而且很活跃。
整体环境都处在发展阶段，且不说以后，就目前来看，能够在容器管理上有很好发挥的是apache的mesos和google的kubernetes。
上面描述过大概的特点，最终还不是很适合在具体环境中应用。
后续会继续跟进kubernetes的发展，重点放在docker技术部分。
侧重关注轻量级的管理工具，比如fig。

然后会在开发和测试环境进行具体的实践，敬请期待！