package com.zf.study.data.mapper;


import com.zf.study.core.entity.person;
import com.zf.study.core.entity.personExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface personMapper {
    int countByExample(personExample example);

    int deleteByExample(personExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(person record);

    int insertSelective(person record);

    List<person> selectByExample(personExample example);

    person selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") person record, @Param("example") personExample example);

    int updateByExample(@Param("record") person record, @Param("example") personExample example);

    int updateByPrimaryKeySelective(person record);

    int updateByPrimaryKey(person record);
}