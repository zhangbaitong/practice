##Upstart


##参考资料

http://www.mike.org.cn/articles/understand-upstart/

http://manpages.ubuntu.com/manpages/quantal/en/man8/initctl.8.html

##背景介绍

	Ubuntu从6.10开始逐步用Upstart()代替原来的SysVinit进行服务进程的管理。
	RHEL(CentOS)也都从版本6开始转用Upstart代替以往的init.d/rcX.d的线性启动方式。
	UpStart(Upstart init daemon)是基于事件的启动系统，它使用事件来启动和关闭系统服务,并且是并行的。
	系统不同模式启动目录：/etc/rcX.d
	系统默认启动目录：/etc/init.d
	upstart目录:/etc/init

##简单使用

job基本用法　

initctl start|stop|restart|reload|status yourjobname(这里也可以省略掉initctl，使用连接方式)
initctl list

event基本用法

initctl emit yourevent

帮助

initctl help
man initctl

示例

description "description"
author      "ZhangBaitong"


start on runlevel [235]
stop on shutdown
respawn


script
    cd /root
    export /root
    exec php xxx.php 2>&1 >> /nohup.out
end script

###作业(job)
　　一个作业(job)是init可以理解的一系列指令。典型的指令包括一个程序(二进制文件或是脚本)和事件的名称。
　　作业又可以分为任务作业(Task)、服务作业(Services)、抽象作业(Abstact Job)。
　　任务作业是有确定的生命周期和终止状态的，运行并在执行结束后返回到等待状态的作业。例如：删除一个文件。
　　服务作业是那些长期运行的进程，例如守护进程，通常不会自己终止。比如数据库、web服务器、ftp服务器等就被实现为服务。
　　抽象作业是没有exec节或script节的作业，这样的作业仍然可以被启动和终止，但是不会被分配PID。这样作业启动后如果没有被管理员终止，会永久的运行。抽象作业只存在于Upstart自己内部，但有时个它非常有用，例如定义“永久运行”的作业，用来同步等。

　　作业的10种状态：
　　waiting: 初始状态。
　　starting: 作业开始启动。
　　pre-start: 运行pre-start配置节。
　　spawned: 运行script或exec节。
　　post-start: 运行post-start节。
　　running: 运行完post-start节之后的临时状态，表示作业正在运行(但可能没有关联的PID)。
　　pre-stop:运行pre-stop节。
　　stopping:运行完pre-stop节之后的临时状态。
　　killed: 作业要被终止。
　　post-stop: 运行post-stop节。

　　init daemon会监测每个服务的状态,如果服务出现问题会重启服务,在某些事件触发时或手工停止时会杀死服务。
　　Upstart init daemon只能监测哪些使用exec运行的作业，无法监测使用script…end script运行的作业。也就是说，服务应该使用exec运行，而任务则可以使用任意的方法。
　　Upstart init守护进程读取/etc/init目录下的作业配置文件，并使用inotify来监控它们的改变。配置文件名必须以.conf结尾，可以放在/etc/init/下的子目录中。每个文件定义一个服务或作业，其名称按路径名来称呼。例如定义在/etc/init/rc-sysinit.conf中的作业就称为rc-sysinit，而定义在/etc/init/net/apache.conf的作业称为net/apache。这些文件必须是纯文本且不可执行的。

###进程(Process)
　　Process是由工作(jobs)定义的服务(Services)或者任务(Task)，它将被init daemon运行。每个job可以定义一个或者多个不同的process，分别在其生命周期的不同状态运行。除抽象作业(Abstact Job)外的所有作业配置文件都必须要含有exec节(exec stanza)或者script节(script stanza)。它们指定这个工作运行什么文件。
　　在运行exec或者script指定的程序之前或者之后，你可以运行一些附加的shell代码。附加代码不是用来启动主进程的，实际上他们也不能启动主进程。附加代码是用来准备运行环境和进行清理工作的。启动前运行的脚本指定主进程运行前所要执行的shell代码，跟script一样，所以任何一个命令执行失败都将终止运行脚本。它也以”end script”作为结束。
　　Process定义如下：
	exec COMMAND [ARG]...
