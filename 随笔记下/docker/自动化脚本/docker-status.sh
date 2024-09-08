#!/bin/bash

function keep_docker_service_active()
{
    result=`systemctl is-active docker`

    # result: active or inactive
    if [[ $result = "active" ]]; then
        echo "[-] docker 正在运行"
        exit
    fi

    echo "[-] docker 没有运行"
    echo "[-] 执行重启..."
    systemctl restart docker
    echo "[-] Done!"
    echo `systemctl status docker | awk '/Active/'`
}

function main()
{
    keep_docker_service_active
}

main


