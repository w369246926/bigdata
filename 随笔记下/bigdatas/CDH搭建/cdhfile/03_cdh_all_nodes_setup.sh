#!/bin/bash

# 检查是否以root权限运行
if [ "$EUID" -ne 0 ]
  then echo "请以root权限运行此脚本"
  exit
fi

echo "开始在所有节点上安装Cloudera Manager组件..."

# 安装Cloudera Manager Daemons
rpm -ivh /opt/cdhfile/cloudera-manager/cloudera-manager-daemons-6.3.1-1466458.el7.x86_64.rpm --nodeps --force

# 安装Cloudera Manager Agent
rpm -ivh /opt/cdhfile/cloudera-manager/cloudera-manager-agent-6.3.1-1466458.el7.x86_64.rpm --nodeps --force

# 更新Cloudera Manager Agent配置
sed -i 's/server_host=localhost/server_host=hadoop101/' /etc/cloudera-scm-agent/config.ini

echo "Cloudera Manager组件安装完成。"
echo "已将Cloudera Manager Agent配置中的server_host更新为hadoop101。"
