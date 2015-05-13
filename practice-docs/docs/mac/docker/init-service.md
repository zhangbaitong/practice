init-service

#!/bin/sh

#chkconfig: 2345 80 05 
#descrīption: service 

case $1 in

start)


;;

stop)


;;

*)


;;

esac
＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
首先拷贝一个上面的模板备用，下面解释一下
#!/bin/sh 是说明该脚本调用的shell的类型
#chkconfig: 2345 80 05 其中2345是指明服务的运行等级，80表明是系统启动时要启动第80号服务（服务号可以重复）。05表明是系统关闭要停止的服务号。
#descrīption: service  这里的service你可以随便添，但必须有
case $1 in  这里的case是个选择语句。$1是个变量，用于指代下面的start,stop等等。
然后就可以在start)下写服务启动要执行的命令了。
stop)下写的是服务停止时执行的命令。
＊）下写其他情况下的执行内容。
保存成文件名为service，然后拷贝到/etc/init.d/下，然后启动shell,输入
chkconfig --add service
服务就添加成功了。
然后你就可以用chkconfig --list查看，在服务列表里就会出现自定义的服务了









事先准备工作：源码安装apache 。安装目录为/usr/local/httpd

任务需求：
1、可通过 service httpd start|stop|status|restart 命令对服务进行控制

2、httpd服务可开机自启动

思路：
1、start、stop操作可直接调用源码安装的httpd的控制程序apachectl
2、在启动服务时，建立httpd.lock文件；停止服务时删除
3、status操作检测httpd.lock文件是否存在，存在判断服务已启动，不存在表示服务停止
4、对每个操作建立对应的函数，进行调用
5、restart操作先调用stop函数，在调用start函数
6、服务脚本的控制参数通过位置变量 $1 传入，使用case分支进行识别、执行相应的操作
7、在脚本开头添加chkconfig管理参数，定义哪个运行级别启动、服务启动优先级、服务关闭优先级（让服务开机自启动，必须添加），description服务描述，进程名

PS：看过系统已有系统服务脚本，发现那些比我写的复杂多了。原谅我刚学shell,第一次写系统服务脚本

脚本如下：

#vim /etc/init.d/httpd
#!bin/bash 
#chkconfig:2345 55 25    //运行级别、启动优先级、关闭优先级 
#processname:httpd        //进程名 
#description:source httpd server daemon  //服务描述 
prog=/usr/local/httpd/bin/apachectl      //控制程序路径 
lock=/usr/local/httpd/httpd.lock        //lock文件路径 
start(){                                //start函数 
        $prog start 
        echo "正在启动服务...."
        touch $lock 
} 
stop(){                                //stop函数 
        $prog stop 
        echo "正在停止服务...."
        rm -rf $lock 
} 
status(){                        //status函数 
        if [ -e $lock ];then
            echo "$0 服务正在运行"
        else
            echo "$0 服务已经停止"
        fi
} 
restart(){              //restart函数 
        stop 
        start          //直接调用stop、start函数， 
} 
case "$1" in        //case分支结构匹配，$1位置参数对控制参数调用 
"start") 
        start      //调用start函数 
        ;; 
"stop")            //调用stop函数 
        stop 
        ;; 
"status")            //调用status函数 
        status 
        ;; 
"restart")            //调用restart函数 
        restart 
        ;; 
*)                //其他参数就输出脚本正确用法 
        echo "用法：$0 start|stop|status|restart"
        ;; 
esac

验证：
[root@ndbA /]# service httpd start
正在启动服务....
[root@ndbA /]# service httpd status
/etc/init.d/httpd 服务正在运行
[root@ndbA /]# service httpd stop  
正在停止服务....
[root@ndbA /]# service httpd status
/etc/init.d/httpd 服务已经停止
[root@ndbA /]# service httpd stop  
httpd (no pid file) not running
正在停止服务....
[root@ndbA /]# service httpd restatus
用法：/etc/init.d/httpd start|stop|status|restart
[root@ndbA /]# service httpd restart
httpd (no pid file) not running
正在停止服务....
正在启动服务....
[root@ndbA /]#

[root@ndbA /]# chkconfig --list httpd
httpd          0:关闭  1:关闭  2:启用  3:启用  4:启用  5:启用  6:关闭
[root@ndbA /]# chkconfig  httpd off  
[root@ndbA /]# chkconfig --list httpd
httpd          0:关闭  1:关闭  2:关闭  3:关闭  4:关闭  5:关闭  6:关闭
[root@ndbA /]# chkconfig  httpd on
[root@ndbA /]# chkconfig --list httpd
httpd          0:关闭  1:关闭  2:启用  3:启用  4:启用  5:启用  6:关闭
[root@ndbA /]#