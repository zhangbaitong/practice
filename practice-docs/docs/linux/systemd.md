systemctl

http://fedoraproject.org/wiki/Systemd/zh-cn

http://www.freedesktop.org/software/systemd/man/systemd.service.html

http://linuxtoy.org/archives/interview-creater-of-systemd-and-pulseaudio-lennart.html

更多详细信息请参考手册页(man systemctl)。systemd-cgls 以树形列出正在运行的进程。它可以递归显示给定控制组内容。详情请参阅 systemd-cgls 手册页。

如何启动/关闭、启用/禁用服务？

运行一个服务：

  systemctl start foo.service 
关闭一个服务：

  systemctl stop foo.service 
重启一个服务：

 systemctl restart foo.service 
显示一个服务（无论运行与否）的状态：

 systemctl status foo.service 
在开机时启用一个服务：

 systemctl enable foo.service 
在开机时禁用一个服务：

 systemctl disable foo.service 
检查一个服务是否是开机启用：

 systemctl is-enabled foo.service; echo $? 
0 表示已开机启用，1 表示没有开机启用。在 Fedora 17 中，除了返回值外，相应的 "enable" 或 "disable" 也会显示到标准输出。

更多详情请参看 systemctl 手册。

如何改变运行级别？

systemd 使用比 sysvinit 的运行级更为自由的 target 概念作为替代。

第 3 运行级用 multi-user.target 替代。第 5 运行级用 graphical.target 替代。runlevel3.target 和 runlevel5.target 分别是指向 multi-user.target 和 graphical.target 的符号链接。

你可以使用下面的命令切换到“运行级 3 ”：

 systemctl isolate multi-user.target (or) systemctl isolate runlevel3.target 
你也可以使用下面的命令切换到“运行级 5 ”：

 systemctl isolate graphical.target (or) systemctl isolate runlevel5.target 
如何改变默认运行级别？

systemd 使用链接来指向默认的运行级别。在创建新的链接前，你可以通过下面命令删除存在的链接：

 rm /etc/systemd/system/default.target 
默认切换到运行级 3 ：

 ln -sf /lib/systemd/system/multi-user.target /etc/systemd/system/default.target 
默认切换到运行级 5 ：

 ln -sf /lib/systemd/system/graphical.target /etc/systemd/system/default.target 
systemd 不使用/etc/inittab 文件。

如何查看当下运行级别？

runlevel 命令在 systemd 下仍然可以工作。你可以继续使用它，尽管 systemd 使用 'target' 概念(多个的 'target' 可以同时激活)替换了之前系统的 runlevel 。等价的 systemd 命令是

 systemctl list-units --type=target 
如何关机？

你可以使用

poweroff
更多可行的命令是： halt -p、 init 0、 shutdown -P now

需要注意的是，在之前的 Fedora 发布版中 halt 与 poweroff 的效果一样。但是 systemd 区别对待这两项，因而，没有参数的 halt 做的就是像它说的那样（停止）——仅仅停止系统而不关机。

service 命令兼容 systemd 吗？

兼容。service 经过修改可以在处理 systemd 服务时调用 systemctl 实现。因而下面的命令所做的事情相同

 service NetworkManager stop 
(or)

 systemctl stop NetworkManager.service 
chkconfig 命令兼容 systemd 吗？

兼容，如果是开启/关闭服务，兼容性保证两种方式都可以运行。不过 chkconfig 经过修改使得在处理 systemd 服务时调用 systemctl 工具。同样，在处理传统 sysv 初始化文件时 systemd 自动调用 chkconfig 。

因此，下面的命令做的事情是一样的

 chkconfig NetworkManager off 
(or)

 systemctl disable NetworkManager.service 
chkconfig --list 不会列出 systemd 服务，只列出 Sys V 服务。chkconfig 的输出结果里附带了对此的说明信息。

system-config-services 与 systemd 兼容吗？

Feodra 15 的 system-config-services 版本也可以处理 systemd 的服务文件。如果你遇到问题，直接报告一个 bug 。

如何改变默认 getty 数目？

添加一个新的 getty ：

