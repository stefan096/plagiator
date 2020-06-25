package com.ftn.plagiator.elasticsearch.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaperElasticDTO {

	private Long id;
	private String title;
	private String coauthor;
	private String keyTerms;
	private String abstractForPaper;
	//private ScientificAreaElastic scientificAreaForPaper;
	private String journal;
	private String author;
	private String content;
	private boolean openAccess;
	//private List<UserElastic> users; //recezenti
	private MultipartFile[] fajlovi;

}
