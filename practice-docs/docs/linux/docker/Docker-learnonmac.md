Docker-learnonmac

中文参考文档
http://www.widuu.com/chinese_docker/

https://docs.docker.com/userguide/dockerlinks/

1.安装docker on mac
http://qiniu.widuu.com/Boot2Docker-1.1.1.pkg
会安装docker的同时安装virtualBox

https://docs.docker.com/installation/centos/

2.启动
直接启动boot2docker
boot2docker start(还需要配置一些东西，暂时没有处理)

3.运行
docker run ubuntu echo hello world
这样会下载ubuntu的镜像，同时在镜像里运行echo命令，输出hello world
注意：这个时候镜像会在运行后自动关闭

4.端口重定向
docker run --rm -i -t -p 80:80 ngin
boot2docker ip（查看ip）
默认192.168.59.103,但能通过virtualbox的dhcp修改

5.用户系统
boot2docker的默认用户名是docker密码是tcuser

6.命令查看
boot2docker（列出可用命令和参数）

##用户指南

1.使用Docker Hub

注册用户
	网站注册：https://hub.docker.com/account/signup/
	命令注册：docker login
	然后通过mail激活下就可以了

2.运行"hello Word"

	执行简单命令
		docker run ubuntu:14.04 /bin/echo 'Hello world'
		使用docker运行名为ubuntu:14.04的镜像，同时执行命令/bin/echo，参数为‘Hello world’
		注意：docker获得命令后容器激活，输出后容器停止
	执行交互命令
		docker run -t -i ubuntu:14.04 /bin/bash
		-t表示在新容器执行一个终端
		-i表示允许我们对容器的STDIN（标准输入）进行交互
		输入exit退出
		注：这就相当于登陆到这个镜像中操作一样了,但同样你退出后，容器就停止了
	守护进程Hello world
		docker run -d ubuntu:14.04 /bin/sh -c "while true; do echo hello world; sleep 1; done"
		-d表示后台运行模式
		docker ps(查看当前docker进程中的所有容器)
		注：这里可以看到短名的容器id和别名
		docker logs <id|name>(查看容器的标准输出内容)
		docker stop <id|name>(停止指定容器)
3.使用容器（客户端）
	客户端语法格式：[sudo] docker [flags] [command] [arguments] ..
	版本信息：docker version
	查看docker命令帮助：docker
	查看详细命令帮助:docker xxxx|docker xxxx --help

4.使用容器（在Docker中运行一个web应用）
	docker run -d -P training/webapp python app.py
	-P标示通知Docker所需的网络端口映射从主机映射到我们的容器内
	docker ps -l(查看最后一次执行的镜像容器)
	docker ps -a(查看已经停止的镜像容器)
	0.0.0.0:49155->5000/tcp
	使用-P标示。Docker中开放映射到我们主机的端口。
	这种情况下，docker开放了5000端口（默认Phtyon端口）映射到主机端口49155上。
	Docker可以配置绑定网络端口。在最后一个例子中-P标示，是-p 5000的快捷方式，-p 5000可以使端口5000映射到外部的端口(49000到49900端口)。我们也可以指定-p标示来指定端口。
	例如：docker run -d -p 5000:5000 training/webapp python app.py
	boot2docker ip（查看容器IP）
	访问：http://192.168.59.103:49155
5.使用容器（网络端口快捷方式）
	docker port（可以指定容器ID或者名字映射到主机端的端口号）
	docker port nostalgic_morse 5000
	0.0.0.0:49155
	容器的5000映射到了主机的49155
6.使用容器（查看WEB应用程序日志）
	docker logs -f nostalgic_morse
	-f表示用tail得方式查看logs

	docker top <image>（查看容器进程）
	docker inspect <image>(查看容器内容)
	docker inspect -f '{{ .NetworkSettings.IPAddress }}' <image>(查看IP)
	docker stop(停止)
	docker start|stop|restart
	docker rm <image>(删除)
	注：无法删除正在运行的镜像
