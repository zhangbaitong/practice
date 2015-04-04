docker-reg

https://github.com/docker/docker-registry

http://docs.docker.com/reference/api/registry_api/

1.docker hub
	http://hub.docker.com
	使用
		docker login
		.dockercfg(本地用户目录)
		zhangbaitong/centos(用户名+镜像仓库名)
	拉镜像
		docker pull image
	推送镜像
		docker push image
	自动创建
		github
		bitbucket
2.docker pool
	http://dockerpool.com
	查看镜像
		http://www.dockerpool.com/downloads
	下载
		docker pull d1.dockerpool.com:5000/ubuntu:12.04
		docker pull dl.dockerpool.com:5000/registry
		--insecure-registry=dl.dockerpool.com:5000
	更新标签与官方同步
		docker tag d1.dockerpool.com:5000/ubuntu:12.04 ubuntu:12.04
3.registry-私有仓库
	启动
		docker run -d -p 5000:5000 registry
		(存储路径：/tmp/registry)
	启动2
		docker run -d -p 5000:5000 -v /opt/data/registry:/tmp/registry registry
		docker run -d -p 5000:5000 -v /data/zhangtao/registry:/tmp/registry registry
		docker start image-id
	下载register失败
		删除/dev/mapper下对应的磁盘和../dm-0特殊文件
	删除多了怎么办
		1.关闭docker
		2.重新yum remove，yum install
		3.reboot
	测试
		curl 127.0.0.1:5000
	管理
		重命名标签
			docker tag registry 10.122.75.228:5000/registry
			tag image:tag registryhost:port/username/name:tag
		上传
			docker push 10.122.75.228:5000/registry
		问题
			2015/01/04 21:43:37 Error: Invalid registry endpoint https://10.122.75.228:5000/v1/: Get https://10.122.75.228:5000/v1/_ping: EOF. If this private registry supports only HTTP or HTTPS with an unknown CA certificate, please add `--insecure-registry 10.122.75.228:5000` to the daemon's arguments. In the case of HTTPS, if you have access to the registry's CA certificate, no need for the flag; simply place the CA certificate at /etc/docker/certs.d/10.122.75.228:5000/ca.crt
		解决
			vi /etc/sysconfig/docker
			other_args='--insecure-registry 10.122.75.228:5000'
			docker push 10.122.75.228:5000/registry

		查看
			curl http://10.122.75.228:5000/v1/search
			curl http://10.122.75.228:5000/v1/repositories/library/centos6.6/tags
			
		下载
			docker pull 10.122.75.228:5000/registry
		改为通用标签
			docker tag 10.122.75.228:5000/registry registry
		通用适用方法
			docker --insecure-registry 10.122.75.228:5000 -d &
			是否可以考虑适用-H参数，从远程下载镜像？
4.自动推送脚本
	https://github.com/yeasy/docker_practice/blob/master/_local/push_images.sh
	registry=10.122.75.228:5000
	image=libmesos/ubuntu
	docker tag $image $registry/$image
	docker push $registry/$image
	docker rmi $registry/$image

5.registry api

	http://docs.docker.com/reference/api/registry_api/

	显示指定仓库的标签列表

		curl http://127.0.0.1:5000/v1/repositories/(namespace)/(repository)/tags

	查看默认命名空间library下的registry仓库的标签列表

		curl http://127.0.0.1:5000/v1/repositories/library/registry/tags

	删除指定仓库的标签

		curl http://127.0.0.1:5000/v1/repositories/(namespace)/(repository)/tags/(tag*) -X DELETE

		curl http://127.0.0.1:5000/v1/repositories/reynholm/help-system-server/tags/latest -X DELETE

	删除指定仓库

		curl http://127.0.0.1:5000/v1/repositories/(namespace)/(repository)/ -X DELETE

	高级查找

		curl http://127.0.0.1:5000/v1/search

		支持参数

			q 	 - 你想要查询的关键词
			n 	 - 查询结果中每页显示的数据，默认为25，最小为1，最大为100
			page - 查询结果的页码数

		curl http://127.0.0.1:5000/v1/search?q=search_term&page=1&n=25

		num_pages - 表示查询返回的总页数
		num_results - 表示查询返回的总结果数
		results - 当前页包含查询结果的列表
		page_size - 查询结果中每页包含的结果数
		query - 查询的关键词
		page - 当前页数

	状态

		检查registry是否正常工作（同时还常常用来验证registry是否支持SSL）

			curl http://127.0.0.1:5000/v1/_ping
	错误状态码含义

		200 – 正常
		400 - 无效数据
		401 – 需要验证
		404 – 标签，镜像或仓库不存在
		500 - 服务器错误

