http://gitblit.com/

参考文章：

http://gitblit.com/setup_go.html

1.下载安装文件

http://dl.bintray.com/gitblit/releases/gitblit-1.6.2.tar.gz

2.修改配置文件

vi data/gitblit.properties

server.httpPort=8080
server.httpsPort
server.storePassword(不支持#)
git.packedGitLimit（仓库大小限制）

3.设置认证信息

./authority.sh

3.启动

./gitblit.sh

4.访问

 http://localhost:8080
 https://localhost:8443

 5.管理员登陆

 admin/admin

 6.数据位置

 --baseFolder
 指定目录的下级目录data