只需要在 getty.target.wants/ 目录下新建一个链接到 getty 的示例即可：

ln -sf /lib/systemd/system/getty@.service /etc/systemd/system/getty.target.wants/getty@tty9.service
systemctl daemon-reload
systemctl start getty@tty9.service
删除一个 getty ：

直接删掉 getty.target.wants/ 目录下你不想要的哪个 getty 链接即可：

rm /etc/systemd/system/getty.target.wants/getty@tty5.service /etc/systemd/system/getty.target.wants/getty@tty6.service
systemctl daemon-reload
systemctl stop getty@tty5.service getty@tty6.service
systemd 不使用 /etc/inittab 文件。

虚拟终端如何设置自动登录？

首先创建一个新的类似于 getty@.service 的服务：

# cp /lib/systemd/system/getty@.service \
     /etc/systemd/system/autologin@.service
# ln -s /etc/systemd/system/autologin@.service \
        /etc/systemd/system/getty.target.wants/getty@tty8.service
然后编辑 ExecStart、Restart 和 Alias 的值，如：

...
ExecStart=-/sbin/mingetty --autologin USERNAME %I
Restart=no
...
Alias=getty.target.wants/getty@tty8.service
最后重新加载守护进程，运行服务：

systemctl daemon-reload
systemctl start getty@tty8.service
需要注意的是，如果你退出了 tty8 的会话，你需要等到下次重新启动才能使用，除非你给 Restart 的值是 'always' ，这样你可以使用systemctl 手动开启（但是出于安全考虑，强烈建议你不要那么做）。

如何定制或增加一个自定义 unit 文件？

unit 文件在 /etc/systemd/system 下的优先级要高于 /lib/systemd/system 下的。按照个人的需求从后者移动到前者并进行自定义修改。

如果一行以 .include 开始，后接文件名，那么该文件在此时被解析为特殊文件。请确保包含的文件在指令前有适当的章节头信息。

如果可能的话，你应当使用 .include 声明 unit 文件而不是在 /lib/systemd/system 下复制整个 unit 文件到 /etc/systemd/system 目录下。这样你才可以在将来升级软件包时正确地升级未改变的指令。

在使用 .include 和指令时需要小心，因为它可以有多次定义(像 EnvironmentFile= 一样)。由于我们只能添加新指令而不能删除已定义的指令，此时，我们就必须从 /lib/systemd/system 复制整个文件到 /etc/systemd/system 中去。

假设我们有一个 lighttpd 服务，我们现在想降低它的 niceness 值。我们需要做的就只是添加 Nice=-5 到 lighttpd.service 文件中。我们可以通过复制整个文件 /lib/systemd/system/lighttpd.service 到 /etc/systemd/system/lighttpd.service 或者在 /etc/systemd/system/lighttpd.service 中创建如下文件做到

.include /lib/systemd/system/lighttpd.service 
[Service]
Nice=-5
不要忘记在编辑一个 unit 文件后使用 systemdctl daemon-reload 重载 systemd 守护进程。

注：当修改一个拥有指向它的链接的 unit 时，例如：display-manager.service -> prefdm.service ，需要复制链接而不是实际的 unit ，例中即复制 display-manager.service 到 /etc/systemd/system 目录下，或者是用 .include 新建一个相应名字的 unit 。 

如何调试系统事件？

http://fedoraproject.org/wiki/How_to_debug_Systemd_problems

预读功能

systemd 有内置的预读功能(默认升级时未启用)，它可以提高开机速度，但具体提升幅度视个人硬件而定。 要使用它，使用命令：

systemctl enable systemd-readahead-collect.service
systemctl enable systemd-readahead-replay.service
关于 /usr 分区的警告

http://freedesktop.org/wiki/Software/systemd/separate-usr-is-broken

http://cgit.freedesktop.org/systemd/tree/README

man 手册

systemd 有丰富的文档，其中也包括一些 man 手册页。其在线版地址是： http://www.freedesktop.org/software/systemd/man/




https://wiki.archlinux.org/index.php/systemd_(%E7%AE%80%E4%BD%93%E4%B8%AD%E6%96%87)
https://wiki.archlinux.org/index.php/Systemd

