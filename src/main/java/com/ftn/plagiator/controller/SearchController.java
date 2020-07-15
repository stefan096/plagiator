package com.ftn.plagiator.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.plagiator.elasticsearch.dto.SimpleQueryDTO;
import com.ftn.plagiator.elasticsearch.model.PaperElastic;
import com.ftn.plagiator.elasticsearch.model.UserElastics;
import com.ftn.plagiator.service.SearchService;

@RestController
@RequestMapping(value = "/api")
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<UserElastics>> search(@RequestBody @NotNull SimpleQueryDTO searchParams, BindingResult result){
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(searchParams.getParams() == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		if(searchParams.getParams().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
//		if(searchParams.isAllFields()) {
//			return new ResponseEntity<>(searchService.executeSearchAll(searchParams), HttpStatus.OK);
//		}
		
		Page<UserElastics> eee = searchService.search2(searchParams);
		
		return new ResponseEntity<>(eee, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/shingl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<PaperElastic>> search22(@RequestBody String stringParam){
	
//		System.out.println(stringParam);
//		List<PaperElastic> papers = searchService.listaRadova(stringParam);
		
		System.out.println(stringParam);
		Page<PaperElastic> papers = searchService.listaRadova(stringParam);
		
		return new ResponseEntity<>(papers, HttpStatus.OK);
	}
	
}
