#!/bin/bash

# 检查是否以root权限运行
if [ "$EUID" -ne 0 ]
  then echo "请以root权限运行此脚本"
  exit
fi

echo "开始CentOS环境配置..."

# 更新CentOS 7 yum仓库配置
cat > /etc/yum.repos.d/CentOS-Base.repo << EOF
[base]
name=CentOS-\$releasever - Base
baseurl=http://vault.centos.org/7.9.2009/os/\$basearch/
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

[updates]
name=CentOS-\$releasever - Updates
baseurl=http://vault.centos.org/7.9.2009/os/\$basearch/
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

[extras]
name=CentOS-\$releasever - Extras
baseurl=http://vault.centos.org/7.9.2009/os/\$basearch/
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

[centosplus]
name=CentOS-\$releasever - Plus
baseurl=http://vault.centos.org/7.9.2009/os/\$basearch/
gpgcheck=1
enabled=0
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
EOF

# 清理 yum 缓存并更新
yum clean all
yum makecache

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

echo "CentOS环境配置完成！"
