package com.flink.dcyw.dao;

import com.flink.dcyw.WifiProbeForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WifiProbe1Dao {

    List<WifiProbeForm> selectWifiProbeHoursAgo(@Param("timeInMillis") long timeInMillis);

    List<WifiProbeForm> selectWifiProbeFromid(@Param("id") long id);
    
    void deleteDataByTime(long timeInMillis);
}
