docker-install

checkout代码

	svn co http://10.10.10.4/svn/new_workspace2012/product/guiyang/ --depth=empty
	mkdir o2omall
	svn add o2omall
	svn commit o2omall/ -m "o2omall init"

安装docker

	rpm -ivh http://dl.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm

	yum update

	yum install docker-io -y

	> /etc/sysconfig/docker

	echo "other_args='--insecure-registry 10.122.75.228:5000'" >> /etc/sysconfig/docker

	service docker start

查看镜像仓库

	curl http://10.122.75.228:5000/v1/search
	curl http://10.122.75.228:5000/v1/search?q=chen
	curl http://10.122.75.228:5000/v1/repositories/registry/images


如果你的docker不是最新的，可以通过以下方式升级：

	wget https://get.docker.com/builds/Linux/x86_64/docker-latest -O /usr/bin/docker
	wget https://get.docker.com/builds/Linux/x86_64/docker-1.3.2 -O /usr/bin/docker
	wget https://get.docker.com/builds/Linux/x86_64/docker-1.4.0 -O /usr/bin/docker
    service docker restart
