package com.flink.jdbc;

public class sql拼接 {
    public static void main(String[] args  , AssetsBean assetsBean) {
        //增加查询条件
        String whereSql = whereStrategySql(assetsBean);
        //SqlParameterSource ps=new BeanPropertySqlParameterSource(AssetsBean.class);//插入数据时 javabean转驼峰
        String countSql = "select count(1) from bus_devices where 1=1 "+whereSql;

    }
    private static String whereStrategySql(AssetsBean assetsBean){
        StringBuilder str =new StringBuilder();
        String devName1 = assetsBean.getDev_name();
        if(devName1!=null && !devName1.equals("")){
            str.append(" and dev_name='"+devName1+"'");
        }
        String devSn = assetsBean.getDev_sn();
        if(devSn!=null && !devSn.equals("")){
            str.append(" and dev_sn='"+devSn+"'");
        }
        String devType = assetsBean.getDev_type();
        if(devType!=null && !devType.equals("")){
            str.append(" and dev_type='"+devType+"'");
        }
        String groupid = assetsBean.getGroup_id();
        if(groupid!=null && !groupid.equals("")){
            str.append(" and group_id='"+groupid+"'");
        }
        String devStatus = assetsBean.getDev_status();
        if(devStatus!=null && !devStatus.equals("")){
            str.append(" and dev_status='"+devStatus+"'");
        }
        return str.toString();
    }


    private class AssetsBean {
        String Dev_name;

        String Dev_sn;

        String Dev_type;

        String Group_id;

        String Dev_status;

        public String getDev_name() {
            return Dev_name;
        }

        public void setDev_name(String dev_name) {
            Dev_name = dev_name;
        }

        public String getDev_sn() {
            return Dev_sn;
        }

        public void setDev_sn(String dev_sn) {
            Dev_sn = dev_sn;
        }

        public String getDev_type() {
            return Dev_type;
        }

        public void setDev_type(String dev_type) {
            Dev_type = dev_type;
        }

        public String getGroup_id() {
            return Group_id;
        }

        public void setGroup_id(String group_id) {
            Group_id = group_id;
        }

        public String getDev_status() {
            return Dev_status;
        }

        public void setDev_status(String dev_status) {
            Dev_status = dev_status;
        }
    }
}
