package com.example.taskmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmate.entity.Memo;
import com.example.taskmate.mapper.MemoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

	private final MemoMapper memoMapper;
	
	@Override
	@Transactional
	public void regist(Memo memo) {

		memoMapper.insert(memo);
		
	}

	@Override
	@Transactional
	public void remove(Integer memoId) {

		memoMapper.delete(memoId);
		
	}

}
