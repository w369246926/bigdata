package com.flink.domain;


public class tracelocal {
    private String event_type;
    private int data_timestamp;
    private int device_id;
    private String fre_start;
    private String fre_stop;
    private String rbw;
    private String ref_level;
    private String igain;
    private String iatt;
    private String point;
    private String segment;
    private String btrace_name;
    private String threshold;
    private String index_name;
    private String compoundcode;
    private String read_format;
    private String file_id;

    public tracelocal() {
    }

    public tracelocal(String event_type, int data_timestamp, int device_id, String fre_start, String fre_stop, String rbw, String ref_level, String igain, String iatt, String point, String segment, String btrace_name, String threshold, String index_name, String compoundcode, String read_format, String file_id) {
        this.event_type = event_type;
        this.data_timestamp = data_timestamp;
        this.device_id = device_id;
        this.fre_start = fre_start;
        this.fre_stop = fre_stop;
        this.rbw = rbw;
        this.ref_level = ref_level;
        this.igain = igain;
        this.iatt = iatt;
        this.point = point;
        this.segment = segment;
        this.btrace_name = btrace_name;
        this.threshold = threshold;
        this.index_name = index_name;
        this.compoundcode = compoundcode;
        this.read_format = read_format;
        this.file_id = file_id;
    }

    @Override
    public String toString() {
        return "trace_local{" +
                "event_type='" + event_type + '\'' +
                ", data_timestamp=" + data_timestamp +
                ", device_id=" + device_id +
                ", fre_start='" + fre_start + '\'' +
                ", fre_stop='" + fre_stop + '\'' +
                ", rbw='" + rbw + '\'' +
                ", ref_level='" + ref_level + '\'' +
                ", igain='" + igain + '\'' +
                ", iatt='" + iatt + '\'' +
                ", point='" + point + '\'' +
                ", segment='" + segment + '\'' +
                ", btrace_name='" + btrace_name + '\'' +
                ", threshold='" + threshold + '\'' +
                ", index_name='" + index_name + '\'' +
                ", compoundcode='" + compoundcode + '\'' +
                ", read_format='" + read_format + '\'' +
                ", file_id='" + file_id + '\'' +
                '}';
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public int getData_timestamp() {
        return data_timestamp;
    }

    public void setData_timestamp(int data_timestamp) {
        this.data_timestamp = data_timestamp;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getFre_start() {
        return fre_start;
    }

    public void setFre_start(String fre_start) {
        this.fre_start = fre_start;
    }

    public String getFre_stop() {
        return fre_stop;
    }

    public void setFre_stop(String fre_stop) {
        this.fre_stop = fre_stop;
    }

    public String getRbw() {
        return rbw;
    }

    public void setRbw(String rbw) {
        this.rbw = rbw;
    }

    public String getRef_level() {
        return ref_level;
    }

    public void setRef_level(String ref_level) {
        this.ref_level = ref_level;
    }

    public String getIgain() {
        return igain;
    }

    public void setIgain(String igain) {
        this.igain = igain;
    }

    public String getIatt() {
        return iatt;
    }

    public void setIatt(String iatt) {
        this.iatt = iatt;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getBtrace_name() {
        return btrace_name;
    }

    public void setBtrace_name(String btrace_name) {
        this.btrace_name = btrace_name;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getIndex_name() {
        return index_name;
    }

    public void setIndex_name(String index_name) {
        this.index_name = index_name;
    }

    public String getCompoundcode() {
        return compoundcode;
    }

    public void setCompoundcode(String compoundcode) {
        this.compoundcode = compoundcode;
    }

    public String getRead_format() {
        return read_format;
    }

    public void setRead_format(String read_format) {
        this.read_format = read_format;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
}