7.使用docker镜像

	在主机上列出镜像
		docker images
		注：如果你不指定一个镜像的版本标签，例如你只使用Ubuntu，Docker将默认使用Ubuntu:latest镜像
	获取一个新的镜像
		docker pull（下载镜像）
	查找镜像
		docker search（也可以通过网站搜索）（starts表示关注度）（ubuntu为基础镜像，一般为docker公司提供）
	创建自己的镜像
		更新并且提交镜像
			先创建一个：docker run -t -i training/sinatra /bin/bash
			在我们的容器内添加json（我们做出的改变）：gem install json
			退出这个容器：输入exit命令
			提交这个容器：docker commit -m="Added json gem" -a="Kate Smith" 0b2616b0e5a8 ouruser/sinatra:v2
			提交这个容器：docker commit -m=这里是提交信息，可以作为版本信息 -a=允许对我们的更新制定一个用户 镜像来源 指定的镜像目标（新用户名+原有源名字+标签）
			查看刚才的镜像：docker images
			使用新镜像创建容器：docker run -t -i ouruser/sinatra:v2 /bin/bash
		使用Dockerfile创建镜像
			我们创建一个目录，并且创建一个Dockerfile
				mkdir sinatra
				cd sinatra
				touch Dockerfile
				例如：
				# This is a comment
				FROM ubuntu:14.04（告诉Docker使用哪个镜像源）
				MAINTAINER Kate Smith <ksmith@example.com>（指定谁是维护者）
				RUN apt-get -qq update（更新了APT缓存）
				RUN apt-get -qqy install ruby ruby-dev（安装了Ruby和RubyGems）
				RUN gem install sinatra
				指令格式：INSTRUCTION statement（指令前缀都必须大写）
				开始创建镜像
				docker build -t="ouruser/sinatra:v2" .
				docker build命令和-t来标示我们的新镜像，用户是ouruser、仓库源名称sinatra、标签是v2。
				如果Dockerfile在我们当前目录下，我们可以使用.来指定Dockerfile
				当所有的指令执行完成之后，我们就会得到324104cde6ad镜像（有助于标记ouruser/sinatra:v2）,然后所有中间容器会被删除干净。
				从新镜像运行一个新的容器
				docker run -t -i ouruser/sinatra:v2 /bin/bash
		设置镜像标签
			docker tag 5db5f8471261 ouruser/sinatra:devel
		向Docker Hub推送镜像
			docker push ouruser/sinatra
		主机中移除镜像
			docker rmi training/sinatra
8.连接容器
	网络端口映射
		默认绑定
		docker run -d -P training/webapp python app.py
		docker ps nostalgic_morse
		绑定指定端口
		docker run -d -p 5000:5000 training/webapp python app.py
		localhost绑定
		docker run -d -p 127.0.0.1:5000:5000 training/webapp python app.py
		动态端口
		docker run -d -p 127.0.0.1::5000 training/webapp python app.py
		绑定udp
		docker run -d -p 127.0.0.1:5000:5000/udp training/webapp python app.py
		通过port命令（有助于向我们展示特定的端口）
		docker port nostalgic_morse 5000
		127.0.0.1:49155
	Docker容器连接
		docker有一个连接系统允许将多个容器连接在一起，共享连接信息。docker连接会创建一个父子关系，其中父容器可以看到子容器的信息。
		容器命名
			docker run -d -P --name web training/webapp python app.py
			查看：
				docker ps
				docker inspect -f "{{ .Name }}" aed84ee21bde
		容器连接
			docker run -d --name db training/postgres
			创建web容器连接db容器（web是子，db是父）
			docker run -d -P --name web --link db:db training/webapp python app.py
			--link的形式：--link name:alias（name是我们连接容器的名字，alias是link的别名）
		Docker在父容器中开放子容器连接信息有两种方法
			环境变量
				docker run --rm --name web2 --link db:db training/webapp env
			更新/etc/hosts文件
				cat /etc/hosts
					172.17.0.7  aed84ee21bde
					. . .
					172.17.0.5  db
				安装ping
					apt-get install -yqq inetutils-ping
					ping db
