1、ps：查看当前系统进程状态
1）基本语法
语法	说明
ps aux	查看系统中所有进程
ps -ef	可以查看父子进程之间的关系
2）选项说明
选项	说明
a	列出带有终端的所有用户的进程
x	列出当前用户的所有进程，包括没有终端的进程
u	面相用户友好的显示风格
-e	列出所有进程
-u	列出某个用户关联的所有进程
-f	显示完整格式的进程列表
3）ps aux：查看进程 CPU、内存占用率
[root@testx ~]# ps aux
USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root          1  0.0  0.3 194084  6244 ?        Ss   5月12   0:47 /usr/lib/systemd/systemd --switched-root --system --deserialize 22
root          2  0.0  0.0      0     0 ?        S    5月12   0:00 [kthreadd]
root          4  0.0  0.0      0     0 ?        S<   5月12   0:00 [kworker/0:0H]
USER： 该进程是由哪个用户产生的
PID： 进程的 ID 号
%CPU： 该进程占用 CPU 资源的百分比， 占用越高， 进程越耗费资源
%MEM： 该进程占用物理内存的百分比， 占用越高， 进程越耗费资源
VSZ： 该进程占用虚拟内存的大小， 单位 KB
RSS： 该进程占用实际物理内存的大小， 单位 KB
TTY： 该进程是在哪个终端中运行的。 对于 CentOS 来说， tty1 是图形化终端，tty2-tty6 是本地的字符界面终端。 pts/0-255 代表虚拟终端。
STAT： 进程状态，常见的状态有：
R： 运行状态
S： 睡眠状态
T： 暂停状态
Z： 僵尸状态
s： 包含子进程
l： 多线程
+： 前台显示
START： 该进程的启动时间
TIME： 该进程占用 CPU 的运算时间， 注意不是系统时间
COMMAND： 产生此进程的命令名
4）ps -ef：显示父子进程信息
如果想查看进程的 CPU 占用率和内存占用率， 可以使用 ps aux

如果想查看进程的父进程 ID 可以使用 ps ef

[root@testx ~]# ps -ef
UID         PID   PPID  C STIME TTY          TIME CMD
root          1      0  0 5月12 ?       00:00:47 /usr/lib/systemd/systemd --switched-root --system --deserialize 22
root          2      0  0 5月12 ?       00:00:00 [kthreadd]
root          4      2  0 5月12 ?       00:00:00 [kworker/0:0H]
root          6      2  0 5月12 ?       00:00:01 [ksoftirqd/0]
UID： 用户 ID
PID： 进程 ID
PPID： 父进程 ID
C： CPU 用于计算执行优先级的因子。 数值越大， 表明进程是 CPU 密集型运算，执行优先级会降低； 数值越小， 表明进程是 I/O 密集型运算， 执行优先级会提高
STIME： 进程启动的时间
TTY： 完整的终端名称
TIME： CPU 时间
CMD： 启动进程所用的命令和参数
5）ps -Lf pid：查看某个进程的所有内核线程
[root@testx ~]# ps -Lf 103268
UID         PID   PPID    LWP  C NLWP STIME TTY      STAT   TIME CMD
root     103268 101406 103268  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
root     103268 101406 103269  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
root     103268 101406 103270  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
root     103268 101406 103271  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
root     103268 101406 103272  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
root     103268 101406 103273  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
root     103268 101406 103275  0   21 14:32 pts/0    Sl+    0:00 java ThreadStatus
UID： 用户 ID
PID： 进程 ID
PPID： 父进程 ID
C： CPU 用于计算执行优先级的因子。 数值越大， 表明进程是 CPU 密集型运算，执行优先级会降低； 数值越小， 表明进程是 I/O 密集型运算， 执行优先级会提高
STIME： 进程启动的时间
TTY： 完整的终端名称
TIME： CPU 时间
CMD： 启动进程所用的命令和参数
2、kill：终止进程
1）基本语法
语法	说明
kill [选项] 进程号	通过进程号杀死进程
killall 进程名称	通过进程名称杀死进程，也支持通配符，这在系统因负载过大而变得很慢时很有用
2）选项
选项	说明
-9	表示强迫进程立即执行
3）案例实操
（1）关闭火狐浏览器
# 通过ps查询出firefox的进程，发现进程id是1855
[root@testx java]# ps -ef | grep firefox
root       1855  67619 22 21:22 ?        00:00:15 /usr/lib64/firefox/firefox
# 结束1855这个进程
[root@testx java]# kill -9 1855
（2）通过进程名称杀死进程
[root@testx firefox]# killall firefox
3、pstree：查看树进程
pstree 命令以树状图的方式展现进程之间的派生关系，显示效果比较直观。