6.配置文件

	通过-e DOCKER_REGISTRY_CONFIG=/opt/registry-config/config.yml设置container的环境变量。如果不设置，则默认使用config_sample.yml
	docker run \
	-d -p 0.0.0.0:33307:22 \
	-p 0.0.0.0:5000:5000 \
	-v /opt/docker-image:/opt/docker-image \
	-e SQLALCHEMY_INDEX_DATABASE:sqlite:////opt/docker-image/docker-registry.db \
	-e STORAGE_PATH=/opt/docker-image \
	registry


	默认情况registry将会使用config_sample.yml作为配置文件来启动

	同时可以通过使用环境变量来覆盖指定的配置

	例如使用 docker run -e STORAGE_PATH=/registry 来覆盖存储路径的配置

	同时可以使用这个模板开始配置你自己的配置文件

	https://github.com/docker/docker-registry/blob/master/config/config_sample.yml

	使用自己的配置文件

	需要挂在自己配置文件所在的目录到容器中：
	-v /home/me/myfolder:/registry-conf
	同时还要设置环境变量指定使用配置文件：
	DOCKER_REGISTRY_CONFIG

	sudo docker run -p 5000:5000 -v /home/me/myfolder:/registry-conf -e DOCKER_REGISTRY_CONFIG=/registry-conf/mysuperconfig.yml registry

7.几种模式

http://docs.docker.com/reference/api/registry_api/#set-a-tag-for-a-specified-image-id
http://www.open-open.com/lib/view/open1419920701890.html
http://flux7.com/blogs/docker/docker-tutorial-series-part-4-registry-workflows/


在这篇文章中，我们将介绍一个重要的Docker 组件：Docker Registry。它是所有仓库（包括共有和私有）以及工作流的中央Registry。在我们深入Docker Registry之前，先让我们去看看一些常见的术语和仓库相关的概念。 
Repositories（仓库）可以被标记为喜欢或者像书签一样标记起来
用户可以在仓库下评论。
私有仓库和共有仓库类似，不同之处在于前者不会在搜索结果中显示，并且也没有访问它的权限。用户设置为合作者才能访问私有库。
成功推送之后配置webhooks。

Docker Registry有三个角色，分别是index、registry和registry client。 

角色 1 -- Index 

index负责并维护有关用户帐户、镜像的校验以及公共命名空间的信息。它使用以下组件维护这些信息： 
Web UI
元数据存储
认证服务
符号化

这也分解了较长的URL，以方便使用和验证库的拥有者。 

角色 2 --Registry 

registry是镜像和图表的仓库。然而，它不具有本地数据库以及不提供用户认证，由S3、云文件和本地文件系统提供数据库支持。此外，通过Index Auth service的Token的方式进行认证。Registries可以有不同的类型。我们来分析其中的几个： 
Sponsor Registry：第三方的registry，供其客户和Docker社区使用。
Mirror Registry：第三方的registry，只让客户使用。
Vendor Registry：由分发Docker镜像的供应商提供的registry。
Private Registry：通过与防火墙和额外的安全层的私有实体提供的registry。

角色 3 --Registry Client 
Docker充当registry客户端来维护推送和拉取，以及客户端的授权。 

Docker Registry工作流程详解
现在，让我们讨论五种情景模式，以便更好地理解Docker Registry。 

情景A：用户要获取并下载镜像。所涉及的步骤如下： 
用户发送请求到index来下载镜像。
index 响应返回三个相关部分信息：
该镜像位于的registry
该镜像包括所有层的校验
以授权目的Token > 注意：当请求header里有X-Docker-Token时才会返回Token。而私人仓库需要基本的身份验证，对于公有库它不是强制性的。
用户通过响应中返回的Token和registry沟通，registry全权负责镜像，它存储基本的镜像和继承的层。
registry现在要与index证实该token是被授权的。
index会发送“true” 或者 “false”给registry，由此允许用户下载所需要的镜像。

Docker入门教程（四）Docker Registry 


情景B：在用户想要推送镜像到registry中。涉及的步骤如下： 
用户发送带证书请求到index要求分配库名。
在成功认证，命名空间可用以及库名被分配之后。index响应返回临时的token。
镜像连带token，一起被推送到registry中。
registry与index证实token，然后在index验证之后开始读取推送流。
该index然后更新由Docker生成的镜像校验。

Docker入门教程（四）Docker Registry 


情景C：用户想要从index或registry中删除镜像： 
index接收来自Docker一个删除库的信号。
如果index验证库成功，它将删除该库，并返回一个临时token。
registry现在接收到带有该token的删除信号。
registry与index核实该token，然后删除库以及所有相关信息。
Docker现在通知有关删除的index，然后index移除库的所有记录。

Docker入门教程（四）Docker Registry 


情景D：用户希望在没有index的独立模式中使用registry。 
使用没有index的registry，这完全由Docker控制，它最适合于在私有网络存储镜像。registry运行在一个特殊的模式里，此模式限制了registry与Docker index的通信。所有的安全和身份验证需要用户自己注意。 

情景E：该用户想要在有index的独立模式中使用registry。 
在这种情况下，一个自定义的index会被创建在私有网络里来存储和访问镜像。然而，通知Docker有关定制的index是耗时的。 Docker提供一个有趣的概念chaining registries，从而，实现负载均衡和为具体请求而指定的registry分配。在接下来的Docker教程系列中，我们将讨论如何在上述每个情景 中使用Docker Registry API ，以及深入了解Docker Security。 





