1、gzip/gunzip：压缩、解压
1）基本语法

命令	描述
gzip 文件	压缩文件，只能将文件压缩为*.gz 文件
gunzip 文件.gz	解压文件
2）经验技巧

只能压缩文件不能压缩目录
不保留原来的文件
同时多个文件会产生多个压缩包
3）案例

（1）gzip 压缩文件

压缩后，源文件会消失

[root@testx b]# ls
spring.log
[root@testx b]# gzip spring.log
[root@testx b]# ls
spring.log.gz
（2）gunzip 解压文件

解压后，源文件会消失

[root@testx b]# ls
spring.log.gz
[root@testx b]# gunzip spring.log.gz
[root@testx b]# ls
spring.log
2、zip/unzip：压缩、解压
1）基本语法

语法	描述
zip [选项] xxx.zip 要压缩的文件列(可以有多个)	压缩文件和目录的命令
unzip [选项] xxx.zip	解压文件
2）选项说明

zip 选项	功能
-r	压缩目录
unzip 选项	功能
-d 目录	指定解压后文件的存放目录
3）经验技巧

zip 压缩命令在 windows/linux 都通用，可以压缩目录且保留源文件。

4）案例

（1）将 1.log 和 2.log 压缩到 package.zip 中

[root@testx b]# ls
1.log  2.log
[root@testx b]# zip package.zip 1.log 2.log
adding: 1.log (deflated 75%)
adding: 2.log (deflated 75%)
[root@testx b]# ls
1.log  2.log  package.zip
（2）将 log 目录压缩到 log.zip 中

# 当前目录有3个文件
[root@testx log]# ls
1.log  2.log  package.zip
# 删除2个log文件，保留等待解压的package.zip
[root@testx log]# rm -rf 1.log 2.log
# 剩下带解压的package.zip
[root@testx log]# ls
package.zip
# 对其进行解压，默认会解压在当前目录
[root@testx log]# unzip package.zip
Archive:  package.zip
inflating: 1.log
inflating: 2.log
# 当前目录有3个文件了
[root@testx log]# ls
1.log  2.log  package.zip
（3）将 package.zip 文件解压到 logs 目录

[root@testx log]# ls
package.zip
# 创建一个 logs 目录，用来存放稍后解压的文件
[root@testx log]# mkdir logs
[root@testx log]# ls
logs  package.zip
# 将package.zip解压到logs目录
[root@testx log]# unzip -d logs/ package.zip
Archive:  package.zip
inflating: logs/1.log
inflating: logs/2.log
[root@testx log]# ls logs/
1.log  2.log
（4）将 log 目录压缩到 log.zip 中

[root@testx b]# ls
log
[root@testx b]# ls log
1.log  2.log
[root@testx b]# zip -r log.zip log
adding: log/ (stored 0%)
adding: log/1.log (deflated 75%)
adding: log/2.log (deflated 75%)
[root@testx b]# ls
log  log.zip
3、tar：打包、解压（常用）
1）基本语法

tar [选项] xxx.tar.gz 需要打包的文件列表
打包文件或者目录，压缩后的文件格式为 tar.gz

2）选项说明

选项	说明
-c	产生.tar 打包文件
-v	显示详细信息
-f	指定文件名
-z	用 gzip 对文件进行压缩或者解压
-x	解包.tar 文件
-C	解压到指定目录
3）案例

（1）tar -czvf：压缩多个文件

语法：tar -czvf 打包后的文件.tar.gz 被打包的文件列表

[root@testx b]# ls
1.log  2.log
[root@testx b]# tar -czvf log.tar.gz 1.log 2.log
1.log
2.log
[root@testx b]# ls
1.log  2.log  log.tar.gz
（2）tar -czvf：打包目录

语法：tar -czvf 打包后的文件.tar.gz 被打包的目录

[root@testx b]# ls
logs
[root@testx b]# ls logs/
1.log  2.log
# 将logs目录打包为log.tar.gz文件
[root@testx b]# tar -czvf log.tar.gz logs/
logs/
logs/1.log
logs/2.log
[root@testx b]# ls
logs  log.tar.gz
（3）tar -xzvf：解压文件，默认解压到当前目录

语法：tar -xzvf 被解压的文件

[root@testx b]# ls
log.tar.gz
[root@testx b]# tar -xzvf log.tar.gz
logs/
logs/1.log
logs/2.log
[root@testx b]# ls
logs  log.tar.gz
[root@testx b]# ls logs/
1.log  2.log
（3）tar -xzvf：解压文件到指定的目录

语法：tar -xzvf 被解压的文件 -C 解压后的目标目录

[root@testx b]# ls
log.tar.gz
[root@testx b]# mkdir target
[root@testx b]# tar -xzvf log.tar.gz -C target/
logs/
logs/1.log
logs/2.log
[root@testx b]# ls
log.tar.gz  target
[root@testx b]# ls target/
logs
[root@testx b]# ls target/logs/
1.log  2.log