package com.ftn.plagiator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class PaperDTO {

	private Long id;
	private String title;
	private MultipartFile file;
	private String pathForPDF;
}