9.管理容器数据
	数据卷
		概念：数据卷是在一个或多个容器内指定数据卷，并且绕过Union File System来持续提供一些有用的特性和共享数据。
			数据卷之间可以共享或重用容器
			可以直接更改数据卷
			更改数据卷不会被容器镜像当成更新
			体积不变一直到容器不使用它们
		添加一个数据卷
			docker run -d -P --name web -v /webapp training/webapp python app.py
			-v标识来给容器内添加一个数据卷，你可以使用-v标识多次给docker创建多个数据卷
			这会在容器内部创建一个新的卷/webapp
			注：你可以在Dockerfile中使用VOLUME指令来给创建的镜像添加一个或多个卷。
		挂载一个主机目录作为卷到容器中
			docker run -d -P --name web -v /src/webapp:/opt/webapp training/webapp python app.py
			挂载本地目录/src/webapp到容器的/opt/webapp目录。这在做测试时是非常有用的。例如我们可以挂载我们本地的源代码到容器内部，我们可以看到我们改变源代码时的应用时如何工作的。主机上的目录必须是绝对路径，如果目录不存在docker会自动创建它。
			注：这是不可以在Dockerfile的，因为Dockerfile的便携性和移植性，作为主机目录，其性质，可能使依赖这个目录的所有主机都无法工作。
			docker默认情况下是对卷有读写权限，但是我们也可以让卷只读。
			docker run -d -P --name web -v /src/webapp:/opt/webapp:ro training/webapp python app.py(ro选项限制只读)
	容器中的数据卷
		创建一个挂在数据卷的容器
			如果你想要容器之间数据共享，或者从非持久化容器中使用一些持久化数据，最好创建一个名为数据卷的容器，然后用它挂载数据。
			创建一个数据共享命名的容器
				docker run -d -v /dbdata --name dbdata training/postgres
			使用--volumes-from标识来再另外一个容器挂载/dbdata卷
				docker run -d --volumes-from dbdata --name db1 training/postgres
				另外一个
				docker run -d --volumes-from dbdata --name db2 training/postgres
				您也可以使用多个--volumes-from参数来将多个数据卷桥接到多个容器中。
				你也可以通过挂载卷来扩展链，这里挂载在dbdata容器的卷还挂载到了db1和db2容器
				docker run -d --name db3 --volumes-from db1 training/postgres
				当你删除挂载卷的dbdata容器，包括初始化数据化容器，或者随后的db1容器和db2容器，该卷将不会被删除直到没有容器使用该卷。这允许你升级，或者把有效的数据卷在容器之间迁移。
	备份、恢复或者迁移数据卷
		备份
		为此我们使用--volumes-from参数来创建一个容器，并且挂载卷
			docker run --volumes-from dbdata -v $(pwd):/backup ubuntu tar cvf /backup/backup.tar /dbdata
			启动了一个新容器并且挂载dbdata卷，我们挂载了一个本地目录作为/backup卷。最后，我们通过使用tar命令备份dbdata卷的内容到我们的/backup目录下的backup.tar文件中。当命令完成或者容器停止，我们会留下我们的dbdata卷的备份。
		恢复
			在同一容器中恢复，或者另一个你所在的容器内进行。创建一个新的容器
			docker run -v /dbdata --name dbdata2 ubuntu /bin/bash
			在新的容器的卷标中解压备份文件。
			docker run --volumes-from dbdata2 -v $(pwd):/backup busybox tar xvf /backup/backup.tar
			使用此技术进行自动备份、迁移和使用您喜欢的方式进行恢复。
10.使用Docker Hub
	Docker Hub是docker公司提供的公共注册中心，它包含了15,000个镜像，你可以下载它来创建容器。它还提供了身份验证、工作组结构、工作流工具像webhooks和创建触发器，还有私人仓库来存储你不想分享的镜像。
	Docker命令和Docker Hub
		Docker本身就提供Docker Hub服务通过使用docker search、pull、login和push命令。这个页面将向您展示如何使用这些命令。
	账号注册和登陆
		docker login
		注：你的身份验证凭证将被存储在你本地目录的.dockercfg文件中。
	搜索镜像
		docker search centos（搜素镜像使用镜像名称、用户名或者描述）
		centos和tianon/centos。第二个结果是用户仓储库，名称空间是tianon/，而第一个结构centos没有用户空间这就意味着它是可信的顶级名称空间。/字符分割用户镜像和存储库的名称。
	下载
		docker pull <imagename>
	向Docker Hub贡献
		推送镜像到Docker Hub
			docker push yourname/newimage
	Docker Hub特征
		私人仓库
			有时候你不想你的镜像公开或者分享。所以Docker Hub允许你有私有仓库。你可以登录设置它
		组织和团队
			一个私人仓库有用的地方在于你可以分享给你团队的成员或者你的组织成员。Docker Hub会告诉你如何创建组织，你可以和你的同事来管理你的私有仓库。你可以学到如何创建和管理一个组织。
		自动创建
			Docker　Hub会自动化更新和构建来自Github和BitBucket的镜像。工作原理是添加一个GitHub或者BitBucket的仓库钩子，当你推送提交的时候就会触发构建和更新。
			设置一个自动化构建
			1.创建一个Docker Hub账户并且登陆
			2.通过“Link Accounts”按钮连接你的GitHub或者BitBucket
			3.配置自动化构建
			4.选择一个Github和BitBucket项目来构建你想要构建的Dockerfile
			5.选择你想建立的分支（默认是主分支）
			6.给自动构建创建一个名称
			7.指定一个Docker标签来构建
			8.指定Dockerfile的路径，默认是/。
			一旦你配置好自动构建，他就会自动触发构建，等几分钟，你就会在Docker Hub仓库源看到你新创建的自动构建。它将会和你的Github或者BitBucket保持同步更新直到你解除自动构建。
			如果你想看到你自动化构建的状态，你可以去你的Docker Hub自动化构建页面.它将会想你展示你构建的状态和构建历史。
			一旦你创建了一个自动化构建，你可以禁用和删除它。然而，你不能通过docker push推送一个自动化构建。你只能通过在Github或者BitBucket提交你的代码来管理它。
			你可以创建多个自动化的仓库，并且配置它们只想你指定的Dockerfile或Git 分支。
			建立触发器
			你可以通过Docker Hub的Url来实现自动构建。这是满足你重新自动化构建的需求。
		Webhooks
			webhooks属于你的存储库的一部分，当一个镜像更新或者推送到你的存储库时允许你触发一个时间。当你的镜像被推送的时候，webhook可以根据你指定的url和一个有效的Json来递送。
