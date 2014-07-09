##mysql

数据库备份：
mysqldump -u User --flush-logs --delete-master-logs --all-databases > alldb.sql

日志文件路径
mysql> show variables like 'general_log_file';
+------------------+------------------------------------+
| Variable_name    | Value                              |
+------------------+------------------------------------+
| general_log_file | /usr/local/mysql/data/localhost.log |
+------------------+------------------------------------+
1 row in set (0.00 sec)
错误日志文件路径
mysql> show variables like 'log_error';
+---------------+------------------------------------+
| Variable_name | Value                              |
+---------------+------------------------------------+
| log_error     | /usr/local/mysql/data/localhost.err |
+---------------+------------------------------------+
1 row in set (0.00 sec)
慢查询日志文件路径
mysql> show variables like 'slow_query_log_file';
+---------------------+-----------------------------------------+
| Variable_name       | Value                                   |
+---------------------+-----------------------------------------+
| slow_query_log_file | /usr/local/mysql/data/localhost-slow.log |
+---------------------+-----------------------------------------+
1 row in set (0.01 sec)




SELECT * FROM INFORMATION_SCHEMA.INNODB_TRX; 

可以看到一个时间持续了比较久的事务，现在时间是13点了，而这个事务的开始时间是2012-11-09 12:15:14，显然是不正常的，我们看这个事务对应的mysql的线程ID（trx_mysql_thread_id）是82230715，就是这个事务导致的 

    13:01:48pm> SELECT * FROM INFORMATION_SCHEMA.INNODB_TRX;
    +-----------+-----------+---------------------+-----------------------+------------------+------------+---------------------+-----------+---------------------+-------------------+-------------------+------------------+-----------------------+-----------------+-------------------+-------------------------+---------------------+-------------------+------------------------+----------------------------+---------------------------+---------------------------+
    | trx_id | trx_state | trx_started | trx_requested_lock_id | trx_wait_started | trx_weight | trx_mysql_thread_id | trx_query | trx_operation_state | trx_tables_in_use | trx_tables_locked | trx_lock_structs | trx_lock_memory_bytes | trx_rows_locked | trx_rows_modified | trx_concurrency_tickets | trx_isolation_level | trx_unique_checks | trx_foreign_key_checks | trx_last_foreign_key_error | trx_adaptive_hash_latched | trx_adaptive_hash_timeout |
    +-----------+-----------+---------------------+-----------------------+------------------+------------+---------------------+-----------+---------------------+-------------------+-------------------+------------------+-----------------------+-----------------+-------------------+-------------------------+---------------------+-------------------+------------------------+----------------------------+---------------------------+---------------------------+
    | 31868CED0 | RUNNING | 2012-11-09 12:15:14 | NULL | NULL | 2 | 82230715 | NULL | NULL | 0 | 0 | 2 | 376 | 1 | 0 | 0 | REPEATABLE READ | 1 | 1 | NULL | 0 | 10000 |
    +-----------+-----------+---------------------+-----------------------+------------------+------------+---------------------+-----------+---------------------+-------------------+-------------------+------------------+-----------------------+-----------------+-------------------+-------------------------+---------------------+-------------------+------------------------+----------------------------+---------------------------+---------------------------+

我们登录到mysql把它kill掉

    13:01:55pm> kill 82230715;



查看正在锁的事务
SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCKS;  
 
查看等待锁的事务
SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCK_WAITS; 



查看 mysql库和表的状态信息
查整个库的状态：
select concat(truncate(sum(data_length)/1024/1024,2),'MB') as data_size,
concat(truncate(sum(max_data_length)/1024/1024,2),'MB') as max_data_size,
concat(truncate(sum(data_free)/1024/1024,2),'MB') as data_free,
concat(truncate(sum(index_length)/1024/1024,2),'MB') as index_size
from information_schema.tables where TABLE_SCHEMA = 'databasename';

查单表：
select concat(truncate(sum(data_length)/1024/1024,2),'MB') as data_size,
concat(truncate(sum(max_data_length)/1024/1024,2),'MB') as max_data_size,
concat(truncate(sum(data_free)/1024/1024,2),'MB') as data_free,
concat(truncate(sum(index_length)/1024/1024,2),'MB') as index_size
from information_schema.tables where TABLE_NAME = 'tablename';
 
另外，详细信息可以用：
show table status from 库名;
如果已经使用过“use 库名"的命令，使用show table status是直接查的当前库
 
show status
可以查mysql的系统变量


见官方文档：http://dev.mysql.com/doc/refman/5.1/zh/storage-engines.html#innodb-lock-modes 

可直接在mysql命令行执行：show engine innodb status\G; 
查看造成死锁的sql语句，分析索引情况，然后优化sql 
然后 
show processlist; 
kill processid; 
另外可以打开慢查询日志，linux下打开需在my.cnf的[mysqld]里面加上以下内容： 
long_query_time = 2   
log-slow-queries = /usr/local/mysql/mysql-slow.log






mysql 查看当前连接数  

2009-10-27 18:09:47|  分类： mysql |举报|字号 订阅
命令： show processlist; 
如果是root帐号，你能看到所有用户的当前连接。如果是其它普通帐号，只能看到自己占用的连接。 
show processlist;只列出前100条，如果想全列出请使用show full processlist; 
mysql> show processlist;

命令： show status;

Aborted_clients 由于客户没有正确关闭连接已经死掉，已经放弃的连接数量。 
Aborted_connects 尝试已经失败的MySQL服务器的连接的次数。 
Connections 试图连接MySQL服务器的次数。 
Created_tmp_tables 当执行语句时，已经被创造了的隐含临时表的数量。 
Delayed_insert_threads 正在使用的延迟插入处理器线程的数量。 
Delayed_writes 用INSERT DELAYED写入的行数。 
Delayed_errors 用INSERT DELAYED写入的发生某些错误(可能重复键值)的行数。 
Flush_commands 执行FLUSH命令的次数。 
Handler_delete 请求从一张表中删除行的次数。 
Handler_read_first 请求读入表中第一行的次数。 
Handler_read_key 请求数字基于键读行。 
Handler_read_next 请求读入基于一个键的一行的次数。 
Handler_read_rnd 请求读入基于一个固定位置的一行的次数。 
Handler_update 请求更新表中一行的次数。 
Handler_write 请求向表中插入一行的次数。 
Key_blocks_used 用于关键字缓存的块的数量。 
Key_read_requests 请求从缓存读入一个键值的次数。 
Key_reads 从磁盘物理读入一个键值的次数。 
Key_write_requests 请求将一个关键字块写入缓存次数。 
Key_writes 将一个键值块物理写入磁盘的次数。 
Max_used_connections 同时使用的连接的最大数目。 
Not_flushed_key_blocks 在键缓存中已经改变但是还没被清空到磁盘上的键块。 
Not_flushed_delayed_rows 在INSERT DELAY队列中等待写入的行的数量。 
Open_tables 打开表的数量。 
Open_files 打开文件的数量。 
Open_streams 打开流的数量(主要用于日志记载） 
Opened_tables 已经打开的表的数量。 
Questions 发往服务器的查询的数量。 
Slow_queries 要花超过long_query_time时间的查询数量。 
Threads_connected 当前打开的连接的数量。 
Threads_running 不在睡眠的线程数量。 
Uptime 服务器工作了多少秒。