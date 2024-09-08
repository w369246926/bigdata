#!/bin/bash  
function docker_server_install()
{
#yum install -y gcc
#yum install -y gcc-c++
#yum install -y yum-utils

#rpm -ivh *.rpm --force --nodeps

#systemctl start docker

doc

echo `systemctl status docker | awk '/Active/'`

#docker load < images.tar
#docker load < clickhouse-images.tar

echo `docker images`

chmod +x /usr/local/bin/docker-compose

echo `docker-compose --version`

#docker-compose up -d 

}
function main()
{
    docker_server_install
}

main