分析系统状态
输出激活的单元：

$ systemctl
以下命令等效：

$ systemctl list-units
输出运行失败的单元：

$ systemctl --failed
所有可用的单元文件存放在 /usr/lib/systemd/system/ 和 /etc/systemd/system/ 目录（后者优先级更高）。查看所有已安装服务：

$ systemctl list-unit-files
使用单元
一个单元配置文件可以描述如下内容之一：系统服务（.service）、挂载点（.mount）、sockets（.sockets 、系统设备、交换分区/文件、启动目标（target）、文件系统路径、由 systemd 管理的计时器。详情参阅 man 5 systemd.unit.

使用 systemctl 控制单元时，通常需要使用单元文件的全名，包括扩展名（例如 sshd.service）。但是有些单元可以在systemctl中使用简写方式。

如果无扩展名，systemctl 默认把扩展名当作 .service。例如 netcfg 和 netcfg.service 是等价的。
挂载点会自动转化为相应的 .mount 单元。例如 /home 等价于 home.mount。
设备会自动转化为相应的 .device 单元，所以 /dev/sda2 等价于 dev-sda2.device。
立即激活单元：

# systemctl start <单元>
立即停止单元：

# systemctl stop <单元>
重启单元：

# systemctl restart <单元>
命令单元重新读取配置：

# systemctl reload <单元>
输出单元运行状态：

$ systemctl status <单元>
检查单元是否配置为自动启动：

$ systemctl is-enabled <单元>
开机自动激活单元：

# systemctl enable <单元>
注意: 如果服务没有Install段落，一般意味着应该通过其它服务自动调用它们。如果真的需要手动安装，可以直接连接服务，如下（将foo替换为真实的服务名）：

# ln -s /usr/lib/systemd/system/foo.service /etc/systemd/system/graphical.target.wants/
取消开机自动激活单元：

# systemctl disable <单元>
显示单元的手册页（必须由单元文件提供）：

# systemctl help <单元>
重新载入 systemd，扫描新的或有变动的单元：

# systemctl daemon-reload
电源管理
安装 polkit 后才可使用电源管理。

如果你正登录在一个本地的systemd-logind用户会话，且当前没有其它活动的会话，那么以下命令无需root权限即可执行。否则（例如，当前有另一个用户登录在某个tty），systemd 将会自动请求输入root密码。

重启：

$ systemctl reboot
退出系统并停止电源：

$ systemctl poweroff
待机：

$ systemctl suspend
休眠：

$ systemctl hibernate
混合休眠模式（同时休眠到硬盘并待机）：

$ systemctl hybrid-sleep
原生 systemd 配置文件

注意: 可能需要手动创建某些文件。所有文件的权限都是644，属主 root，属组 root。

虚拟控制台
可以用/etc/vconsole.conf 文件或者localectl 配置虚拟控制台，包括键盘布局和控制台字体。详情请访问 控制台字体 和 键盘布局。

硬件时钟
systemd 默认硬件时钟为协调世界时（UTC）。

小贴士: 推荐使用NTP服务来在线同步硬件时钟。

硬件时钟设定为地方时
将硬件时钟配置为地方时（不建议）：

# timedatectl set-local-rtc true
重新调整为 UTC：

# timedatectl set-local-rtc false
如果设置成本地时间，处理夏令时有些麻烦。如果夏令时调整发生在关机时，下次启动时时间会出现问题。最新的内核直接从实时时钟芯片（RTC）读取时间，不使用 hwclock，内核把从 RTC 读取的时间当作 UTC 处理。所以如果硬件时间是地方时，系统启动一开始识别的时间是错误的，之后很快会进行矫正。这可能导致一些问题（尤其是时间倒退时）。

如果同时安装了 Windows 操作系统（默认使用地方时），那么一般 RTC 会被设置为地方时。Windows 其实也能处理 UTC，需要修改注册表。建议让 Windows 使用 UTC，而非让 Linux 使用地方时。Windows 使用 UTC 后，请记得禁用 Windows 的时间同步功能，以防 Windows 错误设置硬件时间。如上文所说，Linux 可以使用NTP服务来在线同步硬件时钟。


