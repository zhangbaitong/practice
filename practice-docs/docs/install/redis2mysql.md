思路：编写SQL语句，把mysql数据组合成Redis协议数据流

Redis协议

http://redis.io/topics/protocol

*<args><cr><lf> 参数个数
$<len><cr><lf> 第一个参数长度
<arg0><cr><lf> 第一个参数
$<len><cr><lf> 第二个参数长度
<arg1><cr><lf> 第二个参数
... ...


SELECT CONCAT(
"*16\r\n",
'$', LENGTH(redis_cmd), '\r\n',redis_cmd, '\r\n','$', LENGTH(redis_key), '\r\n',redis_key, '\r\n',
'$', LENGTH(hkey1), '\r\n',hkey1, '\r\n','$', LENGTH(hval1), '\r\n', hval1, '\r\n'
'$', LENGTH(hkey2), '\r\n',hkey2, '\r\n','$', LENGTH(hval2), '\r\n', hval2, '\r\n'
'$', LENGTH(hkey3), '\r\n',hkey3, '\r\n','$', LENGTH(hval3), '\r\n', hval3, '\r\n'
'$', LENGTH(hkey4), '\r\n',hkey4, '\r\n','$', LENGTH(hval4), '\r\n', hval4, '\r\n'
'$', LENGTH(hkey5), '\r\n',hkey5, '\r\n','$', LENGTH(hval5), '\r\n', hval5, '\r\n'
'$', LENGTH(hkey6), '\r\n',hkey6, '\r\n','$', LENGTH(hval6), '\r\n', hval6, '\r\n'
'$', LENGTH(hkey7), '\r\n',hkey7, '\r\n','$', LENGTH(hval7), '\r\n', hval7, '\r'
)
FROM (
 SELECT
'HMSET' AS redis_cmd, CONCAT(resource_id,'_hash') AS redis_key,
'name' AS hkey1,resource_title  AS hval1,
'type' AS hkey2,resource_type_name AS hval2,
'exe' AS hkey3,resource_format AS hval3,
'page' AS hkey4,resource_page AS hval4,
'size' AS hkey5,resource_size AS hval5,
'time' AS hkey6,create_time AS hval6,
'num' AS hkey7,resource_downcount AS hval7
 FROM t_resource_info
 ) AS t

 结果

 *16\r\n$5\r\nHMSET\r\n$9\r\nNBA231058\r\n$4\r\nname\r\n$51\r\n绿色食品的定义及绿色食品标志的使用\r\n$4\r\ntype\r\n$6\r\n文本\r\n$3\r\nexe\r\n$3\r\ndoc\r\n$4\r\npage\r\n$1\r\n1\r\n$4\r\nsize\r\n$6\r\n79.5KB\r\n$4\r\ntime\r\n$19\r\n2013-05-31 08:56:59\r\n$3\r\nnum\r\n$2\r\n18\r\n


 执行下面的语句

mysql -h 10.10.3.218 -uroot -p123456 -Dtest_db --skip-column-names --raw < /usr/local/redis.sql | redis-cli --pipe
–raw: 使mysql不转换字段值中的换行符。
–skip-column-names: 使mysql输出的每行中不包含列名。

单独执行

echo -en '*3\r\n$3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n' | redis-cli --pipe
 经测试200W条数据，2分钟以内就可以完成。