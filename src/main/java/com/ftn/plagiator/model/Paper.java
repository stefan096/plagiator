package com.ftn.plagiator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ftn.plagiator.dto.PaperDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Paper{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String pathForPDF;

	public Paper(PaperDTO paper) {
		this.id = paper.getId();
		this.title = paper.getTitle();
		this.pathForPDF = paper.getPathForPDF();
	}

}
