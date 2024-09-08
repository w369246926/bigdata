#!/bin/bash

while true; do
    # 使用 top 命令获取所有进程的信息，以及以%CPU降序排序
    processes=$(top -b -n 1 -o %CPU | tail -n +8)
    
    # 遍历所有进程
    while IFS= read -r line; do
        # 提取进程名称、PID 和 CPU 使用率
        process_name=$(echo "$line" | awk '{print $12}')
        pid=$(echo "$line" | awk '{print $1}')
        cpu_usage=$(echo "$line" | awk '{print $9}')
        
        # 判断 CPU 使用率是否超过 100%
        if (( $(echo "$cpu_usage > 100" | bc -l) )); then
            echo "Process $process_name (PID: $pid) is using too much CPU ($cpu_usage%)"
            
            # 杀死进程
            echo "Killing process $process_name (PID: $pid)"
            kill -9 $pid
        fi
    done <<< "$processes"
    
    # 每隔一段时间进行检测
    sleep 5
done

