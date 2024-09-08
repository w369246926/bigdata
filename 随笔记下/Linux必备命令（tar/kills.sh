#!/bin/bash

while true; do
    # 获取所有进程的信息，并按照 CPU 使用率降序排序
    processes=$(ps aux --sort=-%cpu)
    
    # 遍历所有进程
    while IFS= read -r line; do
        # 提取进程名称、PID 和 CPU 使用率
        process_name=$(echo "$line" | awk '{print $11}')
        pid=$(echo "$line" | awk '{print $2}')
        cpu_usage=$(echo "$line" | awk '{print $3}')
        
        # 判断 CPU 使用率是否超过 150%
        if (( $(echo "$cpu_usage > 150" | bc -l) )); then
            echo "Process $process_name (PID: $pid) is using too much CPU ($cpu_usage%)"
            
            # 杀死进程
            echo "Killing process $process_name (PID: $pid)"
            kill -9 $pid
        fi
    done <<< "$processes"
    
    # 每隔一段时间进行检测  查看该进程PID pgrep -f kills.sh
    sleep 5
done

