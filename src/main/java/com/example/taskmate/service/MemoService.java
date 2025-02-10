package com.example.taskmate.service;

import com.example.taskmate.entity.Memo;

public interface MemoService {

	// 登録
	void regist(Memo memo);

	// １件削除
	void remove(Integer memoId);

}
