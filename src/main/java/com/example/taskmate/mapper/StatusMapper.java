package com.example.taskmate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.taskmate.entity.Status;

@Mapper
public interface StatusMapper {

	// 全件検索
	List<Status> selectAll();

	// １件検索
	Status selectByCode(@Param("statusCode") String statusCode);

}
