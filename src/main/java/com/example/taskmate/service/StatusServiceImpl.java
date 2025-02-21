package com.example.taskmate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmate.entity.Status;
import com.example.taskmate.mapper.StatusMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

	private final StatusMapper statusMapper;
	
	@Override
	@Transactional(readOnly = true)	
	public List<Status> findAll() {

		List<Status> list = statusMapper.selectAll();
		
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Status findByCode(String statusCode) {

		Status status = statusMapper.selectByCode(statusCode);
		
		return status;
	}

}
