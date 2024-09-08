//package com.flink.dcyw.dao;
//
//
//
//import com.flink.dcyw.bean.WifiApClientBean;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@Mapper
//public interface WifiApClientDWDao {
//
//	void insertBatch(ArrayList<WifiApClientBean> apClientList);
//
//	List<WifiApClientBean> selectCorresponding1(@Param("mac")String mac, @Param("updateTime")String updateTime);
//
//	List<WifiApClientBean> selectCorresponding(HashMap<String,Object> paramMap);
//	List<WifiApClientBean> currentConnectionInfo(WifiProbeParam wifiProbeParam);
//
//	List<WifiApClientBean> hisConnectionInfo(WifiProbeParam wifiProbeParam);
//
//	WifiApClientBean selectCurrentClient(WifiProbeParam wifiProbeParam);
//
//	List<String> getHisConnMacList(WifiProbeParam wifiProbeParam);
//
//	String selectCurrentConnMac(WifiProbeParam wifiProbeParam);
//
//	List<WifiApClientBean> selectSingalCorresponding(WifiProbeParam wifiProbeParam);
//}