11.官方案例
	Docker中运行Node.js web应用
		https://github.com/enokd/docker-node-hello/
		创建你的镜像
			cd dockerfile所在目录
			docker build -t zhangbaitong/centos-node-hello .
		查看镜像
			docker images
		运行
			docker run -p 49160:8080 -d <your username>/centos-node-hello
		查看日志
			docker ps
			docker logs id
		测试
			sudo apt-get install curl
			curl -i localhost:49160
	Docker中运行ssh服务容器(sshd/src/Dockerfile)
		创建镜像
			cd dockerfile所在目录
			docker build --rm -t eg_sshd .
		启动镜像
			docker run -d -P --name test_sshd eg_sshd
		映射端口
			另外：使用docker port来找出容器端口22映射到主机的端口
			docker port test_sshd 22
		ssh登陆Docker进程
			ssh登陆Docker进程的主机IP地址，端口是49154(IP地址可以使用ifconfig获取)：
			获取IP：boot2docker ip
			获得端口映射：docker port test_sshd 22
			密码：dockerfile中设置的screencast
			ssh root@192.168.59.103 -p 49153
			Permission denied (publickey,password)
		删除镜像
			停止容器：docker stop test_sshd
			删除容器：docker rm test_sshd
			删除镜像：docker rmi eg_sshd
	创建一个支持SSH的容器
		手动方式
		 	docker pull arkii/centos65
		 	docker run --name=centos6ssh -i -t arkii/centos65 /bin/bash
		 	yum install openssh-server -y
		 	vi /etc/ssh/sshd_config
		 	将其中UsePAM参数设置成“no”
		 	/etc/init.d/sshd start
		 	useradd admin
		 	echo 'admin:admin' | chpasswd
		 	安装sudo软件包
		 	yum install sudo
			visudo
			admin   ALL=(ALL)       ALL
		测试
			使用ifconfig来查看一下容器的ip
			或者docker inspect
			宿主机器上通过ssh admin@<ip>
		保存镜像
			退出容器
			docker commit <container id> <image name>
	创建一个支持SSH容器(sshd-centos/src/Dockerfile)(通过)
		docker build -t centos6-ssh .
		docker run -d -P --name=mytest centos6-ssh
		docker inspect mytest(获得容器ip)
		boot2docker ip(获得宿主机ip)
		方法1：
		ssh admin@172.17.0.38 -p 22
		方法2：
		ssh admin@192.168.59.103 -p 49154
		# 下面这两句比较特殊，在centos6上必须要有，否则创建出来的容器sshd不能登录  
		RUN ssh-keygen -t dsa -f /etc/ssh/ssh_host_dsa_key  
		RUN ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key  
	Docker中运行Reids服务
		创建镜像
			docker build -t <your username>/redis .
		运行服务
			docker run -name redis -d <your username>/redis（后台运行，但没有打开端口，使用另一个容器连接这个redis容器）
		创建你的web应用容器
			docker run --link redis:db -i -t ubuntu:14.04 /bin/bash（使用别名）
		测试
			安装redis-cli
				apt-get update
				apt-get -y install redis-server
				service redis-server stop
			查看环境变量
				env
			连接
				redis-cli -h $DB_PORT_6379_TCP_ADDR（这里使用了上面的一个变量）



============================================
启动一个后台运行的容器
	docker run -d -i ubuntu /bin/bash
	-d保证后台运行（可选）
	-i保证可以获取容器的标准输入（必选）

连接后台运行的容器
 	docker exec -t -i elegant_curie /bin/bash
 	-t保证运行时操作以终端的方式运行（可选）
 	-i保证可以获取容器的标准输入（必选）
 清理容器
 	列出所有容器记录：docker ps -a
 	删除容器：docker rm container1 container2
 	列出所有镜像：docker images
 	删除镜像：docker rmi image1 image2
 