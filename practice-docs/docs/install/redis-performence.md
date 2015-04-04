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