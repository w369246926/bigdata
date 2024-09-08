package com.flink.sockettest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class socket {

        public static void main(String args[]) throws Exception {
            //为了简单起见，所有的异常都直接往外抛
            //String host = "10.10.42.241";  //要连接的服务端IP地址192.168.110.245
            String host = "172.16.10.161";
            int port = 20181;   //要连接的服务端对应的监听端口
            //与服务端建立连接
            //建立连接后就可以往服务端写数据了
            while (true) {
                Socket client = new Socket(host, port);
                Writer writer = new OutputStreamWriter(client.getOutputStream(), "GBK");
                //writer.write("你好，服务端。");
                //writer.write("你好 服务端。\n");

                //writer.write("eof\n");
                //writer.write("eof\n");
                //ahmetadata测试:
                writer.write("{\"sendType\":\"syslog\",\"productVendorName\":\"安恒\",\"customerId\":\"-1\"," +
                        "\"deviceId\":\"-2\",\"eventCount\":\"1\",\"securityEyeLogType\":\"1\"," +
                        "\"deviceName\":\"devicename\",\"sendHostAddress\":\"10.10.40.114\"," +
                        "\"deviceAddress\":\"10.10.40.114\",\"deviceMac\":\"08:3a:88:c6:ae:44\"," +
                        "\"machineCode\":\"083a88c6ae44\",\"device_id\":\"0\",\"device_port_id\":-1," +
                        "\"device_ip\":\"10.10.40.114\",\"interface_icon\":\"核心交换\",\"data_type\":1," +
                        "\"time\":1662087975313,\"sip\":\"185.212.130.14\",\"sipv6\":\"\"," +
                        "\"dip\":\"192.168.33.211\",\"dipv6\":\"\",\"smac\":\"00-E0-4C-2F-83-5B\"," +
                        "\"sport\":38521,\"dmac\":\"00-23-89-8D-29-F1\",\"dport\":80,\"network_protocol\":0," +
                        "\"transport_protocol\":6,\"session_protocol\":0,\"app_protocol\":1," +
                        "\"sess_id\":\"2209021106150533390\",\"log_type\":3," +
                        "\"deviceSendProductName\":\"安恒全流量深度威胁检测系统\",\"deviceProductType\":\"流量控制\"," +
                        "\"deviceAssetSubType\":\"流量监控设备\",\"severity\":\"1\",\"name\":\"HTTP请求访问\"," +
                        "\"catBehavior\":\"/Access\",\"deviceCat\":\"/Audit\"," +
                        "\"catObject\":\"/Host/Application/Service\",\"catOutcome\":\"Attempt\"," +
                        "\"catSignificance\":\"/Informational\",\"catTechnique\":\"/UNKNOW\"," +
                        "\"eventId\":\"220902110615000040101\",\"startTime\":\"2022-09-02 11:06:15\"," +
                        "\"endTime\":\"2022-09-02 11:06:15\",\"collectorReceiptTime\":\"2022-09-02 11:06:15\"," +
                        "\"method\":\"GET\",\"origin\":\"/setup.asp?id=1 out_file(a)\",\"uri\":\"/setup.asp\"," +
                        "\"dirName\":\"/\",\"suffix\":\"asp\",\"fileName\":\"setup.asp\",\"httpVersion\":\"1.0\"," +
                        "\"host\":\"192.168.33.211\",\"agent\":\"Wget/1.12 (linux-gnu)\"," +
                        "\"http_req_header\":\"GET /setup.asp?id=1%20out_file(a) HTTP/1.0\\\\x0D\\\\x0AUser-Agent: Wget/1.12 (linux-gnu)\\\\x0D\\\\x0AAccept: */*\\\\x0D\\\\x0AHost: 192.168.33.211\\\\x0D\\\\x0AConnection: Keep-Alive\\\\x0D\\\\x0A\\\\x0D\\\\x0A\"," +
                        "\"http_res_code\":404,\"requestTime\":\"0\",\"content_type\":\"\",\"content_length\":\"0\",\"file_list\":[]}\n");
                //writer.write("{\"sendType\":\"syslog\", \"productVendorName\":\"安恒\", \"customerId\":\"-1\", \"deviceId\":\"-2\", \"eventCount\":\"1\", \"securityEyeLogType\":\"1\", \"deviceName\":\"devicename\", \"sendHostAddress\":\"10.10.40.114\", \"deviceAddress\":\"10.10.40.114\", \"deviceMac\":\"08:3a:88:c6:ae:44\", \"machineCode\":\"083a88c6ae44\", \"device_id\":\"0\", \"device_port_id\":-1, \"device_ip\":\"10.10.40.114\", \"interface_icon\":\"核心交换\", \"data_type\":1, \"time\":1662087975313, \"sip\":\"185.212.130.14\", \"sipv6\":\"\", \"dip\":\"192.168.33.211\", \"dipv6\":\"\", \"smac\":\"00-E0-4C-2F-83-5B\", \"sport\":38521, \"dmac\":\"00-23-89-8D-29-F1\", \"dport\":80, \"network_protocol\":0, \"transport_protocol\":6, \"session_protocol\":0, \"app_protocol\":1, \"sess_id\":\"2209021106150533390\", \"log_type\":3, \"deviceSendProductName\":\"安恒全流量深度威胁检测系统\", \"deviceProductType\":\"流量控制\", \"deviceAssetSubType\":\"流量监控设备\", \"severity\":\"1\", \"name\":\"HTTP请求访问\", \"catBehavior\":\"/Access\", \"deviceCat\":\"/Audit\", \"catObject\":\"/Host/Application/Service\", \"catOutcome\":\"Attempt\", \"catSignificance\":\"/Informational\", \"catTechnique\":\"/UNKNOW\", \"eventId\":\"220902110615000040101\", \"startTime\":\"2022-09-02 11:06:15\", \"endTime\":\"2022-09-02 11:06:15\", \"collectorReceiptTime\":\"2022-09-02 11:06:15\", \"method\":\"GET\", \"origin\":\"/setup.asp?id=1 out_file(a)\", \"uri\":\"/setup.asp\", \"dirName\":\"/\", \"suffix\":\"asp\", \"fileName\":\"setup.asp\", \"httpVersion\":\"1.0\", \"host\":\"192.168.33.211\", \"agent\":\"Wget/1.12 (linux-gnu)\", \"http_req_header\":\"GET /setup.asp?id=1%20out_file(a) HTTP/1.0\\\\x0D\\\\x0AUser-Agent: Wget/1.12 (linux-gnu)\\\\x0D\\\\x0AAccept: */*\\\\x0D\\\\x0AHost: 192.168.33.211\\\\x0D\\\\x0AConnection: Keep-Alive\\\\x0D\\\\x0A\\\\x0D\\\\x0A\", \"http_res_code\":404, \"requestTime\":\"0\", \"content_type\":\"\", \"content_length\":\"0\", \"file_list\":[]}\n");
                //ahwarning测试:
                //writer.write("{\"sendHostAddress\":\"10.10.40.114\", \"machineCode\":\"083a88c6ae44\", \"transProtocol\":\"TCP\", \"appProtocol\":\"ftp\", \"logSessionId\":\"2209021106150533537\", \"srcAddress\":\"185.212.130.14\", \"srcPort\":\"49141\", \"srcMacAddress\":\"00-23-89-8D-29-F1\", \"destMacAddress\":\"54-EE-75-96-F0-34\", \"destAddress\":\"192.168.10.216\", \"destPort\":\"21\", \"vlanId\":\"0\", \"productVendorName\":\"安恒\", \"deviceAddress\":\"10.10.40.114\", \"eventCount\":\"1\", \"deviceSendProductName\":\"安恒APT攻击（网络战）预警机\", \"deviceProductType\":\"入侵检测系统\", \"deviceName\":\"devicename\", \"deviceVersion\":\"V2.0.69\", \"srcGeoCountry\":\"荷兰\", \"srcGeoRegion\":\"荷兰\", \"srcGeoAddress\":\"yandex.ru\", \"srcGeoLongitude\":\"5.32986\", \"srcGeoLatitude\":\"52.1082\", \"destGeoCountry\":\"中国\", \"destGeoRegion\":\"北京\", \"destGeoCity\":\"北京\", \"destGeoLongitude\":\"116.327805\", \"destGeoLatitude\":\"39.941715\", \"direction\":\"10\", \"srcSecurityZone\":\"outer\", \"destSecurityZone\":\"inner_886da035-c033-46b8-8ce8-b31e03db7ab2_1537173568979\", \"logType\":\"alert\", \"dataType\":\"ids\", \"dataSubType\":\"attackAlert\", \"deviceCat\":\"/IDS/Network\", \"catObject\":\"/Host/Application/Service\", \"catBehavior\":\"/Access\", \"catOutcome\":\"Attempt\", \"catTechnique\":\"/Code/Trojan\", \"severity\":\"7\", \"catSignificance\":\"/Informational/Alert\", \"eventId\":\"220902110722939996302\", \"startTime\":\"2022-09-02 11:07:22\", \"endTime\":\"2022-09-02 11:07:22\", \"collectorReceiptTime\":\"2022-09-02 11:07:22\", \"ruleName\":\"Trojan.Win32.Bublik.rzr;;修改内存地址为可读可写可执行;将数据写入远程进程;调用CreateRemoteThread进行线程注入;结束自身进程;复制文件句柄（一般用于防删除）;遍历文件;加载资源到内存;写入自启动注册表,增加自启动2;修改windows防火墙设置;打开服务控制管理器;收集磁盘信息;系统配置信息收集;调用加密算法库;尝试打开Csrss进程\", \"ruleType\":\"/Malware/Trojan\", \"payload\":\"恶意软件通过修改内存属性，以达到在内存中解密&执行恶意代码<br/>调用WriteProcessMemory将数据写入其它进程地址空间,以达到注入shellcode或恶意dll。<br/>通过调用CreateRemoteThread在其它进程地址空间中运行的恶意代码。<br/>当发现当前运行环境不符合时（如检测到杀毒软件等），主动退出达到规避检测的目的.恶意行为可能没有完全触发。<br/>恶意程序通过复制句柄的方式占用句柄,以达到文件占坑影响文件正常操作的目的<br/>通过文件遍历查找指定目标文件<br/>恶意程序通过从资源段释放资源到内存中，进行解密操作<br/>恶意程序通过修改注册表的方式实现随系统自启动,以达到长期控制或驻留系统的目的<br/>通过修改windows防火墙设置,达到绕过防火墙保护<br/>恶意程序通过打开服务控制管理器(Service Control Manager),以达到对服务进行控制的目的<br/>恶意程序通过获取用户磁盘信\\\\xE6\\\\x81\", \"name\":\"恶意文件攻击->木马 : Trojan.Win32.Bublik.rzr;;修改内存地址为可读可写可执行;将数据写入远程进程;调用CreateRemoteThread进行线程注入;结束自身进程;复制文件句柄（一般用于防删除）;遍历文件;加载资源到内存;写入自启动注册表,增加自启动2;修改windows防火墙设置;打开服务控制管理器;收集磁盘信息;系统配置信息收集;调用加密算法库;尝试打开Csrss进程\", \"opType\":\"download\", \"txId\":\"0\", \"fileName\":\"7s.rar [ 3.exe ]\", \"fileMd5\":\"1a20d628cb89aa1750ab634d1bd60e48\", \"fileType\":\"rar\", \"fileSize\":\"436072\", \"sha256\":\"fc294f97d3e1564cc21f1e56c74585d86dee41084d525edcc16da539af51fde5\", \"virusType\":\"木马\", \"sandboxReportId\":\"246\", \"attackStage\":\"2\", \"attackDirection\":\"0\", \"attackStatus\":\"2\"}\n");
                //
                //writer.write("{\"messageProtocolVersion\":\"1\",\"messageDeviceTypeId\":1,\"messageProductId\":0,\"messageDeviceDescribe\":\"0\",\"messageEmbeddedSoftwareVersion\":\"0\",\"messageChipVersion\":\"0\",\"messageDeviceSerialId\":\"JAJX01710001C100\",\"messagePackageId\":0,\"messageLoadLength\":0,\"messageNumber\":1,\"messageSplitSerialId\":0,\"verifyCode\":0,\"reserved\":0,\"basicMessageBasicList\":[{\"srcMac\":\"11-22-33-aa-44-55\",\"destMac\":\"10-20-30-40-50-60\",\"srcIp\":\"10.10.10.100\",\"destIp\":\"10.10.10.110\",\"srcPort\":8080,\"destPort\":8090,\"protocolType\":0,\"byteNumber\":-31395,\"packageNumber\":21773,\"verifyMessageBodyType\":1,\"verifyTypeId\":101,\"loadLength\":31,\"verifyFunctionModuleCode\":0}],\"tempVerifyCode\":0,\"messageSplit\":false}\n");
                //{"messageProtocolVersion":"0","messageDeviceTypeId":0,"messageProductId":0,"messageDeviceDescribe":"0","messageEmbeddedSoftwareVersion":"0","messageChipVersion":"0","messageDeviceSerialId":"JAJX01710001C100","messagePackageId":0,"messageLoadLength":0,"messageNumber":1,"messageSplitSerialId":0,"verifyCode":0,"reserved":0,"basicMessageBasicList":[{"srcMac":"11-22-33-aa-44-55","destMac":"10-20-30-40-50-60","srcIp":"10.10.10.100","destIp":"10.10.10.110","srcPort":8080,"destPort":8090,"protocolType":0,"byteNumber":-31395,"packageNumber":21773,"verifyMessageBodyType":1,"verifyTypeId":101,"loadLength":31,"verifyFunctionModuleCode":0}],"tempVerifyCode":0,"messageSplit":false}

                //"srcMac":"11-22-33-aa-44-55",
                //writer.write("{\"userId\":\"1\",\"day\":\"2020-01-05\",\"data\":[{\"package\":\"com.zyd\",\"activetime\":\"2311\"}]}\n");
                Thread.sleep(5000);
                writer.flush();
                writer.close();

                System.out.println("数据发送");
            }
            //写完以后进行读操作
            /*BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            //设置超时间为10秒
            client.setSoTimeout(10*1000);
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            try {
                while ((temp=br.readLine()) != null) {
                    if ((index = temp.indexOf("eof")) != -1) {
                        sb.append(temp.substring(0, index));
                        break;
                    }
                    sb.append(temp);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("数据读取超时。");
            }
            System.out.println("服务端: " + sb);
            writer.close();
            br.close();
            client.close();*/
        }
    }
