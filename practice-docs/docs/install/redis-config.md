redis-config

redis使用中文翻译文档
http://redis.readthedocs.org/en/latest/

redis通用配置

#GENERAL  
daemonize no  
tcp-backlog 511  
timeout 0  
tcp-keepalive 0  
loglevel notice  
databases 16  
dir /opt/redis/data  
slave-serve-stale-data yes  
#slave只读  
slave-read-only yes  
#not use default  
repl-disable-tcp-nodelay yes  
slave-priority 100  
#打开aof持久化  
appendonly yes  
#每秒一次aof写  
appendfsync everysec  
#关闭在aof rewrite的时候对新的写操作进行fsync  
no-appendfsync-on-rewrite yes  
auto-aof-rewrite-min-size 64mb  
lua-time-limit 5000  
#打开redis集群  
cluster-enabled yes  
#节点互连超时的阀值  
cluster-node-timeout 15000  
cluster-migration-barrier 1  
slowlog-log-slower-than 10000  
slowlog-max-len 128  
notify-keyspace-events ""  
hash-max-ziplist-entries 512  
hash-max-ziplist-value 64  
list-max-ziplist-entries 512  
list-max-ziplist-value 64  
set-max-intset-entries 512  
zset-max-ziplist-entries 128  
zset-max-ziplist-value 64  
activerehashing yes  
client-output-buffer-limit normal 0 0 0  
client-output-buffer-limit slave 256mb 64mb 60  
client-output-buffer-limit pubsub 32mb 8mb 60  
hz 10  
aof-rewrite-incremental-fsync yes  


redis特殊配置

#包含通用配置  
include /opt/redis/redis-common.conf  
#监听tcp端口  
port 6379  
#最大可用内存  
maxmemory 100m  
#内存耗尽时采用的淘汰策略:  
# volatile-lru -> remove the key with an expire set using an LRU algorithm  
# allkeys-lru -> remove any key accordingly to the LRU algorithm  
# volatile-random -> remove a random key with an expire set  
# allkeys-random -> remove a random key, any key  
# volatile-ttl -> remove the key with the nearest expire time (minor TTL)  
# noeviction -> don't expire at all, just return an error on write operations  
maxmemory-policy allkeys-lru  
#aof存储文件  
appendfilename "appendonly-6379.aof"  
#不开启rdb存储,只用于添加slave过程  
dbfilename dump-6379.rdb  
#cluster配置文件(启动自动生成)  
cluster-config-file nodes-6379.conf  
#部署在同一机器的redis实例，把auto-aof-rewrite搓开，因为cluster环境下内存占用基本一致.  
#防止同意机器下瞬间fork所有redis进程做aof rewrite,占用大量内存(ps:cluster必须开启aof)  
auto-aof-rewrite-percentage 80-100  
