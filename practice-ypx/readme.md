##条形码生成器

http://barcode.cnaidc.com/

EAN码的构成。由代表12位数字的产品代码和1位校验码组成。
产品代码的前3位为国别码；中间4位数字为制造商号；后5位数字为产品代码。
EAN码的国别码由EAN总部分配管理。我国的国别代码为690。
制造商号代码由EAN在各国的分支机构分配管理。
我国由“中国物品编码中心”统一分配企业代码。产品代码由制造商根据规定自己编制。

目前系统使用12位码
3-4-5

产品码
690000100001
	6900001000013
690000100002
	6900001000020
690000100003
	6900001000037
690000100004
690000100005
功能码
690000199995
690000199996
690000199997
	6900001999973
690000199998
	6900001999980
690000199999
	6900001999997

扫描枪usb

##Bootstrap

	http://www.bootcss.com/
	http://v3.bootcss.com/

##amcharts

	http://www.amcharts.com/

##jquery

	http://jquery.com/

##PHP htaccess

		/etc/apache2/httpd.conf
/private/etc/apache2/httpd.conf

		/etc/apache2/users/yourname.conf
/private/etc/apache2/users/yourname.conf


RewriteBase	/~username/path


##PHP framework

http://flightphp.com/
https://github.com/mikecao/flight

web目录： 

	/Users/yourname/Sites

配置文件：

	apache

	/private/etc/apache2/users/yourname.conf

	 <Directory "/Users/yourname/Sites" >                                        
		 Options FollowSymLinks Indexes MultiViews
		 AllowOverride All
		 Order allow,deny
		 Allow from all
		 RewriteEngine On
		 RewriteCond %{REQUEST_FILENAME} !-f
		 RewriteCond %{REQUEST_FILENAME} !-d
		 RewriteRule ^(.*)$ index.php [QSA,L]
		 RewriteBase /~yourname/ypx
	 </Directory>

	 nginx

	 server {
	    location / {
		    try_files $uri $uri/ /index.php;
		}
	}

	打开调试信息
		php.ini
		display_errors = On

访问地址：

	http://localhost/~yourname/ypx/index

数据库（sqlite3）：

	http://www.w3cschool.cc/sqlite/sqlite-tutorial.html

	sqlite3 cakedb.db

	CREATE TABLE main.cake (ID INTEGER PRIMARY KEY NOT NULL,NAME TEXT NOT NULL,NUM INT NOT NULL,SUM INT NOT NULL,DATETIME timestamp);

	INSERT INTO cake (NAME,NUM,SUM,DATETIME) VALUES ('name',1,15,datetime('now'));

	datetime(CURRENT_TIMESTAMP,'localtime');

	.header on
	.mode column

	select * from cake;

	SELECT sql FROM sqlite_master WHERE type = 'table' AND tbl_name = 'cake';

	sqlite readonly(db文件没有权限)
	sqlite unable to open database file(插入时会生成临时文件，但是没有权限)

