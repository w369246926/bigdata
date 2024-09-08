package com.flink.tree;

import java.util.List;

public class BusGroupDevYs {

    //todo:group表
    //子级
    private Integer groupId;  //ID
    //private Integer subCount = 0;
    //单位名称
    private String groupName;  //单位名称
    //private Integer groupSn;
    //private Timestamp createTime;
    //父级
    private Integer pid;  //父ID
    //private String unit;
    //private String createBy;
    //private String updateBy;
    //private Timestamp updateTime;
    //todo:devices
    //private Integer id;
    //设备名称
    private String devName;  //设备名称
    //设别编码
    private String devSn;  //设备编号
    private String devType;  //设备类型
    private Integer devStatus;  //设备状态
    //private String createBy;
    //private String updateBy;
    //private Timestamp createTime;
    //private Timestamp updateTime;
    private String isip;  //内网IP
    private String osip;  //外网IP
    //private String cloak;
    //private Long warning;
    //private Long flow;
    //private Long strategy;
    //private Integer groupId;
    //private BusGroup busGroup;
    //private Integer pidsn;
    private String pidname; //驻地信息
    //private String whitelist;
    //private String camouflage;
    //private String safety;
    //private String security;
    //private String visit;
    //private String strategyPort;
//    private String deploymentMode;
//    private String useSpace;
//    private String remainingSpace;
//    private String totalDiskCapacity;
//    private String numberOfPoliciesUsed;
//    private String numberOfRemainingPolicies;
//    private String totalPolicies;
//    private String softwareVersion;
//    private String fpgaVersion;
//    private Integer devMold;
    //隐身表
    //private Integer id;
    //private String devSn;
    private Integer isDelete;
    private Integer isWork;
    //    private String whiteFrom;
//    private Integer index;
//    private String action;
//    private String actionName;
//    private Integer protocolType;
//    private Integer isByIp;
    private String startSrcIp;
    private String endSrcIp;
    private String startSrcPort;
    private String endSrcPort;
    private String startDestIp;
    private String endDestIp;
    private String startDestPort;
    private String endDestPort;
    private String desc;  //描述
    //    private String dealUser;  //处理人
//    private String md5Value;
//    private Integer vlanId;
//    private String outputNetInterface;
//    private String inputNetInterface;
//    private String messageNetInterface;
//    private String repSrcMac;
//    private String repDestMac;
//    private String repSrcIp;
//    private String repDestIp;
//    private String repSrcPort;
//    private String repDestPort;
//    private Integer repVlanId;
//    private String repTtl;
//    private Integer repVxlanId;
//    private String repVxlanDirection;
//    private String repVxlanVni;
//    private String repVxlanEn;
    private String strategy_name;  //策略名称
    //    private String bqMiji;
//    private String bqDisControl;
//    private String bqSecLevel;
//    private String bqMessagePrompt;
//    private String carEn;
//    private Integer carId;
//    private String mesageEn;
//    private String stutfulCheck;

    //重点!!!
    private List<BusGroupDevYs> children;



}
