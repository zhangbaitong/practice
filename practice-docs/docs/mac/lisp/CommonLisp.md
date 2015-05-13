Common Lisp

All Common Lisp Implementations

http://common-lisp.net/~dlw/LispSurvey.html

简要统计了一下，目前世界上共有 13 种 Common Lisp 实现，其中 7 个是开源的，6 个是商业实现。最近 SCL 开放给非商业用户了，另外我朋友 Mazha 买了一台 PowerMac G4，结果所有这 13 种平台现在我都有条件运行起来，用于测试我的可移植程序了。

名称
主要支持的平台
站点
CMUCL
Darwin/x86, Linux/x86
http://www.cons.org/cmucl
SBCL
Linux/x86
http://www.sbcl.org
Clozure CL
Darwin/amd64, Darwin/ppc
http://www.clozure.com/clozurecl.html
ABCL
Java VM
http://common-lisp.net/project/armedbear
ECL
Linux/x86
http://ecls.sourceforge.net
CLISP
All platforms
http://clisp.cons.org
MCL
Darwin/ppc
http://www.digitool.com
Corman Lisp
win32
http://www.cormanlisp.com
Power Lisp
Darwin/ppc
http://www.cormanlisp.com/PowerLisp.html
Allegro CL
win32, Linux
http://www.franz.com
LispWorks
win32, Darwin, Linux
http://www.lispworks.com
Scieneer CL
Solaris/amd64, Linux/amd64
http://www.scieneer.com/scl
Open Genera
Alpha VM on Linux/amd64
http://www.symbolics.com

注意并非所有这 13 种平台都是从头写出来的，其中一些是由另一些发展而来的：SBCL 和 Scieneer CL 来源于 CMUCL；Clozure CL, Power Lisp 以及 Corman Lisp 都是来源于商业 MCL (Macintosh Common Lisp)。

虽然 Common Lisp 早已标准化了，但是要想写出能在所有上述平台上运行的 Common Lisp 程序还是有些难度的，因为一个真正有用处的程序还可能用到网络、多线程、外部函数接口、GUI，以及一些尚未标准化的语言特性 (流，MOP) 等。




1.《Common Lisp: A Gentle Introduction to Symbolic Computation》
2.《Common Lisp: The Language 2nd》
3.《On Lisp》
4.《Practical Common Lisp》
5.《计算机程序的构造和解释》

关于Lisp的另一则小故事：Lisp在Google的命运
http://blog.csdn.net/albert_lee/article/details/6146596
http://www.flownet.com/gat/jpl-lisp.html
