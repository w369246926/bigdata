package com.flink.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class 时间戳 {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String msgContent = "43421342342452345345dfsdf";
    String[] split = msgContent.split("\\|");
    long l = Long.parseLong(split[1]);//时间毫秒数
    Date date = new Date(l);
    String format1 = format.format(date);
}
