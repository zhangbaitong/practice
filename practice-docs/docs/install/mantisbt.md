##安装资料参考

官方资料

http://www.mantisbt.org/

http://www.mantisbt.org/docs/master-1.2.x/en/administration_guide.html

##安装

1.下载上传：scp ~/Downloads/mantisbt-1.2.17.tar.gz root@ip:/software

2.解压：tar -xzf mantisbt-1.2.17.tar.gz 

3.移动到web目录：mv /software/mantisbt-1.2.17 bt

3.修改目录权限：chmod 755 bt

4.创建文件：vim config_inc.php

	<?php
		$g_hostname = 'localhost';
		$g_db_type = 'mysql';
		$g_database_name = 'bugtracker';
		$g_db_username = 'root';
		$g_db_password = '';
	?>

5.默认管理员：administrator / root

6.创建新的管理员，然后禁用administrator

7.删除admin目录