#!/bin/bash

# 检查是否以root权限运行
if [ "$EUID" -ne 0 ]
  then echo "请以root权限运行此脚本"
  exit
fi

# 设置Transparent Huge Pages
echo never > /sys/kernel/mm/transparent_hugepage/defrag
echo never > /sys/kernel/mm/transparent_hugepage/enabled

# 设置vm.swappiness
echo 'vm.swappiness=10' >> /etc/sysctl.conf
sysctl -p

# 停止firewalld服务
systemctl stop firewalld
systemctl disable firewalld

# 添加主机名映射
cat << EOF >> /etc/hosts
192.168.88.101 hadoop101 hadoop101
192.168.88.102 hadoop102 hadoop102
192.168.88.103 hadoop103 hadoop103
192.168.88.104 hadoop104 hadoop104
192.168.88.105 hadoop105 hadoop105
192.168.88.106 hadoop106 hadoop106
192.168.88.107 hadoop107 hadoop107
EOF

# 启动NTP服务
systemctl start ntpd
systemctl enable ntpd

# 生成SSH密钥
ssh-keygen -t rsa -N "" -f ~/.ssh/id_rsa

# 移除所有Java包
rpm -qa | grep -i 'java' | xargs -n1 rpm -e --nodeps

# 安装Cloudera的Java
rpm -ivh /opt/cdhfile/oracle-j2sdk1.8-1.8.0+update181-1.x86_64.rpm

# 配置Java环境变量
cat << EOF >> /etc/profile
export JAVA_HOME=/usr/java/jdk1.8.0_181-cloudera
export CLASSPATH=.:$CLASSPATH:$JAVA_HOME/lib
export PATH=$PATH:$JAVA_HOME/bin
EOF

# 应用环境变量
source /etc/profile

# 移除所有MySQL包和库
yum -y remove mysql-libs*
rpm -qa | grep -i 'mysql' | xargs -n1 rpm -e --nodeps

# 安装MySQL 8.0
yum localinstall -y /opt/cdhfile/mysql80-community-release-el7-3.noarch.rpm
rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2023
yum install -y mysql-community-server

# 启动并启用MySQL服务
systemctl start mysqld
systemctl enable mysqld

echo "CDH集群必要设置已完成！"
echo "请注意检查MySQL的初始密码，通常可以在 /var/log/mysqld.log 文件中找到。"
