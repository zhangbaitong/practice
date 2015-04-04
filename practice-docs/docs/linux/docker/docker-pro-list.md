docker-pro-list

docker集成测试部署之道

http://www.infoq.com/cn/articles/docker-integrated-test-and-deployment

DOCKER PAAS

1.Flynn – https://github.com/flynn

Flynn是最符合Docker PaaS的预期开源项目. 使用git push部署到Docker, Flynn简化了应用的部署和维护，没有使用复杂的配置管理系统，Flynn允许容器式部署能自我服务管理，Flynn受赞助也是很多，大概有超过14+ sponsors。

 

2. Deis – https://github.com/opdemand/deis

Deis也是使用git push 部署风格， Deis 平衡了Chef, Docker, Django, Celery, Heroku Buildpacks, 和 Slugbuilder一起发挥了魔术效果. Deis采取out-of-the-box方式支持Ruby, Python, Node.js, Java, Clojure, Scala, Play, PHP, Perl, Dart 和 Go. Also, Deis能使用Heroku Buildpacks Docker images 或 Chef recipes部署任何东西, . Deis能部署在任何系统上包括公有云 私有云等。 Deis 当前支持亚马逊的EC2、Rackspace 和 Digital Ocean的自动化provisioning

 

3. Dokku – https://github.com/progrium/dokku

如果只需要最少的git push到Docker容器，可以看看Dokku. 一个很小很强大的吸引眼球的开源项目，功能没有Deis or Flynn强大, 但是相当容易：在Ubuntu 13 or 12.04 x64.用下面命令

$ wget -qO- https://raw.github.com/progrium/dokku/v0.2.2/bootstrap.sh | sudo DOKKU_TAG=v0.2.2 bash
 

DOCKER ORCHESTRATION混合产品

4. CoreOS – https://github.com/coreos

CoreOS 能激活基于一个小型现代操作系统之上的仓库规模的计算环境。

CoreOS不是一个单个开源项目，而是一系列开源工具，需要与etcd, docker, systemd一起配置工作，可能开始使用CoreOS有点艰难， etcd库是通用的key/value存储用来协调服务一起工作，以及跨应用分享证书。

 

5. Fig – https://github.com/orchardup/fig

"使用Docker实现的快速 隔离的开发环境"

Fig让你编写一个简单的fig.yml文件列表出你的应用需要的所有Docker容器，以及它们是如何连接在一起的，一旦你编写fig.yml以后，只需要加上-d 参数运行，你的应用就开始上线运行。

 

6. Serf – https://github.com/hashicorp/serf

"一个分散式服务发现和指挥协调的解决方案，支持轻量 高可用和容错。"

尽管 Serf不是 Docker 规范,. Serf来自开发Vagrant的一帮家伙. 它和CoreOS 和 etcd是相竞争的，Serf还可以以许多不同方式运行在etcd 和 CoreOS不太灵活的地方。

 

CI/CD持续集成等

7. Drone – https://github.com/drone/drone

"一个基于Socker的持续集成平台"

Drone给你一个简单的二进制debian文件用来发布，能带来完整的CI/CD管道连接到Docker. Cool, 你的代码再也不需要在你的笔记本或公司网络中测试，大公司们也被阻止在公有环境如GitHub 和 Travis. 能部署你的充分测试环境带入生产环境，为不同地方打造相同几乎一样的环境。

Docker的管理UI

8. Shipyard – https://github.com/shipyard/shipyard
"开源 Docker管理系统

Shipyard能让你管理Docker资源，包括containers, images, hosts, 等等，提供统一的管理界面，包括：多主机支持, 容器量衡, 和一个RESTful API. 部署Shipyard, 只要输入:

$ docker run -i -t -v /var/run/docker.sock:/docker.sock shipyard/deploy setup
这样你就可以登录http://localhost:8000 ，能够可视化看到所有容器被关闭或开启。

 

9. DockerUI – https://github.com/crosbymichael/dockerui
"一个A web interface for Docker"

Docker UI是另外一个Docker容器可视化，只要输入下面命令：

$ docker run -d -p 9000:9000 -v /var/run/docker.sock:/docker.sock crosbymichael/dockerui -e /docker.sock
然后登录http://localhost:9000 ，DockerUI并不支持多主机，但是提供等同docker命令行的大部分功能。

 

其他

10. OpenStack Docker – https://github.com/dotcloud/openstack-docker

这个项目已经被迁移到OpenStack官方作为Havana发布, Docker容器与虚拟机作为第一等公民是游戏的改变者，因为OpenStack开始成为大部分IaaS的采取平台，请询问你的云计算提供商 是否首先提供Docker和CoreOS支持。

