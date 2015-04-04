boot2docker

https://github.com/boot2docker/boot2docker

boot2docker：基于 Tiny Core Linux 的轻量级Linux发行版

boot2docker是基于 Tiny Core Linux 的轻量级Linux发行版，专为 Docker准备，完全运行于内存中，24M大小，启动仅5-6秒。ISO 可点此下载。

特性

带有AUFS的3.14.1版本的内核，Docker 0.11.1 - 使用libcontainer
容器通过磁盘自动挂载到/var/lib/docker上而永久存在
SSH 秘钥通过磁盘自动挂载而持久存在
安装

使用boot2docker管理脚本安装

OSX

$ brew update
$ brew install boot2docker

Linux/Unix

$ curl -L -O https://raw.githubusercontent.com/boot2docker/boot2docker/master/boot2docker
$ chmod +x boot2docker

手动安装

你也可以使用boot2docker.iso安装到你的虚拟机或物理主机上。请记住要给虚拟磁盘至少512M RAM，如果你想在boot2docker上做docker开发的话，或许需要更多的磁盘。

怎么使用

boot2docker附带有利用虚拟Box的启动脚本。你可以在命令行上随时的启动、停止或删除虚拟机。

初始化

$ boot2docker init
启动虚拟机

$ boot2docker up
升级boot2docker虚拟映像

$ boot2docker stop
$ boot2docker download
$ boot2docker up

http://tinycorelinux.net/

https://www.docker.io/

https://github.com/boot2docker/boot2docker/releases