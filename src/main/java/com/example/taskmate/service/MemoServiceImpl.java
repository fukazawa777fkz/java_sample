package com.example.taskmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmate.entity.Memo;
import com.example.taskmate.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

	private final MemoRepository memoRepository;
	
	@Override
	@Transactional
	public void regist(Memo memo) {

		memoRepository.insert(memo);
		
	}

	@Override
	@Transactional
	public void remove(Integer memoId) {

		memoRepository.delete(memoId);
		
	}

}
