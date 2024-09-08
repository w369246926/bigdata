package com.flink.flinktask;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConstants {

    public static String PROPERTIES_FILE_NAME = "";

    static {
        Properties properties = new Properties();
        InputStream in = PropertiesConstants.class.getClassLoader().getResourceAsStream("config/application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String profile = properties.getProperty("fenv.profile");
        if (profile.equals("dev")) {
            PROPERTIES_FILE_NAME = "config/application-dev.properties";
        }
        if (profile.equals("test")) {
            PROPERTIES_FILE_NAME = "config/application-test.properties";
        }
        if (profile.equals("prod")) {
            PROPERTIES_FILE_NAME = "config/application-prod.properties";
        }
    }

    /**
     * Datax参数
     */
    public static final String TOPIC = "topic";
    public static final String TASKNAME = "taskName";
    public static final String GROUPID = "groupId";
    public static final String ISES = "isES";


    /**
     * flink环境参数
     */
    public static final String STREAM_PARALLELISM = "fenc.flink.stream.parallelism";
    public static final String STREAM_SINK_PARALLELISM = "fenc.flink.stream.sink.parallelism";
    public static final String STREAM_DEFAULT_PARALLELISM = "fenc.flink.stream.default.parallelism";

    public static final String STREAM_CHECKPOINT_ENABLE = "fenc.flink.stream.checkpoint.enable";
    public static final String STREAM_CHECKPOINT_DIR = "fenc.flink.stream.checkpoint.dir";
    public static final String STREAM_CHECKPOINT_TYPE = "fenc.flink.stream.checkpoint.type";
    public static final String STREAM_CHECKPOINT_INTERVAL = "fenc.flink.stream.checkpoint.interval";

    /**
     * kafka参数 消费者
     */
    public static final String KAFKA_BROKERS = "fenc.kafka.brokers";
    public static final String KAFKA_GROUP_ID = "fenc.kafka.groupId";
    public static final String CONSUMER_KAFKA_TOPIC = "fenc.kafka.consumer.topic";
    public static final String KAFKA_ZOOKEEPER = "fenc.kafka.zookeeper";

    /**
     * hbase参数
     */
    public static final String HBASE_ZOOKEEPER_QUORUM = "fenc.hbase.zookeeper.quorum";
    public static final String HBASE_CLIENT_RETRIES_NUMBER = "fenc.hbase.zookeeper.clientPort";
    public static final String HBASE_NAMESPACE = "fenc.hbase.namespace";
    public static final String HBASE_ROODIR = "fenc.hbase.roodir";
    public static final String HBASE_DEFAULT_VERSION_SKIP = "fenc.hbase.defaults.for.version.skip";
    public static final String HBASE_ZK_ZNODE_PARENT = "fenc.hbase.zookeeper.znode.parent";

    /**
     * es参数
     */
    public static final String ES_CLUSTERNAME = "fenc.es.clustername";
    public static final String ES_INETADDRS = "fenc.es.inetaddrs";
    public static final String ES_PORT = "fenc.es.port";


    /**
     * hdfs 配置参数
     */
    public static final String HDFS_PATH = "hdfs.path";
    public static final String HDFS_PATH_DATE_FORMAT = "hdfs.path.date.format";
    public static final String HDFS_PATH_DATE_ZONE = "hdfs.path.date.zone";


    /**
     * hive参数
     */
    public static final String HIVE_JDBC_URL = "hive.jdbc.url";
    public static final String HIVE_DATABASE = "hive.database";
    public static final String HIVE_LOCATION = "hive.hdfs.location";
    /**
     * impala参数
     */
    public static final String IMPALA_JDBC_URL = "impala.jdbc.url";


    /**
     * mysql参数
     */
    public static final String MYSQL_DRIVER = "fenc.mysql.driver";
    public static final String MYSQL_URL = "fenc.mysql.url";
    public static final String MYSQL_USERNAME = "fenc.mysql.username";
    public static final String MYSQL_PASSWORD = "fenc.mysql.password";


    /**
     * redis的参数
     */
    public static final String REDIS_HOST = "fenc.redis.host";
    public static final String REDIS_PORT = "fenc.redis.port";
    public static final String REDIS_PASSWORD = "fenc.redis.password";
    public static final String REDIS_TIMEOUT = "fenc.redis.timeout";
    public static final String REDIS_DATABASE = "fenc.redis.database";
    public static final String REDIS_MAXIDLE = "fenc.redis.maxidle";
    public static final String REDIS_MINIDLE = "fenc.redis.minidle";
    public static final String REDIS_MAXTOTAL = "fenc.redis.maxtotal";

    /**
     * 告警 fix 参数
     */
    public static final String FIX_FTP_IP = "fenc.fix.ftp.ip";
    public static final String FIX_FTP_PORT = "fenc.fix.ftp.port";
    public static final String FIX_FTP_USER = "fenc.fix.ftp.user";
    public static final String FIX_FTP_PWD = "fenc.fix.ftp.pwd";
    public static final String FIX_TCP_IP = "fenc.fix.tcp.ip";
    public static final String FIX_TCP_PORT = "fenc.fix.tcp.port";

    /**
     * fenc.fix.ydtcp.ip=10.10.42.234
     * fenc.fix.ydtcp.port=19997
     */
    public static final String FIX_YDTCP_IP = "fenc.fix.ydtcp.ip";
    public static final String FIX_YDTCP_PORT = "fenc.fix.ydtcp.port";

    public static final String FIX_WARNTCP_IP = "fenc.fix.warntcp.ip";
    public static final String FIX_WARNTCP_PORT = "fenc.fix.warntcp.port";


    public static final String FIX_SIGNALTCP_IP = "fenc.fix.signaltcp.ip";
    public static final String FIX_SIGNALTCP_PORT = "fenc.fix.signaltcp.port";

    /**
     * 交互音频去重
     */
    public static final String FIX_AUDIO_REDIS_URL = "fenc.fix.audio.redis_url";
    public static final String FIX_AUDIO_REDIS_PASSWARD = "fenc.fix.audio.redis_passward";

}

