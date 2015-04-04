lxc虚拟化快速入门

http://mageedu.blog.51cto.com/4265610/1543749

一、Cgroups

Cgroups是为Control groups的缩写，是Linux内核的一个功能，用来限制报告和隔离一个进程组的资源(CPU、内存、磁盘输入输出等)。最初由由Google的工程师(主要是Paul Menage和Rohit Seth)在2006年以“process containers(进程容器)”的名字开始的， 在2007年的晚些时候被重命名为“控制组”并被合并到了2.6.24版的内核中。

简单来讲，Cgroup是用于将一个或多个进程关联成“进程组”并统一进行的监控和管理的容器。根据监控或管理的需要，运行中的系统上很可能存在多个Cgroup，而这些Cgroup被组织成了层级结构（倒置的树状结构）。事实上，Cgroups是一种资源控制机制，其资源配置的最小单位是所谓的Subsystem——一种资源控制器，通过将资源划分为多种不同的类型（CPU、内存、磁盘输入输出等）并将这些类型的资源关联至层级结构中指定的Cgroup实现资源分配。

（待补全）

二、LXC

LXC即LinuX Containers，它是一种利用新版Linux内核的特性（Cgroups等）实现的无需hypervisor的轻型虚拟化技术。LXC能够虚拟出一个完整的系统环境（rootfs），也可以仅为单个或多个应用程序提供虚拟化运行环境。从使用方式来看，它更像是增强版的chroot环境。

（待补全）

三、CentOS 6.5上使用lxc-1.0.5

3.1 解决依赖关系

# yum install libcgroup 
# service cgconfig start

3.2 提供虚拟网桥接口

在/etc/sysconfig/network-scripts目录中新建名为ifcfg-br0的配置文件，其内容如下：

DEVICE=br0
TYPE=Bridge
BOOTPROTO=static
IPADDR=172.16.100.7
NETMASK=255.255.0.0
GATEWAY=172.16.0.1
ONBOOT=yes
DELAY=0
NM_CONTROLLED=no

接下将桥接的物理网卡（假设为eth0）关联至前面定义的桥接设备，编辑/etc/sysconfig/network-script/ifcfg-eth0为类似如下内容：

DEVICE="eth0"
BOOTPROTO="static"
NM_CONTROLLED="no"
ONBOOT="yes"
TYPE="Ethernet"
BRIDGE=br0

上述步骤无误后重启network服务即可。另外，还有其它简单的方式来实现桥接设备的创建，例如使用brctl或virsh等，这里不再详述。

3.3 安装lxc

epel源中提供的lxc版本为0.9.0，其未带centos系统模板。因此，这里选择使用目前最新的lxc版本1.0.5。编译安装过程较为简单，按其源码目录中的INSTALL文档中的提示进行即可。我们事先经过测试后已经将lxc-1.0.5制作成了适用于centos 6 x86_64平台的rpm包（通过附件下载），因此，这里将直接使用rpm命令安装。

# yum install lxc-1.0.5-1.el6.x86_64.rpm  lxc-libs-1.0.5-1.el6.x86_64.rpm

3.4 检查lxc运行环境

# lxc-checkconfig 
Kernel configuration not found at /proc/config.gz; searching...
Kernel configuration found at /boot/config-2.6.32-431.el6.x86_64
--- Namespaces ---
Namespaces: enabled
Utsname namespace: enabled
Ipc namespace: enabled
Pid namespace: enabled
User namespace: enabled
Network namespace: enabled
Multiple /dev/pts instances: enabled

--- Control groups ---
Cgroup: enabled
Cgroup namespace: enabled
Cgroup device: enabled
Cgroup sched: enabled
Cgroup cpu account: enabled
Cgroup memory controller: enabled
Cgroup cpuset: enabled

--- Misc ---
Veth pair device: enabled
Macvlan: enabled
Vlan: enabled
File capabilities: enabled

Note : Before booting a new kernel, you can check its configuration
usage : CONFIG=/path/to/config /usr/bin/lxc-checkconfig

3.5 创建centos虚拟机

lxc为创建虚拟机提供了模板文件，它们位于/usr/share/lxc/templates目录中。其中的lxc-centos即为创建lxc centos系统的模板。

另外，lxc为虚拟机提供的默认配置文件为/etc/lxc/default.conf，其中使用的桥接接口名称为virbr0，此与前面的创建的接口名称不一致，因此需要作出修改。当然，也可以将此文件复制之后进行修改，并以为作为接下来的要创建的centos虚拟机的专用配置文件。修改后的default.conf如下所示。

lxc.network.type = veth
lxc.network.link = br0
lxc.network.flags = up


创建虚拟机centos:

# lxc-create -n centos -t /usr/share/lxc/templates/lxc-centos 
Host CPE ID from /etc/system-release-cpe: cpe:/o:centos:linux:6:GA
Checking cache download in /var/cache/lxc/centos/x86_64/6/rootfs ... 
…………
…………
Complete!
Download complete.
Copy /var/cache/lxc/centos/x86_64/6/rootfs to /var/lib/lxc/centos/rootfs ... 
Copying rootfs to /var/lib/lxc/centos/rootfs ...
Storing root password in '/var/lib/lxc/centos/tmp_root_pass'
Expiring password for user root.
passwd: Success

Container rootfs and config have been created.
Edit the config file to check/enable networking setup.

The temporary root password is stored in:

        '/var/lib/lxc/centos/tmp_root_pass'


The root password is set up as expired and will require it to be changed
at first login, which you should do as soon as possible.  If you lose the
root password or wish to change it without starting the container, you
can change it from the host by running the following command (which will
also reset the expired flag):

        chroot /var/lib/lxc/centos/rootfs passwd

上述输出内容表示系统安装已经成功，可由lxc-start命令启动了。另外，目标系统的root用户的默认密码在/var/lib/lxc/centos/tmp_root_pass文件中。


3.6 启动目标系统centos：

启动lxc虚拟机需要使用lxc-start命令。此命令的常用选项有
-n NAME：要启动的虚拟机的名称
-d: 在后台运行此虚拟机
-o /path/to/somefile: 日志文件的保存位置
-l: 日志级别

简单的启动命令如下所示：
# lxc-start -n centos

启动后可直接连接至目标系统的控制台，并在显示登录提示符后登录系统即可。需要注意的是，root用户的默认密码已经被设置为过期，因此第一次登录时需要修改密码后方可使用。

也可以在lxc-start的命令后面附加-d选项，让系统运行于后台。如果要停止虚拟机，使用lxc-stop命令即可。