内核模块
请访问Kernel modules
https://wiki.archlinux.org/index.php/Kernel_modules_(%E7%AE%80%E4%BD%93%E4%B8%AD%E6%96%87)

文件系统挂载
默认行为是：在启动一个需要挂载特定分区的服务之前，系统自动检查并挂载分区。/etc/fstab 中设定的网络文件系统（如 NFS、Samba）无需配置即可正常工作，systemd 将确保网络文件系统在网络链接就绪后挂载。

详情参阅：man 5 systemd.mount。

LVM
如果装有不通过 initramfs 激活的LVM卷，则需启动 lvm-monitoring 服务（由 lvm2 软件包提供）：

# systemctl enable lvm-monitoring
ACPI 电源管理
参阅 Power Management

https://wiki.archlinux.org/index.php/Power_Management_(%E7%AE%80%E4%BD%93%E4%B8%AD%E6%96%87)

临时文件
/usr/lib/tmpfiles.d/ 和 /etc/tmpfiles.d/ 中的文件描述了 systemd-tmpfiles 如何创建、清理、删除临时文件和目录，这些文件和目录通常存放在 /run 和 /tmp 中。配置文件名称为 /etc/tmpfiles.d/<program>.conf。此处的配置能覆盖 /usr/lib/tmpfiles.d/ 目录中的同名配置。

临时文件通常和服务文件同时提供，以生成守护进程需要的文件和目录。例如 Samba 服务需要目录 /run/samba 存在并设置正确的权限位，就象这样：

/usr/lib/tmpfiles.d/samba.conf
D /run/samba 0755 root root
此外，临时文件还可以用来在开机时向特定文件写入某些内容。比如，要禁止系统从USB设备唤醒，利用旧的 /etc/rc.local 可以用 echo USBE > /proc/acpi/wakeup，而现在可以这么做：

/etc/tmpfiles.d/disable-usb-wake.conf
w /proc/acpi/wakeup - - - - USBE
详情参见 man 5 tmpfiles.d。

注意: 该方法不能向 /sys 中的配置文件添加参数，因为 systemd-tmpfiles-setup 有可能在相关模块加载前运行。这种情况下，需要首先通过 modinfo <模块名> 确认需要的参数，并在 /etc/modprobe.d 下的一个文件中设置改参数。另外，还可以使用 udev 规则，在设备就绪时设置相应属性。

自己编写 .service 文件
systemd 的单元文件是受 XDG Desktop Entry .desktop 文件启发而产生，而最初起源是 Windows 下的 .ini 文件。

处理依赖关系
使用systemd时，可通过正确编写单元配置文件来解决其依赖关系。典型的情况是，单元A要求单元B在A启动之前运行。在此情况下，向单元A配置文件中的 [Unit] 段添加 Requires=B 和 After=B 即可。若此依赖关系是可选的，可添加 Wants=B 和 After=B。请注意 Wants= 和 Requires= 并不意味着 After=，即如果 After= 选项没有制定，这两个单元将被并行启动。

依赖关系通常被用在服务（service）而不是目标（target）上。例如， network.target 一般会被某个配置网络接口的服务引入，所以，将自定义的单元排在该服务之后即可，因为 network.target 已经启动。

启动方式
编写自定义的service文件时，可以选择几种不同的服务启动方式。启动方式可通过配置文件 [Service] 段中的 Type= 参数进行设置。具体的参数说明请参阅 man systemd.service 。