　　定义作业要运行的主进程，注意若有特殊的字符(如引号或$符)将导致整个命令被传递给Shell来运行。例如exec /usr/sbin/acpid -c $EVENTSDIR -s $SOCKET。
Scritp
......
exec|end script
　　定义Shell脚本来运行指定的主进程，该脚本由sh来执行。Shell的-e选项总是被使用，因此任何命令失败将导致脚本终止。注意作业的主进程只能用exec或script节中的一种来定义，不能同时用exec和script配置节来定义。
Pre-start
......
exec|end script
　　本进程在作业的starting事件完成之后，主进程运行之前执行。通常用来准备相关环境，例如创建必要的目录。
Post-start 
......
exec|end script
　　本进程在作业的started事件触发之前，主进程产生之前执行。通常用来发送必要的命令给主进程，或者用来延迟started事件，直到主进程准备好接收客户端的访问。
Pre-stop 
......
exec|end script
　　本进程在作业被stop on节中的一个事件停止或被stop命令停止时执行。它在作业的stopping事件之前，及主进程被杀死之前执行。通常用来发送必要的shutdown命令给主进程，或调用不带参数的start命令来取消stop。
Post-stop 
......
exec|end script
　　本进程在主进程被杀死之后，作业的stopped事件触发之前执行。通常用来清理相关环境，例如删除临时的目录。
###事件(event)
　　事件(event)是指系统状态的一种改变，这种改变会被通知给init进程。init进程可以得到的状态变更信息，几乎系统所有的内部或外部状态变更都可以触发一个事件。比如引导程序会触发启动(startup)事件，系统进入运行级别2会触发运行级别2(runlevel 2)事件，而文件系统加载则会触发路径加载(path-mounted)事件,拔掉或安装一个热插拔或USB设备(如打印机)也会触发一个事件。这些事件会被通知给init进程，然后init进程来决定系统如何处理这些事件。用户还可以通过initctl emit命令来手动触发一个事件。
　　你可以用start on列出一些事件，表明发出这些事件时你想要启动这个工作，也可以用stop on来指明发出某些事件时你想要停止这个工作。
　　事件定义格式如下：
定义能导致作业自动启动的事件集。KEY和VALUE指定环境变量及其值
start on Event[[KEY=] Value … and| or …]
 
定义导致作业自动停止的事件集。
stop on Event[[KEY=] Value … and| or …]
　　事件定义示例：
start on started gdm or started kdm
start on device-added SUBSYSTEM=tty DEVPATH=ttyS*
start on net-device-added INTERFACE!=lo
　　用户也可以自己定义一个事件，并让一个作业被这个事件触发。如下的myjob作业定义文件定义了一个被hithere事件触发的作业：
>cat /etc/init/myjob.conf
 
