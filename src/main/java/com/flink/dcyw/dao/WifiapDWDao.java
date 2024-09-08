//package com.flink.dcyw.dao;
//
//
//import com.baomidou.mybatisplus.mapper.BaseMapper;
//import com.flink.dcyw.bean.WifiApBean;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@Mapper
//public interface WifiapDWDao extends BaseMapper<WifiApBean> {
//
//	ArrayList<WifiApBean> selectWifiApEveryDay(WifiProbeParam wifiProbeParam);
//
//	String selectLatestUpdateTime();
//
//	void insertBatch(ArrayList<WifiApBean> apList);
//
////	List<String> selectMacAndDeviceId(@Param("strATime")String strATime);
//	List<String> selectMacAndDeviceId(@Param("beginDate")String beginDate,@Param("endDate")String endDate);
//
//
//
//	List<String> selectEncryption();
//
//	List<String> selectApType();
//
//	List<WifiApBean> selectLatestUpdate(@Param("updateTime")String updateTime);
//
//	void updateApCount(HashMap<String, Object> map);
//
//	Integer countNewAp(@Param("updateTime")String updateTime);
//
//	Integer countNormalAp(@Param("updateTime")String updateTime);
//
//	Integer countCheckedAp(@Param("updateTime")String updateTime);
//
//	Integer selectDistribution(@Param("updateTime")String updateTime);
//
//	Integer selectDistribution1(@Param("updateTime")String updateTime);
//
//	Integer countEncryption(@Param("encryption")String encryption,@Param("updateTime") String updateTime);
//
//	List<String> selectEncryptionWithTime(@Param("updateTime")String updateTime);
//
//	List<ApTypeCountVo> selectApTypeCount(String updateTime);
//
//	WifiApBean selectWifiApById(Integer id);
//
//	List<String> selectXTime(@Param("apUpdateTime")String apUpdateTime,@Param("mac")String mac);
//
//	List<Integer> selectChannelList(Integer freqBand);
//
//	//Integer selectChannelByMac(HashMap<String,Object> paramMap);
//	List<HashMap<String,Object>> selectChannelByMac(@Param("mac")String mac,@Param("freqBand")Integer freqBand, @Param("updateTimeList")List<String> updateTimeList);
//
//	List<Integer>  selectPowerByCurrentAp(@Param("mac")String mac, @Param("updateTimeList")List<String> updateTimeList);
//
////	Integer selectIcountByCurrentAp(@Param("mac")String mac,@Param("updateTime")String updateTime);
//
//	List<FluxRes> selectFluxByCurrentAp(@Param("mac")String mac, @Param("updateTimeList")List<String> updateTimeList);
//
//    List<ApRankVo> apIcountRank();
//
//    List<String> selectXListByTime(WifiProbeParam wifiProbeParam);
//
//	int selectIsNewAp(@Param("mac")String mac,@Param("isNewTime")String isNewTime);
//
//    List<WifiApBean> selectApList(@Param("updateTime")String updateTime);
//
//	String selectSecondLateUpdateTime();
//
//	String selectCurrentConnMac(WifiProbeParam wifiProbeParam);
//
//	String getManufacturerbyMac(String prefix);
//
//	List<WifiApBean> selectForPage(WifiProbeParam wifiProbeParam);
//
//	List<WifiApBean> page(WifiProbeParam query);
//
//	Integer resCount(WifiProbeParam query);
//
////    List<String> selectApMacList(@Param("updateTime")String updateTime,@Param("deviceId")Integer deviceId);
//    List<String> selectApMacList(HashMap<String,Object> map);
//
//	List<WifiApBean> selectNewAp(String updateTime);
//
//    List<String> selectApMacList1(HashMap<String, Object> map);
//
//	List<String> selectAllApMac();
//
//	List<WifiApBean> selectNewAp1(String updateTime);
//
//	WifiApBean selectAp(WifiProbeParam wifiProbeParam);
//}
