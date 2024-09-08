package com.flink.dcyw.bean;

import com.baomidou.mybatisplus.annotations.TableField;

public class WifiProbeBean {

    private Long id;
    @TableField("event_type")
    private String eventType;
    private Long time;
    private Integer deviceId;
    private String ssid;
    private String mac;
    private String encryption;
    private String terminalSsid;
    private String terminalMac;
    private Integer channel;
    private String primaryClassification;
    private String secondaryClassification;
    private String power;
    private String distance;
    private String monitoringSite;
    private String indexName;
    private String upside;
    private String down;
    private Integer duration;

    public WifiProbeBean() {
    }

    public WifiProbeBean(Long id, String eventType, Long time, Integer deviceId, String ssid, String mac, String encryption, String terminalSsid, String terminalMac, Integer channel, String primaryClassification, String secondaryClassification, String power, String distance, String monitoringSite, String indexName, String upside, String down, Integer duration) {
        this.id = id;
        this.eventType = eventType;
        this.time = time;
        this.deviceId = deviceId;
        this.ssid = ssid;
        this.mac = mac;
        this.encryption = encryption;
        this.terminalSsid = terminalSsid;
        this.terminalMac = terminalMac;
        this.channel = channel;
        this.primaryClassification = primaryClassification;
        this.secondaryClassification = secondaryClassification;
        this.power = power;
        this.distance = distance;
        this.monitoringSite = monitoringSite;
        this.indexName = indexName;
        this.upside = upside;
        this.down = down;
        this.duration = duration;
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

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
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
