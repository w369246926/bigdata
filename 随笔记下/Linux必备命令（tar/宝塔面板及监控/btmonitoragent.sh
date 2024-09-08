#!/bin/bash
#########################

# 广东堡塔安全技术有限公司
# author: 赤井秀一
# mail: 1021266737@qq.com

#########################

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

Command_Exists() {
    command -v "$@" >/dev/null 2>&1
}

monitor_path="/www/server/bt-monitor"
get_node_url(){
	if [ ! -f /bin/curl ];then
		if [ "${PM}" = "yum" ]; then
			yum install curl -y
		elif [ "${PM}" = "apt-get" ]; then
			apt-get install curl -y
		fi
	fi

	if [ -f "/www/node.pl" ];then
		download_Url=$(cat /www/node.pl)
		echo "Download node: $download_Url";
		echo '---------------------------------------------';
		return
	fi
	
	echo '---------------------------------------------';
	echo "Selected download node...";
	#nodes=(http://dg2.bt.cn http://dg1.bt.cn http://125.90.93.52:5880 http://36.133.1.8:5880 http://123.129.198.197 http://38.34.185.130 http://116.213.43.206:5880 http://128.1.164.196);
	# nodes=(http://dg2.bt.cn http://dg1.bt.cn http://125.90.93.52:5880 http://36.133.1.8:5880 http://123.129.198.197 http://116.213.43.206:5880);
	nodes=(https://dg2.bt.cn https://dg1.bt.cn https://download.bt.cn);
	tmp_file1=/dev/shm/net_test1.pl
	tmp_file2=/dev/shm/net_test2.pl
	[ -f "${tmp_file1}" ] && rm -f ${tmp_file1}
	[ -f "${tmp_file2}" ] && rm -f ${tmp_file2}
	touch $tmp_file1
	touch $tmp_file2
	for node in ${nodes[@]};
	do
		NODE_CHECK=$(curl --connect-timeout 3 -m 3 2>/dev/null -w "%{http_code} %{time_total}" ${node}/net_test|xargs)
		RES=$(echo ${NODE_CHECK}|awk '{print $1}')
		NODE_STATUS=$(echo ${NODE_CHECK}|awk '{print $2}')
		TIME_TOTAL=$(echo ${NODE_CHECK}|awk '{print $3 * 1000 - 500 }'|cut -d '.' -f 1)
		if [ "${NODE_STATUS}" == "200" ];then
			if [ $TIME_TOTAL -lt 100 ];then
				if [ $RES -ge 1500 ];then
					echo "$RES $node" >> $tmp_file1
				fi
			else
				if [ $RES -ge 1500 ];then
					echo "$TIME_TOTAL $node" >> $tmp_file2
				fi
			fi

			i=$(($i+1))
			if [ $TIME_TOTAL -lt 100 ];then
				if [ $RES -ge 3000 ];then
					break;
				fi
			fi	
		fi
	done

	NODE_URL=$(cat $tmp_file1|sort -r -g -t " " -k 1|head -n 1|awk '{print $2}')
	if [ -z "$NODE_URL" ];then
		NODE_URL=$(cat $tmp_file2|sort -g -t " " -k 1|head -n 1|awk '{print $2}')
		if [ -z "$NODE_URL" ];then
			NODE_URL='https://download.bt.cn';
		fi
	fi
	rm -f $tmp_file1
	rm -f $tmp_file2
	download_Url=$NODE_URL
	echo "Download node: $download_Url";
	echo '---------------------------------------------';
}

Get_Pack_Manager(){
	if [ -f "/usr/bin/yum" ] && [ -d "/etc/yum.repos.d" ]; then
		PM="yum"
	elif [ -f "/usr/bin/apt-get" ] && [ -f "/usr/bin/dpkg" ]; then
		PM="apt-get"		
	fi
}

target_dir="/usr/local/btmonitoragent"
init_sh="/etc/init.d/btmonitoragent"

# 下载BT_Monitor_Agent
# 云端文件名格式：
# version变量定义版本信息
# file_name变量定义文件名称
# 例子：btmonitoragent-1.0.0.zip
Download_Agent(){
    version="1.0"
    file_name="btmonitoragent"
    agent_src="btmonitoragent.zip"

    if [ -d "$target_dir" ]; then
        rm -rf $target_dir
    fi
    cd /tmp
    wget -O $agent_src $download_Url/install/src/$file_name-$version.zip -t 5 -T 10
    tmp_size=$(du -b $agent_src|awk '{print $1}')
    if [ $tmp_size -lt 10703460 ];then
        rm -f $agent_src
        echo -e "\033[31mERROR: 下载云监控客户端失败，请尝试重新安装！\033[0m"
        exit 1
    fi

    echo "正在解压云监控客户端..."
    unzip -d /usr/local/ $agent_src > /dev/null 2>&1
    rm -rf $agent_src

    if [ ! -f "$target_dir/BT-MonitorAgent" ];then
        rm -f $target_dir
        echo -e "\033[31mERROR: 解压云监控客户端失败，请尝试重新安装！\033[0m"
        exit 1
    else
        chmod -R 755 $target_dir
        chown -R root.root $target_dir
        chmod +x $target_dir/BT-MonitorAgent
        chmod -R +x $target_dir/plugin
    fi
}

# 安装bt_monitor_agent
Install_Monitor_Agent(){
    # Timezones_Check
    Download_Agent

    btmonitoragent_conf="/usr/local/btmonitoragent/config/config.json"

    if Command_Exists curl; then
        curl -fsSL --connect-time 10 --retry 5 -k $action/api/bind > $btmonitoragent_conf
    else
        wget -O $btmonitoragent_conf $action/api/bind -t 5 -T 10 --no-check-certificate
    fi

    chmod -R 755 $btmonitoragent_conf
    chown -R root.root $btmonitoragent_conf

    if [ ! -d "/etc/init.d" ];then
		mkdir -p /etc/init.d
	fi

    wget -O $init_sh ${download_Url}/init/btmonitoragent.init -t 5 -T 10
    chmod +x $init_sh
    ln -sf $init_sh /usr/bin/btmonitoragent

    Service_Add
    Set_Crontab
    /usr/bin/btmonitoragent restart
    if [ "$?" != "0" ]; then
        echo ""
        echo -e "\033[31m堡塔云监控客户端安装失败！\033[0m"
        exit 1
    else
        echo "堡塔云监控客户端安装成功！"
    fi
    LOCAL_IP=$(ip addr | grep -E -o '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}' | grep -E -v "^127\.|^255\.|^0\." | head -n 1)
    curl -o /dev/null -fsSL --connect-time 10 "https://www.bt.cn/api/wpanel/SetupCountCloud?cloud_type=2&token=$md5_pl&ip_address=$LOCAL_IP" > /dev/null 2>&1
}

Service_Add(){
    if [ $Command_Exists systemctl ]; then
        wget -O /usr/lib/systemd/system/btmonitoragent.service ${download_Url}/init/systemd/btmonitoragent.service -t 5 -T 10
        systemctl enable btmonitoragent
    else
        if [ "${PM}" == "yum" ] || [ "${PM}" == "dnf" ]; then
            chkconfig --add btmonitoragent
            chkconfig --level 2345 btmonitoragent on            
        elif [ "${PM}" == "apt-get" ]; then
            update-rc.d btmonitoragent defaults
        fi
    fi
}

Service_Del(){
    if [ $Command_Exists systemctl ]; then
        systemctl disable btmonitoragent
        [ -f "/usr/lib/systemd/system/btmonitoragent.service" ] && rm -rf /usr/lib/systemd/system/btmonitoragent.service
    else
        if [ "${PM}" == "yum" ] || [ "${PM}" == "dnf" ]; then
            chkconfig --del btmonitoragent
            chkconfig --level 2345 btmonitoragent off
        elif [ "${PM}" == "apt-get" ]; then
            update-rc.d btmonitoragent remove
        fi
    fi
}

Timezones_Check(){
    if [[ "$action" =~ "127.0.0.1" ]];then
        echo "检测到地址是127.0.0.1,本机安装,跳过时区检测"
        return
    fi
    # bind_conf="curl -s -k https://192.168.99.100:806/api/bind|grep -E -o '\+[0-9]{4}'"
    bind_conf=`curl -s -k $action/api/bind|awk -F '/' '{print $6}'|awk -F '\"' '{print $1}'`
    list_timectl=`timedatectl list-timezones | grep $bind_conf`
    local_timectl=`timedatectl | grep 'Time zone' | grep $bind_conf`
    if [ -z "$bind_conf" ]; then 
        echo -e "\033[31m错误：无法获取主监控服务器的时区，请检查与主监控服务器连接是否正常！\033[0m"
        exit 1
    fi
    if [ -z "$local_timectl" ]; then
        echo ""
        echo "被控服务器时区与主监控服务器时区不一致，将自动设置成与主监控服务器一致的时区！"
        sleep 1
        timedatectl set-timezone $list_timectl
        if [ "$?" != "0" ]; then
            echo -e "\033[31m错误：时区设置错误，请手动将当前服务器时区设置与主监控服务器时区一致！\033[0m"
            echo -e "\033[31m错误：主监控服务器时区是$list_timectl \033[0m"
            exit 1
        fi
        echo "已将当前服务器时区设置为：$list_timectl"
        echo ""
    fi
}

Install_RPM_Pack(){
	yumPacks="wget curl unzip crontabs"
	yum install -y ${yumPacks}

	for yumPack in ${yumPacks}
	do
		rpmPack=$(rpm -q ${yumPack})
		packCheck=$(echo ${rpmPack}|grep not)
		if [ "${packCheck}" ]; then
			yum install ${yumPack} -y
		fi
	done
}

Install_Deb_Pack(){
    debPacks="wget curl unzip cron";
	apt-get install -y $debPacks --force-yes

	for debPack in ${debPacks}
	do
		packCheck=$(dpkg -l ${debPack})
		if [ "$?" -ne "0" ] ;then
			apt-get install -y $debPack
		fi
	done
}

Connent_Test(){
    for ((i=1; i<=10; i++));
    do
        http_code=$(curl --connect-time 10 --retry 5 -s -o /dev/null -k -w %{http_code} ${action})
        if [ "$http_code" == "000" ]; then
            echo "本机连接服务器的状态码是: ${http_code}"
            echo "---------------------------------------------------"
            echo -e "\033[31mError-错误: 本机与云监控服务端通信失败!\033[0m"
            echo ""
            echo "请检查本机与云监控服务端的${panelPort}端口是否正常通信!"
            echo "---------------------------------------------------"
            exit 1
        fi
    done

}

get_pids(){
    monitoragent_pid=$(ps aux |grep 'BT-MonitorAgent'|grep -v grep|awk '{print $2}')
    networktraffic_pid=$(ps aux |grep 'networktraffic'|grep -v grep|awk '{print $2}')
}

Set_Crontab(){
    crond_text='*/1 * * * * /bin/bash /usr/local/btmonitoragent/crontab_tasks/btm_agent_runfix.sh 2>&1'
    [ ! -d "$target_dir/crontab_tasks" ] && mkdir -p $target_dir/crontab_tasks
    wget -O $target_dir/crontab_tasks/btm_agent_runfix.sh ${download_Url}/tools/btm_agent_runfix.sh -t 5 -T 10
    echo -e "正在添加被控端守护任务...\c"
    if [ $PM = "yum" ]; then
        sed -i "/btm_agent_runfix/d" /var/spool/cron/root
        echo "$crond_text" >> "/var/spool/cron/root"
        systemctl restart crond
    else
        sed -i "/btm_agent_runfix/d" /var/spool/cron/crontabs/root
        echo "$crond_text" >> "/var/spool/cron/crontabs/root"
        systemctl restart cron
    fi
    echo -e "   \033[32mdone\033[0m"
}

Uninstall_Monitor_Agent(){
    /etc/init.d/btmonitoragent stop
    get_pids
    arr=($monitoragent_pid)
    if [ ! -z $arr ]; then
        for p in ${arr[@]}
        do
            kill -9 $p
        done
    fi
    arr1=($networktraffic_pid)
    if [ ! -z $arr1 ]; then
        for b in ${arr1[@]}
        do
            kill -9 $b
        done
    fi

    rm -rf $target_dir
    rm -rf $init_sh
    
    Service_Del
    echo -e "堡塔云监控agent端卸载成功!"
}

action="${1}"
md5_pl="${2}"
if [ "$action" == "uninstall" ];then
    Uninstall_Monitor_Agent
elif [[ "$action" =~ "https" ]];then
    get_pids
    if [[ ! -z "$monitoragent_pid" ]]; then
        echo "已存在旧的Agent端,即将卸载旧程序..."
        Uninstall_Monitor_Agent
    fi
    if [ -f "/www/server/bt-monitor/BT-MONITOR" ] || [ -f "/www/server/bt-monitor/tools.py" ] || [ -d "/www/server/bt-monitor" ];then
        c_port=$(cat $monitor_path/config/config.json |awk -F '\"port\"' '{print $2}'|awk -F ":" '{print $2}'|awk -F '"' '{print $1}')
        panelPort=$(echo $c_port|awk -F ',' '{print $1}')
        action="https://127.0.0.1:${panelPort}"
    fi
    Connent_Test
    get_node_url
    Get_Pack_Manager
    if [ $PM = "yum" ]; then
        Install_RPM_Pack
    else
        Install_Deb_Pack
    fi
    Install_Monitor_Agent
elif [ -z "$action" ];then
    echo -e "\033[31m请输入正确的服务端地址,举例如下:\033[0m"
    echo ""
    echo "sh btmonitoragent.sh https://192.168.1.100:806 2bf13044c5e1eafc"
    echo ""
fi