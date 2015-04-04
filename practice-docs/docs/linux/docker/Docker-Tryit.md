Docker-Tryit

built

share

image

Docker Hub Registry

cloud-based collection of applications

#0
Docker引擎部分

查看Docker所有的命令参数

	docker

A:Docker的版本

	docker version

#1查找镜像

	docker search tutorial
	docker search <string>

Found 1 results matching your query ("tutorial")
NAME                      DESCRIPTION
learn/tutorial            An image for the interactive tutorial

#2下载容器镜像

	docker pull learn/tutorial
	docker pull <username>/<repository>

Pulling repository learn/tutorial from https://index.docker.io/v1
Pulling image 8dbd9e392a964056420e5d58ca5cc376ef18e2de93b5cc90e868a1bbc8318c1c (precise) from ubuntu
Pulling image b750fe79269d2ec9a3c593ef05b4332b1d1a02a62b4accb2c21d589ff2f5f2dc (12.10) from ubuntu
Pulling image 27cf784147099545 () from tutorial

#3HelloWorld
	
	docker run learn/tutorial echo "hello world"
	docker run <image name> <command>

#4安装东西(基于ubuntu的ping工具)

	apt-get install -y ping
	docker run learn/tutorial apt-get install -y ping

#5保存你得更改

	docker commit（查看commit命令参数解释）
	docker ps -l（找到你安装ping工具的容器id）

ID                  IMAGE               COMMAND                CREATED             STATUS              POR
TS
6982a9948422        ubuntu:12.04        apt-get install ping   1 minute ago        Exit 0

	docker commit 698 learn/ping
	得到一个ID为镜像的标示effb66b31edb

#6运行你新的镜像

	docker run learn/ping ping google.com

#7检查你的镜像

	docker ps(查看运行的镜像列表)
	docker inspect(查看镜像的有用信息)
	docker inspect <id>

#8推送你得镜像到docker仓库

	docker images(查看你当前host的镜像)
	docker images learn/ping
	docker push <image name>(推送你得镜像)

#9注册你得docker仓库账户

	https://hub.docker.com/account/signup/

#10安装应用和使用文档

	https://docs.docker.com/installation/

