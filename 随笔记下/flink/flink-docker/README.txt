flink,docker部署
更新下载源
apt-get update
apt-get install iputils-ping
apt-get vim
将宿主机hosts内ip写到容器内的hosts
vim /etc/hosts
192.168.88.161 hadoop101......

报错:
connecting to node node3:9092 (id: 2 rack: null) java.net.UnknownHostException: node3
更改容器中的vim /etc/hosts
      192.168.88.161 hadoop101......

本地代码无法连接到kafka:超时问题
开启集群内所有卡夫卡试试

