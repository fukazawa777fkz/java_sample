package com.example.taskmate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.taskmate.entity.Memo;

@Mapper
public interface MemoMapper {

	// 登録
	void insert(@Param("memo") Memo memo);

	// １件削除
	void delete(@Param("memoId") Integer memoId);

}
