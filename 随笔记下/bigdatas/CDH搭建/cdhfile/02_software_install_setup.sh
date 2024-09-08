#!/bin/bash

# 检查是否以root权限运行
if [ "$EUID" -ne 0 ]
  then echo "请以root权限运行此脚本"
  exit
fi

echo "开始软件安装和配置..."

# 移除所有Java包
rpm -qa | grep -i 'java' | xargs -n1 rpm -e --nodeps

# 安装Cloudera的Java
rpm -ivh /opt/cdhfile/oracle-j2sdk1.8-1.8.0+update181-1.x86_64.rpm

# 配置Java环境变量
cat << 'EOF' >> /etc/profile
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

# 移动parcel-repo文件
mv /opt/cdhfile/mysql-connector-java.jar /usr/share/java

echo "软件安装和配置完成！"

# 获取并显示MySQL初始密码
echo "MySQL的初始密码是："
grep 'temporary password' /var/log/mysqld.log | awk '{print $NF}'

echo "请使用上述密码登录MySQL并及时修改密码。"
echo "建议使用以下命令登录MySQL并修改密码："
echo "mysql -uroot -p"
echo "ALTER USER 'root'@'localhost' IDENTIFIED BY '新密码';"
