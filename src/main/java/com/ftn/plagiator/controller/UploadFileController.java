package com.ftn.plagiator.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ftn.plagiator.elasticsearch.handler.PDFHandler;
import com.ftn.plagiator.elasticsearch.handler.Word2007Handler;
import com.ftn.plagiator.elasticsearch.handler.WordHandler;
import com.ftn.plagiator.elasticsearch.model.PaperElastic;
import com.ftn.plagiator.elasticsearch.repository.PaperElasticRepository;
import com.ftn.plagiator.model.Paper;
import com.ftn.plagiator.service.PaperService;
import com.ftn.plagiator.util.PdfClass;
import com.ftn.plagiator.validation.StaticData;

@Controller
public class UploadFileController {
	
	@Autowired
	PaperService paperService;

	@Autowired
	PaperElasticRepository per;
	
	private PDFHandler pdfHandler = new PDFHandler();
	private WordHandler wordHandler = new WordHandler();
	private Word2007Handler word2007Handler = new Word2007Handler();
  
    @RequestMapping(value = "api/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
    		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaperDTO> addPaper(@ModelAttribute PaperDTO paperDTO) {

		String path = "";
		try {
			path = PdfClass.saveFile(paperDTO.getFile(), StaticData.PUTANJA_DO_FAJLA);
		} catch (IOException e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		paperDTO.setPathForPDF(path);
		paperDTO.setTitle(paperDTO.getFile().getOriginalFilename());
		//paperDTO.setTitle(paperDTO.getFile().getOriginalFilename().split(".")[0]);
		
		//do something with file
		String text = "";
		File file = new File(path);
		
		if(paperDTO.getFile().getOriginalFilename().contains(".pdf")) {
			text = pdfHandler.getText(file);
		}
		else if(paperDTO.getFile().getOriginalFilename().contains(".docx")){
			text = word2007Handler.getText(file);
		}
		else if(paperDTO.getFile().getOriginalFilename().contains(".doc")){
			text = wordHandler.getText(file);
		}

		System.out.println(text);
		//do something with file
		
		Paper paper = new Paper(paperDTO);
		paper = paperService.save(paper);
		
		PaperElastic paperElastic = new PaperElastic();
		paperElastic.setId(paper.getId());
		paperElastic.setText(text);
		paperElastic.setTitle(paper.getTitle());
		per.save(paperElastic);

		return new ResponseEntity<>(new PaperDTO(), HttpStatus.OK);
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
}
