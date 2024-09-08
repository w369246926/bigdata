#!/bin/bash

# 检查是否以root权限运行
if [ "$EUID" -ne 0 ]
  then echo "请以root权限运行此脚本"
  exit
fi

echo "开始在主节点上安装额外的Cloudera Manager组件..."

# 安装Cloudera Manager Server
rpm -ivh /opt/cdhfile/cloudera-manager/cloudera-manager-server-6.3.1-1466458.el7.x86_64.rpm --nodeps --force

# 移动parcel-repo文件
mv /opt/cdhfile/parcel-repo* /opt/cloudera/

# 更新Cloudera Manager Server数据库配置
cat > /etc/cloudera-scm-server/db.properties << EOF
com.cloudera.cmf.db.type=mysql
com.cloudera.cmf.db.host=hadoop101:3306
com.cloudera.cmf.db.name=scm
com.cloudera.cmf.db.user=scm
com.cloudera.cmf.db.password=scm
com.cloudera.cmf.db.setupType=EXTERNAL
EOF

echo "主节点Cloudera Manager Server安装和配置完成。"
echo "已更新数据库配置文件 /etc/cloudera-scm-server/db.properties。"
