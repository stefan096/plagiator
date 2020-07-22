package com.ftn.plagiator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.plagiator.model.AdvancePaper;
import com.ftn.plagiator.repository.AdvancePaperRepository;

@Service
public class AdvancePaperService {

	@Autowired
	AdvancePaperRepository advancePaperRepository;
	
	public AdvancePaper findOne(Long id) {
		return advancePaperRepository.findById(id).get();
	}

	public AdvancePaper save(AdvancePaper advancePaper) {
		return advancePaperRepository.save(advancePaper);
	}
	
	public List<AdvancePaper> findByResultItemId(Long id){
		return advancePaperRepository.findByResultItemId(id);
	}
}