Type=simple（默认值）：systemd认为该服务将立即启动。服务进程不会fork。如果该服务要启动其他服务，不要使用此类型启动，除非该服务是socket激活型。
Type=forking：systemd认为当该服务进程fork，且父进程退出后服务启动成功。对于常规的守护进程（daemon），除非你确定此启动方式无法满足需求，使用此类型启动即可。使用此启动类型应同时指定 PIDFile=，以便systemd能够跟踪服务的主进程。
Type=oneshot：这一选项适用于只执行一项任务、随后立即退出的服务。可能需要同时设置 RemainAfterExit=yes 使得 systemd 在服务进程退出之后仍然认为服务处于激活状态。
Type=notify：与 Type=simple 相同，但约定服务会在就绪后向 systemd 发送一个信号。这一通知的实现由 libsystemd-daemon.so 提供。
Type=dbus：若以此方式启动，当指定的 BusName 出现在DBus系统总线上时，systemd认为服务就绪。
修改现存单元文件
要更改由软件包提供的单元文件，先创建名为 /etc/systemd/system/<单元名>.d/ 的目录（如 /etc/systemd/system/httpd.service.d/），然后放入 *.conf 文件，其中可以添加或重置参数。这里设置的参数优先级高于原来的单元文件。例如，如果想添加一个额外的依赖，创建这么一个文件即可：

/etc/systemd/system/<unit>.d/customdependency.conf

[Unit]
Requires=<新依赖>
After=<新依赖>
然后运行以下命令使更改生效：

# systemctl daemon-reload
# systemctl restart <单元>
此外，把旧的单元文件从 /usr/lib/systemd/system/ 复制到 /etc/systemd/system/，然后进行修改，也可以达到同样效果。在 /etc/systemd/system/ 目录中的单元文件的优先级总是高于 /usr/lib/systemd/system/ 目录中的同名单元文件。注意，当 /usr/lib/ 中的单元文件因软件包升级变更时，/etc/ 中自定义的单元文件不会同步更新。此外，你还得执行 systemctl reenable <unit>，手动重新启用该单元。因此，建议使用前面一种利用 *.conf 的方法。

小贴士: 可以用 systemd-delta 命令来查看哪些单元文件被覆盖、哪些被修改。

单元配置文件的 vim 语法高亮支持
可从官方仓库安装 vim-systemd 软件包，使 unit 配置文件在 Vim 下支持语法高亮。

目标（target）
启动级别（runlevel）是一个旧的概念。现在，systemd 引入了一个和启动级别功能相似又不同的概念——目标（target）。不像数字表示的启动级别，每个目标都有名字和独特的功能，并且能同时启用多个。一些目标继承其他目标的服务，并启动新服务。systemd 提供了一些模仿 sysvinit 启动级别的目标，仍可以使用旧的 telinit 启动级别 命令切换。
获取当前目标

不要使用 runlevel 命令了：

$ systemctl list-units --type=target
创建新目标
在 Fedora 中，启动级别 0、1、3、5、6 都被赋予特定用途，并且都对应一个 systemd 的目标。然而，没有什么很好的移植用户定义的启动级别（2、4）的方法。要实现类似功能，可以以原有的启动级别为基础，创建一个新的目标 /etc/systemd/system/<新目标>（可以参考 /usr/lib/systemd/system/graphical.target），创建 /etc/systemd/system/<新目标>.wants 目录，向其中加入额外服务的链接（指向 /usr/lib/systemd/system/ 中的单元文件）。

目标表

SysV 启动级别	Systemd 目标	注释
0	runlevel0.target, poweroff.target	中断系统（halt）
1, s, single	runlevel1.target, rescue.target	单用户模式
2, 4	runlevel2.target, runlevel4.target, multi-user.target	用户自定义启动级别，通常识别为级别3。
3	runlevel3.target, multi-user.target	多用户，无图形界面。用户可以通过终端或网络登录。
5	runlevel5.target, graphical.target	多用户，图形界面。继承级别3的服务，并启动图形界面服务。
6	runlevel6.target, reboot.target	重启
emergency	emergency.target	急救模式（Emergency shell）
切换启动级别/目标
systemd 中，启动级别通过“目标单元”访问。通过如下命令切换：

# systemctl isolate graphical.target
该命令对下次启动无影响。等价于telinit 3 或 telinit 5。

修改默认启动级别/目标
开机启动进的目标是 default.target，默认链接到 graphical.target （大致相当于原来的启动级别5）。可以通过内核参数更改默认启动级别：

小贴士: 可以省略扩展名 .target。

 systemd.unit=multi-user.target （大致相当于级别3）
 systemd.unit=rescue.target （大致相当于级别1）
