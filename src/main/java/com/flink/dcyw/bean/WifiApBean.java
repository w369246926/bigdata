package com.flink.dcyw.bean;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

public class WifiApBean implements Serializable {


    private Long id;

    //0为正常 1为告警
    private Integer warning = 0;

    /*
     * 数据类型
     */
    @TableField(value = "event_type")
    private String eventType;

    /*
     * 设备id
     */
    private Integer deviceId;

    /*
     * ssid
     */
    private String ssid;

    /*
     * mac
     */
    private String mac;

    private String manufacturer;

    /*
     * 加密信息
     */
    private String encryption;

    /*
     * 信道
     */
    private Integer channel;

    /*
     * 功率值
     */
    private Integer power;

    /*
     * 距离
     */
    private String distance;

    /*
     * 监测场所信息
     */
    private String monitoringSite;

    /*
     * 索引名
     */
    private String indexName;

    private String updateTime;

    private String unixTime;

    private Integer status;

    private Integer risk;

    private String apType;

    private Integer icount;

    private Integer freqBand;

	private String upside;
	private String down;
	private Integer duration;


    public WifiApBean(){}
    public WifiApBean(long id, String event_type, long device_id, String ssid, String mac, String encryption, Long channel, String power, String distance, String monitoring_site, String index_name, String updateTime2, String unix_time, long risk, long status, String ap_type, long icount, long freq_band, String upside, String down, String duration) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMonitoringSite() {
        return monitoringSite;
    }

    public void setMonitoringSite(String monitoringSite) {
        this.monitoringSite = monitoringSite;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(String unixTime) {
        this.unixTime = unixTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(Integer risk) {
        this.risk = risk;
    }

    public String getApType() {
        return apType;
    }

    public void setApType(String apType) {
        this.apType = apType;
    }

    public Integer getIcount() {
        return icount;
    }

    public void setIcount(Integer icount) {
        this.icount = icount;
    }

    public Integer getFreqBand() {
        return freqBand;
    }

    public void setFreqBand(Integer freqBand) {
        this.freqBand = freqBand;
    }

    public Integer getWarning() {
        return warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

	public String getUpside() {
		return upside;
	}

	public void setUpside(String upside) {
		this.upside = upside;
	}

	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
