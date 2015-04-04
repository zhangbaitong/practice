#常见操作

查看所有
KEYS *

查看当前配置
CONFIG GET *

删除当前数据库key
flushdb

删除所有数据库key
flushall

清除缓存
dbsize
flushall


// 分片信息 
List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
JedisShardInfo si = new JedisShardInfo("localhost", 6379);
si.setPassword("foobared");
shards.add(si);
si = new JedisShardInfo("localhost", 6380);
si.setPassword("foobared");
shards.add(si);
 
// 池对象 
ShardedJedisPool pool = new ShardedJedisPool(new Config(), shards);
 
// 开始使用 
ShardedJedis jedis = pool.getResource();
jedis.set("a", "foo");
.... // do your work here
pool.returnResource(jedis);
 
.... // a few moments later
ShardedJedis jedis2 = pool.getResource();
jedis.set("z", "bar");
pool.returnResource(jedis);
pool.destroy();





集群
Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//Jedis Cluster will attempt to discover cluster nodes automatically
jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
JedisCluster jc = new JedisCluster(jedisClusterNodes);
jc.set("foo", "bar");
String value = jc.get("foo");