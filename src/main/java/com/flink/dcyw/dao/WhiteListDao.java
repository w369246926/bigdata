package com.flink.dcyw.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.flink.dcyw.bean.WhiteListBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WhiteListDao extends BaseMapper<WhiteListBean> {


    Integer selectApCount(@Param("mac")String mac);

    Integer selectClientCount(@Param("terminalMac")String terminalMac);
}
