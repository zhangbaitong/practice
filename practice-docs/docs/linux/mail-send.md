linux下命令行mail发邮件方法
1. 直接发送
	mail -s title **@mail.com
	ctrl+d
2.使用管道
　　echo “Test text” | mail -s title **@mail.com
3.重定向
	mail -s title **@mail.com < file(发送文件内容)
4.附件
	echo "sql bakeup" | mail -s title -a filename **@mail.com


eg.mysql数据库备份并发送邮件

#!/bin/sh
#get cunrrent date
date=`date +%Y%m%d`
echo $date
#dump sql file for bakeup tables with current date
mysqldump -u root dbname > ${date}-yourname.sql
#send mail to **@mail.com with sql file
echo "dbname sql bakeup" | mail -s yourname-sql-bakeup -a ${date}-yourname.sql **@mail.com

