package com.ftn.plagiator.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaperDTO {

	private Long id;
	private String title;
	private MultipartFile file;
	private String pathForPDF;
	
//	private String content;
//	private String text;
	private double searchHits;
}



