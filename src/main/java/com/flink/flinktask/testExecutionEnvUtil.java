package com.flink.flinktask;

//import cn.hutool.core.util.IdUtil;

import com.flink.flinktask.PropertiesConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class testExecutionEnvUtil {

    /**
     * ParameterTool全局参数
     * mergeWith()的会覆盖前面的
     *
     * @param args
     * @return
     */
    public static ParameterTool createParameterTool(final String[] args) {
        try {
            return ParameterTool
                    .fromPropertiesFile(testExecutionEnvUtil.class.getResourceAsStream(PropertiesConstants.PROPERTIES_FILE_NAME))
                    .mergeWith(ParameterTool.fromArgs(args))
                    .mergeWith(ParameterTool.fromSystemProperties());
        } catch (Exception e) {
            log.error("获取ParameterTool全局参数异常");
        }
        return ParameterTool.fromSystemProperties();

    }

    /**
     * ParameterTool全局参数
     *
     * @return
     */
    public static ParameterTool createParameterTool() {
        try {
            String propertiesFileName = PropertiesConstants.PROPERTIES_FILE_NAME;
            InputStream in = testExecutionEnvUtil.class.getClassLoader().getResourceAsStream(propertiesFileName);
            return ParameterTool.fromPropertiesFile(in);
        } catch (Exception e) {
            log.error("获取ParameterTool全局参数异常");
        }
        return ParameterTool.fromSystemProperties();
    }

    public static void main(String[] args) {
        ParameterTool parameterTool = createParameterTool();
        System.out.println(11);
    }


    public static List<InetSocketAddress> getEsInetSocketAddr(ParameterTool cfg) throws UnknownHostException {
        List<InetSocketAddress> transportAddresses = new ArrayList<>();
        String esIp = cfg.get(PropertiesConstants.ES_INETADDRS, "");
        int esPort = cfg.getInt(PropertiesConstants.ES_PORT, 9300);
        String[] split = esIp.split(",");
        for (int i = 0; i < split.length; i++) {
            transportAddresses.add(new InetSocketAddress(InetAddress.getByName(split[i]), esPort));
        }
        return transportAddresses;
    }

    public static Map<String, String> getEsUserCustompro(String clusterName) {
        Map<String, String> config = new HashMap<>();
        config.put("cluster.name", clusterName);
        config.put("bulk.flush.max.actions", "100");
        config.put("bulk.flush.max.size.mb", "500");
        config.put("bulk.flush.interval.ms", "5000");
        config.put("bulk.flush.backoff.enable", "true");
        config.put("bulk.flush.backoff.type", "CONSTANT");
        config.put("bulk.flush.backoff.delay", "2");
        config.put("bulk.flush.backoff.retries", "3");
//        config.put("connectTimeout", "5000");
//        config.put("socketTimeout", "40000");
//        config.put("connectionRequestTimeout", "1000");
//        config.put("maxRetryTimeoutMillis", "300000");
        return config;
    }

    public static ParameterTool createKafkaProperties(ParameterTool cfg) {
        String zookeeperConnect = cfg.get(PropertiesConstants.KAFKA_ZOOKEEPER, "");
        String bootstrapServers = cfg.get(PropertiesConstants.KAFKA_BROKERS, "");
        //String groupId = cfg.get(PropertiesConstants.GROUPID, IdUtil.simpleUUID() + "");
        Map properties = new HashMap();
        properties.put("zookeeper.connect", zookeeperConnect);
        properties.put("bootstrap.servers", bootstrapServers);
        //properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("max.poll.records", "15000");
        properties.put("auto.offset.reset", "latest");
        properties.put("session.timeout.ms", "30000");
        properties.put("heartbeat.interval.ms", "10000");
        properties.put("max.partition.fetch.bytes", "120971520");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.put("fetch.max.wait.ms", "500");
        properties.put("fetch.min.bytes", "3655360");
        //  properties.put("receive.buffer.bytes", "655360");
        //  new TypeInformationSerializationSchema()
        return ParameterTool.fromMap(properties);
    }

    public static ParameterTool getParameterTool(String[] args) throws IOException {
        //定义CFG变量
        ParameterTool cfg;
        //定义cfg1变量，创建parameterTool,
        /*
        *    public static ParameterTool createParameterTool(final String[] args) {
        try {
            return ParameterTool
                    .fromPropertiesFile(testExecutionEnvUtil.class.getResourceAsStream(PropertiesConstants.PROPERTIES_FILE_NAME))
                    .mergeWith(ParameterTool.fromArgs(args))
                    .mergeWith(ParameterTool.fromSystemProperties());
        } catch (Exception e) {
            log.error("获取ParameterTool全局参数异常");
        }
        return ParameterTool.fromSystemProperties();

    }
    *
    *public static ParameterTool createParameterTool() {
        try {
            String propertiesFileName = PropertiesConstants.PROPERTIES_FILE_NAME;
            InputStream in = testExecutionEnvUtil.class.getClassLoader().getResourceAsStream(propertiesFileName);
            return  ParameterTool.fromPropertiesFile(in);
        } catch (Exception e) {
            log.error("获取ParameterTool全局参数异常");
        }
        return ParameterTool.fromSystemProperties();
    }  */
        ParameterTool cfg1 = testExecutionEnvUtil.createParameterTool();
        //
        String local_path = ParameterTool.fromArgs(args).get("local_path", null);
        ParameterTool paramsDatax = ParameterTool.fromPropertiesFile(local_path);
        cfg = cfg1.mergeWith(paramsDatax);
        return cfg;
    }

    public static ParameterTool getParameterTool() throws IOException {
        return testExecutionEnvUtil.createParameterTool();
    }
}