另一个方法是修改 default.target。可以通过 systemctl 修改它：

# systemctl enable multi-user.target
命令执行情况由 systemctl 显示：链接 /etc/systemd/system/default.target 被创建，指向新的默认启动级别。该方法当且仅当目标配置文件中有以下内容时有效：

[Install]
Alias=default.target
目前，multi-user.target、graphical.target 都包含这段内容。

日志
systemd提供了自己日志系统（logging system），称为 journal. 使用 systemd 日志，无需额外安装日志服务（syslog）。读取日志的命令：

# journalctl
默认情况下（当 Storage= 在文件 /etc/systemd/journald.conf 中被设置为 auto），日志记录将被写入 /var/log/journal/。该目录是 systemd 软件包的一部分。若被删除，systemd 不会自动创建它，直到下次升级软件包时重建该目录。如果该目录缺失，systemd 会将日志记录写入 /run/systemd/journal。这意味着，系统重启后日志将丢失。

过滤输出
journalctl可以根据特定字段过滤输出，例如：

显示本次启动后的所有日志：

# journalctl -b
不过，一般大家更关心的不是本次启动后的日志，而是上次启动时的（例如，刚刚系统崩溃了）。目前还没有这项功能，正在 systemd-devel@lists.freedesktop.org 讨论中。

目前的折中方案是：

# journalctl --since=today | tac | sed -n '/-- Reboot --/{n;:r;/-- Reboot --/q;p;n;b r}' | tac
以上命令输出本日内的所有启动信息。但要注意，如果日志很多，该命令执行时间会比较漫长。

动态跟踪最新信息：

# journalctl -f
显示特定程序的所有消息:

 # journalctl /usr/lib/systemd/systemd
显示特定进程的所有消息:

# journalctl _PID=1
显示指定单元的所有消息：

# journalctl -u netcfg
详情参阅man journalctl、man systemd.journal-fields

日志大小限制
如果按上面的操作保留日志的话，默认日志最大限制为所在文件系统容量的 10%，即：如果 /var/log/journal 储存在 50GiB 的根分区中，那么日志最多存储 5GiB 数据。可以修改 /etc/systemd/journald.conf 中的 SystemMaxUse 来指定该最大限制。如限制日志最大 50MiB：

SystemMaxUse=50M
详情参见 man journald.conf.

配合syslog使用
systemd提供了 socket /run/systemd/journal/syslog，以兼容传统日志服务。所有系统信息都会被传入。要使传统日志服务工作，需要让服务链接该 socket，而非 /dev/log（官方说明）。Arch 软件仓库中的 syslog-ng 已经包含了需要的配置。

设置开机启动 syslog-ng：

 # systemctl enable syslog-ng
疑难解答
关机/重启十分缓慢
如果关机特别慢（甚至跟死机了一样），很可能是某个拒不退出的服务在作怪。systemd 会等待一段时间，然后再尝试杀死它。请阅读这篇文章，确认你是否是该问题受害者。

短时进程无日志记录
若 journalctl -u foounit.service 没有显示某个短时进程的任何输出，那么改用 PID 试试。例如，若 systemd-modules-load.service 执行失败，那么先用 systemctl status systemd-modules-load 查询其 PID（比如是123），然后检索该 PID 相关的日志 journalctl -b _PID=123。运行时进程的日志元数据（诸如 _SYSTEMD_UNIT 和 _COMM）被乱序收集在 /proc 目录。要修复该问题，必须修改内核，使其通过套接字连接来提供上述数据，该过程类似于 SCM_CREDENTIALS。

诊断启动问题
使用如下内核参数引导： systemd.log_level=debug systemd.log_target=kmsg log_buf_len=1M

禁止在程序崩溃时转储内存
要使用老的内核转储，创建下面文件:

/etc/sysctl.d/49-coredump.conf

kernel.core_pattern = core
kernel.core_uses_pid = 0
然后运行：

# /usr/lib/systemd/systemd-sysctl
同样可能需要执行“unlimit”设置文件大小：

$ ulimit -c unlimited
转自：https://wiki.archlinux.org/index.php/systemd_(简体中文)