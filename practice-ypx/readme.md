##条形码生成器

http://barcode.cnaidc.com/

00001-00003

code128	1	pixel	96	png

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

