package com.ftn.plagiator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.plagiator.dto.PaperDTO;
import com.ftn.plagiator.service.PaperService;
import com.ftn.plagiator.util.ObjectMapperUtil;

@RestController
@RequestMapping(value = "/api/papers")
public class PaperController {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	ObjectMapperUtil objectMapper;
	
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<PaperDTO>> getPapers(Pageable page) {
		
		return new ResponseEntity<>(objectMapper.mapAll(paperService.findAllFromElastic(), 
				PaperDTO.class), HttpStatus.OK);
	}

}
