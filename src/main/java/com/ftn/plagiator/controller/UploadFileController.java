package com.ftn.plagiator.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ftn.plagiator.dto.PaperDTO;
import com.ftn.plagiator.dto.PaperResultPlagiator;
import com.ftn.plagiator.dto.ResultItem;
import com.ftn.plagiator.elasticsearch.model.PaperElastic;
import com.ftn.plagiator.elasticsearch.repository.PaperElasticRepository;
import com.ftn.plagiator.model.Paper;
import com.ftn.plagiator.service.EmailService;
import com.ftn.plagiator.service.PaperService;
import com.ftn.plagiator.service.SearchService;
import com.ftn.plagiator.service.UserService;
import com.ftn.plagiator.util.FileClass;
import com.ftn.plagiator.util.ObjectMapperUtil;
import com.ftn.plagiator.validation.StaticData;

@Controller
public class UploadFileController {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	SearchService searchService;

	@Autowired
	PaperElasticRepository paperElasticRepository;
	
	@Autowired
	ObjectMapperUtil objectMapper;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService userService;
  
    @RequestMapping(value = "api/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
    		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaperDTO> addPaper(@ModelAttribute PaperDTO paperDTO) {

    	PaperElastic paperElastic = this.uploadFileAndSavePaperElastic(paperDTO);
    	if(paperElastic == null) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}

		return new ResponseEntity<>(objectMapper.map(paperElastic, PaperDTO.class), HttpStatus.OK);
	}
    
    @RequestMapping(value = "api/file/upload/new", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
    		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaperResultPlagiator> addPaperNew(@ModelAttribute PaperDTO paperDTO, HttpServletRequest request) {

    	PaperElastic paperElastic = this.uploadFileAndSavePaperElastic(paperDTO);
    	if(paperElastic == null) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	//uploadovao je uspesno novi rad sad da vidimo koji slicni radovi postoje
    	Paper paper = paperService.findOne(paperElastic.getId());
		File file = new File(paper.getPathForPDF());
    	String stringParam =  FileClass.getTextFromFile(paperDTO.getFile().getOriginalFilename(), file);
		//System.out.println(stringParam);
		
		
		String[] wordList = stringParam.split("\\s+"); //splituj po space-u
		System.out.println(wordList.length);
		
		StringBuilder strBilder = new StringBuilder();
		PaperResultPlagiator paperResultPlagiator = new PaperResultPlagiator();
		int partOfPage = 0;
		
		for(int i=1; i <= wordList.length; ++i) {
			
			strBilder.append(wordList[i-1]);
			strBilder.append(" ");
			
			if(i % StaticData.NUMBERS_OF_WORDS_SPLITER == 0 || i == wordList.length) {
				//dosao sam do prvog parceta teksta odnosno poslednjeg
				
				Page<PaperElastic> papers = searchService.listaRadova(strBilder.toString());
				strBilder = new StringBuilder();
				++partOfPage;
				
				List<PaperDTO> retVal = new ArrayList<PaperDTO>();
				int counter = 0;
				
				if(papers.hasContent()) { //ukoliko ima bar jedan dokument	
					for (PaperElastic item : papers) {
					
						//iskljuciti dodavanje istog
//						if(item.getId() != paper.getId()) {
//							
//						}
						
						retVal.add(objectMapper.map(item, PaperDTO.class));
						
						++counter;
						if(counter == StaticData.NUMBERS_OF_FILES) {
							break;
						}
					}
				}
				
				ResultItem resultItem = new ResultItem();
				resultItem.setPapers(retVal);
				resultItem.setPartOfPage(partOfPage);
				paperResultPlagiator.getItems().add(resultItem);
			}
		}
		
		//TODO: poslati neki mail ako je potrebno
		Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        System.out.println(email);
        
//        User logged = userService.findByEmail(email);
//        
//		try {
//			emailService.sendNotificaitionUploadOfNewDocument(logged, paperDTO.getFile().getOriginalFilename());
//		} catch (MailException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		return new ResponseEntity<>(paperResultPlagiator, HttpStatus.OK);
	}
    
	@RequestMapping(value = "api/file/download/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> download(@PathVariable Long id, HttpServletResponse response) {

		Paper paper = paperService.findOne(id);

		if (paper == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		byte[] content;

		try {
			File paperPdf = new File(paper.getPathForPDF());
			System.out.println(paper.getPathForPDF());
			content = Files.readAllBytes(paperPdf.toPath());
			headers.setContentDispositionFormData(paperPdf.getName(), paperPdf.getName());
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		} catch (IOException e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
	
	private PaperElastic uploadFileAndSavePaperElastic(PaperDTO paperDTO) {
		String path = "";
		try {
			path = FileClass.saveFile(paperDTO.getFile(), StaticData.PUTANJA_DO_FAJLA);
		} catch (IOException e) {

			return null; 
		}

		paperDTO.setPathForPDF(path);
		paperDTO.setTitle(paperDTO.getFile().getOriginalFilename());
		//paperDTO.setTitle(paperDTO.getFile().getOriginalFilename().split(".")[0]);
		
		//do something with file
		String text = "";
		File file = new File(path);
		text = FileClass.getTextFromFile(paperDTO.getFile().getOriginalFilename(), file);
		//System.out.println(text);
		//do something with file
		
		Paper paper = new Paper(paperDTO);
		paper = paperService.save(paper);
		
		PaperElastic paperElastic = new PaperElastic();
		paperElastic.setId(paper.getId());
		paperElastic.setText(text);
		paperElastic.setTitle(paper.getTitle());
		paperElastic = paperElasticRepository.save(paperElastic);
		
		return paperElastic;
	}
	

}
