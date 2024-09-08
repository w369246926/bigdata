package com.flink.dcyw.bean;

public class WifiApClientBean {

	private Long id;
    
    /*
	 * 数据类型
	 */
	private String eventType;
    
    /*
	 * 时间
	 */
	private Long time;
	
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
	
	/*
	 * 加密信息
	 */
	private String encryption;
	
	private Integer icount;
	
	/*
	 * 终端ssid
	 */
	private String terminalSsid;
	/*
	 * 终端mac
	 */
	private String terminalMac;
	
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

	private String primaryClassification;

	private String secondaryClassification;

	private Integer status;

	private String clientType;

	private Integer risk;

	private String upside;
	private String down;
	private Integer duration;

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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
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

	public Integer getIcount() {
		return icount;
	}

	public void setIcount(Integer icount) {
		this.icount = icount;
	}

	public String getTerminalSsid() {
		return terminalSsid;
	}

	public void setTerminalSsid(String terminalSsid) {
		this.terminalSsid = terminalSsid;
	}

	public String getTerminalMac() {
		return terminalMac;
	}

	public void setTerminalMac(String terminalMac) {
		this.terminalMac = terminalMac;
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

	public String getPrimaryClassification() {
		return primaryClassification;
	}

	public void setPrimaryClassification(String primaryClassification) {
		this.primaryClassification = primaryClassification;
	}

	public String getSecondaryClassification() {
		return secondaryClassification;
	}

	public void setSecondaryClassification(String secondaryClassification) {
		this.secondaryClassification = secondaryClassification;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Integer getRisk() {
		return risk;
	}

	public void setRisk(Integer risk) {
		this.risk = risk;
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
