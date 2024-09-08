//package com.flink.dcyw;
//
////import cn.itcast.connectors.ckSink;
////import org.apache.flink.api.common.RuntimeExecutionMode;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.flink.dcyw.bean.WhiteListBean;
//import com.flink.dcyw.bean.WifiApBean;
//import com.flink.dcyw.bean.WifiApClientBean;
//import com.flink.dcyw.dao.WhiteListDao;
//
//import com.flink.jdbc.utils.JDBCUtils;
//import org.apache.flink.api.common.functions.FlatMapFunction;
//import org.apache.flink.api.java.tuple.Tuple15;
//import org.apache.flink.api.java.tuple.Tuple4;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
//import org.apache.flink.util.Collector;
//import org.apache.ibatis.session.RowBounds;
//
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.io.UnsupportedEncodingException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.sql.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.Date;
//
///**
// * Author itcast
// * Desc 演示Flink-Connectors-KafkaComsumer/Source
// */
//public class Flinkreadtxt {
//    public static void main(String[] args) throws Exception {
//        //TODO 0.env
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
//
//        //TODO 1.source
//        DataStream<String> ds1 = env.readTextFile("data/input/wifiProbe.txt");
//        //TODO 2.transformation
//
//        final Base64.Decoder decoder = Base64.getDecoder();
//        //定义时间
//        long start = System.currentTimeMillis();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        //接口对象
//
//
//        WifiapDWDao wifiapDWDao  = new WifiapDWDao() {
//            @Override
//            public ArrayList<WifiApBean> selectWifiApEveryDay(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public String selectLatestUpdateTime() {
//                return null;
//            }
//
//            @Override
//            public void insertBatch(ArrayList<WifiApBean> apList) {
//
//            }
//
//            @Override
//            public List<String> selectMacAndDeviceId(String beginDate, String endDate) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectEncryption() {
//                return null;
//            }
//
//            @Override
//            public List<String> selectApType() {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectLatestUpdate(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public void updateApCount(HashMap<String, Object> map) {
//
//            }
//
//            @Override
//            public Integer countNewAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer countNormalAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer countCheckedAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer selectDistribution(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer selectDistribution1(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer countEncryption(String encryption, String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectEncryptionWithTime(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<ApTypeCountVo> selectApTypeCount(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectWifiApById(Integer id) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectXTime(String apUpdateTime, String mac) {
//                return null;
//            }
//
//            @Override
//            public List<Integer> selectChannelList(Integer freqBand) {
//                return null;
//            }
//
//            @Override
//            public List<HashMap<String, Object>> selectChannelByMac(String mac, Integer freqBand, List<String> updateTimeList) {
//                return null;
//            }
//
//            @Override
//            public List<Integer> selectPowerByCurrentAp(String mac, List<String> updateTimeList) {
//                return null;
//            }
//
//            @Override
//            public List<FluxRes> selectFluxByCurrentAp(String mac, List<String> updateTimeList) {
//                return null;
//            }
//
//            @Override
//            public List<ApRankVo> apIcountRank() {
//                return null;
//            }
//
//            @Override
//            public List<String> selectXListByTime(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public int selectIsNewAp(String mac, String isNewTime) {
//                return 0;
//            }
//
//            @Override
//            public List<WifiApBean> selectApList(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public String selectSecondLateUpdateTime() {
//                return null;
//            }
//
//            @Override
//            public String selectCurrentConnMac(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public String getManufacturerbyMac(String prefix) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectForPage(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> page(WifiProbeParam query) {
//                return null;
//            }
//
//            @Override
//            public Integer resCount(WifiProbeParam query) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectApMacList(HashMap<String, Object> map) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectNewAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectApMacList1(HashMap<String, Object> map) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectAllApMac() {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectNewAp1(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectAp(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public Integer insert(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer insertAllColumn(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteById(Serializable id) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteByMap(Map<String, Object> columnMap) {
//                return null;
//            }
//
//            @Override
//            public Integer delete(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteBatchIds(Collection<? extends Serializable> idList) {
//                return null;
//            }
//
//            @Override
//            public Integer updateById(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer updateAllColumnById(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer update(WifiApBean entity, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public Integer updateForSet(String setStr, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectById(Serializable id) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectBatchIds(Collection<? extends Serializable> idList) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectByMap(Map<String, Object> columnMap) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectOne(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer selectCount(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectList(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Map<String, Object>> selectMaps(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Object> selectObjs(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectPage(RowBounds rowBounds, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Map<String, Object>> selectMapsPage(RowBounds rowBounds, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//        };
//
//        WifiApClientDWDao wifiApClientDWDao = new WifiApClientDWDao() {
//            @Override
//            public void insertBatch(ArrayList<WifiApClientBean> apClientList) {
//
//            }
//
//            @Override
//            public List<WifiApClientBean> selectCorresponding1(String mac, String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApClientBean> selectCorresponding(HashMap<String, Object> paramMap) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApClientBean> currentConnectionInfo(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApClientBean> hisConnectionInfo(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public WifiApClientBean selectCurrentClient(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public List<String> getHisConnMacList(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public String selectCurrentConnMac(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApClientBean> selectSingalCorresponding(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//        };
//
//
//
//
//        //创建map对象
//        HashMap<Integer, List<WifiProbeForm>> hashMap = new HashMap<>();
//        DataStream<Tuple15<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>> dataStream = ds1.flatMap(new FlatMapFunction<String, Tuple15< String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>>() {
//            @Override
//            public void flatMap(String s, Collector<Tuple15<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>> collector) throws Exception {
//                String[] split = s.split("\\|");
//                System.out.println(split.length);//15
//                Long time = Long.valueOf(split[1]) ;
//                Integer sdeviceId = Integer.valueOf(split[2]) ;//deviceId
//                //创建wifiProbeForm对象
//                WifiProbeForm wifiProbeForm = new WifiProbeForm();
//                wifiProbeForm.setDeviceId(sdeviceId);
//                wifiProbeForm.setTime(time);
//
//                //??
//                //RedisMessageListener.wifiLink.getFirst() != null
//                if (time != null && time > 300000) {
//                    return ;
//                }
//                try{
//                    if (wifiProbeForm != null) {
//                        Integer deviceId = wifiProbeForm.getDeviceId();
//                        if (hashMap.containsKey(deviceId)) {
//                            List<WifiProbeForm> wifiProbeForms = hashMap.get(deviceId);
//                            wifiProbeForms.add(wifiProbeForm);
//                            hashMap.put(deviceId, wifiProbeForms);
//                        } else {
//                            List<WifiProbeForm> list = new ArrayList<>();
//                            list.add(wifiProbeForm);
//                            hashMap.put(deviceId, list);
//                        }
//                    }
//                }catch (Exception e){
//                    //RedisMessageListener.wifiLink.clear();
//                    //break;
//                }
//
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                calendar.add(calendar.MINUTE, -15);
//                //calendar.add(calendar.HOUR_OF_DAY, -3);//计算对应关系时  使用备份原始数据表wifiprobe1  取三个小时的数据，并在定时任务中每15分钟删除超过三个小时的数据
//                long timeInMillis = calendar.getTimeInMillis();
//                String updateTime = format.format(date);
//                //??存储时间
//                //List<WifiProbeForm> probeList = wifiProbe1Dao.selectWifiProbeHoursAgo(timeInMillis);
//
//                List<WifiProbeForm> probeList = wifiProbe1Dao(timeInMillis);
//
//
//                //遍历
//                for (Integer deviceId : hashMap.keySet()){
//                    List<WifiProbeForm> list = hashMap.get(deviceId);
//                    cleanDataForAp618(list, updateTime);
//                    cleanDataForApClient618(probeList, updateTime, deviceId);
//                }
//                //List<WifiApBean> apList = wifiapDWDao.selectLatestUpdate(updateTime);
//                List<WifiApBean> apList = wifiapDWDao(updateTime);
//
//
//                HashMap<String, Object> map = new HashMap<>();
//                HashMap<String, Object> paramMap = new HashMap<>();
//                for (int i = 0; i < apList.size(); i++) {
//
//                    String mac = apList.get(i).getMac();
//                    Integer deviceId = apList.get(i).getDeviceId();
//                    paramMap.put("mac", mac);
//                    paramMap.put("updateTime", updateTime);
//                    paramMap.put("deviceId", deviceId);
//
//                    //List<WifiApClientBean> list1 = wifiApClientDWDao.selectCorresponding(paramMap);
//                    List<WifiApClientBean> list1 = wifiApClientDWDao(paramMap);
//                    int upside = 0;
//                    int down = 0;
//                    for (int i1 = 0; i1 < list1.size(); i1++) {
//                        String upside2 = list1.get(i1).getUpside();
//                        String down2 = list1.get(i1).getDown();
//                        int upside1 = Integer.parseInt(upside2);
//                        int down1 = Integer.parseInt(down2);
//
//                        upside += upside1;
//                        down += down1;
//
//                    }
////            apList618.get(i).setUpside(String.valueOf(upside));
////            apList618.get(i).setDown(String.valueOf(down));
//                    map.put("upside", upside + "");
//                    map.put("down", down + "");
//                    map.put("mac", mac);
//                    map.put("updateTime", updateTime);
//                    map.put("deviceId", deviceId);
//                    try {
//                        wifiapDWDao.updateApCount(map);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    map.clear();
//                }
//                //        //推送 ap
////        List<WifiApBean> newApList = wifiapDWDao.selectNewAp(updateTime);//只查询status=0的，新增的
//                List<WifiApBean> newApList = wifiapDWDao.selectNewAp1(updateTime);
//
//                if (newApList.size() > 0) {
//
//                    System.out.println(updateTime + "~~共推送热点告警:" + newApList.size() + "条");
//                    System.out.println("开始推送热点告警至交互");
//                    for (int i = 0; i < newApList.size(); i++) {
//                        WifiApBean wifiApBean = newApList.get(i);
//                        //System.out.println("第"+(i+1)+"条:status为"+wifiApBean.getStatus());
//                        //String mac = wifiApBean.getMac();
//                        //与白名单进行对比，白名单中没有的推送交互
////                Integer count = whiteListDao.selectApCount(mac);
////                if (count == 0) {//白名单中没有
////                    wifiApBean.setWarning(1);
//                        try {
//                            //创建发送端Socket对象（创建连接）
//                            Socket s = new Socket(InetAddress.getByName(platformIp), platfromPort);
//                            //获取输出流对象
//                            OutputStream os = s.getOutputStream();
//                            //发送数据
//                            JSONObject o = (JSONObject) JSONObject.toJSON(wifiApBean);
//                            String s1 = o.toString();
//
//                            os.write((s1 + "\n").getBytes("UTF-8"));
//                            Thread.sleep(500);
//                            //释放资源
//                            os.close();
//                            s.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
//                //推送 client
////        List<WifiClientBean> newClientList = wifiClientDWDao.selectNewClient(updateTime);//只推送新增的
//                List<WifiClientBean> newClientList = wifiClientDWDao.selectNewClient1(updateTime);
//                if (newClientList.size() > 0) {
//                    System.out.println(updateTime + "~~共推送终端告警:" + newClientList.size() + "条");
//                    System.out.println("开始推送终端告警至交互");
//                    for (int i = 0; i < newClientList.size(); i++) {
//                        WifiClientBean wifiClientBean = newClientList.get(i);
//                        //System.out.println("第"+(i+1)+"条:status为"+wifiApBean.getStatus());
//                        //String terminalMac = wifiClientBean.getTerminalMac();
//                        //与白名单进行对比，白名单中没有的推送交互
////                Integer count = whiteListDao.selectApCount(mac);
////                if (count == 0) {//白名单中没有
////                    wifiApBean.setWarning(1);
//                        try {
//                            //创建发送端Socket对象（创建连接）
//                            Socket s = new Socket(InetAddress.getByName(platformIp), platfromPort);
//                            //获取输出流对象
//                            OutputStream os = s.getOutputStream();
//                            //发送数据
//                            JSONObject o = (JSONObject) JSONObject.toJSON(wifiClientBean);
//                            String s1 = o.toString();
//
//                            //System.out.println(o.get("ssid"));
//                            os.write((s1 + "\n").getBytes("UTF-8"));
//                            Thread.sleep(500);
//                            //释放资源
//                            os.close();
//                            s.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
//                count++;
//
//            }
//        });
//
//        //TODO 3.sink
//        //dataStream.addSink(new ckSink());
//        //dataStream.print();
//
//        //TODO 4.execute
//        env.execute();
//    }
//
//    private static List<WifiApClientBean> wifiApClientDWDao(HashMap<String, Object> paramMap) {
//    }
//
//    private static List<WifiProbeForm> wifiProbe1Dao(Long time) throws SQLException {
//        //1. 注册驱动
//        //2. 根据驱动管理类, 获取连接
//        Connection connection = com.flink.dcyw.JDBCUtils.getConnection();
//        //3. 根据连接创建sql语句执行平台
//        Statement statement = connection.createStatement();
//        //4. 执行SQL, 获取结果
//        String sql = "select id, event_type eventType,device_id " +
//                "deviceId,ssid,mac,encryption,terminal_ssid terminalSsid," +
//                "terminal_mac terminalMac,channel,power,distance,monitoring_site monitoringSite," +
//                "index_name indexName,time,primary_classification primaryClassification," +
//                "secondary_classification secondaryClassification,upside,down," +
//                "duration from wifiprobe1 where time > '"+ time +"';";
//        ResultSet resultSet = statement.executeQuery(sql);
//        List<WifiProbeForm> probeList = new ArrayList<>();
//
//        //5. 处理结果集:
//        while(resultSet.next()){
//            //WifiProbeForm wifiProbeForm = new WifiProbeForm();
//            long id = resultSet.getLong("id");
//            //wifiProbeForm.setId(id);
//            String event_type = resultSet.getString("event_type");
//            long timea = resultSet.getLong("time");
//            long device_id = resultSet.getLong("device_id");
//            String ssid = resultSet.getString("ssid");
//            String mac = resultSet.getString("mac");
//            String encryption = resultSet.getString("encryption");
//            String terminal_ssid = resultSet.getString("terminal_ssid");
//            String terminal_mac = resultSet.getString("terminal_mac");
//            Long channel = resultSet.getLong("channel");
//            String primary_classification = resultSet.getString("primary_classification");
//            String secondary_classification = resultSet.getString("secondary_classification");
//            String power = resultSet.getString("power");
//            String distance = resultSet.getString("distance");
//            String monitoring_site = resultSet.getString("monitoring_site");
//            String index_name = resultSet.getString("index_name");
//            String upside = resultSet.getString("upside");
//            String down = resultSet.getString("down");
//            String duration = resultSet.getString("duration");
//            WifiProbeForm wifiProbeForm = new WifiProbeForm(id,event_type,timea,device_id,ssid,mac,
//                    encryption,terminal_ssid,terminal_mac,channel,primary_classification,secondary_classification,
//                    power,distance,monitoring_site,index_name,upside,down,duration);
//
//            probeList.add(wifiProbeForm);
//            System.out.println(wifiProbeForm);
//        }
//        return probeList;
//    }
//    private static List<WifiApBean> wifiapDWDao(String updateTime) throws SQLException {
//        //1. 注册驱动
//        //2. 根据驱动管理类, 获取连接
//        Connection connection = com.flink.dcyw.JDBCUtils.getConnection();
//        //3. 根据连接创建sql语句执行平台
//        Statement statement = connection.createStatement();
//        //4. 执行SQL, 获取结果
//
//        String sql = "select id,event_type eventType,device_id deviceId,ssid,mac,encryption,channel,power,distance," +
//                "monitoring_site monitoringSite\n" +
//                "        ,index_name indexName,update_time updateTime,unix_time unixTime,status,risk,ap_type apType,freq_band freqBand\n" +
//                "        from wifiap where update_time='"+updateTime+"'}";
//        ResultSet resultSet = statement.executeQuery(sql);
//        List<WifiApBean> probeList = new ArrayList<>();
//
//        //5. 处理结果集:
//        while(resultSet.next()){
//            //WifiProbeForm wifiProbeForm = new WifiProbeForm();
//            long id = resultSet.getLong("id");
//            String event_type = resultSet.getString("event_type");
//            long device_id = resultSet.getLong("device_id");
//            String ssid = resultSet.getString("ssid");
//            String mac = resultSet.getString("mac");
//            String encryption = resultSet.getString("encryption");
//            //String terminal_ssid = resultSet.getString("terminal_ssid");
//            //String terminal_mac = resultSet.getString("terminal_mac");
//            Long channel = resultSet.getLong("channel");
//            //String primary_classification = resultSet.getString("primary_classification");
//            //String secondary_classification = resultSet.getString("secondary_classification");
//            String power = resultSet.getString("power");
//            String distance = resultSet.getString("distance");
//            String monitoring_site = resultSet.getString("monitoring_site");
//            String index_name = resultSet.getString("index_name");
//            String updateTime2 = resultSet.getString("updatetime");
//            String unix_time = resultSet.getString("unix_time");
//            long risk = resultSet.getLong("risk");
//            long status = resultSet.getLong("status");
//            String ap_type = resultSet.getString("ap_type");
//            long icount = resultSet.getLong("icount");
//            long freq_band = resultSet.getLong("freq_band");
//            String upside = resultSet.getString("upside");
//            String down = resultSet.getString("down");
//            String duration = resultSet.getString("duration");
//            WifiApBean wifiApBean = new WifiApBean(id,event_type,device_id,ssid,mac,
//                    encryption,channel,power,distance,monitoring_site,index_name,updateTime2,unix_time,
//                    risk,status,ap_type,icount,freq_band,upside,down,duration);
//
//
//            probeList.add(wifiApBean);
//            System.out.println(wifiApBean);
//        }
//        return probeList;
//    }
//
//
//    private static class ckSink extends RichSinkFunction<Tuple4<String, String, String, String>> {
//        private Connection conn;
//        private PreparedStatement preparedStatement;
//
//        @Override
//        public void open(Configuration parameters) throws Exception {
//            super.open(parameters);
//            //加载JDBC驱动
//            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
//            //获取数据库连接
//            conn = DriverManager.getConnection("jdbc:clickhouse://192.168.88.162:8123/default");
//            //String sql = "INSERT INTO `user_order_all` (`oder_id`, `user_id`, `price`, `creat_time`) VALUES (?,?,?,?);";
//            //conn.prepareStatement(sql);
//            preparedStatement = conn.prepareStatement("INSERT INTO traceone(keywords,eventType,traceInTime,deviceId) VALUES (?,?,?,?)");
//
//        }
//
//        @Override
//        public void close() throws Exception {
//            super.close();
//            if (preparedStatement != null) {
//
//                preparedStatement.close();
//            }
//        }
//
//        @Override
//        public void invoke(Tuple4<String, String, String, String> value, Context context) throws Exception {
//            try {
//                String keywords = value.f0;
//                String eventType = value.f1;
//                String traceInTime = value.f2;
//                String deviceId = value.f3;
//                preparedStatement.setString(1, keywords);
//                preparedStatement.setString(2, eventType);
//                preparedStatement.setString(3, traceInTime);
//                preparedStatement.setString(4, deviceId);
//                preparedStatement.executeUpdate();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//
//    }
//
//    private static void cleanDataForAp618(List<WifiProbeForm> list, String updateTime) {
//
//        WhiteListDao whiteListDao = new WhiteListDao() {
//            @Override
//            public Integer selectApCount(String mac) {
//                return null;
//            }
//
//            @Override
//            public Integer selectClientCount(String terminalMac) {
//                return null;
//            }
//
//            @Override
//            public Integer insert(WhiteListBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer insertAllColumn(WhiteListBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteById(Serializable id) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteByMap(Map<String, Object> columnMap) {
//                return null;
//            }
//
//            @Override
//            public Integer delete(Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteBatchIds(Collection<? extends Serializable> idList) {
//                return null;
//            }
//
//            @Override
//            public Integer updateById(WhiteListBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer updateAllColumnById(WhiteListBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer update(WhiteListBean entity, Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public Integer updateForSet(String setStr, Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public WhiteListBean selectById(Serializable id) {
//                return null;
//            }
//
//            @Override
//            public List<WhiteListBean> selectBatchIds(Collection<? extends Serializable> idList) {
//                return null;
//            }
//
//            @Override
//            public List<WhiteListBean> selectByMap(Map<String, Object> columnMap) {
//                return null;
//            }
//
//            @Override
//            public WhiteListBean selectOne(WhiteListBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer selectCount(Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<WhiteListBean> selectList(Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Map<String, Object>> selectMaps(Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Object> selectObjs(Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<WhiteListBean> selectPage(RowBounds rowBounds, Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Map<String, Object>> selectMapsPage(RowBounds rowBounds, Wrapper<WhiteListBean> wrapper) {
//                return null;
//            }
//        };//whiteListDao
//        WifiapDWDao wifiapDWDao = new WifiapDWDao() {
//            @Override
//            public ArrayList<WifiApBean> selectWifiApEveryDay(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public String selectLatestUpdateTime() {
//                return null;
//            }
//
//            @Override
//            public void insertBatch(ArrayList<WifiApBean> apList) {
//
//            }
//
//            @Override
//            public List<String> selectMacAndDeviceId(String beginDate, String endDate) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectEncryption() {
//                return null;
//            }
//
//            @Override
//            public List<String> selectApType() {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectLatestUpdate(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public void updateApCount(HashMap<String, Object> map) {
//
//            }
//
//            @Override
//            public Integer countNewAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer countNormalAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer countCheckedAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer selectDistribution(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer selectDistribution1(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public Integer countEncryption(String encryption, String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectEncryptionWithTime(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<ApTypeCountVo> selectApTypeCount(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectWifiApById(Integer id) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectXTime(String apUpdateTime, String mac) {
//                return null;
//            }
//
//            @Override
//            public List<Integer> selectChannelList(Integer freqBand) {
//                return null;
//            }
//
//            @Override
//            public List<HashMap<String, Object>> selectChannelByMac(String mac, Integer freqBand, List<String> updateTimeList) {
//                return null;
//            }
//
//            @Override
//            public List<Integer> selectPowerByCurrentAp(String mac, List<String> updateTimeList) {
//                return null;
//            }
//
//            @Override
//            public List<FluxRes> selectFluxByCurrentAp(String mac, List<String> updateTimeList) {
//                return null;
//            }
//
//            @Override
//            public List<ApRankVo> apIcountRank() {
//                return null;
//            }
//
//            @Override
//            public List<String> selectXListByTime(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public int selectIsNewAp(String mac, String isNewTime) {
//                return 0;
//            }
//
//            @Override
//            public List<WifiApBean> selectApList(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public String selectSecondLateUpdateTime() {
//                return null;
//            }
//
//            @Override
//            public String selectCurrentConnMac(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public String getManufacturerbyMac(String prefix) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectForPage(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> page(WifiProbeParam query) {
//                return null;
//            }
//
//            @Override
//            public Integer resCount(WifiProbeParam query) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectApMacList(HashMap<String, Object> map) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectNewAp(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectApMacList1(HashMap<String, Object> map) {
//                return null;
//            }
//
//            @Override
//            public List<String> selectAllApMac() {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectNewAp1(String updateTime) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectAp(WifiProbeParam wifiProbeParam) {
//                return null;
//            }
//
//            @Override
//            public Integer insert(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer insertAllColumn(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteById(Serializable id) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteByMap(Map<String, Object> columnMap) {
//                return null;
//            }
//
//            @Override
//            public Integer delete(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public Integer deleteBatchIds(Collection<? extends Serializable> idList) {
//                return null;
//            }
//
//            @Override
//            public Integer updateById(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer updateAllColumnById(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer update(WifiApBean entity, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public Integer updateForSet(String setStr, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectById(Serializable id) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectBatchIds(Collection<? extends Serializable> idList) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectByMap(Map<String, Object> columnMap) {
//                return null;
//            }
//
//            @Override
//            public WifiApBean selectOne(WifiApBean entity) {
//                return null;
//            }
//
//            @Override
//            public Integer selectCount(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectList(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Map<String, Object>> selectMaps(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Object> selectObjs(Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<WifiApBean> selectPage(RowBounds rowBounds, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//
//            @Override
//            public List<Map<String, Object>> selectMapsPage(RowBounds rowBounds, Wrapper<WifiApBean> wrapper) {
//                return null;
//            }
//        };
//        ArrayList<WifiApBean> apList618 = new ArrayList<>();
//
//        long start = System.currentTimeMillis();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        ArrayList<WifiApBean> apList = new ArrayList<>();
//
//        Integer risk;
//        Integer status;
//
//        int apperCount = 0;
//        long sec = System.currentTimeMillis();
//        String isNewTime = "";//三个月以前的时间
//
//        Date parse = null;
//        try {
//            parse = format.parse(updateTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long updateLong = parse.getTime();
//
//        //1. 删除mac地址为空字符串，以及ssid为"LS0="的无效数据，有效数据按照mac地址存入map；
//        HashMap<String, List<WifiProbeForm>> macWifiListMap = new HashMap<>();
//
//        for (WifiProbeForm wifiProbeForm : list) {
//            if ((!wifiProbeForm.getMac().equals("")) && (!wifiProbeForm.getSsid().equals("LS0="))) {
//
//                String mac = wifiProbeForm.getMac();
//                if (macWifiListMap.get(mac) != null) {
//
//                    List<WifiProbeForm> macListTemp = macWifiListMap.get(wifiProbeForm.getMac());
//                    macListTemp.add(wifiProbeForm);
//                    macWifiListMap.put(mac, macListTemp);
//                } else {
//
//                    List<WifiProbeForm> macListTemp = new ArrayList<>();
//                    macListTemp.add(wifiProbeForm);
//                    macWifiListMap.put(mac, macListTemp);
//                }
//            }
//        }
//
//        for (String mac : macWifiListMap.keySet()) {
//
//            List<WifiProbeForm> wifiProbeFormList = macWifiListMap.get(mac);
//            if (wifiProbeFormList.size() < 3) {  //2.出现次数小于3的数据集不处理
//
//            } else {
//
//                WifiApBean wifiApBean = new WifiApBean();
//
//                String encryptionRes = "";
//
//                //3.加密字段筛选，集合中encryption出现次数最多者即为选取的encryption
//                Map<String, Integer> encryptionMap = new HashMap<>();
//                WifiProbeForm wifi = null;
//                float disTem = 100f;
//                for (WifiProbeForm wifiProbeForm : wifiProbeFormList) {
//
//                    String encryption = wifiProbeForm.getEncryption();
//                    if (encryptionMap.get(encryption) != null) {
//                        encryptionMap.put(encryption, encryptionMap.get(encryption) + 1);
//                    } else {
//                        encryptionMap.put(encryption, 1);
//                    }
//
//                    String power = wifiProbeForm.getPower();
//                    Float distance = Float.parseFloat(wifiProbeForm.getDistance());
//                    if (!"--".equals(power)) {
//
//                        if (distance < disTem) {
//                            disTem = distance;
//                            wifi = wifiProbeForm;
//                        }
//                    }
//
//                }
//                int countMax = 0;
//                //选择出现次数最多的encryption
//                for (String key : encryptionMap.keySet()) {
//
//                    if (encryptionMap.get(key) > countMax) {
//                        encryptionRes = key;
//                        countMax = encryptionMap.get(key);
//                    }
//                }
//
//                wifiApBean.setEventType(wifi.getEventType());
//                wifiApBean.setDeviceId(wifi.getDeviceId());
//                String rawSsid = wifi.getSsid();
//                wifiApBean.setSsid(decode(rawSsid));
//                wifiApBean.setMac(wifi.getMac());
//                wifiApBean.setEncryption(encryptionRes);
//                wifiApBean.setChannel(wifi.getChannel());
//                wifiApBean.setPower(Integer.parseInt(wifi.getPower()));
//                wifiApBean.setDistance(wifi.getDistance());
//                wifiApBean.setMonitoringSite(wifi.getMonitoringSite());
//                wifiApBean.setIndexName(wifi.getIndexName());
//                wifiApBean.setUpdateTime(updateTime);
//
//                wifiApBean.setDuration(wifi.getDuration());
//
//                Long time = wifi.getTime();
//                Date date1 = new Date();
//                date1.setTime(time);
//                String unixTime = format.format(date1);
//                wifiApBean.setUnixTime(unixTime);
//
//                if (wifi.getChannel() < 15) {
//                    wifiApBean.setFreqBand(0);
//                } else if (wifi.getChannel() > 15) {
//                    wifiApBean.setFreqBand(1);
//                }
//
//                //需增加与白名单匹配代码，查看是否存在于白名单中，存在则为已核查
//                String mac1 = wifi.getMac();
//
//                Integer apWhiteCount = whiteListDao.selectApCount(mac1);
//                if (apWhiteCount >= 1) {
//                    status = 2;
//                } else {
//                    //sec = sec - 7776000000l;//90天之前
////                    sec = sec - 300000;//五分钟之前
//                    sec = updateLong - 3600000;//五分钟之前
//                    isNewTime = format.format(new Date(sec));
//                    apperCount = wifiapDWDao.selectIsNewAp(wifi.getMac(), isNewTime);
//                    if (apperCount >= 1) {//查看在三个月内，此热点是否出现过，如为第一次出现，则为新增
//                        status = 1;
//                    } else {
//                        status = 0;
//                    }
//                }
//                wifiApBean.setStatus(status);
//                if (status == 2) {//在白名单内为已核查，已核查即为无风险
//                    risk = 0;
//                } else {
//                    risk = 1;
//                }
//                wifiApBean.setRisk(risk);
//
//                int n = (int) (Math.random() * 3) + 1;
//                if (n == 1) {
//                    wifiApBean.setApType("热点类型一");
//                } else if (n == 2) {
//                    wifiApBean.setApType("热点类型二");
//                } else {
//                    wifiApBean.setApType("热点类型三");
//                }
//                apList.add(wifiApBean);
//                apList618.add(wifiApBean);
//
//            }
//        }
//
//        //4.批量存储
//        if (apList.size() > 0) {
////            wifiapDWDao.insertBatch(apList);
//            apList.clear();
//        }
//
//        long end = System.currentTimeMillis();
////        System.out.println("此次刷新热点共花费:" + (end - start));
//
//    }
//
//    public static String decode(String encodedText) {
//        String text = null;
//        try {
//            text = new String(decoder.decode(encodedText), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return text;
//    }
//    private static void cleanDataForApClient618(List<WifiProbeForm> list, String updateTime, Integer deviceId) {
//        ArrayList<WifiApClientBean> apClientList = new ArrayList<>();
//
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Integer risk;
//        Integer status;
//        int apperCount = 0;
//        long sec = 0l;
//        String isNewTime = "";
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("updateTime", updateTime);
//        map.put("deviceId", deviceId);
////        List<String> apMacList = wifiapDWDao.selectApMacList(updateTime, deviceId);
//
//        List<String> apMacList1 = new ArrayList<>();
//        for (WifiApBean wifiApBean : apList618) {
//            apMacList1.add(wifiApBean.getMac());
//        }
//
////        List<String> apMacList1 = wifiapDWDao.selectApMacList(map);
//
//        List<String> apMacList = new ArrayList<>();
//        String strLi = "";
//        for (int i = 0; i < apMacList1.size(); i++) {
//            String s = apMacList1.get(i);
//            if (strLi.indexOf(s) > -1) {
//                continue;
//            }
//            if (s.length() < 17 || s.indexOf("(") > -1) {
//                continue;
//            } else {
//                if (strLi == "") {
//                    strLi = s;
//                } else {
//                    strLi = strLi + "|" + s;
//                }
//            }
//        }
//        String[] split = strLi.split("\\|");
//        apMacList = Arrays.asList(split);
//
//        for (String mac : apMacList) {
//
//
////            if(!"36:80:9B:CB:AB:F7".equals(mac)){
////                continue;
////            }
////            List<String> terminalMacList = new ArrayList<>();
//            HashMap<String, List<WifiProbeForm>> terminalMap = new HashMap<>();
//            //查询出原始数据中，mac为ap表中mac值，且符合一系列条件的终端mac集合
//            for (int i = 0; i < list.size(); i++) {
//                WifiProbeForm wifiProbeForm = list.get(i);
//                String mac1 = wifiProbeForm.getMac();
//                String terminalMac = wifiProbeForm.getTerminalMac();
//                String power = wifiProbeForm.getPower();
//                String encryption = wifiProbeForm.getEncryption();
//                String primary = wifiProbeForm.getPrimaryClassification();
//                String secondary = wifiProbeForm.getSecondaryClassification();
//
//                if (mac1.equals(mac)) {
////                    if (!terminalMacList.contains(terminalMac)) {
//                    if (
//                            (!("--".equals(power)))
//                                    && (!("--".equals(encryption)))
//                                    && (!("FF:FF:FF:FF:FF:FF".equals(terminalMac)))
//                                    && (("02".equals(primary)) || ("01".equals(primary) && "1B".equals(secondary)) || ("01".equals(primary) && "05".equals(secondary)))
//                    ) {
////                            terminalMacList.add(terminalMac);
//                        if (terminalMap.containsKey(terminalMac)) {
//
//                            List<WifiProbeForm> wifiProbeForms = terminalMap.get(terminalMac);
//                            wifiProbeForms.add(wifiProbeForm);
//                            terminalMap.put(terminalMac, wifiProbeForms);
//                        } else {
//
//                            List<WifiProbeForm> wifiProbeList = new ArrayList<>();
//                            wifiProbeList.add(wifiProbeForm);
//                            terminalMap.put(terminalMac, wifiProbeList);
//                        }
//                    }
////                    }
//                }
//
//            }
//
//            Set<String> terminalMacList = terminalMap.keySet();
//            if (terminalMacList.isEmpty()) {
//                continue;
//            }
//            for (String terminalMac : terminalMacList) {
//
//                ArrayList<String> concatList = new ArrayList<>();
//                WifiProbeForm wifi = new WifiProbeForm();
//                int upside = 0;
//                int down = 0;
//
////                int count = wifiProbeDao.selectConcatCount(terminalMac);
//
//                int count = 0;
//                Float disTem = 100f;
////                List<WifiProbeForm> list1 = terminalMap.get(terminalMac);
//                for (int i = 0; i < list.size(); i++) {
//
//                    WifiProbeForm wifiProbeForm = list.get(i);
//
//                    String terminalMac1 = wifiProbeForm.getTerminalMac();
//                    String mac2 = wifiProbeForm.getMac();
//
//                    if (!terminalMac.equals(terminalMac1)) {
//                        continue;
//                    }
//                    String primaryClassification = wifiProbeForm.getPrimaryClassification();
//                    String secondaryClassification = wifiProbeForm.getSecondaryClassification();
//                    String power = wifiProbeForm.getPower();
//                    String encryption = wifiProbeForm.getEncryption();
//                    String concat = primaryClassification + secondaryClassification;
//                    Float distance = Float.parseFloat(wifiProbeForm.getDistance());
//                    String mac1 = wifiProbeForm.getMac();
//
//
////                    if(mac2.equals(mac) && terminalMac.equals(terminalMac1)){
//
//                    if ((!("02".equals(primaryClassification)) && (!("--".equals(power))) && (!("--".equals(encryption))))) {
//                        boolean contains = concatList.contains(concat);
//                        if (!contains) {
//                            concatList.add(concat);
//                        }
//                    }
//
//
////                    if (terminalMac.equals(terminalMac1) && mac.equals(mac1)) {
//
//                    if (!mac2.equals(mac)) {
//                        continue;
//                    }
//                    //查询出最短距离，power不等于--,即功率最强的一条
//                    if (!("--".equals(power)) && !("--".equals(encryption))) {
//
//                        if (distance < disTem) {
//                            wifi = wifiProbeForm;
//                            disTem = distance;
//                        }
//                    }
//
//                    //计算原始数据中终端mac一致的上行及下行的和
//                    int upside1 = Integer.parseInt(list.get(i).getUpside());
//                    int down1 = Integer.parseInt(list.get(i).getDown());
//
//                    upside += upside1;
//                    down += down1;
////                    }
//                }
//                count = concatList.size();
//                if (count < 1) {
//                    continue;
//                }
//
//                WifiApClientBean wifiApClientBean = new WifiApClientBean();
//                wifiApClientBean.setEventType(wifi.getEventType());
//                wifiApClientBean.setDeviceId(wifi.getDeviceId());
//                String rawSsid = wifi.getSsid();
//                wifiApClientBean.setSsid(FrontCommonUtils.decode(rawSsid));
//                wifiApClientBean.setMac(wifi.getMac());
//                wifiApClientBean.setEncryption(wifi.getEncryption());
//
//                wifiApClientBean.setUpside(upside + "");
//                wifiApClientBean.setDown(down + "");
//                wifiApClientBean.setDuration(wifi.getDuration());
//
//                wifiApClientBean.setTerminalMac(wifi.getTerminalMac());
//                wifiApClientBean.setTerminalSsid(wifi.getTerminalSsid());
//                wifiApClientBean.setChannel(wifi.getChannel());
//                wifiApClientBean.setPower(Integer.parseInt(wifi.getPower()));
//                wifiApClientBean.setDistance(wifi.getDistance());
//                wifiApClientBean.setMonitoringSite(wifi.getMonitoringSite());
//                wifiApClientBean.setIndexName(wifi.getIndexName());
//                wifiApClientBean.setUpdateTime(updateTime);
//
//                Long time = wifi.getTime();
//                Date date1 = new Date();
//                date1.setTime(time);
//                String unixTime = format1.format(date1);
//                wifiApClientBean.setUnixTime(unixTime);
//                wifiApClientBean.setPrimaryClassification(wifi.getPrimaryClassification());
//                wifiApClientBean.setSecondaryClassification(wifi.getSecondaryClassification());
//
//                //需增加与白名单匹配代码，查看是否存在于白名单中，存在则为已核查
//                if ((wifi.getChannel() % 2) == 0) {//此处判断条件为模拟条件//todo
//                    status = 2;
//                } else {
//                    //sec = sec - 7776000000l;
//                    sec = sec - 300000;
//                    isNewTime = format1.format(new Date(sec));
//                    apperCount = wifiClientDWDao.selectIsNewClient(wifi.getTerminalMac(), isNewTime);
//                    if (apperCount >= 1) {//查看在三个月内，此热点是否出现过，如为第一次出现，则为新增
//                        status = 1;
//                    } else {
//                        status = 0;
//                    }
//                }
//                wifiApClientBean.setStatus(status);
//                if (status == 2) {//在白名单内为已核查，已核查即为无风险
//                    risk = 0;
//                } else {
//                    risk = 1;
//                }
//                wifiApClientBean.setRisk(risk);
//
//                //此处为模拟的类型判断条件
//                int n = (int) (Math.random() * 3) + 1;
//                if (n == 1) {
//                    wifiApClientBean.setClientType("终端类型一");
//                } else if (n == 2) {
//                    wifiApClientBean.setClientType("终端类型二");
//                } else {
//                    wifiApClientBean.setClientType("终端类型三");
//                }
//                apClientList.add(wifiApClientBean);
//
//
//            }
//        }
//
//        if (apClientList.size() > 0) {
//            wifiApClientDWDao.insertBatch(apClientList);
//
//        }
//        HashSet<String> apMac = new HashSet<>();
//        HashSet<String> clientMac = new HashSet<>();
//        for (WifiApClientBean wifiApClientBean : apClientList) {
//            apMac.add(wifiApClientBean.getMac());
////                clientMac.add(wifiApClientBean.getTerminalMac());
//
//            WifiClientBean wifiClientBean = new WifiClientBean();
//            wifiClientBean.setEventType(wifiApClientBean.getEventType());
//            wifiClientBean.setDeviceId(wifiApClientBean.getDeviceId());
//            wifiClientBean.setTerminalSsid(wifiApClientBean.getTerminalSsid());
//            wifiClientBean.setTerminalMac(wifiApClientBean.getTerminalMac());
//            wifiClientBean.setChannel(wifiApClientBean.getChannel());
//            wifiClientBean.setPower(wifiApClientBean.getPower());
//            wifiClientBean.setDistance(wifiApClientBean.getDistance());
//            wifiClientBean.setMonitoringSite(wifiApClientBean.getMonitoringSite());
//            wifiClientBean.setIndexName(wifiApClientBean.getIndexName());
//            wifiClientBean.setUpdateTime(wifiApClientBean.getUpdateTime());
//            wifiClientBean.setUnixTime(wifiApClientBean.getUnixTime());
//            wifiClientBean.setRisk(wifiApClientBean.getRisk());
//            wifiClientBean.setStatus(wifiApClientBean.getStatus());
//            wifiClientBean.setClientType(wifiApClientBean.getClientType());
//            wifiClientBean.setPrimaryClassification(wifiApClientBean.getPrimaryClassification());
//            wifiClientBean.setSecondaryClassification(wifiApClientBean.getSecondaryClassification());
//            if (wifiApClientBean.getChannel() < 15) {
//                wifiClientBean.setFreqBand(0);
//            } else if (wifiApClientBean.getChannel() > 15) {
//                wifiClientBean.setFreqBand(1);
//            }
//            wifiClientBean.setUpside(wifiApClientBean.getUpside());
//            wifiClientBean.setDown(wifiApClientBean.getDown());
//            wifiClientBean.setDuration(wifiApClientBean.getDuration());
//            wifiClientBean.setSsid(wifiApClientBean.getSsid());
//            wifiClientBean.setApMac(wifiApClientBean.getMac());
//
//            clientList618.add(wifiClientBean);
//        }
//
//        //热点距离≥20且未连接终端的直接忽略，保留热点距离在20米内的和20米外连接了终端的热点
//        ArrayList<WifiApBean> apListTemp = new ArrayList<>();
//
//        ArrayList<String> ssidList = new ArrayList<>();
//        for (WifiApBean wifiApBean : apList618) {
//            if (apMac.contains(wifiApBean.getMac())) {
//                apListTemp.add(wifiApBean);
//            } else if (Float.parseFloat(wifiApBean.getDistance()) < 20f) {
//                apListTemp.add(wifiApBean);
//            }
//            ssidList.add(wifiApBean.getSsid());
//        }
////            1、根据是否连接热点进行筛选，未连接热点的终端直接忽略
////            2、满足1的情况下，终端距离≥20的直接忽略
//        ArrayList<WifiClientBean> clientListTemp = new ArrayList<>();
//        ArrayList<WifiClientBean> clientList = new ArrayList<>();
//        for (WifiClientBean wifiClientBean : clientList618) {
////                if (clientMac.contains(wifiClientBean.getTerminalMac())){
//            if (Float.parseFloat(wifiClientBean.getDistance()) < 20f) {
//                String ssid = wifiClientBean.getSsid();
//                if (ssidList.contains(ssid)) {
//                    clientList.add(wifiClientBean);
//                }
//            }
////                }
//        }
//
//        if (apListTemp.size() > 0) {
//            wifiapDWDao.insertBatch(apListTemp);
//        }
//
//        if (clientList.size() > 0) {
//            wifiClientDWDao.insertBatch(clientList);
//        }
//
//        apList618.clear();
//        clientList618.clear();
//        apClientList.clear();
//
//
//        long end = System.currentTimeMillis();
////        System.out.println("此次刷新对应关系共花费:" + (end - start));
//    }
//
//
//
//
//}
////准备主题 /export/server/kafka/bin/kafka-topics.sh --create --zookeeper node1:2181 --replication-factor 2 --partitions 3 --topic flink_kafka
////启动控制台生产者发送数据 /export/server/kafka/bin/kafka-console-producer.sh --broker-list node1:9092 --topic flink_kafka
////启动程序FlinkKafkaConsumer
////观察控制台输出结果