1）基本语法
pstree [选项]
2）选项说明
选项	说明
-p	显示进程的 PID
-u	显示进程的所属用户
3）案例实操
（1）显示进程 pid
图片

（2）查看一个特定的进程
如果您希望 pstree 仅显示特定进程的父级和子级信息，请使用-s 选项。

pstree -s [PID]
如下，6826 是一个 java 的进程，还可以看到这个进程下面的所有线程列表

图片

4、top：实时监控系统状态
1）语法
top [选项]
2）选项说明
选项	说明
-d 秒数	指定 top 命令每隔几秒刷新一下结果，默认是 3 秒在 top 命令的交互模式当中可以执行命令
-i	使 top 不显示任何闲置或者僵死进程
-p	通过指定监控进程 ID 来监控某个进程的状态
-c	显示整个命令行而不只是显示命令名
3）操作说明
在 top 命令结果中可以执行下面操作参与交互

操作	功能
P	以 CPU 使用率排序，默认就是此项
M	以内存的使用率排序
N	以 PID 排序
q	退出 top
4）查询结果字段解释
top - 22:56:30 up 1 day, 16:32,  4 users,  load average: 0.00, 0.01, 0.05
Tasks: 267 total,   1 running, 266 sleeping,   0 stopped,   0 zombie
%Cpu(s):  0.0 us,  0.1 sy,  0.0 ni, 99.9 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
KiB Mem :  1863032 total,    87068 free,   812768 used,   963196 buff/cache
KiB Swap:  2097148 total,  2091004 free,     6144 used.   856636 avail Mem

PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND
811 root      20   0   21684   1264    952 S   0.3  0.1   0:18.61 irqbalance
832 root      20   0  201428   1088    588 S   0.3  0.1   0:00.58 gssproxy
67907 root      20   0  608612  13344   6708 S   0.3  0.7   1:01.76 vmtoolsd
1 root      20   0  194084   6260   3188 S   0.0  0.3   1:03.54 systemd
2 root      20   0       0      0      0 S   0.0  0.0   0:00.28 kthreadd
4 root       0 -20       0      0      0 S   0.0  0.0   0:00.00 kworker/0:0H
6 root      20   0       0      0      0 S   0.0  0.0   0:02.08 ksoftirqd/0
7 root      rt   0       0      0      0 S   0.0  0.0   0:00.24 migration/0
8 root      20   0       0      0      0 S   0.0  0.0   0:00.00 rcu_bh
9 root      20   0       0      0      0 S   0.0  0.0   0:32.09 rcu_sched
10 root       0 -20       0      0      0 S   0.0  0.0   0:00.00 lru-add-drain
11 root      rt   0       0      0      0 S   0.0  0.0   0:01.23 watchdog/0
（1）第 1 行信息为任务队列信息
内容	说明
12:26:46	系统当前时间
up 1 day, 13:32	系统的运行时间，本金以运行 1 天 13 小时 32 分
2 users	当前登录了 2 个用户
load average: 0.00, 0.00, 0.00	系统在之前 1 分钟， 5 分钟， 15 分钟的平均负 载。 一般认为小于 1 时， 负载较小。 如果大于 1， 系统已经超出负荷
（2）第 2 行为进程信息
内容	说明
Tasks: 95 total	系统中的进程总数
1 running	正在运行的进程数
94 sleeping	睡眠的进程数
0 stopped	正在停止的进程数
0 zombie	僵尸进程。 如果不是 0， 需要手工检查僵尸进程
（3）第 3 行为 CPU 信息
内容	说明
us	用户空间占用的 CPU 百分比
sy	内核空间占用的 CPU 百分比
ni	改变过优先级的用户进程占用的 CPU 百分比
id	空闲 CPU 百分比
wa	等待输入/输出的进程的占用 CPU 百分比
hi	硬中断请求服务占用的 CPU 百分比
si	软中断请求服务占用的 CPU 百分比
st	st（ Steal time） 虚拟时间百分比。 就是当有虚拟 机时， 虚拟 CPU 等待实际 CPU 的时间百分比。
（4）第 4 行为物理内存信息
内容	说明
total	物理内存的总量， 单位 KB
used	已经使用的物理内存数量
free	空闲的物理内存重量
buffers	作为缓冲的内存数量
（5）第 5 行为交换分区信息
内容	说明
Swap: 524280k total	交换分区（虚拟内存） 的总大小
0k used	已经使用的交换分区的大小
524280k free	空闲交换分区的大小
409280k cached	作为缓存的交换分区的大小
（6）进程信息区
统计信息区域的下方显示了各个进程的详细信息，首先来认识一下各列的含义。

