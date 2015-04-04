MacOS-install-docker

官方安装文档
http://docs.docker.com/installation/mac/

软件集合包
https://github.com/boot2docker/osx-installer/releases

虚拟机下载地址
https://www.virtualbox.org/wiki/Downloads

中文使用手册
http://www.widuu.com/chinese_docker/userguide/usingdocker.html

安装boot2docker
bash
unset DYLD_LIBRARY_PATH ; unset LD_LIBRARY_PATH
mkdir -p ~/.boot2docker
if [ ! -f ~/.boot2docker/boot2docker.iso ]; then cp /usr/local/share/boot2docker/boot2docker.iso ~/.boot2docker/ ; fi
/usr/local/bin/boot2docker init 
/usr/local/bin/boot2docker up 
$(/usr/local/bin/boot2docker shellinit)
docker version
Last login: Fri Oct 31 22:07:38 on ttys000
zhangtaodeMBP:~ zhangtao$ bash
bash-3.2$ unset DYLD_LIBRARY_PATH ; unset LD_LIBRARY_PATH
bash-3.2$ mkdir -p ~/.boot2docker
bash-3.2$ if [ ! -f ~/.boot2docker/boot2docker.iso ]; then cp /usr/local/share/boot2docker/boot2docker.iso ~/.boot2docker/ ; fi
bash-3.2$ /usr/local/bin/boot2docker init 
Generating public/private rsa key pair.
Your identification has been saved in /Users/zhangtao/.ssh/id_boot2docker.
Your public key has been saved in /Users/zhangtao/.ssh/id_boot2docker.pub.
The key fingerprint is:
c8:76:ee:29:b0:52:d9:b8:4e:40:d1:9d:d9:f6:25:a4 zhangtao@zhangtaodeMBP
The key's randomart image is:
+--[ RSA 2048]----+
|  .. . + ..      |
|   .. + o.. .    |
|  .    .E. o     |
| .   . .  .      |
|  .  ++ S        |
|   .=..o         |
|   ..+  .        |
|  ..o .. .       |
|   o.  .o        |
+-----------------+
bash-3.2$ /usr/local/bin/boot2docker up 
Waiting for VM and Docker daemon to start...
.............................ooooooooooooooo
Started.
Writing /Users/zhangtao/.boot2docker/certs/boot2docker-vm/ca.pem
Writing /Users/zhangtao/.boot2docker/certs/boot2docker-vm/cert.pem
Writing /Users/zhangtao/.boot2docker/certs/boot2docker-vm/key.pem

To connect the Docker client to the Docker daemon, please set:
    export DOCKER_HOST=tcp://192.168.59.103:2376
    export DOCKER_CERT_PATH=/Users/zhangtao/.boot2docker/certs/boot2docker-vm
    export DOCKER_TLS_VERIFY=1

bash-3.2$ $(/usr/local/bin/boot2docker shellinit)
Writing /Users/zhangtao/.boot2docker/certs/boot2docker-vm/ca.pem
Writing /Users/zhangtao/.boot2docker/certs/boot2docker-vm/cert.pem
Writing /Users/zhangtao/.boot2docker/certs/boot2docker-vm/key.pem
bash-3.2$ docker version
Client version: 1.3.0
Client API version: 1.15
Go version (client): go1.3.3
Git commit (client): c78088f
OS/Arch (client): darwin/amd64
Server version: 1.3.0
Server API version: 1.15
Go version (server): go1.3.3
Git commit (server): c78088f
bash-3.2$ 