start on hithere
script
echo “Hi there, here I am!” > /tmp/myjob.out
date >> /tmp/myjob.out
end script
　　这段代码指定在接收到hithere事件时将触发该作业。然后代码执行指定的操作(将一条消息和日期输出到/tmp/myjob.out 文件)。
　　用户也可以使用initctl emit命令手动触发事件来执行这个作业。
initctl emit hithere
　　Upstart的事件数量是没有限制的，但init守护进程和telinit工具定义了一组常用的标准事件。主要有以下几个：
　　starting: 当作业被调度并开始运行时，由Upstart触发。
　　started: 当作业正在运行时被触发。
　　stopping: 当作业开始终止时被触发。
　　stopped: 当作业已经完成时(成功或失败)被触发。
　　当upstart init进程启动时，它会发出startup事件，这将激活实现了System V兼容性的事件和runlevel事件。随着作业的启动和停止，init守护进程将触发starting, started, stopping,stopped事件。另一个核心事件shutdown则是在系统关闭时发出的。其他核心事件包括ctrlaltdel，它说明您按下了Ctrl-Alt-Delete或kbdrequest，它用来说明您按下了Alt-Up(向上箭头)键组合。
　　Upstart有三种事件类型
　　Signal Event
　　非阻塞的即异步的。触发信号型事件会立即返回，调用者继续往下执行。信号型的意思就是广播者并不关心谁会接收它，也不需要等待是否发生某种事情，它只是用来提供信息用作通信。使用带–no-wait选项的initctl emit命令来创建信号型事件。例如initctl emit –no-wait mysignal。注意事件触发的非阻塞特性并不会直接影响那些与此事件有关的作业，它只是影响触发者程序，允许其继续执行，而无需等待任何使用这个事件的作业。作业本身的非阻塞特性则会影响作业自己，它使得作业不能被终止或延迟，不能以任何形式持有触发者的操作。
　　Method Event
　　阻塞的即同步的。它通常与Task job结合使用。方法型事件的行为类似于编程语言中的method或function call，调用者需要等待这个工作的完成。例如initctl emit mymethod，这个方法型事件被同步地触发，调用者需要等待直到initctl命令完成。在mymethod事件上启动的任务可能运行成功，也可能失败，假设有一个作业/etc/init/myapp.conf，如下：
　　Hook Event
　　阻塞的即同步的。钩子介于信号和方法之间。它是一种通知，表示系统发生了一些改变。不同于信号，钩子型事件的触发者需要等待作业的完成。因此钩子通常用来标志即将发生的改变一些事情。starting和stopping是钩子型事件，被Upstart触发以表明作业即将启动或即将终止。
　　注意事件与状态是有区别的，虽然Upstart内部使用状态(这些状态可以通过initctl status和list命令显示给用户看)，但事件是配置文件指定作业期望行为的一种方式，starting, started, stopping, stopped是事件，不是状态。这些事件在一些特殊的状态转移发生之前触发。例如，starting事件在与此事件相关的作业实际进行运行队列之前被触发。
###作业生命周期
　　启动一个作业的流程
　　1)Upstart把作业的目标从stop改成start。正如目标的名字指示的一样，作业(实例)现在尝试启动。目标可以用initctl list和status命令显示。
　　2)Upstart触发starting事件，指示作业即将启动。这个事件包括两个环境变量：JOB指定作业名；INSTANCE指定实例名，如果启动单一的实例(没有instance配置节)，则实例名为空。
　　3)starting事件完成。
　　4)如果pre-start节存在，则产生pre-start进程。如果pre-start失败，Upstart把目标从start改成stop，设置表示失败的变量并触发stopping和stopped事件。
　　5)Upstart产生主进程。即运行script或exec配置节，如果没有script或exec配置节，则Upstart什么也不做。
　　6)Upstart确定作业的最终PID，可参考expect fork和expect守护r进程。
　　7)如果post-start配置节存在，则产生post-start进程。。如果post-start失败，Upstart把目标从start改成stop，设置表示失败的变量并触发stopping和stopped事件。
　　8)Upstart触发started事件。这个事件包含与starting同样的环境变量。对Service job，当started事件完成后，主进程即完全地运行起来了。如果是Task job，则任务执行完成(成功或失败)。
　　终止一个作业的流程
　　1)Upstart把作业的目标从start改为stop。现在作业(实例)尝试终止。
　　2)如果pre-stop配置节存在，则产生pre-stop进程。如果pre-stop失败，Upstart设置表示失败的变量，并触发stopping和stopped事件。
　　3)如果作业有script或exec配置节，则终止主进程，首先向主进程发送SIGTERM信号，然后Upstart等待kill timeout秒数(默认为5秒)，如果进程仍然在运行，则向进程发送SIGKILL信号，因为进程不能选择忽略此信号，因此能保证进程被终止。
　　4)Upstart触发stopping事件。这个事件有一系列的相关环境变量，包括：
　　JOB: 与本事件关联的作业名。
　　INSTANCE: 实例名。
　　RESULT: “ok”表示作业正常退出，”failed”表示作业失败，注意退出结果的显示可以用normal exit配置节修改。
　　PROCESS: 导致作业失败的配置节名称。如果RESULT=ok，则本变量不会被设置。如果设置了，可能值有pre-start, post-start, main(表示script或exec配置节), pre-stop, post-stop, respawn(表示作业产生次数超过了respawn limit配置节设置的限制)。
　　EXIT_STATUS或EXIT_SIGNAL: 如果作业自己退出则设置EXIT_STATUS，如果由于接收到信号退出则设置EXIT_SIGNAL。如果两个变量都没有设置，则进程在产生的过程中出现了问题(例如指定要运行的命令没有找到)。
　　5)如果post-stop配置节存在，则生成post-stop进程。如果post-start失败，Upstart设置表示失败的变量并触发stopped事件。
　　6)Upstart触发stopped事件。当stopped事件完成后，作业即完全终止。stopped事件与stopping事件有相同的环境变量集。
###initctl(init daemon control tool)
　　initctl是一个具有root权限的管理员可以使用init进程管理控制工具，可以通过这个工具和Upstart init daemon进行通信。initctl可以用来启动或终止作业、列表作业、以及获取作业的状态、发出事件、重启init 进程等等。比如initctl list让您可以通过标识作业状态来深入了解系统操作，它告诉您目前正在等待哪些服务以及哪些服务目前是活动的。initctl工具还可以显示用于调试而接收的事件。
　　






 
　　这里和以前基于Sysv init的系统有很大的不同，这里不在是启动MySQL的执行脚本，而是指向/lib/init/upstart-job的一个软链接，而upstart-job这个脚本会调用initctl工具启动对应的服务。所以用传统的System V方式删掉/etc/init.d/mysql或/etc/rcX.d中是不会影响到MySQL的开机启动的。正是这个原因，网上很多文章说sysv-rc-conf、update-rc.d，rcconf三个基于以前Sysv init的启动管理工具不会生效的原因。不过这种现象只针对以Upstart工作配置文件为基础的服务。那些还是以SystemV脚本管理的软件包依然可以用上面三个管理工具进行启动行的管理，必定还有很多软件包还未采用Upstart方式进行管理。目前Ubuntu系统也都还兼容System V方式的脚本。
　　六、Ubuntu下常见系统状态下Upstart调用过程
　　系统启动
　　我们以Ubuntu为例子，并从Upstart的视角来阐述。在系统引导时，当initramfs文件系统运行起来时(用于设置RAID、解锁加密的文件系统卷等)，将会运行/sbin/init并分配PID为1，这样Upstart接过控制权。在默认运行级别2上的启动流程如下：
　　1)Upstart执行内部的初始化。
　　2)Upstart触发一个单一的称为startup的事件，这个事件触发其余的系统初始化过程。
　　3)init运行一些指定了start on startup的作业。这其中最著名的就是mountall作业，用来挂载硬盘和文件系统。
　　4)mountall作业依次触发一系列的事件，包括local-filesystems, virtual-filesystems, all-swaps等。当系统设备和挂载点可用时，它运行mountall守护程序来完成挂载硬盘和文件系统的工作。
　　5)virtual-filesystems事件引发udev作业启动。它运行uded守护程序来管理系统的设备，并监控设备的改变。
　　6)udev作业引发upstart-udev-bridge作业启动。
　　7)upstart-udev-bridge作业将会在某个点处触发”net-device-up IFACE=lo”事件，以表示本地网络(例如IPv4的127.0.0.0)可用。
　　8)在最终的文件系统挂载之后，mountall将会触发filesystem事件。
　　9)由于rc-sysinit作业中有start on filesystem and net-device-up IFACE=lo节，Upstart将会启动rc-sysinit作业。
　　10)rc-sysinit作业最后调用telinit命令，格式为telinit “${DEFAULT_RUNLEVEL}”。
　　11)telinit命令触发runlevel事件，即执行runlevel RUNLEVEL=2 PREVLEVEL=N。注意这就是telinit所做的全部工作，它自己并不会切换运行级别，而通过runlevel程序实现。
　　12)runlevel事件引发很多其他的Upstart作业启动，包括/etc/init/rc.conf，它用来启动遗留的SystemV init系统。
　　系统关闭
　　在系统关闭过程中，有一些重要的事实需要知道：
　　1)Upstart决不会关闭自己。Upstart会在系统断电时终止，如果它之前终止过，说明是一个bug。
　　2)Upstart决不会终止没有stop on配置节的作业。
　　3)Ubuntu既使用Upstart作业，也使用SysV作业。核心的服务由Upstart处理，一些额外的服务可以在遗留的SystemV模式下运行。这主要是为向后兼容，因此在Ubuntu的Universe和Mutiverse软件库中有大量的软件包，为避免更改每个软件包以使它能在Upstart下工作，Upstart允许使用已经存在的SystemV(还包括Debian兼容的)脚本。
　　关闭系统需要先执行关机动作，例如在图形用户界面中单击”Shut Down…”，运行命令shutdown -h now等。关机的流程如下：
　　1)假设当前运行级别为2，关机动作将会使Upstart触发runlevel事件，即runlevel RUNLEVEL=0 PREVLEVEL=2。
　　2)作业/etc/init/rc.conf将被运行。这个作业调用/etc/init.d/rc，并传递新的运行级别(“0“)。
　　3)SystemV系统调用/etc/rc0.d/中必要的脚本(都是指向/etc/init.d/中脚本的链接)，来终止SystemV服务。
　　4)其中有一个/etc/init.d/sendsigs脚本，这个脚本中有个do_stop()函数，它负责杀死所有没有被终止的进程(包括Upstart进程)。
　　系统重启
　　先要执行重启动作，例如在图形界面中单击”Restart…”，运行shutdown -r now或reboot。重启的流程如下：
　　1)假设当前运行级别为2，重启动作将会使Upstart触发runlevel事件，即 runlevel RUNLEVEL=6 PREVLEVEL=2。
　　2)作业/etc/init/rc.conf将被运行。这个作业调用/etc/init.d/rc，并传递新的运行级别(“6“)。
　　3)SystemV系统调用/etc/rc6.d/中必要的脚本(都是指向/etc/init.d/中脚本的链接)，来终止SystemV服务。
　　4)其中有一个/etc/init.d/sendsigs脚本，这个脚本中有个do_stop()函数，它负责杀死所有没有被终止的进程(包括Upstart进程)。
　　恢复模式
　　Ubuntu提供了恢复模式以应对系统出现问题的情况。这由friendly-recovery软件包来处理。