PID 进程 id
PPID 父进程 id
USER Real user name
UID 进程所有者的用户 id
USER 进程所有者的用户名
GROUP 进程所有者的组名
TTY 启动进程的终端名。不是从终端启动的进程则显示为 ?
PR 优先级
NI nice 值。负值表示高优先级，正值表示低优先级
P 最后使用的 CPU，仅在多 CPU 环境下有意义
%CPU 上次更新到现在的 CPU 时间占用百分比
TIME 进程使用的 CPU 时间总计，单位秒
TIME+ 进程使用的 CPU 时间总计，单位 1/100 秒
%MEM 进程使用的物理内存百分比
VIRT 进程使用的虚拟内存总量，单位 kb。VIRT=SWAP+RES
SWAP 进程使用的虚拟内存中，被换出的大小，单位 kb
RES 进程使用的、未被换出的物理内存大小，单位 kb，RES=CODE+DATA
CODE 可执行代码占用的物理内存大小，单位 kb
DATA 可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位 kb
SHR 共享内存大小，单位 kb
nFLT 页面错误次数
nDRT 最后一次写入到现在，被修改过的页面数。
S 进程状态。
D=不可中断的睡眠状态
R=运行
S=睡眠
T=跟踪/停止
Z=僵尸进程
COMMAND 命令名/命令行
5）2 个案例
（1）显示进程 1 的状态信息

top -p 进程id
图片

（2）2500 毫秒刷新一次 TOP 内容，总共 5 次，输出内容存放到 performace.txt 文件中

注：要将内容输出到文件中，必须使用 - b，表示批处理选项

top -b -d 2.5 -n 5 > performace.txt
（3）只显示活动中的进程

图片

4、netstat：显示网络状态和端口占用信息
1） 基本语法
命令	说明
netstat -anp | grep 进程号	查看该进程网络信息
netstat –nlp | grep 端口号	查看网络端口号占用情况
2）选项说明
选项	功能
-a	显示所有正在监听（listen） 和未监听的套接字（socket）
-n	拒绝显示别名， 能显示数字的全部转化成数字
-l	仅列出在监听的服务状态
-p	表示显示哪个进程在调用
3）连接状态详解
LISTEN： 侦听来自远方的 TCP 端口的连接请求
SYN-SENT： 再发送连接请求后等待匹配的连接请求
SYN-RECEIVED：再收到和发送一个连接请求后等待对方对连接请求的确认
ESTABLISHED： 代表一个打开的连接
FIN-WAIT-1： 等待远程 TCP 连接中断请求，或先前的连接中断请求的确认
FIN-WAIT-2： 从远程 TCP 等待连接中断请求
CLOSE-WAIT： 等待从本地用户发来的连接中断请求
CLOSING： 等待远程 TCP 对连接中断的确认
LAST-ACK： 等待原来的发向远程 TCP 的连接中断请求的确认
TIME-WAIT： 等待足够的时间以确保远程 TCP 接收到连接中断请求的确认
CLOSED： 没有任何连接状态
4） 案例实操
（1） 通过进程号查看 sshd 进程的网络信息
[root@testx java]# netstat -anp | grep sshd
tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      69266/sshd
tcp        0      0 192.168.200.135:22      192.168.200.1:64660     ESTABLISHED 5266/sshd: root@pts
tcp        0     52 192.168.200.135:22      192.168.200.1:51188     ESTABLISHED 117205/sshd: root@p
tcp        0      0 192.168.200.135:22      192.168.200.1:64607     ESTABLISHED 5104/sshd: root@pts
tcp6       0      0 :::22                   :::*                    LISTEN      69266/sshd
unix  3      [ ]         STREAM     CONNECTED     271445   69266/sshd
unix  2      [ ]         DGRAM                    478469   117205/sshd: root@p
unix  2      [ ]         DGRAM                    549896   5104/sshd: root@pts
unix  2      [ ]         DGRAM                    542430   5266/sshd: root@pts
（2） 查看 22 端口号是否被占用
[root@testx java]# netstat -nltp | grep 22
tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      69266/sshd
tcp6       0      0 :::22                   :::*                    LISTEN      69266/sshd