package com.example.taskmate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmate.entity.Status;
import com.example.taskmate.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

	private final StatusRepository statusRepository;
	
	@Override
	@Transactional(readOnly = true)	
	public List<Status> findAll() {

		List<Status> list = statusRepository.selectAll();
		
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Status findByCode(String statusCode) {

		Status status = statusRepository.selectByCode(statusCode);
		
		return status;
	}

}
