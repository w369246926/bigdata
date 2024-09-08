创建目录:
mkdir -p /data/minio_data/
查看磁盘挂载清空
df -h
若没有可挂载盘,选择可以卸载的盘符
卸载:
umount /dev/sda5 /home
挂载到目录
mount /dev/sda5 /data/minio_data/
查看
lsblk
创建启动脚本目录
mkdir /opt/minio/
vim /opt/minio/run.sh

#!/bin/bash
export MINIO_ACCESS_KEY=Xxyminio
export MINIO_SECRET_KEY=Test123456

/opt/minio/minio server \
http://172.16.101.27/data/minio_data/data1 http://172.16.101.31/data/minio_data/data1 \
http://172.16.101.46/data/minio_data/data1 http://172.16.101.47/data/minio_data/data1

增加启动权限
chmod -R 777 minio/
编写服务脚本
vim /usr/lib/systemd/system/minio.service

[Unit]
Description=Minio service
Documentation=https://docs.minio.io/

[Service]
WorkingDirectory=/opt/minio/
ExecStart=/opt/minio/run.sh

Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target

启动服务
[root@localhost minio]# systemctl daemon-reload
[root@localhost minio]#
[root@localhost minio]# systemctl start minio
[root@localhost minio]#
[root@localhost minio]# systemctl status minio
● minio.service - Minio service
Loaded: loaded (/usr/lib/systemd/system/minio.service; disabled; vendor preset: disabled)
Active: active (running) since Wed 2020-04-22 10:32:54 CST; 44s ago
Docs: https://docs.minio.io/
Main PID: 1569 (run.sh)
CGroup: /system.slice/minio.service
├─1569 /bin/bash /opt/minio/run.sh
└─1570 /opt/minio/minio server http://192.168.129.133/data/minio_data/data1 http://192.168.129.135/data/minio_data/data1 http://192....

Apr 22 10:32:54 localhost.localdomain systemd[1]: Started Minio service.
Apr 22 10:33:17 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 18s)
Apr 22 10:33:20 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 21s)
Apr 22 10:33:23 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 24s)
Apr 22 10:33:26 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 27s)
Apr 22 10:33:29 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 30s)
Apr 22 10:33:32 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 33s)
Apr 22 10:33:35 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 36s)
Apr 22 10:33:38 localhost.localdomain run.sh[1569]: Waiting for a minimum of 2 disks to come online (elapsed 39s)
[root@localhost minio]# 