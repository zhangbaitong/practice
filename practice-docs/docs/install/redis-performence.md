1.redis.conf中maxclients 设置为0表示不作限制
2.redis的file descriptor数目限制
V2.2.4的ae.h的36行
#define AE_SETSIZE (1024*10)    /* Max number of fd supported */
3.文件打开数限制
查看：ulimit -a
修改：ulimit -n number
独立用户修改
vi /etc/rc.local
unlimit -n 65535
当前系统文件句柄的最大数目，只用于查看，不能设置修改
cat /proc/sys/fs/file-max
查看所有进程的文件打开数
lsof |wc -l
跟踪进程打开文件数
ps -ef | grep yourapp
ls -l /proc/yourpid/fd/ | wc -l
查看进程限制
cat /proc/yourpid/limits
进程子任务描述位置
/proc/yourpid/task
一个进程打开的描述符号
ll /proc/yourpid/fd/
查看关联文件的数量
lsof | grep redis | wc –l
lsof | grep 31886 | wc –l
临时修改-基于当前终端连接的session
ulimit -SHn 2048
永久修改
/etc/security/limits.conf
* hard nofile 2048
* soft nofile 2048
如果进程已经开启，特别是线上业务。如果不想重启进程的话，怎么动态修改呢？
# echo -n ‘Max open files=65535:65535’ > /proc/31886/limits  
内核参数对文件描述符也有限制，如果设置的值大于内核的限制，也是不行的：

# sysctl -a|grep file-max   //查找file-max的内核参数：
# sysctl -w file-max=65535   //更改file-max的内核参数
Sysctl也是临时的，要想永久生效，可以通过更改sysctl的文件，编辑/etc/sysctl.conf文件，添加或修改以下一行：
fs.file-max=65535
总结：
需要注意的是，文件描述符的限制，不局限于这里描述的这些，还可能和进程的启动参数、用户的环境设置有关。当然，如果是进程BUG造成文件描述符没有及时关闭回收，这增大限制也只是治标，根本上还得修复BUG。
此外，lsof会列出系统中所占用的资源,但是这些资源不一定会占用打开的文件描述符(比如共享内存,信号量,消息队列,内存映射.等,虽然占用了这些资源,但不占用打开文件号)，因此有可能出现cat /proc/sys/fs/file-max 的值小于lsof | wc -l

4.网络并发限制
vi /etc/sysctl.conf
net.ipv4.netfilter.ip_conntrack_max=393216
net.nf_conntrack_max
net.netfilter.nf_conntrack_max
降低timeout时间
net.ipv4.netfilter.ip_conntrack_tcp_timeout_established = 300
net.ipv4.netfilter.ip_conntrack_tcp_timeout_time_wait = 120
net.ipv4.netfilter.ip_conntrack_tcp_timeout_close_wait = 60
net.ipv4.netfilter.ip_conntrack_tcp_timeout_fin_wait = 120
5.修改conntrack_max 参数（2.4内核）
增大 hashsize （在i386架构上，HASHSIZE = CONNTRACK_MAX / 8）
修改：
vi /etc/modprobe.conf
options ip_conntrack hashsize=131072
重启：
service iptalbes restart
查看参数效果：
sysctl -a | grep conntrack(自动调整为哈希表大小的8倍)
不要在/etc/sysctl.conf中设置以下两项的值：
net.ipv4.netfilter.ip_conntrack_max　net.ipv4.ip_conntrack_max

5.网络状态
socket 的 FIN_WAIT_1
可能由于服务端还没有收到客户端反馈信息，客户端就已经断开了
net.ipv4.tcp_fin_timeout = 60



/etc/security/limits.conf
* hard nofile 32768
* soft nofile 32768

chkconfig --add redis

chkconfig --list

chkconfig --level 3 redis on
